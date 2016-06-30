package com.snorlon.sevestralkingdoms.Interfaces;

import com.snorlon.sevestralkingdoms.GameComponents.Common;
import com.snorlon.sevestralkingdoms.GameComponents.Diety;
import com.snorlon.sevestralkingdoms.GameComponents.GameModule;
import com.snorlon.sevestralkingdoms.GameTools.Core;
import com.snorlon.sevestralkingdoms.GameTools.Gesture;
import com.snorlon.sevestralkingdoms.GameTools.InterfaceElement;
import com.snorlon.sevestralkingdoms.GameTools.Renderer;
import com.snorlon.sevestralkingdoms.NPC.Unit;
import com.snorlon.sevestralkingdoms.Places.Location;
import com.snorlon.sevestralkingdoms.Places.Town;

public class InterfaceLocalMap extends GenericInterface
{
	
	//our coordinates in the current area
	String planet = "Wind";//this is the planet we're viewing
	int x = 1;
	int y = 1;
	
	boolean regetLocation = true;

	InterfaceElement rebuildBG = null;
	InterfaceElement rebuildInterface = null;

	public InterfaceLocalMap(Core newCore)
	{
		super(newCore);
		// TODO Auto-generated constructor stub
		
		id = "InterfaceLocalMap";
	}
	
	//used to pull up the particular screen in question
	public void activate()
	{
		if(gameModule.partyData.getMainCharacter().currentLocation == null)
		{
			System.out.println("InterfaceLocalMap error: Invalid location");
		}
		
		if(regetLocation)
		{
			x = gameModule.partyData.getMainCharacter().currentLocation.getX();
			y = gameModule.partyData.getMainCharacter().currentLocation.getY();
			
			planet = gameModule.partyData.getMainCharacter().currentLocation.planet;
		}
		
		//create a gui container
		newElement = new InterfaceElement(coreModule, "InterfaceContainer", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 720, 1180, 0, 0);
		renderModule.addElement("CenterNode", newElement);
		rebuildInterface = new InterfaceElement(coreModule, "InterfaceContainerR", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 720, 1180, 0, 0);
		renderModule.addElement("CenterNode", rebuildInterface);
		
		//bg
		newElement = new InterfaceElement(coreModule, "bg", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 718, 1280, 24, 51, 1.0f, 1.0f, "menu/menubg.png");
		renderModule.addElement("InterfaceContainer", newElement);
		newElement = new InterfaceElement(coreModule, "background_bg", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 718, 1280, -24, -50, 1.0f, 1.0f, "interfaces/localmap/background.png");
		renderModule.addElement("bg", newElement);
		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 700, 70, 200+(9 - coreModule.gameModule.planetNames.get(planet).length())*15, 425, 2.0f, coreModule.gameModule.planetNames.get(planet), coreModule.renderModule.FONT_IMPACT);
		renderModule.addElement("bg", newElement);
		
		rebuildBG = new InterfaceElement(coreModule, "rebuild_bg", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 718, 1280, 24, 51);
		renderModule.addElement("InterfaceContainer", rebuildBG);
		
		buildMap();
		
