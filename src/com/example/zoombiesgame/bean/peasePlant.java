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
		if(bullets.size()<1){
			final PeaseBullet bullet=new PeaseBullet();
			bullet.setPosition(CCNode.ccp(this.getPosition().x+20,
					this.getPosition().y+40));
			this.getParent().addChild(bullet);
			
			this.setDieListener(new DieListener() {
				
				@Override
				public void die() {
					bullets.remove(bullet);
				}
			});
			bullets.add(bullet);
			bullet.move();
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
