package org.luchini.bgserver.server;

import java.util.TimerTask;

public class ListEventContender extends TimerTask {

	private BGServerThread thread;
	
	public ListEventContender(BGServerThread thread) {
		this.thread = thread;
	}

	@Override
	public void run() {
		this.thread.respondLastListChanged();
	}

}
