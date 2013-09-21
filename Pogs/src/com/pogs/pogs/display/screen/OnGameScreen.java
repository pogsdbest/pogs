package com.pogs.pogs.display.screen;

import java.util.ArrayList;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.equations.Bounce;
import aurelienribon.tweenengine.equations.Cubic;
import aurelienribon.tweenengine.equations.Elastic;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.pogs.pogs.PogsMain;
import com.pogs.pogs.display.objects.Fling;
import com.pogs.pogs.display.ui.GameButton;
import com.pogs.pogs.display.ui.GameObject;
import com.pogs.pogs.display.ui.GamePanel;
import com.pogs.pogs.util.Assets;
import com.pogs.pogs.util.GameData;
import com.pogs.pogs.util.GameLevel;
import com.pogs.pogs.util.GameSetting;
import com.pogs.pogs.util.Hint;
import com.pogs.pogs.util.HintGenerator;
import com.pogs.pogs.util.LevelModel;

public class OnGameScreen extends GameDisplay
{
	private GamePanel panel;
	private ArrayList<Fling> flings;
	private ArrayList<ArrayList<Fling>> savedEntities;
	private ArrayList<Hint> hints;
	private Fling selected;
	private int[][] lv;
	private Fling current;
	public boolean stageClear = false;
	private float time;
	private boolean gameOver = false;
	private GameButton undoB;
	
	private GameObject stageCompleted;
	private GameObject game_Over;
	private GameObject quit_Game;
	private GameObject show_Hint;
	
	private GameButton soundB;
	private GameButton homeB;
	private GameButton hintB;
	private GameButton yesB;
	private GameButton noB;
	
	private boolean showHint = false;
	private boolean quitGame = false;
	
