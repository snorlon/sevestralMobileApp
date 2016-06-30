package com.snorlon.sevestralkingdoms.Interfaces;

import com.snorlon.sevestralkingdoms.GameTools.Core;
import com.snorlon.sevestralkingdoms.GameTools.Gesture;
import com.snorlon.sevestralkingdoms.GameTools.InterfaceElement;

public class ExampleInterface extends GenericInterface
{

	public ExampleInterface(Core newCore)
	{
		super(newCore);
		// TODO Auto-generated constructor stub
		
		id = "ExampleInterface";
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
		newElement = new InterfaceElement(coreModule, "overview_background_bg", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 718, 1280, -24, -50, 1.0f, 1.0f, "menu/main/main menu.png");
		renderModule.addElement("bg", newElement);
		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 700, 70, 190, 425, 2.0f, "Main Menu", coreModule.renderModule.FONT_IMPACT);
		renderModule.addElement("bg", newElement);
		
		//Inventory BUTTON
		newElement = new InterfaceElement(coreModule, "overview_button_container_inventory", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 268, 190, -142, -341);
		new Gesture("Click", "interface~action~ExampleInterface", newElement);//clicking should enter party
		renderModule.addElement("InterfaceContainer", newElement);
		
		//return text
		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 546, 70, 80, -548, 1.2f, "Return to Main Menu", coreModule.renderModule.FONT_IMPACT);
		new Gesture("Click", "interface~open~InterfaceMenuOverview", newElement);
		renderModule.addElement("bg", newElement);	
	}

}
