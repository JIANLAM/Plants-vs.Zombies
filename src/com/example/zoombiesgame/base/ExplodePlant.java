package com.example.zoombiesgame.base;

import org.cocos2d.actions.base.CCAction;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCSequence;

import com.example.zoombiesgame.uilts.CommonUilts;


/*
 * 
 * �Ա���ֲ��
 */
public abstract class ExplodePlant extends Plant {
  
	
	  public int attack=60;
	public ExplodePlant(String filepath) {
		super(filepath);
	}

/**
 * �Ա�
 */
    public abstract void  ExplodeMyself();


}
