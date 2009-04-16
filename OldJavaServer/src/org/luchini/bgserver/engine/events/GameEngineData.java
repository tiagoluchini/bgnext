package org.luchini.bgserver.engine.events;

import java.util.ArrayList;
import java.util.Collection;

import org.luchini.bgserver.engine.GameEngine;
import org.luchini.treeview.annotations.TreeAttribute;
import org.luchini.treeview.annotations.TreeNode;

@TreeNode(attributesOnly=true)
public class GameEngineData {

	@TreeAttribute private String uniqueName;
	@TreeAttribute private String name;
	@TreeAttribute private String iconURL;
	
	private GameEngineData(GameEngine gameEngine) {
		this.uniqueName = gameEngine.getUniqueName();
		this.name = gameEngine.getName();
		this.iconURL = gameEngine.getIconURL();
	}
	
	public String getUniqueName() {
		return this.uniqueName;
	}
	public String getName() {
		return this.name;
	}
	public String getIconURL() {
		return this.iconURL;
	}
	
	public static Collection<GameEngineData> convertCollection(Collection<GameEngine> engines) {
		Collection<GameEngineData> out = null;
		if (engines != null) {
			out = new ArrayList<GameEngineData>(engines.size());
			for (GameEngine engine : engines) {
				out.add(GameEngineData.createGameEngineData(engine));
			}
		}
		return out;
	}
	
	public static GameEngineData createGameEngineData(GameEngine engine) {
		if (engine != null)
			return new GameEngineData(engine);
		else 
			return null;
	}
	
}
