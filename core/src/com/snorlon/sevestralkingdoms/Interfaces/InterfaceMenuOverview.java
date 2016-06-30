package com.snorlon.sevestralkingdoms.Interfaces;

import com.snorlon.sevestralkingdoms.GameTools.Core;
import com.snorlon.sevestralkingdoms.GameTools.Gesture;
import com.snorlon.sevestralkingdoms.GameTools.InterfaceElement;

public class InterfaceMenuOverview extends GenericInterface
{

	public InterfaceMenuOverview(Core newCore)
	{
		super(newCore);
		// TODO Auto-generated constructor stub
		
		id = "InterfaceMenuOverview";
	}
	
	//used to pull up the particular screen in question
	public void activate()
	{
		//create a gui container
		newElement = new InterfaceElement(coreModule, "InterfaceContainer", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 720, 1180, 0, 0);
		renderModule.addElement("CenterNode", newElement);
		
		//bg
		newElement = new InterfaceElement(coreModule, "overview_bg", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 718, 1280, 24, 51, 1.0f, 1.0f, "menu/menubg.png");
		renderModule.addElement("InterfaceContainer", newElement);
		newElement = new InterfaceElement(coreModule, "overview_background_bg", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 718, 1280, -24, -50, 1.0f, 1.0f, "menu/main/main menu.png");
		renderModule.addElement("overview_bg", newElement);
		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 700, 70, 190, 425, 2.0f, "Main Menu", renderModule.FONT_IMPACT);
		renderModule.addElement("overview_bg", newElement);
		