	private int hintIndex = 0;
	private boolean doHint = false;
	public OnGameScreen(PogsMain game)
	{
		super(game);
		
		//init panel
		panel = new GamePanel(manager);
		
		flings = new ArrayList<Fling>();
		savedEntities = new ArrayList<ArrayList<Fling>>();
		
		undoB = new GameButton(Assets.undoB,Assets.undoB);
		undoB.setPosition(0, 0);
		
		stageCompleted = new GameObject(Assets.stage_completed);
        stageCompleted.setPosition( (PogsMain.WIDTH-Assets.stage_completed.getRegionWidth())/2, ( PogsMain.HEIGHT - Assets.stage_completed.getRegionHeight())/2);
        stageCompleted.alpha = 0;
        Tween[] tween = new Tween[]{
        		stageCompleted.tweenTo( stageCompleted.x,stageCompleted.y, 1.0f, 1.0f, stageCompleted.rotation , 1.0f, Cubic.IN, 1500, 200,0 ),
        		stageCompleted.tweenTo( stageCompleted.x, stageCompleted.y , 1.0f, 1.0f, stageCompleted.rotation , 0.0f, Cubic.OUT, 1500, 200,0 )};
        stageCompleted.setTween(tween,manager);
        
        game_Over = new GameObject(Assets.game_over);
        game_Over.setPosition( (PogsMain.WIDTH-Assets.game_over.getRegionWidth())/2, ( PogsMain.HEIGHT - Assets.game_over.getRegionHeight())/2);
        game_Over.alpha = 0;
        tween = new Tween[]{
        		game_Over.tweenTo( game_Over.x,game_Over.y, 1.0f, 1.0f, game_Over.rotation , 1.0f, Cubic.IN, 1500, 200,0 ),
        		game_Over.tweenTo( game_Over.x, game_Over.y , 1.0f, 1.0f, game_Over.rotation , 0.0f, Cubic.OUT, 1500, 200,0 )};
        game_Over.setTween(tween,manager);
        
        quit_Game = new GameObject(Assets.quit_game);
        quit_Game.setPosition( (PogsMain.WIDTH-Assets.quit_game.getRegionWidth())/2, PogsMain.HEIGHT);
        tween = new Tween[]{
        		quit_Game.tweenTo( quit_Game.x,( PogsMain.HEIGHT - Assets.quit_game.getRegionHeight())/2, 1.0f, 1.0f, quit_Game.rotation , quit_Game.alpha, Bounce.OUT, 2000, 200,0 ),
        		quit_Game.tweenTo( quit_Game.x, PogsMain.HEIGHT , 1.0f, 1.0f, quit_Game.rotation , quit_Game.alpha, Bounce.IN, 2000, 200,0 )};
        quit_Game.setTween(tween,manager);
        
        show_Hint = new GameObject(Assets.show_hint);
        show_Hint.setPosition( (PogsMain.WIDTH-Assets.show_hint.getRegionWidth())/2, PogsMain.HEIGHT);
        tween = new Tween[]{
        		show_Hint.tweenTo( show_Hint.x,( PogsMain.HEIGHT - Assets.show_hint.getRegionHeight())/2, 1.0f, 1.0f, show_Hint.rotation , show_Hint.alpha, Bounce.OUT, 2000, 200,0 ),
        		show_Hint.tweenTo( show_Hint.x, PogsMain.HEIGHT , 1.0f, 1.0f, show_Hint.rotation , show_Hint.alpha, Bounce.IN, 2000, 200,0 )};
        show_Hint.setTween(tween,manager);
        
        homeB = new GameButton(Assets.homeB,Assets.homeB);
		homeB.setPosition( PogsMain.WIDTH - homeB.getWidth(), 0);
		
		hintB = new GameButton(Assets.hintB,Assets.hintB);
		hintB.setPosition(PogsMain.WIDTH/4*2 - (hintB.getWidth()/2), 0);
		
		soundB = new GameButton(Assets.soundB,Assets.sound_offB);
		soundB.setPosition(PogsMain.WIDTH/4*1 - (soundB.getWidth()/2), 0);
		
		yesB = new GameButton(Assets.yesB,Assets.yesB);
		yesB.setPosition(PogsMain.WIDTH/5*2 - (yesB.getWidth()/2), 150);
		
		noB = new GameButton(Assets.noB,Assets.noB);
		noB.setPosition(PogsMain.WIDTH/5*4 - (noB.getWidth()/2), 150);
        
		if(!GameSetting.soundOn)
		{
			soundB.pressed(false);
		}
        initLevel();
	}
	public void draw(SpriteBatch batch)
	{
		drawBackground(batch);
		
		//draw flings on the ground
		for(int i=0;i<flings.size();i++)
		{
			flings.get(i).draw(batch);
		}
		//draw the panel(shows score,time and current level
		panel.draw(batch);
		stageCompleted.draw(batch);
		game_Over.draw(batch);
		
		if(!stageClear && !showHint && !quitGame && !doHint){
			hintB.draw(batch);
			homeB.draw(batch);
			undoB.draw(batch);
			soundB.draw(batch);
		}
		if(quitGame || showHint){
			if(showHint)
				show_Hint.draw(batch);
			if(quitGame)
				quit_Game.draw(batch);
			yesB.draw(batch);
			noB.draw(batch);
		}
		
		//Assets.font.draw(batch,""+savedEntities.size(),100,100);
	}
	public void update(float delta)
	{
		if(!gameOver && !showHint && !quitGame)
			panel.update(delta);
		//check for flings out of the screen, if out' remove
		for(int i=0;i<flings.size();i++)
		{
			Fling fling = flings.get(i);
			if(fling.x < -fling.getWidth() || fling.x > PogsMain.WIDTH
					|| fling.y < -fling.getWidth() || fling.y >PogsMain.HEIGHT)
			{
				flings.remove(fling);
			}
		}
		//do the hint
		if(doHint)
		{
			if(allStop() && hintIndex<hints.size())
			{
				Hint hint = hints.get(hintIndex);
				current = getFling();
				current.state = Fling.ROLLING;
				if(hint.direction.equals("up"))
					current.setDirection(Fling.GO_UP);
				else if(hint.direction.equals("down"))
					current.setDirection(Fling.GO_DOWN);
				else if(hint.direction.equals("left"))
					current.setDirection(Fling.GO_LEFT);
				else if(hint.direction.equals("right"))
					current.setDirection(Fling.GO_RIGHT);
				hintIndex++;
				
			}
		}
		//check for collisions
		if(current!=null)
		{
			for(int i=0;i<flings.size();i++)
			{
				Fling fling = flings.get(i);
				if(current == fling || fling.ghost)
					continue;
				
				if(current.getBounds().overlaps(fling.getBounds()) ){
					if(current.direction == Fling.GO_DOWN && fling.column == current.column ){
						
						if(current.row < fling.row){
							current.setPosition(fling.column*40, PogsMain.HEIGHT - 160 - ((fling.row)-1)*40);
							current.row = fling.row-1;
							current.state = Fling.STOP;
							fling.setDirection(current.direction);
							fling.state = Fling.ROLLING;
							current = fling;
						}
					}
					else if(current.direction == Fling.GO_UP && fling.column == current.column ){
						if(current.row > fling.row){
							current.setPosition(fling.column*40, PogsMain.HEIGHT - 160 - ((fling.row)+1)*40);
							current.row = fling.row+1;
							current.state = Fling.STOP;
							fling.setDirection(current.direction);
							fling.state = Fling.ROLLING;
							current = fling;
						}
					}
					else if(current.direction == Fling.GO_LEFT && fling.row == current.row ){
						if(current.column > fling.column){
							current.setPosition((fling.column+1)*40, PogsMain.HEIGHT - 160 - (fling.row*40));
							current.column = fling.column+1;
							current.state = Fling.STOP;
							fling.setDirection(current.direction);
							fling.state = Fling.ROLLING;
							current = fling;
						}
					}
					else if(current.direction == Fling.GO_RIGHT && fling.row == current.row ){
						if(current.column < fling.column){
							current.setPosition((fling.column-1)*40, PogsMain.HEIGHT - 160 - (fling.row*40));
							current.column = fling.column-1;
							current.state = Fling.STOP;
							fling.setDirection(current.direction);
							fling.state = Fling.ROLLING;
							current = fling;
						}
					}
				}
			}
		}
		//update the flings on the ground
		for(int i=0;i<flings.size();i++)
		{
			flings.get(i).update(delta);
		}
		//check the number of flings
		if(flings.size() == 1 && stageClear == false)
		{
			stageClear = true;
			panel.start = false;
			stageCompleted.activateTween(0);
		}
		//check if the time reaches to 0
		if(panel.time<=0 && !gameOver)
		{
			gameOver = true;
			game_Over.activateTween(0);
		}
		
		if(stageClear)
		{
			time+=delta;
			if(time>=4){
				time = 0;
				stageClear = false;
				GameSetting.level+=1;
				flings.clear();
				initLevel();
				stageCompleted.activateTween(1);
			}
		}
	}
	public void input(float x,float y)
	{
		
	}
	private float ix;
	private float iy;
	public void touchDown (float x, float y) 
	{	
    	Rectangle touch = new Rectangle(x,y,10,10);
    	if(gameOver){
    		game.setDisplay(PogsMain.MAIN_MENU_SCREEN);
    	}
    	if(allStop())
    	{
    		for(int i=0;i<flings.size();i++)
        	{
        		if(touch.overlaps(flings.get(i).getBounds()) && !flings.get(i).ghost){
        			if(flings.get(i).state != Fling.ROLLING)
        			{
        				selected = flings.get(i);
            			ix = selected.x + 20;
            			iy = selected.y - 20;
        			}
        		}
        	}
    	}
    	if(!stageClear)
    	{
    		if(touch.overlaps(undoB.getBounds())){
    			if(savedEntities.size()>1)
    			{
    				flings.clear();
    			
    				flings = savedEntities.get(savedEntities.size()-1);
    				savedEntities.remove(savedEntities.size()-1);
    			}
    		}
    		else if(touch.overlaps(homeB.getBounds()) && !showHint){
    			quitGame = true;
    			quit_Game.activateTween(0);
    		}
    		else if(touch.overlaps(soundB.getBounds())){
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
    		else if(touch.overlaps(hintB.getBounds()) && !quitGame){
    			showHint =true;
    			show_Hint.activateTween(0);
    		}
    	}
    	if(showHint || quitGame){
    		if(touch.overlaps(yesB.getBounds())){
    			if(showHint){
    				showHint=false;
    				show_Hint.activateTween(1);
    				doHint = true;
    				if(savedEntities.size()>1)
    					flings = savedEntities.get(1);
    			}
    			else if(quitGame){
    				GameData.getInstance().save();
    				game.setDisplay(PogsMain.MAIN_MENU_SCREEN);
    			}
    		}
    		else if(touch.overlaps(noB.getBounds())){
    			if(showHint){
    				showHint=false;
    				
    				show_Hint.activateTween(1);
    			}
    			else if(quitGame){
    				quitGame=false;
    				quit_Game.activateTween(1);
    			}
    		}
    	}
	}
	public void touchUp (float x, float y)
	{
    	if(selected!=null)
    	{
    		
        	int dx = (int)Math.abs(x-ix);
        	int dy = (int)Math.abs(y-iy);
        	if(dx>dy){
        		if(selected.x > x){
        			if(checkDirection(Fling.GO_LEFT) >1){
        				selected.setDirection(Fling.GO_LEFT);
        				selected.state = Fling.ROLLING;
        				current = selected;
        				savedEntities.add(cloneEntities());
        			}
        			else if(checkDirection(Fling.GO_LEFT)== -1)
        				createGhostFling(Fling.GO_LEFT);
        			else if(checkDirection(Fling.GO_UP)== 1){
        				selected.setDirection(Fling.GO_LEFT);
        				
        			}
        				
            	}
            	else if(selected.x < x){
            		if(checkDirection(Fling.GO_RIGHT) >1){
            			selected.setDirection(Fling.GO_RIGHT);
                		selected.state = Fling.ROLLING;
                		current = selected;
                		savedEntities.add(cloneEntities());
            		}
            		else if(checkDirection(Fling.GO_RIGHT)== -1)
            			createGhostFling(Fling.GO_RIGHT);
            		else if(checkDirection(Fling.GO_RIGHT)== 1)
        				selected.setDirection(Fling.GO_RIGHT);
            	}
        	}
        	else
        	{
        		if(selected.y > y){
        			if(checkDirection(Fling.GO_DOWN) > 1){
        				selected.setDirection(Fling.GO_DOWN);
                		selected.state = Fling.ROLLING;
                		current = selected;
                		savedEntities.add(cloneEntities());
        			}
        			else if(checkDirection(Fling.GO_DOWN)== -1)
        				createGhostFling(Fling.GO_DOWN);
        			else if(checkDirection(Fling.GO_DOWN)== 1)
        				selected.setDirection(Fling.GO_DOWN);
            	}
            	else if(selected.y < y){
            		if(checkDirection(Fling.GO_UP) > 1){
            			selected.setDirection(Fling.GO_UP);
                		selected.state = Fling.ROLLING;
                		current = selected;
                		savedEntities.add(cloneEntities());
            		}
            		else if(checkDirection(Fling.GO_UP)== -1)
            			createGhostFling(Fling.GO_UP);
            		else if(checkDirection(Fling.GO_UP)== 1)
        				selected.setDirection(Fling.GO_UP);
            	}
        	}
        	selected = null;
    	}
	}
	public void playMusic()
	{
		
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
	public void initLevel()
	{
		//generate the fling according to the level
		this.lv = GameLevel.getLevel(GameSetting.level);
		int delay = 1400;
		for(int i=0;i<GameLevel.MAX_COLUMN;i++)
		{
			for(int j=0;j<GameLevel.MAX_ROW;j++)
			{
				if(lv[i][j] == 1)
				{
					//generate fling(random color,direction);
					TextureRegion[] flingTexture=Assets.green;
					int flingType = (int)(Math.random() * 5);
					switch(flingType)
					{
					case 0:flingTexture = Assets.green;break;
					case 1:flingTexture = Assets.blue;break;
					case 2:flingTexture = Assets.red;break;
					case 3:flingTexture = Assets.orange;break;
					case 4:flingTexture = Assets.yellow;break;
					}
					int direction = (int)(Math.random() * 4);
					Fling fling = new Fling(flingTexture,direction);
					fling.setOrigin(fling.getWidth()/2, fling.getHeight()/2);
					fling.setPosition(i*40 , PogsMain.HEIGHT - 160 - (j * 40) );
					fling.setScale(0, 0);
					fling.column = i;
					fling.row = j;
					Tween[] tween = new Tween[]{fling.tweenTo( fling.x, fling.y, 1.0f, 1.0f, fling.rotation , fling.alpha, Elastic.OUT, 1200, delay,0 )};
			        tween[0].addToManager(manager);
					flings.add(fling);
					delay += 200;
				}
			}
		}
		panel.score=(int)(panel.score+panel.time);
		panel.time=panel.time+30;
		
		panel.start=true;
		
		hints = HintGenerator.getInstance().getHint(GameSetting.level);
		hintIndex = 0;
		doHint = false;
		/*for(int i=0;i<hints.size();i++)
		{
			Gdx.app.log(hints.get(i).col+"", "");
			Gdx.app.log(hints.get(i).row+"", "");
			Gdx.app.log(hints.get(i).direction+"", "");
		}
		*/
		savedEntities.clear();
		savedEntities.add(flings);
		
		LevelModel levelModel = GameData.getInstance().getGameLevel(GameSetting.level);
		levelModel.lock = false;
	}
	private int checkDirection(int direction)
	{
		
		if(direction == Fling.GO_LEFT){
			for(int i=selected.column;i>=0;i--){
				for(int j=0;j<flings.size();j++){
					if(flings.get(j).row == selected.row && flings.get(j).column < selected.column)
						if(!flings.get(j).ghost)
							return selected.column - flings.get(j).column;
				}
			}
		}
		else if(direction == Fling.GO_RIGHT){
			for(int i=selected.column;i<GameLevel.MAX_COLUMN;i++){
				for(int j=0;j<flings.size();j++){
					if(flings.get(j).row == selected.row && flings.get(j).column > selected.column)
						if(!flings.get(j).ghost)
							return flings.get(j).column - selected.column;
				}
			}
		}
		else if(direction == Fling.GO_UP){
			for(int i=selected.row;i>=0;i--){
				for(int j=0;j<flings.size();j++){
					if(flings.get(j).column == selected.column && flings.get(j).row < selected.row)
						if(!flings.get(j).ghost)
							return selected.row - flings.get(j).row;;
					
				}
			}
		}
		else if(direction == Fling.GO_DOWN){
			for(int i=selected.row;i<GameLevel.MAX_ROW;i++){
				for(int j=0;j<flings.size();j++){
					if(flings.get(j).column == selected.column && flings.get(j).row > selected.row)
						if(!flings.get(j).ghost)
							return flings.get(j).row - selected.row;
					
				}
			}
		}
		return -1;
	}
	private void createGhostFling(int direction)
	{
		Fling fling = new Fling(selected.sprites,direction);
		fling.state = Fling.ROLLING;
		fling.setDirection(direction);
		fling.alpha = .5f;
		fling.setOrigin(selected.originX, selected.originY);
		fling.setPosition(selected.x, selected.y);
		fling.setScale(selected.scaleX, selected.scaleY);
		fling.ghost = true;
		flings.add(fling);
	}
	private boolean allStop()
	{
		for(int i=0;i<flings.size();i++)
		{
			if(flings.get(i).state == Fling.ROLLING)
				return false;
		}
		return true;
	}
	public ArrayList<Fling> cloneEntities()
	{
		ArrayList<Fling> temp = new ArrayList<Fling>();
		for(int i=0;i<flings.size();i++)
		{
			Fling f = flings.get(i);
			Fling fling = new Fling(f.sprites,f.direction);
			fling.setOrigin(f.getWidth()/2, f.getHeight()/2);
			fling.setScale(f.scaleX, f.scaleY);
			fling.column = f.column;
			fling.row = f.row;
			fling.setPosition(f.column*40 , PogsMain.HEIGHT - 160 - (f.row * 40) );
			temp.add(fling);
		}
		return temp;
	}
	private Fling getFling()
	{
		Hint hint = hints.get(hintIndex);
		for(int i=0;i<flings.size();i++)
		{
			if(hint.row == flings.get(i).row && hint.col == flings.get(i).column)
				return flings.get(i);
		}
		
		return null;
	}
}
