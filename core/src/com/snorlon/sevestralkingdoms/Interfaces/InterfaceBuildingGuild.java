package com.snorlon.sevestralkingdoms.Interfaces;

import java.util.ArrayList;

import com.snorlon.sevestralkingdoms.Buildings.BuildingBase;
import com.snorlon.sevestralkingdoms.Buildings.Guild;
import com.snorlon.sevestralkingdoms.Buildings.Inn;
import com.snorlon.sevestralkingdoms.GameTools.Core;
import com.snorlon.sevestralkingdoms.GameTools.Gesture;
import com.snorlon.sevestralkingdoms.GameTools.InterfaceElement;
import com.snorlon.sevestralkingdoms.Items.Item;
import com.snorlon.sevestralkingdoms.NPC.CreatureData;
import com.snorlon.sevestralkingdoms.NPC.Unit;
import com.snorlon.sevestralkingdoms.Places.Location;
import com.snorlon.sevestralkingdoms.Places.Map;
import com.snorlon.sevestralkingdoms.Places.Town;
import com.snorlon.sevestralkingdoms.Quests.Quest;
import com.snorlon.sevestralkingdoms.Quests.QuestList;

public class InterfaceBuildingGuild extends GenericInterface
{
	int selectedIndex = 0;
	
	int maxIndex = 0;
	
	boolean reset = true;
	
	InterfaceElement rebuildContainer = null;

	public InterfaceBuildingGuild(Core newCore)
	{
		super(newCore);
		// TODO Auto-generated constructor stub
		
		id = "InterfaceBuildingGuild";
	}
	
	//used to pull up the particular screen in question
	public void activate()
	{		
		//create a gui container
		newElement = new InterfaceElement(coreModule, "InterfaceContainer", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 720, 1180, 0, 0);
		renderModule.addElement("CenterNode", newElement);
		
		//bg
		newElement = new InterfaceElement(coreModule, "bg", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 718, 1280, 24, 51, 1.0f, 1.0f, "menu/menubg.png");
		renderModule.addElement("InterfaceContainer", newElement);
		newElement = new InterfaceElement(coreModule, "background_bg2", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 718, 1280, -24, -50, 1.0f, 1.0f, "interfaces/background.png");
		renderModule.addElement("bg", newElement);
		newElement = new InterfaceElement(coreModule, "background_bg", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 718, 1280, -24, -50, 1.0f, 1.0f, "interfaces/building_guild/background.png");
		renderModule.addElement("bg", newElement);
		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 700, 70, 230, 425, 2.0f, "Guild", coreModule.renderModule.FONT_IMPACT);
		renderModule.addElement("bg", newElement);
		
		newElement = new InterfaceElement(coreModule, "BuildingSprite", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 192, 192, -233, 470, 1.0f, 1.0f, "map/buildings/guild.png");
		coreModule.renderModule.addElement("bg", newElement);
		
		//rebuild our rendering container
		rebuildContainer = new InterfaceElement(coreModule, "TopLayerContainer", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 720, 1180, 0, 0);
		renderModule.addElement("background_bg", rebuildContainer);
		
		buildListing();
		
		//return text
		newElement = new InterfaceElement(coreModule, "return_button", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 560, 60, -24, -554);
		new Gesture("Click", "interface~open~InterfaceTownOverview", newElement);
		renderModule.addElement("bg", newElement);	
		
		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 546, 70, 140, 10, 1.2f, "Return to Town", coreModule.renderModule.FONT_IMPACT);
		renderModule.addElement("return_button", newElement);
	}
	
	public void buildListing()
	{
		int index = 0;
		
		Location l = gameModule.partyData.getMainCharacter().currentLocation;
		
		if(l==null)
			return;
		
		Map m = l.getMap();
		if(m==null)
			return;
		
		Town t = (Town) m;
		
		if(t.getBuilding("Guild")==null)
			return;
		
		Guild g = (Guild) t.getBuilding("Guild");
		
		QuestList quests = g.guildQuests;
		
		for(Quest q : quests.getList())
		{
			newElement = new InterfaceElement(coreModule, "listing_"+index, id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 519, 129, 0, 340-137*index, 1.0f, 1.0f, "interfaces/quests/listing.png");
			new Gesture("Click", "interface~action~InterfaceBuildingGuild:Details:"+index, newElement);
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

			newElement = new InterfaceElement(coreModule, id, "Center", "Center", 500, 70, 200, 43, 1, gameModule.planetNames.get(q.getPlanet()), coreModule.renderModule.FONT_IMPACT);
			renderModule.addElement("listing_"+index, newElement);

			newElement = new InterfaceElement(coreModule, id, "Center", "Center", 500, 70, 240, 12, 0.9f, "("+q.getX()+","+q.getY()+")", coreModule.renderModule.FONT_IMPACT);
			renderModule.addElement("listing_"+index, newElement);
			
			index++;
		}
	}
	
	//given a string of input, it will perform something upon the interface
	public void processInput(String inputString, String additionalInput)
	{
		if(inputString.equals("Details"))
		{
			if(additionalInput.length() > 0)
			{
				//give the quest set we pulled this from
				gameModule.giveInterfaceInput("InterfaceQuestDetailed", "Normal", "");
				gameModule.giveInterfaceInput("InterfaceQuestDetailed", "Guild", additionalInput);
			}
		}
	}

	
	public void destroy()
	{
		rebuildContainer = null;
		
		super.destroy();
	}
}
