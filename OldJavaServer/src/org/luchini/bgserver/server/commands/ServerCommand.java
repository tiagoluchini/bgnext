package org.luchini.bgserver.server.commands;

import org.luchini.bgserver.server.BGServerThread;
import org.luchini.bgserver.server.Protocol;
import org.luchini.bgserver.server.responses.AbstractResponse;

public interface ServerCommand extends Command {

	public AbstractResponse exec(Protocol protocol, 
			BGServerThread thread, String[] params); 
}