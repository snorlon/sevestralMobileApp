package com.snorlon.sevestralkingdoms.DataManagement;

import com.snorlon.sevestralkingdoms.Ability.Ability;
import com.snorlon.sevestralkingdoms.Ability.SpellAbility;
import com.snorlon.sevestralkingdoms.Buildings.Blacksmith;
import com.snorlon.sevestralkingdoms.Buildings.BuildingBase;
import com.snorlon.sevestralkingdoms.Buildings.GeneralStore;
import com.snorlon.sevestralkingdoms.Buildings.Guild;
import com.snorlon.sevestralkingdoms.Buildings.HouseBuilding;
import com.snorlon.sevestralkingdoms.Buildings.Inn;
import com.snorlon.sevestralkingdoms.Buildings.Market;
import com.snorlon.sevestralkingdoms.Buildings.Spellshop;
import com.snorlon.sevestralkingdoms.GameComponents.Common;
import com.snorlon.sevestralkingdoms.GameTools.Core;
import com.snorlon.sevestralkingdoms.Housing.Floor;
import com.snorlon.sevestralkingdoms.Housing.House;
import com.snorlon.sevestralkingdoms.Items.Armor;
import com.snorlon.sevestralkingdoms.Items.Furniture;
import com.snorlon.sevestralkingdoms.Items.Item;
import com.snorlon.sevestralkingdoms.Items.Spell;
import com.snorlon.sevestralkingdoms.Items.Weapon;
import com.snorlon.sevestralkingdoms.NPC.HumanUnit;
import com.snorlon.sevestralkingdoms.NPC.Unit;
import com.snorlon.sevestralkingdoms.Places.Location;
import com.snorlon.sevestralkingdoms.Places.Town;
import com.snorlon.sevestralkingdoms.Quests.Quest;
import com.snorlon.sevestralkingdoms.Quests.QuestList;

public class LoadSaveManager
{
	public static int processSave(String r, Core coreModule)
	{
		//load in lines until we get what we want
		int startIndex = 0;
		int endIndex = 0;
		
		for(int stringIndex = 0; stringIndex < r.length(); stringIndex++)
		{
			
			if(r.charAt(stringIndex) == '\n' || r.charAt(stringIndex) == '\0')
			{
				endIndex = stringIndex;
				
				if(endIndex - startIndex <= 0)
					continue;
				
				String partString = r.substring(startIndex , endIndex).trim();
				
				if(partString.length() <= 0)
					continue;
				
				startIndex = stringIndex;
				endIndex = stringIndex;

				//truncate up to the first non tab
				String parts[] = (partString).split("\t");

				if(parts[0].equals("[/SAVEFILE]"))
				{
					return stringIndex;
				}
				else if(parts[0].equals("[PARTY]"))
				{
					stringIndex = processParty(r, stringIndex+1, coreModule);
					startIndex = stringIndex;
					endIndex = stringIndex;
				}
				else if(parts[0].equals("[INVENTORY]"))
				{
					stringIndex = processInventory(r, stringIndex+1, coreModule);
					startIndex = stringIndex;
					endIndex = stringIndex;
				
				}
				else if(parts[0].equals("[CONFIG]"))
				{
					stringIndex = processConfig(r, stringIndex+1, coreModule);
					startIndex = stringIndex;
					endIndex = stringIndex;
				
				}
				else if(parts[0].equals("[VERSION]"))
				{
					int vvalue = Integer.parseInt(parts[1].trim());
					
					if(vvalue < coreModule.getMinVersion())
					{
						System.out.println("Savefile version incompatible!");
						return endIndex;
					}
				}
				else if(parts[0].equals("[MAP]"))
				{
					Location l = coreModule.gameModule.locationModule.getLocation(parts[1], Integer.parseInt(parts[2]), Integer.parseInt(parts[3]));
					
					if(l!=null)
					{
						l.setOwner(parts[4]);
						l.danger_level = Integer.parseInt(parts[5]);
						l.level = Integer.parseInt(parts[6]);
					}
				}
				else if(parts[0].equals("[TOWN]"))
				{
					stringIndex = processTown(r, stringIndex+1, coreModule);
					startIndex = stringIndex;
					endIndex = stringIndex;
				}
				else if(parts[0].equals("[TURN]"))
				{
					int vvalue = Integer.parseInt(parts[1].trim());
					
					if(vvalue >= 1)
					{
						coreModule.gameModule.setTurn(vvalue);
					}
				}
				else if(parts[0].equals("[QUEST]"))
				{
					QuestList ql = null;
					Quest q = null;
					
					if(parts[1].equals("Daily"))
					{
						ql = coreModule.gameModule.dailyQuests;
					}
					else if(parts[1].equals("Normal"))
					{
						ql = coreModule.gameModule.playerQuests;
					}
					
					if(ql!=null)
					{
						int rType = Integer.parseInt(parts[2]);
						int dif = Integer.parseInt(parts[3]);
						String planet = parts[4].trim();
						int x = Integer.parseInt(parts[5]);
						int y = Integer.parseInt(parts[6]);
						String target = parts[7].trim();
						int tNum = Integer.parseInt(parts[8]);
						int tCNum = Integer.parseInt(parts[9]);
						int g = Integer.parseInt(parts[10]);
						int a = Integer.parseInt(parts[11]);
						String item = parts[12].trim();
						int lv = Integer.parseInt(parts[13]);
						
						switch(rType)
						{
							case Quest.REWARD_MONEY:
								q = ql.addQuest(rType, dif, planet, x, y, target, lv, tNum, g);
								break;
							case Quest.REWARD_ITEM:
								q = ql.addQuest(rType, dif, planet, x, y, target, lv, tNum, item);
								break;
						}
						
						if(q!=null)
							q.setCurrQty(tCNum);
					}
				}
				else if(parts[0].equals("[LOCATION]"))
				{
					coreModule.gameModule.travelLocation(parts[1].trim(), Integer.parseInt(parts[2].trim()), Integer.parseInt(parts[3].trim()), true);
				}
			}
			else if(stringIndex == r.length() - 1)
			{
				return endIndex;
			}
		}
		
		//return the index we end on for the prior area to pick up on it
		return endIndex;
	}
	
