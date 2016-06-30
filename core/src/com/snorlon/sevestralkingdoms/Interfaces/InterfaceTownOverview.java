package com.snorlon.sevestralkingdoms.Interfaces;

import com.snorlon.sevestralkingdoms.Buildings.BuildingBase;
import com.snorlon.sevestralkingdoms.Buildings.HouseBuilding;
import com.snorlon.sevestralkingdoms.GameTools.Core;
import com.snorlon.sevestralkingdoms.GameTools.Gesture;
import com.snorlon.sevestralkingdoms.GameTools.InterfaceElement;
import com.snorlon.sevestralkingdoms.Housing.House;
import com.snorlon.sevestralkingdoms.Places.Town;

public class InterfaceTownOverview extends GenericInterface
{

	/*Patterns shelves
	Based on terrain
Each "Window" has a background related to the planet
	(Smog, clear skies, etc), May change for weather*/
	
	public InterfaceTownOverview(Core newCore)
	{
		super(newCore);
		// TODO Auto-generated constructor stub
		
		id = "InterfaceTownOverview";
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
		newElement = new InterfaceElement(coreModule, "overview_background_bg", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 718, 1280, -24, -50, 1.0f, 1.0f, "interfaces/town_overview/background.png");
		renderModule.addElement("bg", newElement);
		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 700, 70, 170, 425, 2.0f, "Town View", renderModule.FONT_IMPACT);
		renderModule.addElement("bg", newElement);
		

		//render the items!
		if(gameModule.partyData.getMainCharacter().currentLocation!=null)
		{
			if(gameModule.partyData.getMainCharacter().currentLocation.getMap()!=null)
			{
				int shelfFrame = 0;
				String p = gameModule.partyData.getMainCharacter().currentLocation.planet;

				if(p.equals("Water"))
					shelfFrame = 1;
				else if(p.equals("Earth"))
					shelfFrame = 2;
				else if(p.equals("Wind"))
					shelfFrame = 3;
				else if(p.equals("Life"))
					shelfFrame = 4;
				else if(p.equals("Time"))
					shelfFrame = 5;
				else if(p.equals("Space"))
					shelfFrame = 6;
				else if(p.equals("Void"))
					shelfFrame = 7;
				
				if(gameModule.partyData.getMainCharacter().currentLocation.getMap() instanceof Town)
				{
					Town currentTown = (Town) (gameModule.partyData.getMainCharacter().currentLocation.getMap());
					
					//render the town buildings
					for(int i=0; i<currentTown.getPlotCount(); i++)
					{
						BuildingBase b = currentTown.getBuildings()[i];
						if(b!=null)
						{
							int x = i%2;
							int y = i/2;
							//render dat building
							newElement = new InterfaceElement(coreModule, "BuildingContainer"+x+y, id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 218, 183, 264*x - 153, 197*y - 355);							
							renderModule.addElement("bg", newElement);
							
							//draw a shelfie!
							newElement = new InterfaceElement(coreModule, "Shelf"+x+y, id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 1808, 141, 0, -60, 1.0f, 1.0f, "interfaces/town_overview/shelf.png");
							newElement.generateFrames(226, 141, 0, shelfFrame, 7, false);
							renderModule.addElement("BuildingContainer"+x+y, newElement);
							
							b.GenerateImage("BuildingContainer"+x+y, id, 1.0f);
							
							//draw the level
							if(!(b instanceof HouseBuilding))
							{
								newElement = new InterfaceElement(coreModule, id, "Center", "Center", 300, 70, 55, -60, 1.0f, "Lv"+b.getLevel(), renderModule.FONT_IMPACT);
								renderModule.addElement("BuildingContainer"+x+y, newElement);
							}
							
							b.GenerateLink("BuildingContainer"+x+y, id);
						}
					}
				}
			}
			
		}

		
		//return text
		newElement = new InterfaceElement(coreModule, "return_button", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 560, 60, -24, -554);
		new Gesture("Click", "interface~open~InterfaceMenuOverview", newElement);
		renderModule.addElement("bg", newElement);
		
		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 546, 70, 100, 10, 1.2f, "Return to Main Menu", coreModule.renderModule.FONT_IMPACT);
		renderModule.addElement("return_button", newElement);
	}

}
