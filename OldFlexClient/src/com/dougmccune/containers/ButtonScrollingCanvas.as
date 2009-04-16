////////////////////////////////////////////////////////////////////////////////
//
//  Copyright (C) 2007 Doug McCune
//  http://dougmccune.com/blog
//  
//  Permission is hereby granted, free of charge, to any person
//  obtaining a copy of this software and associated documentation
//  files (the "Software"), to deal in the Software without
//  restriction, including without limitation the rights to use, misuse,
//  copy, modify, merge, publish, distribute, love, hate, sublicense, 
//  and/or sell copies of the Software, and to permit persons to whom the
//  Software is furnished to do so, subject to no conditions whatsoever.
// 
//  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
//  EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
//  OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
//  NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
//  HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
//  WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
//  FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
//  OTHER DEALINGS IN THE SOFTWARE. DON'T SUE ME FOR SOMETHING DUMB
//  YOU DO. 
//
////////////////////////////////////////////////////////////////////////////////
package com.dougmccune.containers
{
	import flash.display.DisplayObject;
	import flash.events.Event;
	import flash.events.MouseEvent;
	import flash.events.TimerEvent;
	import flash.utils.Timer;
	
	import mx.containers.Canvas;
	import mx.controls.Button;
	import mx.core.Container;
	import mx.core.EdgeMetrics;
	import mx.core.ScrollPolicy;
	import mx.events.ScrollEvent;

	
	[Style(name="buttonWidth", type="Number", inherit="no")]
	
	[Style(name="leftButtonStyleName", type="String", inherit="no")]
	[Style(name="rightButtonStyleName", type="String", inherit="no")]
	[Style(name="upButtonStyleName", type="String", inherit="no")]
	[Style(name="downButtonStyleName", type="String", inherit="no")]
	
	public class ButtonScrollingCanvas extends Canvas
	{
		private var leftButton:Button;
		private var rightButton:Button;
		private var upButton:Button;
		private var downButton:Button;
		
		private var _explicitButtonHeight:Number;
		
		private var innerCanvas:Canvas;
		
		private var timer:Timer;
		
		public var scrollSpeed:Number = 10;
	   	public var scrollJump:Number = 10;
		
		private var _childrenCreated:Boolean = false;
		
		private var _startScrollingEvent:String = MouseEvent.MOUSE_OVER;
		
		public function get startScrollingEvent():String {
			return this._startScrollingEvent;
		}
		
		public function set startScrollingEvent(value:String):void {
			if(_childrenCreated) {
				removeListeners(_startScrollingEvent);
				addListeners(value);
			}
			_startScrollingEvent = value;
			
		}
		
		private var _stopScrollingEvent:String = MouseEvent.MOUSE_OVER;
		
		public function get stopScrollingEvent():String {
			return this._stopScrollingEvent;
		}
		
		public function set stopScrollingEvent(value:String):void {
			_stopScrollingEvent = value;
		}
		
		public static var DEFAULT_BUTTON_WIDTH:Number = 50;
		
		public function ButtonScrollingCanvas()
		{ 
			super();
		}
		
		override protected function createChildren():void {
			super.createChildren();
			
			timer = new Timer(scrollSpeed);
			
			// We have 4 buttons for each side of the conainter
			leftButton = new Button();
			rightButton = new Button();
			upButton = new Button();
			downButton = new Button();
			
			leftButton.styleName = getStyle("leftButtonStyleName");
			rightButton.styleName = getStyle("rightButtonStyleName");
			upButton.styleName = getStyle("upButtonStyleName");
			downButton.styleName = getStyle("downButtonStyleName");
			
			// this is the main canvas component, we tell it to
			// never show the scrollbars since we're controlling them
			// on our own
			innerCanvas = new Canvas();
			innerCanvas.horizontalScrollPolicy = ScrollPolicy.OFF;
			innerCanvas.verticalScrollPolicy = ScrollPolicy.OFF;
			innerCanvas.clipContent = true;
	        
	        // Since the createChild method can get called after children have 
	        // already been added to the Canvas, we have to swap any children
	        // that have already been added into the innerCanvas	
			while(this.numChildren > 0) {
				innerCanvas.addChild(this.removeChildAt(0));
			}
			
			// Add the innerCanvas and all the buttons to rawChildren
			rawChildren.addChild(innerCanvas);
			rawChildren.addChild(leftButton);
			rawChildren.addChild(rightButton);
			rawChildren.addChild(upButton);
			rawChildren.addChild(downButton);
		
			_childrenCreated = true;
				
			// and of course we listen for mouseover events on our buttons.
			// if you wanted to use mousedown instead you would change these lines
			addListeners(_startScrollingEvent);
		}
		
		private function addListeners(eventString:String):void {
			leftButton.addEventListener(eventString, startScrollingLeft, false, 0, true);
			rightButton.addEventListener(eventString, startScrollingRight, false, 0, true); 
			upButton.addEventListener(eventString, startScrollingUp, false, 0, true); 
			downButton.addEventListener(eventString, startScrollingDown, false, 0, true); 	
		}
		
		private function removeListeners(eventString:String):void {
			leftButton.removeEventListener(eventString, startScrollingLeft);
			rightButton.removeEventListener(eventString, startScrollingRight); 
			upButton.removeEventListener(eventString, startScrollingUp); 
			downButton.removeEventListener(eventString, startScrollingDown); 	
		}
		
		/**
		 * If we have already created the innerCanvas element, then we add the child to
		 * that. If not, that means we haven't called createChildren yet. So what we do
		 * is add the child to this main Canvas, and once we call createChildren we'll
		 * remove all the children and switch them over to innerCanvas.
		 */
		override public function addChild(child:DisplayObject):DisplayObject {
			if(innerCanvas) {
				return innerCanvas.addChild(child);
			}
			else {
				return super.addChild(child);
			}
		}
		
		override public function get horizontalScrollPosition():Number {
			return innerCanvas.horizontalScrollPosition;
		}
		
		override public function set horizontalScrollPosition(value:Number):void {
			innerCanvas.horizontalScrollPosition = value;
			
			callLater(enableOrDisableButtons);
		}
		
		override public function get verticalScrollPosition():Number {
			return innerCanvas.verticalScrollPosition;
		}
		
		override public function set verticalScrollPosition(value:Number):void {
			innerCanvas.verticalScrollPosition = value;
			
			callLater(enableOrDisableButtons);
		}
		
		override public function get maxHorizontalScrollPosition():Number {
			return innerCanvas.maxHorizontalScrollPosition;
		}
		
		override public function get maxVerticalScrollPosition():Number {
			return innerCanvas.maxVerticalScrollPosition;
		}
		
		public function get buttonWidth():Number {
			var s:Number = getStyle("buttonWidth");
			if(s) return s;
			
			return ButtonScrollingCanvas.DEFAULT_BUTTON_WIDTH;
		}
		public function set buttonWidth(value:Number):void {
			this.setStyle("buttonWidth", value);
			invalidateDisplayList();
		}
		
		public function set explicitButtonHeight(value:Number):void {
			this._explicitButtonHeight = value;
			invalidateDisplayList();
		}
		
		
		override protected function updateDisplayList(unscaledWidth:Number, unscaledHeight:Number):void {
			
			super.updateDisplayList(unscaledWidth, unscaledHeight);
			
			innerCanvas.setActualSize(unscaledWidth, unscaledHeight);
			
			positionButtons(unscaledWidth, unscaledHeight);
			
			callLater(enableOrDisableButtons);
		}
		
		private function positionButtons(unscaledWidth:Number, unscaledHeight:Number):void {
			var buttonWidth:Number = this.buttonWidth;
			
			var buttonHeight:Number = _explicitButtonHeight ? _explicitButtonHeight : buttonWidth; 
			
			leftButton.move(0, 0);
			leftButton.setActualSize(buttonWidth, buttonHeight);
			
			rightButton.move(unscaledWidth - buttonWidth, 0);
			rightButton.setActualSize(buttonWidth, buttonHeight);
			
			if(leftButton.visible) {
				upButton.move(buttonWidth, 0);
				downButton.move(buttonWidth, unscaledHeight - buttonWidth);
					
				if(rightButton.visible) {
					upButton.setActualSize(unscaledWidth - buttonWidth * 2, buttonWidth);
					downButton.setActualSize(unscaledWidth - buttonWidth * 2, buttonWidth);
				}
				else {
					upButton.setActualSize(unscaledWidth - buttonWidth, buttonWidth);
					downButton.setActualSize(unscaledWidth - buttonWidth, buttonWidth);
				}
			}
			else {
				upButton.move(0, 0);
				downButton.move(0, unscaledHeight - buttonWidth);
				
				if(rightButton.visible) {
					upButton.setActualSize(unscaledWidth - buttonWidth, buttonWidth);
					downButton.setActualSize(unscaledWidth - buttonWidth, buttonWidth);
				}
				else {
					upButton.setActualSize(unscaledWidth, buttonWidth);
					downButton.setActualSize(unscaledWidth, buttonWidth);
				}
			}
		}
		
		
		
		
		private function startScrollingLeft(event:Event):void {
	    	if(!(event.currentTarget as Button).enabled) return;
	    	
			startScrolling(scrollLeft, event.currentTarget as Button);
	    }
	    
	    private function startScrollingRight(event:Event):void {
	    	if(!(event.currentTarget as Button).enabled) return;
	    	
			startScrolling(scrollRight, event.currentTarget as Button);
	    }
	    
	    private function startScrollingUp(event:Event):void {
	    	if(!(event.currentTarget as Button).enabled) return;
	    	
			startScrolling(scrollUp, event.currentTarget as Button);
	    }
	    
	    private function startScrollingDown(event:Event):void {
	    	if(!(event.currentTarget as Button).enabled) return;
	    	
	    	startScrolling(scrollDown, event.currentTarget as Button);
	    }
	    
	    private function startScrolling(scrollFunction:Function, button:Button):void {
	    	if(_stopScrollingEvent == MouseEvent.MOUSE_UP) {
	    		stage.addEventListener(_stopScrollingEvent, stopScrolling);
	    	}
	    	else {
	    		button.addEventListener(_stopScrollingEvent, stopScrolling);
	    	}
	    	
	    	if(timer.running) {
				timer.stop();
			}
			
			timer = new Timer(this.scrollSpeed);
			timer.addEventListener(TimerEvent.TIMER, scrollFunction);
			
			timer.start();
	    }
	    
	    private function stopScrolling(event:Event):void {
	    	if(timer.running) {
				timer.stop();
			}
	    }
	    
	    private function scrollLeft(event:TimerEvent):void {
	    	innerCanvas.horizontalScrollPosition -= scrollJump;
	    	enableOrDisableButtons();
	    }
	    
	    private function scrollRight(event:TimerEvent):void {
			innerCanvas.horizontalScrollPosition += scrollJump;
			enableOrDisableButtons();
		}
		
		private function scrollUp(event:TimerEvent):void {
	    	innerCanvas.verticalScrollPosition -= scrollJump;
	    	enableOrDisableButtons();
	    }
	    
	    private function scrollDown(event:TimerEvent):void {
			innerCanvas.verticalScrollPosition += scrollJump;
			enableOrDisableButtons();
		}
	    
	   
	    /**
	     * We check to see if the buttons should be shown. If we can't scroll in
	     * one direction then we don't show that particular button.
	     */ 
	    protected function enableOrDisableButtons():void {
	    	leftButton.visible = leftButton.enabled = innerCanvas.horizontalScrollPosition > 0;
	    	rightButton.visible = rightButton.enabled = innerCanvas.horizontalScrollPosition < innerCanvas.maxHorizontalScrollPosition;
	    	
	    	
	    	upButton.visible = upButton.enabled = innerCanvas.verticalScrollPosition > 0;
	    	downButton.visible = downButton.enabled = innerCanvas.verticalScrollPosition < innerCanvas.maxVerticalScrollPosition;
	    
	    
	    	positionButtons(this.width, this.height);
	    }
		
	}
}