package org.luchini.bgserver.server.commands.params;

import java.util.ArrayList;
import java.util.List;

public class ParamKit {

	private List<Param> params;
	
	public ParamKit() {
		this.params = new ArrayList<Param>();
	}
	
	public ParamKit add(Param param) {
		this.params.add(param);
		return this;
	}
	
	public List<Param> getParams() {
		return this.params;
	}
	
}