	public static int processInventory(String r, int oldIndex, Core coreModule)
	{
		//load in lines until we get what we want
		int startIndex = oldIndex;
		int endIndex = oldIndex;
		
		for(int stringIndex = oldIndex; stringIndex < r.length(); stringIndex++)
		{
			
			if(r.charAt(stringIndex) == '\n' || r.charAt(stringIndex) == '\0')
			{
				endIndex = stringIndex;
				
				if(endIndex - startIndex <= 0)
					continue;
				
				String partString = r.substring(startIndex , endIndex).trim();
				
				if(partString.length() <= 0)
					continue;
				
				startIndex = stringIndex;
				endIndex = stringIndex;

				//truncate up to the first non tab
				String parts[] = (partString).split("\t");
				
				if(parts[0].equals("[/INVENTORY]"))
				{

					return stringIndex;
				}
				else if(parts[0].equals("[ITEM]"))
				{
					Item i = coreModule.gameModule.itemLookupModule.getItem(parts[1].trim());
					
					if(i!=null)
					{
						Item newItem = new Item(i);
						coreModule.gameModule.playerInventory.moveItem(newItem);
					}
				}
				else if(parts[0].equals("[FURNITURE]"))
				{
					Item i = coreModule.gameModule.itemLookupModule.getItem(parts[1].trim());
					
					if(i!=null && i instanceof Furniture)
					{
						Item newItem = new Furniture((Furniture) i);
						coreModule.gameModule.playerInventory.moveItem(newItem);
					}
				}
				else if(parts[0].equals("[WEAPON]"))
				{
					Item i = coreModule.gameModule.itemLookupModule.getItem(parts[1].trim());
					
					if(i!=null)
					{
						Weapon newItem = new Weapon(i);
						coreModule.gameModule.playerInventory.moveItem(newItem);
					}
				}
				else if(parts[0].equals("[ARMOR]"))
				{
					Item i = coreModule.gameModule.itemLookupModule.getItem(parts[1].trim());
					
					if(i!=null)
					{
						Armor newItem = new Armor(i);
						coreModule.gameModule.playerInventory.moveItem(newItem);
					}
				}
				else if(parts[0].equals("[SPELLBOOK]"))
				{
					Item i = coreModule.gameModule.itemLookupModule.getItem(parts[1].trim());
					
					if(i!=null)
					{
						Item newItem = new Spell(i);
						coreModule.gameModule.playerInventory.moveItem(newItem);
					}
				}
			}
			else if(stringIndex == r.length() - 1)
			{
				return endIndex;
			}
		}
		
		//return the index we end on for the prior area to pick up on it
		return endIndex;
	}
	
