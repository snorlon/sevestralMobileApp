package com.snorlon.sevestralkingdoms.Housing;

import com.snorlon.sevestralkingdoms.GameTools.Core;
import com.snorlon.sevestralkingdoms.Items.Furniture;

public class Floor
{
	//store furniture in this class along with their position
	/*
	 * several layers of furniture
	 * 	rugs and shit
	 * 	wall stuff
	 * 	normal furniture + tables
	 * 	things that sit on tables
	 * 		nothing goes here without a table underneath
	 * 		if that thing underneath disappears, so do we
	 */
	//our contents
	int[][] tiles;
	Furniture[][] floorFurnitureFloorWall;
	Furniture[][] floorFurnitureMediumLarge;
	Furniture[][] floorFurnitureSmall;

	Furniture DudA;
	Furniture DudM;
	Furniture DudL;
	
	//useful data is stored in our template, no need to copy it
	House_Template ourTemplate = null;
	
	Floor priorFloor = null;//only have a door if this is null
	Floor nextFloor = null;
	
	public Floor(Core coreModule, House_Template ntemplate)
	{
		ourTemplate = ntemplate;

		DudA = new Furniture("DUD", 0, 0, 1, 1, false, Furniture.FURNITURE_FLOOR, 1);
		DudM = new Furniture("DUD", 0, 0, 1, 1, false, Furniture.FURNITURE_MEDIUM, 1);
		DudL = new Furniture("DUD", 0, 0, 1, 1, false, Furniture.FURNITURE_BIG, 1);

		tiles = new int[ourTemplate.getHeight()+2][ourTemplate.getWidth()];
		floorFurnitureSmall = new Furniture[ourTemplate.getHeight()][ourTemplate.getWidth()];
		floorFurnitureMediumLarge = new Furniture[ourTemplate.getHeight()][ourTemplate.getWidth()];
		//default values
		for(int i=0; i<ourTemplate.getHeight()+2; i++)
		{
			for(int j=0; j<ourTemplate.getWidth(); j++)
			{
				if(i<ourTemplate.getHeight())
					floorFurnitureMediumLarge[i][j] = null;
			}
		}
		
		floorFurnitureFloorWall = new Furniture[ourTemplate.getHeight()+2][ourTemplate.getWidth()];
		
		//populate our tile data
		for(int i=0; i< 2; i++)
		{
			for(int j=0; j<ourTemplate.getWidth(); j++)
			{
				tiles[i][j] = ourTemplate.wallIndex+10*i;
			}
		}
		
		for(int i=2; i< 2+ourTemplate.getHeight(); i++)
		{
			for(int j=0; j<ourTemplate.getWidth(); j++)
			{
				tiles[i][j] = ourTemplate.floorIndex;
			}
		}
		
		//TEMPORARY
		
		for(int i=0; i<coreModule.random.nextInt(3)+2; i++)
		{
			switch(coreModule.random.nextInt(2))
			{
				case 1:
					//placeFurniture(new Furniture("Pyrash Rug", "DICKS", "Pyrash Rug.png", 0, 2, 2, false, Furniture.FURNITURE_FLOOR, 2), coreModule.random.nextInt(ourTemplate.getWidth()), coreModule.random.nextInt(ourTemplate.getHeight()), coreModule.random.nextInt(4));
					break;
				case 0:
					//placeFurniture(new Furniture("Wooden Wall Clock", "DICKS", "Wooden Wall Clock.png", 0, 1, 1, true, Furniture.FURNITURE_WALL, 1), coreModule.random.nextInt(ourTemplate.getWidth()), coreModule.random.nextInt(2) - 1, coreModule.random.nextInt(4));
					break;
			}
		}
		
		//removeFurniture(5, 4, 2);
	}
	
	public boolean canRemoveFurniture(int x, int y)
	{
		if(x>0 && y>0)
		{
			//assumes that small items can never be bigger than a 1x1
			if(hasDud(floorFurnitureSmall[y-1][x-1]))
				return false;
			else if(floorFurnitureSmall[y-1][x-1]!=null)
				return true;
			else if(hasDud(floorFurnitureMediumLarge[y-1][x-1]))
				return false;
			else if(floorFurnitureMediumLarge[y-1][x-1]!=null)
			{
				for(int i=0; i<floorFurnitureMediumLarge[y-1][x-1].getEHeight(); i++)
				{
					for(int j=0; j<floorFurnitureMediumLarge[y-1][x-1].getWidth(); j++)
					{
						//check for small stuff on top of us
						if(floorFurnitureSmall[(y-i)-1][x+j-1] != null)
							return false;
					}
				}
				
				//if we get here, we can assume we found no conflicts above us
				return true;
			}
		}
		if(hasDud(floorFurnitureFloorWall[y+2-1][x-1]))
			return false;
		else if(floorFurnitureFloorWall[y+2-1][x-1]!=null)
			return true;
		
		
		return false;//default is to return true
	}
	
