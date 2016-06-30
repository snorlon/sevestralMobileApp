package com.snorlon.sevestralkingdoms.Interfaces;

import com.snorlon.sevestralkingdoms.GameComponents.Diety;
import com.snorlon.sevestralkingdoms.GameTools.Core;
import com.snorlon.sevestralkingdoms.GameTools.Gesture;
import com.snorlon.sevestralkingdoms.GameTools.InterfaceElement;
import com.snorlon.sevestralkingdoms.NPC.Unit;
import com.snorlon.sevestralkingdoms.Places.Location;

public class InterfaceBattleResults extends GenericInterface
{	
	public static final int DEFAULT = 1;
	public static final int WILD_BATTLE = 1;
	
	boolean win = false;

	int expGained = 0;
	int goldGained = 0;
	
	int matchType = 0;
	
	static final int moraleGain = 1;
	static final int moraleLoss = 2;
	
	public InterfaceBattleResults(Core newCore)
	{
		super(newCore);
		
		id = "InterfaceBattleResults";
	}
	
	//used to pull up the particular screen in question
	public void activate()
	{
		//stop all music
		coreModule.soundModule.disableMusic();
		
		
		//create a gui container
		newElement = new InterfaceElement(coreModule, "InterfaceContainer", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 720, 1180, 0, 0);
		renderModule.addElement("CenterNode", newElement);
		
		//bg
		newElement = new InterfaceElement(coreModule, "bg", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 718, 1280, 24, 51, 1.0f, 1.0f, "menu/menubg.png");
		renderModule.addElement("InterfaceContainer", newElement);
		
		newElement = new InterfaceElement(coreModule, "interface_layer", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 718, 1280, 24, 51);
		renderModule.addElement("InterfaceContainer", newElement);
		
		newElement = new InterfaceElement(coreModule, "overview_background_bg", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 718, 1280, -24, -50, 1.0f, 1.0f, "interfaces/battle/results background.png");
		renderModule.addElement("bg", newElement);
		
		if(win)
		{
			newElement = new InterfaceElement(coreModule, id, "Center", "Center", 700, 70, 230, 425, 2.0f, "Victory", coreModule.renderModule.FONT_IMPACT);
		}
		else
		{
			newElement = new InterfaceElement(coreModule, id, "Center", "Center", 700, 70, 240, 425, 2.0f, "Defeat", coreModule.renderModule.FONT_IMPACT);
		}
		renderModule.addElement("bg", newElement);

		//make sure our units are able to fight!
		for(Unit e : gameModule.partyData.party_members)
		{
			//make sure they have at least one health
			if(e != null)
			{
				e.heal(100);
			}
		}

		for(int i=3; i>0; i--)
		{
			for(int j=1; j<=5; j++)
			{
				for(Unit element : gameModule.partyData.party_members)
				{
					if(element != null && element.x == j && element.y == i)
					{
						int drawY = (int) ((element.y * 91) + -230);
						int drawX = -328 + (element.x * 103);
						
						element.generateImage(coreModule, renderModule, "bg", ""+i+""+j,2, drawX , drawY, false);
						
						//give exp gain
						element.exp += expGained;
						element.tick(0, coreModule);
						
						System.out.println(element.name+" gained "+expGained+" exp!");
						
						//give them a morale bonus if we won, or a morale nerf if we lost
						if(win)
							element.addMorale(moraleGain);
						else
							element.addMorale(-moraleLoss);
					}
				}
			}
		}
		
		if(gameModule.partyData.getMainCharacter().currentLocation!=null)
		{
			String iName = gameModule.partyData.getMainCharacter().currentLocation.name;
			
			String offset = "";
			
			while(offset.length()*2 + iName.length() < 15)
			{
				offset+=" ";
			}
			
			newElement = new InterfaceElement(coreModule, id, "Center", "Center", 600, 80, 86, 340, 1.6f, offset+gameModule.partyData.getMainCharacter().currentLocation.name, coreModule.renderModule.FONT_IMPACT);
			renderModule.addElement("interface_layer", newElement);
		}

		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 600, 80, 70, 260, 1.0f, "Gained "+expGained+" experience.", coreModule.renderModule.FONT_IMPACT);
		renderModule.addElement("interface_layer", newElement);

		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 600, 80, 70, 188, 1.0f, "Found 0 items in battle.", coreModule.renderModule.FONT_IMPACT);
		renderModule.addElement("interface_layer", newElement);
		

		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 600, 80, 153, 100, 1.6f, "Your Party", coreModule.renderModule.FONT_IMPACT);
		renderModule.addElement("interface_layer", newElement);

		switch(this.matchType)
		{
			case WILD_BATTLE:
				newElement = new InterfaceElement(coreModule, id, "Center", "Center", 600, 80, 153, -360, 1.6f, "Wild Battle", coreModule.renderModule.FONT_IMPACT);
				renderModule.addElement("interface_layer", newElement);
				
				if(win)
				{
					newElement = new InterfaceElement(coreModule, id, "Center", "Center", 600, 80, 70, -410, 1.0f, "+"+moraleGain+" Morale for winning the battle.", coreModule.renderModule.FONT_IMPACT);
					renderModule.addElement("interface_layer", newElement);
					
					//if a diety was here, disable them
					Location l = gameModule.partyData.getMainCharacter().currentLocation;
					if(l!=null)
					{
						Diety d = gameModule.dietyModule.getDiety(l.getX(), l.getY(), l.planet);
						if(d!=null)
							d.disable();
					}
				}
				else
				{
					newElement = new InterfaceElement(coreModule, id, "Center", "Center", 600, 80, 53, -410, 1.0f, "-"+moraleLoss+" Morale for losing the battle.", coreModule.renderModule.FONT_IMPACT);
					renderModule.addElement("interface_layer", newElement);
				}
				
				break;
		}
		
		//dropped gold
		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 700, 70, 159, -223, 1.4f, ""+goldGained, coreModule.renderModule.FONT_IMPACT);
		renderModule.addElement("InterfaceContainer", newElement);
		
		newElement = new InterfaceElement(coreModule, "money", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 40, 40, -220, -225, 1.0f, 1.0f, "interfaces/building_shop/money.png");
		coreModule.renderModule.addElement("InterfaceContainer", newElement);
		
		//grant the gold to EVERY party unit
		for(Unit u : gameModule.partyData.getPartyMembers())
		{
			if(u!=null)
			{
				u.giveMoney(goldGained);
			}
		}

		expGained = 0;
		goldGained = 0;
		
		if(win)
		{
			//guaranteed drop difficulty if you won
			if(gameModule.partyData.getMainCharacter().currentLocation!=null)
			{
				gameModule.partyData.getMainCharacter().currentLocation.dropDifficulty();
			}
		}
		
		gameModule.stepTurn();//force the game world to update!
		
		//save the same!
		gameModule.saveSave();
		
		//return text
		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 546, 70, 80, -548, 1.2f, "Return to Main Menu", coreModule.renderModule.FONT_IMPACT);
		new Gesture("Click", "interface~open~InterfaceMenuOverview", newElement);
		renderModule.addElement("bg", newElement);	
	}
	
	public void Tick(float dt)
	{
		//update all units owned by the player
		for(Unit u : coreModule.gameModule.partyData.party_members)
		{
			if(u!=null)
			{				
				u.tick(dt, coreModule);
				
				u.update_render_bars(coreModule);
			}
		}
	}

	
	//given a string of input, it will perform something upon the interface
	public void processInput(String inputString, String additionalInput)
	{
		String[] extraInput = additionalInput.split(":");
		
		if(inputString.equals("Winner"))
		{
			win = true;
			
			setBattleType(extraInput[0]);
			expGained = Integer.parseInt(extraInput[1]);
			goldGained = Integer.parseInt(extraInput[2]);
		}
		else if(inputString.equals("Loser"))
		{
			win = false;

			setBattleType(extraInput[0]);
			expGained = Integer.parseInt(extraInput[1]);
			goldGained = Integer.parseInt(extraInput[2]);
		}
		
	}
	
	public void setBattleType(String btype)
	{
		if(btype.equals("Wild"))
		{
			matchType = WILD_BATTLE;
		}
	}
}