	public static int processConfig(String r, int oldIndex, Core coreModule)
	{
		//load in lines until we get what we want
		int startIndex = oldIndex;
		int endIndex = oldIndex;
		
		for(int stringIndex = oldIndex; stringIndex < r.length(); stringIndex++)
		{
			
			if(r.charAt(stringIndex) == '\n' || r.charAt(stringIndex) == '\0')
			{
				endIndex = stringIndex;
				
				if(endIndex - startIndex <= 0)
					continue;
				
				String partString = r.substring(startIndex , endIndex).trim();
				
				if(partString.length() <= 0)
					continue;
				
				startIndex = stringIndex;
				endIndex = stringIndex;

				//truncate up to the first non tab
				String parts[] = (partString).split("\t");
				
				if(parts[0].equals("[/CONFIG]"))
				{

					return stringIndex;
				}
				else if(parts[0].equals("FPS"))
				{
					if(parts[1].equals("on"))
						coreModule.gameModule.gameConfig.enableFPS();
					else
						coreModule.gameModule.gameConfig.disableFPS();
				}
				else if(parts[0].equals("SFX"))
				{
					coreModule.gameModule.gameConfig.setSFX(Integer.parseInt(parts[1]));
				}
				else if(parts[0].equals("BGM"))
				{
					coreModule.gameModule.gameConfig.setBGM(Integer.parseInt(parts[1]));
				}
			}
			else if(stringIndex == r.length() - 1)
			{
				return endIndex;
			}
		}
		
		//return the index we end on for the prior area to pick up on it
		return endIndex;
	}
	
	public static int processParty(String r, int oldIndex, Core coreModule)
	{
		//load in lines until we get what we want
		int startIndex = oldIndex;
		int endIndex = oldIndex;
		
		for(int stringIndex = oldIndex; stringIndex < r.length(); stringIndex++)
		{
			
			if(r.charAt(stringIndex) == '\n' || r.charAt(stringIndex) == '\0')
			{
				endIndex = stringIndex;
				
				if(endIndex - startIndex <= 0)
					continue;
				
				String partString = r.substring(startIndex , endIndex).trim();
				
				if(partString.length() <= 0)
					continue;
				
				startIndex = stringIndex;
				endIndex = stringIndex;

				//truncate up to the first non tab
				String parts[] = (partString).split("\t");
				
				if(parts[0].equals("[/PARTY]"))
				{

					return stringIndex;
				}
				else if(parts[0].equals("[UNIT]"))
				{
					//create a unit
					Unit newUnit = new Unit("Error", 1, new float[]{5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f}, new float[]{1.0f,1.0f,1.0f,1.0f,1.0f,1.0f,1.0f,1.0f}, new float[]{1.0f,1.0f,1.0f,1.0f,1.0f,1.0f,1.0f,1.0f,1.0f}, "Player", 1.0f, "", false, 0, null, false);
					newUnit.coreModule = coreModule;
					
					//throw it into a loader
					stringIndex = processUnit(r, stringIndex+1, coreModule, newUnit, false);
					startIndex = stringIndex;
					endIndex = stringIndex;
					
					//do some post-loading management
					newUnit.recalcMaxExp();
					
					//store it afterwards in the party if able
					//or toss it if no room
					if(coreModule.gameModule.partyData.countFree() >= 1)
					{
						coreModule.gameModule.partyData.addUnit(newUnit);
					}
				}
				else if(parts[0].equals("[HUMANUNIT]"))
				{
					//create a unit
					Unit newUnit = new HumanUnit("Error", 1, new float[]{5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f}, new float[]{1.0f,1.0f,1.0f,1.0f,1.0f,1.0f,1.0f,1.0f}, new float[]{1.0f,1.0f,1.0f,1.0f,1.0f,1.0f,1.0f,1.0f,1.0f}, "Player", 1.0f, "", false, 0, null, false);
					newUnit.coreModule = coreModule;
					
					//throw it into a loader
					stringIndex = processUnit(r, stringIndex+1, coreModule, newUnit, true);
					startIndex = stringIndex;
					endIndex = stringIndex;
					
					//do some post-loading management
					newUnit.recalcMaxExp();
					
					//store it afterwards in the party if able
					//or toss it if no room
					if(coreModule.gameModule.partyData.countFree() >= 1)
					{
						coreModule.gameModule.partyData.addUnit(newUnit);
					}
				}
			}
			else if(stringIndex == r.length() - 1)
			{
				return endIndex;
			}
		}
		
		//return the index we end on for the prior area to pick up on it
		return endIndex;
	}
	
