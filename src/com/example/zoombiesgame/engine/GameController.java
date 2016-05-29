package com.example.zoombiesgame.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import org.cocos2d.actions.CCProgressTimer;
import org.cocos2d.actions.CCScheduler;
import org.cocos2d.actions.base.CCAction;
import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.interval.CCAnimate;
import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.layers.CCTMXTiledMap;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;
import org.cocos2d.types.CGSize;

import com.example.zoombiesgame.Layer.fightLayer;
import com.example.zoombiesgame.base.Plant;
import com.example.zoombiesgame.bean.Nut;
import com.example.zoombiesgame.bean.PrimaryZoombies;
import com.example.zoombiesgame.bean.ShowPlant;
import com.example.zoombiesgame.bean.Sunflower;
import com.example.zoombiesgame.bean.SuperPeasePlant;
import com.example.zoombiesgame.bean.peasePlant;
import com.example.zoombiesgame.uilts.CommonUilts;

import android.R.bool;


/**
 * ��Ϸ����
 * ����
 *
 */
public class GameController {

	 public static boolean isStart;
	 
	private CCTMXTiledMap map;
	private List<ShowPlant> selectedPlant;
	public GameController() {
	}
	
	//����5�е��߼�
	private  static List<Fightline> lines;
	static{
		lines=new ArrayList<Fightline>();
		for(int i=0;i<5;i++){
			Fightline line=new Fightline(i);
			lines.add(line);
		}
		  
	}
	private static GameController controller=new GameController();

	public List<CGPoint> roadPoint;
	
	
	//�õ�һ��ʵ��
	public static GameController getInstance(){
		
		
		return controller;
		 
	}
	
	public void startGame(CCTMXTiledMap map, List<ShowPlant> selectedPlant){
		isStart=true;
		this.map=map;
		this.selectedPlant=selectedPlant;
		//������·
		loadMap();
	  /*
	   * ���Ž�ʬ
	   * ��ʱ��
	   * be carefull������Ҫ���ʱ�� ����4���Ƿ���ͣ
	   */
		CCScheduler.sharedScheduler().schedule("addZoombies", this, 2, false);
		 progress();
	}
	
	//��¼����ֲ��ĵ�
	CGPoint[][] towers = new CGPoint[5][9];
	
	private void loadMap() {
		roadPoint = CommonUilts.getObjectPoint(map, "road");
		String format="tower%02d";
		for(int i=1;i<=5;i++){
		
			List<CGPoint> towerPoint = CommonUilts.getObjectPoint(map, String.format(format, i));
			
			for(int j=0;j<towerPoint.size();j++){
				towers[i-1][j]=towerPoint.get(j);
			}
		}
	}

	//���Ž�ʬ
	public void addZoombies(float t){
		Random random=new Random();
		//�к�
		int lineNum = random.nextInt(5);
		
		PrimaryZoombies zoombies=new PrimaryZoombies(roadPoint.get(lineNum*2),
				roadPoint.get(lineNum*2+1));
		map.addChild(zoombies,1);
		//��ӵ���ս��
		lines.get(lineNum).addZoombies(zoombies);
		progress+=5;
		 
		progressTimer.setPercentage(progress);//�����µĽ���
		
		//��ʾ���һ������
		  if(progress==60){
			  CGSize winSize = CCDirector.sharedDirector().winSize();
			  finalWave = CCSprite.sprite("image/fight/finalWave/FinalWave_01.png");
			  finalWave .setPosition(winSize.width/2,winSize.height/2);
			  map.addChild(finalWave);
			  
			  //����֡����
			  CCAction animate = CommonUilts.getAnimate("image/fight/finalWave/FinalWave_%02d.png",
					  2, false);
			  CCDelayTime delayTime=CCDelayTime.action(1.5f);
			  CCSequence sequence=CCSequence.actions((CCAnimate)animate,delayTime, CCCallFunc.action(this,
					  "Hide"));
			  
			  finalWave.runAction(sequence);
		  }
	} 
	
	
	//�������һ���Ķ���
	public void Hide(){
		 
		  finalWave.setVisible(false);
	}
	
