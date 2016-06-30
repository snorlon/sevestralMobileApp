package com.snorlon.sevestralkingdoms.Buildings;

import com.snorlon.sevestralkingdoms.GameTools.Core;
import com.snorlon.sevestralkingdoms.GameTools.InterfaceElement;
import com.snorlon.sevestralkingdoms.Items.Shop;
import com.snorlon.sevestralkingdoms.Places.Town;

public class BuildingBase
{
	int level = 1;//start out at level 1, maxed at 10
	public int sign_offset = 0;
	
	Core coreModule;
	
	InterfaceElement buildingImage = null;
	InterfaceElement link = null;
	
	Town associatedTown = null;
	
	String name = "Empty Plot";
	
	public BuildingBase(Core gameCore, Town nTown, int nlevel)
	{
		coreModule = gameCore;
		
		associatedTown = nTown;

		level = nlevel;
	}
	
	public void initialize()
	{
		
	}
	
	public int getLevel()
	{
		return level;
	}
	
	public InterfaceElement GenerateImage(String containerName, String id, float scale)
	{

		
		return buildingImage;
	}
	
	public void GenerateLink(String containerName, String id)
	{
		
	}
	
	public void GenerateLink()
	{
		
	}
	
	public void DestroyImage()
	{
		buildingImage = null;
		link = null;
	}
	
	public void Enter()
	{
		
	}
	
	public void Exit()
	{
		
		
	}
	
	public void Tick(float dt)
	{
	}
	
	public String getName()
	{
		return name;
	}
	
	public void stepTurn()
	{
		
	}

	public Shop getShop() {
		// TODO Auto-generated method stub
		return null;
	}
}
