package com.example.zoombiesgame.bean;

import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCMoveBy;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.util.CGPointUtil;

import com.example.zoombiesgame.base.Bullet;
import com.example.zoombiesgame.uilts.CommonUilts;

public class PeaseBullet extends Bullet {

	public PeaseBullet( ) {
		super("image/fight/bullet.png");
		setScale(0.65f);
	}

	public void move() {
      CGPoint position = getPosition();
      CGPoint targetPosition=CCNode.ccp(CCDirector.sharedDirector().winSize().width
    		  , 0);
      float t = CGPointUtil.distance(position, targetPosition)/speed;
      CCMoveBy moveBy=CCMoveBy.action(t, targetPosition);
      CCSequence sequence=CCSequence.actions(moveBy, 
    		  CCCallFunc.action(this, "destory"));
      this.runAction(sequence);
      
	}

}