	public Furniture removeFurniture(int x, int y)
	{
		if(!canRemoveFurniture(x, y))
			return null;//can't give up our stuff bro!
		
		Furniture returnValue = removeFurniture(x, y, 3);
		
		if(returnValue == null)
			returnValue = removeFurniture(x, y, 2);
		
		if(returnValue == null)
			returnValue = removeFurniture(x, y, 1);
		
		return returnValue;
	}
	
	public Furniture removeFurniture(int x, int y, int layer)
	{
		/*layers:
		 * floor/wall	1
		 * medium/large	2
		 * small	3
		 */
		
		Furniture returnObject = null;
		
		//get the furniture from it
		switch(layer)
		{
			//floor/wall
			case 1:
				if(floorFurnitureFloorWall[y+2-1][x-1] != null)
				{
					returnObject = floorFurnitureFloorWall[y+2-1][x-1];
					
					for(int i=0; i<returnObject.getEHeight(); i++)
					{
						for(int j=0; j<returnObject.getWidth(); j++)
						{
							floorFurnitureFloorWall[(y+2-i)-1][x+j-1] = null;
						}
					}
				}
				break;
			//medium/large
			case 2:
				if(y<1)
					return returnObject;
				
				if(floorFurnitureMediumLarge[y-1][x-1] != null)
				{
					returnObject = floorFurnitureMediumLarge[y-1][x-1];
					
					for(int i=0; i<returnObject.getEHeight(); i++)
					{
						for(int j=0; j<returnObject.getWidth(); j++)
						{
							floorFurnitureMediumLarge[(y-i)-1][x+j-1] = null;
						}
					}
				}
				break;
			//small
			case 3:
				if(y<1)
					return returnObject;
				
				if(floorFurnitureSmall[y-1][x-1] != null)
				{
					returnObject = floorFurnitureSmall[y-1][x-1];
					
					for(int i=0; i<returnObject.getEHeight(); i++)
					{
						for(int j=0; j<returnObject.getWidth(); j++)
						{
							floorFurnitureSmall[(y-i)-1][x+j-1] = null;
						}
					}
				}
				break;
		}
		
		//reset the furnitures rotation
		
		//return null if none found
		return returnObject;
	}
	
	//returns the new piece of furniture if it couldn't place it at that location
	public Furniture placeFurniture(Furniture newPiece, int x, int y, int rotation)
	{
		if(!canPlaceFurniture(newPiece, x, y))
			return newPiece;
		
		//if that check passed, set it to the appropriate layer for it as there is clearly room
		if(newPiece.getFurnitureType() == Furniture.FURNITURE_BIG || newPiece.getFurnitureType() == Furniture.FURNITURE_MEDIUM)
		{
			floorFurnitureMediumLarge[y-1][x-1] = newPiece;
			//fill also-occupied spaces with duds
			for(int i=0; i<newPiece.getEHeight(); i++)
			{
				for(int j=0; j<newPiece.getWidth(); j++)
				{
					if(i!=0 || j!=0)
					{
						if(newPiece.getFurnitureType() == Furniture.FURNITURE_BIG)
							floorFurnitureMediumLarge[(y-i)-1][(x+j)-1] = DudL;
						else
							floorFurnitureMediumLarge[(y-i)-1][(x+j)-1] = DudM;
					}
				}
			}
			newPiece.setRotation(rotation);
		}
		else if(newPiece.getFurnitureType() == Furniture.FURNITURE_FLOOR || newPiece.getFurnitureType() == Furniture.FURNITURE_WALL)
		{
			floorFurnitureFloorWall[y+2-1][x-1] = newPiece;
			//fill also-occupied spaces with duds
			for(int i=0; i<newPiece.getHeight(); i++)
			{
				for(int j=0; j<newPiece.getWidth(); j++)
				{
					if(i!=0 || j!=0)
					{
						floorFurnitureFloorWall[(y+2-i)-1][(x+j)-1] = DudA;
					}
				}
			}
			newPiece.setRotation(rotation);
		}
		else if(newPiece.getFurnitureType() == Furniture.FURNITURE_SMALL)
		{
			floorFurnitureSmall[y-1][x-1] = newPiece;
			//fill also-occupied spaces with duds
			for(int i=0; i<newPiece.getEHeight(); i++)
			{
				for(int j=0; j<newPiece.getWidth(); j++)
				{
					if(i!=0 || j!=0)
					{
						floorFurnitureSmall[(y-i)-1][(x+j)-1] = DudA;
					}
				}
			}
			newPiece.setRotation(rotation);
		}
		else
		{
			return null;
		}
		
		return null;
	}
	
