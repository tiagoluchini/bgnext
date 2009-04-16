package org.luchini.bgserver.server;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.luchini.bgserver.engine.GameEngine;
import org.luchini.bgserver.engine.GameState;
import org.luchini.bgserver.engine.ServerEngine;
import org.luchini.bgserver.engine.events.Event;
import org.luchini.bgserver.server.commands.CommandUtils;
import org.luchini.bgserver.server.commands.params.Param;
import org.luchini.bgserver.server.commands.params.ParamKit;
import org.luchini.bgserver.util.Config;
import org.luchini.bgserver.util.LogUtil;

public class Protocol {

	private static Logger logger = LogUtil.getLogger(Protocol.class);
	
	private static final String NAMESPACE_SEP = ":";
	private static final String COMMAND_SEP = " ";
	private static final String PARAM_SEP = ";";
	
	private static final String ID_PROTOCOLVERSION = "PROTOCOLVERSION";
	public  static final String FORCE_QUIT_SIGNAL = "FORCE_QUIT";
	
	private Map<String, ServerProtocol> serverProtocols;
	private Map<String, GameEngine> engines;
	
	private Map<String, ParamKit> universalCommands;

	private ServerEngine serverEngine;
	
	public Protocol() {
		this.serverEngine = new ServerEngine();
		this.initUniversalCommands();
		this.initServerProtocols();
		this.initEngines();
	}
	
	private void initUniversalCommands() {
		this.universalCommands = new HashMap<String, ParamKit>();
		this.universalCommands.put(Protocol.ID_PROTOCOLVERSION, new ParamKit().add(new Param(true)));
	}
	
	private void initServerProtocols() {
		int qty = Integer.parseInt(Config.getInstance().getEntry(Config.SERVER_PROTOCOL_QTY));
		this.serverProtocols = new HashMap<String, ServerProtocol>();
		for (int i=0; i<qty; i++) {
			String protocolClass = Config.getInstance().getEntry(Config.SERVER_PROTOCOL_CLASS_PREFIX + i);
			logger.debug("ServerProtocol to be loaded: " + protocolClass);
			ServerProtocol serverProtocol = null;
			try {
				serverProtocol = (ServerProtocol)Class.forName(protocolClass).newInstance();
			} catch (InstantiationException e) {
				logger.error("ServerProtocol " + protocolClass + " could not be instantiated: " + e);
			} catch (IllegalAccessException e) {
				logger.error("ServerProtocol " + protocolClass + " has some protection issue: " + e);
			} catch (ClassNotFoundException e) {
				logger.error("ServerProtocol " + protocolClass + " class not found: " + e);
			}
			if (serverProtocol == null) {
				logger.error("A ServerProtocol could not be read. Aborting start up sequence.");
				System.exit(1);
			} else {
				logger.info("Loaded ServerProtocol: " + serverProtocol.getVersionNumber() + " - " + serverProtocol.getDescription());
				this.serverProtocols.put(serverProtocol.getVersionNumber(), serverProtocol);
			}
		}
	}
	
	private void initEngines() {
		this.engines = new HashMap<String, GameEngine>();
		
		int qty = Integer.parseInt(Config.getInstance().getEntry(Config.GAMES_QTY));
		for (int i=0; i<qty; i++) {
			String engineClass = Config.getInstance().getEntry(Config.GAMES_ENGINE_CLASS_PREFIX + i);
			logger.debug("GameEngine to be loaded: " + engineClass);
			GameEngine engine = null;
			try {
				engine = (GameEngine)Class.forName(engineClass).newInstance();
			} catch (InstantiationException e) {
				logger.error("GameEngine " + engineClass + " could not be instantiated: " + e);
			} catch (IllegalAccessException e) {
				logger.error("GameEngine " + engineClass + " has some protection issue: " + e);
			} catch (ClassNotFoundException e) {
				logger.error("GameEngine " + engineClass + " class not found: " + e);
			}
			if (engine == null) {
				logger.error("A GameEngine could not be read. Aborting start up sequence.");
				System.exit(1);
			} else {
				logger.info("Loaded GameEngine: " + engine.getUniqueName() + " - " + engine.getName());
				this.engines.put(engine.getUniqueName(), engine);
			}
		}
	}
	
	public GameEngine findGameEngine(String uniqueName) {
		return this.engines.get(uniqueName);
	}
	
