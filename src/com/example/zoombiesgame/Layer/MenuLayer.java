package com.example.zoombiesgame.Layer;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.menus.CCMenu;
import org.cocos2d.menus.CCMenuItem;
import org.cocos2d.menus.CCMenuItemSprite;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.nodes.CCSprite;

import com.example.zoombiesgame.uilts.CommonUilts;


public class MenuLayer extends BaseLayer {

	 private CCMenu Menu;

	public MenuLayer() {
		init();
	}

	private void init() {
		CCSprite menu=CCSprite.sprite("image/menu/main_menu_bg.jpg");
		menu.setAnchorPoint(0,0);
		this.addChild(menu);
		
		
		CCSprite normalSprite=CCSprite.sprite("image/menu/start_adventure_default.png");
		CCSprite selectedSprite=CCSprite.sprite("image/menu/start_adventure_press.png");
		
		CCMenuItem items=CCMenuItemSprite.item(normalSprite, selectedSprite,
				this, "click");
		Menu = CCMenu.menu(items);
		Menu.setScale(0.5f);
		Menu.setPosition(winSize.width/2-25, winSize.height/2-110);
		Menu.setRotation(4.5f);
		this.addChild(Menu);
	}
	/*
	 * Object object必须添加才能知道点击了哪个item
	 */
	 
	 public void click(Object object){
		   CommonUilts.changLayer(new fightLayer());
		   
		   System.out.println("l am ok");
	 }
}
