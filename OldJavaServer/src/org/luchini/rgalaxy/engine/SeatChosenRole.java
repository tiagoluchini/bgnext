package org.luchini.rgalaxy.engine;

import org.luchini.bgserver.engine.events.SeatStateData;
import org.luchini.treeview.annotations.TreeNode;

@TreeNode
public class SeatChosenRole {

	@TreeNode protected SeatStateData seatStateData;
	@TreeNode protected Phase chosenPhase;
	
	public SeatChosenRole(SeatStateData seatStateData, Phase chosenPhase) {
		this.seatStateData = seatStateData;
		this.chosenPhase = chosenPhase;
	}
	
}
