package com.snorlon.sevestralkingdoms.Interfaces;

import com.snorlon.sevestralkingdoms.GameComponents.Common;
import com.snorlon.sevestralkingdoms.GameTools.Core;
import com.snorlon.sevestralkingdoms.GameTools.Gesture;
import com.snorlon.sevestralkingdoms.GameTools.InterfaceElement;
import com.snorlon.sevestralkingdoms.Items.Spell;
import com.snorlon.sevestralkingdoms.Items.Weapon;
import com.snorlon.sevestralkingdoms.NPC.Unit;

public class InterfacePartyEquipmentOffensive extends GenericInterface
{

	public InterfacePartyEquipmentOffensive(Core newCore)
	{
		super(newCore);
		// TODO Auto-generated constructor stub
		
		id = "InterfacePartyEquipmentOffensive";
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
		newElement = new InterfaceElement(coreModule, "party_background_bg", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 718, 1280, -24, -50, 1.0f, 1.0f, "menu/party/party equipment background.png");
		renderModule.addElement("bg", newElement);
		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 700, 70, 130, 446, 1.2f, "Unit Offensive Equipment", coreModule.renderModule.FONT_IMPACT);
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
		
		//show dem weapons
		if(currUnit.weaponSlot != null)
		{
			Weapon currItem = currUnit.weaponSlot;
			
			//create a page for the weapon
			newElement = new InterfaceElement(coreModule, "weapon_container", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 546, 142, 85, 311);
			//new Gesture("Click", "interface~open~InterfacePartyEquipmentDefensive", newElement);//clicking should view gear in detail
			renderModule.addElement("InterfaceContainer", newElement);	

			newElement = new InterfaceElement(coreModule, id, "Center", "Center", 700, 55, 120, -8, 1.0f, ""+currItem.name, coreModule.renderModule.FONT_IMPACT);
			renderModule.addElement("weapon_container", newElement);
			
			//item class
			newElement = new InterfaceElement(coreModule, id, "Center", "Center", 200, 55, -242, -100, 0.75f, "Weapon", coreModule.renderModule.FONT_IMPACT);
			renderModule.addElement("weapon_container", newElement);
			
			//weapon attack type
			newElement = new InterfaceElement(coreModule, id, "Center", "Center", 700, 55, 120, -54, 1.0f, ""+currItem.wepAbility.getAttackType(), coreModule.renderModule.FONT_IMPACT);
			renderModule.addElement("weapon_container", newElement);
			
			//weapon rank
			newElement = new InterfaceElement(coreModule, id, "Center", "Center", 700, 55, 340, -54, 1.0f, "Rank: "+currItem.getRank(), coreModule.renderModule.FONT_IMPACT);
			renderModule.addElement("weapon_container", newElement);

			//unequip
			if(gameModule.playerInventory.getContents().size() < gameModule.playerInventory.getCapacity() && gameModule.partyData.getMainCharacter() == currUnit)
			{
				newElement = new InterfaceElement(coreModule, "weapon_unequip", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 546, 43, -83, -154);
				new Gesture("Click", "interface~action~InterfacePartyEquipmentOffensive:unequip:0", newElement);
				renderModule.addElement("weapon_container", newElement);
				newElement = new InterfaceElement(coreModule, id, "Center", "Center", 250, 55, 60, 4, 1.1f, "Unequip", coreModule.renderModule.FONT_IMPACT);
				renderModule.addElement("weapon_unequip", newElement);
			}
			
			//item icon
			currItem.generateIcon(coreModule,""+currItem.imageID,id, "weapon_container", -306, -37);
			
			//type icons
			newElement = new InterfaceElement(coreModule, "type1"+currItem.imageID, id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 720, 28, 53, -104, 1.0f, 1.0f, "battle/abilities/types.png");
			newElement.generateFrames(80, 28, 0, 0, 9, false);
			renderModule.addElement("weapon_container", newElement);
			newElement = new InterfaceElement(coreModule, "type2"+currItem.imageID, id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 720, 28, 143, -104, 1.0f, 1.0f, "battle/abilities/types.png");
			newElement.generateFrames(80, 28, 0, 0, 9, false);
			renderModule.addElement("weapon_container", newElement);
			
			//damage base
			newElement = new InterfaceElement(coreModule, id, "Center", "Center", 700, 55, 110, -97, 0.85f, ""+currItem.wepAbility.getDamageMultiplier()+"x Dmg", coreModule.renderModule.FONT_IMPACT);
			renderModule.addElement("weapon_container", newElement);
			
			//stamina
			newElement = new InterfaceElement(coreModule, id, "Center", "Center", 700, 55, 242, -97, 0.85f, ""+currItem.wepAbility.getStaminaCost(currUnit)+" Stam", coreModule.renderModule.FONT_IMPACT);
			renderModule.addElement("weapon_container", newElement);
		}
		else
		{
			//create a page for the weapon
			newElement = new InterfaceElement(coreModule, "weapon_container", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 546, 142, 85, 311);
			renderModule.addElement("InterfaceContainer", newElement);	

			newElement = new InterfaceElement(coreModule, id, "Center", "Center", 700, 55, 120, -8, 1.0f, "No Weapon Equipped", coreModule.renderModule.FONT_IMPACT);
			renderModule.addElement("weapon_container", newElement);
		}
		
