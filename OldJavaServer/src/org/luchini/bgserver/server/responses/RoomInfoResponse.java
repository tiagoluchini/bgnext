package org.luchini.bgserver.server.responses;

import java.util.List;

import org.luchini.bgserver.engine.RoomInfo;
import org.luchini.bgserver.engine.events.RoomInfoData;
import org.luchini.treeview.annotations.TreeAttribute;
import org.luchini.treeview.annotations.TreeNode;

public class RoomInfoResponse extends AbstractResponse {

	public static final String ALL = "ALL";
	public static final String PERSONAL = "PERSONAL";
	public static final String SPECIFIC_GAME = "SPECIFIC_GAME";
	public static final String SPECIFIC_ROOM = "SPECIFIC_ROOM";
	
	@TreeAttribute private String nature;
	@TreeAttribute private String sourceRef;
	
	@TreeNode private List<RoomInfoData> rooms;
	
	public RoomInfoResponse(String sourceRef, String nature, List<RoomInfo> rooms) {
		super(sourceRef);
		this.sourceRef = sourceRef;
		this.nature = nature;
		this.rooms = RoomInfoData.convertList(rooms);
	}

	public String getNature() {
		return this.nature;
	}
	
	public List<RoomInfoData> getRooms() {
		return this.rooms;
	}
	
	public String getSourceRef() {
		return this.sourceRef;
	}

}
