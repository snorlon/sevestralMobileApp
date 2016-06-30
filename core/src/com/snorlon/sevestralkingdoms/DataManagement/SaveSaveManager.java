package com.snorlon.sevestralkingdoms.DataManagement;

import java.io.PrintWriter;

import com.snorlon.sevestralkingdoms.Ability.Ability;
import com.snorlon.sevestralkingdoms.Ability.SpellAbility;
import com.snorlon.sevestralkingdoms.Ability.WeaponAbility;
import com.snorlon.sevestralkingdoms.Buildings.BuildingBase;
import com.snorlon.sevestralkingdoms.Buildings.Guild;
import com.snorlon.sevestralkingdoms.Buildings.HouseBuilding;
import com.snorlon.sevestralkingdoms.Buildings.Inn;
import com.snorlon.sevestralkingdoms.GameComponents.Common;
import com.snorlon.sevestralkingdoms.GameTools.Core;
import com.snorlon.sevestralkingdoms.Housing.Floor;
import com.snorlon.sevestralkingdoms.Housing.House;
import com.snorlon.sevestralkingdoms.Items.Item;
import com.snorlon.sevestralkingdoms.Items.Shop;
import com.snorlon.sevestralkingdoms.NPC.HumanUnit;
import com.snorlon.sevestralkingdoms.NPC.Unit;
import com.snorlon.sevestralkingdoms.Places.Location;
import com.snorlon.sevestralkingdoms.Places.Town;
import com.snorlon.sevestralkingdoms.Quests.Quest;

public class SaveSaveManager
{
	public static void printTabs(PrintWriter out, int layer)
	{
		//print out tabs for every layer
		for(int i=0; i<layer; i++)
		{
			out.write('\t');
		}
	}
	
	public static void processConfig(PrintWriter out, int layer, Core coreModule)
	{
		printTabs(out, layer);
		out.write("FPS\t");
		if(coreModule.gameModule.gameConfig.getFPSFrameOn() == 1)
			out.write("off\n");
		else
			out.write("on\n");
		
		printTabs(out, layer);
		out.write("SFX\t"+coreModule.gameModule.gameConfig.getSFXVolume()+"\n");
		
		printTabs(out, layer);
		out.write("BGM\t"+coreModule.gameModule.gameConfig.getBGMVolume()+"\n");
	}
	
	public static void processSave(PrintWriter out, int layer, Core coreModule)
	{
		//output the game version
		printTabs(out, layer);
		out.write("[VERSION]\t"+coreModule.getVersion()+"\n\n");
		
		//print out config data
		printTabs(out, layer);
		out.write("[CONFIG]\n");
		processConfig(out, layer+1, coreModule);
		printTabs(out, layer);
		out.write("[/CONFIG]\n\n");
		
		//turn
		printTabs(out, layer);
		out.write("[TURN]\t"+coreModule.gameModule.getTurn()+"\n\n");
		
		//save out our quest information
		for(Quest q : coreModule.gameModule.playerQuests.getList())
		{
			//print out all of our quests
			printTabs(out, layer);
			out.write("[QUEST]\tNormal\t"+q.getDataString()+"\n");
		}
		for(Quest q : coreModule.gameModule.dailyQuests.getList())
		{
			//print out all of our quests
			printTabs(out, layer);
			out.write("[QUEST]\tDaily\t"+q.getDataString()+"\n");
		}

		printTabs(out, layer);
		out.write("[INVENTORY]\n");
		
		processInventory(out, layer+1, coreModule);
		
		printTabs(out, layer);
		out.write("[/INVENTORY]\n\n");
		
		processMaps(out, layer, coreModule);
		
		//party tags
		printTabs(out, layer);
		out.write("[PARTY]\n");
		
		//save our party info
		processParty(out, layer+1, coreModule);
		
		//party tags
		printTabs(out, layer);
		out.write("[/PARTY]\n");
		
		//put our location
		if(coreModule.gameModule.partyData.getMainCharacter() != null)
		{
			printTabs(out, layer);
			out.write("[LOCATION]\t"+coreModule.gameModule.partyData.getMainCharacter().currentLocation.planet+"\t"+coreModule.gameModule.partyData.getMainCharacter().currentLocation.getX()+"\t"+coreModule.gameModule.partyData.getMainCharacter().currentLocation.getY()+"\n\n");
		}
	}
	
