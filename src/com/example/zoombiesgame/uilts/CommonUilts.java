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

	
	//�ı仭��
	public static void changLayer(CCLayer newlayer) {
		
		
		CCScene Scene=CCScene.node();
		Scene.addChild(newlayer);
	 CCFlipXTransition  Transition=
			 CCFlipXTransition.transition(2, Scene, 1);
		CCDirector.sharedDirector().replaceScene( Transition);
	}
	
	//������ͼ
	
	public static List<CGPoint> getObjectPoint(CCTMXTiledMap map,String name){
		List<CGPoint> point = new ArrayList<CGPoint>();
		//��õ�ͼ����
		CCTMXObjectGroup  mapObject=map.objectGroupNamed(name);
		
		//��ȡhashmap����
		ArrayList<HashMap<String, String>> objects = mapObject.objects;
		
		//ѭ���õ����������
		for(HashMap<String, String> hashmap:objects){
			   
			int x = Integer.parseInt(hashmap.get("x"));
			int y = Integer.parseInt(hashmap.get("y"));
		  
			CGPoint cp = CCNode.ccp(x, y);
			point.add(cp); 
	
	}
	 	 return point;
	}
	
	
	//���в���
	
	public static CCAction getAnimate(String format,int num,boolean isForever){
          
	     ArrayList<CCSpriteFrame> frames=new ArrayList<CCSpriteFrame>();
	    
	    //��׼�� 02d��ʾ��λ�� 0�Զ���һλ��
	    
	   
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
