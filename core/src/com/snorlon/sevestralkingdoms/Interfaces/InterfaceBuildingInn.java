package com.snorlon.sevestralkingdoms.Interfaces;

import java.util.ArrayList;

import com.snorlon.sevestralkingdoms.Buildings.BuildingBase;
import com.snorlon.sevestralkingdoms.Buildings.Inn;
import com.snorlon.sevestralkingdoms.GameTools.Core;
import com.snorlon.sevestralkingdoms.GameTools.Gesture;
import com.snorlon.sevestralkingdoms.GameTools.InterfaceElement;
import com.snorlon.sevestralkingdoms.NPC.Unit;
import com.snorlon.sevestralkingdoms.Places.Town;

public class InterfaceBuildingInn extends GenericInterface
{
	int selectedIndex = 0;
	
	int maxIndex = 0;
	
	boolean reset = true;

	public InterfaceBuildingInn(Core newCore)
	{
		super(newCore);
		// TODO Auto-generated constructor stub
		
		id = "InterfaceBuildingInn";
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
		newElement = new InterfaceElement(coreModule, "background_bg", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 718, 1280, -24, -50, 1.0f, 1.0f, "interfaces/building_inn/background.png");
		renderModule.addElement("bg", newElement);
		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 700, 70, 230, 425, 2.0f, "Inn: Hiring", coreModule.renderModule.FONT_IMPACT);
		renderModule.addElement("bg", newElement);
		
		newElement = new InterfaceElement(coreModule, "BuildingSprite", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 192, 192, -233, 470, 1.0f, 1.0f, "map/buildings/inn.png");
		coreModule.renderModule.addElement("bg", newElement);
		
		//our gold
		if(gameModule.partyData.getMainCharacter() != null)
		{
			newElement = new InterfaceElement(coreModule, id, "Center", "Center", 700, 70, 170, -295, 1.4f, ""+gameModule.partyData.getMainCharacter().getMoney(), coreModule.renderModule.FONT_IMPACT);
			renderModule.addElement("InterfaceContainer", newElement);
		}
		
		newElement = new InterfaceElement(coreModule, "money", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 40, 40, -233, -299, 1.0f, 1.0f, "interfaces/building_shop/money.png");
		coreModule.renderModule.addElement("InterfaceContainer", newElement);
		
		//Inventory BUTTON
		newElement = new InterfaceElement(coreModule, "button_container_inventory", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 268, 190, -142, -341);
		new Gesture("Click", "change scene~Inventory", newElement);//clicking should enter party
		renderModule.addElement("InterfaceContainer", newElement);

		//render the selected unit
		if(gameModule.partyData.getMainCharacter().currentLocation!=null)
		{
			if(gameModule.partyData.getMainCharacter().currentLocation.getMap()!=null)
			{
				if(gameModule.partyData.getMainCharacter().currentLocation.getMap() instanceof Town)
				{
					Town currentTown = (Town) (gameModule.partyData.getMainCharacter().currentLocation.getMap());
					
					BuildingBase b = currentTown.getBuilding("Inn");
					
					if(b!=null && b instanceof Inn)
					{
						Inn i = (Inn) b;
						
						ArrayList<Unit> units = i.getHirable();
						
						if(reset && units.size() > 0)
							selectedIndex = coreModule.random.nextInt(units.size());
						
						maxIndex = units.size() - 1;
						
						if(selectedIndex < units.size() && selectedIndex >= 0)
						{
							//display the selected unit
							Unit u = units.get(selectedIndex);
							
							newElement = new InterfaceElement(coreModule, id, "Center", "Center", 650, 70, 100, 227, 1.8f, u.name, coreModule.renderModule.FONT_IMPACT);
							renderModule.addElement("InterfaceContainer", newElement);
							
							newElement = new InterfaceElement(coreModule, id, "Center", "Center", 350, 70, 314, 234, 1.4f, "Lv"+u.level, coreModule.renderModule.FONT_IMPACT);
							renderModule.addElement("InterfaceContainer", newElement);
							
							newElement = new InterfaceElement(coreModule, id, "Center", "Center", 700, 70, 170, -208, 1.4f, ""+((int) u.threatLevel()), coreModule.renderModule.FONT_IMPACT);
							renderModule.addElement("InterfaceContainer", newElement);
							
							u.generateImage(coreModule, renderModule, "InterfaceContainer", id, 2.0f, -165, -50);
							
							//stats
							int ratings[] = {0,0,0,0,0,0,0,0};
							double rawstats[] = {u.Strength_growth, u.Toughness_growth,u.Agility_growth,u.Dexterity_growth,u.Intelligence_growth,u.Willpower_growth,u.Charisma_growth,u.Luck_growth};
							
							for(int j=0; j<8; j++)
							{
								if(rawstats[j]>1.2f)
									ratings[j]++;
								if(rawstats[j]>1.9f)
									ratings[j]++;
							}
							
							
							int x = 0;
							int y = 0;
							
							for(int j=0; j<8; j++)
							{
								newElement = new InterfaceElement(coreModule, "star"+i, id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 126, 42, 124+101*x, -130-55*y, 1.0f, 1.0f, "interfaces/building_inn/star.png");
								newElement.generateFrames(42, 42, 0, ratings[j], 2, false);
								renderModule.addElement("InterfaceContainer", newElement);
								
								x++;
								
								if(x>1)
								{
									x = 0;
									y++;
								}
							}
							
							int value = (int) (u.threatLevel()*5);
							
							newElement = new InterfaceElement(coreModule, "hire_button", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 546, 83, 0, -396);
							new Gesture("Click", "interface~action~InterfaceBuildingInn:Hire", newElement);
							renderModule.addElement("InterfaceContainer", newElement);	

							newElement = new InterfaceElement(coreModule, id, "Center", "Center", 546, 70, 130, 0, 1.5f, "Hire - ", coreModule.renderModule.FONT_IMPACT);
							renderModule.addElement("hire_button", newElement);
							
							newElement = new InterfaceElement(coreModule, id, "Center", "Center", 546, 70, 320, 0, 1.5f, ""+value, coreModule.renderModule.FONT_IMPACT);
							renderModule.addElement("hire_button", newElement);
							
							newElement = new InterfaceElement(coreModule, "money", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 40, 40, 20, -395, 1.0f, 1.0f, "interfaces/building_shop/money.png");
							coreModule.renderModule.addElement("InterfaceContainer", newElement);
						}
					}
				}
			}
		}
		
