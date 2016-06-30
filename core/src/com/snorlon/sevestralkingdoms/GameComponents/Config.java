package com.snorlon.sevestralkingdoms.GameComponents;

import com.snorlon.sevestralkingdoms.GameTools.Core;
import com.snorlon.sevestralkingdoms.GameTools.InterfaceElement;

//holds basic game config info
public class Config
{
	public final static int DIFFICULTY_EASY = 0;
	public final static int DIFFICULTY_MEDIUM = 1;
	public final static int DIFFICULTY_HARD = 2;
	public final static int DIFFICULTY_VERY_HARD = 3;
	public final static int DIFFICULTY_GODLIKE = 4;
	public final static int DIFFICULTY_IMPOSSIBLE = 5;
	
	int bgmVolume = 70;//scale of 1 to 100
	int sfxVolume = 70;//scale of 1 to 100
	int volume = 100;//scale of 1 to 100, serves as upper bound
	
	boolean showFPS = false;
	
	String backgroundImage = "bricks.png";
	
	Core coreModule = null;
	
	int gameDifficulty = DIFFICULTY_MEDIUM;
	
	boolean isDebug = true;//disable this when releasing to avoid costly startups!
	
	Config(Core newCore)
	{
		coreModule = newCore;
	}
	
	public double getLevelMultiplier()
	{
		switch(gameDifficulty)
		{
			case DIFFICULTY_EASY:
				return 0.5;
			case DIFFICULTY_MEDIUM:
				return 1.0;
			case DIFFICULTY_HARD:
				return 1.25;
			case DIFFICULTY_VERY_HARD:
				return 2;
			case DIFFICULTY_GODLIKE:
				return 2.5;
			case DIFFICULTY_IMPOSSIBLE:
				return 4;
		}
		
		return 1.0;//default
	}
	
	public boolean isDebugging()
	{
		return isDebug;
	}
	
	public int getSFXVolume()
	{
		return Math.min(sfxVolume, volume);
	}
	
	public int getBGMVolume()
	{
		return Math.min(bgmVolume, volume);
	}
	
	public void SFXUp()
	{
		sfxVolume+=10;
		if(sfxVolume > 100)
			sfxVolume = 100;
	}
	
	public void SFXDown()
	{
		sfxVolume-=10;
		if(sfxVolume < 0)
			sfxVolume = 0;
	}
	
	public void BGMUp()
	{
		bgmVolume+=10;
		if(bgmVolume > 100)
			bgmVolume = 100;
		
		//update all music volume
		float volume = bgmVolume/100.0f;
		
		coreModule.soundModule.updateBGMVolume(volume);
	}
	
	public void BGMDown()
	{
		bgmVolume-=10;
		if(bgmVolume < 0)
			bgmVolume = 0;
		
		//update all music volume
		float volume = bgmVolume/100.0f;
		
		coreModule.soundModule.updateBGMVolume(volume);
	}
	
	public void enableFPS()
	{
		showFPS = true;
	}
	
	public void disableFPS()
	{
		showFPS = false;
	}
	
	public void processFPS(InterfaceElement fpsElement)
	{
		if(fpsElement!=null)
		{
			if(showFPS)
			{
				fpsElement.show();
			}
			else
				fpsElement.hide();
		}
	}
	
	public int getFPSFrameOn()
	{
		if(!showFPS)
			return 1;
		
		else
			return 0;
	}
	
	public int getFPSFrameOff()
	{
		if(showFPS)
			return 1;
		
		else
			return 0;
	}

	public void setSFX(int newSFX)
	{
		sfxVolume = newSFX;
		
		if(sfxVolume > 100)
			sfxVolume = 100;
		
		if(sfxVolume < 0)
			sfxVolume = 0;
	}

	public void setBGM(int newBGM)
	{
		bgmVolume = newBGM;
		
		if(bgmVolume > 100)
			bgmVolume = 100;
		
		if(bgmVolume < 0)
			bgmVolume = 0;
	}
}
