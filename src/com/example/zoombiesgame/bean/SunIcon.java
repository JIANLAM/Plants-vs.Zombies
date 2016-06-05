package com.example.zoombiesgame.bean;

import org.cocos2d.actions.interval.CCMoveBy;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.types.CGPoint;

import com.example.zoombiesgame.base.BaseElement;

public class SunIcon extends BaseElement {

	public SunIcon( ) {
		super("image/product/sun.png");
		baseAction();
	}

	public void baseAction() {
		CGPoint currentPoint = getPosition();
		CGPoint position =CCNode.ccp(currentPoint.x+40
				,currentPoint.y=40);
	 CCMoveBy moveBy=CCMoveBy.action(0.5f, position);
	 
	 this.runAction(moveBy);
	}
  
	 
}
