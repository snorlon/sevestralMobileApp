package com.snorlon.sevestralkingdoms.Interfaces;

import com.snorlon.sevestralkingdoms.Buildings.Guild;
import com.snorlon.sevestralkingdoms.GameTools.Core;
import com.snorlon.sevestralkingdoms.GameTools.Gesture;
import com.snorlon.sevestralkingdoms.GameTools.InterfaceElement;
import com.snorlon.sevestralkingdoms.NPC.CreatureData;
import com.snorlon.sevestralkingdoms.NPC.Unit;
import com.snorlon.sevestralkingdoms.Places.Location;
import com.snorlon.sevestralkingdoms.Places.Map;
import com.snorlon.sevestralkingdoms.Places.Town;
import com.snorlon.sevestralkingdoms.Quests.Quest;
import com.snorlon.sevestralkingdoms.Quests.QuestList;
import com.snorlon.sevestralkingdoms.Items.Item;

public class InterfaceQuestDetailed extends GenericInterface
{
	public final static int LIST_NORMAL = 0;
	public final static int LIST_DAILY = 1;
	public final static int LIST_ACHIEVEMENT = 2;
	
	public final static int ORIGIN_QUEST_LIST = 0;
	public final static int ORIGIN_GUILD = 1;
	
	int currIndex = 2;
	
	int mode = LIST_NORMAL;
	int origin = ORIGIN_QUEST_LIST;
	
	InterfaceElement rebuildContainer = null;
	
	public InterfaceQuestDetailed(Core newCore)
	{
		super(newCore);
		// TODO Auto-generated constructor stub
		
		id = "InterfaceQuestDetailed";
	}
	
	//used to pull up the particular screen in question
	public void activate()
	{		
		//currIndex = coreModule.random.nextInt(6);
		
		//create a gui container
		newElement = new InterfaceElement(coreModule, "InterfaceContainer", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 720, 1180, 0, 0);
		renderModule.addElement("CenterNode", newElement);
		
		//bg
		newElement = new InterfaceElement(coreModule, "bg", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 718, 1280, 24, 51, 1.0f, 1.0f, "menu/menubg.png");
		renderModule.addElement("InterfaceContainer", newElement);
		
		//party bg
		newElement = new InterfaceElement(coreModule, "background_bg", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 718, 1280, -24, -50, 1.0f, 1.0f, "interfaces/background.png");
		renderModule.addElement("bg", newElement);
		newElement = new InterfaceElement(coreModule, "background_bg2", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 718, 1280, -24, -50, 1.0f, 1.0f, "interfaces/quests/background2.png");
		renderModule.addElement("bg", newElement);

		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 700, 70, 140, 425, 2.0f, "Quest Details", coreModule.renderModule.FONT_IMPACT);
		renderModule.addElement("bg", newElement);
		
		//rebuild our rendering container
		rebuildContainer = new InterfaceElement(coreModule, "TopLayerContainer", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 720, 1180, 0, 0);
		renderModule.addElement("background_bg2", rebuildContainer);
		
		
		buildListing();
		

