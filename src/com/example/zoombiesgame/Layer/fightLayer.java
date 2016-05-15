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
		
		loadMap();//加载地图
		
		moveMap();//移动地图
		
		parseMap();//解析地图
		
		showzoom();//展示僵尸
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

	
	//容器
	public void container(){
		choose = CCSprite.sprite("image/fight/chose/fight_choose.png");
		choose.setAnchorPoint(0,0);
		chose = CCSprite.sprite("image/fight/chose/fight_chose.png");
		
		chose.setAnchorPoint(0,1);
		chose.setPosition(0, winSize.height);
		this.addChild(chose,0,TAG_CHOSE);
		this.addChild(choose);
		
		//加载植物
		loadShowPlant();
		
		//加载按钮
		
		start =  CCSprite.sprite("image/fight/chose/fight_start.png");
		
		start.setPosition(choose.getContentSize().width/2,30 );
		choose.addChild(start);
		
	}
	
	//添加植物的集合
	private List<ShowPlant> allplant;
	private void loadShowPlant() {
		
		allplant=new ArrayList<ShowPlant>();
		for(int i=1;i<=9;i++){
			
			ShowPlant showplant=new ShowPlant(i); 
			
		 
			CCSprite plant = showplant.getPlant();
			
			//背景植物
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
	//已选择植物
	private List<ShowPlant> selectedPlant=new CopyOnWriteArrayList<ShowPlant>();

	private CCSprite start;

	private CCSprite mprologue;
public boolean ccTouchesBegan(MotionEvent event) {
	   
 
	   //转换成cocos2d的坐标
	   CGPoint point = this.convertTouchToNodeSpace(event);
	   
	   
	   //游戏开始后 转移到游戏控制器中
	   if(GameController.isStart){
		   
		   GameController.getInstance().handleTouch(point );
		     return super.ccTouchesBegan(event);
	   }
	   //大框
	   CGRect chooseBox = choose.getBoundingBox();
	   
	   //小框
	   CGRect choseBox = chose.getBoundingBox();
	   
	  
	   if(CGRect.containsPoint(choseBox, point)){ //植物的反选
		   
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
		   
		   //准备开始游戏
		   if(CGRect.containsPoint(start.getBoundingBox(), point)){
			    
			   readyToGame();
		   }  else if(selectedPlant.size()<5 && !lock){   //植物的选择
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

 

//准备开始游戏
private void readyToGame() {
	//缩小小容器的大小
	chose.setScale(0.65f);
	//重新添加植物到小容器
	
	for(ShowPlant plant:selectedPlant){
		
		 //缩小植物卡片大小
		plant.getPlant().setScale(0.65f);
		plant.getPlant().setPosition(
				plant.getPlant().getPosition().x * 0.65f,
				plant.getPlant().getPosition().y

				+ (CCDirector.sharedDirector().getWinSize().height - plant

				.getPlant().getPosition().y)
				* 0.35f);// 设置坐标
		this.addChild(plant.getPlant());
	 	}
	
	//回收容器
	choose.removeSelf();
	
	//移动地图
	int x=(int) (map.getContentSize().width-winSize.width);
	CCMoveBy move=CCMoveBy.action(1, ccp(x, 0));
	CCSequence sequence=CCSequence.actions(move, CCCallFunc.action(this,
			"prologue"));
	map.runAction(sequence);
	
}

//开场序列帧
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

//开始游戏
public void startGame(){
	mprologue.removeSelf();
	
	//转到游戏控制器上
	GameController controller=GameController.getInstance();
	controller.startGame(map,selectedPlant);
}
public void unlock() {
  lock=false;
}
}
