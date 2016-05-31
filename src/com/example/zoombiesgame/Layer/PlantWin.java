package com.example.zoombiesgame.Layer;

import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;

public class PlantWin extends BaseLayer {

	
	  public PlantWin(){
		  CCSprite endGame=CCSprite.sprite("image/fight/youwin.jpg");
   		  endGame.setAnchorPoint(0,0);
   
   		endGame.setPosition(winSize.width/2,winSize.height/2);
   		 
   	      this.addChild(endGame);
	  }
}
