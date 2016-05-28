package com.example.zoombiesgame.base;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.example.zoombiesgame.bean.SunShine;

/**
 * 生产型植物
 * @author Administrator
 *
 */
public abstract class ProductPlant extends Plant {

	
	// 阳光集合
	protected List<SunShine> sunShine = new CopyOnWriteArrayList<SunShine>();
	public ProductPlant(String filepath) {
		super(filepath);
	}

	/**
	 * 阳光、金币
	 */
	public abstract void create();
	

}
