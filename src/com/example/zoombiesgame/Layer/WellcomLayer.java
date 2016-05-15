package com.example.zoombiesgame.Layer;

import java.util.ArrayList;

import org.cocos2d.actions.base.CCAction;
import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.instant.CCHide;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCAnimation;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteFrame;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;
import org.cocos2d.types.CGSize;

import com.example.zoombiesgame.uilts.CommonUilts;
 

import android.os.AsyncTask;
import android.os.SystemClock;
import android.view.MotionEvent;

public class WellcomLayer extends BaseLayer {

	

	private CCSprite start;


	public WellcomLayer() {
		
		new AsyncTask<Void, Void, Void>() {
			
			//后台子线程
			protected Void doInBackground(Void... arg0) {
				
				SystemClock.sleep(6000);
				return null;
			}
			//子线程之后开始执行

			@Override
			protected void onPostExecute(Void result) {
			
				super.onPostExecute(result);
				
				start.setVisible(true);
				setIsTouchEnabled(true);
			}
			
			
		}.execute();
		init();
	}

	
public boolean ccTouchesBegan(MotionEvent event) {
	   
	
	CGPoint convertTouchToNodeSpace = this.convertTouchToNodeSpace(event);
	CGRect boundingBox = start.getBoundingBox();
	  if(CGRect.containsPoint(boundingBox, convertTouchToNodeSpace)){ 
		  
		  CommonUilts.changLayer(new MenuLayer());
	  }
	return super.ccTouchesBegan(event);
}
	private void init() {
		CCSprite logo=CCSprite.sprite("image/popcap_logo.png");
		
	   
	    
	    logo.setPosition(winSize.width/2,winSize.height/2);
	    
	    this.addChild(logo);
	    
	    //隐藏
	    CCHide hide=CCHide.action();
	    CCDelayTime delayTime=CCDelayTime.action(1);
	    
	    CCSequence sequence=CCSequence.actions(delayTime, delayTime,
	    		hide,CCCallFunc.action(this, "loadingWellcome")	);
	    logo.runAction(sequence);
	    
	}
	
	
   public void loadingWellcome(){
	     CCSprite loadingwindow=CCSprite.sprite("image/welcome.jpg");
	     loadingwindow.setAnchorPoint(0,0);
	     this.addChild(loadingwindow);
	     
	     loading();
   }

   public void loading() {
	   CCSprite loading=CCSprite.sprite("image/loading/loading_01.png");
	     loading.setPosition(winSize.width/2,50);
	     this.addChild(loading);
	     
	     
//	     ArrayList<CCSpriteFrame> frames=new ArrayList<CCSpriteFrame>();
//	     
//	     String format="image/loading/loading_%02d.png";
//	     for(int i=1;i<=9;i++){
//	    	 CCSpriteFrame displayedFrame = CCSprite.sprite(String.format(format, i)).displayedFrame();
//	    	 frames.add(displayedFrame);
//	     }
//		CCAnimation anim=CCAnimation.animation("加载", 0.2f, frames);
//		CCAnimate animate=CCAnimate.action(anim,false);
//		
	     CCAction animate = CommonUilts.getAnimate("image/loading/loading_%02d.png",
	    		 9, false);
		loading.runAction(animate);
		
		start = CCSprite.sprite("image/loading/loading_start.png");
		start.setPosition(winSize.width/2,50);
		start.setVisible(false);
	     this.addChild(start);
}
}
