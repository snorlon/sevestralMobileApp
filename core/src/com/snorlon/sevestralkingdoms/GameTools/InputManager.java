package com.snorlon.sevestralkingdoms.GameTools;

import com.badlogic.gdx.Gdx;
import com.snorlon.sevestralkingdoms.Ability.Ability;
import com.snorlon.sevestralkingdoms.GameComponents.GameModule;
import com.snorlon.sevestralkingdoms.GameComponents.PartyManager;
import com.snorlon.sevestralkingdoms.Interfaces.GenericInterface;
import com.snorlon.sevestralkingdoms.Items.Item;
import com.snorlon.sevestralkingdoms.NPC.Unit;
import com.snorlon.sevestralkingdoms.Places.Location;

public class InputManager {
	int lastX;
	int lastY;
	int currentX;
	int currentY;
	int changeX;
	int changeY;
	boolean activeInput = false;//if finger is down
	float timeDelay = 0.0f;
	float resetTimeAmount = 0.19f;
	
	Core coreModule;
	
	//constants
	public static int tapThreshold = 10;
	
	public InputManager(Core nCore)
	{
		coreModule = nCore;
	}
	
	public void generateEvent()
	{
		
	}
	
	public Gesture touchDown(int screenX, int screenY, int pointer, int button)
	{
		//simply update things
		lastX = screenX;
		lastY = screenY;
		currentX = screenX;
		currentY = screenY;
		
		activeInput = true;
		
		return new Gesture("Click", lastX, lastY, currentX, currentY);
	}
	
	public Gesture touchUp(int screenX, int screenY, int pointer, int button)
	{
		Gesture newGesture = null;
		currentX = screenX;
		currentY = screenY;
		
		activeInput = false;
		
		/*generate the gesture based on circumstances
		if((currentX-lastX) + (currentX-lastX) < tapThreshold)//for generic taps
		{
			newGesture = new Gesture("Click", lastX, lastY, currentX, currentY);
		}
		else if(currentX-lastX < tapThreshold)//for when moving finger up or down
		{
			if(currentY > lastY)//up
			{
				newGesture = new Gesture("Up", lastX, lastY, currentX, currentY);
			}
			else//down
			{
				newGesture = new Gesture("Down", lastX, lastY, currentX, currentY);
			}
		}
		else if(currentY-lastY < tapThreshold)//for when moving finger up or down
		{
			if(currentX > lastX)//right
			{
				newGesture = new Gesture("Right", lastX, lastY, currentX, currentY);
			}
			else//left
			{
				newGesture = new Gesture("Left", lastX, lastY, currentX, currentY);
			}
		}
		else//default case
		{
			return null;
		}*/
		
		return newGesture;
	}
	
	public Gesture touchDragged(int screenX, int screenY, int pointer)
	{
		Gesture newGesture = null;
		currentX = screenX;
		currentY = screenY;
		
		//generate the gesture based on circumstances
		/*if((currentX-lastX) + (currentX-lastX) < tapThreshold)//for generic taps
		{
			newGesture = new Gesture("Hold", lastX, lastY, currentX, currentY);
		}
		else if(currentX-lastX < tapThreshold)//for when moving finger up or down
		{
			if(currentY > lastY)//up
			{
				newGesture = new Gesture("Dragged Up", lastX, lastY, currentX, currentY);
			}
			else//down
			{
				newGesture = new Gesture("Dragged Down", lastX, lastY, currentX, currentY);
			}
		}
		else if(currentY-lastY < tapThreshold)//for when moving finger up or down
		{
			if(currentX > lastX)//right
			{
				newGesture = new Gesture("Dragged Right", lastX, lastY, currentX, currentY);
			}
			else//left
			{
				newGesture = new Gesture("Dragged Left", lastX, lastY, currentX, currentY);
			}
		}
		else//default case
		{
			return null;
		}*/
		
		return newGesture;
	}
	
