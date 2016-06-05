package com.example.zoombiesgame.bean;

import org.cocos2d.actions.CCScheduler;
import org.cocos2d.actions.base.CCAction;
import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.nodes.CCAnimation;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGSize;
import org.cocos2d.types.util.CGPointUtil;

import com.example.zoombiesgame.Layer.ZombiesWin;
import com.example.zoombiesgame.base.BaseElement;
import com.example.zoombiesgame.base.Plant;
import com.example.zoombiesgame.base.Zombies;
import com.example.zoombiesgame.uilts.CommonUilts;

public class PrimaryZoombies extends Zombies {

	
	public PrimaryZoombies(CGPoint startPoint,CGPoint endPoint) {
		super("image/zombies/zombies_1/walk/z_1_01.png");
		this.startPoint = startPoint;
		this.endPoint = endPoint;
		setPosition(startPoint);
		 move();
	}

	@Override
	public void move() {
		CCAction animate = CommonUilts.getAnimate("image/zombies/zombies_1/walk/z_1_%02d.png",
				7, true);
		 
         this.runAction(animate);
         
         //移动endPoint
         float t=CGPointUtil.distance(getPosition(), endPoint)/speed;
         CCMoveTo moveTo=CCMoveTo.action(t, endPoint);
         CCSequence sequence=CCSequence.actions(moveTo, 
        		 CCCallFunc.action(this, "zombiesWin"));
         this.runAction(sequence);
         
        
         
	}

	//游戏结束
	public void zombiesWin(){
		
	   //判断僵尸是否已经走到房子内
         
         	
     		CommonUilts.changLayer(new ZombiesWin(),1);
     		this.destroy();
       
	}
	
	Plant targePlant;
	public void attack(BaseElement element) {
          if(element instanceof Plant){
        	  
        	  Plant plant=(Plant) element;
        	  if(targePlant==null){
        	   	  targePlant=plant;
            	  stopAllActions();
            	  
            	  //切换到攻击模式
            	  CCAction animate = CommonUilts.getAnimate("image/zombies/zombies_1/attack/z_1_attack_%02d.png",
            			  10, true);
            	  this.runAction(animate);
            	  
            	  //植物的掉血
            	  CCScheduler.sharedScheduler().schedule("reduceLife",
            			  this, 0.5f, false);
        	  }
     
         
          }
	}

	//生命减少
	public void reduceLife(float t){
		
		 
			 //植物被攻击
			targePlant.attacked(attack);
			if(targePlant.getLife()<=0){
			 	targePlant=null;
			 	  CCScheduler.sharedScheduler().unschedule("reduceLife",
	        			  this);
			 	  stopAllActions();
			 	  move();
			    }
		 
	   
		
	}
	public void attacked(int attack) {

		 life-=attack;
		 if(life<=0){
			 
			 //切换到死亡动画模式
			 stopAllActions();
			 CCAction animateOfdrophead = CommonUilts.getAnimate("image/zombies/zombies_1/head/z_1_head_%02d.png", 
					 6, false);
			 CCAction animateOfdie = CommonUilts.getAnimate("image/zombies/zombies_1/die/z_1_die_%02d.png", 
					 6, false);
			CCSequence sequence=CCSequence.actions((CCAnimate)animateOfdrophead,(CCAnimate)animateOfdie
					,CCCallFunc.action(this, "destroy"));
			 this.runAction(sequence);
		 
		 }
	}

	public void baseAction() {

	}

}
