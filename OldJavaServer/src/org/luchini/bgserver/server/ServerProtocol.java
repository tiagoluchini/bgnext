package org.luchini.bgserver.server;

import org.luchini.bgserver.engine.events.Event;

public interface ServerProtocol {

	public String processCommand(Protocol protocol, BGServerThread thread, String command, String[] params);
	public String processEvent(Protocol protocol, BGServerThread thread, Event event);
	public void connectionLost(Protocol protocol, BGServerThread thread);
	
	public String getDescription();
	public String getVersionNumber();
	public String createUnknownResponse();
	
}
