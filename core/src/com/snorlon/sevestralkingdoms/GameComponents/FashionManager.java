package com.snorlon.sevestralkingdoms.GameComponents;

import java.util.ArrayList;
import java.util.Hashtable;

import com.snorlon.sevestralkingdoms.GameTools.Core;

public class FashionManager
{
	//manually add new clothing here!
	
	Core coreModule = null;
	
	//every in-game outfit that isn't associated with a faction goes here!
	ArrayList<String> globalOutfits = new ArrayList<String>();
	
	//unused, basically 50% chance when spawning units to spawn one with this outfit
	Hashtable<String, ArrayList<String> > factionOutfits = new Hashtable<String, ArrayList<String>>();


	public String faces[] = {"neutral blue.png","neutral red.png"};

	public ArrayList<String> girlhair = new ArrayList<String>();
	public ArrayList<String> guyhair = new ArrayList<String>();
	
	public FashionManager()
	{
		//add clothing to the lists
		globalOutfits.add("platemail.png");
		globalOutfits.add("generic leather.png");
		
		//add hair
		girlhair.add("blonde swirly.png");
		girlhair.add("red swirly.png");
		guyhair.add("brown calm.png");
		guyhair.add("red calm.png");
		
		//DaiminaOrder
		ArrayList<String> newFaction = new ArrayList<String>();
		newFaction.add("Daimina Order");
		
		factionOutfits.put("DaiminaOrder.png", newFaction);
		globalOutfits.add("DaiminaOrder.png");
		
	}
	
	public void init(Core coreManager)
	{
		coreModule = coreManager;
	}
	
	public String getOutfit(Core coreModule, String faction)
	{
		int odds = 75;
		
		if(!factionOutfits.containsKey(faction))
		{
			odds = -1;
		}
		
		int roll = coreModule.random.nextInt(100);
		
		if(faction.equals("Random"))
		{
			roll = 101;
		}
		
		if(roll > odds)
		{
			//generate a random global outfit
			return globalOutfits.get(coreModule.random.nextInt(globalOutfits.size()));
		}
		else
		{
			ArrayList<String> factionList = factionOutfits.get(faction);
			
			//generate a random global outfit
			return factionList.get(coreModule.random.nextInt(factionList.size()));
		}
	}
	
	public String getHair(boolean isMale)
	{
		if(isMale)
			return guyhair.get(coreModule.random.nextInt(guyhair.size()));
		else
			return girlhair.get(coreModule.random.nextInt(girlhair.size()));
	}
	
	public String getFace()
	{
		return faces[coreModule.random.nextInt(faces.length)];
	}
}
