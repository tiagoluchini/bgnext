package org.luchini.rgalaxy.engine;

import org.luchini.rgalaxy.deck.PhaseBehaviour;
import org.luchini.treeview.annotations.TreeAttribute;
import org.luchini.treeview.annotations.TreeNode;

@TreeNode(attributesOnly=true)
public class PhaseBehaviourPOJO {

	@TreeAttribute protected String phaseID;
	@TreeAttribute protected String phaseName;
	@TreeAttribute protected String reference;
	
	public PhaseBehaviourPOJO(PhaseType phaseType, PhaseBehaviour phaseBehaviour) {
		if (phaseBehaviour != null)
			this.reference = phaseBehaviour.getReference();
		else
			this.reference = "";
		this.phaseID = phaseType.getID();
		this.phaseName = phaseType.getName();
	}
	
}
