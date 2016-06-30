package com.snorlon.sevestralkingdoms.Places;

import java.util.ArrayList;
import java.util.List;

import com.snorlon.sevestralkingdoms.Buildings.Blacksmith;
import com.snorlon.sevestralkingdoms.Buildings.BuildingBase;
import com.snorlon.sevestralkingdoms.Buildings.GeneralStore;
import com.snorlon.sevestralkingdoms.Buildings.GuardTower;
import com.snorlon.sevestralkingdoms.Buildings.Guild;
import com.snorlon.sevestralkingdoms.Buildings.Hospital;
import com.snorlon.sevestralkingdoms.Buildings.HouseBuilding;
import com.snorlon.sevestralkingdoms.Buildings.Inn;
import com.snorlon.sevestralkingdoms.Buildings.Market;
import com.snorlon.sevestralkingdoms.Buildings.Spellshop;
import com.snorlon.sevestralkingdoms.GameComponents.Common;
import com.snorlon.sevestralkingdoms.GameTools.Core;
import com.snorlon.sevestralkingdoms.Items.Bag;

public class Town extends Map
{
	public static int buildingPlotCounts = 8;
	
	int money = 0;
	
	int OwnerLoyalty = 50;
	int PlayerLoyalty = 0;
	
	//buildings to implement
	/* 
	 * 
	 * Battle Simulator (Training)
	 */
	
	//Building Requirements
		/*
		 * Enterable
		 * Each has its own menu and functionality, ala inventorymanager
		 * Can be captured/downgraded/destroyed
		 */
	
	//Need to hold materials in town for upgrades (ex: wood)
	Bag stockroom = null;
	
	double taxRate = 0.075;//default tax rate for products

	BuildingBase building[] = new BuildingBase[buildingPlotCounts];
	
	boolean hasPort = false;
	
	int Loyalty = 50;
		//goes up with quests
		//goes down by doing things such as taking from funds or recruiting for army
		//While high, population may go up?
		//While low, population may go down?
			//Buildings may be destroyed when really low?
	
	//Upgrade based on population
		//Population auto-increases with tourism
		//Citizens may move between cities of their own accord

	public Town(Core gameCore, Location nLocation)
	{
		super(gameCore, nLocation);
		
		stockroom = new Bag(coreModule, 200);
		
		//TEMPORARY
		//generate a few buildings here
		//buildings.add(new Inn(coreModule, 555, 787));
		//buildings.add(new GeneralStore(coreModule, 698, 413));
		//buildings.add(new GuardTower(coreModule, 382, 125));
		
		//generate some pathing nodes
	}
	
	public int getOwnerLoyalty()
	{
		return OwnerLoyalty;
	}
	
	public int getPlayerLoyalty()
	{
		return PlayerLoyalty;
	}
	
	public void setOwnerLoyalty(int nVal)
	{
		OwnerLoyalty = nVal;
	}
	
	public void setPlayerLoyalty(int nVal)
	{
		PlayerLoyalty = nVal;
	}
	
	public void addOwnerLoyalty(int nVal)
	{
		OwnerLoyalty += nVal;
		
		if(OwnerLoyalty > 100)
			OwnerLoyalty = 100;
	}
	
	public void addPlayerLoyalty(int nVal)
	{
		PlayerLoyalty += nVal;
		
		if(PlayerLoyalty > 100)
			PlayerLoyalty = 100;
	}
	
	public boolean hasBuildingPlot()
	{
		//pick a random spot for the building (if available)
		ArrayList<Integer> slots = new ArrayList<Integer>();
		
		for(int i=1; i<=8; i++)
		{
			if(building[i-1] != null)
			{
				if(building[i-1] == null)
					slots.add(i);
			}
		}
		
		if(slots.size() <= 0)
		{
			slots.clear();
			return false;//no slots to build on, get rekt son!
		}

		slots.clear();
		return true;
	}
	
