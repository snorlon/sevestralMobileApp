package com.snorlon.sevestralkingdoms.GameComponents;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.snorlon.sevestralkingdoms.GameTools.Core;
import com.snorlon.sevestralkingdoms.GameTools.InterfaceElement;
import com.snorlon.sevestralkingdoms.NPC.HumanUnit;
import com.snorlon.sevestralkingdoms.NPC.Unit;
import com.snorlon.sevestralkingdoms.Places.Location;

public class SpawnList {
	//spawn area
	//boss count
	//small unit count
	//level to generate
	//level deviation max
	//battle "scenario", ex(5 protect boss)

	public ArrayList<Unit> spawns = new ArrayList<Unit>();
	
	public SpawnList()
	{
	}
	
	public Unit spawnUnits(Core coreModule, ArrayList<Unit> destination, int count, int level, int level_deviation, Location ourLocation)
	{
		Random r = new Random();
		
		//generate our possible positions
		ArrayList<int[]> positions = new ArrayList<int[]>();
		for(int i=1; i<=3; i++)
		{
			for(int j=1; j<=5; j++)
			{
				positions.add(new int[] {j, i});
			}
		}
		
		//generate mobs
		for(int i=0; i<count && positions.size() > 0; i++)
		{
			//get a random position
			int index = r.nextInt(positions.size());
			int currPos[] = positions.get(index);
			//remove the position
			positions.remove(index);
			
			//use it to make a new unit
			Unit u = spawnAt(coreModule, destination, level, level_deviation, currPos[0], currPos[1], ourLocation);
			
			if(u == null)
				break;
		}
		
		return null;
	}
	
	public Unit spawnAt(Core coreModule, ArrayList<Unit> destination, int level, int level_deviation, int x, int y, Location ourLocation)
	{
		//count up available monsters in that area that fit criteria, must be at least one or none will spawn!
		Diety d = coreModule.gameModule.dietyModule.getDiety(ourLocation.getX(), ourLocation.getY(), ourLocation.planet);
		
		if(d!=null)
		{

			Unit newSpawn = null;
			if(coreModule.gameModule.creatureDatabase.database.containsKey(d.getName()))
			{
				newSpawn = new Unit(coreModule, d.getName(), 100, coreModule.gameModule.creatureDatabase.database);
				Unit newunit = new Unit(newSpawn);

				
				//set its position
				newunit.x = x;
				newunit.y = y;
				
				//generate a random deviation									
				newunit.level_to(d.getLevel(coreModule));
				
				//roll a personality
				newunit.Personality = coreModule.random.nextInt(Common.MAX_PERSONALITY+1);
				
				newunit.recalcMaxExp();
				
				//set their visual stuffs
				newunit.display_exp = newunit.exp;
				newunit.display_max_exp = newunit.max_exp;
				newunit.display_level = newunit.level;
				
				//add to the container
				if(destination!=null)
					destination.add(newunit);
				
				System.out.println("Spawned diety "+newunit.name+" at "+newunit.x+","+newunit.y);
				
				return null;
			}
		}
		else
		{
			Random r = new Random();
			
			int qualifiedCount = spawns.size();
			if(qualifiedCount == 0)
			{
				return null;//can't return what we can't make
			}
			
			if(level + level_deviation > 999)//bound our level
				level = 999 - level_deviation;
			if(level - level_deviation <= 0)
				level_deviation = 0;
					
			
			//generate a random index
			int index = r.nextInt(qualifiedCount);
			
			for (Unit element : spawns)
			{
				if(index == 0)
				{
					//spawn it, return it
					Unit newunit = null;

					if(element.isHuman)
					{
						newunit = new HumanUnit(element);
						
						if(((HumanUnit) newunit).genderLocked)
						{
							((HumanUnit) newunit).isMale = ((HumanUnit) element).isMale;
						}
						else
						{
							((HumanUnit) newunit).isMale = coreModule.random.nextBoolean();
						}
						
						//generate outfit stuffs
						String outfit = coreModule.gameModule.fashionModule.getOutfit(coreModule, newunit.faction) + ".png";
						String hair = coreModule.gameModule.fashionModule.getHair(((HumanUnit) newunit).isMale) + ".png";
						String face = coreModule.gameModule.fashionModule.getFace()+".png";
						
						((HumanUnit) newunit).dress(outfit, hair, face);
					}
					else
					{
						newunit = new Unit(element);
						if(newunit.attacks.size() == 0)
							System.out.println("Uh oh, no attacks on spawned unit!");
					}
					
					//set its position
					newunit.x = x;
					newunit.y = y;
					
					//generate a random deviation
					int deviation = r.nextInt((2*level_deviation)+1) - level_deviation;
					int sum = deviation + level;
					if(sum < 1)
						sum = 1;
										
					newunit.level_to(sum);
					
					//roll a personality
					newunit.Personality = r.nextInt(Common.MAX_PERSONALITY+1);
					
					newunit.recalcMaxExp();
					
					//set their visual stuffs
					newunit.display_exp = newunit.exp;
					newunit.display_max_exp = newunit.max_exp;
					newunit.display_level = newunit.level;
					
					//add to the container
					if(destination!=null)
						destination.add(newunit);
					
					System.out.println("Spawned unit "+newunit.name+" at "+newunit.x+","+newunit.y);
					
					return newunit;
				}
				else
				{
					index--;
				}
			}
		}
		
		
		return null;
	}
	
