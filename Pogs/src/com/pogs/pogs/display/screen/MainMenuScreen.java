package com.pogs.pogs.display.screen;

import java.util.ArrayList;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.equations.Back;
import aurelienribon.tweenengine.equations.Bounce;
import aurelienribon.tweenengine.equations.Elastic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.pogs.pogs.PogsMain;
import com.pogs.pogs.display.ui.GameButton;
import com.pogs.pogs.display.ui.GameObject;
import com.pogs.pogs.util.Assets;
import com.pogs.pogs.util.GameData;
import com.pogs.pogs.util.GameSetting;
import com.pogs.pogs.util.LevelModel;

public class MainMenuScreen extends GameDisplay
{
	public static final int TITLE_SCREEN = 1;
	public static final int LEVEL_SELECTION = 2;
	private int state = 1;
	private GameButton startB;
	private GameButton aboutB;
	private GameButton helpB;
	private GameButton soundB;
	private GameObject gameTitle;
	private GameObject aboutPanel;
	
	private float cloudX;
	private boolean goLeft;
	private boolean showAbout= false;
	
	public MainMenuScreen(PogsMain game)
	{
		super(game);
		
		gameTitle = new GameObject(Assets.title);
        gameTitle.setScale(0f, 0f);
        gameTitle.setPosition( PogsMain.WIDTH/2, PogsMain.HEIGHT - Assets.title.getRegionHeight()-100);
        Tween[] tween = new Tween[]{gameTitle.tweenTo( ((PogsMain.WIDTH - Assets.title.getRegionWidth())/2), gameTitle.y , 1.0f, 1.0f, gameTitle.rotation , gameTitle.alpha, Elastic.OUT, 1200, 200,0 )};
        tween[0].addToManager(manager);
        
		startB = new GameButton(Assets.startB,Assets.startB,Assets.press_sound);
        startB.setPosition(PogsMain.WIDTH/2 , PogsMain.HEIGHT/3);
        startB.setScale(0.0f, 0.0f);
        tween = new Tween[]{startB.tweenTo(startB.x - (startB.getWidth()/2), startB.y , 1.0f, 1.0f,startB.rotation , startB.alpha, Elastic.OUT, 1000, 400,0 ) , 
        		startB.tweenTo(PogsMain.WIDTH/2, startB.y , 0.0f, 0.0f, startB.rotation , startB.alpha, Back.IN, 500, 400,0 )};
        startB.setTween(tween , manager);
        startB.activateTween(0);
        
        aboutB = new GameButton(Assets.aboutB,Assets.aboutB,Assets.press_sound);
        aboutB.setPosition(PogsMain.WIDTH/2 , PogsMain.HEIGHT/3 - 60);
        aboutB.setScale(0.0f, 0.0f);
        tween = new Tween[]{aboutB.tweenTo(aboutB.x - (aboutB.getWidth()/2), aboutB.y , 1.0f, 1.0f,aboutB.rotation , aboutB.alpha, Elastic.OUT, 1000, 400,0 ) , 
        		aboutB.tweenTo(PogsMain.WIDTH/2, aboutB.y , 0.0f, 0.0f, aboutB.rotation , aboutB.alpha, Back.IN, 500, 400,0 )};
        aboutB.setTween(tween , manager);
        aboutB.activateTween(0);
        
        helpB = new GameButton(Assets.helpB,Assets.helpB,Assets.press_sound);
        helpB.setPosition(PogsMain.WIDTH/2 , PogsMain.HEIGHT/3 - 120);
        helpB.setScale(0.0f, 0.0f);
        tween = new Tween[]{helpB.tweenTo(helpB.x - (helpB.getWidth()/2), helpB.y , 1.0f, 1.0f,helpB.rotation , helpB.alpha, Elastic.OUT, 1000, 400,0 ) , 
        		helpB.tweenTo(PogsMain.WIDTH/2, helpB.y , 0.0f, 0.0f, helpB.rotation , helpB.alpha, Back.IN, 500, 400,0 )};
        helpB.setTween(tween , manager);
        helpB.activateTween(0);
        
        soundB = new GameButton(Assets.soundB,Assets.sound_offB);
		soundB.setPosition(PogsMain.WIDTH-soundB.getWidth(), PogsMain.HEIGHT-soundB.getHeight());
		
		aboutPanel = new GameObject(Assets.about);
        aboutPanel.setPosition( (PogsMain.WIDTH-Assets.about.getRegionWidth())/2, PogsMain.HEIGHT);
        tween = new Tween[]{
        		aboutPanel.tweenTo( aboutPanel.x,( PogsMain.HEIGHT - Assets.about.getRegionHeight())/2, 1.0f, 1.0f, aboutPanel.rotation , aboutPanel.alpha, Bounce.OUT, 2000, 200,0 ),
        		aboutPanel.tweenTo( aboutPanel.x, PogsMain.HEIGHT , 1.0f, 1.0f, aboutPanel.rotation , aboutPanel.alpha, Bounce.IN, 2000, 200,0 )};
        aboutPanel.setTween(tween,manager);
		
        goLeft = true;
        cloudX = 0;
        
        
	}
	public void draw(SpriteBatch batch)
	{
		//draw gradient
		batch.draw(Assets.gradient,0, PogsMain.HEIGHT - Assets.gradient.getRegionHeight());
		//draw clouds
		batch.draw(Assets.clouds, cloudX, PogsMain.HEIGHT -Assets.clouds.getRegionHeight() );
		//draw grass
		batch.draw(Assets.grass, 0,0);
		if(state == TITLE_SCREEN)
		{
			gameTitle.draw(batch);
			startB.draw(batch);
			aboutB.draw(batch);
			helpB.draw(batch);
			soundB.draw(batch);
			aboutPanel.draw(batch);
		}
		else if(state == LEVEL_SELECTION){
			ArrayList<LevelModel> levels = GameData.getInstance().gameLevels;
			for(int i=0;i<levels.size();i++){
				LevelModel level = levels.get(i);
				
				level.draw(batch);
			}
		}
		
	}
	public void update(float delta)
	{
		if(state == TITLE_SCREEN)
		{
			if(goLeft)
				cloudX -= (delta * 20 * 1000 )/1000;
			else 
				cloudX += (delta * 20 * 1000 )/1000;
			if(cloudX > PogsMain.WIDTH + 100 - Assets.clouds.getRegionWidth())
				goLeft = true;
			if(cloudX < -100)
				goLeft = false;
			
			if(startB.activate && startB.tweenDone)
				state = LEVEL_SELECTION;
			if(aboutB.activate && aboutB.tweenDone){
				if(!showAbout){
					showAbout = true;
					aboutPanel.activateTween(0);
				}
			}
		}
		else{
			ArrayList<LevelModel> levels = GameData.getInstance().gameLevels;
			for(int i=0;i<levels.size();i++){
				LevelModel level = levels.get(i);
				level.update(delta);
				level.x = (i%3+1) * (PogsMain.WIDTH/4) - level.getWidth()/2;
				level.y = PogsMain.HEIGHT - ((i/3+1) * (PogsMain.HEIGHT/4) - 25);
			}
		}
		
	}
	public void input(float x,float y)
	{
		if(Gdx.input.justTouched())
		{
			Rectangle touch = new Rectangle(x,y,10,10);
			if(state == TITLE_SCREEN)
			{
				if(showAbout){
					aboutPanel.activateTween(1);
					aboutB.activateTween(0);
					showAbout=false;
					aboutB.unpressed();
					return;
				}
				if(touch.overlaps(startB.getBounds()))
				{
					startB.pressed(GameSetting.soundOn);
					startB.activateTween(1);
					GameData.getInstance().load();
				}
				else if(touch.overlaps(aboutB.getBounds()))
				{
					aboutB.pressed(GameSetting.soundOn);
					aboutB.activateTween(1);
				}
				else if(touch.overlaps(helpB.getBounds()))
				{
					helpB.pressed(GameSetting.soundOn);
					helpB.activateTween(1);
				}
				else if(touch.overlaps(soundB.getBounds()))
				{
					if(soundB.activate){
						GameSetting.soundOn=true;
						soundB.unpressed();
						Assets.playMusic(Assets.currentMusic);
					}
					else{
						GameSetting.soundOn=false;
						soundB.pressed(GameSetting.soundOn);
						Assets.stopMusic(Assets.currentMusic);
						
					}
					
				}
			}
			else if(state == LEVEL_SELECTION){
				ArrayList<LevelModel> levels = GameData.getInstance().gameLevels;
				for(int i=0;i<levels.size();i++){
					LevelModel level = levels.get(i);
					if(touch.overlaps(level.getBounds()) && !level.lock){
						GameSetting.level = level.lv;
						game.setDisplay(PogsMain.ON_GAME_SCREEN);
					}
				}
			}
		}
	}
	public void playMusic()
	{
		Assets.bgm.setLooping(true);
		Assets.playMusic(Assets.bgm);
	}
	public void stopMusic()
	{
		
	}
	private void drawBackground(SpriteBatch batch)
	{
		/*tile based algorithm
		 * algorithm based on a single tileset with different
		 * tiles used to build an entire world model for games
		 */
		//draw background-----------
		boolean tile1 = true;
		for(int i =0;i<game.WIDTH/40;i++)
		{
			for(int j=0;j<game.HEIGHT/40;j++)
			{
				if(tile1)
					batch.draw(Assets.tile1, i*40, j*40);
				else
					batch.draw(Assets.tile2, i*40, j*40);
				if(tile1) tile1=false;
				else tile1=true;
			}
			if(tile1) tile1=false;
			else tile1=true;
		}
		//---------------------------
	}
}