	public void SpawnBuilding(String buildingType, int buildingLevel)
	{
		int i;
		
		if(buildingType.equals("Spaceport"))
		{
			hasPort = true;;
			return;
		}
		
		for(i=7; i>=0; i--)
		{
			if(building[i] == null)
				break;
		}
		
		if(i>7 || i<0)
		{
			System.out.println("Disaster! No room for building!");
			return;//no slots to build on, get rekt son!
		}

		if(buildingType.equals("Inn"))
		{
			building[i] = new Inn(coreModule, this, buildingLevel);
		}
		else if(buildingType.equals("General Store"))
		{
			building[i] = new GeneralStore(coreModule, this, buildingLevel);
		}
		else if(buildingType.equals("Guard Tower"))
		{
			building[i] = new GuardTower(coreModule, this, buildingLevel);
		}
		else if(buildingType.equals("Hospital"))
		{
			building[i] = new Hospital(coreModule, this, buildingLevel);
		}
		else if(buildingType.equals("Blacksmith"))
		{
			building[i] = new Blacksmith(coreModule, this, buildingLevel);
		}
		else if(buildingType.equals("Spellshop"))
		{
			building[i] = new Spellshop(coreModule, this, buildingLevel);
		}
		else if(buildingType.equals("Market"))
		{
			building[i] = new Market(coreModule, this, buildingLevel);
		}
		else if(buildingType.equals("House"))
		{
			building[i] = new HouseBuilding(coreModule, this, buildingLevel);
		}
		else if(buildingType.equals("Guild"))
		{
			building[i] = new Guild(coreModule, this, buildingLevel);
		}
		else
		{
			System.out.println("Building of type "+buildingType+" does not exist!");
		}
	}
	
	public void postInit()
	{
		for(BuildingBase b : building)
		{
			if(b!=null)
				b.initialize();
		}
	}
	
	public boolean hasSpaceport()
	{
		return hasPort;
	}
	
	public BuildingBase getBuilding(String buildingType)
	{
		for(int i=0; i<buildingPlotCounts; i++)
		{
			if(building[i]!=null)
			{
				if(building[i].getName().equals(buildingType))
				{
					return building[i];
				}
			}
		}
		
		return null;
	}
	
	public int getTaxedAmount(int value)
	{
		int amount = (int) (value*taxRate) + value;
		
		if(amount>99999)
			amount = 99999;
		if(amount <0)
			amount = 0;
		
		return amount;
	}

	public BuildingBase[] getBuildings()
	{
		return building;
	}

	public int getPlotCount()
	{
		return buildingPlotCounts;
	}
	
	public void stepTurn()
	{
		for(int i=0; i<buildingPlotCounts; i++)
		{
			if(building[i]!=null)
			{
				building[i].stepTurn();
			}
		}

		//up military strength
		ourLocation.MilitaryPower+=2;
		
		//if we have a guard tower, increase military buildup
		for(BuildingBase b : building)
		{
			if(b!=null && b instanceof GuardTower)
			{
				ourLocation.MilitaryPower+=3*b.getLevel();
			}
		}
		
		//cap military strength
		if(ourLocation.MilitaryPower > ourLocation.level*10)
		{
			ourLocation.MilitaryPower = ourLocation.level*10;
		}
		
		//update loyalty if applicable
		//if player loyalty exceeds faction loyalty, trend towards that
		if(PlayerLoyalty >= OwnerLoyalty+1 && ourLocation.controllingFaction == Common.FACTION_PLAYER)
			OwnerLoyalty+=2;
		else if(PlayerLoyalty >= OwnerLoyalty+20)
		{
			ChangeOwnership(Common.FACTION_PLAYER);
			System.out.println("Player took ownership of "+ourLocation.name+" through reputation!");
		}
		
		//check if need for rebellion
		if(OwnerLoyalty < 20 && ourLocation.controllingFaction != Common.FACTION_NONE)
		{
			//rebel
			ChangeOwnership(Common.FACTION_NONE);
			ourLocation.MilitaryPower = (int) Math.floor(ourLocation.MilitaryPower*0.1);
			
			System.out.println("Faction lost ownership of "+ourLocation.name+" through reputation!");
		}
	}
	
	public void takeLoyalty(int amount)
	{
		OwnerLoyalty -= amount;
		
		//all player loyalty is hit when the player fucks up or does something dickish
		if(ourLocation.controllingFaction == Common.FACTION_PLAYER)
		{
			PlayerLoyalty -= amount;
		}
	}
	
	public void ChangeOwnership(String nfaction)
	{
		ourLocation.controllingFaction = nfaction;
		OwnerLoyalty = 60;
	}

	public int getMoney()
	{
		return money;
	}

	public double getTaxRate()
	{
		return taxRate;
	}

	public void setMoney(int nMoney)
	{
		money = nMoney;
	}

	public void setTax(double newTax)
	{
		taxRate = newTax;
	}

	public void giveMoney(int m)
	{
		money += m;
	}
}
