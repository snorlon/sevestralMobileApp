package com.snorlon.sevestralkingdoms.Interfaces;

import com.snorlon.sevestralkingdoms.GameTools.Core;
import com.snorlon.sevestralkingdoms.GameTools.Gesture;
import com.snorlon.sevestralkingdoms.GameTools.InterfaceElement;
import com.snorlon.sevestralkingdoms.Places.Location;

public class InterfaceWorldMap extends GenericInterface
{

	public InterfaceWorldMap(Core newCore)
	{
		super(newCore);
		// TODO Auto-generated constructor stub
		
		id = "InterfaceWorldMap";
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
		newElement = new InterfaceElement(coreModule, "background_bg", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 718, 1280, -24, -50, 1.0f, 1.0f, "interfaces/world_map/background.png");
		renderModule.addElement("bg", newElement);
		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 700, 70, 180, 425, 2.0f, "World Map", renderModule.FONT_IMPACT);
		renderModule.addElement("bg", newElement);
		
		//planet buttons

		newElement = new InterfaceElement(coreModule, "planet_button_fire", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 258, 179, -143, 290);
		if(gameModule.availableLocations.contains("Fire"))
			new Gesture("Click", "interface~action~InterfaceViewMap:Activate:Fire", newElement);
		renderModule.addElement("background_bg", newElement);
		if(gameModule.availableLocations.contains("Fire"))
		{
			newElement = new InterfaceElement(coreModule, id, "Center", "Center", 300, 70, 62, -25, 2.0f, ""+gameModule.planetNames.get("Fire"), renderModule.FONT_IMPACT);
			renderModule.addElement("planet_button_fire", newElement);
		}
		if(gameModule.partyData.getMainCharacter().currentLocation != null && gameModule.partyData.getMainCharacter().currentLocation.planet.equals("Fire"))
		{
			newElement = new InterfaceElement(coreModule, "here_button", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 258, 96, 0, 50, 1.0f, 1.0f, "interfaces/world_map/here.png");
			renderModule.addElement("planet_button_fire", newElement);
		}

		newElement = new InterfaceElement(coreModule, "planet_button_water", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 258, 179, 143, 290);
		if(gameModule.availableLocations.contains("Water"))
			new Gesture("Click", "interface~action~InterfaceViewMap:Activate:Water", newElement);
		renderModule.addElement("background_bg", newElement);
		if(gameModule.availableLocations.contains("Water"))
		{
			newElement = new InterfaceElement(coreModule, id, "Center", "Center", 300, 70, 24, -25, 2.0f, ""+gameModule.planetNames.get("Water"), renderModule.FONT_IMPACT);
			renderModule.addElement("planet_button_water", newElement);
		}
		if(gameModule.partyData.getMainCharacter().currentLocation != null && gameModule.partyData.getMainCharacter().currentLocation.planet.equals("Water"))
		{
			newElement = new InterfaceElement(coreModule, "here_button", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 258, 96, 0, 50, 1.0f, 1.0f, "interfaces/world_map/here.png");
			renderModule.addElement("planet_button_water", newElement);
		}

		newElement = new InterfaceElement(coreModule, "planet_button_earth", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 258, 179, -143, 91);
		if(gameModule.availableLocations.contains("Earth"))
			new Gesture("Click", "interface~action~InterfaceViewMap:Activate:Earth", newElement);
		renderModule.addElement("background_bg", newElement);
		if(gameModule.availableLocations.contains("Earth"))
		{
			newElement = new InterfaceElement(coreModule, id, "Center", "Center", 300, 70, 62, -25, 2.0f, ""+gameModule.planetNames.get("Earth"), renderModule.FONT_IMPACT);
			renderModule.addElement("planet_button_earth", newElement);
		}
		if(gameModule.partyData.getMainCharacter().currentLocation != null && gameModule.partyData.getMainCharacter().currentLocation.planet.equals("Earth"))
		{
			newElement = new InterfaceElement(coreModule, "here_button", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 258, 96, 0, 50, 1.0f, 1.0f, "interfaces/world_map/here.png");
			renderModule.addElement("planet_button_earth", newElement);
		}
			
		newElement = new InterfaceElement(coreModule, "planet_button_wind", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 258, 179, 143, 91);
		if(gameModule.availableLocations.contains("Wind"))
			new Gesture("Click", "interface~action~InterfaceViewMap:Activate:Wind", newElement);
		renderModule.addElement("background_bg", newElement);
		if(gameModule.availableLocations.contains("Wind"))
		{
			newElement = new InterfaceElement(coreModule, id, "Center", "Center", 300, 70, 60, -25, 2.0f, ""+gameModule.planetNames.get("Wind"), renderModule.FONT_IMPACT);
			renderModule.addElement("planet_button_wind", newElement);
		}
		if(gameModule.partyData.getMainCharacter().currentLocation != null && gameModule.partyData.getMainCharacter().currentLocation.planet.equals("Wind"))
		{
			newElement = new InterfaceElement(coreModule, "here_button", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 258, 96, 0, 50, 1.0f, 1.0f, "interfaces/world_map/here.png");
			renderModule.addElement("planet_button_wind", newElement);
		}
			

