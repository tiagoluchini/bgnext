package org.luchini.bgserver.server.responses;

import java.util.List;

import org.luchini.bgserver.engine.PlayerInfo;
import org.luchini.bgserver.engine.events.PlayerInfoData;
import org.luchini.treeview.annotations.TreeAttribute;
import org.luchini.treeview.annotations.TreeNode;

public class PlayerInfoResponse extends AbstractResponse {

	public static final String ROOM_PLAYERS = "ROOM_PLAYERS";
	public static final String PERSONAL = "PERSONAL";
	public static final String SPECIFIC_PLAYER = "SPECIFIC_PLAYER";
	
	@TreeAttribute private String nature;
	@TreeAttribute private String sourceRef;
	
	@TreeNode private List<PlayerInfoData> players;
	
	public PlayerInfoResponse(String sourceRef, String nature, List<PlayerInfo> players) {
		super(sourceRef);
		this.nature = nature;
		this.players = PlayerInfoData.convertList(players);
		this.sourceRef = sourceRef;
	}

	public String getNature() {
		return this.nature;
	}
	
	public List<PlayerInfoData> getPlayers() {
		return this.players;
	}

	public String getSourceRef() {
		return this.sourceRef;
	}

}
