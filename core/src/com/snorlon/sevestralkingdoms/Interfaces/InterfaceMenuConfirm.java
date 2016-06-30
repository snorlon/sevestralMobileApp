package com.snorlon.sevestralkingdoms.Interfaces;

import com.snorlon.sevestralkingdoms.GameComponents.Common;
import com.snorlon.sevestralkingdoms.GameTools.Core;
import com.snorlon.sevestralkingdoms.GameTools.Gesture;
import com.snorlon.sevestralkingdoms.GameTools.InterfaceElement;
import com.snorlon.sevestralkingdoms.Items.*;
import com.snorlon.sevestralkingdoms.NPC.Unit;

//this menu exists purely to add a confirmation aspect to modules of the app universally
public class InterfaceMenuConfirm extends GenericInterface
{
	static final public int MODE_CONSUME_INVENTORY = 0;
	static final public int MODE_RESET_GAME = 1;
	static final public int MODE_FIRE = 2;
	static final public int MODE_DISCARD_INVENTORY = 3;
	static final public int MODE_NEW_GAME = 4;
	
	int mode = MODE_CONSUME_INVENTORY;

	//TO DO: Redesign around recieving mode, and on loading this UI, poll related modules for data 
	public InterfaceMenuConfirm(Core newCore)
	{
		super(newCore);
		
		id = "InterfaceMenuConfirm";
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
		newElement = new InterfaceElement(coreModule, "overview_background_bg", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 718, 1280, -24, -50, 1.0f, 1.0f, "interfaces/confirmation/background.png");
		renderModule.addElement("bg", newElement);
		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 700, 70, 58, 425, 2.0f, "Confirmation Prompt", coreModule.renderModule.FONT_IMPACT);
		renderModule.addElement("bg", newElement);
		
		//dynamicly determined strings
		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 500, 70, 0, 360, 1.5f, generateQuestionString(), coreModule.renderModule.FONT_IMPACT);
		renderModule.addElement("InterfaceContainer", newElement);
		
		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 450, 70, 0, 42, 1.2f, generateConfirmString(), coreModule.renderModule.FONT_IMPACT);
		renderModule.addElement("InterfaceContainer", newElement);
		
		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 450, 70, 0, -292, 1.2f, generateCancelString(), coreModule.renderModule.FONT_IMPACT);
		renderModule.addElement("InterfaceContainer", newElement);
		
		//buttons
		newElement = new InterfaceElement(coreModule, "confirmButton", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center",	545, 130, 0, 149);
		new Gesture("Click", "interface~action~InterfaceMenuConfirm:confirm", newElement);
		renderModule.addElement("InterfaceContainer", newElement);

		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 546, 70, 186, 3, 1.5f, "Confirm", coreModule.renderModule.FONT_IMPACT);
		renderModule.addElement("confirmButton", newElement);
		
		newElement = new InterfaceElement(coreModule, "cancelButton", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center",	545, 130, 0, -185);
		new Gesture("Click", "interface~action~InterfaceMenuConfirm:cancel", newElement);
		renderModule.addElement("InterfaceContainer", newElement);

		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 546, 70, 195, 3, 1.5f, "Cancel", coreModule.renderModule.FONT_IMPACT);
		renderModule.addElement("cancelButton", newElement);
	}
	
	public String generateQuestionString()
	{
		switch(mode)
		{
			case MODE_CONSUME_INVENTORY:
				return "Are you sure you want to consume that item?";
			case MODE_RESET_GAME:
				return "Are you sure you want to reset the game?";
			case MODE_FIRE:
				return "Are you sure you want to fire that unit?";
			case MODE_DISCARD_INVENTORY:
				return "Are you sure you want to destroy that item?";
			case MODE_NEW_GAME:
				return "Are you sure you want to start a new game?";
		}

		return "Error. No question for activity.";
	}
	
	public String generateConfirmString()
	{
		
		switch(mode)
		{
			case MODE_CONSUME_INVENTORY:
				return "The item disappears after consuming.";
			case MODE_RESET_GAME:
				return "This will destroy any existing savefile.";
			case MODE_FIRE:
				return "You'll never be able to recruit this unit again.";
			case MODE_DISCARD_INVENTORY:
				return "This destroys the item permanently.";
			case MODE_NEW_GAME:
				return "Any existing savefile will be overwritten.";
		}
		
		return "Error. No confirmation for activity.";
	}
	
	public String generateCancelString()
	{
		switch(mode)
		{
			case MODE_CONSUME_INVENTORY:
				return "Returns you to your inventory.";
			case MODE_RESET_GAME:
				return "Returns you to game options.";
			case MODE_FIRE:
				return "Returns you to the prior screen.";
			case MODE_DISCARD_INVENTORY:
				return "Returns you to your inventory.";
			case MODE_NEW_GAME:
				return "Returns you to the main menu.";
		}

		return "Error. No cancelation for activity.";
	}

	
	//given a string of input, it will perform something upon the interface
	public void processInput(String inputString, String additionalInput)
	{
		if(inputString.equals("confirm"))
		{
			//in this case, we should return to the prior screen also but we should ALSO be giving input to the ui to continue with the action it was taking
			GenericInterface e;
			switch(mode)
			{
				case MODE_CONSUME_INVENTORY:
					//tell e to consume the item
					gameModule.giveInterfaceInput("InterfaceInventory","consume_conf", "");
					break;
				case MODE_RESET_GAME:
					//reinitialize the entire game from here (tell the options screen to), it'll directly take you to the main menu here
					//delete our save file
					gameModule.deleteSave();
					
					//reset the application state
					coreModule.resetGame();
					break;
				case MODE_FIRE:
					//currently unused, but will destroy the unit in the selected slot if not the player
					//and then returns you to the unit management screen
					break;
				case MODE_DISCARD_INVENTORY:
					gameModule.giveInterfaceInput("InterfaceInventory","destroySelected", "");
					break;
				case MODE_NEW_GAME:
					//this basically forces the character creation process ignoring prior savefile until it HAS to save, giving the player a bit more time to change their mind than they think they have
					break;
			}
		}
		else if(inputString.equals("cancel"))
		{
			//we should return to the prior screen, attempting to reverse what we did getting to a confirmation UI
			GenericInterface e;
			switch(mode)
			{
				case MODE_CONSUME_INVENTORY:
					gameModule.activateInterface("InterfaceInventory");
					break;
				case MODE_RESET_GAME:
					//returns the player to the options
					gameModule.activateInterface("InterfaceOptions");
					break;
				case MODE_FIRE:
					//returns the player to the unit management screen
					gameModule.activateInterface("InterfacePartyOverview");
					break;
				case MODE_DISCARD_INVENTORY:
					e = gameModule.getInterface("InterfaceInventory");
					((InterfaceInventory) e).preserve = true;
					gameModule.activateInterface("InterfaceInventory");
					break;
				case MODE_NEW_GAME:
					//returns the player to the main menu
					gameModule.activateInterface("InterfaceMainMenu");
					break;
			}
		}
		else if(inputString.equals("activate"))
		{
			if(additionalInput.equals("consume"))
				mode = MODE_CONSUME_INVENTORY;
			else if(additionalInput.equals("reset"))
				mode = MODE_RESET_GAME;
			else if(additionalInput.equals("fire"))
				mode = MODE_FIRE;
			else if(additionalInput.equals("discard"))
				mode = MODE_DISCARD_INVENTORY;
			else if(additionalInput.equals("new"))
				mode = MODE_NEW_GAME;
			
			//make sure to activate this ui in the process
			gameModule.activateInterface(id);
		}
	}
}