	public static void processInventory(PrintWriter out, int layer, Core coreModule)
	{
		for(Item i : coreModule.gameModule.playerInventory.getContents())
		{
			i.save(out, layer, coreModule);
		}
	}
	
	public static void processParty(PrintWriter out, int layer, Core coreModule)
	{		
		//save each of our party units
		for(Unit u : coreModule.gameModule.partyData.party_members)
		{
			if(u!=null)
			{
				//save human and monster units separately
				if(u instanceof HumanUnit)
				{
					//unit tags
					printTabs(out, layer);
					out.write("[HUMANUNIT]\n");
					
					processHumanUnit(out, layer+1, coreModule, (HumanUnit) u);
					
					//unit tags
					printTabs(out, layer);
					out.write("[/HUMANUNIT]\n");
				}
				else
				{
					//unit tags
					printTabs(out, layer);
					out.write("[UNIT]\n");
					
					processUnit(out, layer+1, coreModule, u);
					
					//unit tags
					printTabs(out, layer);
					out.write("[/UNIT]\n");
				}
				
				out.flush();
			}
		}
	}
	
	public static void processHumanUnit(PrintWriter out, int layer, Core coreModule, HumanUnit currUnit)
	{
		//print out the gender
		printTabs(out, layer);
		if(currUnit.isMale)
		{
			out.write("[GENDER]\tMale\n");
		}
		else
		{
			out.write("[GENDER]\tFemale\n");
		}

		String outfit = currUnit.getOutfit();
		String hair = currUnit.getHair();
		String face = currUnit.getFace();

		printTabs(out, layer);
		out.write("[OUTFIT]\t"+outfit+"\n");
		printTabs(out, layer);
		out.write("[HAIR]\t"+hair+"\n");
		printTabs(out, layer);
		out.write("[FACE]\t"+face+"\n");
		
		
		processUnit(out, layer, coreModule, currUnit);
	}
	
