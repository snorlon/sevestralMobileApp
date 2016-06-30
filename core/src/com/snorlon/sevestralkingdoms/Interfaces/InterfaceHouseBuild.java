package com.snorlon.sevestralkingdoms.Interfaces;

import java.util.ArrayList;

import com.snorlon.sevestralkingdoms.GameTools.Core;
import com.snorlon.sevestralkingdoms.GameTools.Gesture;
import com.snorlon.sevestralkingdoms.GameTools.InterfaceElement;
import com.snorlon.sevestralkingdoms.Items.*;

public class InterfaceHouseBuild extends GenericInterface
{
	
	Furniture selectedItem = null;
	int currentPage = 1;
	
	final int itemsPerRow = 5;
	final int itemsPerCol = 3;
	final int itemsPerSet = itemsPerRow*itemsPerCol;

	
	int selectedIndex = 0;
	
	int furnitureFilter = Furniture.FURNITURE_MEDIUM;
	
	boolean preserve = false;

	public InterfaceHouseBuild(Core newCore)
	{
		super(newCore);
		// TODO Auto-generated constructor stub
		
		id = "InterfaceHouseBuild";
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
		newElement = new InterfaceElement(coreModule, "background_bg", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 718, 1280, -24, -50, 1.0f, 1.0f, "interfaces/house/background2.png");
		renderModule.addElement("bg", newElement);
		newElement = new InterfaceElement(coreModule, "overview_background_bg", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 718, 1280, -24, -50, 1.0f, 1.0f, "interfaces/house build/background.png");
		renderModule.addElement("bg", newElement);
		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 700, 70, 120, 425, 2.0f, "Select Furniture", coreModule.renderModule.FONT_IMPACT);
		renderModule.addElement("bg", newElement);
		