	public static int processTown(String r, int oldIndex, Core coreModule)
	{
		//load in lines until we get what we want
		int startIndex = oldIndex;
		int endIndex = oldIndex;
		
		Location l = null;
		Town t = null;
		
		BuildingBase lastBuilding = null;
		
		for(int stringIndex = oldIndex; stringIndex < r.length(); stringIndex++)
		{
			
			if(r.charAt(stringIndex) == '\n' || r.charAt(stringIndex) == '\0')
			{
				endIndex = stringIndex;
				
				if(endIndex - startIndex <= 0)
					continue;
				
				String partString = r.substring(startIndex , endIndex).trim();
				
				if(partString.length() <= 0)
					continue;
				
				startIndex = stringIndex;
				endIndex = stringIndex;

				//truncate up to the first non tab
				String parts[] = (partString).split("\t");

				if(parts[0].equals("[/TOWN]"))
				{

					return stringIndex;
				}
				else if(parts[0].equals("Location"))
				{
					l = coreModule.gameModule.locationModule.getLocation(parts[1], Integer.parseInt(parts[2]),Integer.parseInt(parts[3]));
					
					if(l!=null && l.getMap()!=null)
					{
						t = (Town) l.getMap();
						
						//purge the town buildings while we're at it
						for(int i=0; i<Town.buildingPlotCounts; i++)
						{
							if(t.getBuildings()[i]!=null)
								t.getBuildings()[i].DestroyImage();
							t.getBuildings()[i] = null;
						}
					}
				}
				else if(parts[0].equals("Faction"))
				{
					if(l!=null)
					{
						l.setOwner(parts[1]);
					}
				}
				else if(parts[0].equals("Money"))
				{
					if(t!=null)
					{
						t.setMoney(Integer.parseInt(parts[1]));
					}
				}
				else if(parts[0].equals("Player Loyalty"))
				{
					if(t!=null)
					{
						t.setPlayerLoyalty(Integer.parseInt(parts[1]));
					}
				}
				else if(parts[0].equals("Owner Loyalty"))
				{
					if(t!=null)
					{
						t.setOwnerLoyalty(Integer.parseInt(parts[1]));
					}
				}
				else if(parts[0].equals("Military"))
				{
					if(t!=null)
					{
						l.setMilitaryPower(Integer.parseInt(parts[1]));
					}
				}
				else if(parts[0].equals("Population"))
				{
					if(l!=null)
					{
						l.level = Integer.parseInt(parts[1]);
					}
				}
				else if(parts[0].equals("Taxrate"))
				{
					if(t!=null)
					{
						t.setTax(Double.parseDouble(parts[1]));
					}
				}
				else if(parts[0].equals("Building"))
				{
					if(t!=null)
					{
						t.SpawnBuilding(parts[1], Integer.parseInt(parts[2]));
						BuildingBase b = t.getBuilding(parts[1]);
						
						//force the buildings to restock if no data
						if(lastBuilding!=null && (lastBuilding instanceof GeneralStore || lastBuilding instanceof Blacksmith || lastBuilding instanceof Market || lastBuilding instanceof Spellshop) && lastBuilding.getShop() != null && lastBuilding.getShop().countMerchandise() == 0)
						{
							lastBuilding.getShop().changeMerchandise(lastBuilding.getLevel());
						}
						
						lastBuilding = b;
						
						if(b!=null && b.getShop() != null)
						{
							//we're going to get the merchandise ourselves teehee
							b.getShop().clearMerchandise();
						}
					}
				}
				else if(parts[0].equals("[FURNITUREINTERNAL]"))
				{
					if(lastBuilding!=null && lastBuilding instanceof HouseBuilding)
					{
						Item i = coreModule.gameModule.itemLookupModule.getItem(parts[1].trim());
						
						if(i!=null && i instanceof Furniture)
						{
							Furniture newItem = new Furniture((Furniture) i);
							//lastBuilding.getShop().moveItem(newItem);
							//put the new furniture on the appropriate floor of the house, if we're able to
							HouseBuilding h = (HouseBuilding) lastBuilding;
							h.initialize();//MUST initialize house beforehand
							House m = h.getHouse();
							Floor f = m.getFloor(Integer.parseInt(parts[2].trim()));
							int yOffset = 0;
							if(newItem.getFurnitureType() == Furniture.FURNITURE_FLOOR || newItem.getFurnitureType() == Furniture.FURNITURE_WALL)
								yOffset = -2;
							Furniture a = f.placeFurniture(newItem, Integer.parseInt(parts[3].trim()), Integer.parseInt(parts[4].trim())+yOffset, Integer.parseInt(parts[5].trim()));
							
							if(a!=null)
							{
								System.out.println("Failed to load furniture");
							}
						}
					}
				}
				else if(parts[0].equals("[ITEM]"))
				{
					if(lastBuilding!=null && lastBuilding.getShop() != null)
					{
						Item i = coreModule.gameModule.itemLookupModule.getItem(parts[1].trim());
						
						if(i!=null)
						{
							Item newItem = new Item(i);
							lastBuilding.getShop().moveItem(newItem);
						}
					}
				}
				else if(parts[0].equals("[HUMANUNIT]"))
				{
					if(lastBuilding!=null && lastBuilding instanceof Inn)
					{
						//create a unit
						Unit newUnit = new HumanUnit("Error", 1, new float[]{5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f,5.0f}, new float[]{1.0f,1.0f,1.0f,1.0f,1.0f,1.0f,1.0f,1.0f}, new float[]{1.0f,1.0f,1.0f,1.0f,1.0f,1.0f,1.0f,1.0f,1.0f}, "Player", 1.0f, "", false, 0, null, false);
						newUnit.coreModule = coreModule;
						
						//throw it into a loader
						stringIndex = processUnit(r, stringIndex+1, coreModule, newUnit, true);
						startIndex = stringIndex;
						endIndex = stringIndex;
						
						//do some post-loading management
						newUnit.recalcMaxExp();
						
						//store it afterwards in the party if able
						//or toss it if no room
						Inn i = (Inn) lastBuilding;
						if(i.getHirable() != null)
						{
							i.getHirable().add(newUnit);
						}
					}
				}
				else if(parts[0].equals("[FURNITURE]"))
				{
					if(lastBuilding!=null && lastBuilding.getShop() != null)
					{
						Item i = coreModule.gameModule.itemLookupModule.getItem(parts[1].trim());
						
						if(i!=null && i instanceof Furniture)
						{
							Item newItem = new Furniture((Furniture) i);
							lastBuilding.getShop().moveItem(newItem);
						}
					}
				}
				else if(parts[0].equals("[WEAPON]"))
				{
					if(lastBuilding!=null && lastBuilding.getShop() != null)
					{

						Item ni = coreModule.gameModule.itemLookupModule.getItem(parts[1].trim());
							
						if(ni!=null)
						{
							Weapon s = new Weapon(ni);

							lastBuilding.getShop().moveItem(s);
						}
					}
				}
				else if(parts[0].equals("[QUEST]"))
				{
					if(lastBuilding!=null && lastBuilding instanceof Guild)
					{
						Guild guild = (Guild) lastBuilding;
						
						int rType = Integer.parseInt(parts[2]);
						int dif = Integer.parseInt(parts[3]);
						String planet = parts[4].trim();
						int x = Integer.parseInt(parts[5]);
						int y = Integer.parseInt(parts[6]);
						String target = parts[7].trim();
						int tNum = Integer.parseInt(parts[8]);
						int g = Integer.parseInt(parts[10]);
						int a = Integer.parseInt(parts[11]);
						String item = parts[12].trim();
						int lv = Integer.parseInt(parts[13]);
						
						Quest q;
						
						
						switch(rType)
						{
							case Quest.REWARD_MONEY:
								q = guild.guildQuests.addQuest(rType, dif, planet, x, y, target, lv, tNum, g);
								break;
							case Quest.REWARD_ITEM:
								q = guild.guildQuests.addQuest(rType, dif, planet, x, y, target, lv, tNum, item);
								break;
						}
					}
					
				}
				else if(parts[0].equals("[SPELLBOOK]"))
				{
					if(lastBuilding!=null && lastBuilding.getShop() != null)
					{
						Item i = coreModule.gameModule.itemLookupModule.getItem(parts[1].trim());
						
						if(i!=null)
						{
							Item newItem = new Spell(i);
							lastBuilding.getShop().moveItem(newItem);
						}
					}
				}
				else if(parts[0].equals("[ARMOR]"))
				{
					if(lastBuilding!=null && lastBuilding.getShop() != null)
					{

						Item ni = coreModule.gameModule.itemLookupModule.getItem(parts[1].trim());
							
						if(ni!=null)
						{
							Armor s = new Armor(ni);

							lastBuilding.getShop().moveItem(s);
						}
					}
				}
			}
			else if(stringIndex == r.length() - 1)
			{
				return endIndex;
			}
		}
		
		//return the index we end on for the prior area to pick up on it
		return endIndex;
	}
	
