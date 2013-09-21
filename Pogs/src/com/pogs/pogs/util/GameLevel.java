package com.pogs.pogs.util;

import com.badlogic.gdx.Gdx;

public class GameLevel
{
	public static final int MAX_COLUMN = 8;
	public static final int MAX_ROW = 8;
	
	
	public static int[][] getLevel(int lv)
	{
		String level = Assets.getText("data/levels.txt", "-lv"+lv);
		int[][] levelStructure = new int[MAX_COLUMN][MAX_ROW];
		int i=0;
		int j=0;
		int index=0;
		while(i<level.length())
		{
			if(level.charAt(i) != '\n')
			{
				levelStructure[index%8][j] = Integer.parseInt(level.charAt(i)+"");
				index+=1;
			}
			else if(level.charAt(i) == '\n'){
				j+=1;
			}
			i++;
		}
		
		return levelStructure;
	}
}