		if(origin == InterfaceQuestDetailed.ORIGIN_QUEST_LIST)
		{
			//return text
			newElement = new InterfaceElement(coreModule, "return_button", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 560, 60, -24, -554);
			new Gesture("Click", "interface~open~InterfaceQuests", newElement);
			renderModule.addElement("bg", newElement);
			
			newElement = new InterfaceElement(coreModule, id, "Center", "Center", 546, 70, 62, 10, 1.2f, "Return to your Quest List", coreModule.renderModule.FONT_IMPACT);
			renderModule.addElement("return_button", newElement);
		}
		else if(origin == InterfaceQuestDetailed.ORIGIN_GUILD)
		{
			//return text
			newElement = new InterfaceElement(coreModule, "return_button", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 560, 60, -24, -554);
			new Gesture("Click", "interface~open~InterfaceBuildingGuild", newElement);
			renderModule.addElement("bg", newElement);
			
			newElement = new InterfaceElement(coreModule, id, "Center", "Center", 546, 70, 142, 10, 1.2f, "Return to Guild", coreModule.renderModule.FONT_IMPACT);
			renderModule.addElement("return_button", newElement);
		}
	}
	
	public QuestList getList()
	{
		if(origin == ORIGIN_GUILD)
		{
			Location l = gameModule.partyData.getMainCharacter().currentLocation;
			
			if(l!=null)
			{
				Map m = l.getMap();
				if(m!=null)
				{
					Town t = (Town) m;
					
					if(t.getBuilding("Guild")!=null)
					{
						Guild g = (Guild) t.getBuilding("Guild");
						
						return g.guildQuests;
					}
				}
			}
		}
		
		//update for daily quests later
		if(mode == LIST_NORMAL)
			return gameModule.playerQuests;
		else if(mode == LIST_DAILY)
			return gameModule.dailyQuests;
		else if(mode == LIST_DAILY)
			return gameModule.dailyQuests;
		else
			return gameModule.playerQuests;
	}
	
	public void destroy()
	{
		rebuildContainer = null;
		
		super.destroy();
	}
	
	public void buildListing()
	{
		QuestList quests = getList();
		
		if(currIndex < quests.getList().size() && currIndex >= 0)
		{
			Quest q = quests.getList().get(currIndex);
			
			newElement = new InterfaceElement(coreModule, "listing_", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 519, 667, 0, 81, 1.0f, 1.0f, "interfaces/quests/detailed listing.png");
			renderModule.addElement("TopLayerContainer", newElement);
			
			String creature = q.getCreature();
			
			CreatureData c = gameModule.creatureDatabase.database.get(creature);
			
			Item item = null;
			
			if(q.getRewardType() == Quest.REWARD_ITEM)
				item = gameModule.itemLookupModule.getItem(q.getItem());
			
			//ensure our data is valid!
			if(c!=null && (q.getRewardType() != Quest.REWARD_ITEM || item!=null))
			{
				newElement = new InterfaceElement(coreModule, "sprite", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 144, 72, -156, 170, 1.0f, 1.0f, "sprites/"+c.filename);
				newElement.generateFrames(72, 72, c.animationSpeed, 0, 1, true);
				renderModule.addElement("listing_", newElement);
				newElement = new InterfaceElement(coreModule, id, "Center", "Center", 150, 70, 17, -24, 1.2f, "Lv"+q.getTargetLevel(), coreModule.renderModule.FONT_IMPACT);
				renderModule.addElement("sprite", newElement);
				
				if(q.getRewardType() == Quest.REWARD_ITEM)
				{
					item.generateIcon(coreModule,"item",id, "listing_", 164, 10, false);
					

					newElement = new InterfaceElement(coreModule, id, "Center", "Center", 300, 70, -80, 48, 1.5f, ""+Item.itemTypeToString(item.type), coreModule.renderModule.FONT_IMPACT);
					renderModule.addElement("listing_", newElement);
					newElement = new InterfaceElement(coreModule, id, "Center", "Center", 500, 70, 20, -16, 1.2f, ""+item.name, coreModule.renderModule.FONT_IMPACT);
					renderModule.addElement("listing_", newElement);
				}
				else if(q.getRewardType() == Quest.REWARD_MONEY)
				{
					newElement = new InterfaceElement(coreModule, "item", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 40, 40, 164, 10, 1, 1, "interfaces/building_shop/money.png");
					renderModule.addElement("listing_", newElement);
					

					newElement = new InterfaceElement(coreModule, id, "Center", "Center", 300, 70, -80, 48, 1.5f, "Money", coreModule.renderModule.FONT_IMPACT);
					renderModule.addElement("listing_", newElement);
					newElement = new InterfaceElement(coreModule, id, "Center", "Center", 500, 70, 20, -16, 1.2f, ""+q.getAmount()+" Gold", coreModule.renderModule.FONT_IMPACT);
					renderModule.addElement("listing_", newElement);
					
					int xOffset = 0;
					
					if(q.getAmount() > 999)
					{
						xOffset = -10;
					}
					newElement = new InterfaceElement(coreModule, id, "Center", "Center", 300, 70, 130+xOffset, -15, 1.0f, ""+q.getAmount(), coreModule.renderModule.FONT_IMPACT);
					renderModule.addElement("item", newElement);
				}
				
				int j = 0;
				for(j=0; j<q.getDifficulty() && j<5; j++)
				{
					newElement = new InterfaceElement(coreModule, "star"+j, id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 126, 42, 30+j*33, 125, 1.0f, 1.0f, "interfaces/building_inn/star.png");
					newElement.generateFrames(42, 42, 0, 0, 0, false);
					renderModule.addElement("listing_", newElement);
				}
				for(int k=j; k<5; k++)
				{
					newElement = new InterfaceElement(coreModule, "star"+j, id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 126, 42, 30+k*33, 125, 1.0f, 1.0f, "interfaces/building_inn/star_dark.png");
					newElement.generateFrames(42, 42, 0, 0, 0, false);
					renderModule.addElement("listing_", newElement);
				}
			}
			
			//render planet image
			newElement = new InterfaceElement(coreModule, "planet", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 384, 192, -5, -198, 1.0f, 1.0f, "map/world/small/"+q.getPlanet()+".png");
			renderModule.addElement("listing_", newElement);
			
			//render creature locations on this map
			Location[][] p = gameModule.locationModule.getPlanet(q.getPlanet());
			
			if(p!=null)
			{
				for(Location[] row : p)
				{
					for(Location loc : row)
					{
						//check if it contains the creature we're pursuing
						if(loc!=null && loc.hasCreature && loc.spawns.spawns.size() > 0)
						{
							Unit u = loc.spawns.spawns.get(0);
							if(u.name.equals(q.getCreature()))
							{
								//render star here
								newElement = new InterfaceElement(coreModule, "target_loc", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 126, 42, -203+24*loc.getX(), 106-24*loc.getY(), 2.0f, 2.0f, "interfaces/building_inn/star.png");
								newElement.generateFrames(42, 42, 0, 0, 0, false);
								renderModule.addElement("planet", newElement);
							}
						}
					}
				}
			}
			else
			{
				System.out.println("KAPPA\n\n\n\n\n\n\n");
			}
			

			newElement = new InterfaceElement(coreModule, id, "Center", "Center", 500, 70, 208, 180, 2f, ""+q.getQtyCurr()+"/"+q.getQtyMax(), coreModule.renderModule.FONT_IMPACT);
			renderModule.addElement("listing_", newElement);

			if(origin != ORIGIN_GUILD)
			{
				if(q.getQtyCurr() < q.getQtyMax())
				{
					int xOffset = 0;
					
					if(((int) (100*q.getQtyCurr()/q.getQtyMax())) >= 10)
						xOffset = -25;
					
					newElement = new InterfaceElement(coreModule, id, "Center", "Center", 500, 70, 400+xOffset, 170, 2.4f, ""+((int) (100*q.getQtyCurr()/q.getQtyMax()))+"%", coreModule.renderModule.FONT_IMPACT);
					renderModule.addElement("listing_", newElement);
					
					//button to complete/forfeit/accept a quest
					newElement = new InterfaceElement(coreModule, "button", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 537, 159, 0, -434);
					new Gesture("Click", "interface~action~InterfaceQuestDetailed:Forfeit", newElement);
					renderModule.addElement("listing_", newElement);
					newElement = new InterfaceElement(coreModule, id, "Center", "Center", 500, 70, 70, -12, 2, "Forfeit Quest", coreModule.renderModule.FONT_IMPACT);
					newElement.setColor(255, 0, 0, 1.0f);
					renderModule.addElement("button", newElement);
				}
				else
				{
					newElement = new InterfaceElement(coreModule, "complete", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 110, 110, 190, 194, 1.0f, 1.0f, "interfaces/quests/complete.png");
					renderModule.addElement("listing_", newElement);
					
					//button to complete/forfeit/accept a quest
					newElement = new InterfaceElement(coreModule, "button", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 537, 159, 0, -434);
					new Gesture("Click", "interface~action~InterfaceQuestDetailed:Complete", newElement);
					renderModule.addElement("listing_", newElement);
					newElement = new InterfaceElement(coreModule, id, "Center", "Center", 500, 70, 40, -12, 2, "Complete Quest", coreModule.renderModule.FONT_IMPACT);
					newElement.setColor(0, 255, 0, 1.0f);
					renderModule.addElement("button", newElement);
				}
				
			}
			else if(!gameModule.playerQuests.isFull())
			{

				newElement = new InterfaceElement(coreModule, "complete", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 110, 110, 190, 194, 1.0f, 1.0f, "interfaces/quests/complete.png");
				renderModule.addElement("listing_", newElement);
				
				//button to complete/forfeit/accept a quest
				newElement = new InterfaceElement(coreModule, "button", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 537, 159, 0, -434);
				new Gesture("Click", "interface~action~InterfaceQuestDetailed:Accept", newElement);
				renderModule.addElement("listing_", newElement);
				newElement = new InterfaceElement(coreModule, id, "Center", "Center", 500, 70, 65, -12, 2, "Accept Quest", coreModule.renderModule.FONT_IMPACT);
				renderModule.addElement("button", newElement);
			}

			newElement = new InterfaceElement(coreModule, id, "Center", "Center", 500, 70, 290, 286, 1.6f, gameModule.planetNames.get(q.getPlanet()), coreModule.renderModule.FONT_IMPACT);
			renderModule.addElement("listing_", newElement);
			
			newElement = new InterfaceElement(coreModule, id, "Center", "Center", 500, 70, 20, 286, 1.6f, "Defeat "+q.getQtyMax(), coreModule.renderModule.FONT_IMPACT);
			renderModule.addElement("listing_", newElement);
		}
	}
	
	//given a string of input, it will perform something upon the interface
	public void processInput(String inputString, String additionalInput)
	{
		if(inputString.equals("List"))
		{
			if(additionalInput.length() > 0)
			{
				int index = Integer.parseInt(additionalInput);
				
				origin = ORIGIN_QUEST_LIST;
				
				if(index >= 0 && index < getList().getList().size())
					currIndex = index;
				else
					currIndex = 0;
				
				gameModule.activateInterface(id);
			}
		}
		else if(inputString.equals("Guild"))
		{
			if(additionalInput.length() > 0)
			{
				int index = Integer.parseInt(additionalInput);
				
				origin = ORIGIN_GUILD;
				
				if(index >= 0 && index < getList().getList().size())
					currIndex = index;
				else
					currIndex = 0;
				
				gameModule.activateInterface(id);
			}
		}
		else if(inputString.equals("Normal"))
		{
			mode = LIST_NORMAL;
		}
		else if(inputString.equals("Daily"))
		{
			mode = LIST_DAILY;
		}
		else if(inputString.equals("Achievement"))
		{
			mode = LIST_ACHIEVEMENT;
		}
		else if(inputString.equals("Forfeit"))
		{
			if(currIndex >= 0 && currIndex < getList().getList().size())
			{
				//remove the entry and exit
				getList().getList().remove(currIndex);
				
				//save the game
				gameModule.saveSave();
				
				gameModule.activateInterface("InterfaceQuests");
				return;
			}
		}
		else if(inputString.equals("Accept"))
		{
			if(currIndex >= 0 && currIndex < getList().getList().size())
			{
				//remove the entry and exit
				Quest q = getList().getList().remove(currIndex);
				
				//add the quest to the players quest list
				gameModule.playerQuests.addQuest(q);
				
				//save the game
				gameModule.saveSave();
				
				gameModule.activateInterface("InterfaceQuests");
				return;
			}
		}
		else if(inputString.equals("Complete"))
		{
			if(currIndex >= 0 && currIndex < getList().getList().size())
			{
				Quest q = getList().getList().get(currIndex);
				
				//remove the entry and exit
				getList().getList().remove(currIndex);
				
				//give the loyalty equal to quest difficulty to the town that you helped
				Location l = gameModule.locationModule.getLocation(q.getPlanet(), q.getX(), q.getY());
				if(l!=null && l.danger_level == 0 && l.getMap()!=null)
				{
					Town t = (Town) l.getMap();
					t.addPlayerLoyalty(q.getDifficulty());
				}
				
				//collect the reward
				if(q.getRewardType() == Quest.REWARD_ITEM)
				{
					if(gameModule.playerInventory.getRemainingCapacity() > 0)
					{
						gameModule.playerInventory.giveItem(q.getItem());
					}
				}
				else if(q.getRewardType() == Quest.REWARD_MONEY)
				{
					gameModule.partyData.getMainCharacter().giveMoney(q.getAmount());
				}
				
				//save the game
				gameModule.saveSave();
				
				gameModule.activateInterface("InterfaceQuests");
				return;
			}
		}
	}
}
