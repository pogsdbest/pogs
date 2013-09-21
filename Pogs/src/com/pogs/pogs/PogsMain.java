package com.pogs.pogs;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.pogs.pogs.display.screen.GameDisplay;
import com.pogs.pogs.display.screen.MainMenuScreen;
import com.pogs.pogs.display.screen.OnGameScreen;
import com.pogs.pogs.util.Assets;
import com.pogs.pogs.util.GameData;


public class PogsMain implements ApplicationListener{
	
	private GameDisplay screen;
	
	public static final int SPLASH_SCREEN = 1;
	public static final int MAIN_MENU_SCREEN = 2;
	public static final int ON_GAME_SCREEN = 3;
	
	public static final int WIDTH = 320;
	public static final int HEIGHT = 480;
	
	public PogsMain()
	{
		
	}
	public void create ()
	{
		Assets.load();
		setDisplay(MAIN_MENU_SCREEN);
    }
	public void setDisplay(int nextScreen)
	{
		if(screen!=null)
			screen.stopMusic();
		
		switch(nextScreen)
		{
		case MAIN_MENU_SCREEN:
			screen = new MainMenuScreen(this);
			break;
		case ON_GAME_SCREEN:
			screen = new OnGameScreen(this);
			break;
		}
	}
    public void render () {
    	if(screen!=null)
    		screen.render(Gdx.graphics.getDeltaTime());
    }

    public void resize (int width, int height) {
    }

    public void pause () {
    	
    }

    public void resume () {
    }

    public void dispose () {
    	Assets.dispose();
    	screen = null;
    	GameData.getInstance().save();
    }

}
