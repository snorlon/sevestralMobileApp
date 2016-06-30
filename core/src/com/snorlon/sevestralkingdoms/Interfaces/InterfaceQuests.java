package com.snorlon.sevestralkingdoms.Interfaces;

import com.snorlon.sevestralkingdoms.GameTools.Core;
import com.snorlon.sevestralkingdoms.GameTools.Gesture;
import com.snorlon.sevestralkingdoms.GameTools.InterfaceElement;
import com.snorlon.sevestralkingdoms.NPC.CreatureData;
import com.snorlon.sevestralkingdoms.Quests.Quest;
import com.snorlon.sevestralkingdoms.Quests.QuestList;
import com.snorlon.sevestralkingdoms.Items.Item;

public class InterfaceQuests extends GenericInterface
{
	int currIndex = 0;
	
	InterfaceElement rebuildContainer = null;
	
	public InterfaceQuests(Core newCore)
	{
		super(newCore);
		// TODO Auto-generated constructor stub
		
		id = "InterfaceQuests";
	}
	
	//used to pull up the particular screen in question
	public void activate()
	{		
		currIndex = 0;
		
		//create a gui container
		newElement = new InterfaceElement(coreModule, "InterfaceContainer", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 720, 1180, 0, 0);
		renderModule.addElement("CenterNode", newElement);
		
		//bg
		newElement = new InterfaceElement(coreModule, "bg", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 718, 1280, 24, 51, 1.0f, 1.0f, "menu/menubg.png");
		renderModule.addElement("InterfaceContainer", newElement);
		
		//party bg
		newElement = new InterfaceElement(coreModule, "background_bg", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 718, 1280, -24, -50, 1.0f, 1.0f, "interfaces/background.png");
		renderModule.addElement("bg", newElement);
		newElement = new InterfaceElement(coreModule, "background_bg2", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 718, 1280, -24, -50, 1.0f, 1.0f, "interfaces/quests/background.png");
		renderModule.addElement("bg", newElement);

		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 700, 70, 130, 425, 2.0f, "Current Quests", coreModule.renderModule.FONT_IMPACT);
		renderModule.addElement("bg", newElement);
		
