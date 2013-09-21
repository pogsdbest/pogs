package com.pogs.pogs.display.ui;

import java.text.DecimalFormat;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;
import aurelienribon.tweenengine.equations.Elastic;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.pogs.pogs.PogsMain;
import com.pogs.pogs.util.Assets;
import com.pogs.pogs.util.GameSetting;

public class GamePanel {
	
	private GameObject panel;
	private BitmapFont font;
	
	public int score;
	public float time;
	public boolean start =false;
	
	public GamePanel(TweenManager manager)
	{
		font = Assets.font;
		
		panel = new GameObject(Assets.panel);
        panel.setPosition( (PogsMain.WIDTH-Assets.panel.getRegionWidth())/2, PogsMain.HEIGHT);
        Tween[] tween = new Tween[]{panel.tweenTo( panel.x, PogsMain.HEIGHT - Assets.panel.getRegionHeight() - 15, 1.0f, 1.0f, panel.rotation , panel.alpha, Elastic.OUT, 1200, 200,0 )};
        tween[0].addToManager(manager);
        
        score = 0;
        time = 0;
	}
	public void draw(SpriteBatch batch)
	{
		panel.draw(batch);
		
		float x = panel.x;
		float y = panel.y+panel.getHeight();
		font.draw(batch, "Level ", x+15, y-15);
		font.draw(batch, "Score ", x+115, y-15);
		font.draw(batch, "Time ", x+215, y-15);
		
		font.draw(batch, ""+GameSetting.level, x+25, y-35);
		font.draw(batch, ""+score, x+125, y-35);
		
		DecimalFormat format = new DecimalFormat("##.##");
		font.draw(batch, ""+format.format(time), x+225, y-35);
	}
	public void update(float delta)
	{
		if(start)
			time-=delta;
		
	}
}
