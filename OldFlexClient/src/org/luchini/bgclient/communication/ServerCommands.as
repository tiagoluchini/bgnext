package org.luchini.bgclient.communication
{
	public class ServerCommands
	{
		
		private static const PARAM_SEP:String = ";";
		
		public function ServerCommands() {}
		
		public static function createRoom(gameUniqueName:String):void {
			BGSocket.getInstance().send ("createRoom " + gameUniqueName);
		}
		
		public static function subscribeRoomList():void {
			BGSocket.getInstance().send("subscribeRoomList");
		}
		
		public static function subscribeSpecificRoomList(gameUnique:String):void {
			BGSocket.getInstance().send("subscribeRoomList " + gameUnique);
		}

		public static function unsubscribeRoomList():void {
			BGSocket.getInstance().send("unsubscribeRoomList");
		}
		
		public static function listAvailableEngines():void {
			BGSocket.getInstance().send("listAvailableEngines");
		}
		
		public static function setMyNick(newNick:String):void {
			BGSocket.getInstance().send("setMyNick " + newNick);
		}

		public static function setRoomNick(roomID:String, newNick:String):void {
			BGSocket.getInstance().send("setRoomNick " + roomID + PARAM_SEP + newNick);
		}
		
		public static function getMyInfo():void {
			BGSocket.getInstance().send("getMyInfo");
		}

		public static function enterRoom(roomID:String):void {
			BGSocket.getInstance().send("enterRoom " + roomID);
		}
	}
}