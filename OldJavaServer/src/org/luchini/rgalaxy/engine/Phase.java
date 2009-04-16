package org.luchini.rgalaxy.engine;

import org.luchini.treeview.annotations.TreeAttribute;
import org.luchini.treeview.annotations.TreeNode;

@TreeNode(attributesOnly=true)
public class Phase {

	@TreeAttribute private String id;
	@TreeAttribute private String name;
	@TreeAttribute private PhaseType phaseType;
	
	public Phase(PhaseType phaseType) {
		this.id = phaseType.getID();
		this.name = phaseType.getName();
		this.phaseType = phaseType;
	}
	
	public String getID() {
		return this.id;
	}
	public String getName() {
		return this.name;
	}
	public PhaseType getPhaseType() {
		return this.phaseType;
	}
	
}