		newElement = new InterfaceElement(coreModule, "planet_button_time", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 258, 179, -143, -108);
		if(gameModule.availableLocations.contains("Time"))
			new Gesture("Click", "interface~action~InterfaceViewMap:Activate:Time", newElement);
		renderModule.addElement("background_bg", newElement);
		if(gameModule.availableLocations.contains("Time"))
		{
			newElement = new InterfaceElement(coreModule, id, "Center", "Center", 300, 70, 47, -25, 2.0f, ""+gameModule.planetNames.get("Time"), renderModule.FONT_IMPACT);
			renderModule.addElement("planet_button_time", newElement);
		}
		if(gameModule.partyData.getMainCharacter().currentLocation != null && gameModule.partyData.getMainCharacter().currentLocation.planet.equals("Time"))
		{
			newElement = new InterfaceElement(coreModule, "here_button", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 258, 96, 0, 50, 1.0f, 1.0f, "interfaces/world_map/here.png");
			renderModule.addElement("planet_button_time", newElement);
		}
		
		newElement = new InterfaceElement(coreModule, "planet_button_space", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 258, 179, 143, -108);
		if(gameModule.availableLocations.contains("Space"))
			new Gesture("Click", "interface~action~InterfaceViewMap:Activate:Space", newElement);
		renderModule.addElement("background_bg", newElement);
		if(gameModule.availableLocations.contains("Space"))
		{
			newElement = new InterfaceElement(coreModule, id, "Center", "Center", 300, 70, 28, -25, 2.0f, ""+gameModule.planetNames.get("Space"), renderModule.FONT_IMPACT);
			renderModule.addElement("planet_button_space", newElement);
		}
		if(gameModule.partyData.getMainCharacter().currentLocation != null && gameModule.partyData.getMainCharacter().currentLocation.planet.equals("Space"))
		{
			newElement = new InterfaceElement(coreModule, "here_button", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 258, 96, 0, 50, 1.0f, 1.0f, "interfaces/world_map/here.png");
			renderModule.addElement("planet_button_space", newElement);
		}

		newElement = new InterfaceElement(coreModule, "planet_button_life", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 258, 179, -143, -307);
		if(gameModule.availableLocations.contains("Life"))
			new Gesture("Click", "interface~action~InterfaceViewMap:Activate:Life", newElement);
		renderModule.addElement("background_bg", newElement);
		if(gameModule.availableLocations.contains("Life"))
		{
			newElement = new InterfaceElement(coreModule, id, "Center", "Center", 300, 70, 50, -25, 2.0f, ""+gameModule.planetNames.get("Life"), renderModule.FONT_IMPACT);
			renderModule.addElement("planet_button_life", newElement);
		}
		if(gameModule.partyData.getMainCharacter().currentLocation != null && gameModule.partyData.getMainCharacter().currentLocation.planet.equals("Life"))
		{
			newElement = new InterfaceElement(coreModule, "here_button", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 258, 96, 0, 50, 1.0f, 1.0f, "interfaces/world_map/here.png");
			renderModule.addElement("planet_button_life", newElement);
		}

		newElement = new InterfaceElement(coreModule, "planet_button_void", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 258, 179, 143, -307);
		if(gameModule.availableLocations.contains("Void"))
			new Gesture("Click", "interface~action~InterfaceViewMap:Activate:Void", newElement);
		renderModule.addElement("background_bg", newElement);
		if(gameModule.availableLocations.contains("Void"))
		{
			newElement = new InterfaceElement(coreModule, id, "Center", "Center", 300, 70, 40, -25, 2.0f, ""+gameModule.planetNames.get("Void"), renderModule.FONT_IMPACT);
			renderModule.addElement("planet_button_void", newElement);
		}
		if(gameModule.partyData.getMainCharacter().currentLocation != null && gameModule.partyData.getMainCharacter().currentLocation.planet.equals("Voi"))
		{
			newElement = new InterfaceElement(coreModule, "here_button", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 258, 96, 0, 50, 1.0f, 1.0f, "interfaces/world_map/here.png");
			renderModule.addElement("planet_button_void", newElement);
		}
		
		//return text
		newElement = new InterfaceElement(coreModule, "return_button", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 560, 60, -24, -554);
		new Gesture("Click", "interface~open~InterfaceMenuOverview", newElement);
		renderModule.addElement("bg", newElement);
		
		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 546, 70, 100, 10, 1.2f, "Return to Main Menu", coreModule.renderModule.FONT_IMPACT);
		renderModule.addElement("return_button", newElement);
	}
	
	public void destroy()
	{
		super.destroy();
	}

	//given a string of input, it will perform something upon the interface
	public void processInput(String inputString, String additionalInput)
	{
	}
}
