package org.luchini.rgalaxy.server;

import org.apache.log4j.Logger;
import org.luchini.bgserver.engine.GameState;
import org.luchini.bgserver.server.BGServerThread;
import org.luchini.bgserver.server.GameProtocol;
import org.luchini.bgserver.server.commands.CommandUtils;
import org.luchini.bgserver.server.commands.GameCommand;
import org.luchini.bgserver.server.responses.AbstractResponse;
import org.luchini.bgserver.server.responses.ErrorResponse;
import org.luchini.bgserver.util.LogUtil;
import org.luchini.rgalaxy.engine.RGalaxyEngine;
import org.luchini.treeview.parser.TreeViewBuilder;
import org.luchini.treeview.parser.TreeViewXMLParser;

public class RGalaxyProtocol implements GameProtocol {

	private RGalaxyEngine engine;
	
	private TreeViewBuilder builder;
	private CommandUtils commandUtil;
	
	private static Logger logger = LogUtil.getLogger(RGalaxyProtocol.class);

	private final String commandClasses[] = {
			"org.luchini.rgalaxy.server.commands.StartGameCommand",
			"org.luchini.rgalaxy.server.commands.ChooseRoleCommand"
		};
	
	public RGalaxyProtocol(RGalaxyEngine engine) {
		this.engine = engine;
		this.builder = new TreeViewBuilder(new TreeViewXMLParser());
		this.commandUtil = new CommandUtils(this.commandClasses);
	}
	
	@Override
	public void connectionLost(BGServerThread thread) {
		// TODO Auto-generated method stub
	}

	@Override
	public String processCommand(GameState gameState, BGServerThread thread,
			String command, String[] params) {
		String out = null;
		AbstractResponse resp = null;
		try {
			GameCommand cmd = (GameCommand)this.commandUtil.findCommand(command);
			if (cmd != null) {
				if (CommandUtils.validateParams(cmd.getParamKit(), params)) {
					resp = cmd.exec(this.engine, gameState, thread, params);
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
			out = this.builder.process(out);
		return out;
	}

}
