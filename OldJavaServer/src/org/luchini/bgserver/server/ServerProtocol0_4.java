package org.luchini.bgserver.server;

import org.apache.log4j.Logger;
import org.luchini.bgserver.engine.events.Event;
import org.luchini.bgserver.server.commands.ServerCommand;
import org.luchini.bgserver.server.commands.CommandUtils;
import org.luchini.bgserver.server.responses.AbstractResponse;
import org.luchini.bgserver.server.responses.ErrorResponse;
import org.luchini.bgserver.util.LogUtil;
import org.luchini.treeview.parser.TreeViewBuilder;
import org.luchini.treeview.parser.TreeViewXMLParser;

public class ServerProtocol0_4 implements ServerProtocol {

	private TreeViewBuilder builder;
	private CommandUtils commandUtil;
	
	private static Logger logger = LogUtil.getLogger(ServerProtocol0_4.class);
	
	private final String commandClasses[] = {
		"org.luchini.bgserver.server.commands.CreateRoomCommand",
		"org.luchini.bgserver.server.commands.EnterRoomCommand",
		"org.luchini.bgserver.server.commands.GetMyInfoCommand",
		"org.luchini.bgserver.server.commands.GetRoomInfoCommand",
		"org.luchini.bgserver.server.commands.LeaveGameCommand",
		"org.luchini.bgserver.server.commands.ListMyRoomsCommand",
		"org.luchini.bgserver.server.commands.ListRoomPlayersCommand",
		"org.luchini.bgserver.server.commands.ListRoomsCommand",
		"org.luchini.bgserver.server.commands.QuitCommand",
		"org.luchini.bgserver.server.commands.QuitRoomCommand",
		"org.luchini.bgserver.server.commands.SayCommand",
		"org.luchini.bgserver.server.commands.SeatGameCommand",
		"org.luchini.bgserver.server.commands.SetRoomNickCommand",
		"org.luchini.bgserver.server.commands.SetMyNickCommand",
		"org.luchini.bgserver.server.commands.SubscribeRoomListCommand",
		"org.luchini.bgserver.server.commands.UnsubscribeRoomListCommand",
		"org.luchini.bgserver.server.commands.ListAvailableEnginesCommand"
	};
		
	public ServerProtocol0_4() {
		this.builder = new TreeViewBuilder(new TreeViewXMLParser());
		this.commandUtil = new CommandUtils(this.commandClasses);
	}
	
	@Override
	public String processCommand(Protocol protocol, BGServerThread thread,
			String command, String[] params) {
		String out = null;
		AbstractResponse resp = null;
		try {
			ServerCommand cmd = (ServerCommand)this.commandUtil.findCommand(command);
			if (cmd != null) {
				if (CommandUtils.validateParams(cmd.getParamKit(), params)) {
					resp = cmd.exec(protocol, thread, params);
				} else {
					resp = new ErrorResponse(cmd.command(), ErrorResponse.WRONG_PARAMS_CODE, 
							ErrorResponse.WRONG_PARAMS_MSG);
				}
			}
		} catch (Exception e) {
			resp = new ErrorResponse(command, ErrorResponse.SERVER_ERROR_CODE, 
					ErrorResponse.SERVER_ERROR_MSG);
			logger.error("Error processing: " + command + ". Error: " + e.toString());
		}
		if (resp != null)
			out = this.builder.process(resp);
		return out;
	}
	
	@Override
	public String processEvent(Protocol protocol, BGServerThread thread,
			Event event) {
		String outString = this.builder.process(event);
		return outString;
	}
	
	@Override
	public void connectionLost(Protocol protocol, BGServerThread thread) {
		CommandUtils.closeEverythingDown(protocol.getServerEngine(), thread);
		protocol.getServerEngine().connectionLost(thread.getPlayerInfo());
	}

	@Override
	public String getDescription() {
		return "BGServer ver. [" + this.getVersionNumber() + "]";
	}

	@Override
	public String getVersionNumber() {
		return "0.4";
	}

	@Override
	public String createUnknownResponse() {
		ErrorResponse unknown = new ErrorResponse(Protocol.class.getSimpleName(),
				ErrorResponse.UNKNOWN_CODE, ErrorResponse.UNKNOWN_MSG);
		return this.builder.process(unknown);
	}

		
}