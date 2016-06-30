package com.snorlon.sevestralkingdoms.Interfaces;

import com.snorlon.sevestralkingdoms.GameComponents.Common;
import com.snorlon.sevestralkingdoms.GameTools.Core;
import com.snorlon.sevestralkingdoms.GameTools.Gesture;
import com.snorlon.sevestralkingdoms.GameTools.InterfaceElement;
import com.snorlon.sevestralkingdoms.NPC.Unit;

public class InterfacePartyOverview extends GenericInterface
{
	
	boolean swapping = false;

	public InterfacePartyOverview(Core newCore)
	{
		super(newCore);
		// TODO Auto-generated constructor stub
		
		id = "InterfacePartyOverview";
	}
	
	//used to pull up the particular screen in question
	public void activate()
	{
		if(coreModule.gameModule.partyData.getSelectedUnit() == 1)
			coreModule.gameModule.partyData.selectUnit(0);	
		//reset things
		coreModule.gameModule.partyData.setCurrentAbility(null);
		
		//create a gui container
		newElement = new InterfaceElement(coreModule, "InterfaceContainer", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 720, 1180, 0, 0);
		renderModule.addElement("CenterNode", newElement);
		
		//bg
		newElement = new InterfaceElement(coreModule, "bg", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 718, 1280, 24, 51, 1.0f, 1.0f, "menu/menubg.png");
		renderModule.addElement("InterfaceContainer", newElement);
		
		//party bg
		newElement = new InterfaceElement(coreModule, "party_background_bg", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 718, 1280, -24, -50, 1.0f, 1.0f, "interfaces/party_overview/background.png");
		renderModule.addElement("bg", newElement);
		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 700, 70, 210, 446, 1.2f, "Manage Party", coreModule.renderModule.FONT_IMPACT);
		renderModule.addElement("bg", newElement);
		
		//rebuild our rendering container
		newElement = new InterfaceElement(coreModule, "TopLayerContainer1", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 720, 1180, 0, 0);
		renderModule.addElement("party_background_bg", newElement);
		newElement = new InterfaceElement(coreModule, "TopLayerContainer2", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 720, 1180, 0, 0);
		renderModule.addElement("party_background_bg", newElement);
		newElement = new InterfaceElement(coreModule, "TopLayerContainer3", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 720, 1180, 0, 0);
		renderModule.addElement("party_background_bg", newElement);
		newElement = new InterfaceElement(coreModule, "TopLayerContainer", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 720, 1180, 0, 0);
		renderModule.addElement("party_background_bg", newElement);
		
		int index = 1;

		//render all units owned by the player
		for(Unit u : coreModule.gameModule.partyData.getPartyMembers())
		{
			//DESTROY all battle elements besides our own
			
			if(u!=null)
			{
				//render their image at a location
				for(int i=3; i>0; i--)
				{
					for(int j=1; j<=5; j++)
					{
						if(u.x == j && u.y == i)
						{
							//shift for this part		
							int drawY = (int) ((u.y * 104) - 55);
							int drawX = -310 + (u.x * 104);

							newElement = u.generateImage(coreModule, renderModule, "TopLayerContainer"+(4-u.y), id, 2.0f, drawX, drawY);
							new Gesture("Click", "party_screen_select~"+index, newElement);
							
							//if it's the party leader, show them as such
							if(coreModule.gameModule.partyData.getMainCharacter() == u)
							{
								newElement = new InterfaceElement(coreModule, "leadericon", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 98, 98, 0, 0, 1.0f, 1.0f, "menu/party/leadericon.png");
								newElement.setColor(255, 255, 255, 0.8f);
								coreModule.renderModule.addElement(u.imageID+id, newElement);
							}
							
							if(coreModule.gameModule.partyData.getCurrentUnit() == u)
							{
								//readd it
								newElement = new InterfaceElement(coreModule, "selectionBox", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 98, 98, 0, 0, 1.0f, 1.0f, "menu/party/highlightedunit.png");
								coreModule.renderModule.addElement(u.imageID+id, newElement);
							}
						}
					}
				}
			}
			
			index++;
		}
		
		//add various elements to it
		
