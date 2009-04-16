package org.luchini.bgclient.roomlist
{
	import flash.events.Event;

	public class EnterRoomEvent extends Event
	{
		
		private var _selectedRoomInfo:XML;
		
		public function EnterRoomEvent(type:String, selectedRoomInfo:XML, bubbles:Boolean=false, cancelable:Boolean=false)
		{
			super(type, bubbles, cancelable);
			_selectedRoomInfo = selectedRoomInfo;
		}
		
		public function get selectedRoomInfo():XML {
			return _selectedRoomInfo;
		}
		
	}
}