		//our gold
		if(gameModule.partyData.getMainCharacter()!=null)
		{
			newElement = new InterfaceElement(coreModule, id, "Center", "Center", 700, 70, 150, 365, 1.4f, ""+gameModule.partyData.getMainCharacter().getMoney(), coreModule.renderModule.FONT_IMPACT);
			renderModule.addElement("InterfaceContainer", newElement);
		}
		newElement = new InterfaceElement(coreModule, "money", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 40, 40, -233, 361, 1.0f, 1.0f, "interfaces/building_shop/money.png");
		coreModule.renderModule.addElement("InterfaceContainer", newElement);
		
		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 300, 70, 250, 365, 1.4f, ""+gameModule.getTurn(), coreModule.renderModule.FONT_IMPACT);
		renderModule.addElement("InterfaceContainer", newElement);
		
		//Army BUTTON
		newElement = new InterfaceElement(coreModule, "overview_button_container_army", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 546, 84, 0, 85);
		new Gesture("Click", "interface~open~InterfaceCommandOverview", newElement);
		renderModule.addElement("InterfaceContainer", newElement);
		
		//Party BUTTON
		newElement = new InterfaceElement(coreModule, "overview_button_container_party", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 546, 84, 0, 185);
		new Gesture("Click", "interface~open~InterfacePartyOverview", newElement);
		renderModule.addElement("InterfaceContainer", newElement);

		
		//EXPLORE BUTTON
		newElement = new InterfaceElement(coreModule, "overview_button_container_explore", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 546, 84, 0, 280);
		new Gesture("Click", "interface~open~InterfaceLocalMap", newElement);
		renderModule.addElement("InterfaceContainer", newElement);
		
		/*
		newElement = new InterfaceElement(coreModule, "test", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 546, 84, 0, 0, 1.0f, 1.0f, "test.png");
		renderModule.addElement("overview_button_container_explore", newElement);*/
		
		//Map BUTTON
		newElement = new InterfaceElement(coreModule, "overview_button_container_map", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 268, 190, 142, -341);
		//new Gesture("Click", "change scene~Inventory", newElement);
		renderModule.addElement("InterfaceContainer", newElement);
		
		//help BUTTON
		newElement = new InterfaceElement(coreModule, "overview_button_container_help", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 188, 70, -188, -505);
		//new Gesture("Click", "change scene~Inventory", newElement);
		renderModule.addElement("InterfaceContainer", newElement);

		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 188, 55, 57, -5, 1.3f, "Help", renderModule.FONT_IMPACT);
		renderModule.addElement("overview_button_container_help", newElement);
		
		//options BUTTON
		newElement = new InterfaceElement(coreModule, "overview_button_container_options", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 188, 70, 2, -505);
		new Gesture("Click", "interface~open~InterfaceOptions", newElement);
		renderModule.addElement("InterfaceContainer", newElement);

		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 188, 55, 30, -5, 1.3f, "Options", renderModule.FONT_IMPACT);
		renderModule.addElement("overview_button_container_options", newElement);
		
		//power BUTTON
		newElement = new InterfaceElement(coreModule, "overview_button_container_power", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 188, 70, 191, -505);
		new Gesture("Click", "Quit", newElement);//turn the app off
		renderModule.addElement("InterfaceContainer", newElement);
		
		//render inventory
		newElement = new InterfaceElement(coreModule, "button_inventory", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 175, 190, -185, -340);
		new Gesture("Click", "interface~open~InterfaceInventory", newElement);
		renderModule.addElement("InterfaceContainer", newElement);
		
		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 346, 70, 120, 60, 1.5f, ""+gameModule.playerInventory.getContents().size()+"/"+gameModule.playerInventory.getCapacity(), renderModule.FONT_IMPACT);
		renderModule.addElement("button_inventory", newElement);	
		
		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 346, 70, 120, -40, 1.5f, "Items", renderModule.FONT_IMPACT);
		renderModule.addElement("button_inventory", newElement);	
		
		//render quests
		newElement = new InterfaceElement(coreModule, "button_quest", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 175, 190, 0, -340);
		new Gesture("Click", "interface~open~InterfaceQuests", newElement);
		renderModule.addElement("InterfaceContainer", newElement);
		
		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 346, 70, 106, -40, 1.5f, "Quests", renderModule.FONT_IMPACT);
		renderModule.addElement("button_quest", newElement);
		
		//render world
		newElement = new InterfaceElement(coreModule, "button_world", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 175, 190, 185, -340);
		new Gesture("Click", "interface~open~InterfaceWorldMap", newElement);
		renderModule.addElement("InterfaceContainer", newElement);
		

		if(gameModule.partyData.getMainCharacter().currentLocation != null)
		{
			newElement = new InterfaceElement(coreModule, "Enter Map", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 316, 52, -93, -180);
			new Gesture("Click", "interface~action~InterfaceLocalMap:Enter", newElement);//clicking should enter battle
			renderModule.addElement("InterfaceContainer", newElement);
			
			//draw some text for the map name
			String locationName = gameModule.partyData.getMainCharacter().currentLocation.name;
			
			newElement = new InterfaceElement(coreModule, id, "Center", "Center", 400, 55, -43, -95, 1.0f, ""+locationName, renderModule.FONT_IMPACT);
			renderModule.addElement("InterfaceContainer", newElement);
			
			String tempFaction = "";
			
			if(gameModule.partyData.getMainCharacter().currentLocation != null)
			{
				tempFaction = gameModule.partyData.getMainCharacter().currentLocation.getOwner();
			}

			if(gameModule.partyData.getMainCharacter().currentLocation.danger_level > 0)
			{
				newElement = new InterfaceElement(coreModule, id, "Center", "Center", 200, 55, 57, -2, 1.3f, "Fight", renderModule.FONT_IMPACT);
				renderModule.addElement("Enter Map", newElement);
				
				newElement = new InterfaceElement(coreModule, id, "Center", "Center", 300, 55, -93, -129, 1.0f, "Field", renderModule.FONT_IMPACT);
				renderModule.addElement("InterfaceContainer", newElement);
			}
			else
			{
				newElement = new InterfaceElement(coreModule, id, "Center", "Center", 200, 55, 57, -2, 1.3f, "Enter", renderModule.FONT_IMPACT);
				renderModule.addElement("Enter Map", newElement);
				
				newElement = new InterfaceElement(coreModule, id, "Center", "Center", 300, 55, -93, -129, 1.0f, tempFaction+" Town", renderModule.FONT_IMPACT);
				renderModule.addElement("InterfaceContainer", newElement);
			}
			
			if(!gameModule.partyData.getMainCharacter().currentLocation.planet.equals("None"))
			{
				newElement = new InterfaceElement(coreModule, "map_image", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 2400, 1200, 179, -129, 1.01f, 1.01f, "map/world/"+gameModule.partyData.getMainCharacter().currentLocation.planet+".png");
				newElement.generateFrames(150, 150, 5, 0, 128, false);
				newElement.current_frame = (gameModule.partyData.getMainCharacter().currentLocation.getX()-1) + (gameModule.partyData.getMainCharacter().currentLocation.getY()-1)*16;
				renderModule.addElement("InterfaceContainer", newElement);
				
				if(gameModule.partyData.getMainCharacter().currentLocation.danger_level == 0)
				{
					newElement = new InterfaceElement(coreModule, "map_image2", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 149, 149, 0, 0, 1.01f, 1.01f, "interfaces/world_map/towns.png");
					renderModule.addElement("map_image", newElement);
				}
				
				int tLevel = gameModule.partyData.getMainCharacter().currentLocation.level;
				int offsetX = 0;

				if(tLevel < 10)
					offsetX = 10;
				else if(tLevel > 99)
					offsetX = -10;
				
				newElement = new InterfaceElement(coreModule, id, "Center", "Center", 300, 55, 110+offsetX, -65, 1.0f, "Lv "+tLevel, renderModule.FONT_IMPACT);
				renderModule.addElement("map_image", newElement);
				
				//draw world map over world button
				newElement = new InterfaceElement(coreModule, "map_button_img", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 167, 84, 2, 0, 1, 1, "map/world/mini/"+gameModule.partyData.getMainCharacter().currentLocation.planet+".png");
				renderModule.addElement("button_world", newElement);
			}
		}
		
		//render world text
		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 346, 70, 116, -40, 1.5f, "World", renderModule.FONT_IMPACT);
		renderModule.addElement("button_world", newElement);
	}

}
