package org.luchini.bgserver.server.responses;

import java.util.Collection;

import org.luchini.bgserver.engine.GameEngine;
import org.luchini.bgserver.engine.events.GameEngineData;
import org.luchini.treeview.annotations.TreeAttribute;
import org.luchini.treeview.annotations.TreeNode;

public class AvailableEnginesResponse extends AbstractResponse {

	@TreeAttribute private String sourceRef;	
	@TreeNode private Collection<GameEngineData> engines;

	public AvailableEnginesResponse(String sourceRef, Collection<GameEngine> gameEngines) {
		super(sourceRef);
		this.sourceRef = sourceRef;
		this.engines = GameEngineData.convertCollection(gameEngines);
	}

	@Override
	public String getSourceRef() {
		return this.sourceRef;
	}
	
	public Collection<GameEngineData> getEngines() {
		return this.engines;
	}


}
