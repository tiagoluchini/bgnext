////////////////////////////////////////////////////////////////////////////////
//
//  Code modified by Doug McCune. 
//  http://dougmccune.com/blog
//
//  Much of this code is Adobe's code. I guess you have to follow whatever their 
//  licensing rules are. As for any addition or modification that I made, you can use 
//  my code for whatever you want. Just don't go and try to sell it as your own. 
//  If you use it to make something better and want to sell that then go for it.
//
//  Let's all play nice and be happy.
//
////////////////////////////////////////////////////////////////////////////////

////////////////////////////////////////////////////////////////////////////////
//
//  Copyright (C) 2003-2006 Adobe Macromedia Software LLC and its licensors.
//  All Rights Reserved. The following is Source Code and is subject to all
//  restrictions on such code as contained in the End User License Agreement
//  accompanying this product.
//
////////////////////////////////////////////////////////////////////////////////

package com.dougmccune.controls
{

import flash.events.Event;
import flash.events.MouseEvent;
import mx.collections.ICollectionView;
import mx.controls.PopUpButton;
import mx.controls.listClasses.IListItemRenderer;
import mx.controls.menuClasses.IMenuDataDescriptor;
import mx.controls.treeClasses.DefaultDataDescriptor;
import mx.core.IUIComponent;
import mx.core.ScrollPolicy;
import mx.core.mx_internal;
import mx.events.FlexEvent;
import mx.events.ListEvent;
import mx.events.MenuEvent;
import mx.managers.PopUpManager;

use namespace mx_internal;

//--------------------------------------
//  Events
//-------------------------------------- 

/**
 *  Dispatched when a user selects an item from the pop-up menu.
 *
 *  @eventType mx.events.MenuEvent.ITEM_CLICK
 */
[Event(name="itemClick", type="mx.events.MenuEvent")]

//--------------------------------------
//  Excluded APIs
//--------------------------------------

[Exclude(name="toggle", kind="property")]
[Exclude(name="selectedDisabledIcon", kind="style")]
[Exclude(name="selectedDisabledSkin", kind="style")]
[Exclude(name="selectedDownIcon", kind="style")]
[Exclude(name="selectedDownSkin", kind="style")]
[Exclude(name="selectedOverIcon", kind="style")]
[Exclude(name="selectedOverSkin", kind="style")]
[Exclude(name="selectedUpIcon", kind="style")]
[Exclude(name="selectedUpSkin", kind="style")]

//--------------------------------------
//  Other metadata
//-------------------------------------- 

//[IconFile("PopUpMenuButton.png")]

[RequiresDataBinding(true)]

/**
 * The ScrollablePopUpMenuButton is a duplicate of the PopUpMenuButton class, with slight modification.
 * Because of the difficulty of extending the PopUpButton class, this class copied the entire source
 * code of the original PopUpButton and simply changed a few things. 
 * 
 * A smaller extension of PopUpButton was not possible because it would not have given access to many of
 * the private variables and methods that would have been needed. So it's a little silly, but copying the
 * entire class was the only readily-available solution. 
 * 
 * @see mx.controls.PopUpMenuButton
 * @see com.dougmccune.controls.ScrollableMenu
 */
public class ScrollablePopUpMenuButton extends PopUpButton
{
   // include "../core/Version.as";

    //--------------------------------------------------------------------------
    //
    //  Constructor
    //
    //--------------------------------------------------------------------------

    /**
     *  Constructor.
     */
    public function ScrollablePopUpMenuButton()
    {
        super();
       
    }
    
    
    /**
    * Adding support for verticalScrollPolicy
    * Added by Doug McCune
    */
    private var _verticalScrollPolicy:String;
    
    public function set verticalScrollPolicy(value:String):void {
		var newPolicy:String = value.toLowerCase();

	    if (_verticalScrollPolicy != newPolicy)
	    {
	    	_verticalScrollPolicy = newPolicy;
	    }
	    
	    if(this.popUpMenu) {
	    	popUpMenu.verticalScrollPolicy = this.verticalScrollPolicy;
	    }
        //invalidateDisplayList();
	}
		
		
	public function get verticalScrollPolicy():String {
		return this._verticalScrollPolicy;
	}
	
	private var _arrowScrollPolicy:String;
    
    public function set arrowScrollPolicy(value:String):void {
		var newPolicy:String = value.toLowerCase();

	    if (_arrowScrollPolicy != newPolicy)
	    {
	    	_arrowScrollPolicy = newPolicy;
	    }
	    
	    if(this.popUpMenu) {
	    	popUpMenu.arrowScrollPolicy = this.arrowScrollPolicy;
	    }
      //  invalidateDisplayList();
	}
		
		
	public function get arrowScrollPolicy():String {
		return this._arrowScrollPolicy;
	}
    
    

    //--------------------------------------------------------------------------
    //
    //  Variables
    //
    //--------------------------------------------------------------------------

    /**
     *  @private
     */
    private var dataProviderChanged:Boolean = false;
    
    /**
     *  @private
     */
    private var labelSet:Boolean = false;
    
    /**
     *  @private
     */
    private var popUpMenu:ScrollableArrowMenu = null;
    
    /**
     *  @private
     */
    private var selectedIndex:int = -1;
    
    /**
     *  @private
     */ 
    private var itemRenderer:IListItemRenderer = null;

    //--------------------------------------------------------------------------
    //
    //  Overridden properties
    //
    //--------------------------------------------------------------------------

    /**
     *  A reference to the pop-up Menu object.
     *
     *  <p>This property is read-only, and setting it has no effect.
     *  Set the <code>dataProvider</code> property, instead.
     *  (The write-only indicator appears in the syntax summary because the
     *  property in the superclass is read-write and this class overrides
     *  the setter with an empty implementation.)</p>
     */
    override public function set popUp(value:IUIComponent):void
    {
    }

    //--------------------------------------------------------------------------
    //
    //  Properties
    //
    //--------------------------------------------------------------------------

    //--------------------------------------------------------------------------
    // dataDescriptor
    //--------------------------------------------------------------------------

    /**
     *  @private
     *  Storage for the dataDescriptor property.
     */
    private var _dataDescriptor:IMenuDataDescriptor =
                                        new DefaultDataDescriptor();
    
    /**
     *  The data descriptor accesses and manipulates data in the data provider.
     *  <p>When you specify this property as an attribute in MXML, you must
     *  use a reference to the data descriptor, not the string name of the
     *  descriptor. Use the following format for the property:</p>
     *
     *  <pre>&lt;mx:PopUpMenuButton id="menubar" dataDescriptor="{new MyCustomDataDescriptor()}"/&gt;</pre>
     *
     *  <p>Alternatively, you can specify the property in MXML as a nested
     *  subtag, as the following example shows:</p>
     *
     *  <pre>&lt;mx:PopUpMenuButton&gt;
     *  &lt;mx:dataDescriptor&gt;
     *     &lt;myCustomDataDescriptor&gt;
     *  &lt;/mx:dataDescriptor&gt;
     *  ...</pre>
     *
     *  <p>The default value is an internal instance of the
     *  DefaultDataDescriptor class.</p>
     */
    public function get dataDescriptor():IMenuDataDescriptor
    {
        return IMenuDataDescriptor(_dataDescriptor);
    }

    /**
     *  @private
     */
    public function set dataDescriptor(value:IMenuDataDescriptor):void
    {
        _dataDescriptor = value;
    }

    //--------------------------------------------------------------------------
    // dataProvider
    //--------------------------------------------------------------------------

    /**
     *  @private
     *  Storage for dataProvider property.
     */
    private var _dataProvider:Object = null;

    [Bindable("collectionChange")]
    [Inspectable(category="Data", defaultValue="null")]

    /**
     *  DataProvider for popUpMenu.
     *
     *  @default null
     */
    public function get dataProvider():Object
    {
        if (popUpMenu)
            return ScrollableArrowMenu(popUpMenu).dataProvider;
        return null;
    }

    /**
     *  @private
     */
    public function set dataProvider(value:Object):void
    {
        _dataProvider = value;
        dataProviderChanged = true;

        // In general we shouldn't create the popUp's until
        // they are actually popped up. However, in this case
        // the initial label, icon and action on the main button's 
        // click are to be borrowed from the popped menu. 
        // Moreover since PopUpMenuButton doesn't expose selectedIndex
        // selectedItem etc., one should be able to access them
        // prior to popping up the menu.        
        getPopUp();
        
        invalidateProperties();     
    }

    //--------------------------------------------------------------------------
    //  label
    //--------------------------------------------------------------------------

    /**
     *  @private
     *  Storage for the label property.
     */
    private var _label:String = "";

    [Inspectable(category="General", defaultValue="")]

    /**
     *  @private
     */
    override public function set label(value:String):void
    {
        // labelSet is different from labelChanged as it is never unset.
        labelSet = true;
        _label = value;
        super.label = _label;
    }

    //--------------------------------------------------------------------------
    //  labelField
    //--------------------------------------------------------------------------

    /**
     *  @private
     *  Storage for the labelField property.
     */
    private var _labelField:String = "label";

    [Bindable("labelFieldChanged")]
    [Inspectable(category="Data", defaultValue="label")]

    /**
     *  Name of the field in the <code>dataProvider</code> Array that contains the text to
     *  show for each menu item.
     *  The <code>labelFunction</code> property, if set, overrides this property.
     *  If the data provider is an Array of Strings, Flex uses each String
     *  value as the label.
     *  If the data provider is an E4X XML object, you must set this property
     *  explicitly; for example, use &#064;label to specify the <code>label</code> attribute.
     *
     *  @default "label"
     */
    public function get labelField():String
    {
        return _labelField;
    }

    /**
     *  @private
     */
    public function set labelField(value:String):void
    {
        if (_labelField != value)
        {
            _labelField = value;
            
            if (popUpMenu)
                popUpMenu.labelField = _labelField;
            
            dispatchEvent(new Event("labelFieldChanged"));
        }
    }

    //--------------------------------------------------------------------------
    //  labelFunction
    //--------------------------------------------------------------------------

    /**
     *  @private
     *  Storage for the labelFunction property.
     */
    private var _labelFunction:Function;

    [Inspectable(category="Data")]

    /**
     *  A function that determines the text to display for each menu item.
     *  If you omit this property, Flex uses the contents of the field or attribute
     *  determined by the <code>labelField</code> property.
     *  If you specify this property, Flex ignores any <code>labelField</code>
     *  property value.
     *
     *  <p>If you specify this property, the label function must find the
     *  appropriate field or fields and return a displayable string.
     *  The <code>labelFunction</code> property is good for handling formatting
     *  and localization.</p>
     *
     *  <p>The label function must take a single argument which is the item
     *  in the dataProvider and return a String.</p>
     * 
     *  <blockquote>
     *  <code>labelFunction(item:Object):String</code>
     *  </blockquote>
     *
     *  @default null
     */
    public function get labelFunction():Function
    {
        return _labelFunction;
    }

    /**
     *  @private
     */
    public function set labelFunction(value:Function):void
    {
        if (_labelFunction != value)
        {
            _labelFunction = value;
            
            if (popUpMenu)
                popUpMenu.labelFunction = _labelFunction;
        }
    }

    //--------------------------------------------------------------------------
    //  showRoot
    //--------------------------------------------------------------------------

    /**
     *  @private
     *  Storage for the showRoot property.
     */
    mx_internal var _showRoot:Boolean = true;

    /**
     *  @private
     */
    private var _showRootChanged:Boolean = false;

    [Inspectable(category="Data", enumeration="true,false", defaultValue="true")]

    /**
     *  Specifies whether to display the top-level node or nodes of the data provider.
     *
     *  If this is set to <code>false</code>, the control displays
     *  only descendants of the first top-level node.
     *  Any other top-level nodes are ignored.
     *  You normally set this property to <code>false</code> for
     *  E4X format XML data providers, where the top-level node is the document
     *  object.
     *
     *  @default true
     */
    public function get showRoot():Boolean
    {
        return _showRoot;
    }

    /**
     *  @private
     */
    public function set showRoot(value:Boolean):void
    {
        if (_showRoot != value)
        {
            _showRoot = value;
            _showRootChanged = true;

            invalidateProperties();
        }
    }

    //--------------------------------------------------------------------------
    //
    //  Overridden methods: UIComopnent
    //
    //--------------------------------------------------------------------------

    /**
     *  @private
     */
    override protected function commitProperties():void
    {
        if (_showRootChanged)
        {
            _showRootChanged = false;
            if (popUpMenu != null)
                popUpMenu.showRoot = _showRoot;
            invalidateDisplayList();
        }

        if (popUpMenu && dataProviderChanged)
        {
            popUpMenu.dataProvider = _dataProvider;
			popUpMenu.validateNow();

            if (dataProvider.length)
            {
                selectedIndex = 0;
                
                var item:* = popUpMenu.dataProvider[selectedIndex];
                
                // Set button label.
                if (labelSet)
                    super.label = _label;
                else
                    super.label = popUpMenu.itemToLabel(item);
                
                // Set button icon,
                setStyle("icon", popUpMenu.itemToIcon(item));
            }
            else
            {
                selectedIndex = -1;
                
                if (labelSet)
                    super.label = _label;
                else
                    super.label = "";
                
                clearStyle("icon");
            }

            dataProviderChanged = false;
        }

        super.commitProperties();
    }


	/**
	 * Added by Doug McCune
	 * Added this override of setting maxHeight to make setting the maxHeight 
	 * also set the maxHeight of the popUpMenu item.
	 */ 
	override public function set maxHeight(value:Number):void {
		if(popUpMenu) {
			popUpMenu.maxHeight = value;
		}
		
		super.maxHeight = value;
	}
    //--------------------------------------------------------------------------
    //
    //  Overridden methods: PopUpButton
    //
    //--------------------------------------------------------------------------

    /**
     *  @private
     */
    override mx_internal function getPopUp():IUIComponent
    {
        super.getPopUp();

        if (!popUpMenu)
        {
        	/* And here are another 2 lines that are different from the original PopUpMenuButton */
            popUpMenu = new ScrollableArrowMenu();
            popUpMenu.maxHeight = this.maxHeight;
            
            popUpMenu.labelField = _labelField;
            popUpMenu.labelFunction = _labelFunction;
            popUpMenu.showRoot = _showRoot;
            popUpMenu.dataDescriptor = _dataDescriptor;
            popUpMenu.dataProvider = _dataProvider;
            popUpMenu.addEventListener(MenuEvent.ITEM_CLICK, menuChangeHandler);
            popUpMenu.addEventListener(FlexEvent.VALUE_COMMIT,
                                       menuValueCommitHandler);
            super.popUp = popUpMenu;
            // Add PopUp to PopUpManager here so that
            // commitProperties of Menu gets called even
            // before the PopUp is opened. This is 
            // necessary to get the initial label and dp.
            PopUpManager.addPopUp(super.popUp, this, false);
            super.popUp.owner = this;
        }
        else {
        	popUpMenu.invalidateDisplayList();
        }
        
        if(popUpMenu.verticalScrollPolicy != this.verticalScrollPolicy) {
        	popUpMenu.verticalScrollPolicy = this.verticalScrollPolicy;
        }
        
        if(popUpMenu.arrowScrollPolicy != this.arrowScrollPolicy) {
        	popUpMenu.arrowScrollPolicy = this.arrowScrollPolicy;
        }

        return popUpMenu;
    }

    //--------------------------------------------------------------------------
    //
    //  Overridden event handlers: Button
    //
    //--------------------------------------------------------------------------

    /**
     *  @private
     */
    override protected function clickHandler(event:MouseEvent):void
    {
        super.clickHandler(event);

        if (!overArrowButton(event))
            menuClickHandler(event);
    }

    //--------------------------------------------------------------------------
    //
    //  Event handlers
    //
    //--------------------------------------------------------------------------

    /**
     *  @private
     */
    private function menuClickHandler(event:MouseEvent):void
    {
        if (selectedIndex >= 0)
        {
            var menuEvent:MenuEvent = new MenuEvent(MenuEvent.ITEM_CLICK);
            menuEvent.menu = popUpMenu;
            menuEvent.menu.selectedIndex = selectedIndex;
            menuEvent.item =  popUpMenu.dataProvider[selectedIndex];
            menuEvent.itemRenderer = itemRenderer;
            menuEvent.index = selectedIndex;
            menuEvent.label =
                popUpMenu.itemToLabel(popUpMenu.dataProvider[selectedIndex]);
            dispatchEvent(menuEvent);
            
            // Reset selection after the change event is dispatched
            // just like in a menu.
            popUpMenu.selectedIndex = -1;
        }
    }

    /**
     *  @private
     */
    private function menuValueCommitHandler(event:FlexEvent):void
    {
        // Change label/icon if selectedIndex is changed programatically.
        if (popUpMenu.selectedIndex >=0)
        {
            selectedIndex = popUpMenu.selectedIndex;
            if (labelSet)
                super.label = _label;
            else
                super.label = popUpMenu.itemToLabel(popUpMenu.dataProvider[selectedIndex]);
            setStyle("icon", popUpMenu.itemToIcon(popUpMenu.dataProvider[selectedIndex]));
        }
    }

    /**
     *  @private
     */
    private function menuChangeHandler(event:MenuEvent):void
    {
        if (event.index >= 0)
        {
            var menuEvent:MenuEvent = new MenuEvent(MenuEvent.ITEM_CLICK);
            
            menuEvent.label = popUpMenu.itemToLabel(event.item);
            if (labelSet)
                super.label = _label;
            else
                super.label = popUpMenu.itemToLabel(event.item);
            setStyle("icon", popUpMenu.itemToIcon(event.item));
            menuEvent.menu = popUpMenu;
            menuEvent.menu.selectedIndex = menuEvent.index = 
                selectedIndex = event.index;
            menuEvent.item = event.item;
            itemRenderer = menuEvent.itemRenderer = 
                event.itemRenderer;
            dispatchEvent(menuEvent);
        }
    }
}

}
