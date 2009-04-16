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
//  Some of this file probably contain code I originally got from:
//  http://www.arpitonline.com/blog/?p=35
//  I forget how much of the original code I ended up using versus writing new.
//  Check out the guy's blog. Good stuff.
////////////////////////////////////////////////////////////////////////////////
package com.dougmccune.controls {
	
	import com.dougmccune.containers.SuperTabNavigator;
	import com.dougmccune.containers.tabBarClasses.SuperTab;
	import com.dougmccune.events.TabReorderEvent;
	
	import flash.display.DisplayObject;
	import flash.events.Event;
	import flash.events.MouseEvent;
	
	import mx.collections.IList;
	import mx.containers.ViewStack;
	import mx.controls.Button;
	import mx.controls.TabBar;
	import mx.core.ClassFactory;
	import mx.core.DragSource;
	import mx.core.IFlexDisplayObject;
	import mx.core.IUIComponent;
	import mx.core.mx_internal;
	import mx.events.DragEvent;
	import mx.managers.DragManager;
	import flash.display.Bitmap;
	import flash.display.BitmapData;
	import mx.containers.Canvas;
	import mx.core.UIComponent;
	
	public class SuperTabBar extends TabBar{
		
		use namespace mx_internal;
		
		public static const TABS_REORDERED:String = "tabsReordered";
		
		private var _closePolicy:String = SuperTab.CLOSE_ROLLOVER;
		
		private var _dragEnabled:Boolean = true;
		public function get dragEnabled():Boolean {
			return _dragEnabled;
		}
		
		public function set dragEnabled(value:Boolean):void {
			this._dragEnabled = value;
			
			var n:int = numChildren;
		    for (var i:int = 0; i < n; i++)
		    {
		    	var child:SuperTab = SuperTab(getChildAt(i));
		    	
		    	if(value) {
		    		addDragListeners(child);
		    	}
		    	else {
		    		removeDragListeners(child);
		    	}
		    }
		}
		
		private var _dropEnabled:Boolean = true;
		public function get dropEnabled():Boolean {
			return _dropEnabled;
		}
		
		public function set dropEnabled(value:Boolean):void {
			this._dropEnabled = value;
			
			var n:int = numChildren;
		    for (var i:int = 0; i < n; i++)
		    {
		    	var child:SuperTab = SuperTab(getChildAt(i));
		    	
		    	if(value) {
		    		addDropListeners(child);
		    	}
		    	else {
		    		removeDropListeners(child);
		    	}
		    }
		}
		
		
		
		public function get closePolicy():String {
			return _closePolicy;
		}
		
		public function set closePolicy(value:String):void {
			this._closePolicy = value;
			this.invalidateDisplayList();
			
			var n:int = numChildren;
	        for (var i:int = 0; i < n; i++)
	        {
	            var child:SuperTab = SuperTab(getChildAt(i));
				child.closePolicy = value;
	        }
		}
		
		public function SuperTabBar(){
			super();
			navItemFactory = new ClassFactory(SuperTab);
		}
		
		override protected function createNavItem(
										label:String,
										icon:Class = null):IFlexDisplayObject{
											
			var tab:SuperTab = super.createNavItem(label,icon) as SuperTab;
			
			tab.closePolicy = this.closePolicy;
			if(dragEnabled) {
				addDragListeners(tab);
			}
			
			if(dropEnabled) {
				addDropListeners(tab);
			}
			tab.addEventListener(SuperTab.CLOSE_TAB_EVENT, onCloseTabClicked, false, 0, true);
			
			return tab;
		}
		
		private function addDragListeners(tab:SuperTab):void {
			tab.addEventListener(MouseEvent.MOUSE_DOWN, tryDrag, false, 0, true);
			tab.addEventListener(MouseEvent.MOUSE_UP, removeDrag, false, 0, true);
		}
		
		private function removeDragListeners(tab:SuperTab):void {
			tab.removeEventListener(MouseEvent.MOUSE_DOWN, tryDrag);
			tab.removeEventListener(MouseEvent.MOUSE_UP, removeDrag);
		}
		
		private function addDropListeners(tab:SuperTab):void {
			tab.addEventListener(DragEvent.DRAG_ENTER, tabDragEnter, false, 0, true);
			tab.addEventListener(DragEvent.DRAG_OVER, tabDragOver, false, 0, true);
			tab.addEventListener(DragEvent.DRAG_DROP, tabDragDrop, false, 0, true);
			tab.addEventListener(DragEvent.DRAG_EXIT, tabDragExit, false, 0, true);	
		}
		
		private function removeDropListeners(tab:SuperTab):void {
			tab.removeEventListener(DragEvent.DRAG_ENTER, tabDragEnter);
			tab.removeEventListener(DragEvent.DRAG_OVER, tabDragOver);
			tab.removeEventListener(DragEvent.DRAG_DROP, tabDragDrop);
			tab.removeEventListener(DragEvent.DRAG_EXIT, tabDragExit);	
		}
		
		private function tryDrag(e:MouseEvent):void{
			e.target.addEventListener(MouseEvent.MOUSE_MOVE, doDrag);
		}
		
		private function removeDrag(e:MouseEvent):void{
			e.target.removeEventListener(MouseEvent.MOUSE_MOVE,doDrag);
		}
		public function onCloseTabClicked(event:Event):void{
			var index:int = getChildIndex(DisplayObject(event.currentTarget));
			if(dataProvider is IList){
				dataProvider.removeItemAt(index);
			}
			else if(dataProvider is ViewStack){
				dataProvider.removeChildAt(index);
			}
		}
		
		private function doDrag(event:MouseEvent):void{
				if(IUIComponent(event.target) is SuperTab || (IUIComponent(event.target).parent is SuperTab && !(IUIComponent(event.target) is Button))) {
					
					var tab:SuperTab;
					
					if(IUIComponent(event.target) is SuperTab) {
						tab = IUIComponent(event.target) as SuperTab;
					}
					
					if(IUIComponent(event.target).parent is SuperTab) {
						tab = IUIComponent(event.target).parent as SuperTab;
					}
					
					var ds:DragSource = new DragSource();
					ds.addData(event.currentTarget,'tabDrag');
					
					var bmapData:BitmapData = new BitmapData(tab.width, tab.height, true, 0x00000000);
					bmapData.draw(tab);
					var dragProxy:Bitmap = new Bitmap(bmapData); 
					
					var obj:UIComponent = new UIComponent();
					obj.addChild(dragProxy);
					
					DragManager.doDrag(IUIComponent(event.target),ds,event,obj);	
				}					
		}
		
		
		
		private function tabDragEnter(event:DragEvent):void{
			if(event.dragSource.hasFormat('tabDrag') && event.draggedItem != event.dragInitiator){
				
				DragManager.acceptDragDrop(IUIComponent(event.target));
			}
		}
		
		private function tabDragOver(event:DragEvent):void{
			if(event.dragSource.hasFormat('tabDrag') && event.dragInitiator != event.currentTarget){
				
				
				var dropTab:SuperTab = (event.currentTarget as SuperTab);
				var dropIndex:Number = this.getChildIndex(dropTab);
				
				var gap:Number = 0;
				
				var left:Boolean = event.localX < dropTab.width/2;
				
				if((left && dropIndex > 0) 
					|| (dropIndex < this.numChildren - 1) ) {
					gap = this.getStyle("horizontalGap")/2;
				}
				
				if(left) {
					gap = -gap;
				}
				else {
					gap = dropTab.width + gap;
				}
				
				dropTab.showIndicatorAt(gap);
				
				DragManager.showFeedback(DragManager.LINK);
			}
		}
		
		private function tabDragExit(event:DragEvent):void{
			var dropTab:SuperTab = (event.currentTarget as SuperTab);
			dropTab.showIndicator = false;
				
		}
		
		private function tabDragDrop(event:DragEvent):void{
			if(event.dragSource.hasFormat('tabDrag') && event.draggedItem != event.dragInitiator){
			
				var dropTab:SuperTab = (event.currentTarget as SuperTab);
				var dragTab:SuperTab = (event.dragInitiator as SuperTab);
				
				var left:Boolean = event.localX < dropTab.width/2;
				
				var parentNavigator:SuperTabNavigator;
				var parentBar:SuperTabBar;
				
				var object:* = event.dragInitiator;
				while(object && object.parent) {
					object = object.parent;
					
					if(object is SuperTab) {
						dragTab = object;
						break;
					}
				}
				
				object = dragTab;
				while(object && object.parent) {
					object = object.parent;
					
					if(object is SuperTabBar) {
						parentBar = object;
					}
					if(object is SuperTabNavigator) {
						parentNavigator = object as SuperTabNavigator;
						break;
					}
				}
				
				
					
				dropTab.showIndicator = false;
				
				var oldIndex:Number = parentBar.getChildIndex(dragTab);
				
				
				var newIndex:Number = this.getChildIndex(dropTab);
				if(!left) {
					newIndex += 1;
				}
				
				this.dispatchEvent(new TabReorderEvent(SuperTabBar.TABS_REORDERED, false, false, parentNavigator, oldIndex, newIndex));
			}	
		}	
		
		
	}
}