		//BUTTONS
		//Attacks BUTTON
		newElement = new InterfaceElement(coreModule, "party_button_container2", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 141, 70, -216, 389);
		new Gesture("Click", "interface~open~InterfacePartyMemberAbilities", newElement);
		renderModule.addElement("TopLayerContainer", newElement);
		
		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 154, 55, 26, 34, 1.0f, "Abilities", coreModule.renderModule.FONT_IMPACT);
		renderModule.addElement("party_button_container2", newElement);
		
		//Weapons BUTTON
		newElement = new InterfaceElement(coreModule, "party_button_container3", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 141, 70, -71, 389);
		new Gesture("Click", "interface~open~InterfacePartyEquipmentOffensive", newElement);
		renderModule.addElement("TopLayerContainer", newElement);
		
		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 144, 55, 13, 34, 1.0f, "Weapons", coreModule.renderModule.FONT_IMPACT);
		renderModule.addElement("party_button_container3", newElement);
		
		//Armor BUTTON
		newElement = new InterfaceElement(coreModule, "party_button_container5", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 141, 70, 71, 389);
		new Gesture("Click", "interface~open~InterfacePartyEquipmentDefensive", newElement);
		renderModule.addElement("TopLayerContainer", newElement);
		
		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 114, 55, 20, 34, 1.0f, "Armor", coreModule.renderModule.FONT_IMPACT);
		renderModule.addElement("party_button_container5", newElement);
		
		//swap BUTTON
		newElement = new InterfaceElement(coreModule, "party_button_container4", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 141, 70, 215, 389);
		new Gesture("Click", "interface~action~InterfacePartyOverview:start swap", newElement);
		renderModule.addElement("TopLayerContainer", newElement);
		
		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 144, 55, 34, 0, 1.0f, "Swap", coreModule.renderModule.FONT_IMPACT);
		renderModule.addElement("party_button_container4", newElement);
		
		
		
		coreModule.gameModule.partyData.selectDisplayUnit(coreModule.gameModule.partyData.getSelectedUnit());//reset our selected index


		Unit currUnit = gameModule.partyData.getCurrentUnit();
		
		//display unit info
		//name
		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 600, 55, 90, -68, 1.2f, currUnit.name, coreModule.renderModule.FONT_IMPACT);
		renderModule.addElement("TopLayerContainer", newElement);
		//power
		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 600, 55, 90, -126, 1.0f, ""+((int) (currUnit.threatLevel())), coreModule.renderModule.FONT_IMPACT);
		renderModule.addElement("TopLayerContainer", newElement);
		//level
		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 600, 55, 336, -68, 1.2f, "Lv"+currUnit.level, coreModule.renderModule.FONT_IMPACT);
		renderModule.addElement("TopLayerContainer", newElement);
		//stats
		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 600, 55, 75, -180, 1.0f, ""+((int) Math.floor(currUnit.Strength)), coreModule.renderModule.FONT_IMPACT);
		renderModule.addElement("TopLayerContainer", newElement);
		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 600, 55, 176, -180, 1.0f, ""+((int) Math.floor(currUnit.Toughness)), coreModule.renderModule.FONT_IMPACT);
		renderModule.addElement("TopLayerContainer", newElement);
		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 600, 55, 75, -235, 1.0f, ""+((int) Math.floor(currUnit.Agility)), coreModule.renderModule.FONT_IMPACT);
		renderModule.addElement("TopLayerContainer", newElement);
		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 600, 55, 176, -235, 1.0f, ""+((int) Math.floor(currUnit.Dexterity)), coreModule.renderModule.FONT_IMPACT);
		renderModule.addElement("TopLayerContainer", newElement);
		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 600, 55, 75, -290, 1.0f, ""+((int) Math.floor(currUnit.Intelligence)), coreModule.renderModule.FONT_IMPACT);
		renderModule.addElement("TopLayerContainer", newElement);
		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 600, 55, 176, -290, 1.0f, ""+((int) Math.floor(currUnit.Willpower)), coreModule.renderModule.FONT_IMPACT);
		renderModule.addElement("TopLayerContainer", newElement);
		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 600, 55, 75, -345, 1.0f, ""+((int) Math.floor(currUnit.Charisma)), coreModule.renderModule.FONT_IMPACT);
		renderModule.addElement("TopLayerContainer", newElement);
		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 600, 55, 176, -345, 1.0f, ""+((int) Math.floor(currUnit.Luck)), coreModule.renderModule.FONT_IMPACT);
		renderModule.addElement("TopLayerContainer", newElement);
		
		//resistances
		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 600, 55, 496, -124, 1.0f, ""+((int) Math.floor((currUnit.getResist(Common.TYPE_NONE))*100))+"%", coreModule.renderModule.FONT_IMPACT);
		renderModule.addElement("TopLayerContainer", newElement);
		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 600, 55, 325, -180, 1.0f, ""+((int) Math.floor((currUnit.getResist(Common.TYPE_FIRE))*100))+"%", coreModule.renderModule.FONT_IMPACT);
		renderModule.addElement("TopLayerContainer", newElement);
		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 600, 55, 496, -180, 1.0f, ""+((int) Math.floor((currUnit.getResist(Common.TYPE_WATER))*100))+"%", coreModule.renderModule.FONT_IMPACT);
		renderModule.addElement("TopLayerContainer", newElement);
		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 600, 55, 325, -236, 1.0f, ""+((int) Math.floor((currUnit.getResist(Common.TYPE_EARTH))*100))+"%", coreModule.renderModule.FONT_IMPACT);
		renderModule.addElement("TopLayerContainer", newElement);
		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 600, 55, 496, -236, 1.0f, ""+((int) Math.floor((currUnit.getResist(Common.TYPE_WIND))*100))+"%", coreModule.renderModule.FONT_IMPACT);
		renderModule.addElement("TopLayerContainer", newElement);
		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 600, 55, 325, -290, 1.0f, ""+((int) Math.floor((currUnit.getResist(Common.TYPE_TIME))*100))+"%", coreModule.renderModule.FONT_IMPACT);
		renderModule.addElement("TopLayerContainer", newElement);
		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 600, 55, 496, -290, 1.0f, ""+((int) Math.floor((currUnit.getResist(Common.TYPE_SPACE))*100))+"%", coreModule.renderModule.FONT_IMPACT);
		renderModule.addElement("TopLayerContainer", newElement);
		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 600, 55, 325, -346, 1.0f, ""+((int) Math.floor((currUnit.getResist(Common.TYPE_LIFE))*100))+"%", coreModule.renderModule.FONT_IMPACT);
		renderModule.addElement("TopLayerContainer", newElement);
		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 600, 55, 496, -346, 1.0f, ""+((int) Math.floor((currUnit.getResist(Common.TYPE_VOID))*100))+"%", coreModule.renderModule.FONT_IMPACT);
		renderModule.addElement("TopLayerContainer", newElement);
		
		//morale
		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 300, 55, 330, -120, 1.0f, ""+currUnit.Morale+"%", coreModule.renderModule.FONT_IMPACT);
		renderModule.addElement("bg", newElement);
		
		//draw morale, loyalty, exp bars
		//experience bar
		newElement = new InterfaceElement(coreModule, "expBarContainer", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 321, 26, -95, -419);
		renderModule.addElement("TopLayerContainer", newElement);
		newElement = new InterfaceElement(coreModule, "expBar", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Left", "Center", 321, 26, 0, 0, (float) (currUnit.exp / currUnit.max_exp), 1.0f, "menu/party/experience_bar.png");
		renderModule.addElement("expBarContainer", newElement);
		
		//draw bar frames
		if(currUnit.max_exp <= 0)
		{
			System.out.println("ERROR: Max Experience is zero!");
		}
		
		//display exp bar info
		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 600, 55, 150, -422, 1.3f, ""+(String.format("%.2f", (currUnit.exp*100 / currUnit.max_exp)))+"%", coreModule.renderModule.FONT_IMPACT);
		renderModule.addElement("TopLayerContainer", newElement);
		
		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 300, 55, 225, -410, 1.1f, ""+(int)(currUnit.exp)+"/"+((int) currUnit.max_exp), coreModule.renderModule.FONT_IMPACT);
		renderModule.addElement("TopLayerContainer", newElement);
		
		//return text
		newElement = new InterfaceElement(coreModule, "return_button", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 560, 60, -24, -554);
		new Gesture("Click", "interface~open~InterfaceMenuOverview", newElement);
		renderModule.addElement("bg", newElement);
		
		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 546, 70, 100, 10, 1.2f, "Return to Main Menu", coreModule.renderModule.FONT_IMPACT);
		renderModule.addElement("return_button", newElement);
	}
	
	//given a string of input, it will perform something upon the interface
	public void processInput(String inputString, String additionalInput)
	{
		if(inputString.equals("start swap"))
		{
			InterfaceElement newElement = null;
			
			if(swapping)
				return;///don't swap if we're already swapping!
			
			Unit currUnit = null;
			
			if(coreModule.gameModule.partyData.getPartyMembers()[coreModule.gameModule.partyData.getSelectedUnit()-1]!=null)
				currUnit = coreModule.gameModule.partyData.getPartyMembers()[coreModule.gameModule.partyData.getSelectedUnit()-1];
			else
				return;
			
			//draw boxes for swapping
			for(int y=1; y<=3; y++)
			{
				for(int x=1; x<=5; x++)
				{
					int drawY = (int) ((y * 104) - 55);
					int drawX = -310 + (x * 104);
					
					if(currUnit != null)
					{
						if(x == currUnit.x && y == currUnit.y)
						{
							newElement = new InterfaceElement(coreModule, "partySwap"+x+""+y, id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 98, 98, drawX, drawY, 1.0f, 1.0f, "menu/party/cancel_swap.png");
							new Gesture("Click", "interface~action~InterfacePartyOverview:cancel swap", newElement);
							coreModule.renderModule.addElement("TopLayerContainer", newElement);
						}
						else
						{
							newElement = new InterfaceElement(coreModule, "partySwap"+x+""+y, id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 98, 98, drawX, drawY, 1.0f, 1.0f, "menu/party/swap.png");
							new Gesture("Click", "interface~action~InterfacePartyOverview:finish swap:"+x+" "+y, newElement);
							coreModule.renderModule.addElement("TopLayerContainer", newElement);
						}
					}
				}
			}
			
			swapping = true;
		}

		else if(inputString.equals("cancel swap"))
		{
			swapping = false;
			gameModule.activateInterface(id);
		}
		
		else if(inputString.equals("finish swap"))
		{
			if(swapping)
			{
				String[] data = additionalInput.split(" ");
				
				if(data.length>=2)
				{
	
					int x = Integer.parseInt(data[0]);
					int y = Integer.parseInt(data[1]);
					
					coreModule.gameModule.partyData.swapUnit(x, y);
				}
				
				swapping = false;
			}
			
		}
	}
}
