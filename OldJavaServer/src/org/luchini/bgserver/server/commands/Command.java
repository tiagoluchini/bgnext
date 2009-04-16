package org.luchini.bgserver.server.commands;

import org.luchini.bgserver.server.commands.params.ParamKit;

public interface Command {

	public String command();
	public ParamKit getParamKit();
	
}
