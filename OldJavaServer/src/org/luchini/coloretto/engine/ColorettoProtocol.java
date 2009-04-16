package org.luchini.coloretto.engine;

import org.luchini.bgserver.engine.GameState;
import org.luchini.bgserver.server.BGServerThread;
import org.luchini.bgserver.server.GameProtocol;

public class ColorettoProtocol implements GameProtocol {

	@Override
	public void connectionLost(BGServerThread thread) {
		// TODO Auto-generated method stub

	}

	@Override
	public String processCommand(GameState gameState, BGServerThread thread,
			String command, String[] params) {
		// TODO Auto-generated method stub
		return null;
	}

}
