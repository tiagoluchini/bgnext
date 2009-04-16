package org.luchini.bgserver.engine.events;

import java.util.ArrayList;
import java.util.List;

import org.luchini.bgserver.engine.RoomInfo;
import org.luchini.bgserver.engine.StatusType;
import org.luchini.treeview.annotations.TreeAttribute;
import org.luchini.treeview.annotations.TreeNode;

@TreeNode
public class RoomInfoData {

	@TreeAttribute private String uniqueID;
	@TreeAttribute private String nickname;
	@TreeAttribute private int totalSeats;
	@TreeAttribute private int seatsAvailable;
	@TreeAttribute private int minimumSeatedToStart;
	@TreeAttribute private StatusType statusType;
	
	@TreeNode private GameEngineData gameEngineData; 
	@TreeNode(alias="playersInRoom") private List<PlayerInfoData> players;
	@TreeNode(alias="seatedPlayers") private List<SeatStateData> seatStates;
	
	@TreeNode protected Object gameSpecificData;

	private RoomInfoData(RoomInfo roomInfo) {
		this.uniqueID = roomInfo.getUniqueID();
		this.nickname = roomInfo.getNickname();
		this.totalSeats = roomInfo.getTotalSeats();
		this.seatsAvailable = roomInfo.getSeatsAvailable();
		this.minimumSeatedToStart = roomInfo.getMinimumSeatedToStart();
		this.statusType = roomInfo.getStatusType();
		
		this.gameEngineData = GameEngineData.createGameEngineData(roomInfo.getGameEngine());
		this.players = PlayerInfoData.convertList(roomInfo.getPlayers());
		this.seatStates = SeatStateData.convertList(roomInfo.getSeatStates());
			
		this.gameSpecificData = roomInfo.getGameSpecificData();
	}
	
	public static List<RoomInfoData> convertList(List<RoomInfo> rooms) {
		List<RoomInfoData> out = null;
		if (rooms != null) {
			out = new ArrayList<RoomInfoData>(rooms.size());
			for (RoomInfo room : rooms) {
				out.add(RoomInfoData.createRoomInfoData(room));
			}
		}
		return out;
	}

	public static RoomInfoData createRoomInfoData(RoomInfo roomInfo) {
		if (roomInfo != null)
			return new RoomInfoData(roomInfo);
		else
			return null;
	}
	
	public String getUniqueID() {
		return uniqueID;
	}

	public String getNickname() {
		return nickname;
	}

	public int getTotalSeats() {
		return totalSeats;
	}

	public int getSeatsAvailable() {
		return seatsAvailable;
	}

	public int getMinimumSeatedToStart() {
		return minimumSeatedToStart;
	}

	public StatusType getStatusType() {
		return statusType;
	}

	public GameEngineData getGameEngineData() {
		return gameEngineData;
	}

	public List<PlayerInfoData> getPlayers() {
		return players;
	}

	public List<SeatStateData> getSeatStates() {
		return seatStates;
	}

	public Object getGameSpecificData() {
		return gameSpecificData;
	}

	@Override
	public String toString() {
		return this.uniqueID;
	}
	
}
