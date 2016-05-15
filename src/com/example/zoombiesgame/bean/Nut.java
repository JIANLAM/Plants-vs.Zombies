package com.example.zoombiesgame.bean;

import org.cocos2d.actions.base.CCAction;

import com.example.zoombiesgame.base.DefancePlant;
import com.example.zoombiesgame.uilts.CommonUilts;

public class Nut extends DefancePlant {

	 

	public Nut() {
		super("image/plant/nut/p_3_01.png");
		baseAction();
	}

	@Override
	public void baseAction() {

		 CCAction animate = CommonUilts.getAnimate("image/plant/nut/p_3_%02d.png",
				 11, true);
		 this.runAction(animate);
	}

}
