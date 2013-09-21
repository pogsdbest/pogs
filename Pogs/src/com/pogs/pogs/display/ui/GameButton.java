package com.pogs.pogs.display.ui;


import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.pogs.pogs.util.Assets;

public class GameButton extends GameObject
{
	protected Sound sound;
	
	private TextureRegion press;
	private TextureRegion unpress;
	public boolean activate = false;	//activate if the this button is pressed
	
	public GameButton(TextureRegion unpress,TextureRegion press)
	{
		super(unpress);
		this.press = press;
		this.unpress = unpress;
	}
	public GameButton(TextureRegion unpress,TextureRegion press,Sound sound)
	{
		this(unpress,press);
		this.sound = sound;
	}
	public void draw(SpriteBatch batch)
	{
		super.draw(batch);
	}
	public void pressed(boolean playSound)
	{
		texture = press;
		activate = true;
		if(playSound && sound != null){
			Assets.playSound(sound);
		}
		
	}
	public void unpressed()
	{
		texture = unpress;
		activate = false;
		
	}
	public void setPressedSound(Sound sound)
	{
		this.sound = sound;
	}
}
