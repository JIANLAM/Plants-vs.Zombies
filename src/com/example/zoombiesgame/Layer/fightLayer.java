package com.example.zoombiesgame.Layer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.cocos2d.actions.base.CCAction;
import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCMoveBy;
import org.cocos2d.actions.interval.CCMoveTo;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.layers.CCTMXObjectGroup;
import org.cocos2d.layers.CCTMXTiledMap;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;
import org.cocos2d.types.CGSize;

import android.view.MotionEvent;

import com.example.zoombiesgame.bean.ShowPlant;
import com.example.zoombiesgame.bean.showZoobies;
import com.example.zoombiesgame.engine.GameController;
import com.example.zoombiesgame.uilts.CommonUilts;

public class fightLayer extends BaseLayer{
	public static final int TAG_CHOSE = 0;

	private CCTMXTiledMap map;
  
private List<CGPoint> zoombiesPoint;

 

private CCSprite chose;

private CCSprite choose;
	public fightLayer() {
		
		init();
	}

	private void init() {
		
		loadMap();//���ص�ͼ
		
		moveMap();//�ƶ���ͼ
		
		parseMap();//������ͼ
		
		showzoom();//չʾ��ʬ
	}
	

	private void loadMap() {
		
		map = CCTMXTiledMap.
				tiledMap("image/fight/map_day.tmx");
		
		map.setAnchorPoint(0.5f,0.5f);
		CGSize contentSize = map.getContentSize();
		map.setPosition( contentSize.width/2, 
				contentSize.height/2);
		
		this.addChild(map);
		
		
	}
	

	private void showzoom() {
		for(int i=0;i<zoombiesPoint.size();i++){
			CGPoint cgPoint = zoombiesPoint.get(i);
			
		   showZoobies zoombies=new showZoobies();
		   zoombies.setPosition(cgPoint);
		   map.addChild(zoombies);
		}
	}

	private void parseMap() {
		
		zoombiesPoint = CommonUilts.getObjectPoint(map, "zombies");
	}

	private void moveMap() {
		int x=(int) (winSize.width-map.getContentSize().width);
		CCMoveBy move=CCMoveBy.action(2, ccp(x, 0));
	 
		CCSequence sequence=CCSequence.actions(CCDelayTime.action(4), 
				move,CCDelayTime.action(2),CCCallFunc.action(this, "container"));
		
		map.runAction(sequence);
	}

	
	//����
	public void container(){
		choose = CCSprite.sprite("image/fight/chose/fight_choose.png");
		choose.setAnchorPoint(0,0);
		chose = CCSprite.sprite("image/fight/chose/fight_chose.png");
		
		chose.setAnchorPoint(0,1);
		chose.setPosition(0, winSize.height);
		this.addChild(chose,0,TAG_CHOSE);
		this.addChild(choose);
		
		//����ֲ��
		loadShowPlant();
		
		//���ذ�ť
		
		start =  CCSprite.sprite("image/fight/chose/fight_start.png");
		
		start.setPosition(choose.getContentSize().width/2,30 );
		choose.addChild(start);
		
	}
	
	//���ֲ��ļ���
	private List<ShowPlant> allplant;
	private void loadShowPlant() {
		
		allplant=new ArrayList<ShowPlant>();
		for(int i=1;i<=9;i++){
			
			ShowPlant showplant=new ShowPlant(i); 
			
		 
			CCSprite plant = showplant.getPlant();
			
			//����ֲ��
			CCSprite darkplant = showplant.getDarkplant();
			
			darkplant.setPosition(16+(i-1)%4*54,
					175-(i-1)/4*59);
			choose.addChild(darkplant);
		  plant.setPosition(16+(i-1)%4*54,
					175-(i-1)/4*59);
			choose.addChild(plant);
			
			allplant.add(showplant);
		}
		setIsTouchEnabled(true);
	}

	boolean isdel;
	 boolean lock;
	//��ѡ��ֲ��
	private List<ShowPlant> selectedPlant=new CopyOnWriteArrayList<ShowPlant>();

	private CCSprite start;

