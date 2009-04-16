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
	
	import com.dougmccune.controls.ScrollableMenu;
	import com.dougmccune.controls.SuperTabBar;
	import com.dougmccune.events.TabReorderEvent;
	import com.dougmccune.skins.TabPopUpButtonSkin;
	
	import flash.display.DisplayObject;
	import flash.events.Event;
	import flash.events.MouseEvent;
	import flash.events.TimerEvent;
	import flash.utils.Timer;
	
	import mx.collections.ArrayCollection;
	import mx.containers.Canvas;
	import mx.containers.Box;
	import mx.containers.TabNavigator;
	import mx.controls.Button;
	import mx.controls.Menu;
	import mx.controls.PopUpButton;
	import mx.controls.Spacer;
	import mx.controls.tabBarClasses.Tab;
	import mx.core.EdgeMetrics;
	import mx.core.ScrollPolicy;
	import mx.events.ChildExistenceChangedEvent;
	import mx.events.IndexChangedEvent;
	import mx.events.MenuEvent;
	import com.dougmccune.controls.ScrollableArrowMenu;
	import com.dougmccune.controls.SuperTabBar;
	import com.dougmccune.containers.tabBarClasses.SuperTab;
	import mx.containers.BoxDirection;
	import mx.effects.Tween;

	[Style(name="popupButtonStyleName", type="String", inherit="no")]
	
	public class SuperTabNavigator extends TabNavigator
	{
	    
	    /**
	    * holder is a Box component that we are going to use to hold the scrolling
	    * canvas and the popUpMenuButton. I've left this Box, instead of HBox
	    * or VBox because I intend to extend this component to allow for vertical 
	    * tabs along the side.
	    */
	   	protected var holder:Box;
	    
	    /**
	    * canvas is the Canvas component that will hold the TabBar. Tis is used
	    * so we get access to the scrolling functionality of a container. To do
	    * the scrolling we're going to use the horizontalScrollPosition of the 
	    * Canvas.
	    */
	    protected var canvas:ButtonScrollingCanvas;
	    protected var popupButton:PopUpButton;
	    protected var menu:ScrollableArrowMenu;
	    protected var spacer:Spacer;
	    
	  
	  	
		private var forcedTabWidth:Number = -1;
		private var originalTabWidthStyle:Number = -1;
		
		private var _minTabWidth:Number = 60;
		
		
		
		
		
		
		/**
		 * Static variables indicating the policy to show the popUpMenuButton on 
		 * the right side of the tabs. 
		 * 
		 * POPUPPOLICY_AUTO means the PopUpMenuButton will be shown if there is
		 * more thjan one tab.
		 * POPUPPOLICY_ON means the PopUpMenutButton will always be shown, even if
		 * there is only a single tab.
		 * POPUPPOLICY_OFF means the buttons will never be shown.
		 */
		public static var POPUPPOLICY_AUTO:String = "auto";
		public static var POPUPPOLICY_ON:String = "on";
		public static var POPUPPOLICY_OFF:String = "off";
		
		/**
		 * Our internal variable to keep track of the policy to show the PopUpMenuButton
		 */
		private var _popUpButtonPolicy:String;
		
		public function get popUpButtonPolicy():String {
			return _popUpButtonPolicy;	
		}
		
		public function set popUpButtonPolicy(value:String):void {
			var old:String = this._popUpButtonPolicy;
			this._popUpButtonPolicy = value;
			
			if(old != value) {
				this.invalidateDisplayList();
			}
		}
		
		private var _startScrollingEvent:String = MouseEvent.MOUSE_OVER;
		
		public function get startScrollingEvent():String {
			if(canvas) {
				return canvas.startScrollingEvent;
			}
			else {
				return _startScrollingEvent;
			}
		}
		
		public function set startScrollingEvent(value:String):void {
			_startScrollingEvent = value;
			if(canvas) {
				canvas.startScrollingEvent = value;
			}
		}
		
		private var _stopScrollingEvent:String = MouseEvent.MOUSE_OVER;
		
		public function get stopScrollingEvent():String {
			if(canvas) {
				return canvas.stopScrollingEvent;
			}
			else {
				return _stopScrollingEvent;
			}
		}
		
		public function set stopScrollingEvent(value:String):void {
			_stopScrollingEvent = value;
			canvas.stopScrollingEvent = value;
		}
		
		private var _scrollSpeed:Number = 100;
		
		public function set scrollSpeed(value:Number):void {
			if(canvas) {
				canvas.scrollSpeed = value;
			}
			_scrollSpeed = value;	
		}
		
		public function get scrollSpeed():Number {
			if(canvas) return canvas.scrollSpeed;
			return _scrollSpeed;
		}
		
		
		private var _dragEnabled:Boolean = true;
		
		public function get dragEnabled():Boolean {
			if(tabBar) {
				return (tabBar as SuperTabBar).dragEnabled;
			}
			else {
				return _dragEnabled;
			}
		}
		
		public function set dragEnabled(value:Boolean):void {
			_dragEnabled = value;
			
			if(tabBar) {
				(tabBar as SuperTabBar).dragEnabled = value;
			}
		}
		
		private var _dropEnabled:Boolean = true;
		
		public function get dropEnabled():Boolean {
			if(tabBar) {
				return (tabBar as SuperTabBar).dropEnabled;
			}
			else {
				return _dropEnabled;
			}
		}
		
		public function set dropEnabled(value:Boolean):void {
			_dropEnabled = value;
			
			if(tabBar) {
				(tabBar as SuperTabBar).dropEnabled = value;
			}
		}
		
		
		public function get minTabWidth():Number {
			return _minTabWidth;
		}
		
		public function set minTabWidth(value:Number):void {
			this._minTabWidth = value;
			this.invalidateDisplayList();
		}
		
		
		override protected function createChildren():void
	    {
	    	if (!tabBar){
	    		// We're using our custom SuperTabBar class instead of TabBar
				tabBar = new SuperTabBar();
				tabBar.name = "tabBar";
				tabBar.focusEnabled = false;
				tabBar.styleName = this;
				(tabBar as SuperTabBar).dragEnabled = this._dragEnabled;
				(tabBar as SuperTabBar).dropEnabled = this._dropEnabled;
				
				tabBar.setStyle("borderStyle", "none");
				tabBar.setStyle("paddingTop", 0);
				tabBar.setStyle("paddingBottom", 0);
				
				(tabBar as SuperTabBar).closePolicy = SuperTab.CLOSE_ROLLOVER;
			}
			
			// We need to create our tabBar above BEFORE calling creteChildren
			// because otherwise it would get created in the super class.
			// Once we create it then the super class will skip it. It still hasn't
			// been added as a child however (this gets done below).
	        super.createChildren();
	        
	     	
	        if(!holder) {
	        	// Why not just use HBox? Because in the future we might want
	        	// to use a VBox for vertical tabs. This lets a subclass simply
	        	// change the direction.
	        	holder = new Box();
	        	holder.direction = BoxDirection.HORIZONTAL;
	        	
	        	holder.setStyle("horizontalGap", 0);
	        	holder.setStyle("borderStyle", "none");
	        	holder.setStyle("paddingTop", 0);
				holder.setStyle("paddingBottom", 0);
	        	
	        	holder.horizontalScrollPolicy = "off";
	        	
	        	rawChildren.addChild(holder);
	        }

	         if(!canvas) {
	       		canvas = new ButtonScrollingCanvas();
	        	canvas.styleName = this;
	        	canvas.setStyle("borderStyle", "none");
	        	canvas.setStyle("backgroundAlpha", 0);
	        	canvas.setStyle("paddingTop", 0);
				canvas.setStyle("paddingBottom", 0);
	        	
	        	canvas.startScrollingEvent = _startScrollingEvent;
	        	canvas.stopScrollingEvent = _stopScrollingEvent;
	        	canvas.scrollSpeed = _scrollSpeed;
	        	
	        	// So we can see our child heirarchy: 
	        	// holder (Box) -> canvas (ButtonScrollingCanvas) -> tabBar (SuperTabBar)
	        	canvas.addChild(tabBar);
	        	holder.addChild(canvas);
	        }
	        
	        // Now we add a spacer that will take up the rest of the box width
	        spacer = new Spacer();
	        spacer.percentWidth = 100;
	        holder.addChild(spacer);
	        
	        // We create the menu once. This doesn't get shown until we click
	        // the PopUpMenuButton. But it can get created here so we don't have
	        // to create it every time.
	        if(!menu) {
	        	menu = new ScrollableArrowMenu();
	        	// If we wanted to change the scroll policy for the scrolling menu we
	        	// could modify the following two lines. For example, turning 
	        	// verticalScrollPolicy to OFF will remove the side scrollbars and leave
	        	// just the arrow buttons on top and bottom.
	        	menu.verticalScrollPolicy = ScrollPolicy.AUTO;
	        	menu.arrowScrollPolicy = ScrollPolicy.AUTO;
	        	
	        	menu.addEventListener(MenuEvent.ITEM_CLICK, changeTabs);
	        }
	        
	        if(!popupButton) {
	        	popupButton = new PopUpButton();
	        	popupButton.popUp = menu;
	        	popupButton.width = 18;
	        	
	        	popupButton.styleName = getStyle("popupButtonStyleName");
	        	
	        	// So now holder has 3 children: canvas, spacer, and popupButton
	        	holder.addChild(popupButton);
	        }
	        
	        
	        tabBar.addEventListener(ChildExistenceChangedEvent.CHILD_ADD, tabsChanged);
	        tabBar.addEventListener(ChildExistenceChangedEvent.CHILD_REMOVE, tabsChanged);
	        
	        // This is a custom event that gets fired from SuperTabBar if the tabs are
	        // dragged and reordered.
	        tabBar.addEventListener(SuperTabBar.TABS_REORDERED, tabsReordered);
	
			this.addEventListener(IndexChangedEvent.CHANGE,tabChangedEvent); 
	
	        invalidateSize();
	    }
	    
	    public function get closePolicy():String {
	    	return (tabBar as SuperTabBar).closePolicy;
	    }
	    
	    public function set closePolicy(value:String):void {
	    	var old:String = (tabBar as SuperTabBar).closePolicy;
	    	(tabBar as SuperTabBar).closePolicy = value;
	    	if(old != value) {
	    		invalidateDisplayList();
	    	}
	    }
	    
	    
	    private function tabsReordered(event:TabReorderEvent):void {
	    	// The relatedObject of our custom event is the SuperTabNavigator component
	    	// where the tab originated. This is so we can properly move tabs from
	    	// one navigator to another.
	    	var sourceNav:SuperTabNavigator = event.relatedObject as SuperTabNavigator;
	    	
	    	// The oldIndex property of the event specifies the index of the tab
	    	// in the original navigator. Note that the tab might not be a child of
	    	// this current tab navigator that we're in (ie sourceNav might not == this).
	    	var child:DisplayObject = sourceNav.getChildAt(event.oldIndex);
	    	sourceNav.removeChildAt(event.oldIndex);
	    	
	    	// If we are moving a tab from this same navigator then we might need
	    	// to adjust the index that we're moving to
	    	if(this == sourceNav && event.oldIndex < event.newIndex) {
	    		event.newIndex--;
	    	}
	    	
	    	// We add the tab to ourself at the new index position
	    	this.addChildAt(child, event.newIndex);
	    	
	    	// Calling validateNow before calling selectedIndex makes sure we 
	    	// don't get a little display bug that tends to creep up
	    	this.validateNow();
	    	
	    	// If we just dropped a tab then we want to select it,
	    	// that just seems like the intuitive thing to do
	    	this.selectedIndex = event.newIndex;
	    	
	    	// We might be dragging from a different tab navigator. if so, we need
	    	// to update the drop-down menu to relfect the new tabs
	    	if(sourceNav != this) {
	    		sourceNav.reorderTabList();
	    		sourceNav.invalidateDisplayList();
	    	}
	    	
	    	// Now update the drop-down menu to show the newly ordered tabs
	    	reorderTabList();
	    	this.invalidateDisplayList();
	    }
	    
	    /**
	    * tabBarHeight is the same as the same funtion in TabNavigator, but the
	    * one in TabNavigator was private, so we had to reproduce it here.
	    */
	    protected function get tabBarHeight():Number
	    {
	        var tabHeight:Number = getStyle("tabHeight");
	
	        if (isNaN(tabHeight))
	            tabHeight = tabBar.getExplicitOrMeasuredHeight();
	
	        return tabHeight - 1;
	    }
	    
	    override protected function updateDisplayList(unscaledWidth:Number,
                                                  unscaledHeight:Number):void
	    {
	        super.updateDisplayList(unscaledWidth, unscaledHeight);
	        
	        // Are we supposed to be showing the PopUpMenuButton?
	        if(_popUpButtonPolicy == SuperTabNavigator.POPUPPOLICY_AUTO) {
	        	popupButton.includeInLayout = popupButton.visible = this.numChildren > 1;
	        }
	        else if(_popUpButtonPolicy == SuperTabNavigator.POPUPPOLICY_ON) {
	        	popupButton.includeInLayout = popupButton.visible = true;
	        }
	        else if(_popUpButtonPolicy == SuperTabNavigator.POPUPPOLICY_OFF) {
	        	popupButton.includeInLayout = popupButton.visible = false;
	        }
	        
	        spacer.includeInLayout = popupButton.includeInLayout;
	        
	        var vm:EdgeMetrics = viewMetrics;
	        var w:Number = unscaledWidth - vm.left - vm.right;
	
	        var th:Number = tabBarHeight + 1;
	        var pw:Number = tabBar.getExplicitOrMeasuredWidth();
	        
	        // tabBarSpace is used tot ry to figure out how much space we 
	        // need for the tabs, to figure out if we need to scroll them
	        var tabBarSpace:Number = w;
	        if(popupButton.includeInLayout) {
				tabBarSpace -= popupButton.width;
			}
	        
	        // The following code tries to determin if we need to force the tabs to be
	        // smaller than their natural width. If we can squeeze them all in and keep
	        // them larger than whatever minTabWidth is set to, then we should squeeze them.
	        // If we can't squeeze them in then we need to scroll them.
	        if(pw > tabBarSpace) {
	       		var numTabs:Number = tabBar.numChildren;
	       		var tabSizeNeeded:Number = Math.max((tabBarSpace - this.getStyle("horizontalGap")*(numTabs - 1))/numTabs, _minTabWidth);
	       		
				if(forcedTabWidth != tabSizeNeeded) {
					if(originalTabWidthStyle == -1) {
						originalTabWidthStyle = this.getStyle("tabWidth");
					}
					
       				forcedTabWidth = tabSizeNeeded;
       				this.setStyle("tabWidth", forcedTabWidth);
					callLater(invalidateDisplayList);
					return;
	   			}
	       	}
	       	else {
	       		if(forcedTabWidth == -1 && this.getStyle("tabWidth") != originalTabWidthStyle && originalTabWidthStyle != -1) {
	       			
	       			if(this.getStyle("tabWidth") != undefined) {
						if(isNaN(originalTabWidthStyle)) {
							this.clearStyle("tabWidth");
							tabBar.validateNow();
						}
						else {
							this.setStyle("tabWidth", originalTabWidthStyle);
		    			}
		    			
		    			callLater(invalidateDisplayList);
	       			}
	       		}
	       		forcedTabWidth = -1;
	       	}
	        
	        if(forcedTabWidth != -1) {
				pw = (forcedTabWidth * tabBar.numChildren) + (this.getStyle("horizontalGap") * (tabBar.numChildren-1));
			}
	        
	        
	        
	        holder.move(0, 1);
	        holder.setActualSize(unscaledWidth, th);
	       
           
           	var canvasWidth:Number = unscaledWidth;
			
			if(popupButton.includeInLayout) {
				canvasWidth -= popupButton.width;
			}
			
			canvas.width = canvasWidth;
			canvas.height = th;
			canvas.explicitButtonHeight = th - 1;
			
			if(pw <= canvasWidth) {
				canvas.horizontalScrollPosition = 0;
			} 
			
			tabBar.setActualSize(pw, th);
			tabBar.move(0, 0);
			
			/* we only care about horizontalAlign if we're not taking up too
			   much space already */
			if(pw < canvasWidth) {
				
				switch (getStyle("horizontalAlign"))
		        {
		        case "left":
		            tabBar.move(0, tabBar.y);
		            break;
		        case "right":
		            tabBar.move(unscaledWidth - tabBar.width, tabBar.y);
		            break;
		        case "center":
		            tabBar.move((unscaledWidth - tabBar.width) / 2, tabBar.y);
		        }
			}        
	    }
	     
	    /**
	    * This is the event handler for when the user clicks the drop-down menu.
	    * If we're selecting a new tab then we'll dispatch an IndexChangedEvent, which
	    * will trigger the call to ensureTabIsVisible(). If we are not switching tabs
	    * though, then the event wouldn't get dispatched, so we have to call ensureTabIsVisible
	    */
		private function changeTabs(event:MenuEvent):void {
	    	if(this.selectedIndex == event.index) {
	    		ensureTabIsVisible();
	    	}
	    	
	    	this.selectedIndex = event.index;
	    }
	    
	    /**
	    * The tabs can be changed any number of ways (via drop-down menu, via AS, etc)
	    * so this listener function will make sure that the tab that gets selected is 
	    * visible.
	    */
	    private function tabChangedEvent(event:IndexChangedEvent):void {
	    	callLater(ensureTabIsVisible);
	    }
	    
	    /**
	    * Check to make sure that the currently selected tab is viaible. This means
	    * that we might have to scroll the canvas component so the tab comes into view
	    */
	    private function ensureTabIsVisible():void {
	    	var tab:DisplayObject = this.tabBar.getChildAt(this.selectedIndex);
	    	
	    	var newHorizontalPosition:Number;
	    	
	    	if(tab.x + tab.width > this.canvas.horizontalScrollPosition + this.canvas.width) {
	    		newHorizontalPosition = tab.x  - canvas.width + tab.width + canvas.getStyle("buttonWidth");	
	    	}
	    	else if(this.canvas.horizontalScrollPosition > tab.x) {
	    		newHorizontalPosition = tab.x - canvas.getStyle("buttonWidth");
	    	}
	    	else {
	    		newHorizontalPosition = canvas.horizontalScrollPosition;
	    	}
	    	
	    	if(newHorizontalPosition) {
	    		// We tween the motion so it looks super sweet
	    		var tween:Tween = new Tween(this, canvas.horizontalScrollPosition, newHorizontalPosition, 500);
	    		
	    		// Alternatively if we didn't want to use the tweening we could just set the
	    		// horizontalScrollPosition right away (this is what I first did)
	    		//canvas.horizontalScrollPosition = newHorizontalPosition;
	    	}
	    }
	    
	    public function onTweenUpdate(val:Object):void {
            canvas.horizontalScrollPosition = val as Number;
        }
        
        public function onTweenEnd(val:Object):void {
           canvas.horizontalScrollPosition = val as Number;
        }

	    /**
	    * Listener that gets caled when a tab is added or removed.
	    */
	    private function tabsChanged(event:ChildExistenceChangedEvent):void {
	    	callLater(reorderTabList);
	    }
	    
	    /**
	    * reorderTabList loops over all the tabs and makes sure that the drop-down
	    * list is correct. This should get called every time tabs are added, removed,
	    * or re-ordered.
	    */
	    public function reorderTabList():void {
	    	var popupMenuDP:ArrayCollection = new ArrayCollection();
			
			for(var i:int=0; i<tabBar.numChildren; i++) {
				var child:DisplayObject = tabBar.getChildAt(i);
				var label:String = "Untitled Tab";
				if(child is Tab && (child as Tab).label != "") {
					label = (child as Tab).label;
				}
				popupMenuDP.addItem(label);
			}
				
			menu.dataProvider = popupMenuDP;	
	    }
	}
}