		//filters
		newElement = new InterfaceElement(coreModule, "ConsumableFilter", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 173, 52, -208, 288);
		new Gesture("Click", "interface~action~InterfaceHouseBuild:filter:small", newElement);
		renderModule.addElement("bg", newElement);
		if((furnitureFilter == Furniture.FURNITURE_SMALL)){flag = 1;}else{flag = 0;}
		newElement = new InterfaceElement(coreModule, "ConsumableButton", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 360, 57, 0, 0, 1.0f, 1.0f, "interfaces/inventory/filter_small.png");
		newElement.generateFrames(180, 57, 0, flag, 1, false);
		coreModule.renderModule.addElement("ConsumableFilter", newElement);
		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 173, 70, 41, 7, 1.2f, "Small", coreModule.renderModule.FONT_IMPACT);
		renderModule.addElement("ConsumableFilter", newElement);	
		
		newElement = new InterfaceElement(coreModule, "FurnitureFilter", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 173, 52, -22, 288);
		new Gesture("Click", "interface~action~InterfaceHouseBuild:filter:medium", newElement);
		renderModule.addElement("bg", newElement);
		if((furnitureFilter == Furniture.FURNITURE_MEDIUM)){flag = 1;}else{flag = 0;}
		newElement = new InterfaceElement(coreModule, "FurnitureButton", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 360, 57, 0, 0, 1.0f, 1.0f, "interfaces/inventory/filter_small.png");
		newElement.generateFrames(180, 57, 0, flag, 1, false);
		coreModule.renderModule.addElement("FurnitureFilter", newElement);
		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 230, 70, 55, 7, 1.2f, "Medium", coreModule.renderModule.FONT_IMPACT);
		renderModule.addElement("FurnitureFilter", newElement);	
		
		newElement = new InterfaceElement(coreModule, "LargeFilter", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 173, 52, 164, 288);
		new Gesture("Click", "interface~action~InterfaceHouseBuild:filter:large", newElement);
		renderModule.addElement("bg", newElement);
		if((furnitureFilter == Furniture.FURNITURE_BIG)){flag = 1;}else{flag = 0;}
		newElement = new InterfaceElement(coreModule, "LargeButton", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 360, 57, 0, 0, 1.0f, 1.0f, "interfaces/inventory/filter_small.png");
		newElement.generateFrames(180, 57, 0, flag, 1, false);
		coreModule.renderModule.addElement("LargeFilter", newElement);
		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 173, 70, 41, 7, 1.2f, "Large", coreModule.renderModule.FONT_IMPACT);
		renderModule.addElement("LargeFilter", newElement);
		
		newElement = new InterfaceElement(coreModule, "WeaponFilter", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 173, 52, -208, 349);
		new Gesture("Click", "interface~action~InterfaceHouseBuild:filter:floor", newElement);
		renderModule.addElement("bg", newElement);
		if((furnitureFilter == Furniture.FURNITURE_FLOOR)){flag = 1;}else{flag = 0;}
		newElement = new InterfaceElement(coreModule, "WeaponButton", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 360, 57, 0, 0, 1.0f, 1.0f, "interfaces/inventory/filter_small.png");
		newElement.generateFrames(180, 57, 0, flag, 1, false);
		coreModule.renderModule.addElement("WeaponFilter", newElement);
		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 173, 70, 45, 7, 1.2f, "Floor", coreModule.renderModule.FONT_IMPACT);
		renderModule.addElement("WeaponFilter", newElement);	
		
		newElement = new InterfaceElement(coreModule, "WallFilter", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 173, 52, -22, 349);
		new Gesture("Click", "interface~action~InterfaceHouseBuild:filter:wall", newElement);
		renderModule.addElement("bg", newElement);
		if((furnitureFilter == Furniture.FURNITURE_WALL)){flag = 1;}else{flag = 0;}
		newElement = new InterfaceElement(coreModule, "WallButton", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 360, 57, 0, 0, 1.0f, 1.0f, "interfaces/inventory/filter_small.png");
		newElement.generateFrames(180, 57, 0, flag, 1, false);
		coreModule.renderModule.addElement("WallFilter", newElement);
		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 173, 70, 49, 7, 1.2f, "Wall", coreModule.renderModule.FONT_IMPACT);
		renderModule.addElement("WallFilter", newElement);	
		
		newElement = new InterfaceElement(coreModule, "Prev", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 150, 52, -222, -259);
		new Gesture("Click", "interface~action~InterfaceHouseBuild:Previous", newElement);
		renderModule.addElement("bg", newElement);
		
		newElement = new InterfaceElement(coreModule, "Next", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 150, 52, 177, -259);
		new Gesture("Click", "interface~action~InterfaceHouseBuild:Next", newElement);
		renderModule.addElement("bg", newElement);

		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 600, 70, 182, -252, 1.1f, "Page "+currentPage+" of "+((int) Math.ceil(gameModule.playerInventory.getCapacity() / itemsPerSet)), coreModule.renderModule.FONT_IMPACT);
		renderModule.addElement("bg", newElement);	
		
		//display options for the selected item
		if(selectedItem!=null)
		{	
			newElement = new InterfaceElement(coreModule, id, "Center", "Center", 450, 120, 100, -242, 1.0f, ""+selectedItem.name, coreModule.renderModule.FONT_IMPACT);
			renderModule.addElement("InterfaceContainer", newElement);
			
			newElement = new InterfaceElement(coreModule, id, "Center", "Center", 450, 120, 100, -300, 1.0f, "Size: "+selectedItem.getWidth()+"x"+selectedItem.getEHeight(), coreModule.renderModule.FONT_IMPACT);
			renderModule.addElement("InterfaceContainer", newElement);
			
			int wallOffset = 0;
			
			if(selectedItem.getFurnitureType() == Furniture.FURNITURE_WALL)
				wallOffset = 96;
			
			//selected item data
			newElement = selectedItem.generateImage(coreModule, -246, -407+wallOffset, 1.0f, id, "furnitureSelected");
			renderModule.addElement("InterfaceContainer", newElement);
			
			newElement = new InterfaceElement(coreModule, "map_confirm", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 233, 68, 0, -130, 1.0f, 1.0f, "interfaces/house/confirm.png");
			new Gesture("Click", "interface~action~InterfaceHouseBuild:Confirm", newElement);
			renderModule.addElement("InterfaceContainer", newElement);
		}
		
		//render all of the items
		ArrayList<Item> playerItems = gameModule.playerInventory.getContents();

		int index = 0;
		int index2 = 0;
		int x = 0;
		int y = 0;
		
		for(Item f : playerItems)
		{
			if(f instanceof Furniture)
			{
				Furniture i = (Furniture) f;
				
				if(i.getFurnitureType() == furnitureFilter)
				{
					if(index >= (currentPage-1)*itemsPerSet && index < currentPage*itemsPerSet)
					{
						newElement = i.generateIcon(coreModule,"item"+index,id, "bg", -237 + 106*x, 171 - 108*y);
						new Gesture("Click", "interface~action~InterfaceHouseBuild:select:"+index2, newElement);
						
						if(selectedItem==i)
						{
							newElement = new InterfaceElement(coreModule, "ItemSelected2", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 96, 96, -234 + 106*x, 171 - 108*y, 1.0f, 1.0f, "interfaces/building_shop/selection.png");
							coreModule.renderModule.addElement("bg", newElement);
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
			}
			index2++;
		}
		

		//return text
		newElement = new InterfaceElement(coreModule, "return_button", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 560, 60, -24, -554);
		new Gesture("Click", "interface~open~InterfaceHouse:return", newElement);
		renderModule.addElement("bg", newElement);
		
		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 546, 70, 140, 10, 1.2f, "Return to House", coreModule.renderModule.FONT_IMPACT);
		renderModule.addElement("return_button", newElement);
	}

	
	//given a string of input, it will perform something upon the interface
	public void processInput(String inputString, String additionalInput)
	{
		if(inputString.equals("Next"))
		{
			if(gameModule.playerInventory.getCapacity() > (currentPage)*itemsPerSet)
			{
				currentPage++;
				
				selectedItem = null;
				
				preserve = true;
				gameModule.activateInterface("InterfaceHouseBuild");
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
				gameModule.activateInterface("InterfaceHouseBuild");
				preserve = false;
			}
		}
		else if(inputString.equals("Confirm"))
		{
			if(selectedItem!=null)
			{
				//pass it on to our house UI
				gameModule.giveInterfaceInput("InterfaceHouse", "buildItem", ""+selectedIndex);
			}
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
					
					if(i instanceof Furniture)
					{
						selectedItem = (Furniture) i;
						selectedIndex = index;
					}
				
					
					preserve = true;
					gameModule.activateInterface("InterfaceHouseBuild");
					preserve = false;
				}
			}
		}
		else if(inputString.equals("filter"))
		{
			if(additionalInput.length() > 0)
			{
				int priorFilter = furnitureFilter;

				if(additionalInput.equals("small"))
				{
					furnitureFilter = Furniture.FURNITURE_SMALL;
				}
				else if(additionalInput.equals("medium"))
				{
					furnitureFilter = Furniture.FURNITURE_MEDIUM;
				}
				else if(additionalInput.equals("large"))
				{
					furnitureFilter = Furniture.FURNITURE_BIG;
					System.out.println("BIG");
				}
				else if(additionalInput.equals("floor"))
				{
					furnitureFilter = Furniture.FURNITURE_FLOOR;
				}
				else if(additionalInput.equals("wall"))
				{
					furnitureFilter = Furniture.FURNITURE_WALL;
				}
				
				selectedItem = null;
				
				if(priorFilter != furnitureFilter)
					currentPage = 1;
				
				preserve = true;
				gameModule.activateInterface("InterfaceHouseBuild");
				preserve = false;
			}
		}
	}
}
