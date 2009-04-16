package org.luchini.bgclient.communication
{
	import flash.events.Event;

	public class MessageEvent extends Event
	{
		public static const BY_TYPE_RESPONSE:String = "RESPOMSE";
		public static const BY_TYPE_EVENT:String = "EVENT";
		
		public static const SOURCE_REF_SUBSCRIBEROOMLIST:String = "SUBSCRIBEROOMLIST";
		public static const SOURCE_REF_UNSUBSCRIBEROOMLIST:String = "UNSUBSCRIBEROOMLIST";
		public static const SOURCE_REF_GETMYINFO:String = "GETMYINFO";
		public static const SOURCE_REF_LISTAVAILABLEENGINES:String = "LISTAVAILABLEENGINES";
		public static const SOURCE_REF_ENTERROOM:String = "ENTERROOM";
		public static const SOURCE_REF_CREATEROOM:String = "CREATEROOM";
		
		public static const ROOM_LIST_CHANGE_EVENT:String = "RoomListChangeEvent";
		
		private var _content:XML;
		
		public function MessageEvent(type:String, content:XML, bubbles:Boolean=false, cancelable:Boolean=false)
		{
			super(type, bubbles, cancelable);
			_content = content;
		}
		
		public function get content():XML {
			return _content;
		}
		
	}
}