package com.snorlon.sevestralkingdoms.GameTools;

import java.io.PrintWriter;
import java.util.Hashtable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.snorlon.sevestralkingdoms.DataManagement.LoadSaveManager;

public class SoundManager {
	Core coreModule = null;

	Hashtable<String, Sound > soundDatabase = new Hashtable<String, Sound>();
	Hashtable<String, Music > musicDatabase = new Hashtable<String, Music>();
	
	SoundManager(Core newCore)
	{
		coreModule = newCore;
		
		//load in our sound files
		loadSounds();
		loadMusic();
	}
	
	public void loadSounds()
	{
		//loads sounds from soundlist
		FileHandle handle = Gdx.files.internal("sounds/soundlist.txt");
		
		if(!handle.exists())
		{
			System.out.println("Sound file not found!");
		}
		
		String r = handle.readString();
		//System.out.println(r);
		int startIndex = 0;
		int endIndex = 0;
		
		for(int stringIndex = 0; stringIndex < r.length(); stringIndex++)
		{
			if(r.charAt(stringIndex) == '\n' || stringIndex + 1 >= r.length())
			{
				endIndex = stringIndex;
				
				if(endIndex - startIndex <= 0)
					continue;
				
				String partString = r.substring(startIndex , endIndex).trim();
				
				if(partString.length() <= 0)
					continue;
				
				startIndex = stringIndex;

				//truncate up to the first non tab
				String parts[] = (partString).split("\t");
				
				loadSound(parts[1],parts[0]);
			}
		}
	}
	
	public void loadMusic()
	{
		//loads sounds from soundlist
		FileHandle handle = Gdx.files.internal("sounds/musiclist.txt");
		
		if(!handle.exists())
		{
			System.out.println("Music file not found!");
		}
		
		String r = handle.readString();
		//System.out.println(r);
		int startIndex = 0;
		int endIndex = 0;
		
		for(int stringIndex = 0; stringIndex < r.length(); stringIndex++)
		{
			if(r.charAt(stringIndex) == '\n' || stringIndex + 1 >= r.length())
			{
				endIndex = stringIndex;
				
				if(endIndex - startIndex <= 0)
					continue;
				
				String partString = r.substring(startIndex , endIndex).trim();
				
				if(partString.length() <= 0)
					continue;
				
				startIndex = stringIndex;

				//truncate up to the first non tab
				String parts[] = (partString).split("\t");
				
				loadMusic(parts[1],parts[0]);
			}
		}
	}
	
	public void loadSound(String soundname, String filen)
	{
		if(Gdx.files.internal("sounds/"+filen)!=null)
		{
			Sound nsound = Gdx.audio.newSound(Gdx.files.internal("sounds/"+filen));
			soundDatabase.put(soundname, nsound);
		}
	}
	
	public void loadMusic(String soundname, String filen)
	{
		if(Gdx.files.internal("sounds/music/"+filen)!=null)
		{
			Music nsound = Gdx.audio.newMusic(Gdx.files.internal("sounds/music/"+filen));
			musicDatabase.put(soundname, nsound);
			
			System.out.println("Loaded music track "+soundname);
		}
	}
	
	public void playSound(String soundID)
	{
		playSound(soundID, 1.0f);
	}
	
	public void playSound(String soundID, float volumeMultiplier)
	{
		//play back the sound with the given volume ONCE
		if(soundDatabase.containsKey(soundID))
		{
			Sound s = soundDatabase.get(soundID);
			float volume = volumeMultiplier * coreModule.gameModule.gameConfig.getSFXVolume()/100.0f;
			
			if(volume > 1.0f)
				volume = 1.0f;
			if(volume < 0.0f)
				volume = 0.0f;
			
			s.play(volume);
		}
	}
	
	public void playMusic(String soundID)
	{
		playMusic(soundID, 1.0f);
	}
	
	public void playMusic(String soundID, float volumeMultiplier)
	{
		//abort if the song we want is already playing
		if(musicDatabase.containsKey(soundID))
		{
			Music m = musicDatabase.get(soundID);
			
			if(m.isPlaying())
				return;
		}
		
		disableMusic();
		
		//play back the sound with the given volume ONCE
		if(musicDatabase.containsKey(soundID))
		{
			Music m = musicDatabase.get(soundID);
			
			float volume = volumeMultiplier * coreModule.gameModule.gameConfig.getBGMVolume()/100.0f;
			
			if(volume > 1.0f)
				volume = 1.0f;
			if(volume < 0.0f)
				volume = 0.0f;
			
			m.setLooping(true);
			
			m.play();
			
			m.setVolume(volume);
			
			System.out.println("Started playing song "+soundID+"!");
		}
		else
		{
			System.out.println("Song "+soundID+" not found!");
		}
	}
	
	public void updateBGMVolume(float newVolume)
	{
		for(String key: musicDatabase.keySet())
		{
			musicDatabase.get(key).setVolume(newVolume);
		}
	}
	
	public void disableSounds()
	{
		//stops playing ALL sounds
		for(String key: soundDatabase.keySet())
		{
			soundDatabase.get(key).stop();
		}
	}
	
	public void disableMusic()
	{
		//stops playing ALL sounds
		for(String key: musicDatabase.keySet())
		{
			musicDatabase.get(key).stop();
		}
		
		System.out.println("All music disabled!");
	}
	
	public void tick(float dt)
	{
		//no use for this currently
	}
	
	public void dispose()
	{
		disableSounds();
		disableMusic();
		
		//make sure to dispose of all sounds
		for(String key: soundDatabase.keySet())
		{
			soundDatabase.get(key).dispose();
		}
		
		for(String key: musicDatabase.keySet())
		{
			musicDatabase.get(key).dispose();
		}
	}
}
