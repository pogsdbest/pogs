package com.pogs.pogs.util;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;

public class HintGenerator {

	private static HintGenerator _instance;
	
	public static HintGenerator getInstance()
	{
		if(_instance == null){
			_instance = new HintGenerator();
		}
		
		return _instance;
	}
	public ArrayList<Hint> getHint(int lv)
	{
		ArrayList<Hint> hints = new ArrayList<Hint>();
		String levelHint = Assets.getText("data/hint.txt", "-lv"+lv);
		
		try{
			int count = 0;
			Hint hint = new Hint();
			for(int i=0;i<levelHint.length();i++)
			{
				
				if(levelHint.charAt(i) == '\n'){
					count++;
					if(count>2){
						count = 0;
						hints.add(hint);
						hint = new Hint();
						
					}
					continue;
				}
				if(count == 0){
					hint.col = Integer.parseInt(levelHint.charAt(i)+"");
				}
				else if(count == 1){
					hint.row = Integer.parseInt(levelHint.charAt(i)+"");
				}
				else if(count == 2){
					hint.direction+=levelHint.charAt(i);
				}
			}
		}catch(Exception e){
			Gdx.app.log("HINT GENERATOR ERROR","");
			e.printStackTrace();
		}
		return hints;
	}
}
