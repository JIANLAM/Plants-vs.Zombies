package com.example.zoombiesgame.engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cocos2d.actions.CCScheduler;
import org.cocos2d.actions.base.CCAction;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;

import com.example.zoombiesgame.base.BaseElement.DieListener;
import com.example.zoombiesgame.base.AttackPlant;
import com.example.zoombiesgame.base.Bullet;
import com.example.zoombiesgame.base.ExplodePlant;
import com.example.zoombiesgame.base.Plant;
import com.example.zoombiesgame.base.Zombies;
import com.example.zoombiesgame.bean.Sunflower;
import com.example.zoombiesgame.uilts.CommonUilts;

/**
 * 
 * ÿ�еĹ��ܳ�ȡ��һ��
 * ���԰���ֲ�� ��ʬ
 * ֲ�� ��ʬ�Ĺ���
 *
 */
		
public class Fightline {
	//��ʬ����
	private List<Zombies> zoombies=new ArrayList<Zombies>();
	//������ֲ�Ｏ��
	private List<AttackPlant> attackPlants=new ArrayList<AttackPlant>();
	//������ֲ�Ｏ��
	private List<Sunflower> sunFlower=new ArrayList<Sunflower>();
	//�Ա���ֲ��ļ���
	private List<ExplodePlant> explodePlant=new ArrayList<ExplodePlant>();
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
				 this, 0.1f, false);
		 
		 //��������
		 CCScheduler.sharedScheduler().schedule("createSunshine",
				 this, 10f, false);
		 
		 //�ж��Ƿ��Ա�
		 CCScheduler.sharedScheduler().schedule("isExplode",
				 this, 0.1f, false);
	}
	 
	 
	 //�ж��Ƿ��Ա�
	 public void isExplode(float t){

		 if(zoombies.size()>0 && explodePlant.size()>0){
		   
			  for(Zombies zoombie:zoombies){
				  float x = zoombie.getPosition().x;
				  
				 float left=x-20;
				 float right=x+20;
				 
				 for(ExplodePlant plant:explodePlant){
				 //�жϱ�ըֲ��ͽ�ʬ��λ��
					 if(plant.getPosition().x>left && plant.getPosition().x<right){
						  //��ʬ��Ѫ
							zoombie.attacked(60);    
						 plant.ExplodeMyself();
				 	  
				    }
			
					 
				 }
			  }
		 }
	
		   
	 }
	 //��������
	   public void createSunshine(float t){
 
		    for(Sunflower sunflower:sunFlower){
		    	sunflower.create();
		    
		    	
		    }
	   }
	 
	  //�����ӵ�
	 public void createBullet(float t){
		 if(zoombies.size()>0 && attackPlants.size()>0){
			  for(AttackPlant plant:  attackPlants){
				  plant.createBullet();
			  }
		 }
		
		   
	 }
	 //������ʬ��
		public  static int numOfDeath=0;
 
	 //��ӽ�ʬ
	 public void addZoombies(final Zombies mZoombies){
		 zoombies.add(mZoombies);
		
		 mZoombies.setDieListener(new DieListener() {
			
			public void die() {
				++numOfDeath;
				zoombies.remove(mZoombies);
			 
			}
		});
 
	 }
	 
//	 
//	 public void isOver(){
//		 System.out.println("��Ϸ�����ˣ�");
//		 if(DieZoombies.size()==15){
//			 System.out.println("��Ϸ�����ˣ�");
//		 }
//		   
//	 }
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
			
          if(plant instanceof Sunflower){
				
        	  sunFlower.add((Sunflower) plant);
			}
          
          
          if(plant instanceof ExplodePlant){
        	  explodePlant.add((ExplodePlant)plant);
        	    
          }
           //������������ �Ӷ�Ӧ�����г�ȥ
			plant.setDieListener(new DieListener() {
				
				public void die() {
					plants.remove(plant.getRow());
					if(plant instanceof AttackPlant){
						
						attackPlants.remove(plant);
					}
                     if(plant instanceof Sunflower){
						
                    	 sunFlower.remove(plant);
					}
                     if(plant instanceof ExplodePlant){
 						
                    	 explodePlant.remove(plant);
					}
				}
			});
		}
		

		 //����ֲ��
		
		public void attackPlant(float t){
			if(zoombies.size()>0 && plants.size()>0){
				  for(Zombies zombies:zoombies){
					  CGPoint position = zombies.getPosition();
					int row=  (int) (position.x/44-1);
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
