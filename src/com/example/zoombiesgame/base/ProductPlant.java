package com.example.zoombiesgame.base;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.example.zoombiesgame.bean.SunShine;

/**
 * ������ֲ��
 * @author Administrator
 *
 */
public abstract class ProductPlant extends Plant {

	
	// ���⼯��
	protected List<SunShine> sunShine = new CopyOnWriteArrayList<SunShine>();
	public ProductPlant(String filepath) {
		super(filepath);
	}

	/**
	 * ���⡢���
	 */
	public abstract void create();
	

}
