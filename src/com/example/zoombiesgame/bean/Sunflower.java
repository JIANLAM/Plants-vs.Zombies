package com.example.zoombiesgame.bean;

import java.util.Random;

import org.cocos2d.actions.base.CCAction;
import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.nodes.CCNode;

import com.example.zoombiesgame.base.ProductPlant;
import com.example.zoombiesgame.base.BaseElement.DieListener;
import com.example.zoombiesgame.uilts.CommonUilts;

public class Sunflower extends ProductPlant {

	 

	public Sunflower( ) {
		super("image/plant/sunflower/p_1_01.png");
		baseAction();
		 
	}

	
	 //²úÉúÌ«Ñô
	public void create() {
            final SunShine sun=new SunShine();
            
        	
           sun.setPosition(CCNode.ccp(this.getPosition().x, 
        		   this.getPosition().y+25));
          
           this.getParent().addChild(sun);
           sun.setDieListener(new DieListener() {
				
				public void die() {
					sunShine.remove(sun);
				}
			});
           sunShine.add(sun);
    
           sun.baseAction();
          
		 
	}

	public void baseAction() {
		CCAction animate =  CommonUilts.getAnimate("image/plant/sunflower/p_1_%02d.png", 
        		8, true);
        this.runAction(animate);
	}

}
