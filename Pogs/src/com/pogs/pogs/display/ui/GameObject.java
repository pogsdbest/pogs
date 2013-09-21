package com.pogs.pogs.display.ui;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquation;
import aurelienribon.tweenengine.TweenManager;
import aurelienribon.tweenengine.Tweenable;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class GameObject implements Tweenable,TweenCallback
{
	public static final int TWEEN_ALL = 1;
	
	protected TweenManager manager;
	
	public Tween[] tween;
	
	public TextureRegion texture;
	
	public float x = 0;

    public float y = 0;
	
	public float alpha = 1.0f;

    public float scaleX = 1.0f;

    public float scaleY = 1.0f;

    public float rotation = 0f;
	
	public float width = 0;
	
	public float height = 0;

	public boolean tweenDone = true;		//done if the tween is finished
	
	public boolean flipX = false;
	
	public boolean flipY = false;
	
	public float originX = 0;
	
	public float originY = 0;
	
	public GameObject(TextureRegion texture)
	{
		this.texture = texture;
		
	}
	
	public int getTweenValues(int tweenType, float[] returnValues ) {
		//Return the number of params that assigned/changed/used
		returnValues[0] = x;
		returnValues[1] = y;
		returnValues[2] = scaleX;
		returnValues[3] = scaleY;
		returnValues[4] = rotation;
		returnValues[5] = alpha;
		
		return 6;
	}
	public void onTweenUpdated(int tweenType, float[] newValues ) {
		x = newValues[0];
	    y = newValues[1];
	    scaleX = newValues[2];
	    scaleY = newValues[3];
	    rotation = newValues[4];
	    alpha = newValues[5];
	}
	public void tweenEventOccured(TweenCallback.Types types,Tween tween)
	{
		tweenDone = true;
	}
	public float getWidth()
	{
		return texture.getRegionWidth();
	}
	public float getHeight()
	{
		return texture.getRegionHeight();
	}
	public float getCurrentWidth()
	{
		return texture.getRegionWidth()*scaleX;
	}
	public float getCurrentHeight()
	{
		return texture.getRegionHeight()*scaleY;
	}
	public Tween tweenTo(float targetX, float targetY, float targetScaleX, float targetScaleY, float targetRotation , float alpha, TweenEquation equation, int duration, int delay,int repeat)
	{
		return Tween.to(this, TWEEN_ALL, duration, equation).target(targetX, targetY, targetScaleX, targetScaleY, targetRotation, alpha).delay(delay).repeat(repeat, 0);
	}
	public Tween tweenFrom(float targetX, float targetY, float targetScaleX, float targetScaleY, float targetRotation , float alpha, TweenEquation equation, int duration, int delay,int repeat)
	{
		return Tween.from(this, TWEEN_ALL, duration, equation).target(targetX, targetY, targetScaleX, targetScaleY, targetRotation, alpha).delay(delay).repeat(repeat, 0);
	}
	public void setTween(Tween[] tween,TweenManager manager)
	{
		this.tween = tween;
		this.manager = manager;
	}
	public void activateTween(int index,int delay)
	{
		Tween tween = this.tween[index];
		delay =  ( delay - tween.getDelay());
		if(manager != null && tween != null){
			tween.addCompleteCallback(this);
			tween.delay(delay).addToManager(manager);
			tweenDone = false;
		}
	}
	public void activateTween(int index)
	{
		Tween tween = this.tween[index];
		activateTween(index,tween.getDelay());
	}
	public void draw(SpriteBatch batch)
	{
		batch.setColor(1, 1, 1, alpha);
		batch.draw(texture.getTexture(), x,  y, originX , originY,  (float)texture.getRegionWidth(),  (float)texture.getRegionHeight(),  scaleX,  scaleY,  rotation,  texture.getRegionX(),  texture.getRegionY(),  texture.getRegionWidth(),  texture.getRegionHeight(), flipX, flipY);
		batch.setColor(1, 1, 1, 1);
	}
	public void setPosition(float x,float y)
	{
		this.x = x;
		this.y = y;
	}
	public void setOrigin(float originX,float originY)
	{
		this.originX = originX;
		this.originY = originY;
	}
	public void setScale(float scaleX,float scaleY)
	{
		this.scaleX = scaleX;
		this.scaleY = scaleY;
	}
	public Rectangle getBounds()
	{
		return new Rectangle(x,y,texture.getRegionWidth(),texture.getRegionHeight());
	}
	
	
}
