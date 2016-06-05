package com.example.zoombiesgame.Layer;

import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;

public class ZombiesWin extends BaseLayer {

	
	  public ZombiesWin(){
		  CCSprite endGame=CCSprite.sprite("image/fight/ZombiesWon.jpg");
   		  endGame.setAnchorPoint(0,0);
   
   		endGame.setPosition(winSize.width/2,winSize.height/2);
   		endGame.setScale(0.23);
   	      this.addChild(endGame);
	  }
}