	public void tick(float dt)
	{
		if(timeDelay > 0)
			timeDelay -= dt;
	}
	
	//enact what a string says to do
	public void processCommand(Gesture activeEvent)
	{
		boolean handled = false;
		
		if(activeEvent == null)
			return;//abort from bad input

		//abort if we're busy/on cooldown
		if(timeDelay > 0)
			return;
		
		String elements[] = activeEvent.command.split("\\~");
		
		for(int i=0;i<20 && i<elements.length;i++)
		{
			if(elements[i]!=null)
				elements[i].replaceAll("\\s+","");
		}
		
		//check if we have a command
		if(elements[0].equals("None"))
		{
			return;//abort as we can't do anything
		}
		else if(elements[0].equals("Quit"))
		{
			//auto-save
			coreModule.gameModule.saveSave();
			
			Gdx.app.exit();
		}
		//otherwise, use them to perform the specified task and handle it accordingly
		if(elements.length == 1)
		{
			if(elements[0].equals("test"))
			{
				handled = true;
				
				System.out.println("Input registered!");
			}
		}
		else if(elements.length == 2)
		{
			if(elements[0].equals("party_screen_select") && elements[1] != null)
			{
				handled = true;
			
				coreModule.gameModule.partyData.selectDisplayUnit(Integer.parseInt(elements[1]));
				
				coreModule.gameModule.activateInterface("InterfacePartyOverview");
			}
		}
		else if(elements.length == 3)
		{
			if(elements[0].equals("setText") && elements[1] != null && elements[2] != null)
			{
				//set text on the given id
				InterfaceElement currObject = coreModule.renderModule.interfaceModule.getElement(elements[1]);
				if(currObject != null)
				{
					currObject.updateText(elements[2]);
					return; //succeeded, abort
				}
				handled = true;
			}
			//primary interface changing handler
			else if(elements[0].equals("interface") && elements[1] != null && elements[2] != null)
			{
				handled = true;

				if(elements[1].equals("open"))
				{
					String[] data = elements[2].split(":");
					
					if(data.length > 1)
					{
						coreModule.gameModule.giveInterfaceInput(data[0], data[1], "");
					}
					
					coreModule.gameModule.activateInterface(data[0]);
					coreModule.soundModule.playSound("Click");
				}
				else if(elements[1].equals("close"))
					coreModule.gameModule.deactivateInterface(elements[2]);
				else if(elements[1].equals("action"))
				{
					String[] data = elements[2].split(":");

					if(data.length == 2)
						coreModule.gameModule.giveInterfaceInput(data[0], data[1], "");
					else if(data.length > 2)
						coreModule.gameModule.giveInterfaceInput(data[0], data[1], data[2]);
					coreModule.soundModule.playSound("Click");
				}
			}
			else if(elements[0].equals("change_party_state") && elements[1] != null && elements[2] != null)//for changing party screen state
			{
				GameModule gameModule = coreModule.gameModule;
				String mode = elements[1].trim();
				
				if(gameModule.partyData!=null)
				{
					//switch between possible states to enter
					if(mode.equals("Ability"))
					{
						gameModule.partyData.selectAbility(Integer.parseInt(elements[2]));
						
						gameModule.activateInterface("InterfacePartyMemberAbilitiesDetailed");
						handled = true;
					}
				}
			}
		}
		else if(elements.length == 4)
		{
			if(elements[0].equals("travel") && elements[1] != null && elements[2] != null && elements[3] != null)//for changing map
			{
				GameModule gameModule = coreModule.gameModule;
				
				gameModule.travelLocation(elements[1].trim(), Integer.parseInt(elements[2].trim()), Integer.parseInt(elements[3].trim()), false);

				//save the game!
				gameModule.saveSave();
				
				handled = true;
			}
		}
		
		if(handled)
		{
			//set the time delay
			timeDelay = resetTimeAmount;
		}
	}
}