	public static void processUnit(PrintWriter out, int layer, Core coreModule, Unit currUnit)
	{		
		//output our data
		printTabs(out, layer);
		out.write("[STAT]\t"+"Name\t"+currUnit.name+"\n");
		
		printTabs(out, layer);
		out.write("[STAT]\t"+"Title\t"+currUnit.title+"\n");
		
		printTabs(out, layer);
		out.write("[STAT]\t"+"Filename\t"+currUnit.getFilename()+"\n");
		
		printTabs(out, layer);
		out.write("[STAT]\t"+"Faction\t"+currUnit.faction+"\n");
		
		printTabs(out, layer);
		out.write("[STAT]\t"+"Money\t"+currUnit.getMoney()+"\n");
		
		printTabs(out, layer);
		out.write("[STAT]\t"+"Level\t"+currUnit.level+"\n");
		
		printTabs(out, layer);
		out.write("[STAT]\t"+"Exp\t"+currUnit.exp+"\n");
		
		printTabs(out, layer);
		out.write("[STAT]\t"+"Morale\t"+currUnit.Morale+"\n");

		printTabs(out, layer);
		out.write("[STAT]\t"+"Personality\t"+Common.PersonalityToString(currUnit.Personality)+"\n");
		

		printTabs(out, layer);
		out.write("[STAT]\t"+"Strength\t"+currUnit.Strength+"\n");
		printTabs(out, layer);
		out.write("[STAT]\t"+"Toughness\t"+currUnit.Toughness+"\n");
		printTabs(out, layer);
		out.write("[STAT]\t"+"Agility\t"+currUnit.Agility+"\n");
		printTabs(out, layer);
		out.write("[STAT]\t"+"Dexterity\t"+currUnit.Dexterity+"\n");
		printTabs(out, layer);
		out.write("[STAT]\t"+"Intelligence\t"+currUnit.Intelligence+"\n");
		printTabs(out, layer);
		out.write("[STAT]\t"+"Willpower\t"+currUnit.Willpower+"\n");
		printTabs(out, layer);
		out.write("[STAT]\t"+"Luck\t"+currUnit.Luck+"\n");
		printTabs(out, layer);
		out.write("[STAT]\t"+"Charisma\t"+currUnit.Charisma+"\n");

		printTabs(out, layer);
		out.write("[STAT]\t"+"Strength Gain\t"+currUnit.Strength_growth+"\n");
		printTabs(out, layer);
		out.write("[STAT]\t"+"Toughness Gain\t"+currUnit.Toughness_growth+"\n");
		printTabs(out, layer);
		out.write("[STAT]\t"+"Agility Gain\t"+currUnit.Agility_growth+"\n");
		printTabs(out, layer);
		out.write("[STAT]\t"+"Dexterity Gain\t"+currUnit.Dexterity_growth+"\n");
		printTabs(out, layer);
		out.write("[STAT]\t"+"Intelligence Gain\t"+currUnit.Intelligence_growth+"\n");
		printTabs(out, layer);
		out.write("[STAT]\t"+"Willpower Gain\t"+currUnit.Willpower_growth+"\n");
		printTabs(out, layer);
		out.write("[STAT]\t"+"Luck Gain\t"+currUnit.Luck_growth+"\n");
		printTabs(out, layer);
		out.write("[STAT]\t"+"Charisma Gain\t"+currUnit.Charisma_growth+"\n");

		printTabs(out, layer);
		out.write("[STAT]\t"+"Common Resist\t"+currUnit.resists[Common.TYPE_NONE]+"\n");
		printTabs(out, layer);
		out.write("[STAT]\t"+"Fire Resist\t"+currUnit.resists[Common.TYPE_FIRE]+"\n");
		printTabs(out, layer);
		out.write("[STAT]\t"+"Water Resist\t"+currUnit.resists[Common.TYPE_WATER]+"\n");
		printTabs(out, layer);
		out.write("[STAT]\t"+"Earth Resist\t"+currUnit.resists[Common.TYPE_EARTH]+"\n");
		printTabs(out, layer);
		out.write("[STAT]\t"+"Wind Resist\t"+currUnit.resists[Common.TYPE_WIND]+"\n");
		printTabs(out, layer);
		out.write("[STAT]\t"+"Life Resist\t"+currUnit.resists[Common.TYPE_LIFE]+"\n");
		printTabs(out, layer);
		out.write("[STAT]\t"+"Time Resist\t"+currUnit.resists[Common.TYPE_TIME]+"\n");
		printTabs(out, layer);
		out.write("[STAT]\t"+"Space Resist\t"+currUnit.resists[Common.TYPE_SPACE]+"\n");
		printTabs(out, layer);
		out.write("[STAT]\t"+"Void Resist\t"+currUnit.resists[Common.TYPE_VOID]+"\n");
		
		printTabs(out, layer);
		out.write("[STAT]\t"+"Animation Speed\t"+currUnit.animation_speed+"\n");
		
		printTabs(out, layer);
		out.write("[STAT]\t"+"Position\t"+currUnit.x+"\t"+currUnit.y+"\n");
		
		//save our items
		if(currUnit.weaponSlot!=null)
			currUnit.weaponSlot.save(out, layer, coreModule);
		if(currUnit.helmetSlot!=null)
			currUnit.helmetSlot.save(out, layer, coreModule);
		if(currUnit.chestpieceSlot!=null)
			currUnit.chestpieceSlot.save(out, layer, coreModule);
		if(currUnit.leggingsSlot!=null)
			currUnit.leggingsSlot.save(out, layer, coreModule);
		for(int i=0; i<currUnit.spellSlotCount; i++)
		{
			if(currUnit.spellSlots[i]!=null)
				currUnit.spellSlots[i].save(out, layer, coreModule);
		}
		
		//save all of our abilities
		for(Ability a : currUnit.attacks)
		{
			//do not process if it is a weapon FOR NOW
			if(a instanceof WeaponAbility)
			{
			}
			else if(a instanceof SpellAbility)
			{
				SpellAbility s = ((SpellAbility) a);
				
				if(!s.equipped)
				{
					//ability tags
					printTabs(out, layer);
					out.write("[SPELL]\t"+s.name+"\n");
					
					out.flush();
				}
			}
		}
	}
	