	public static int processUnit(String r, int oldIndex, Core coreModule, Unit newUnit, boolean isHuman)
	{
		//load in lines until we get what we want
		int startIndex = oldIndex;
		int endIndex = oldIndex;
		
		for(int stringIndex = oldIndex; stringIndex < r.length(); stringIndex++)
		{
			
			if(r.charAt(stringIndex) == '\n' || r.charAt(stringIndex) == '\0')
			{
				endIndex = stringIndex;
				
				if(endIndex - startIndex <= 0)
					continue;
				
				String partString = r.substring(startIndex , endIndex).trim();
				
				if(partString.length() <= 0)
					continue;
				
				startIndex = stringIndex;
				endIndex = stringIndex;

				//truncate up to the first non tab
				String parts[] = (partString).split("\t");
				for(String s : parts)
					s = s.trim();

				if(parts[0].equals("[/UNIT]"))
				{
					return stringIndex;
				}
				else if(parts[0].equals("[/HUMANUNIT]"))
				{
					return stringIndex;
				}
				else if(parts[0].equals("[OUTFIT]") && isHuman)
				{
					newUnit.dress(parts[1].trim(), "", "");
				}
				else if(parts[0].equals("[HAIR]") && isHuman)
				{
					newUnit.dress("", parts[1].trim(), "");
				}
				else if(parts[0].equals("[FACE]") && isHuman)
				{
					newUnit.dress("", "", parts[1].trim());
				}
				else if(parts[0].equals("[GENDER]") && isHuman)
				{
					if(parts[1].trim().equals("Male"))
					{
						newUnit.setGender(true);
					}
					else
					{
						newUnit.setGender(false);
					}
				}
				else if(parts[0].equals("[STAT]"))
				{
					//determine what to do based on this
					if(parts[1].equals("Name"))
					{
						newUnit.name = parts[2];
						newUnit.generateImageID();
					}
					else if(parts[1].equals("Title"))
					{
						newUnit.title = parts[2];
					}
					else if(parts[1].equals("Money"))
					{
						newUnit.setMoney(Integer.parseInt(parts[2]));
					}
					else if(parts[1].equals("Filename"))
						newUnit.setFilename(parts[2]);
					else if(parts[1].equals("Faction"))
						newUnit.faction = parts[2];
					else if(parts[1].equals("Level"))
					{
						newUnit.level = Integer.parseInt(parts[2]);
						newUnit.display_level = newUnit.level;
					}
					else if(parts[1].equals("Exp"))
					{
						newUnit.exp = Double.parseDouble(parts[2]);
						newUnit.display_exp = newUnit.exp;
					}
					else if(parts[1].equals("Morale"))
						newUnit.Morale = Integer.parseInt(parts[2]);
					else if(parts[1].equals("Personality"))
					{
						if(parts[2].equals("Reckless"))
							newUnit.Personality = Common.PERSONALITY_RECKLESS;
						else if(parts[2].equals("Neutral"))
							newUnit.Personality = Common.PERSONALITY_NEUTRAL;
						else if(parts[2].equals("Greedy"))
							newUnit.Personality = Common.PERSONALITY_GREEDY;
						else if(parts[2].equals("Survivalist"))
							newUnit.Personality = Common.PERSONALITY_SURVIVALIST;
						else if(parts[2].equals("Coward"))
							newUnit.Personality = Common.PERSONALITY_COWARD;
						else if(parts[2].equals("Adventurer"))
							newUnit.Personality = Common.PERSONALITY_ADVENTURER;
						else if(parts[2].equals("Warrior"))
							newUnit.Personality = Common.PERSONALITY_WARRIOR;
						else if(parts[2].equals("Selfless"))
							newUnit.Personality = Common.PERSONALITY_SELFLESS;
						else if(parts[2].equals("Paranoid"))
							newUnit.Personality = Common.PERSONALITY_PARANOID;
						else if(parts[2].equals("Romantic"))
							newUnit.Personality = Common.PERSONALITY_ROMANTIC;
						else if(parts[2].equals("Courageous"))
							newUnit.Personality = Common.PERSONALITY_COURAGEOUS;
						else
							newUnit.Personality = Common.PERSONALITY_NEUTRAL;
					}
					else if(parts[1].equals("Strength"))
						newUnit.Strength = Float.parseFloat(parts[2]);
					else if(parts[1].equals("Toughness"))
						newUnit.Toughness = Float.parseFloat(parts[2]);
					else if(parts[1].equals("Agility"))
						newUnit.Agility = Float.parseFloat(parts[2]);
					else if(parts[1].equals("Dexterity"))
						newUnit.Dexterity = Float.parseFloat(parts[2]);
					else if(parts[1].equals("Intelligence"))
						newUnit.Intelligence = Float.parseFloat(parts[2]);
					else if(parts[1].equals("Willpower"))
						newUnit.Willpower = Float.parseFloat(parts[2]);
					else if(parts[1].equals("Luck"))
						newUnit.Luck = Float.parseFloat(parts[2]);
					else if(parts[1].equals("Charisma"))
						newUnit.Charisma = Float.parseFloat(parts[2]);
					else if(parts[1].equals("Strength Gain"))
						newUnit.Strength_growth = Float.parseFloat(parts[2]);
					else if(parts[1].equals("Toughness Gain"))
						newUnit.Toughness_growth = Float.parseFloat(parts[2]);
					else if(parts[1].equals("Agility Gain"))
						newUnit.Agility_growth = Float.parseFloat(parts[2]);
					else if(parts[1].equals("Dexterity Gain"))
						newUnit.Dexterity_growth = Float.parseFloat(parts[2]);
					else if(parts[1].equals("Intelligence Gain"))
						newUnit.Intelligence_growth = Float.parseFloat(parts[2]);
					else if(parts[1].equals("Willpower Gain"))
						newUnit.Willpower_growth = Float.parseFloat(parts[2]);
					else if(parts[1].equals("Luck Gain"))
						newUnit.Luck_growth = Float.parseFloat(parts[2]);
					else if(parts[1].equals("Charisma Gain"))
						newUnit.Charisma_growth = Float.parseFloat(parts[2]);
					else if(parts[1].equals("Common Resist"))
						newUnit.resists[Common.TYPE_NONE] = Float.parseFloat(parts[2]);
					else if(parts[1].equals("Fire Resist"))
						newUnit.resists[Common.TYPE_FIRE] = Float.parseFloat(parts[2]);
					else if(parts[1].equals("Water Resist"))
						newUnit.resists[Common.TYPE_WATER] = Float.parseFloat(parts[2]);
					else if(parts[1].equals("Earth Resist"))
						newUnit.resists[Common.TYPE_EARTH] = Float.parseFloat(parts[2]);
					else if(parts[1].equals("Wind Resist"))
						newUnit.resists[Common.TYPE_WIND] = Float.parseFloat(parts[2]);
					else if(parts[1].equals("Life Resist"))
						newUnit.resists[Common.TYPE_LIFE] = Float.parseFloat(parts[2]);
					else if(parts[1].equals("Time Resist"))
						newUnit.resists[Common.TYPE_TIME] = Float.parseFloat(parts[2]);
					else if(parts[1].equals("Space Resist"))
						newUnit.resists[Common.TYPE_SPACE] = Float.parseFloat(parts[2]);
					else if(parts[1].equals("Void Resist"))
						newUnit.resists[Common.TYPE_VOID] = Float.parseFloat(parts[2]);
					else if(parts[1].equals("Animation Speed"))
						newUnit.animation_speed = Float.parseFloat(parts[2]);
					else if(parts[1].equals("Position"))
					{
						newUnit.x = Integer.parseInt(parts[2]);
						newUnit.y = Integer.parseInt(parts[3]);
					}
				}
				else if(parts[0].equals("[SPELL]"))
				{
					Ability a = coreModule.getAbility(parts[1].trim());
					
					if(a!=null)
					{
						new SpellAbility(newUnit.attacks,a);
					}
				}
				else if(parts[0].equals("[SPELLBOOK]"))
				{
					Item ni = coreModule.gameModule.itemLookupModule.getItem(parts[1].trim());
						
					if(ni!=null)
					{
						Spell s = new Spell(ni);
						
						boolean added = false;
						for(int i=0; i<newUnit.spellSlotCount && !added; i++)
						{
							if(newUnit.spellSlots[i]==null)
							{
								newUnit.equipSpell(s, i);
								added = true;
							}
						}
					}
				}
				else if(parts[0].equals("[WEAPON]"))
				{
					Item ni = coreModule.gameModule.itemLookupModule.getItem(parts[1].trim());
						
					if(ni!=null)
					{
						Weapon s = new Weapon(ni);
						
						newUnit.equipWeapon(s);	
					}
				}
				else if(parts[0].equals("[ARMOR]"))
				{
					Item ni = coreModule.gameModule.itemLookupModule.getItem(parts[1].trim());
						
					if(ni!=null)
					{
						Armor s = new Armor(ni);
						
						newUnit.equipArmor(s);	
					}
				}
			}
			else if(stringIndex == r.length() - 1)
			{
				return endIndex;
			}
		}
		
		//return the index we end on for the prior area to pick up on it
		return endIndex;
	}
	
