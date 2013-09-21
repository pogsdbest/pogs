package com.pogs.pogs.display.screen;

import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.pogs.pogs.PogsMain;

public class GameDisplay implements Screen,InputProcessor
{
	protected PogsMain game;
	private SpriteBatch batch;
	protected TweenManager manager;
	private boolean playMusic = false;
	private OrthographicCamera camera;
	
	public GameDisplay(PogsMain game){
		this.game = game;
		batch = new SpriteBatch();
		manager = new TweenManager();
		camera = new OrthographicCamera(PogsMain.WIDTH, PogsMain.HEIGHT);
		camera.position.set(PogsMain.WIDTH/2,PogsMain.HEIGHT/2,0);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		
		Gdx.input.setInputProcessor(this);
	}
	public void render(float delta)
	{
		GL10 gl = Gdx.gl10;
        gl.glClearColor(1, 1, 1, 1);
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        
        batch.begin();
        draw(batch);
        batch.end();
        manager.update();
        
        update(delta);
        checkInput();
        
        if(!playMusic){
        	playMusic();
        	playMusic = true;
        }
	}
	private void checkInput()
	{
		float x = ( (float)Gdx.input.getX() / (float)Gdx.graphics.getWidth() ) * PogsMain.WIDTH;
    	float y =  game.HEIGHT  - ( (float)Gdx.input.getY() / (float)Gdx.graphics.getHeight() ) * PogsMain.HEIGHT;
    	input(x,y);
	}
	public void draw(SpriteBatch batch)
	{
		
	}
	public void update(float delta)
	{
		
	}
	public void input(float x,float y)
	{
		
	}
	public boolean keyDown (int keycode) {
		
        return false;
	}
	public boolean keyUp (int keycode) {
        return false;
	}
	public boolean keyTyped (char character) {
        return false;
	}
	public boolean touchDown (int x, int y, int pointer, int button) {
		float ix = ( (float)x / (float)Gdx.graphics.getWidth() ) * PogsMain.WIDTH;
    	float iy =  PogsMain.HEIGHT  - ( (float)y / (float)Gdx.graphics.getHeight() ) * PogsMain.HEIGHT;
		touchDown(ix,iy);
        return false;
	}
	public void touchDown(float x,float y){}
	public boolean touchUp (int x, int y, int pointer, int button) {
		float ix = ( (float)x / (float)Gdx.graphics.getWidth() ) * PogsMain.WIDTH;
    	float iy =  PogsMain.HEIGHT  - ( (float)y / (float)Gdx.graphics.getHeight() ) * PogsMain.HEIGHT;
		touchUp(ix,iy);
        return false;
	}
	public void touchUp(float x,float y){}
	public boolean touchDragged (int x, int y, int pointer) {
        return false;
	}
	public boolean touchMoved (int x, int y) {
        return false;
	}
	public boolean scrolled (int amount) {
        return false;
	}
	public void playMusic()
	{
		
	}
	public void stopMusic()
	{
		
	}
	public void pause(){}
    public void resume (){}
    public void dispose (){}
    public void resize(int w,int h){}
    public void show(){}
    public void hide(){}
	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		
		return false;
	}
}
