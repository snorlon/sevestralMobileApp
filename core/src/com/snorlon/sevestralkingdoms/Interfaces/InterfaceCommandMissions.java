package com.snorlon.sevestralkingdoms.Interfaces;

import com.snorlon.sevestralkingdoms.GameTools.Core;
import com.snorlon.sevestralkingdoms.GameTools.Gesture;
import com.snorlon.sevestralkingdoms.GameTools.InterfaceElement;

public class InterfaceCommandMissions extends GenericInterface
{
	public InterfaceCommandMissions(Core newCore)
	{
		super(newCore);
		// TODO Auto-generated constructor stub
		
		id = "InterfaceCommandMissions";
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
		newElement = new InterfaceElement(coreModule, "overview_background_bg", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 718, 1280, -24, -50, 1.0f, 1.0f, "interfaces/command_missions/background.png");
		renderModule.addElement("bg", newElement);
		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 700, 70, 210, 425, 2.0f, "Missions", coreModule.renderModule.FONT_IMPACT);
		renderModule.addElement("bg", newElement);

		if(gameModule.partyData.getMainCharacter() != null)
		{
			//newElement = new InterfaceElement(coreModule, id, "Center", "Center", 700, 70, 310, 365, 1.4f, ""+gameModule.partyData.getMainCharacter().getMoney(), coreModule.renderModule.FONT_IMPACT);
			//renderModule.addElement("InterfaceContainer", newElement);
		}
		
		newElement = new InterfaceElement(coreModule, "attack_button", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 521, 154, 2, 203);
		renderModule.addElement("InterfaceContainer", newElement);
		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 521, 70, 54, -10, 2.0f, "Attack Location", coreModule.renderModule.FONT_IMPACT);
		renderModule.addElement("attack_button", newElement);
		
		newElement = new InterfaceElement(coreModule, "defense_button", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 521, 154, 2, 33);
		renderModule.addElement("InterfaceContainer", newElement);
		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 521, 70, 54, -10, 2.0f, "Defend Location", coreModule.renderModule.FONT_IMPACT);
		renderModule.addElement("defense_button", newElement);
		
		newElement = new InterfaceElement(coreModule, "train_button", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 521, 154, 2, -137);
		renderModule.addElement("InterfaceContainer", newElement);
		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 521, 70, 27, -10, 2.0f, "Train at Location", coreModule.renderModule.FONT_IMPACT);
		renderModule.addElement("train_button", newElement);
		
		newElement = new InterfaceElement(coreModule, "end_button", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 521, 154, 2, -307);
		new Gesture("Click", "interface~action~InterfaceCommandMissions:end", newElement);
		renderModule.addElement("InterfaceContainer", newElement);
		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 521, 70, 137, -10, 2.0f, "End Turn", coreModule.renderModule.FONT_IMPACT);
		renderModule.addElement("end_button", newElement);
		
		//return text
		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 546, 70, 15, -548, 1.2f, "Return to Command Overview", coreModule.renderModule.FONT_IMPACT);
		new Gesture("Click", "interface~open~InterfaceCommandOverview", newElement);
		renderModule.addElement("bg", newElement);	
	}

	
	//given a string of input, it will perform something upon the interface
	public void processInput(String inputString, String additionalInput)
	{
		if(inputString.equals("end"))
		{
			gameModule.stepTurn();
			
			gameModule.activateInterface("InterfaceMenuOverview");
		}
	}
}
