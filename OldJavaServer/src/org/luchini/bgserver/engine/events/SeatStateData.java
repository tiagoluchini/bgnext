package org.luchini.bgserver.engine.events;

import java.util.ArrayList;
import java.util.List;

import org.luchini.bgserver.engine.SeatState;
import org.luchini.treeview.annotations.TreeAttribute;
import org.luchini.treeview.annotations.TreeNode;

@TreeNode
public class SeatStateData {

	@TreeAttribute private int hashCode;
	@TreeNode private Object gameSpecificData;
	@TreeNode private PlayerInfoData playerInfoData;
	
	private SeatStateData(SeatState seatState) {
		this.hashCode = seatState.hashCode();
		this.playerInfoData = PlayerInfoData.createPlayerInfoData(seatState.getSeatedPlayer());
		this.gameSpecificData = seatState.getGameSpecificData();
	}
	
	public static List<SeatStateData> convertList(List<SeatState> seats) {
		List<SeatStateData> out = null;
		if (seats != null) {
			out = new ArrayList<SeatStateData>(seats.size());
			for (SeatState seat : seats) {
				out.add(SeatStateData.createSeatStateData(seat));
			}
		}
		return out;
	}
	
	public static SeatStateData createSeatStateData(SeatState seatState) {
		if (seatState != null)
			return new SeatStateData(seatState);
		else
			return null;
	}

	public int getHashCode() {
		return hashCode;
	}

	public Object getGameSpecificData() {
		return gameSpecificData;
	}

	public PlayerInfoData getPlayerInfoData() {
		return playerInfoData;
	}
	
}
