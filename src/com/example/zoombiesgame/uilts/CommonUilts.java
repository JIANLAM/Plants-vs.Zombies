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
import org.cocos2d.transitions.CCFadeDownTransition;
import org.cocos2d.transitions.CCFadeTRTransition;
import org.cocos2d.transitions.CCFlipXTransition;
import org.cocos2d.transitions.CCJumpZoomTransition;
import org.cocos2d.types.CGPoint;

import android.R.string;

public class CommonUilts {

	/*
	 * ѡ�񳡾��л���Ч�ĳ���
	 */
	//ˮƽ�л�
	public static final int FLIPX_TANSITION=0;
	//��Ծ�л�
	public static final int JUMP_TANSITION=1;
	//����
	public static final int FADE_TANSITION=2;
	//�ı仭��
	public static void changLayer(CCLayer newlayer,int i) {
		
		
		CCScene Scene=CCScene.node();
		Scene.addChild(newlayer);
	 
	 
		switch (i) {
				case FLIPX_TANSITION:
					CCFlipXTransition  XTransition=
					 CCFlipXTransition.transition(2, Scene, 1);
					CCDirector.sharedDirector().replaceScene( XTransition);
					break;
				case JUMP_TANSITION:
					CCJumpZoomTransition  JTransition=
							CCJumpZoomTransition.transition(2f, Scene);
					CCDirector.sharedDirector().replaceScene( JTransition);
					break;
				case FADE_TANSITION:
					CCFadeTRTransition  FTransition=
							CCFadeDownTransition.transition(2f, Scene);
					CCDirector.sharedDirector().replaceScene( FTransition);
					break;
				 
				default:
					break;
				}
		
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