		newElement = new InterfaceElement(coreModule, "next_button", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 114, 115, 220, 360);
		new Gesture("Click", "interface~action~InterfaceBuildingInn:Next", newElement);
		renderModule.addElement("InterfaceContainer", newElement);	
		
		newElement = new InterfaceElement(coreModule, "prior_button", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 114, 115, -220, 360);
		new Gesture("Click", "interface~action~InterfaceBuildingInn:Prior", newElement);
		renderModule.addElement("InterfaceContainer", newElement);	
		
		//return text
		newElement = new InterfaceElement(coreModule, "return_button", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 560, 60, -24, -554);
		new Gesture("Click", "interface~open~InterfaceTownOverview", newElement);
		renderModule.addElement("bg", newElement);	
		
		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 546, 70, 140, 10, 1.2f, "Return to Town", coreModule.renderModule.FONT_IMPACT);
		renderModule.addElement("return_button", newElement);
	}
	
	//given a string of input, it will perform something upon the interface
	public void processInput(String inputString, String additionalInput)
	{
		if(inputString.equals("Hire"))
		{
			if(gameModule.partyData.getMainCharacter().currentLocation!=null)
			{
				if(gameModule.partyData.getMainCharacter().currentLocation.getMap()!=null)
				{
					if(gameModule.partyData.getMainCharacter().currentLocation.getMap() instanceof Town)
					{
						Town currentTown = (Town) (gameModule.partyData.getMainCharacter().currentLocation.getMap());
						
						BuildingBase b = currentTown.getBuilding("Inn");
						
						if(b!=null && b instanceof Inn)
						{
							Inn i = (Inn) b;
							
							ArrayList<Unit> units = i.getHirable();
							
							if(reset && units.size() > 0)
								selectedIndex = coreModule.random.nextInt(units.size());
							
							if(selectedIndex < units.size() && selectedIndex >= 0)
							{								
								//display the selected unit
								Unit u = units.get(selectedIndex);
								
								//check that we have enough money to buy their help
								if(gameModule.partyData.getMainCharacter().getMoney() >= u.threatLevel()*5)
								{
									gameModule.partyData.getMainCharacter().takeMoney((int) (u.threatLevel() * 5));

									gameModule.partyData.rollPosition(u);
									
									//move them to our party
									gameModule.partyData.addUnit(u);
									
									//remove them from the inn
									i.getHirable().remove(u);
									
									maxIndex = units.size() - 1;
									selectedIndex = Math.min(maxIndex, selectedIndex);
									
									gameModule.saveSave();//save the new unit data
								}
							}
						}
					}
				}
			}
			
			gameModule.activateInterface("InterfaceBuildingInn");
		}
		else if(inputString.equals("Next"))
		{
			if(selectedIndex < maxIndex)
				selectedIndex++;
			else
				selectedIndex = 0;
			
			reset = false;
			gameModule.activateInterface("InterfaceBuildingInn");
			reset = true;
		}
		else if(inputString.equals("Prior"))
		{
			if(selectedIndex > 0)
				selectedIndex--;
			else
				selectedIndex = maxIndex;
			
			reset = false;
			gameModule.activateInterface("InterfaceBuildingInn");
			reset = true;
		}
	}

}