	public Unit spawnUnit(Core coreModule, ArrayList<Unit> destination, String name, int level, int x, int y)
	{
		//count up available monsters in that area that fit criteria, must be at least one or none will spawn!
		Random r = new Random();

		System.out.println("Start spawn");
		
		int qualifiedCount = 0;
		for (Unit element : spawns)
		{
			if(element.name.equals(name))
				qualifiedCount++;
				
		}
		if(qualifiedCount == 0)
		{
			return null;//can't return what we can't make
		}
				
		
		//generate a random index
		int index = r.nextInt(qualifiedCount);
		
		for (Unit element : spawns)
		{
			if(element.name.equals(name))
			{
				if(index == 0)
				{
					//spawn it, return it
					Unit newunit = null;

					if(element.isHuman)
					{
						newunit = new HumanUnit(element);
						
						if(((HumanUnit) newunit).genderLocked)
						{
							((HumanUnit) newunit).isMale = ((HumanUnit) element).isMale;
						}
						else
						{
							((HumanUnit) newunit).isMale = coreModule.random.nextBoolean();
						}
						
						//generate outfit stuffs
						String outfit = coreModule.gameModule.fashionModule.getOutfit(coreModule, newunit.faction) + ".png";
						String hair = coreModule.gameModule.fashionModule.getHair(((HumanUnit) newunit).isMale) + ".png";
						String face = coreModule.gameModule.fashionModule.getFace()+".png";
						
						((HumanUnit) newunit).dress(outfit, hair, face);
					}
					else
						newunit = new Unit(element);
					
					//set its position
					newunit.x = x;
					newunit.y = y;
					
					//generate a random deviation
					newunit.level_to(level);
					
					//add to the container
					if(destination!=null)
						destination.add(newunit);
					
					System.out.println("Spawned unit "+newunit.name+" at "+newunit.x+","+newunit.y);

					System.out.println("End spawn");
					return newunit;
				}
				else
				{
					index--;
				}
			}
		}
		
		return null;
	}

	public void load(Core coreModule, String creatureName, int rate, boolean isHuman)
	{
		Unit newSpawn = null;
		if(coreModule.gameModule.creatureDatabase.database.containsKey(creatureName))
		{
			if(isHuman)
				newSpawn = new HumanUnit(coreModule, creatureName, rate, coreModule.gameModule.creatureDatabase.database);
			else
				newSpawn = new Unit(coreModule, creatureName, rate, coreModule.gameModule.creatureDatabase.database);
			
			//only accept spawn if it seems to be loaded
			if(!newSpawn.faction.equals("None"))
				spawns.add(newSpawn);
		}
	}
}