	public boolean canPlaceFurniture(Furniture newPiece, int x, int y)
	{
		if(ourTemplate==null)
			return false;
		if(x < 1)
			return false;
		if(x > ourTemplate.getWidth())
			return false;
		
		int bonusValue = 0;
		
		if(newPiece.getFurnitureType() == Furniture.FURNITURE_FLOOR || newPiece.getFurnitureType() == Furniture.FURNITURE_WALL)
			bonusValue = 2;
		
		if(y-newPiece.getEHeight()+1  < 1-bonusValue)
			return false;
		if(y > ourTemplate.getHeight())
			return false;
		
		
		//floor stuff can't go on the wall!
		if(newPiece.getFurnitureType() == Furniture.FURNITURE_FLOOR && y-newPiece.getEHeight()+1 <1)
			return false;
		//walls can't have wall stuff on the floor
		else if(newPiece.getFurnitureType() == Furniture.FURNITURE_WALL && y > 0)
			return false;
		
		//check for overlap here
		
		//check EVERY tile
			//look to see if a single object in any location overlaps with the object at the given position
			//if a single conflict, return false
		

		//check if the object hits our location
		for(int i=0; i<newPiece.getEHeight(); i++)
		{
			for(int j=0; j<newPiece.getWidth(); j++)
			{
				if(newPiece.getFurnitureType() == Furniture.FURNITURE_BIG || newPiece.getFurnitureType() == Furniture.FURNITURE_MEDIUM)
				{
					if(floorFurnitureMediumLarge[(y-i)-1][x+j-1] != null || hasDud(floorFurnitureMediumLarge[(y-i)-1][x+j-1]))
					{
						return false;
					}
				}
				else if(newPiece.getFurnitureType() == Furniture.FURNITURE_FLOOR || newPiece.getFurnitureType() == Furniture.FURNITURE_WALL)
				{
					if(floorFurnitureFloorWall[(y+2-i)-1][x+j-1] != null || hasDud(floorFurnitureFloorWall[(y+2-i)-1][x+j-1]))
					{
						return false;
					}
				}
				else if(newPiece.getFurnitureType() == Furniture.FURNITURE_SMALL)
				{
					//ONLY place small furniture if 1: Nothing is there on the small layer and 2: Something IS on the mediumlarge layer AND it's large
					if(floorFurnitureSmall[(y-i)-1][x+j-1] != null || hasDud(floorFurnitureSmall[(y-i)-1][x+j-1]))
					{
						return false;
					}
					if(floorFurnitureMediumLarge[(y-i)-1][x+j-1] == null)
					{
						return false;
					}
					if(floorFurnitureMediumLarge[(y-i)-1][x+j-1].getFurnitureType() != Furniture.FURNITURE_BIG)
					{
						return false;
					}
				}
			}
		}
		
		return true;
	}
	
	public Furniture[][] getFurnitureSmall()
	{
		return floorFurnitureSmall;
	}
	
	public Furniture[][] getFurnitureMediumLarge()
	{
		return floorFurnitureMediumLarge;
	}
	
	public Furniture[][] getFurnitureFloorWall()
	{
		return floorFurnitureFloorWall;
	}
	
	public int[][] getTiles()
	{
		return tiles;
	}
	
	public void addFloor(Floor nfloor)
	{
		if(nextFloor!=null)
		{
			nextFloor.addFloor(nfloor);
		}
		else
		{
			nextFloor = nfloor;
		}
	}
	
	public House_Template getTemplate()
	{
		return ourTemplate;
	}
	
	public void dispose()
	{
		if(nextFloor!=null)
		{
			nextFloor.dispose();
		}
		priorFloor = null;
		
		for(int i=0; i<ourTemplate.getHeight()+2; i++)
		{
			for(int j=0; j<ourTemplate.getWidth(); j++)
			{
				if(i<ourTemplate.getHeight())
				{
					removeFurniture(j, i, 2);
					removeFurniture(j, i, 3);
				}
				
				removeFurniture(j, i, 1);
			}
		}
	}
	
	public Floor getFloor(int index)
	{
		if(index == 1)
		{
			return this;
		}
		else if(nextFloor == null)
		{
			System.out.println("Missing floor!");
			return this;
		}
		else
		{
			return nextFloor.getFloor(index-1);
		}
	}
	
	public Floor getNext()
	{
		return nextFloor;
	}
	
	public boolean hasDud(Furniture b)
	{
		if(DudA == b)
			return true;
		if(DudL == b)
			return true;
		if(DudM == b)
			return true;
		
		return false;
	}
}