	private CCSprite mprologue;
public boolean ccTouchesBegan(MotionEvent event) {
	   
 
	   //ת����cocos2d������
	   CGPoint point = this.convertTouchToNodeSpace(event);
	   
	   
	   //��Ϸ��ʼ�� ת�Ƶ���Ϸ��������
	   if(GameController.isStart){
		   
		   GameController.getInstance().handleTouch(point );
		     return super.ccTouchesBegan(event);
	   }
	   //���
	   CGRect chooseBox = choose.getBoundingBox();
	   
	   //С��
	   CGRect choseBox = chose.getBoundingBox();
	   
	  
	   if(CGRect.containsPoint(choseBox, point)){ //ֲ��ķ�ѡ
		   
		   isdel=false;
		   for(ShowPlant plant:selectedPlant){
			  CGRect selectedBox= plant.getPlant().getBoundingBox();
			   
	 
			    if(CGRect.containsPoint(selectedBox, point)){
			    	 
			    	CCMoveTo move=CCMoveTo.action(0.5f,
			    			plant.getDarkplant().getPosition());
			    	
			    	plant.getPlant().runAction(move);
			    	selectedPlant.remove(plant);
			    	
			    	isdel=true;
			    	continue;
			    }
			    if(isdel){
			    	
			    	  CCMoveBy moveBy=CCMoveBy.action(0.5f, ccp(-53, 0));
			    	  
			    	  plant.getPlant().runAction(moveBy);
			    }
			    
		   }
		    
	   }  else if(CGRect.containsPoint(chooseBox, point)){
		   
		   //׼����ʼ��Ϸ
		   if(CGRect.containsPoint(start.getBoundingBox(), point)){
			    
			   readyToGame();
		   }  else if(selectedPlant.size()<5 && !lock){   //ֲ���ѡ��
		    for(ShowPlant plant : allplant){
		    	  
		    	    CGRect plantboundingBox = plant.getPlant().getBoundingBox();
		    	    if((CGRect.containsPoint(plantboundingBox, 
		    	    		point))){
		    	    	
		    	    	lock=true;
		    	    	CCMoveTo moveTO=CCMoveTo.action(0.5f, 
		    	    			ccp(70+selectedPlant.size()*53,255));
		    	    	
		    	    	CCSequence sequence=CCSequence.actions(moveTO,
		    	    			CCCallFunc.action(this, "unlock"));
		    	    	plant.getPlant().runAction(sequence);
		    	    	selectedPlant.add(plant);
//		    	    	int size = selectedPlant.size();
//		    	    	System.out.println(size);
		    	    }
		    }
		   }
	 
	   }
	return super.ccTouchesBegan(event);
}

 

//׼����ʼ��Ϸ
private void readyToGame() {
	//��СС�����Ĵ�С
	chose.setScale(0.65f);
	//�������ֲ�ﵽС����
	
	for(ShowPlant plant:selectedPlant){
		
		 //��Сֲ�￨Ƭ��С
		plant.getPlant().setScale(0.65f);
		plant.getPlant().setPosition(
				plant.getPlant().getPosition().x * 0.65f,
				plant.getPlant().getPosition().y

				+ (CCDirector.sharedDirector().getWinSize().height - plant

				.getPlant().getPosition().y)
				* 0.35f);// ��������
		this.addChild(plant.getPlant());
	 	}
	
	//��������
	choose.removeSelf();
	
	//�ƶ���ͼ
	int x=(int) (map.getContentSize().width-winSize.width);
	CCMoveBy move=CCMoveBy.action(1, ccp(x, 0));
	CCSequence sequence=CCSequence.actions(move, CCCallFunc.action(this,
			"prologue"));
	map.runAction(sequence);
	
}

//��������֡
public void prologue(){
	  mprologue = CCSprite.sprite("image/fight/startready_01.png");
	  mprologue.setPosition(winSize.width/2,winSize.height/2);
	  this.addChild(mprologue);
	  
	  String format="image/fight/startready_%02d.png";
	CCAction animate = CommonUilts.getAnimate(format, 3, false);
	CCSequence sequence=CCSequence.actions((CCAnimate)animate, CCCallFunc.action(this,
			"startGame"));
	mprologue.runAction(sequence);
}

//��ʼ��Ϸ
public void startGame(){
	mprologue.removeSelf();
	
	//ת����Ϸ��������
	GameController controller=GameController.getInstance();
	controller.startGame(map,selectedPlant);
}
public void unlock() {
  lock=false;
}
}