	public static void processMaps(PrintWriter out, int layer, Core coreModule)
	{		
		//save each of the maps for each planet
		for(String s : coreModule.gameModule.availableLocations)
		{
			for(int y=1; y<=8; y++)
			{
				for(int x=1; x<=16; x++)
				{
					Location l = coreModule.gameModule.locationModule.getLocation(s, x, y);
					
					//save its data
					if(l!=null && l.danger_level > 0)
					{
						printTabs(out, layer);
						out.write("[MAP]\t"+l.planet+"\t"+x+"\t"+y+"\t"+l.getOwner()+"\t"+l.danger_level+"\t"+l.level+"\n");
					}
					else if(l!=null && l.getMap() != null)
					{
						Town t = (Town) l.getMap();

						printTabs(out, layer);
						out.write("[TOWN]\n");

						printTabs(out, layer+1);
						out.write("Location\t"+l.planet+"\t"+x+"\t"+y+"\n");
						printTabs(out, layer+1);
						out.write("Faction\t"+l.getOwner()+"\n");
						printTabs(out, layer+1);
						out.write("Money\t"+t.getMoney()+"\n");
						printTabs(out, layer+1);
						out.write("Owner Loyalty\t"+t.getOwnerLoyalty()+"\n");
						printTabs(out, layer+1);
						out.write("Player Loyalty\t"+t.getPlayerLoyalty()+"\n");
						printTabs(out, layer+1);
						out.write("Military\t"+l.getMilitaryPower()+"\n");
						printTabs(out, layer+1);
						out.write("Population\t"+l.level+"\n");
						printTabs(out, layer+1);
						out.write("Taxrate\t"+t.getTaxRate()+"\n");
						
						if(t.hasSpaceport())
						{
							printTabs(out, layer+1);
							out.write("Building\tSpaceport\t1\n");
						}
						
						//print all of the buildings
						for(int j=7; j>=0; j--)
						{
							BuildingBase b = t.getBuildings()[j];
							
							if(b!=null)
							{
								printTabs(out, layer+1);
								out.write("Building\t"+b.getName()+"\t"+b.getLevel()+"\n");
								
								if(b.getShop()!=null)
								{
									Shop sh = b.getShop();
									//store the contents of the shop
									for(Item i : sh.getMerchandise())
									{
										if(i!=null)
											i.save(out, layer+2, coreModule);
									}
								}
								else if(b instanceof Inn)
								{
									Inn i = (Inn) b;
									for(Unit u : i.getHirable())
									{
										if(u instanceof HumanUnit)
										{
											//unit tags
											printTabs(out, layer+2);
											out.write("[HUMANUNIT]\n");
											
											processHumanUnit(out, layer+3, coreModule, (HumanUnit) u);
											
											//unit tags
											printTabs(out, layer+2);
											out.write("[/HUMANUNIT]\n");
										}
									}
								}
								else if(b instanceof Guild)
								{
									Guild g = (Guild) b;
									for(Quest q : g.guildQuests.getList())
									{
										//print out all of our quests
										printTabs(out, layer+2);
										out.write("[QUEST]\tQuest\t"+q.getDataString()+"\n");
									}
								}
								else if(b instanceof HouseBuilding)
								{
									//print out our furniture
									//object name, object floor, object x, object y, object rotation
									
									HouseBuilding hb = (HouseBuilding) b;
									House h = hb.getHouse();
									
									//in order of floors from 1 to N
									for(int k=1; k<=h.getFloorCount(); k++)
									{
										Floor f = h.getFloor(k);
										
										for(int i=0; i<h.getHeight()+2; i++)
										{
											for(int m=0; m<h.getWidth(); m++)
											{
												if(f.getFurnitureFloorWall()[i][m]!=null && !f.hasDud(f.getFurnitureFloorWall()[i][m]))
												{
													f.getFurnitureFloorWall()[i][m].save_house(out, layer+2, coreModule, k, m+1, i+1);
												}
											}
										}
										
										for(int i=0; i<h.getHeight(); i++)
										{
											for(int m=0; m<h.getWidth(); m++)
											{
												if(f.getFurnitureMediumLarge()[i][m]!=null && !f.hasDud(f.getFurnitureMediumLarge()[i][m]))
												{
													f.getFurnitureMediumLarge()[i][m].save_house(out, layer+2, coreModule, k, m+1, i+1);
												}
											}
										}
										
										for(int i=0; i<h.getHeight(); i++)
										{
											for(int m=0; m<h.getWidth(); m++)
											{
												if(f.getFurnitureSmall()[i][m]!=null && !f.hasDud(f.getFurnitureSmall()[i][m]))
												{
													f.getFurnitureSmall()[i][m].save_house(out, layer+2, coreModule, k, m+1, i+1);
												}
											}
										}
									}
									
									//do floor and wall stuff first
									//then medium and large stuff
									//then lastly small stuff
								}
							}
						}
						
						/*Building	Inn	2
						Building	Guard Tower	4
						Building	General Store	8
						[Stock]	Item
						Building	Blacksmith	5*/

						printTabs(out, layer);
						out.write("[/TOWN]\n");
					}
					
					//[MAP]	Water	15	5	No Faction	1
				}
			}
		}
		
		out.write("\n");
	}
}