	public ServerEngine getServerEngine() {
		return this.serverEngine;
	}
	
	public Collection<GameEngine> listGameEngines() {
		return this.engines.values();
	}
	
	public String processInput(BGServerThread thread, String input) {
		logger.debug("Processing submission: " + input + " - from client: " + 
				thread.getPlayerInfo().getNickname());
		String out = null;
		if (input != null) {
			String[] line = input.split(COMMAND_SEP, 2); 
			String command = line[0];
			String[] stringParams = null;
			if (line.length > 1) {
				stringParams = line[1].split(PARAM_SEP);
			}
			if (thread.getProtocolVersion() == null) {
				out = this.checkAndSetProtocolVersion(thread, command, stringParams);
				if (thread.getProtocolVersion() == null) {
					out = Protocol.FORCE_QUIT_SIGNAL;
				}
			} else {
				String realCommand = this.getCommPartRealCommand(command);
				String uniqueID = this.getCommPartUniqueID(command);
				ServerProtocol serverProtocol = this.serverProtocols.get(thread.getProtocolVersion());				
				if (uniqueID == null) {
					logger.debug("ServerProtocol " + serverProtocol.getVersionNumber() + " will be used for processing.");
					out = serverProtocol.processCommand(this, thread, realCommand, stringParams);					
				} else {
					GameState game = this.serverEngine.findGameState(uniqueID);
					if (game != null) {
						String engineUnique = game.getRoomInfo().getGameEngineInfo().getUniqueName();
						GameEngine engine = this.engines.get(engineUnique);
						logger.debug("GameEngine " + engine.getUniqueName() + " will be used for processing.");
						if (engine != null) {
							out = engine.getGameProtocol().processCommand(game, thread, command, stringParams);
						}
					}
				}
				if (out == null) {
					out = serverProtocol.createUnknownResponse();
				}
			}
		}
		logger.debug("Responding to " + thread.getPlayerInfo().getNickname() + " with: " + out);
		return out;
	}
	
	private String checkAndSetProtocolVersion(BGServerThread thread, String command, String[] stringParams) {
		String out = null;
		ParamKit protocolKit = this.universalCommands.get(command.toUpperCase());
		if (protocolKit != null) {
			if (CommandUtils.validateParams(protocolKit, stringParams)) {
				ServerProtocol serverProtocol = this.serverProtocols.get(stringParams[0]);
				if (serverProtocol != null) {
					logger.debug("Client " + thread.getPlayerInfo().getNickname() + 
							" is using ServerProtocol version " + stringParams[0]);
					thread.setProtocolVersion(serverProtocol.getVersionNumber());
					out = "OK: PROTOCOLVERSION SET TO [" + stringParams[0] + "] ENJOY!";
				}
			}
		}
		return out;
	}
	
	public String createIDMsg() {
		return "Welcome to BGServer [0.4]";
	}
	
	public String processEvent(BGServerThread thread, Event event) {
		logger.debug("Processing event: " + event.getReference() + 
				" for client: " + thread.getPlayerInfo().getNickname());
		ServerProtocol serverProtocol = this.serverProtocols.get(thread.getProtocolVersion());				
		logger.debug("ServerProtocol " + serverProtocol.getVersionNumber() + " will be used for processing.");
		String out = serverProtocol.processEvent(this, thread, event);
		logger.debug("Sending event to " + thread.getPlayerInfo().getNickname() + 
				" content: " + out);
		return out;
	}
	
	public void connectionLost(BGServerThread thread) {
		ServerProtocol serverProtocol = this.serverProtocols.get(thread.getProtocolVersion());
		if (serverProtocol != null) {
			serverProtocol.connectionLost(this, thread);
		}
	}
	
	private String getCommPartRealCommand(String command) {
		String[] commandParts = command.split(Protocol.NAMESPACE_SEP);
		String realCommand = null;
		if (commandParts.length == 1) {
			realCommand = commandParts[0];
		} else {
			realCommand = commandParts[1];
		}
		return realCommand;
	}
	
	private String getCommPartUniqueID(String command) {
		String[] commandParts = command.split(Protocol.NAMESPACE_SEP);
		String uniqueID = null;
		if (commandParts.length == 2) {
			uniqueID = commandParts[0];
		}
		return uniqueID;		
	}
}
