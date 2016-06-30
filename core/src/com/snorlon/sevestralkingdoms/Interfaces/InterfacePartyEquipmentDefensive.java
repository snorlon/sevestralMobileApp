package com.snorlon.sevestralkingdoms.Interfaces;

import com.snorlon.sevestralkingdoms.GameComponents.Common;
import com.snorlon.sevestralkingdoms.GameTools.Core;
import com.snorlon.sevestralkingdoms.GameTools.Gesture;
import com.snorlon.sevestralkingdoms.GameTools.InterfaceElement;
import com.snorlon.sevestralkingdoms.Items.Armor;
import com.snorlon.sevestralkingdoms.Items.Spell;
import com.snorlon.sevestralkingdoms.Items.Weapon;
import com.snorlon.sevestralkingdoms.NPC.Unit;

public class InterfacePartyEquipmentDefensive extends GenericInterface
{

	public InterfacePartyEquipmentDefensive(Core newCore)
	{
		super(newCore);
		// TODO Auto-generated constructor stub
		
		id = "InterfacePartyEquipmentDefensive";
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
		
		//party bg
		newElement = new InterfaceElement(coreModule, "party_background_bg", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 718, 1280, -24, -50, 1.0f, 1.0f, "menu/party/party equipment background defensive.png");
		renderModule.addElement("bg", newElement);
		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 700, 70, 125, 446, 1.2f, "Unit Defensive Equipment", coreModule.renderModule.FONT_IMPACT);
		renderModule.addElement("bg", newElement);
		

		Unit currUnit = null;
		
		if(coreModule.gameModule.partyData.getPartyMembers()[coreModule.gameModule.partyData.getSelectedUnit()-1]!=null)
			currUnit = coreModule.gameModule.partyData.getPartyMembers()[coreModule.gameModule.partyData.getSelectedUnit()-1];
		else
			return;

