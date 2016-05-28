package com.example.zoombiesgame.bean;

import java.util.Random;

import org.cocos2d.actions.CCScheduler;
import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCMoveBy;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.types.CGPoint;

import com.example.zoombiesgame.base.BaseElement;
import com.example.zoombiesgame.engine.GameController;

public class SunShine extends BaseElement {

	public SunShine( ) {
		super("image/product/sun.png");
		setScale(0.4f);
	
	}

	  //阳光飘动
	public void baseAction() {
		
		//产生一个随机数
		Random random=new Random();
   	  int randomNum = random.nextInt(4)+1;
   	     //获取当前位置  
		 CGPoint currentPoint = getPosition(); 
		CGPoint position =CCNode.ccp(currentPoint.x*0.25*randomNum
				,10*randomNum+5);
		
		this.setOpacity(180);
		CCDelayTime delayTime=CCDelayTime.action(2f);
	 CCMoveBy moveBy=CCMoveBy.action(2f, position);
 CCSequence sequence=CCSequence.actions(delayTime, moveBy);
	 this.runAction(sequence);
	}
  
	  
	 
}