	public void GameOver(){
		isStart=false;
	}

	private ShowPlant clickedPlant;
	
	//׼�����ŵ�ʵ��ֲ��
 	private Plant beReadyToInstalled;
	
 	
 	//��Ϸ��ʼ�� �������¼�
	public void handleTouch(CGPoint point) {
		CCSprite chose=(CCSprite) map.getParent().
				getChildByTag(fightLayer.TAG_CHOSE);
		
		CGRect choseBox = chose.getBoundingBox();
		if(CGRect.containsPoint(choseBox, point)){
			if(clickedPlant!=null){
				 clickedPlant.getPlant().setOpacity(255);
				 clickedPlant=null;
			}
			for(ShowPlant plant:selectedPlant){
				 
				CGRect plantBox = plant.getPlant().getBoundingBox();
				if(CGRect.containsPoint(plantBox, point)){
					
					//ֲ�ﱻѡ��
					 clickedPlant=plant;
					 clickedPlant.getPlant().setOpacity(150);
					 int id = clickedPlant.getId();
					 
					 switch (id) {
					 case 1:
							beReadyToInstalled=new peasePlant();
						  	break;
					 case 2:beReadyToInstalled=new Sunflower();
					  	break;
					case 4:
						beReadyToInstalled=new Nut();
					  	break;

					case 8:
						beReadyToInstalled=new SuperPeasePlant();
					  	break;
					default:
						break;
					}
				}
			 }
		}else{
			
			//����ֲ��
			if(clickedPlant!=null){
				/**
				 * ��ú����ĳ��ȷ�Χ
				 */
				int row = (int) (point.x / 46) - 1; // 1-9 0-8
				int line = (int) ((CCDirector.sharedDirector().
						getWinSize().height - point.y) / 54) - 1;// 1-5
				System.out.println(row);
				System.out.println(line);// 0-4
				// ���ư��ŵ�ֲ��ķ�Χ
				if (row >= 0 && row <= 8 && line >= 0 && line <= 4) {

					// ����ֲ��
					beReadyToInstalled.setLine(line);// ����ֲ����к�
					beReadyToInstalled.setRow(row); // ����ֲ����к�

					beReadyToInstalled.setPosition(towers[line][row]); // ������ֲ�������
//				 
				     if(!lines.contains(row)){
				    		lines.get(line).addPlant(beReadyToInstalled);
							map.addChild(beReadyToInstalled);
					   }
				
					}
				beReadyToInstalled=null;
				 clickedPlant.getPlant().setOpacity(255);
				
					clickedPlant=null;
				}
		
			 
		}
	}

	CCProgressTimer progressTimer;
	int  progress=0;

	private CCSprite finalWave;
	private void progress() {
		// �����˽�����
		progressTimer = CCProgressTimer.progressWithFile("image/fight/progress.png");
		// ���ý�������λ�� 
		progressTimer.setPosition(CCDirector.sharedDirector().getWinSize().width - 80, 13);
		map.getParent().addChild(progressTimer); //ͼ������˽����� 
		progressTimer.setScale(0.6f);  //  ���������� 

		progressTimer.setPercentage(0);// ÿ����һ����ʬ��Ҫ�������ȣ�����5
		progressTimer.setType(CCProgressTimer.kCCProgressTimerTypeHorizontalBarRL);  // ���ȵ���ʽ

		CCSprite sprite = CCSprite.sprite("image/fight/flagmeter.png");
		sprite.setPosition(CCDirector.sharedDirector().getWinSize().width - 80, 13);
		map.getParent().addChild(sprite);
		sprite.setScale(0.6f);
		CCSprite name = CCSprite.sprite("image/fight/FlagMeterLevelProgress.png");
		name.setPosition(CCDirector.sharedDirector().getWinSize().width - 80, 5);
		map.getParent().addChild(name);
		name.setScale(0.6f);
	}
}
