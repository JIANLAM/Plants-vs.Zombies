package com.example.zoombiesgame.bean;

import org.cocos2d.actions.base.CCAction;
import org.cocos2d.nodes.CCNode;

import com.example.zoombiesgame.base.AttackPlant;
import com.example.zoombiesgame.base.Bullet;
import com.example.zoombiesgame.uilts.CommonUilts;
 

public class SuperPeasePlant extends AttackPlant {

	public SuperPeasePlant( ) {
		super("image/plant/pease/p_2_01.png");
		baseAction();
	}

	@Override
	public Bullet createBullet() {
		if(bullets.size()<2){// ֤��֮ǰû�д����ӵ� 
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

		 CCAction animate = CommonUilts.getAnimate("image/plant/SuperPease/superplant_%02d.png",
				 5, true);
		 this.runAction(animate);
	 
	}

}