package org.luchini.bgclient.roomlist
{
	import flash.events.Event;

	public class FilterChangeEvent extends Event
	{
		private var _selected:XML;
		
		public function FilterChangeEvent(type:String, selectedEngine:XML, bubbles:Boolean=false, cancelable:Boolean=false)
		{
			super(type, bubbles, cancelable);
			_selected = selectedEngine;
		}
		
		public function get selectedEngine():XML {
			return _selected;
		}
		
	}
}