	public static int processAbility(String r, int oldIndex, Core coreModule, Ability newAbility)
	{
		//load in lines until we get what we want
		int startIndex = oldIndex;
		int endIndex = oldIndex;
		
		
		for(int stringIndex = oldIndex; stringIndex < r.length(); stringIndex++)
		{
			
			if(r.charAt(stringIndex) == '\n' || r.charAt(stringIndex) == '\0')
			{
				endIndex = stringIndex;
				
				if(endIndex - startIndex <= 0)
					continue;
				
				String partString = r.substring(startIndex , endIndex).trim();
				
				if(partString.length() <= 0)
					continue;
				
				startIndex = stringIndex;
				endIndex = stringIndex;

				//truncate up to the first non tab
				String parts[] = (partString).split("\t");
				for(String s : parts)
					s = s.trim();
				
				if(parts[0].equals("[/ABILITY]"))
				{
					return stringIndex;
				}
				else if(parts[0].equals("[STAT]"))
				{
					//determine what to do based on this
					if(parts[1].equals("Name"))
					{
						newAbility.name = parts[2];
					}
					else if(parts[1].equals("Icon"))
					{
						newAbility.iconName = parts[2];
					}
					else if(parts[1].equals("Damage"))
					{
						newAbility.setRawDamageMultiplier(Double.parseDouble(parts[2]));
					}
					else if(parts[1].equals("Type1"))
					{
						newAbility.type1 = Common.StringToType(parts[2]);
					}
					else if(parts[1].equals("Type2"))
					{
						newAbility.type2 = Common.StringToType(parts[2]);
					}
					else if(parts[1].equals("Physical"))
					{
						newAbility.attackType = Common.PHYSICAL;
					}
					else if(parts[1].equals("Magical"))
					{
						newAbility.attackType = Common.MAGICAL;
					}
					else if(parts[1].equals("Agile"))
					{
						newAbility.attackType = Common.AGILE;
					}
				}
			}
			else if(stringIndex == r.length() - 1)
			{
				return endIndex;
			}
		}
		
		//return the index we end on for the prior area to pick up on it
		return endIndex;
	}
	
