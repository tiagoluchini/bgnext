package org.luchini.bgclient.roomlist
{
	import flash.events.Event;

	public class RoomCreationEvent extends Event
	{
		private var _roomInfo:XML;
		
		public function RoomCreationEvent(type:String, roomInfo:XML, bubbles:Boolean=false, cancelable:Boolean=false)
		{
			super(type, bubbles, cancelable);
			_roomInfo = roomInfo;
		}
		
		public function get roomInfo():XML {
			return _roomInfo;
		}
		
	}
}