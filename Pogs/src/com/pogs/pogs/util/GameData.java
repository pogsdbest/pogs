package com.pogs.pogs.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;

public class GameData {

	public static final String FILE_NAME = ".fling";
	public boolean sounds;
	public ArrayList<LevelModel> gameLevels;
	
	public static GameData _instance;
	
	public static GameData getInstance()
	{
		if(_instance==null)
			_instance = new GameData();
		return _instance;
	}
	private GameData()
	{
		gameLevels = new ArrayList<LevelModel>();
	}
	public void load()
	{
		gameLevels = new ArrayList<LevelModel>();
		BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(Gdx.files.external(FILE_NAME).read()));
            GameSetting.soundOn = Boolean.parseBoolean(in.readLine());
            
            for(int i=0;i<12;i++)
    		{
    			LevelModel lv = new LevelModel(i/3);
    			lv.lv = Integer.parseInt(in.readLine());
    			lv.lock = Boolean.parseBoolean(in.readLine());
    			gameLevels.add(lv);
    		}
        } catch (Throwable e) {
            Gdx.app.log("ERROR", "ERROR weak");
            for(int i=0;i<12;i++)
    		{
            	LevelModel lv = new LevelModel(i/3);
    			lv.lv= i+1;
    			
    			if(i==0) lv.lock = false;
    			else lv.lock = true;
    			gameLevels.add(lv);
    		}
        } finally {
            try {
                if (in != null)
                    in.close();
            } catch (IOException e) {
            }
        }
		
	}
	public void save()
	{
		BufferedWriter out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(Gdx.files.external(FILE_NAME).write(false)));
            out.write(""+GameSetting.soundOn);
            out.newLine();
            
            for(int i=0;i<gameLevels.size();i++)
            {
            	LevelModel lv = gameLevels.get(i);
            	out.write(""+lv.lv);
            	out.newLine();
            	out.write(""+lv.lock);
            	out.newLine();
            }
            
            out.close();

        } catch (Throwable e) {
        	Gdx.app.log("ERROR", "ERROR no directory");
        	e.printStackTrace();
        } finally {
            try {
                if (out != null)
                    out.close();
            } catch (IOException e) {
            }
        }
	}
	public LevelModel getGameLevel(int lv)
	{
		return gameLevels.get(lv-1);
	}
}
