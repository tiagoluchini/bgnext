package org.luchini.bgclient.communication
{
	import flash.events.DataEvent;
	import flash.events.Event;
	import flash.events.EventDispatcher;
	import flash.events.IOErrorEvent;
	import flash.net.XMLSocket;
	
	import mx.controls.Alert;
	
	import org.luchini.bgclient.config.BGConfig;
	
	public class BGSocket extends EventDispatcher
	{
		public static const COMMUNICATION_ERROR:String = "COMMUNICATION_ERROR";
		public static const SERVER_CONNECTED:String = "SERVER_CONNECTED";
		
		private var xmlsocket:XMLSocket;
		private static var _myInstance:BGSocket;
		private var hasNegotiatedVersion:Boolean = false;
		
		public static function getInstance():BGSocket {
			if (_myInstance == null) {
				_myInstance = new BGSocket();
			}
			return _myInstance;
		}
		
		public function BGSocket() {}
		
		public function connect():void {
			xmlsocket = new XMLSocket(BGConfig.SERVER, BGConfig.PORT);
    		xmlsocket.addEventListener(DataEvent.DATA, dataHandler);
    		xmlsocket.addEventListener(Event.CONNECT, connectHandler);
    		xmlsocket.addEventListener(IOErrorEvent.IO_ERROR, errorHandler);
    		xmlsocket.addEventListener(IOErrorEvent.IO_ERROR, errorHandler);			
		}
		
		public function send(command:String):void {
			xmlsocket.send(command + "\r");
		}
		
		private function dataHandler(event:DataEvent):void {
			var out:XML = new XML(event.data);
			if (!hasNegotiatedVersion) {
				if (event.data.indexOf("OK: PROTOCOLVERSION SET TO [0.4]") > -1) {
					hasNegotiatedVersion = true;
					this.dispatchEvent(new Event(SERVER_CONNECTED));
				}
			}
			if (out.@type == "response") {
				this.dispatchEvent(new MessageEvent(MessageEvent.BY_TYPE_RESPONSE, out));
			} else if (out.@type == "event") {
				this.dispatchEvent(new MessageEvent(MessageEvent.BY_TYPE_EVENT, out));
			}
			if (out.localName() != null) {
				this.dispatchEvent(new MessageEvent(out.localName(), out));
			}
			if (out.@sourceRef != null) {
				this.dispatchEvent(new MessageEvent(out.@sourceRef, out));
			}
		}
		
		private function connectHandler(event:Event):void {
			this.send("protocolVersion 0.4");
		}
		
		private function errorHandler(event:Event):void {
			this.dispatchEvent(new Event(COMMUNICATION_ERROR));
		}

	}
}