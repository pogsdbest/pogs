package com.pogs.pogs.display.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.pogs.pogs.display.ui.GameObject;
import com.pogs.pogs.util.Assets;

public class Fling extends GameObject
{
	public static final int GO_LEFT = 0;
	public static final int GO_RIGHT = 1;
	public static final int GO_DOWN = 2;
	public static final int GO_UP = 3;
	
	public static int STOP = 0;
	public static int ROLLING = 1;
	
	public int state;
	public int direction;
	public boolean ghost;
	public boolean moving = false;
	
	protected int index;
	protected float time;
	public TextureRegion[] sprites;
	protected int[] sequence;
	protected boolean playSound;
	public int row;
	public int column;
	
	public Fling(TextureRegion[] sprites,int direction)
	{
		super(sprites[0]);
		this.sprites = sprites;
		
		state = STOP;
		index = 0;
		sequence = new int[]{0,1,2,2,2,3,4};
		setDirection(direction);
		playSound = false;
		ghost = false;
		moving = true;
	}
	public void draw(SpriteBatch batch)
	{
		//Assets.font.draw(batch, "c"+column+"r"+row, x, y);
		super.draw(batch);
	}
	public void update(float delta)
	{
		if(state == ROLLING)
		{
			time += delta;
			if(time >= .100)
			{
				index+=1;
				time = 0;
				if(index>=sequence.length)
					index = 0;
			}
			if(moving){
				if(direction == GO_LEFT) x -= (delta * 200 * 1000 )/1000;
				else if(direction == GO_RIGHT) x += (delta * 200 * 1000 )/1000;
				else if(direction == GO_DOWN) y -= (delta * 200 * 1000 )/1000;
				else if(direction == GO_UP) y += (delta * 200 * 1000 )/1000;
			}
			
		}
		else if(state == STOP)
		{
			index = 0;
		}
		if(!playSound && scaleX > 0){
			Assets.playSound(Assets.boot_sound);
			playSound = true;
		}
		texture = sprites[sequence[index]];
	}
	public void setDirection(int direction)
	{
		this.direction = direction;
		switch(direction)
		{
		case GO_DOWN:
			rotation = 90;
			break;
		case GO_RIGHT:
			rotation = 180;
			break;
		case GO_UP:
			rotation = 270;
			break;
		case GO_LEFT:
			rotation = 0;
			break;
		}
	}
	
}
