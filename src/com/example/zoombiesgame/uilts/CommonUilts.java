package com.example.zoombiesgame.uilts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.cocos2d.actions.base.CCAction;
import org.cocos2d.actions.base.CCRepeatForever;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.layers.CCTMXObjectGroup;
import org.cocos2d.layers.CCTMXTiledMap;
import org.cocos2d.nodes.CCAnimation;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCNode;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.nodes.CCSpriteFrame;
import org.cocos2d.opengl.CCGLSurfaceView;
import org.cocos2d.transitions.CCFlipXTransition;
import org.cocos2d.types.CGPoint;

import android.R.string;

public class CommonUilts {

	
	//改变画面
	public static void changLayer(CCLayer newlayer) {
		
		
		CCScene Scene=CCScene.node();
		Scene.addChild(newlayer);
	 CCFlipXTransition  Transition=
			 CCFlipXTransition.transition(2, Scene, 1);
		CCDirector.sharedDirector().replaceScene( Transition);
	}
	
	//解析地图
	
	public static List<CGPoint> getObjectPoint(CCTMXTiledMap map,String name){
		List<CGPoint> point = new ArrayList<CGPoint>();
		//获得地图对象
		CCTMXObjectGroup  mapObject=map.objectGroupNamed(name);
		
		//获取hashmap集合
		ArrayList<HashMap<String, String>> objects = mapObject.objects;
		
		//循环得到里面的内容
		for(HashMap<String, String> hashmap:objects){
			   
			int x = Integer.parseInt(hashmap.get("x"));
			int y = Integer.parseInt(hashmap.get("y"));
		  
			CGPoint cp = CCNode.ccp(x, y);
			point.add(cp); 
	
	}
	 	 return point;
	}
	
	
	//序列播放
	
	public static CCAction getAnimate(String format,int num,boolean isForever){
          
	     ArrayList<CCSpriteFrame> frames=new ArrayList<CCSpriteFrame>();
	    
	    //标准化 02d表示两位数 0自动补一位数
	    
	   
	    for(int i=1;i<=num;i++){
		    CCSpriteFrame displayedFrame = CCSprite.sprite
		    		(String.format(format, i)).displayedFrame();
		    
		    frames.add(displayedFrame);
	    }
	    CCAnimation ccAnimation=CCAnimation.animation("", 0.2f, frames);
	   if(isForever){
	 
		   CCAnimate ccAnimate=CCAnimate.action(ccAnimation);
		    CCRepeatForever Repeat=CCRepeatForever.action(ccAnimate); 
		    
		    return Repeat;
	   }else{
		  
		   CCAnimate ccAnimate=CCAnimate.action(ccAnimation,false);
		   return ccAnimate;
	   }
		
	   
		  
	}
}
