package com.snorlon.sevestralkingdoms.Interfaces;

import java.util.ArrayList;

import com.snorlon.sevestralkingdoms.GameComponents.Common;
import com.snorlon.sevestralkingdoms.GameTools.Core;
import com.snorlon.sevestralkingdoms.GameTools.Gesture;
import com.snorlon.sevestralkingdoms.GameTools.InterfaceElement;
import com.snorlon.sevestralkingdoms.Items.*;
import com.snorlon.sevestralkingdoms.NPC.Unit;

public class InterfaceInventory extends GenericInterface
{
	
	Item selectedItem = null;
	int currentPage = 1;
	
	final int itemsPerRow = 5;
	final int itemsPerCol = 4;
	final int itemsPerSet = itemsPerRow*itemsPerCol;
	
	int currentFilter = Item.ITEM_TYPE_NONE;
	
	boolean preserve = false;

	public InterfaceInventory(Core newCore)
	{
		super(newCore);
		// TODO Auto-generated constructor stub
		
		id = "InterfaceInventory";
	}
	
	//used to pull up the particular screen in question
	public void activate()
	{
		if(!preserve)
		{
			currentPage = 1;//reset the current page
			selectedItem = null;
		}
		
		int flag = 0;
		
		//create a gui container
		newElement = new InterfaceElement(coreModule, "InterfaceContainer", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 720, 1180, 0, 0);
		renderModule.addElement("CenterNode", newElement);
		
		//bg
		newElement = new InterfaceElement(coreModule, "bg", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 718, 1280, 24, 51, 1.0f, 1.0f, "menu/menubg.png");
		renderModule.addElement("InterfaceContainer", newElement);
		newElement = new InterfaceElement(coreModule, "overview_background_bg", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 718, 1280, -24, -50, 1.0f, 1.0f, "interfaces/inventory/background.png");
		renderModule.addElement("bg", newElement);
		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 700, 70, 130, 425, 2.0f, "Your Inventory", coreModule.renderModule.FONT_IMPACT);
		renderModule.addElement("bg", newElement);
		
		//our gold
		if(gameModule.partyData.getMainCharacter()!=null)
		{
			newElement = new InterfaceElement(coreModule, id, "Center", "Center", 700, 70, 170, 385, 1.4f, ""+gameModule.partyData.getMainCharacter().getMoney(), coreModule.renderModule.FONT_IMPACT);
			renderModule.addElement("InterfaceContainer", newElement);
		}
		
		newElement = new InterfaceElement(coreModule, "money", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 40, 40, -233, 381, 1.0f, 1.0f, "interfaces/building_shop/money.png");
		coreModule.renderModule.addElement("InterfaceContainer", newElement);
		
