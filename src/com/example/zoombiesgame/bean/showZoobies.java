package com.example.zoombiesgame.bean;

import org.cocos2d.actions.base.CCAction;
import org.cocos2d.nodes.CCSprite;

import com.example.zoombiesgame.uilts.CommonUilts;

public class showZoobies extends CCSprite {

	
	public showZoobies() {
		super("image/zombies/zombies_1/shake/z_1_01.png");
		setScale(0.5f);
	    setAnchorPoint(0.5f,0);
	    
	  CCAction animate = CommonUilts.getAnimate("image/zombies/zombies_1/shake/z_1_%02d.png",
	    		2, true);
	 this.runAction(animate);
	}
}
