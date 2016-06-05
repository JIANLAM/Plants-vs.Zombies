package com.example.zoombiesgame.bean;

import org.cocos2d.actions.base.CCAction;
import org.cocos2d.nodes.CCNode;

import com.example.zoombiesgame.base.AttackPlant;
import com.example.zoombiesgame.base.Bullet;
import com.example.zoombiesgame.uilts.CommonUilts;
 

public class peasePlant extends AttackPlant {

	public peasePlant( ) {
		super("image/plant/pease/p_2_01.png");
		baseAction();
	}

	@Override
	public Bullet createBullet() {
		if(bullets.size()<1){// 证明之前没有创建子弹 
			final PeaseBullet pease=new PeaseBullet();
			pease.setPosition(CCNode.ccp(this.getPosition().x+20, this.getPosition().y+40));
			this.getParent().addChild(pease);
			pease.setDieListener(new DieListener() {
				
				@Override
				public void die() {
					 bullets.remove(pease);
				}
			});
			bullets.add(pease);
			
			pease.move();
		}
		return null;
	}

	@Override
	public void baseAction() {

		 CCAction animate = CommonUilts.getAnimate("image/plant/pease/p_2_%02d.png",
				 8, true);
		 this.runAction(animate);
	}

}
