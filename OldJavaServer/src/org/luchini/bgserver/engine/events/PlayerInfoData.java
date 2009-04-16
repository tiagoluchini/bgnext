package org.luchini.bgserver.engine.events;

import java.util.ArrayList;
import java.util.List;

import org.luchini.bgserver.engine.PlayerInfo;
import org.luchini.treeview.annotations.TreeAttribute;
import org.luchini.treeview.annotations.TreeNode;

@TreeNode(attributesOnly=true)
public class PlayerInfoData {

	@TreeAttribute private int hashCode;
	@TreeAttribute private String nickname;
	
	private PlayerInfoData(PlayerInfo playerInfo) {
		this.hashCode = playerInfo.hashCode();
		this.nickname = playerInfo.getNickname();
	}
	
	public static List<PlayerInfoData> convertList(List<PlayerInfo> players) {
		List<PlayerInfoData> out = null;
		if (players != null) {
			out = new ArrayList<PlayerInfoData>(players.size());
			for (PlayerInfo player : players) {
				out.add(PlayerInfoData.createPlayerInfoData(player));
			}
		}
		return out;
	}
	
	public static PlayerInfoData createPlayerInfoData(PlayerInfo playerInfo) {
		if (playerInfo != null)
			return new PlayerInfoData(playerInfo);
		else 
			return null;
	}

	public int getHashCode() {
		return hashCode;
	}

	public String getNickname() {
		return nickname;
	}

	
}
