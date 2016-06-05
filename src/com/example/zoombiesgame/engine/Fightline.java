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
 * 每行的功能抽取成一行
 * 可以安放植物 僵尸
 * 植物 僵尸的攻击
 *
 */
		
public class Fightline {
	//僵尸集合
	private List<Zombies> zoombies=new ArrayList<Zombies>();
	//攻击性植物集合
	private List<AttackPlant> attackPlants=new ArrayList<AttackPlant>();
	//生产性植物集合
	private List<Sunflower> sunFlower=new ArrayList<Sunflower>();
	//自爆性植物的集合
	private List<ExplodePlant> explodePlant=new ArrayList<ExplodePlant>();
	 private int line;
	
	 // 管理每行添加的植物 Integer为行号
	 private Map<Integer, Plant> plants = 
			 new HashMap<Integer, Plant>();
	
	 
	 public Fightline(int line) {
		 this.line=line;
		 //攻击植物
		 CCScheduler.sharedScheduler().schedule("attackPlant",
				 this, 0.2f, false);
		 //攻击僵尸
		 
		 CCScheduler.sharedScheduler().schedule("attackZoombies",
				 this, 0.2f, false);
		 //产生子弹
		 CCScheduler.sharedScheduler().schedule("createBullet",
				 this, 0.1f, false);
		 
		 //产生阳光
		 CCScheduler.sharedScheduler().schedule("createSunshine",
				 this, 10f, false);
		 
		 //判断是否自爆
		 CCScheduler.sharedScheduler().schedule("isExplode",
				 this, 0.1f, false);
	}
	 
	 
	 //判断是否自爆
	 public void isExplode(float t){

		 if(zoombies.size()>0 && explodePlant.size()>0){
		   
			  for(Zombies zoombie:zoombies){
				  float x = zoombie.getPosition().x;
				  
				 float left=x-20;
				 float right=x+20;
				 
				 for(ExplodePlant plant:explodePlant){
				 //判断爆炸植物和僵尸的位置
					 if(plant.getPosition().x>left && plant.getPosition().x<right){
						  //僵尸扣血
							zoombie.attacked(60);    
						 plant.ExplodeMyself();
				 	  
				    }
			
					 
				 }
			  }
		 }
	
		   
	 }
	 //产生阳光
	   public void createSunshine(float t){
 
		    for(Sunflower sunflower:sunFlower){
		    	sunflower.create();
		    
		    	
		    }
	   }
	 
	  //产生子弹
	 public void createBullet(float t){
		 if(zoombies.size()>0 && attackPlants.size()>0){
			  for(AttackPlant plant:  attackPlants){
				  plant.createBullet();
			  }
		 }
		
		   
	 }
	 //死亡僵尸数
		public  static int numOfDeath=0;
 
	 //添加僵尸
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
//		 System.out.println("游戏结束了！");
//		 if(DieZoombies.size()==15){
//			 System.out.println("游戏结束了！");
//		 }
//		   
//	 }
	    /**
		 * 判断该列上 是否有植物
		 * 
		 */
	 
	  	public boolean containsRow(int row) {
			return plants.containsKey(row);
		}
		
	  	
	  
		//添加植物到行中
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
           //设置死亡监听 从对应集合中除去
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
		

		 //攻击植物
		
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
		//攻击僵尸
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
								
								  //僵尸扣血
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
