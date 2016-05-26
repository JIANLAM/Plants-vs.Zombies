package com.example.zoombiesgame.engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cocos2d.actions.CCScheduler;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.types.CGPoint;

import com.example.zoombiesgame.base.BaseElement.DieListener;
import com.example.zoombiesgame.base.AttackPlant;
import com.example.zoombiesgame.base.Bullet;
import com.example.zoombiesgame.base.Plant;
import com.example.zoombiesgame.base.Zombies;

/**
 * 
 * ÿ�еĹ��ܳ�ȡ��һ��
 * ���԰���ֲ�� ��ʬ
 * ֲ�� ��ʬ�Ĺ���
 *
 */
		
public class Fightline {

	private List<Zombies> zoombies=new ArrayList<Zombies>();
	private List<AttackPlant> attackPlants=new ArrayList<AttackPlant>();
	 private int line;
	
	 // ����ÿ����ӵ�ֲ�� IntegerΪ�к�
	 private Map<Integer, Plant> plants = 
			 new HashMap<Integer, Plant>();
	
	 
	 public Fightline(int line) {
		 this.line=line;
		 //����ֲ��
		 CCScheduler.sharedScheduler().schedule("attackPlant",
				 this, 0.2f, false);
		 //������ʬ
		 
		 CCScheduler.sharedScheduler().schedule("attackZoombies",
				 this, 0.2f, false);
		 //�����ӵ�
		 CCScheduler.sharedScheduler().schedule("createBullet",
				 this, 0.2f, false);
	}
	 
	 
	 
	 
	 
	  //�����ӵ�
	 public void createBullet(float t){
		 if(zoombies.size()>0 && attackPlants.size()>0){
			  for(AttackPlant plant:  attackPlants){
				  plant.createBullet();
			  }
		 }
		
		   
	 }
	 public void addZoombies(final Zombies mZoombies){
		 zoombies.add(mZoombies);
		 mZoombies.setDieListener(new DieListener() {
			
			public void die() {;
				zoombies.remove(mZoombies);
			}
		});
	 }
	 
	    /**
		 * �жϸ����� �Ƿ���ֲ��
		 * 
		 */
	 
	  	public boolean containsRow(int row) {
			return plants.containsKey(row);
		}
		
	  	
	  
		//���ֲ�ﵽ����
		public void addPlant(final Plant plant){
			plants.put(plant.getRow(), plant);
			
			if(plant instanceof AttackPlant){
				
				attackPlants.add((AttackPlant)plant);
			}
			
			plant.setDieListener(new DieListener() {
				
				public void die() {
					plants.remove(plant.getRow());
					if(plant instanceof AttackPlant){
						
						attackPlants.remove(plant.getRow());
					}
				}
			});
		}
		

		 //����ֲ��
		
		public void attackPlant(float t){
			if(zoombies.size()>0 && plants.size()>0){
				  for(Zombies zombies:zoombies){
					  CGPoint position = zombies.getPosition();
					int row=  (int) (position.x/50-1);
					Plant plant = plants.get(row);
					if(plant!=null){
						zombies.attack(plant);
					}
					  
				  }
			}
		}
		//������ʬ
		public void attackZoombies(float t){
			 if(zoombies.size()>0 && attackPlants.size()>0){
			   
				  for(Zombies zoombie:zoombies){
					  float x = zoombie.getPosition().x;
					  
					 float left=x-20;
					 float right=x+20;
					 
					 for(AttackPlant plant:attackPlants){
						 List<Bullet> bullets = plant.getBullets();
						for(Bullet bullet:bullets){
							float bulletx = bullet.getPosition().x;
							
							if(bulletx>left && bulletx<right){
								
								  //��ʬ��Ѫ
								zoombie.attacked(bullet.getAttack());
								bullet.setVisible(false);
								bullet.setAttack(0);
							}
						}
					 }
				  }
			 }
		}
	 
}