		//filters
		newElement = new InterfaceElement(coreModule, "PrevF", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center",	140, 52, -218, 92);
		new Gesture("Click", "interface~action~InterfaceInventory:prevFilter", newElement);
		renderModule.addElement("bg", newElement);

		newElement = new InterfaceElement(coreModule, "NextF", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center",	140, 52, 170, 92);
		new Gesture("Click", "interface~action~InterfaceInventory:nextFilter", newElement);
		renderModule.addElement("bg", newElement);
		
		int xOffset = 0;
		int amount = 10 - getFilter().length();
		int xAmt = 8;

		while(amount > 0)
		{
			amount-=1;
			xOffset += xAmt;
		}
		while(amount < 0)
		{
			amount+=1;
			xOffset -= xAmt;
		}
		
		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 600, 70, 195+xOffset, 100, 1.1f, ""+getFilter(), coreModule.renderModule.FONT_IMPACT);
		renderModule.addElement("bg", newElement);

		newElement = new InterfaceElement(coreModule, "Prev", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center",	140, 52, -218, -455);
		new Gesture("Click", "interface~action~InterfaceInventory:Previous", newElement);
		renderModule.addElement("bg", newElement);

		newElement = new InterfaceElement(coreModule, "Next", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center",	140, 52, 170, -455);
		new Gesture("Click", "interface~action~InterfaceInventory:Next", newElement);
		renderModule.addElement("bg", newElement);

		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 600, 70, 185, -449, 1.1f, "Page "+currentPage+" of "+((int) Math.ceil(gameModule.playerInventory.getCapacity() / itemsPerSet)), coreModule.renderModule.FONT_IMPACT);
		renderModule.addElement("bg", newElement);	
		
		//display options for the selected item
		if(selectedItem!=null)
		{
			
			newElement = new InterfaceElement(coreModule, "TossItem", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 150, 103, 164, 196);
			new Gesture("Click", "interface~action~InterfaceInventory:destroy", newElement);
			renderModule.addElement("bg", newElement);
			newElement = new InterfaceElement(coreModule, id, "Center", "Center", 173, 70, 44, 2, 1.5f, "Toss", coreModule.renderModule.FONT_IMPACT);
			renderModule.addElement("TossItem", newElement);	

			newElement = new InterfaceElement(coreModule, "EquipItem", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 150, 103, 164, 309);
			if(selectedItem instanceof Weapon || selectedItem instanceof Armor || selectedItem instanceof Spell)
			{
				new Gesture("Click", "interface~action~InterfaceInventory:equipSelected", newElement);
				renderModule.addElement("bg", newElement);
				newElement = new InterfaceElement(coreModule, id, "Center", "Center", 173, 70, 37, 2, 1.5f, "Equip", coreModule.renderModule.FONT_IMPACT);
				renderModule.addElement("EquipItem", newElement);	
			}
			else if(selectedItem.type == Item.ITEM_TYPE_FOOD || selectedItem.type == Item.ITEM_TYPE_POTION)
			{
				new Gesture("Click", "interface~action~InterfaceInventory:consume", newElement);
				renderModule.addElement("bg", newElement);
				newElement = new InterfaceElement(coreModule, id, "Center", "Center", 173, 70, 26, 2, 1.5f, "Ingest", coreModule.renderModule.FONT_IMPACT);
				renderModule.addElement("EquipItem", newElement);	
			}
			else if(selectedItem.type == Item.ITEM_TYPE_COSTUME)
			{
				//new Gesture("Click", "interface~open~InterfaceMenuOverview", newElement);
				renderModule.addElement("bg", newElement);
				newElement = new InterfaceElement(coreModule, id, "Center", "Center", 173, 70, 37, 2, 1.5f, "Wear", coreModule.renderModule.FONT_IMPACT);
				renderModule.addElement("EquipItem", newElement);	
			}
			else
			{
				renderModule.addElement("bg", newElement);
			}
			

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
		}
		
		//render all of the items
		ArrayList<Item> playerItems = gameModule.playerInventory.getContents();

		int index = 0;
		int index2 = 0;
		int x = 0;
		int y = 0;
		
		for(Item i : playerItems)
		{
			if(i.type == currentFilter || (i.type == Item.ITEM_TYPE_FOOD && currentFilter == Item.ITEM_TYPE_POTION) || currentFilter == Item.ITEM_TYPE_NONE)
			{
				if(index >= (currentPage-1)*itemsPerSet && index < currentPage*itemsPerSet)
				{
					newElement = i.generateIcon(coreModule,"itemb"+index,id, "bg", -237 + 106*x, -20 - 108*y);
					new Gesture("Click", "interface~action~InterfaceInventory:select:"+index2, newElement);
					
					if(selectedItem==i)
					{
						newElement = new InterfaceElement(coreModule, "ItemSelected2", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 96, 96, 0, 0, 1.0f, 1.0f, "interfaces/building_shop/selection.png");
						renderModule.addElement("itemb"+index, newElement);
					}
					
					x++;
					if(x >= itemsPerRow)
					{
						x = 0;
						y++;
					}
				}
				
				index++;
			}
			index2++;
		}
		

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
		if(inputString.equals("Next"))
		{
			if(gameModule.playerInventory.getCapacity() > (currentPage+1)*itemsPerSet)
			{
				currentPage++;
				
				selectedItem = null;
				
				preserve = true;
				gameModule.activateInterface("InterfaceInventory");
				preserve = false;
			}
		}
		else if(inputString.equals("Previous"))
		{
			if(currentPage > 1)
			{
				currentPage--;
				
				selectedItem = null;
				
				preserve = true;
				gameModule.activateInterface("InterfaceInventory");
				preserve = false;
			}
		}
		else if(inputString.equals("destroy"))
		{
			preserve = true;
			gameModule.giveInterfaceInput("InterfaceMenuConfirm","activate","destroy");
		}
		else if(inputString.equals("destroySelected"))
		{
			destroySelected();
		}
		else if(inputString.equals("equipSelected"))
		{
			if(selectedItem!=null)
			{
				Unit currentCharacter = gameModule.partyData.getMainCharacter();
				
				if(selectedItem instanceof Weapon)
				{
					//unequip our current gear (if any)
					Weapon priorWeapon = currentCharacter.weaponSlot;
					
					if(priorWeapon!=null)
						priorWeapon.unequip(currentCharacter);
					
					//equip this gear
					Weapon w = (Weapon) selectedItem;
					
					w.equip(currentCharacter);
					
					//take it out of our bag
					gameModule.playerInventory.getContents().remove(selectedItem);
					
					//put prior gear into bag at the end of the bag
					if(priorWeapon!=null)
						gameModule.playerInventory.moveItem(priorWeapon);
				}
				else if(selectedItem instanceof Spell)
				{
					for(int i=0; i<currentCharacter.spellSlotCount; i++)
					{
						//unequip our current gear (if any)
						Spell priorWeapon = currentCharacter.spellSlots[i];
						
						if(priorWeapon==null)
						{							
							//equip this gear
							Spell w = (Spell) selectedItem;
							
							currentCharacter.equipSpell(w, i);
							
							//take it out of our bag
							gameModule.playerInventory.getContents().remove(selectedItem);
							
							break;
						}
					}
				}
				else if(selectedItem instanceof Armor)
				{
					Armor priorA = null;
					Armor a = (Armor) selectedItem;

					
					//unequip our current gear (if any)
					if(a.armorType == Armor.TYPE_HELMET)
					{
						priorA = currentCharacter.helmetSlot;
						if(priorA!=null)
							currentCharacter.unequipHelmet();
					}
					else if(a.armorType == Armor.TYPE_CHESTPIECE)
					{
						priorA = currentCharacter.chestpieceSlot;
						if(priorA!=null)
							currentCharacter.unequipChestpiece();
					}
					else if(a.armorType == Armor.TYPE_LEGGINGS)
					{
						priorA = currentCharacter.leggingsSlot;
						if(priorA!=null)
							currentCharacter.unequipLeggings();
					}
					
					//equip this gear
					currentCharacter.equipArmor(a);
					
					//take it out of our bag
					gameModule.playerInventory.getContents().remove(selectedItem);
					
					//put prior gear into bag at the end of the bag
					if(priorA!=null)
						gameModule.playerInventory.moveItem(priorA);
				}
				
				selectedItem = null;
				
				preserve = true;
				gameModule.activateInterface("InterfaceInventory");
				preserve = false;
			}
			
			gameModule.saveSave();
		}
		else if(inputString.equals("select"))
		{
			if(additionalInput.length() > 0)
			{
				int index = Integer.parseInt(additionalInput);
				
				ArrayList<Item> items = gameModule.playerInventory.getContents();
				
				if(index < items.size() && index >= 0)
				{
					Item i = items.get(index);
					selectedItem = i;
					
					preserve = true;
					gameModule.activateInterface("InterfaceInventory");
					preserve = false;
				}
			}
		}
		else if(inputString.equals("nextFilter"))
		{
			nextFilter();
			
			preserve = true;
			gameModule.activateInterface("InterfaceInventory");
			preserve = false;
		}
		else if(inputString.equals("prevFilter"))
		{
			priorFilter();
			
			preserve = true;
			gameModule.activateInterface("InterfaceInventory");
			preserve = false;
		}
		else if(inputString.equals("consume"))
		{
			preserve = true;
			gameModule.giveInterfaceInput("InterfaceMenuConfirm","activate","consume");
		}
		else if(inputString.equals("filter"))
		{
			if(additionalInput.length() > 0)
			{
				int priorFilter = currentFilter;

				if(additionalInput.equals("consumable"))
				{
					currentFilter = Item.ITEM_TYPE_POTION;
				}
				else if(additionalInput.equals("costume"))
				{
					currentFilter = Item.ITEM_TYPE_COSTUME;
				}
				else if(additionalInput.equals("furniture"))
				{
					currentFilter = Item.ITEM_TYPE_FURNITURE;
				}
				else if(additionalInput.equals("weapon"))
				{
					currentFilter = Item.ITEM_TYPE_WEAPON;
				}
				else if(additionalInput.equals("armor"))
				{
					currentFilter = Item.ITEM_TYPE_ARMOR;
				}
				else if(additionalInput.equals("spell"))
				{
					currentFilter = Item.ITEM_TYPE_SPELL;
				}
				else if(additionalInput.equals("none"))
				{
					currentFilter = Item.ITEM_TYPE_NONE;
				}
				
				selectedItem = null;
				
				if(priorFilter != currentFilter)
					currentPage = 1;
				
				preserve = true;
				gameModule.activateInterface("InterfaceInventory");
				preserve = false;
			}
		}
		else if(inputString.equals("consume_conf"))
		{
			if(selectedItem!=null)
			{
				if(gameModule.getInterface("InterfaceInventory") != null)
				{
					InterfaceInventory inventory = (InterfaceInventory)(gameModule.getInterface("InterfaceInventory"));
					
					//use it's effect
					if(gameModule.partyData.getMainCharacter() != null)
					{
						gameModule.partyData.getMainCharacter().useItem(selectedItem.getEffect());
					}
					
					//force the game to save
					gameModule.saveSave();
					
					//then purge it
					gameModule.giveInterfaceInput("InterfaceInventory", "destroySelected", "");
					
					gameModule.activateInterface("InterfaceInventory");
					inventory.preserve = false;
				}
				
				selectedItem = null;
			}
		}
	}
	
	public void destroySelected()
	{
		if(selectedItem!=null)
		{
			gameModule.playerInventory.getContents().remove(selectedItem);
			
			if(gameModule.playerInventory.getContents().size()-1 < (currentPage-1)*itemsPerSet)
				currentPage--;
			
			selectedItem = null;
			
			preserve = true;
			gameModule.activateInterface("InterfaceInventory");
			preserve = false;
		}
		
		gameModule.saveSave();
	}
	
	public String getFilter()
	{
		if((currentFilter == Item.ITEM_TYPE_NONE))
			return "Everything";

		else if((currentFilter == Item.ITEM_TYPE_WEAPON))
			return "Weapon";

		else if((currentFilter == Item.ITEM_TYPE_SPELL))
			return "Spell";

		else if((currentFilter == Item.ITEM_TYPE_ARMOR))
			return "Armor";

		else if((currentFilter == Item.ITEM_TYPE_FURNITURE))
			return "Furniture";
		
		else if((currentFilter == Item.ITEM_TYPE_POTION))
			return "Consumable";
		
		else
			return "Error";
	}
	
	public void nextFilter()
	{
		if((currentFilter == Item.ITEM_TYPE_NONE))
			currentFilter = Item.ITEM_TYPE_WEAPON;

		else if((currentFilter == Item.ITEM_TYPE_WEAPON))
			currentFilter = Item.ITEM_TYPE_SPELL;

		else if((currentFilter == Item.ITEM_TYPE_SPELL))
			currentFilter = Item.ITEM_TYPE_ARMOR;

		else if((currentFilter == Item.ITEM_TYPE_ARMOR))
			currentFilter = Item.ITEM_TYPE_FURNITURE;

		else if((currentFilter == Item.ITEM_TYPE_FURNITURE))
			currentFilter = Item.ITEM_TYPE_POTION;
		
		else if((currentFilter == Item.ITEM_TYPE_POTION))
			currentFilter = Item.ITEM_TYPE_NONE;
	}
	
	public void priorFilter()
	{
		if((currentFilter == Item.ITEM_TYPE_NONE))
			currentFilter = Item.ITEM_TYPE_POTION;

		else if((currentFilter == Item.ITEM_TYPE_WEAPON))
			currentFilter = Item.ITEM_TYPE_NONE;

		else if((currentFilter == Item.ITEM_TYPE_SPELL))
			currentFilter = Item.ITEM_TYPE_WEAPON;

		else if((currentFilter == Item.ITEM_TYPE_ARMOR))
			currentFilter = Item.ITEM_TYPE_SPELL;

		else if((currentFilter == Item.ITEM_TYPE_FURNITURE))
			currentFilter = Item.ITEM_TYPE_ARMOR;
		
		else if((currentFilter == Item.ITEM_TYPE_POTION))
			currentFilter = Item.ITEM_TYPE_FURNITURE;
	}
}
