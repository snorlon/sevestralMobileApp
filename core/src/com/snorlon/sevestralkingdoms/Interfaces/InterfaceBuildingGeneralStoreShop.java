package com.snorlon.sevestralkingdoms.Interfaces;

import com.snorlon.sevestralkingdoms.Buildings.BuildingBase;
import com.snorlon.sevestralkingdoms.Buildings.GeneralStore;
import com.snorlon.sevestralkingdoms.GameComponents.Common;
import com.snorlon.sevestralkingdoms.GameTools.Core;
import com.snorlon.sevestralkingdoms.GameTools.Gesture;
import com.snorlon.sevestralkingdoms.GameTools.InterfaceElement;
import com.snorlon.sevestralkingdoms.Items.Armor;
import com.snorlon.sevestralkingdoms.Items.Item;
import com.snorlon.sevestralkingdoms.Items.Shop;
import com.snorlon.sevestralkingdoms.Items.Spell;
import com.snorlon.sevestralkingdoms.Items.Weapon;
import com.snorlon.sevestralkingdoms.Places.Town;

public class InterfaceBuildingGeneralStoreShop extends GenericInterface
{
	int selectedIndex = -1;
	
	boolean saveIndex = false;
	
	String currentShop = "General Store";

	public InterfaceBuildingGeneralStoreShop(Core newCore)
	{
		super(newCore);
		// TODO Auto-generated constructor stub
		
		id = "InterfaceBuildingGeneralStoreShop";
	}
	
	//used to pull up the particular screen in question
	public void activate()
	{
		if(!saveIndex)
			selectedIndex = -1;
		
		//create a gui container
		newElement = new InterfaceElement(coreModule, "InterfaceContainer", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 720, 1180, 0, 0);
		renderModule.addElement("CenterNode", newElement);
		
		//bg
		newElement = new InterfaceElement(coreModule, "bg", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 718, 1280, 24, 51, 1.0f, 1.0f, "menu/menubg.png");
		renderModule.addElement("InterfaceContainer", newElement);
		newElement = new InterfaceElement(coreModule, "background_bg", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 718, 1280, -24, -50, 1.0f, 1.0f, "interfaces/building_shop/background.png");
		renderModule.addElement("bg", newElement);
		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 700, 70, 210, 425, 2.0f, currentShop, coreModule.renderModule.FONT_IMPACT);
		renderModule.addElement("bg", newElement);
		
		newElement = new InterfaceElement(coreModule, "BuildingSprite", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 192, 192, -233, 470, 1.0f, 1.0f, "map/buildings/"+currentShop.toLowerCase()+".png");
		coreModule.renderModule.addElement("bg", newElement);
		
		//our gold
		if(gameModule.partyData.getMainCharacter() != null)
		{
			int cost = 0;
			Town currentTown = (Town) (gameModule.partyData.getMainCharacter().currentLocation.getMap());
			
			BuildingBase shopBuilding = currentTown.getBuilding(currentShop);

			if(shopBuilding!=null)
			{
				Shop s = shopBuilding.getShop();
				if(s.getCost(selectedIndex)>0)
					cost = s.getCost(selectedIndex);
			}
			
			if(cost > 0)
			{
				newElement = new InterfaceElement(coreModule, id, "Center", "Center", 700, 70, 170, 385, 1.4f, ""+(gameModule.partyData.getMainCharacter().getMoney()-cost), coreModule.renderModule.FONT_IMPACT);
				newElement.setColor(255, 0, 0, 1.0f);
				renderModule.addElement("InterfaceContainer", newElement);
			}
			else
			{
				newElement = new InterfaceElement(coreModule, id, "Center", "Center", 700, 70, 170, 385, 1.4f, ""+gameModule.partyData.getMainCharacter().getMoney(), coreModule.renderModule.FONT_IMPACT);
				renderModule.addElement("InterfaceContainer", newElement);
			}
		}
		
		newElement = new InterfaceElement(coreModule, "money", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 40, 40, -233, 381, 1.0f, 1.0f, "interfaces/building_shop/money.png");
		coreModule.renderModule.addElement("InterfaceContainer", newElement);
		
		//Inventory BUTTON
		newElement = new InterfaceElement(coreModule, "button_container_inventory", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 268, 190, -142, -341);
		new Gesture("Click", "change scene~Inventory", newElement);//clicking should enter party
		renderModule.addElement("InterfaceContainer", newElement);

