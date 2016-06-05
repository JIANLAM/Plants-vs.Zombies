package com.example.zoombiesgame.base;

import org.cocos2d.actions.base.CCAction;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCSequence;

import com.example.zoombiesgame.uilts.CommonUilts;


/*
 * 
 * 自爆型植物
 */
public abstract class ExplodePlant extends Plant {
  
	
	  public int attack=60;
	public ExplodePlant(String filepath) {
		super(filepath);
	}

/**
 * 自爆
 */
    public abstract void  ExplodeMyself();


}
