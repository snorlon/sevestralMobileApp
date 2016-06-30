package com.snorlon.sevestralkingdoms.Housing;

import java.util.ArrayList;

import com.snorlon.sevestralkingdoms.GameTools.Core;

public class House
{
	public static final int ROTATION_0 = 0;
	public static final int ROTATION_90 = 1;
	public static final int ROTATION_180 = 2;
	public static final int ROTATION_270 = 3;

	int maxFloorNum = 0;
	
	final int maxFloors = 10;
	
	
	House_Template ourTemplate = null;
	
	//our neighbor floors
	Floor baseFloor = null;

	public House(Core coreModule, ArrayList<House_Template> houseTemplates, int id)
	{
		//using the initial template list, pick out the base floor
		//load in the base floor, make it our default floor
		for(House_Template temp : houseTemplates)
		{
			if(temp.getID() == id)
			{
				for(int i=0; i<temp.getFloorCount(); i++)
				{
					Floor f = new Floor(coreModule, temp);
					System.out.println("Attempting to create floor "+(i+1)+" "+f);
					if(baseFloor==null)
					{
						baseFloor = f;
						maxFloorNum = 1;
					}
					else
					{
						baseFloor.addFloor(f);
						maxFloorNum++;
					}
				}
				
				ourTemplate = temp;
				break;
			}
		}
	}
	
	public House(Core coreModule, ArrayList<House_Template> houseTemplates, String designName)
	{
		//using the initial template list, pick out the base floor
		//load in the base floor, make it our default floor
		for(House_Template temp : houseTemplates)
		{
			if(temp.name.equals(designName))
			{
				for(int i=0; i<temp.getFloorCount(); i++)
				{
					Floor f = new Floor(coreModule, temp);
					if(baseFloor==null)
					{
						baseFloor = f;
						maxFloorNum = 1;
					}
					else
					{
						baseFloor.addFloor(f);
						maxFloorNum++;
					}
				}
				break;
			}
		}
	}
	
	public void dispose()
	{
		if(baseFloor!=null)
		{
			baseFloor.dispose();
		}
	}
	
	public int getFloorCount()
	{
		return maxFloorNum;
	}
	
	public String getDesign()
	{
		if(baseFloor!=null && baseFloor.getTemplate() != null)
		{
			return baseFloor.getTemplate().name;
		}
		
		return "Error";
	}
	
	public Floor getFloor(int index)
	{
		if(index > maxFloorNum || index < 1)
		{
			System.out.println("Floor "+index+" does not exist");
			return null;
		}
		
		return baseFloor.getFloor(index);
	}
	
	public int getHeight()
	{
		if(ourTemplate!=null)
		{
			return ourTemplate.getHeight();
		}
		return 1;
	}
	
	public int getWidth()
	{
		if(ourTemplate!=null)
		{
			return ourTemplate.getWidth();
		}
		return 1;
	}
}
