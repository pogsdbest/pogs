package com.pogs.pogs.util;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.pogs.pogs.display.objects.Fling;
import com.pogs.pogs.display.ui.GameObject;

public class LevelModel extends GameObject {
	
	public int lv;
	public boolean lock;
	public Fling fling;
	
	public LevelModel(int type)
	{
		super(Assets.green[0]);
		TextureRegion[] sprites = Assets.blue;
		switch(type)
		{
		case 0: sprites = Assets.blue;break;
		case 1: sprites = Assets.green;break;
		case 2: sprites = Assets.orange;break;
		case 3: sprites = Assets.red;break;
		}
		fling = new Fling(sprites,Fling.GO_DOWN);
		fling.setOrigin(fling.getWidth()/2, fling.getHeight()/2);
		fling.setScale(1, 1);
		fling.moving = false;
		
	}
	public void draw(SpriteBatch batch)
	{
		fling.draw(batch);
		fling.setPosition(x, y);
		if(lock)
			batch.draw(Assets.lock,x+(fling.getWidth()/2),y-(fling.getHeight()/2));
	}
	public void update(float delta)
	{
		if(!lock){
			fling.state = Fling.ROLLING;
			fling.update(delta);
		}
		
			
	}
}
