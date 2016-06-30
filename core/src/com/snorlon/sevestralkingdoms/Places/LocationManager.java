package com.snorlon.sevestralkingdoms.Places;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.snorlon.sevestralkingdoms.GameComponents.Common;
import com.snorlon.sevestralkingdoms.GameTools.Core;

public class LocationManager
{	
	int mapWidth = 16;
	int mapHeight = 8;
	

	Location[][] firePlanet = new Location[mapHeight][mapWidth];
	Location[][] waterPlanet = new Location[mapHeight][mapWidth];
	Location[][] earthPlanet = new Location[mapHeight][mapWidth];
	Location[][] windPlanet = new Location[mapHeight][mapWidth];
	Location[][] lifePlanet = new Location[mapHeight][mapWidth];
	Location[][] timePlanet = new Location[mapHeight][mapWidth];
	Location[][] spacePlanet = new Location[mapHeight][mapWidth];
	Location[][] voidPlanet = new Location[mapHeight][mapWidth];
	
	Core coreModule;
	
	public LocationManager(Core newCore)
	{
		coreModule = newCore;
		//System.out.println("Loading maps!");
		
		//read the location load list
		FileHandle handle = Gdx.files.internal("Game Data/Maps/loadlist");
		
		String r = handle.readString();
		String parameters[] = r.split("\n");
		
		Location priorLocation = null;
		
		//run through all parameters
		for(String p : parameters)
		{
			if(p.length()>0)
			{
				String parts[] = (p.trim()).split("\t");
				
				if(parts.length >= 4)
				{
					int x = Integer.parseInt(parts[1].trim());
					int y = Integer.parseInt(parts[2].trim());
					
					String flag = " ";

					if(parts.length >= 5)
						flag = parts[4];
					
					if(x >= 1 && x <= mapWidth && y >= 1 && y <= mapHeight)
					{
						//create a new location
						Location newLocation = new Location(parts[3].trim(), x, y, parts[0]);
						newLocation.initialize(coreModule, flag);
						
						priorLocation = newLocation;
						
						if(parts[0].trim().equals("Fire"))
						{
							firePlanet[y-1][x-1] = newLocation;
						}
						else if(parts[0].trim().equals("Water"))
						{
							waterPlanet[y-1][x-1] = newLocation;
						}
						else if(parts[0].trim().equals("Earth"))
						{
							earthPlanet[y-1][x-1] = newLocation;
						}
						else if(parts[0].trim().equals("Wind"))
						{
							windPlanet[y-1][x-1] = newLocation;
						}
						else if(parts[0].trim().equals("Life"))
						{
							lifePlanet[y-1][x-1] = newLocation;
						}
						else if(parts[0].trim().equals("Time"))
						{
							timePlanet[y-1][x-1] = newLocation;
						}
						else if(parts[0].trim().equals("Space"))
						{
							spacePlanet[y-1][x-1] = newLocation;
						}
						else if(parts[0].trim().equals("Void"))
						{
							voidPlanet[y-1][x-1] = newLocation;
						}
					}
				}
				
				else if(parts.length >= 2)
				{
					if(priorLocation!=null && priorLocation.danger_level == 0)
					{
						if(parts[0].equals("Building"))
						{
							priorLocation.getIngameMap().SpawnBuilding(parts[1], Integer.parseInt(parts[2]));
							System.out.println("Building: "+parts[1]+" level "+parts[2]);
						}
						else if(parts[0].equals("Population"))
						{
							priorLocation.level = Integer.parseInt(parts[1].trim());
							System.out.println("Population: "+parts[1]+"\n");
						}
					}
					else if(priorLocation!=null && priorLocation.danger_level > 0)
					{
						if(parts[0].equals("Spawn"))
						{
							//load in our own mob spawn
							System.out.println("Entity spawn: "+parts[1].trim());
							
							boolean isHuman = false;
							
							if(parts.length > 3)
								isHuman = true;
							
							//load that creature
							priorLocation.spawns.load(coreModule, parts[1].trim(),Integer.parseInt(parts[2].trim()), isHuman);
						}
					}
				}
			}
		}
	}
	
