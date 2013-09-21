package com.pogs.pogs.util;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets 
{
	public static Texture t1;
	public static TextureRegion tile1;
	public static TextureRegion tile2;
	public static TextureRegion title;
	public static TextureRegion startB;
	public static TextureRegion panel;
	public static TextureRegion grass;
	public static TextureRegion gradient;
	public static TextureRegion clouds;
	public static TextureRegion[] green;
	public static TextureRegion[] blue;
	public static TextureRegion[] red;
	public static TextureRegion[] yellow;
	public static TextureRegion[] orange;
	public static TextureRegion undoB;
	public static TextureRegion aboutB;
	public static TextureRegion helpB;
	public static TextureRegion exitB;
	public static TextureRegion game_over;
	public static TextureRegion stage_completed;
	public static TextureRegion quit_game;
	public static TextureRegion show_hint;
	public static TextureRegion yesB;
	public static TextureRegion noB;
	public static TextureRegion homeB;
	public static TextureRegion soundB;
	public static TextureRegion sound_offB;
	public static TextureRegion hintB;
	public static TextureRegion about;
	public static TextureRegion lock;
	
	public static BitmapFont font;
	public static Music currentMusic;
	
	public static Sound press_sound;
	public static Sound boot_sound;
	public static Music bgm;
	
	public static void load()
	{
		t1 = load("gfx/t1.png");
		tile1 = new TextureRegion(t1,0,91,40,40);
		tile2 = new TextureRegion(t1,40,91,40,40);
		tile1 = new TextureRegion(t1,0,91,40,40);
		startB = new TextureRegion(t1,12,179,147,47);
		panel = new TextureRegion(t1,0,0,316,89);
		title = new TextureRegion(t1,91,91,204,84);
		green = new TextureRegion[]{new TextureRegion(t1,0,245,40,40),new TextureRegion(t1,40,245,40,40),
				new TextureRegion(t1,80,245,40,40),new TextureRegion(t1,120,245,40,40),new TextureRegion(t1,160,245,40,40)};
		blue = new TextureRegion[]{new TextureRegion(t1,0,290,40,40),new TextureRegion(t1,40,290,40,40),
				new TextureRegion(t1,80,290,40,40),new TextureRegion(t1,120,290,40,40),new TextureRegion(t1,160,290,40,40)};
		red = new TextureRegion[]{new TextureRegion(t1,0,380,40,40),new TextureRegion(t1,40,380,40,40),
				new TextureRegion(t1,80,380,40,40),new TextureRegion(t1,120,380,40,40),new TextureRegion(t1,160,380,40,40)};
		yellow = new TextureRegion[]{new TextureRegion(t1,0,425,40,40),new TextureRegion(t1,40,425,40,40),
				new TextureRegion(t1,80,425,40,40),new TextureRegion(t1,120,425,40,40),new TextureRegion(t1,160,425,40,40)};
		orange = new TextureRegion[]{new TextureRegion(t1,0,335,40,40),new TextureRegion(t1,40,335,40,40),
				new TextureRegion(t1,80,335,40,40),new TextureRegion(t1,120,335,40,40),new TextureRegion(t1,160,335,40,40)};
		grass = new TextureRegion(t1,320,0,320,234);
		grass = new TextureRegion(t1,320,0,320,234);
		clouds = new TextureRegion(t1,650,0,307,174);
		gradient = new TextureRegion(t1,650,200,320,350);
		undoB = new TextureRegion(t1,241,245,46,41);
		aboutB = new TextureRegion(t1,221,300,147,47);
		helpB = new TextureRegion(t1,221,354,147,47);
		exitB = new TextureRegion(t1,221,408,147,47);
		game_over = new TextureRegion(t1,40,478,164,117);
		stage_completed = new TextureRegion(t1,10,597,251,103);
		quit_game = new TextureRegion(t1,31,709,222,44);
		show_hint = new TextureRegion(t1,31,801,222,44);
		yesB = new TextureRegion(t1,40,758,92,36);
		noB = new TextureRegion(t1,166,758,92,36);
		soundB = new TextureRegion(t1,236,485,36,31);
		sound_offB = new TextureRegion(t1,282,483,35,34);
		homeB = new TextureRegion(t1,325,483,47,31);
		hintB = new TextureRegion(t1,234,530,36,33);
		about = new TextureRegion(t1,375,243,268,305);
		lock = new TextureRegion(t1,282,527,32,39);
		
		press_sound = Gdx.audio.newSound(Gdx.files.internal("sfx/press.wav"));
		boot_sound = Gdx.audio.newSound(Gdx.files.internal("sfx/boot.mp3"));
		bgm = Gdx.audio.newMusic(Gdx.files.internal("sfx/bgm.mp3"));
        font = new BitmapFont(Gdx.files.internal("data/font.fnt"), Gdx.files.internal("data/font.png"), false);
	}
	private static Texture load(String file)
	{
		return new Texture(Gdx.files.internal(file));
	}
	public static String getText(String fileName , String index)
	{
		String text = "";
		
		FileHandle handle = Gdx.files.internal(fileName);
		text = handle.readString();
		
		String part="";
		ArrayList<String> stringArray = new ArrayList<String>();
		for(int i=0;i<text.length();i++){
			
			if(text.charAt(i)!= '\n'){
				part+=text.charAt(i);
				
			}
			else{
				stringArray.add(new String(part.substring(0,part.length()-1)));
				part = "";
			}
			if(i>= text.length()-1){
				stringArray.add(new String(part));
			}
		}
		boolean start = false;
		part = "";
		for(int i=0;i<stringArray.size();i++){
			if(start && stringArray.get(i).equals("#end")){
				break;
			}
			if(start){
				part += stringArray.get(i)+"\n";
			}
			if(stringArray.get(i).equals(index)){
				start = true;
			}			
		}
		return part;
	}
	public static void playSound(Sound sound)
	{
		if(GameSetting.soundOn)
			sound.play(1);
	}
	public static void playMusic(Music music)
	{
		if(currentMusic!=null){
			currentMusic.stop();
			currentMusic=null;
		}
		if(GameSetting.soundOn)
		{
			if(!music.isPlaying())
				music.play();
			currentMusic = music;
		}	
	}
	public static void pauseMusic(Music music)
	{
		if(music.isPlaying())
			music.pause();
	}
	public static void stopMusic(Music music)
	{
		if(music.isPlaying())
			music.stop();
	}
	public static void dispose()
    {
		if(currentMusic!=null)
			currentMusic.stop();
		
		t1.dispose();
		bgm.dispose();
    }
}
