package com.example.zoombiesgame;

import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.opengl.CCGLSurfaceView;

import com.example.zoombiesgame.Layer.WellcomLayer;
import com.example.zoombiesgame.Layer.fightLayer;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MainActivity extends Activity {

 /*
 *  this  project is add to GitHub
     let us updata  this game
 */
	private CCDirector director;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		CCGLSurfaceView surfaceView=new CCGLSurfaceView(this);
	    setContentView(surfaceView);
	    
	    director = CCDirector.sharedDirector();
	    director.attachInView(surfaceView);//开启一个线程
	    director.setDeviceOrientation(CCDirector.kCCDeviceOrientationLandscapeLeft);
		//屏幕的自动适配
		director.setScreenSize(480, 320);
	  
		CCScene  Scene=CCScene.node();
		
		Scene.addChild(new fightLayer());
		director.runWithScene(Scene);
		
	}

	protected void onResume() {
		super.onResume();
		director.onResume();
	}
	
	protected void onPause() {
		super.onPause();
		director.onPause();
	}
	protected void onDestroy() {
		super.onDestroy();
	    director.end();
	}
}