	public void postInit()
	{
		for(int i=0; i<8; i++)
		{
			for(int j=0; j<16; j++)
			{
				if(getPlanet("Fire")[i][j]!=null && getPlanet("Fire")[i][j].getMap()!=null)
				{
					getPlanet("Fire")[i][j].getMap().postInit();
				}
				if(getPlanet("Water")[i][j]!=null && getPlanet("Water")[i][j].getMap()!=null)
				{
					getPlanet("Water")[i][j].getMap().postInit();
				}
				if(getPlanet("Earth")[i][j]!=null && getPlanet("Earth")[i][j].getMap()!=null)
				{
					getPlanet("Earth")[i][j].getMap().postInit();
				}
				if(getPlanet("Wind")[i][j]!=null && getPlanet("Wind")[i][j].getMap()!=null)
				{
					getPlanet("Wind")[i][j].getMap().postInit();
				}
				if(getPlanet("Life")[i][j]!=null && getPlanet("Life")[i][j].getMap()!=null)
				{
					getPlanet("Life")[i][j].getMap().postInit();
				}
				if(getPlanet("Time")[i][j]!=null && getPlanet("Time")[i][j].getMap()!=null)
				{
					getPlanet("Time")[i][j].getMap().postInit();
				}
				if(getPlanet("Space")[i][j]!=null && getPlanet("Space")[i][j].getMap()!=null)
				{
					getPlanet("Space")[i][j].getMap().postInit();
				}
				if(getPlanet("Void")[i][j]!=null && getPlanet("Void")[i][j].getMap()!=null)
				{
					getPlanet("Void")[i][j].getMap().postInit();
				}
			}
		}
	}
	
	public Location[][] getPlanet(String planet)
	{
		if(planet.equals("Fire"))
		{
			return firePlanet;
		}
		else if(planet.equals("Water"))
		{
			return waterPlanet;
		}
		else if(planet.equals("Earth"))
		{
			return earthPlanet;
		}
		else if(planet.equals("Wind"))
		{
			return windPlanet;
		}
		else if(planet.equals("Life"))
		{
			return lifePlanet;
		}
		else if(planet.equals("Time"))
		{
			return timePlanet;
		}
		else if(planet.equals("Space"))
		{
			return spacePlanet;
		}
		else if(planet.equals("Void"))
		{
			return voidPlanet;
		}

		System.out.println("Map planet invalid!");
		return null;
	}
	
	public Location getRandomLocation(String planet, int minX, int maxX, int minY, int maxY)
	{
		Location[][] planetData = getPlanet(planet);
		
		if(minX < 0 || maxX > 15 || minY < 0 || maxY > 7)
		{
			return null;
		}
		
		ArrayList<Location> options = new ArrayList<Location>();
		
		if(planetData!=null)
		{
			for(int i=minX; i<=maxX; i++)
			{
				for(int j=minY; j<=maxY; j++)
				{
					if(planetData[j][i]!=null)
					{
						options.add(planetData[j][i]);
					}
				}
			}
		}
		
		if(options.size() > 0)
		{
			return options.get(coreModule.random.nextInt(options.size()));
		}
		
		options.clear();
		
		return null;
	}
	