		//current unit data
		//name
		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 600, 55, 170, 373, 1.7f, ""+currUnit.name, coreModule.renderModule.FONT_IMPACT);
		renderModule.addElement("InterfaceContainer", newElement);
		
		//sprite
		currUnit.generateImage(coreModule, renderModule, "InterfaceContainer", id, 2.0f, -228, 389);
		
		//level
		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 200, 55, 60, -30, 1.0f, "Lv"+currUnit.level, coreModule.renderModule.FONT_IMPACT);
		renderModule.addElement(currUnit.imageID+id, newElement);

		String binName = "";
		Armor currItem = null;
		
		//show dem armor pieces
		for(int i=0; i<3; i++)
		{
			currItem = null;//reset this!

			if(i == 0)
			{
				currItem = currUnit.helmetSlot;
				binName = "helmet_container";
			}
			else if(i == 1)
			{
				currItem = currUnit.chestpieceSlot;
				binName = "chestpiece_container";
			}
			else if(i == 2)
			{
				currItem = currUnit.leggingsSlot;
				binName = "leggings_container";
			}
			
			if(currItem != null)
			{
				
				int renderIndex = 0;
				
				//create a page for the weapon
				newElement = new InterfaceElement(coreModule, binName, id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 546, 232, 85, 307-253*i);
				//new Gesture("Click", "interface~open~InterfacePartyEquipmentDefensive", newElement);//clicking should view gear in detail
				renderModule.addElement("InterfaceContainer", newElement);	

				newElement = new InterfaceElement(coreModule, id, "Center", "Center", 700, 55, 110, -20, 1.3f, ""+currItem.name, coreModule.renderModule.FONT_IMPACT);
				renderModule.addElement(binName, newElement);
				
				//armor class
				newElement = new InterfaceElement(coreModule, id, "Center", "Center", 700, 55, 10, -110, 1.0f, ""+currItem.getSlot(), coreModule.renderModule.FONT_IMPACT);
				renderModule.addElement(binName, newElement);
				
				//armor rank
				newElement = new InterfaceElement(coreModule, id, "Center", "Center", 700, 55, 8, -152, 0.8f, "Rank: "+currItem.getRank(), coreModule.renderModule.FONT_IMPACT);
				renderModule.addElement(binName, newElement);
				
				//item icon
				currItem.generateIcon(coreModule,""+currItem.imageID,id, binName, -306, -37);
				

				//unequip
				if(gameModule.playerInventory.getContents().size() < gameModule.playerInventory.getCapacity() && gameModule.partyData.getMainCharacter() == currUnit)
				{
					newElement = new InterfaceElement(coreModule, binName+"unequip", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 546, 43, -83, -215);
					new Gesture("Click", "interface~action~InterfacePartyEquipmentDefensive:unequip:"+i, newElement);
					renderModule.addElement(binName, newElement);
					newElement = new InterfaceElement(coreModule, id, "Center", "Center", 250, 55, 60, 4, 1.1f, "Unequip", coreModule.renderModule.FONT_IMPACT);
					renderModule.addElement(binName+"unequip", newElement);
				}
				
				//display resists
				for(int j=0; j<9; j++)
				{
					if(j == currItem.getType())
					{
						renderIndex++;
						
						//render the resist for this index
						//type icons							
						newElement = new InterfaceElement(coreModule, "type"+renderIndex+currItem.imageID, id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 720, 28, -205, -22-renderIndex*46, 1.0f, 1.0f, "battle/abilities/types.png");
						newElement.generateFrames(80, 28, 0, j, 9, false);
						renderModule.addElement(binName, newElement);
						
						//text 
						newElement = new InterfaceElement(coreModule, id, "Center", "Center", 700, 55, 165, -110-renderIndex*46, 2.0f, "Resist "+((int) (currItem.getStatValue()*100))+"% ", coreModule.renderModule.FONT_IMPACT);
						renderModule.addElement(binName, newElement);
						
						newElement = new InterfaceElement(coreModule, id, "Center", "Center", 700, 55, 230, -18-renderIndex*46, 1.0f, Common.TypeToString(j)+" Damage", coreModule.renderModule.FONT_IMPACT);
						renderModule.addElement(binName, newElement);
					}
				}
				
			}
			else
			{
				//create a page for the weapon
				newElement = new InterfaceElement(coreModule, binName, id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 546, 232, 85, 307-251*i);
				renderModule.addElement("InterfaceContainer", newElement);	

				switch(i)
				{
					case 0:
						newElement = new InterfaceElement(coreModule, id, "Center", "Center", 700, 55, 110, -15, 1.1f, "No Helmet Equipped", coreModule.renderModule.FONT_IMPACT);
						renderModule.addElement(binName, newElement);
						break;
					case 1:
						newElement = new InterfaceElement(coreModule, id, "Center", "Center", 700, 55, 110, -15, 1.1f, "No Chestpiece Equipped", coreModule.renderModule.FONT_IMPACT);
						renderModule.addElement(binName, newElement);
						break;
					case 2:
						newElement = new InterfaceElement(coreModule, id, "Center", "Center", 700, 55, 110, -15, 1.1f, "No Leggings Equipped", coreModule.renderModule.FONT_IMPACT);
						renderModule.addElement(binName, newElement);
						break;
				}
			}
		}
		
		//return text
		newElement = new InterfaceElement(coreModule, "return_button", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 560, 60, -24, -554);
		new Gesture("Click", "interface~open~InterfacePartyOverview", newElement);
		renderModule.addElement("bg", newElement);
		
		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 546, 70, 70, 10, 1.2f, "Return to Party Overview", coreModule.renderModule.FONT_IMPACT);
		renderModule.addElement("return_button", newElement);
	}
	
	//given a string of input, it will perform something upon the interface
	public void processInput(String inputString, String additionalInput)
	{
		if(inputString.equals("unequip"))
		{
			if(additionalInput.length() > 0)
			{
				int index = Integer.parseInt(additionalInput);
				
				Unit p = gameModule.partyData.getCurrentUnit();

				if(index == 0)
				{
					//unequip the weapon if we have room
					Armor h = p.helmetSlot;
					
					if(h!=null)
					{
						p.unequipHelmet();
						gameModule.playerInventory.moveItem(h);
					}
				}
				else if(index == 1)
				{
					//unequip the weapon if we have room
					Armor h = p.chestpieceSlot;
					
					if(h!=null)
					{
						p.unequipChestpiece();
						gameModule.playerInventory.moveItem(h);
					}
				}
				else if(index == 2)
				{
					//unequip the weapon if we have room
					Armor h = p.leggingsSlot;
					
					if(h!=null)
					{
						p.unequipLeggings();
						gameModule.playerInventory.moveItem(h);
					}
				}
				
				gameModule.saveSave();
				
				gameModule.activateInterface("InterfacePartyEquipmentDefensive");
			}
		}
	}
}

