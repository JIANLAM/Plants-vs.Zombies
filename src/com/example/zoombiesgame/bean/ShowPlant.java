package com.example.zoombiesgame.bean;

import java.util.HashMap;
import java.util.Map;

import org.cocos2d.nodes.CCSprite;

public class ShowPlant {

	static Map<Integer,HashMap<String,String>> db;
	private CCSprite plant;
	private CCSprite darkplant;
	
	private int id;
	//得到植物精灵
	public CCSprite getPlant() {
		return plant;
	}

	static{
		 //模拟数据库
		db=new HashMap<Integer, HashMap<String,String>>();
		
		String format="image/fight/chose/choose_default%02d.png";
		for(int i=0;i<=9;i++){
			HashMap<String,String> value=new HashMap<String, String>();
			value.put("path", String.format(format, i));
			value.put("sun", 50+"");
			db.put(i, value);
		}
	}
	
	public int getId() {
		return id;
	}

	public ShowPlant(int id) {
		this.id=id;
		HashMap<String, String> hashMap = db.get(id);
		String path = hashMap.get("path");
		plant = CCSprite.sprite(path);
		plant.setAnchorPoint(0,0);
		
		
		darkplant= CCSprite.sprite(path);
		darkplant.setAnchorPoint(0,0);
		darkplant.setOpacity(150);
		
	}

	public CCSprite getDarkplant() {
		return darkplant;
	} 
}