	public Location getLocation(String planet, int x, int y)
	{
		if(x < 1 || x > mapWidth || y < 1 || y > mapHeight)
		{
			//System.out.println("Map coordinates out of bounds!");
			return null;
		}

		if(planet.equals("Fire"))
		{
			return firePlanet[y-1][x-1];
		}
		else if(planet.equals("Water"))
		{
			return waterPlanet[y-1][x-1];
		}
		else if(planet.equals("Earth"))
		{
			return earthPlanet[y-1][x-1];
		}
		else if(planet.equals("Wind"))
		{
			return windPlanet[y-1][x-1];
		}
		else if(planet.equals("Life"))
		{
			return lifePlanet[y-1][x-1];
		}
		else if(planet.equals("Time"))
		{
			return timePlanet[y-1][x-1];
		}
		else if(planet.equals("Space"))
		{
			return spacePlanet[y-1][x-1];
		}
		else if(planet.equals("Void"))
		{
			return voidPlanet[y-1][x-1];
		}

		System.out.println("Map planet invalid!");
		return null;
	}
	
	public ArrayList<Location> getLocations(String planet)
	{
		ArrayList<Location> returnLocations = new ArrayList<Location>();
		
		Location[][] locations = firePlanet;

		if(planet.equals("Water"))
		{
			locations = waterPlanet;
		}
		else if(planet.equals("Earth"))
		{
			locations = earthPlanet;
		}
		else if(planet.equals("Wind"))
		{
			locations = windPlanet;
		}
		else if(planet.equals("Life"))
		{
			locations = lifePlanet;
		}
		else if(planet.equals("Time"))
		{
			locations = timePlanet;
		}
		else if(planet.equals("Space"))
		{
			locations = spacePlanet;
		}
		else if(planet.equals("Void"))
		{
			locations = voidPlanet;
			System.out.println("KAPPA\n\n\n\n\n");
		}
		
		for(int i=0; i<mapWidth; i++)
		{
			for(int j=0; j<mapHeight; j++)
			{
				if(locations[j][i]!=null && locations[j][i].danger_level == 0)
				{
					if(locations[j][i].getMap() instanceof Town)
					{
						Town t = (Town) locations[j][i].getMap();
						
						if(t.hasSpaceport())
						{
							returnLocations.add(locations[j][i]);
						}
					}
				}
			}
		}

		return returnLocations;
	}
	
	public void initialize(Core coreModule)
	{
		Location[][][] planetHolders = {firePlanet, waterPlanet, earthPlanet, windPlanet, lifePlanet, timePlanet, spacePlanet, voidPlanet};
		
		for(int i=0; i<mapHeight; i++)
		{
			for(int j=0; j<mapWidth; j++)
			{
				for(Location[][] p : planetHolders)
				{
					Location l = p[i][j];
					
					if(l!=null && l.level <= 0)
					{
						l.level = coreModule.random.nextInt(7)+1;
					}
				}
			}
		}
		
		coreModule.gameModule.creatureDatabase.initialize(coreModule,planetHolders);
	}

	public void stepTurn()
	{
		//ArrayList<String> factionOptions = new ArrayList<String>();
		//factionOptions.add(Common.FACTION_NONE);
		//factionOptions.add(Common.FACTION_PLAYER);
		//factionOptions.add(Common.FACTION_DAIMINA);
		
		for(int i=1; i<=mapWidth; i++)
		{
			for(int j=1; j<= mapHeight; j++)
			{
				//randomly change the factions
				if(firePlanet[j-1][i-1]!=null)
					firePlanet[j-1][i-1].stepTurn();
				if(waterPlanet[j-1][i-1]!=null)
					waterPlanet[j-1][i-1].stepTurn();
				if(windPlanet[j-1][i-1]!=null)
					windPlanet[j-1][i-1].stepTurn();
				if(earthPlanet[j-1][i-1]!=null)
					earthPlanet[j-1][i-1].stepTurn();
				if(lifePlanet[j-1][i-1]!=null)
					lifePlanet[j-1][i-1].stepTurn();
				if(timePlanet[j-1][i-1]!=null)
					timePlanet[j-1][i-1].stepTurn();
				if(spacePlanet[j-1][i-1]!=null)
					spacePlanet[j-1][i-1].stepTurn();
				if(voidPlanet[j-1][i-1]!=null)
					voidPlanet[j-1][i-1].stepTurn();

			}
		}
		
	}
}
