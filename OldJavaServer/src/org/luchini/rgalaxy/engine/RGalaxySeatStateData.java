package org.luchini.rgalaxy.engine;

import org.luchini.treeview.annotations.TreeAttribute;
import org.luchini.treeview.annotations.TreeNode;

@TreeNode(attributesOnly=true)
public class RGalaxySeatStateData {

	@TreeAttribute protected int earnedVPs;
	
	public RGalaxySeatStateData(RGalaxySeatState seat) {
		this.earnedVPs = seat.getEarnedVPs();
	}
	
}