		//render the items!
		if(gameModule.partyData.getMainCharacter().currentLocation!=null)
		{
			if(gameModule.partyData.getMainCharacter().currentLocation.getMap()!=null)
			{
				if(gameModule.partyData.getMainCharacter().currentLocation.getMap() instanceof Town)
				{
					Town currentTown = (Town) (gameModule.partyData.getMainCharacter().currentLocation.getMap());
					
					BuildingBase shopBuilding = currentTown.getBuilding(currentShop);

					if(shopBuilding!=null)
					{
						Shop s = shopBuilding.getShop();
						Item selectedItem = s.getItem(selectedIndex);
						
						//list it's wares!
						for(int i=0; i<25; i++)
						{
							if(s.getItem(i)!= null)
							{
								Item tempItem = s.getItem(i);

								newElement = tempItem.generateIcon(coreModule,"Item"+i,id, "InterfaceContainer", -211 + 106*(i%5), 63 - 108*(i/5));
								new Gesture("Click", "interface~action~InterfaceBuildingGeneralStoreShop:select:"+i, newElement);
								
								newElement = new InterfaceElement(coreModule, "ItemTag"+i, id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 120, 96, 0, 50, 1.0f, 1.0f, "interfaces/building_shop/pricetag.png");
								coreModule.renderModule.addElement("Item"+i, newElement);
								
								newElement = new InterfaceElement(coreModule, id, "Center", "Center", 120, 96, 40, 5, 0.8f, ""+currentTown.getTaxedAmount(tempItem.base_value), coreModule.renderModule.FONT_IMPACT);
								renderModule.addElement("ItemTag"+i, newElement);
								
								newElement = new InterfaceElement(coreModule, "ItemTagG"+i, id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 40, 40, -32, -24, 0.5f, 0.5f, "interfaces/building_shop/money.png");
								renderModule.addElement("ItemTag"+i, newElement);
								
								
								if(selectedItem==tempItem)
								{
									newElement = new InterfaceElement(coreModule, "ItemSelected2", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 96, 96, 0, 0, 1.0f, 1.0f, "interfaces/building_shop/selection.png");
									renderModule.addElement("Item"+i, newElement);
								}
							}
						}	
						
						//display options for the selected item
						if(selectedItem!=null)
						{
							//selected item data
							newElement = selectedItem.generateIcon(coreModule,"ItemSelected", id, "InterfaceContainer", -200, 286);
							
							if(selectedItem.type == Item.ITEM_TYPE_FOOD || selectedItem.type == Item.ITEM_TYPE_POTION)
							{
								newElement = new InterfaceElement(coreModule, id, "Center", "Center", 450, 120, 130, 306, 0.8f, "Effect: \n  "+Item.itemEffectDesc(selectedItem.getEffect()), coreModule.renderModule.FONT_IMPACT);
								renderModule.addElement("InterfaceContainer", newElement);
							}
							else if(selectedItem.type == Item.ITEM_TYPE_ARMOR)
							{
								Armor a = ((Armor) selectedItem);
								
								newElement = new InterfaceElement(coreModule, id, "Center", "Center", 450, 120, 130, 326, 0.8f, "R"+a.getRank()+" "+a.getSlot(), coreModule.renderModule.FONT_IMPACT);
								renderModule.addElement("InterfaceContainer", newElement);
								
								newElement = new InterfaceElement(coreModule, id, "Center", "Center", 450, 120, 220, 287, 1f, ""+((int) (a.getStatValue()*100))+"%", coreModule.renderModule.FONT_IMPACT);
								renderModule.addElement("InterfaceContainer", newElement);
								
								newElement = new InterfaceElement(coreModule, "AttackElement", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 720, 28, -60, 250, 1.0f, 1.0f, "battle/abilities/types.png");
								newElement.generateFrames(80, 28, 0, a.getType(), 2, false);
								coreModule.renderModule.addElement("InterfaceContainer", newElement);
							}
							else if(selectedItem.type == Item.ITEM_TYPE_WEAPON || selectedItem.type == Item.ITEM_TYPE_SPELL)
							{
								int rank = 0;
								int type1 = -1;
								int type2 = -1;
								String wepType = "Error";
								int combatType = Common.MAGICAL;
								
								if(selectedItem.type == Item.ITEM_TYPE_SPELL)
								{
									Spell a = ((Spell) selectedItem);
									rank = a.getRank();
									type1 = a.spellAbility.type1;
									type2 = a.spellAbility.type2;
									wepType = "Spell";
									combatType = a.spellAbility.attackType;
								}
								else if(selectedItem.type == Item.ITEM_TYPE_WEAPON)
								{
									Weapon a = ((Weapon) selectedItem);
									rank = a.getRank();
									type1 = a.wepAbility.type1;
									wepType = a.getWeaponType();
									combatType = a.wepAbility.attackType;
								}
								
								newElement = new InterfaceElement(coreModule, id, "Center", "Center", 450, 120, 130, 326, 0.8f, "R"+rank+" "+wepType, coreModule.renderModule.FONT_IMPACT);
								renderModule.addElement("InterfaceContainer", newElement);
								
								newElement = new InterfaceElement(coreModule, "AttackElement", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 720, 28, -60, 250, 1.0f, 1.0f, "battle/abilities/types.png");
								newElement.generateFrames(80, 28, 0, type1, 2, false);
								coreModule.renderModule.addElement("InterfaceContainer", newElement);
								
								if(type2!=-1)
								{
									newElement = new InterfaceElement(coreModule, "AttackElement2", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 720, 28, 40, 250, 1.0f, 1.0f, "battle/abilities/types.png");
									newElement.generateFrames(80, 28, 0, type2, 2, false);
									coreModule.renderModule.addElement("InterfaceContainer", newElement);
								}
								
								newElement = new InterfaceElement(coreModule, "AttackType", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 180, 90, -13, 202, 1.0f, 1.0f, "interfaces/battle/move attack type.png");
								newElement.generateFrames(180, 30, 0, combatType, 2, false);
								coreModule.renderModule.addElement("InterfaceContainer", newElement);
							}
							
							float scale = 1.0f;
							
							if(selectedItem.name.length() > 12)
								scale = 0.8f;
							
							newElement = new InterfaceElement(coreModule, id, "Center", "Center", 450, 120, 130, 353, scale, ""+selectedItem.name, coreModule.renderModule.FONT_IMPACT);
							renderModule.addElement("InterfaceContainer", newElement);

							newElement = new InterfaceElement(coreModule, "ItemTagS", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 120, 96, 0, 50, 1.0f, 1.0f, "interfaces/building_shop/pricetag.png");
							coreModule.renderModule.addElement("ItemSelected", newElement);
							
							newElement = new InterfaceElement(coreModule, id, "Center", "Center", 120, 96, 40, 5, 0.8f, ""+currentTown.getTaxedAmount(selectedItem.base_value), coreModule.renderModule.FONT_IMPACT);
							renderModule.addElement("ItemTagS", newElement);
							
							newElement = new InterfaceElement(coreModule, "ItemTagGS", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 40, 40, -32, -24, 0.5f, 0.5f, "interfaces/building_shop/money.png");
							renderModule.addElement("ItemTagS", newElement);

							if(gameModule.partyData.getMainCharacter() != null)
							{
								if(gameModule.playerInventory.canGiveItem(selectedItem) && gameModule.partyData.getMainCharacter().canGiveMoney(currentTown.getTaxedAmount(selectedItem.base_value)))
								{
									newElement = new InterfaceElement(coreModule, "EquipItem", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 150, 215, 164, 250);
									new Gesture("Click", "interface~action~InterfaceBuildingGeneralStoreShop:buy", newElement);
									renderModule.addElement("bg", newElement);
									newElement = new InterfaceElement(coreModule, id, "Center", "Center", 173, 70, 48, 2, 1.5f, "Buy", coreModule.renderModule.FONT_IMPACT);
									renderModule.addElement("EquipItem", newElement);	
								}
							}
						}

					}
				}
			}
		}
		
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
		if(inputString.equals("select"))
		{
			int priorIndex = selectedIndex;
			selectedIndex = Integer.parseInt(additionalInput);
			
			if(priorIndex != selectedIndex)
			{
				saveIndex = true;
			}
			else
				selectedIndex = -1;
			
			gameModule.activateInterface(id);
			saveIndex = false;
		}
		else if(inputString.equals("buy"))
		{
			//initiate currency removal and item transfer
			if(gameModule.partyData.getMainCharacter().currentLocation!=null)
			{
				if(gameModule.partyData.getMainCharacter().currentLocation.getMap()!=null)
				{
					if(gameModule.partyData.getMainCharacter().currentLocation.getMap() instanceof Town)
					{
						Town currentTown = (Town) (gameModule.partyData.getMainCharacter().currentLocation.getMap());
						
						BuildingBase shopBuilding = currentTown.getBuilding(currentShop);

						if(shopBuilding!=null)
						{
							Shop s = shopBuilding.getShop();
							Item selectedItem = s.getItem(selectedIndex);
							
							if(selectedItem!=null)
							{
								if(gameModule.partyData.getMainCharacter() != null)
								{
									int amount = currentTown.getTaxedAmount(selectedItem.base_value);
									gameModule.partyData.getMainCharacter().takeMoney(amount);
									
									currentTown.giveMoney(amount - selectedItem.base_value);
									s.takeItem(selectedIndex, gameModule.playerInventory);
								}

								gameModule.activateInterface(id);
							}
							
							//force the game to save
							gameModule.saveSave();
						}
					}
				}
			}
		}
		else if(inputString.equals("General Store"))
		{
			currentShop = "General Store";
		}
		else if(inputString.equals("Blacksmith"))
		{
			currentShop = "Blacksmith";
		}
		else if(inputString.equals("Spellshop"))
		{
			currentShop = "Spellshop";
		}
		else if(inputString.equals("Market"))
		{
			currentShop = "Market";
		}
	}

}
