package com.snorlon.sevestralkingdoms.Interfaces;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.snorlon.sevestralkingdoms.GameComponents.Common;
import com.snorlon.sevestralkingdoms.GameTools.Core;
import com.snorlon.sevestralkingdoms.GameTools.Gesture;
import com.snorlon.sevestralkingdoms.GameTools.InterfaceElement;
import com.snorlon.sevestralkingdoms.GameTools.Renderer;
import com.snorlon.sevestralkingdoms.NPC.Unit;
import com.snorlon.sevestralkingdoms.Places.Location;

public class InterfaceViewMap extends GenericInterface
{
	
	//our coordinates in the current area
	String planet = "Wind";//this is the planet we're viewing

	int optionCount = 0;
	int optionOffset = 0;

	public InterfaceViewMap(Core newCore)
	{
		super(newCore);
		
		id = "InterfaceViewMap";
	}
	
	//used to pull up the particular screen in question
	public void activate()
	{
		if(gameModule.partyData.getMainCharacter().currentLocation == null)
		{
			System.out.println("InterfaceViewMap error: Invalid location");
		}
		
		//create a gui container
		newElement = new InterfaceElement(coreModule, "InterfaceContainer", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 720, 1180, 0, 0);
		renderModule.addElement("CenterNode", newElement);
		
		//bg
		newElement = new InterfaceElement(coreModule, "bg", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 718, 1280, 24, 51, 1.0f, 1.0f, "menu/menubg.png");
		renderModule.addElement("InterfaceContainer", newElement);
		newElement = new InterfaceElement(coreModule, "background_bg", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 718, 1280, -24, -50, 1.0f, 1.0f, "interfaces/background.png");
		renderModule.addElement("bg", newElement);
		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 700, 70, 200+(9 - coreModule.gameModule.planetNames.get(planet).length())*15, 425, 2.0f, gameModule.planetNames.get(planet), renderModule.FONT_IMPACT);
		renderModule.addElement("bg", newElement);
		
		//Location thisLoc = gameModule.locationModule.getLocation(planet, x, y);

		if(!planet.equals("None"))
		{
			//get a list of valid towns to enter
			ArrayList<Location> options = gameModule.locationModule.getLocations(planet);
			
			optionCount = options.size();

			Collections.sort(options, new Comparator<Location>() {
			        @Override
			        public int compare(Location  l1, Location  l2)
			        {
			            return  l1.compareTo(l2);
			        }
			    });
			
			if(optionOffset > 0)
			{
				newElement = new InterfaceElement(coreModule, "buttonup", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 556, 63, -24, 340, 1.0f, 1.0f, "interfaces/world_explore/arrow_up.png");
				new Gesture("Click", "interface~action~InterfaceViewMap:Up", newElement);
				renderModule.addElement("bg", newElement);
			}
			
			if(optionOffset+3 < optionCount)
			{
				newElement = new InterfaceElement(coreModule, "buttondown", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 556, 63, -24, -450, 1.0f, 1.0f, "interfaces/world_explore/arrow_down.png");
				new Gesture("Click", "interface~action~InterfaceViewMap:Down", newElement);
				renderModule.addElement("bg", newElement);
			}
			
			//explore our options for this
			int index = 0;
			int initial = optionOffset;
			for(Location l : options)
			{
				if(initial > 0)
				{
					initial --;
					continue;
				}
				
				if(index >= 4)
					break;
				
				System.out.println(l.name);
				
				newElement = new InterfaceElement(coreModule, "button"+index, id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 562, 170, -24, 215-180*index, 1.0f, 1.0f, "interfaces/world_explore/button.png");
				new Gesture("Click", "travel~"+l.planet+"~"+l.getX()+"~"+l.getY(), newElement);
				renderModule.addElement("bg", newElement);
				
				newElement = new InterfaceElement(coreModule, id, "Center", "Center", 500, 70, 10, 37, 1.4f, l.name, renderModule.FONT_IMPACT);
				renderModule.addElement("button"+index, newElement);
				
				newElement = new InterfaceElement(coreModule, id, "Center", "Center", 500, 70, 10, -33, 1.2f, l.getOwner(), renderModule.FONT_IMPACT);
				renderModule.addElement("button"+index, newElement);
				
				int offsetX = 0;
				
				if(l.level < 100)
					offsetX = 10;
				
				if(l.level<10)
					offsetX = 20;
				
				newElement = new InterfaceElement(coreModule, id, "Center", "Center", 300, 70, 220+offsetX, -33, 1.2f, "Pop: "+l.level, renderModule.FONT_IMPACT);
				renderModule.addElement("button"+index, newElement);
				
				int bonusX = 0;
				if(l.getX() < 10)
					bonusX = 9;
				
				newElement = new InterfaceElement(coreModule, id, "Center", "Center", 300, 70, 295+bonusX, 37, 1.4f, "("+l.getX()+","+l.getY()+")", renderModule.FONT_IMPACT);
				renderModule.addElement("button"+index, newElement);
				
				index++;
			}
			
			for(int i=index; i<4; i++)
			{
				newElement = new InterfaceElement(coreModule, "button"+index, id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 562, 170, -24, 215-180*index, 1.0f, 1.0f, "interfaces/world_explore/button.png");
				renderModule.addElement("bg", newElement);
				
				index++;
			}
			
			options.clear();
		}
		
		//return text
		newElement = new InterfaceElement(coreModule, "return_button", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 560, 60, -24, -554);
		new Gesture("Click", "interface~open~InterfaceWorldMap", newElement);
		renderModule.addElement("bg", newElement);
		
		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 546, 70, 100, 10, 1.2f, "Return to World Map", coreModule.renderModule.FONT_IMPACT);
		renderModule.addElement("return_button", newElement);
	}
	
	public void destroy()
	{
		super.destroy();
		
		if(gameModule.partyData.getMainCharacter()!=null)
			if(gameModule.partyData.getMainCharacter().currentLocation == null)
				return;
	}

	//given a string of input, it will perform something upon the interface
	public void processInput(String inputString, String additionalInput)
	{
		if(inputString.equals("Up"))
		{
			if(optionOffset > 0)
				optionOffset--;
		}
		else if(inputString.equals("Down"))
		{
			if(optionOffset+3 < optionCount)
				optionOffset++;
		}
		else if(inputString.equals("Activate"))
		{
			planet = additionalInput;
		}
		if(inputString.equals("Enter"))
		{
			Location l = gameModule.partyData.getMainCharacter().currentLocation;
			
			if(l!=null)
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
			return;
		}
		
		gameModule.activateInterface("InterfaceViewMap");
	}
}
