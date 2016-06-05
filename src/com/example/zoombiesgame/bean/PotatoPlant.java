package com.example.zoombiesgame.bean;

import org.cocos2d.actions.base.CCAction;
import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCSequence;

import com.example.zoombiesgame.base.ExplodePlant;
import com.example.zoombiesgame.uilts.CommonUilts;

public class PotatoPlant extends ExplodePlant {
	public PotatoPlant( ) {
		super("image/plant/potato/potato_01.png");
		baseAction();
	}
 
	@Override
	public void baseAction() {
		//ÉÁË¸ ×¼±¸±¬Õ¨
      CCAction animate = CommonUilts.getAnimate("image/plant/potato/potato_%02d.png", 3, false);
      CCDelayTime delayTime=CCDelayTime.action(2f);
       CCSequence sequence=CCSequence.actions(delayTime, (CCAnimate)animate);
      this.runAction(sequence);
	}

	@Override
	public void ExplodeMyself() {
		
		CCAction animate = CommonUilts.getAnimate("image/fight/explode/explode_%02d.png", 
				2, false);
		CCDelayTime delayTime=CCDelayTime.action(1f);
		CCSequence sequence =CCSequence.actions((CCAnimate)animate, CCCallFunc.action
				(this, "isdie"));
		this.runAction(sequence);
		
	
	 	 
	}
       public void isdie(){
			this.attacked(attack);
	  }
 
}