		//rebuild our rendering container
		rebuildContainer = new InterfaceElement(coreModule, "TopLayerContainer", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 720, 1180, 0, 0);
		renderModule.addElement("background_bg2", rebuildContainer);
		
		
		buildListing();
		
		
		//return text
		newElement = new InterfaceElement(coreModule, "return_button", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 560, 60, -24, -554);
		new Gesture("Click", "interface~open~InterfaceMenuOverview", newElement);
		renderModule.addElement("bg", newElement);
		
		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 546, 70, 100, 10, 1.2f, "Return to Main Menu", coreModule.renderModule.FONT_IMPACT);
		renderModule.addElement("return_button", newElement);
	}
	
	public void renderCategory()
	{
		//update for daily quests later
		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 546, 70, 172, 350, 2.0f, "Regular", coreModule.renderModule.FONT_IMPACT);
		renderModule.addElement("TopLayerContainer", newElement);
		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 546, 70, 195, 300, 1.7f, "Quests", coreModule.renderModule.FONT_IMPACT);
		renderModule.addElement("TopLayerContainer", newElement);
	}
	
	public QuestList getList()
	{
		//update for daily quests later
		return gameModule.playerQuests;
	}
	
	public void destroy()
	{
		rebuildContainer = null;
		
		super.destroy();
	}
	
	public void buildListing()
	{
		int index = 0;
		int max = 5;//subtract one every one we render
		QuestList quests = getList();
		
		renderCategory();
		
		for(Quest q : quests.getList())
		{
			if(index >= currIndex && max > 0)
			{
				newElement = new InterfaceElement(coreModule, "listing_"+index, id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 519, 129, 0, 166-130*(5-max), 1.0f, 1.0f, "interfaces/quests/listing.png");
				new Gesture("Click", "interface~action~InterfaceQuests:Details:"+index, newElement);
				renderModule.addElement("TopLayerContainer", newElement);
				
				String creature = q.getCreature();
				
				CreatureData c = gameModule.creatureDatabase.database.get(creature);
				
				Item item = null;
				
				if(q.getRewardType() == Quest.REWARD_ITEM)
					item = gameModule.itemLookupModule.getItem(q.getItem());
				
				//ensure our data is valid!
				if(c!=null && (q.getRewardType() != Quest.REWARD_ITEM || item!=null))
				{
					newElement = new InterfaceElement(coreModule, "sprite"+index, id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 144, 72, -200, -5, 1.0f, 1.0f, "sprites/"+c.filename);
					newElement.generateFrames(72, 72, c.animationSpeed, 0, 1, true);
					renderModule.addElement("listing_"+index, newElement);
					newElement = new InterfaceElement(coreModule, id, "Center", "Center", 150, 70, 41, -11, 0.8f, "Lv"+q.getTargetLevel(), coreModule.renderModule.FONT_IMPACT);
					renderModule.addElement("sprite"+index, newElement);
					
					if(q.getRewardType() == Quest.REWARD_ITEM)
					{
						item.generateIcon(coreModule,"item"+index,id, "listing_"+index, -120, -10, true);
					}
					else if(q.getRewardType() == Quest.REWARD_MONEY)
					{
						newElement = new InterfaceElement(coreModule, "item"+index, id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 40, 40, -120, 0, 1, 1, "interfaces/building_shop/money.png");
						renderModule.addElement("listing_"+index, newElement);
						
						int xOffset = 0;
						
						if(q.getAmount() > 999)
						{
							xOffset = -10;
						}
						newElement = new InterfaceElement(coreModule, id, "Center", "Center", 300, 70, 10+xOffset, -15, 1.0f, ""+q.getAmount(), coreModule.renderModule.FONT_IMPACT);
						renderModule.addElement("listing_"+index, newElement);
					}
					
					int j = 0;
					for(j=0; j<q.getDifficulty() && j<5; j++)
					{
						newElement = new InterfaceElement(coreModule, "star"+index+""+j, id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 126, 42, -40+j*33, -38, 1.0f, 1.0f, "interfaces/building_inn/star.png");
						newElement.generateFrames(42, 42, 0, 0, 0, false);
						renderModule.addElement("listing_"+index, newElement);
					}
					for(int k=j; k<5; k++)
					{
						newElement = new InterfaceElement(coreModule, "star"+index+""+j, id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 126, 42, -40+k*33, -38, 1.0f, 1.0f, "interfaces/building_inn/star_dark.png");
						newElement.generateFrames(42, 42, 0, 0, 0, false);
						renderModule.addElement("listing_"+index, newElement);
					}
				}

				if(q.getQtyCurr() < q.getQtyMax())
				{
					int xOffset = 0;
					
					if(((int) (100*q.getQtyCurr()/q.getQtyMax())) >= 10)
						xOffset = -25;
					
					newElement = new InterfaceElement(coreModule, id, "Center", "Center", 500, 70, 400+xOffset, -25, 2.4f, ""+((int) (100*q.getQtyCurr()/q.getQtyMax()))+"%", coreModule.renderModule.FONT_IMPACT);
					renderModule.addElement("listing_"+index, newElement);
				}
				else
				{
					newElement = new InterfaceElement(coreModule, "complete"+index, id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 110, 110, 180, 0, 1.0f, 1.0f, "interfaces/quests/complete.png");
					renderModule.addElement("listing_"+index, newElement);
				}

				newElement = new InterfaceElement(coreModule, id, "Center", "Center", 500, 70, 200, 43, 1, gameModule.planetNames.get(q.getPlanet()), coreModule.renderModule.FONT_IMPACT);
				renderModule.addElement("listing_"+index, newElement);

				newElement = new InterfaceElement(coreModule, id, "Center", "Center", 500, 70, 240, 12, 0.9f, "("+q.getX()+","+q.getY()+")", coreModule.renderModule.FONT_IMPACT);
				renderModule.addElement("listing_"+index, newElement);
				
				max--;
			}
			
			index++;
		}
		
		if(currIndex > 0)
		{
			newElement = new InterfaceElement(coreModule, "up", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 130, 260, 0, 162, 1.0f, 1.0f, "interfaces/quests/tab.png");
			newElement.setColor(255, 255, 255, 0.8f);
			newElement.generateFrames(130, 130, 0, 1, 1, false);
			new Gesture("Click", "interface~action~InterfaceQuests:up", newElement);
			renderModule.addElement("TopLayerContainer", newElement);
		}
		
		if(currIndex+2 < quests.getList().size())
		{
			newElement = new InterfaceElement(coreModule, "down", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 130, 260, 0, -360, 1.0f, 1.0f, "interfaces/quests/tab.png");
			newElement.setColor(255, 255, 255, 0.8f);
			newElement.generateFrames(130, 130, 0, 0, 1, false);
			new Gesture("Click", "interface~action~InterfaceQuests:down", newElement);
			renderModule.addElement("TopLayerContainer", newElement);
		}
	}
	
	//given a string of input, it will perform something upon the interface
	public void processInput(String inputString, String additionalInput)
	{
		if(inputString.equals("down"))
		{
			if(currIndex+1 < getList().getList().size())
			{
				currIndex++;
				
				if(rebuildContainer!=null)
					rebuildContainer.destroyChildren();
				
				buildListing();
			}
		}
		else if(inputString.equals("up"))
		{
			if(currIndex > 0)
			{
				currIndex--;
				
				if(rebuildContainer!=null)
					rebuildContainer.destroyChildren();
				
				buildListing();
			}
		}
		else if(inputString.equals("Details"))
		{
			if(additionalInput.length() > 0)
			{
				//give the quest set we pulled this from
				gameModule.giveInterfaceInput("InterfaceQuestDetailed", "Normal", "");
				gameModule.giveInterfaceInput("InterfaceQuestDetailed", "List", additionalInput);
			}
		}
	}
}
