package com.example.zoombiesgame.base;

import org.cocos2d.types.CGPoint;

import android.view.View;
import android.widget.Button;

/**
 * ��ʬ����
 * 
 * @author Administrator
 * 
 */
public abstract class Zombies extends BaseElement {

	protected int life = 60;// ����
	protected int attack = 5;// ������
	protected int speed = 20;// �ƶ��ٶ�

	protected CGPoint startPoint;// ���
	protected CGPoint endPoint;// �յ�

	public Zombies(String filepath) {
		super(filepath);
		
		setScale(0.5);
		setAnchorPoint(0.5f, 0);// �������ĵ�λ��������֮��
	}

	/**
	 * �ƶ�
	 */
	public abstract void move();

	/**
	 * 
	 * @param element:����ֲ�������ʬ
	 */
	public abstract void attack(BaseElement element);

	/**
	 * ������
	 */
	public abstract void attacked(int attack);

}
