package com.snorlon.sevestralkingdoms.Interfaces;

import com.snorlon.sevestralkingdoms.Ability.Ability;
import com.snorlon.sevestralkingdoms.GameTools.Core;
import com.snorlon.sevestralkingdoms.GameTools.Gesture;
import com.snorlon.sevestralkingdoms.GameTools.InterfaceElement;
import com.snorlon.sevestralkingdoms.NPC.Unit;

public class InterfacePartyMemberAbilities extends GenericInterface
{

	public InterfacePartyMemberAbilities(Core newCore)
	{
		super(newCore);
		// TODO Auto-generated constructor stub
		
		id = "InterfacePartyMemberAbilities";
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
		
		//reset things
		coreModule.gameModule.partyData.setCurrentAbility(null);
		
		//party bg
		newElement = new InterfaceElement(coreModule, "party_background_bg", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 718, 1280, -24, -50, 1.0f, 1.0f, "menu/party/party details background.png");
		renderModule.addElement("bg", newElement);
		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 700, 70, 210, 446, 1.2f, "Unit Abilities", coreModule.renderModule.FONT_IMPACT);
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
		
		
		//render abilitiesInterfaceElement newElement;
		int x = 1;
		int y = 1;
		
		for(Ability element : currUnit.attacks)
		{
			int drawX = (int) (((x-1)*350))-175;
			int drawY = (int) ((-190*y + 443));
			
			newElement = new InterfaceElement(coreModule, "Abilityc"+x+y, "Battle", InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 200, 144, drawX, drawY);
			renderModule.addElement("InterfaceContainer", newElement);
			
			newElement = new InterfaceElement(coreModule, "Abilityc"+x+y+"b", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 200, 144, 0, 0, 1.0f, 1.0f, "battle/abilities/"+element.iconName);
			renderModule.addElement("Abilityc"+x+y, newElement);
			
			//generate the stat icons
			newElement = new InterfaceElement(coreModule, "Abilityc"+x+y+"o", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 180, 90, 0, 17, 1.0f, 1.0f, "interfaces/battle/move attack type.png");
			newElement.generateFrames(180, 30, 0, element.attackType, 2, false);
			renderModule.addElement("Abilityc"+x+y, newElement);
			
			//generate the type icons
			newElement = new InterfaceElement(coreModule, "Abilityc"+x+y+"st1", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 720, 28, -50, -51, 1.0f, 1.0f, "battle/abilities/types.png");
			newElement.generateFrames(80, 28, 0, element.type1, 8, false);
			renderModule.addElement("Abilityc"+x+y, newElement);
			
			
			newElement = new InterfaceElement(coreModule, "Abilityc"+x+y+"st2", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 720, 28, 50, -51, 1.0f, 1.0f, "battle/abilities/types.png");
			newElement.generateFrames(80, 28, 0, element.type2, 8, false);
			renderModule.addElement("Abilityc"+x+y, newElement);
			
			newElement = new InterfaceElement(coreModule, "Abilityc"+x+y+"t", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 200, 144, 0, 0, 1.0f, 1.0f, "battle/abilities/frame.png");
			renderModule.addElement("Abilityc"+x+y, newElement);
			
			newElement = new InterfaceElement(coreModule, id, "Center", "Center", 300, 70, 65-(77*(x-1)), -80, 0.9f, "Dmg: "+element.getDamageMultiplier(), coreModule.renderModule.FONT_IMPACT);
			renderModule.addElement("Abilityc"+x+y, newElement);
			newElement = new InterfaceElement(coreModule, id, "Center", "Center", 300, 70, 200-(77*(x-1)), -80, 0.9f, "Stm: "+element.getStaminaCost(currUnit)+"%", coreModule.renderModule.FONT_IMPACT);
			renderModule.addElement("Abilityc"+x+y, newElement);
			
			x++;
			if(x>2)
			{
				x=1;
				y++;
			}
		}
		
		//return text
		newElement = new InterfaceElement(coreModule, "return_button", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 560, 60, -24, -554);
		new Gesture("Click", "interface~open~InterfacePartyOverview", newElement);
		renderModule.addElement("bg", newElement);
		
		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 546, 70, 70, 10, 1.2f, "Return to Party Overview", coreModule.renderModule.FONT_IMPACT);
		renderModule.addElement("return_button", newElement);
	}
}