		//show dem spells
		for(int i=0; i<3; i++)
		{
			if(currUnit.spellSlots[i] != null)
			{					
				Spell currItem = currUnit.spellSlots[i];
				
				//create a page for the weapon
				newElement = new InterfaceElement(coreModule, "spell_container"+i, id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 546, 142, 85, 311-192*(i+1));
				renderModule.addElement("InterfaceContainer", newElement);	

				newElement = new InterfaceElement(coreModule, id, "Center", "Center", 700, 55, 120, -8, 1.0f, ""+currItem.name, coreModule.renderModule.FONT_IMPACT);
				renderModule.addElement("spell_container"+i, newElement);
				
				//item class
				newElement = new InterfaceElement(coreModule, id, "Center", "Center", 200, 55, -230, -100, 0.75f, "Spell", coreModule.renderModule.FONT_IMPACT);
				renderModule.addElement("spell_container"+i, newElement);
				
				//spell attack type
				newElement = new InterfaceElement(coreModule, id, "Center", "Center", 700, 55, 120, -54, 1.0f, ""+currItem.spellAbility.getAttackType(), coreModule.renderModule.FONT_IMPACT);
				renderModule.addElement("spell_container"+i, newElement);
				
				//spell rank
				newElement = new InterfaceElement(coreModule, id, "Center", "Center", 700, 55, 340, -54, 1.0f, "Rank: "+currItem.getRank(), coreModule.renderModule.FONT_IMPACT);
				renderModule.addElement("spell_container"+i, newElement);

				//unequip
				if(gameModule.playerInventory.getContents().size() < gameModule.playerInventory.getCapacity() && gameModule.partyData.getMainCharacter() == currUnit)
				{					
					newElement = new InterfaceElement(coreModule, "spell_unequip"+i, id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 546, 43, -83, -154);
					new Gesture("Click", "interface~action~InterfacePartyEquipmentOffensive:unequip:"+(i+1), newElement);
					renderModule.addElement("spell_container"+i, newElement);
					newElement = new InterfaceElement(coreModule, id, "Center", "Center", 250, 55, 60, 4, 1.1f, "Unequip", coreModule.renderModule.FONT_IMPACT);
					renderModule.addElement("spell_unequip"+i, newElement);
				}
				
				//item icon
				currItem.generateIcon(coreModule,""+currItem.imageID,id, "spell_container"+i, -306, -37);
				
				//type icons
				newElement = new InterfaceElement(coreModule, "type1"+currItem.imageID, id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 720, 28, 53, -104, 1.0f, 1.0f, "battle/abilities/types.png");
				newElement.generateFrames(80, 28, 0, currItem.spellAbility.type1, 9, false);
				renderModule.addElement("spell_container"+i, newElement);
				newElement = new InterfaceElement(coreModule, "type2"+currItem.imageID, id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 720, 28, 143, -104, 1.0f, 1.0f, "battle/abilities/types.png");
				newElement.generateFrames(80, 28, 0, currItem.spellAbility.type2, 9, false);
				renderModule.addElement("spell_container"+i, newElement);
				
				//damage base
				newElement = new InterfaceElement(coreModule, id, "Center", "Center", 700, 55, 110, -97, 0.85f, ""+currItem.spellAbility.getDamageMultiplier()+"x Dmg", coreModule.renderModule.FONT_IMPACT);
				renderModule.addElement("spell_container"+i, newElement);
				
				//stamina
				newElement = new InterfaceElement(coreModule, id, "Center", "Center", 700, 55, 242, -97, 0.85f, ""+currItem.spellAbility.getStaminaCost(currUnit)+" Stam", coreModule.renderModule.FONT_IMPACT);
				renderModule.addElement("spell_container"+i, newElement);
			}
			else
			{
				//create a page for the weapon
				newElement = new InterfaceElement(coreModule, "spell_container"+i, id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 546, 142, 85, 311-192*(i+1));
				renderModule.addElement("InterfaceContainer", newElement);	

				newElement = new InterfaceElement(coreModule, id, "Center", "Center", 700, 55, 120, -8, 1.0f, "No Spell Equipped", coreModule.renderModule.FONT_IMPACT);
				renderModule.addElement("spell_container"+i, newElement);
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
					Weapon w = p.weaponSlot;
					
					if(w!=null)
					{
						p.unequipWeapon();
						gameModule.playerInventory.moveItem(w);
					}
				}
				else if(index < p.spellSlotCount+1)
				{
					//unequip the weapon if we have room
					Spell s = p.spellSlots[index-1];
					
					if(s!=null)
					{
						p.unequipSpell(index-1);
						gameModule.playerInventory.moveItem(s);
					}
				}
				
				gameModule.activateInterface("InterfacePartyEquipmentOffensive");
			}
		}
	}
}

