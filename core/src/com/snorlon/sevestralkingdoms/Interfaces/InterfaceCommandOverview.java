package com.snorlon.sevestralkingdoms.Interfaces;

import com.snorlon.sevestralkingdoms.GameTools.Core;
import com.snorlon.sevestralkingdoms.GameTools.Gesture;
import com.snorlon.sevestralkingdoms.GameTools.InterfaceElement;

public class InterfaceCommandOverview extends GenericInterface
{
	public InterfaceCommandOverview(Core newCore)
	{
		super(newCore);
		// TODO Auto-generated constructor stub
		
		id = "InterfaceCommandOverview";
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
		newElement = new InterfaceElement(coreModule, "overview_background_bg", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 718, 1280, -24, -50, 1.0f, 1.0f, "interfaces/command_overview/background.png");
		renderModule.addElement("bg", newElement);
		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 700, 70, 120, 425, 2.0f, "Command Menu", coreModule.renderModule.FONT_IMPACT);
		renderModule.addElement("bg", newElement);

		if(gameModule.partyData.getMainCharacter() != null)
		{
			newElement = new InterfaceElement(coreModule, id, "Center", "Center", 700, 70, 310, 365, 1.4f, ""+gameModule.partyData.getMainCharacter().getMoney(), coreModule.renderModule.FONT_IMPACT);
			renderModule.addElement("InterfaceContainer", newElement);
		}
		
		newElement = new InterfaceElement(coreModule, "leadership_button", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 169, 175, 5, 115);
		renderModule.addElement("InterfaceContainer", newElement);
		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 250, 70, 57, -35, 1.0f, "Leadership", coreModule.renderModule.FONT_IMPACT);
		renderModule.addElement("leadership_button", newElement);	
		
		newElement = new InterfaceElement(coreModule, "manage_button", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 169, 175, 189, 115);
		renderModule.addElement("InterfaceContainer", newElement);
		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 250, 70, 57, -35, 1.2f, "Territory", coreModule.renderModule.FONT_IMPACT);
		renderModule.addElement("manage_button", newElement);	
		
		newElement = new InterfaceElement(coreModule, "mission_button", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 169, 175, -179, 115);
		new Gesture("Click", "interface~open~InterfaceCommandMissions", newElement);
		renderModule.addElement("InterfaceContainer", newElement);
		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 250, 70, 60, -35, 1.2f, "Missions", coreModule.renderModule.FONT_IMPACT);
		renderModule.addElement("mission_button", newElement);	
		
		//return text
		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 546, 70, 80, -548, 1.2f, "Return to Main Menu", coreModule.renderModule.FONT_IMPACT);
		new Gesture("Click", "interface~open~InterfaceMenuOverview", newElement);
		renderModule.addElement("bg", newElement);	
	}
}
