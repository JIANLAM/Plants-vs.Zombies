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
 * 游戏控制
 * 单例
 *
 */
public class GameController {

	 public static boolean isStart;
	 
	private CCTMXTiledMap map;
	private List<ShowPlant> selectedPlant;
	public GameController() {
	}
	
	//控制5行的逻辑
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
	
	
	//得到一个实例
	public static GameController getInstance(){
		
		
		return controller;
		 
	}
	
	public void startGame(CCTMXTiledMap map, List<ShowPlant> selectedPlant){
		isStart=true;
		this.map=map;
		this.selectedPlant=selectedPlant;
		//解析道路
		loadMap();
	  /*
	   * 安放僵尸
	   * 定时器
	   * be carefull：函数要添加时间 参数4：是否暂停
	   */
		CCScheduler.sharedScheduler().schedule("addZoombies", this, 2, false);
		 progress();
	}
	
	//记录安放植物的点
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

	//安放僵尸
	public void addZoombies(float t){
		Random random=new Random();
		//行号
		int lineNum = random.nextInt(5);
		
		PrimaryZoombies zoombies=new PrimaryZoombies(roadPoint.get(lineNum*2),
				roadPoint.get(lineNum*2+1));
		map.addChild(zoombies,1);
		//添加到行战场
		lines.get(lineNum).addZoombies(zoombies);
		progress+=5;
		 
		progressTimer.setPercentage(progress);//设置新的进度
		
		//显示最后一波动画
		  if(progress==60){
			  CGSize winSize = CCDirector.sharedDirector().winSize();
			  finalWave = CCSprite.sprite("image/fight/finalWave/FinalWave_01.png");
			  finalWave .setPosition(winSize.width/2,winSize.height/2);
			  map.addChild(finalWave);
			  
			  //序列帧动画
			  CCAction animate = CommonUilts.getAnimate("image/fight/finalWave/FinalWave_%02d.png",
					  2, false);
			  CCDelayTime delayTime=CCDelayTime.action(1.5f);
			  CCSequence sequence=CCSequence.actions((CCAnimate)animate,delayTime, CCCallFunc.action(this,
					  "Hide"));
			  
			  finalWave.runAction(sequence);
		  }
	} 
	
	
	//隐藏最后一波的动画
	public void Hide(){
		 
		  finalWave.setVisible(false);
	}
	
	public void GameOver(){
		isStart=false;
	}

	private ShowPlant clickedPlant;
	
	//准备安放的实体植物
 	private Plant beReadyToInstalled;
	
 	
 	//游戏开始后 处理点击事件
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
					
					//植物被选中
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
			
			//安放植物
			if(clickedPlant!=null){
				/**
				 * 获得横竖的长度范围
				 */
				int row = (int) (point.x / 46) - 1; // 1-9 0-8
				int line = (int) ((CCDirector.sharedDirector().
						getWinSize().height - point.y) / 54) - 1;// 1-5
				System.out.println(row);
				System.out.println(line);// 0-4
				// 限制安放的植物的范围
				if (row >= 0 && row <= 8 && line >= 0 && line <= 4) {

					// 安放植物
					beReadyToInstalled.setLine(line);// 设置植物的行号
					beReadyToInstalled.setRow(row); // 设置植物的列号

					beReadyToInstalled.setPosition(towers[line][row]); // 修正了植物的坐标
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
		// 创建了进度条
		progressTimer = CCProgressTimer.progressWithFile("image/fight/progress.png");
		// 设置进度条的位置 
		progressTimer.setPosition(CCDirector.sharedDirector().getWinSize().width - 80, 13);
		map.getParent().addChild(progressTimer); //图层添加了进度条 
		progressTimer.setScale(0.6f);  //  设置了缩放 

		progressTimer.setPercentage(0);// 每增加一个僵尸需要调整进度，增加5
		progressTimer.setType(CCProgressTimer.kCCProgressTimerTypeHorizontalBarRL);  // 进度的样式

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
