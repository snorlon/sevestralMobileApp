package com.snorlon.sevestralkingdoms.Places;

import com.snorlon.sevestralkingdoms.GameTools.Core;
import com.snorlon.sevestralkingdoms.GameTools.InterfaceElement;
import com.snorlon.sevestralkingdoms.GameTools.Renderer;

public class Map
{
	Core coreModule = null;
	
	public Location ourLocation = null;

	public Map(Core gameCore, Location nLocation)
	{
		coreModule = gameCore;
		
		ourLocation = nLocation;
	}
	
	public void Tick(float dt)
	{
	}
	
	public void SpawnBuilding(String buildingType, int nlevel)
	{
		
	}
	
	public void stepTurn()
	{
		
	}

	public void postInit()
	{
		
	}
}

