package com.snorlon.sevestralkingdoms.Places;

import com.snorlon.sevestralkingdoms.GameComponents.Common;
import com.snorlon.sevestralkingdoms.GameComponents.SpawnList;
import com.snorlon.sevestralkingdoms.GameTools.Core;
import com.snorlon.sevestralkingdoms.NPC.Unit;

public class Location {
	
	public String name = "Error";
	public SpawnList spawns = new SpawnList();//per-map spawns
	public String planet = "None";//Should NEVER be none
	
	public int danger_level = 1;//default is 0, for town, runs up to 5
	
	public int level = 0;//level of the map monsters to spawn, default is 1
	
	int MilitaryPower = 0;
	
	//each location should have a map
	private Map ingameMap = null;
	
	Core coreModule = null;
	
	int x = 0;
	int y = 0;
	
	String controllingFaction = Common.FACTION_NONE;
	public boolean hasCreature = false;
	
	//buildings
		//building upgrade levels
	
	public Location(String nname, int nx, int ny, String p)
	{
		name = nname;
		
		planet = p;
		
		x = nx;
		y = ny;
	}
	
	public int getMilitaryPower()
	{
		return MilitaryPower;
	}
	
	public void setMilitaryPower(int nVal)
	{
		MilitaryPower = nVal;
	}
	
	public Map getMap()
	{
		return getIngameMap();
	}
	
	public String getDangerString()
	{
		switch(danger_level)
		{
			case 0:
				return "   Safe    ";
			case 1:
				return "  Normal   ";
			case 2:
				return "Challenging";
			case 3:
				return " Difficult ";
			case 4:
				return " Dangerous ";
			case 5:
				return "   Fatal   ";
			case 6:
				return "Impossible ";
		}
		return "Error";
	}
	public void initialize(Core gameCore, String flag)
	{
		coreModule = gameCore;
		
		if(flag.equals("T"))
		{
			danger_level = 0;
			ingameMap = new Town(gameCore, this);
		}
	}
	
	public int getMaxSpawn()
	{
		int maxNum = 5 + (danger_level-1)*5;
		
		if(maxNum>15)
			maxNum = 15;
		
		return maxNum;
	}
	
	public int getMinSpawn()
	{
		int minNum = 3 + (danger_level-1)*5;
		
		if(minNum<1)
			minNum = 1;
		
		return minNum;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Unit getRandomUnit()
	{
		Unit returnUnit = null;//this will end up being our spawn
		
		if(spawns.spawns.size() > 0)
			returnUnit = spawns.spawns.get(0);
		
		return returnUnit;
	}

	public Map getIngameMap() {
		return ingameMap;
	}

	public String getOwner()
	{
		return controllingFaction;
	}
	
	public void dropDifficulty()
	{
		//force a difficulty drop, good for when winning a battle
		if(danger_level > 1)
		{
			danger_level--;
		}
	}
	
	public void stepTurn()
	{
		if(ingameMap!= null)
		{
			ingameMap.stepTurn();
		}
		
		//small chance to get harder here
		if(danger_level > 0)
		{
			//3% chance to get harder
			if(coreModule.random.nextInt(100) < 3)
			{
				danger_level++;
				if(danger_level>5)
					danger_level = 5;
			}
			//0.5% chance to get easier
			if(coreModule.random.nextInt(200) < 1)
			{
				danger_level--;
				if(danger_level<1)
					danger_level = 1;
			}
		}
	}

	public void setOwner(String nowner)
	{
		controllingFaction = nowner;
	}

	public int compareTo(Location l2) {
		float compareQuantity = l2.level; 		
		float ourLevel = level;
 
		//ascending order
		return (int) (compareQuantity - ourLevel);
	}
}