	public static int processSpellAbility(String r, int oldIndex, Core coreModule, SpellAbility newAbility)
	{
		//load in lines until we get what we want
		int startIndex = oldIndex;
		int endIndex = oldIndex;
		
		
		for(int stringIndex = oldIndex; stringIndex < r.length(); stringIndex++)
		{
			
			if(r.charAt(stringIndex) == '\n' || r.charAt(stringIndex) == '\0')
			{
				endIndex = stringIndex;
				
				if(endIndex - startIndex <= 0)
					continue;
				
				String partString = r.substring(startIndex , endIndex).trim();
				
				if(partString.length() <= 0)
					continue;
				
				startIndex = stringIndex;
				endIndex = stringIndex;

				//truncate up to the first non tab
				String parts[] = (partString).split("\t");
				for(String s : parts)
					s = s.trim();
				
				if(parts[0].equals("[/SPELL ABILITY]"))
				{					
					newAbility.calculateParticleFX();
					
					return stringIndex;
				}
				else if(parts[0].equals("[STAT]"))
				{
					//determine what to do based on this
					if(parts[1].equals("Name"))
					{
						newAbility.name = parts[2];
					}
					else if(parts[1].equals("Icon"))
					{
						newAbility.iconName = parts[2];
					}
					else if(parts[1].equals("Damage"))
					{
						newAbility.setRawDamageMultiplier(Double.parseDouble(parts[2]));
					}
					else if(parts[1].equals("Type1"))
					{
						newAbility.type1 = Common.StringToType(parts[2]);
					}
					else if(parts[1].equals("Type2"))
					{
						newAbility.type2 = Common.StringToType(parts[2]);
					}
					else if(parts[1].equals("Physical"))
					{
						newAbility.attackType = Common.PHYSICAL;
					}
					else if(parts[1].equals("Magical"))
					{
						newAbility.attackType = Common.MAGICAL;
					}
					else if(parts[1].equals("Agile"))
					{
						newAbility.attackType = Common.AGILE;
					}
				}
			}
			else if(stringIndex == r.length() - 1)
			{				
				newAbility.calculateParticleFX();
				
				return endIndex;
			}
		}
		
		//return the index we end on for the prior area to pick up on it
		return endIndex;
	}
}