		//return text
		newElement = new InterfaceElement(coreModule, "return_button", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 560, 60, -24, -554);
		new Gesture("Click", "interface~open~InterfaceMenuOverview", newElement);
		renderModule.addElement("bg", newElement);
		
		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 546, 70, 100, 10, 1.2f, "Return to Main Menu", coreModule.renderModule.FONT_IMPACT);
		renderModule.addElement("return_button", newElement);
		
		//auto reset ourselves
		regetLocation = true;
	}
	
	public void buildMap()
	{
		if(rebuildBG!=null)
		{
			rebuildBG.destroyChildren();
		}
		if(rebuildInterface!=null)
		{
			rebuildInterface.destroyChildren();
		}
		
		Location thisLoc = gameModule.locationModule.getLocation(planet, x, y);
		
		//draw the map for the region that's present
		int startX = x;
		int startY = y;

		if(!planet.equals("None"))
		{
			int baseX = 0;
			int baseY = -6;
			for(int i=startX-1; i<=startX+1; i++)
			{
				for(int j=startY-1; j<=startY+1; j++)
				{
					Location l = gameModule.locationModule.getLocation(planet, i, j);
					
					int index = (i-startX+2)+((j-startY+1)*3);
					

					newElement = new InterfaceElement(coreModule, "map_image"+index, id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 2400, 1200, baseX+(i-startX)*150, baseY+(1-(j-startY))*150, 1.0f, 1.0f, "map/world/"+planet+".png");
					newElement.generateFrames(150, 150, 5, 0, 128, false);
					if(i>0 && j>0 && i<17 && j<9)
						newElement.current_frame = (i-1) + (j-1)*16;
					renderModule.addElement("InterfaceContainerR", newElement);
					
					if(l==null)
					{
						if(i<=0 || i>16 || j>8 || j<=0)
						{
							newElement = new InterfaceElement(coreModule, "map_imagef"+index, id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 150, 150, 0,0, 1.0f, 1.0f, "interfaces/localmap/map_black.png");
							renderModule.addElement("map_image"+index, newElement);
						}
						else
						{
							//can remove this when every map is made and final
							newElement = new InterfaceElement(coreModule, "map_imagef"+index, id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 150, 150, 0,0, 1.0f, 1.0f, "interfaces/localmap/map_faded.png");
							renderModule.addElement("map_image"+index, newElement);
						}
					}
					else
					{

						//render faction information
						if(!l.getOwner().equals(Common.FACTION_NONE))
						{
							newElement = new InterfaceElement(coreModule, "map_imagef"+index, id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 150, 150, 0, 0, 1.0f, 1.0f, "interfaces/localmap/territory.png");
							int FColor[] = {255,255,255};
							
							FColor = Common.getColor(l.getOwner());
							
							newElement.setColor(FColor[0], FColor[1], FColor[2], 128);
							
							renderModule.addElement("map_image"+index, newElement);
						}
						
						if(l.danger_level == 0)
						{
							newElement = new InterfaceElement(coreModule, "map_imaget"+index, id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 149, 149, 0, 0, 1.01f, 1.01f, "interfaces/world_map/towns.png");
							renderModule.addElement("map_image"+index, newElement);
							
							//display the location loyalty information
							//color green/red based on this
							
							Town t = null;
							
							t = (Town) l.getMap();
							
							int loyalty = 0;

							if(l.getOwner().equals(Common.FACTION_PLAYER) && t!=null)
							{
								loyalty = t.getOwnerLoyalty();
							}
							else if(t!=null)
							{
								loyalty = t.getPlayerLoyalty();
							}
							
							int xOffset = 0;
							if(loyalty < 100)
								xOffset += 14;
							if(loyalty < 10)
								xOffset += 14;
							
							newElement = new InterfaceElement(coreModule, id, "Center", "Center", 250, 70, 130+xOffset, 60, 1.0f, "%"+loyalty, coreModule.renderModule.FONT_IMPACT);
							newElement.setColor(255-(255*loyalty/100), 255*loyalty/100, 0, 1.0f);
							renderModule.addElement("map_image"+index, newElement);
						}
						else if(l!=null && l.hasCreature)
						{
							boolean hasQuest = false;
							int num = 0;
							
							for(Unit u : l.spawns.spawns)
							{
								//check if we have a quest for this creature
								if(num == 0 && !hasQuest && gameModule.hasQuest(u.name))
									hasQuest = true;
								else
									break;
								
								num++;
							}
						
							if(hasQuest)
							{
								//render our quest icon if we have a quest
								newElement = new InterfaceElement(coreModule, "current location sprite", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 50, 50, 45, 45, 1.0f, 1.0f, "interfaces/localmap/quest icon.png");
								renderModule.addElement("map_image"+index, newElement);
							}
						}
					}
					
					if(index%2!=1 && j <= 8 && j>0 && x <= 16 && x>0)
					{
						//can remove this when every map is made and final
						newElement = new InterfaceElement(coreModule, "map_imagearrow"+index, id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 150, 150, baseX+(i-startX)*150, baseY+(1-(j-startY))*150, 1.0f, 1.0f, "interfaces/localmap/arrow"+index+".png");
						newElement.setColor(255, 255, 255, 0.75f);
						//newElement.generateFrames(150, 150, 2, 0, 1, true);
						switch(index)
						{
							case 2:
								new Gesture("Click", "interface~action~InterfaceLocalMap:Up", newElement);
								break;
							case 4:
								new Gesture("Click", "interface~action~InterfaceLocalMap:Left", newElement);
								break;
							case 6:
								new Gesture("Click", "interface~action~InterfaceLocalMap:Right", newElement);
								break;
							case 8:
								new Gesture("Click", "interface~action~InterfaceLocalMap:Down", newElement);
								break;
						}
						renderModule.addElement("InterfaceContainerR", newElement);
					}
					//draw some info about the map
					else if(index==5)
					{
						if(l!=null)
						{
							Diety d = coreModule.gameModule.dietyModule.getDiety(i, j, planet);
							
							//map name text
							float modifier = 1.5f;
							int offsetY = 0;
							
							if(l.name.length() > 17)
							{
								modifier = 1.2f;
								offsetY = 6;
							}
							
							newElement = new InterfaceElement(coreModule, id, "Center", "Center", 546, 70, 0, -262+offsetY, modifier, ""+l.name, coreModule.renderModule.FONT_IMPACT);
							renderModule.addElement("rebuild_bg", newElement);
							
							int extra = 0;
							if(l.level >9)
								extra = -7;
							if(l.level >99)
								extra = -14;
							
							if(l.danger_level <= 0)
							{
								newElement = new InterfaceElement(coreModule, id, "Center", "Center", 546, 70, 4, -312, 1.0f, ""+l.getOwner()+" Town", coreModule.renderModule.FONT_IMPACT);
								renderModule.addElement("rebuild_bg", newElement);
								
								newElement = new InterfaceElement(coreModule, id, "Center", "Center", 546, 70, 4, -367, 1.0f, "No Monsters Attacking", coreModule.renderModule.FONT_IMPACT);
								renderModule.addElement("rebuild_bg", newElement);
								
								newElement = new InterfaceElement(coreModule, id, "Center", "Center", 546, 70, 380+extra, -312, 1.0f, "Level "+l.level, coreModule.renderModule.FONT_IMPACT);
								renderModule.addElement("rebuild_bg", newElement);
								
								newElement = new InterfaceElement(coreModule, id, "Center", "Center", 546, 70, 350, -367, 1.0f, ""+l.getDangerString(), coreModule.renderModule.FONT_IMPACT);
								renderModule.addElement("rebuild_bg", newElement);
							}
							else
							{
								newElement = new InterfaceElement(coreModule, id, "Center", "Center", 546, 70, 4, -312, 1.0f, "Field", coreModule.renderModule.FONT_IMPACT);
								renderModule.addElement("rebuild_bg", newElement);

								
								if(d!=null)
								{
									newElement = new InterfaceElement(coreModule, id, "Center", "Center", 546, 70, 4, -367, 1.0f, "Monsters: "+1, coreModule.renderModule.FONT_IMPACT);
									renderModule.addElement("rebuild_bg", newElement);

									int lvl = d.getLevel(coreModule);
									extra = 0;
									if(lvl >9)
										extra = -7;
									if(lvl >99)
										extra = -14;
									
									newElement = new InterfaceElement(coreModule, id, "Center", "Center", 546, 70, 380+extra, -312, 1.0f, "Level "+lvl, coreModule.renderModule.FONT_IMPACT);
									renderModule.addElement("rebuild_bg", newElement);
									
									newElement = new InterfaceElement(coreModule, id, "Center", "Center", 546, 70, 390, -367, 1.0f, "Diety", coreModule.renderModule.FONT_IMPACT);
									renderModule.addElement("rebuild_bg", newElement);
								}
								else
								{
									newElement = new InterfaceElement(coreModule, id, "Center", "Center", 546, 70, 4, -367, 1.0f, "Monsters: "+l.getMinSpawn()+"-"+l.getMaxSpawn(), coreModule.renderModule.FONT_IMPACT);
									renderModule.addElement("rebuild_bg", newElement);
									
									newElement = new InterfaceElement(coreModule, id, "Center", "Center", 546, 70, 380+extra, -312, 1.0f, "Level "+l.level, coreModule.renderModule.FONT_IMPACT);
									renderModule.addElement("rebuild_bg", newElement);
									
									newElement = new InterfaceElement(coreModule, id, "Center", "Center", 546, 70, 350, -367, 1.0f, ""+l.getDangerString(), coreModule.renderModule.FONT_IMPACT);
									renderModule.addElement("rebuild_bg", newElement);
								}
							}
							
							//draw the creatures for here!
							int num = 0;

							if(l.spawns.spawns.size() < 6)
								num++;
							if(l.spawns.spawns.size() < 4)
								num++;
							if(l.spawns.spawns.size() < 2)
								num++;
							
							int increment = 64;
							int baseXU = -215;
							
							if(l.spawns.spawns.size() < 7 && l.spawns.spawns.size() % 2 == 0)
								baseXU += increment/2;
							
							if(d!=null)
							{
								d.generateImage(coreModule, renderModule, "rebuild_bg", id+"LocDetailed"+0, -24, -180);
							}
							else
							{
								for(Unit u : l.spawns.spawns)
								{
									if(num >= 7)//limit the creatures we see here!
										break;
									
									if(u != null)
									{
										u.generateImage(coreModule, renderModule, "rebuild_bg", id+"LocDetailed"+num, num*increment + baseXU, -180);
									}
									
									num++;
								}
							}
						}
						else
						{
							//map name text
							newElement = new InterfaceElement(coreModule, id, "Center", "Center", 546, 70, 0, -262, 1.5f, "Unavailable", coreModule.renderModule.FONT_IMPACT);
							renderModule.addElement("rebuild_bg", newElement);
							
							newElement = new InterfaceElement(coreModule, id, "Center", "Center", 546, 70, 4, -312, 1.0f, "Unknown", coreModule.renderModule.FONT_IMPACT);
							renderModule.addElement("rebuild_bg", newElement);
						}
						
						//map loc text
						newElement = new InterfaceElement(coreModule, id, "Center", "Center", 546, 70, 380, -262, 1.5f, "("+i+","+j+")", coreModule.renderModule.FONT_IMPACT);
						renderModule.addElement("rebuild_bg", newElement);
					}
					
					Diety d = coreModule.gameModule.dietyModule.getDiety(i, j, planet);
					
					//draw the level
					if(l!=null)
					{
						//draw our sprite if it's our current location
						if(l == gameModule.partyData.getMainCharacter().currentLocation)
						{
							Unit currUnit = gameModule.partyData.getMainCharacter();
							
							if(currUnit != null)
							{
								currUnit.generateImage(coreModule, renderModule, "map_image"+index, id);
							}
							
							newElement = new InterfaceElement(coreModule, "current location sprite", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 600, 150, 0, 40, 1.0f, 1.0f, "interfaces/localmap/your_location.png");
							newElement.setColor(255, 255, 255, 0.8f);
							newElement.generateFrames(150, 150, 2, 0, 3, true);
							renderModule.addElement("map_image"+index, newElement);
						}
						//otherwise, grab a random spawn for that location (doesn't work on towns)
						else
						{
							if(d!=null)
							{
								d.generateImage(coreModule, renderModule, "map_image"+index, id);
							}
							else
							{
								Unit randomUnit = l.getRandomUnit();
								
								if(randomUnit!=null && l.hasCreature)
								{
									randomUnit.generateImage(coreModule, renderModule, "map_image"+index, id);
								}
							}
						}
						
						//draw the location level
						if(d!=null)
						{
							int lvl = d.getLevel(coreModule);
							
							int offset = 0;
							if(lvl > 99)
								offset = -20;
							else if(lvl < 10)
								offset = 20;
							
							newElement = new InterfaceElement(coreModule, id, "Center", "Center", 146, 70, 84+offset, -40, 1.0f, "Lv"+lvl, coreModule.renderModule.FONT_IMPACT);
							renderModule.addElement("map_image"+index, newElement);
						}
						else
						{
							int offset = 0;
							if(l.level > 99)
								offset = -20;
							else if(l.level < 10)
								offset = 20;
							
							newElement = new InterfaceElement(coreModule, id, "Center", "Center", 146, 70, 84+offset, -40, 1.0f, "Lv"+l.level, coreModule.renderModule.FONT_IMPACT);
							renderModule.addElement("map_image"+index, newElement);
						}
					}
				}
			}
		}	
		

		newElement = new InterfaceElement(coreModule, "da button", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 316, 52, -24, -445);
		new Gesture("Click", "interface~action~InterfaceLocalMap:Enter", newElement);//clicking should enter
		renderModule.addElement("rebuild_bg", newElement);
		
		//enter location text
		if(thisLoc == gameModule.partyData.getMainCharacter().currentLocation)
		{
			if(thisLoc.danger_level == 0)
			{
				newElement = new InterfaceElement(coreModule, id, "Center", "Center", 546, 70, 160, -440, 1.2f, "Enter Town", coreModule.renderModule.FONT_IMPACT);
				renderModule.addElement("rebuild_bg", newElement);	
			}
			else
			{
				newElement = new InterfaceElement(coreModule, id, "Center", "Center", 546, 70, 160, -440, 1.2f, "Start Fight", coreModule.renderModule.FONT_IMPACT);
				renderModule.addElement("rebuild_bg", newElement);	
			}
		}
		else if(thisLoc!=null && (thisLoc.hasCreature||thisLoc.danger_level <= 0))
		{
			newElement = new InterfaceElement(coreModule, id, "Center", "Center", 546, 70, 209, -440, 1.2f, "Travel", coreModule.renderModule.FONT_IMPACT);
			new Gesture("Click", "interface~action~InterfaceLocalMap:Enter", newElement);//clicking should enter battle
			renderModule.addElement("rebuild_bg", newElement);	
		}
	}
	
	public void destroy()
	{
		super.destroy();
		
		rebuildBG = null;
		rebuildInterface = null;

		if(gameModule.partyData.getMainCharacter() != null)
			if(gameModule.partyData.getMainCharacter().currentLocation == null)
				return;
		
		if(regetLocation && gameModule.partyData.getMainCharacter()!=null)
		{
			x = gameModule.partyData.getMainCharacter().currentLocation.getX();
			y = gameModule.partyData.getMainCharacter().currentLocation.getY();
			
			planet = gameModule.partyData.getMainCharacter().currentLocation.planet;
		}
	}

	//given a string of input, it will perform something upon the interface
	public void processInput(String inputString, String additionalInput)
	{
		if(inputString.equals("Left") || inputString.equals("Up-Left") || inputString.equals("Down-Left"))
		{
			if(x > 1)
			{
				x--;
			}
			
			buildMap();
			return;
		}
		if(inputString.equals("Right") || inputString.equals("Up-Right") || inputString.equals("Down-Right"))
		{
			if(x < 16)
			{
				x++;
			}
			
			buildMap();
			return;
		}
		if(inputString.equals("Up") || inputString.equals("Up-Left") || inputString.equals("Up-Right"))
		{
			if(y > 1)
			{
				y--;
			}
			
			buildMap();
			return;
		}
		if(inputString.equals("Down") || inputString.equals("Down-Left") || inputString.equals("Down-Right"))
		{
			if(y < 8)
			{
				y++;
			}
			
			buildMap();
			return;
		}
		if(inputString.equals("Enter"))
		{
			Location l = gameModule.locationModule.getLocation(planet, x, y);
			
			if(l!=null)
			{
				if(l!=gameModule.partyData.getMainCharacter().currentLocation)
				{
					gameModule.travelLocation(planet, x, y, false);

					//save the game!
					gameModule.saveSave();
					
					gameModule.activateInterface("InterfaceMenuOverview");
				}
				else
				{
					//if town, enter town
					if(l.danger_level == 0)
					{
						//else, start a battle
						gameModule.activateInterface("InterfaceTownOverview");
					}
					else
					{
						//else, start a battle
						//gameModule.GenerateScene(GameModule.STATE_BATTLE);
						gameModule.activateInterface("InterfaceBattle");
					}
				}
			}
			return;
		}
		
		regetLocation = false;
		
		gameModule.activateInterface("InterfaceLocalMap");
	}
}
