package com.snorlon.sevestralkingdoms.Interfaces;

import java.util.ArrayList;

import com.snorlon.sevestralkingdoms.GameTools.Core;
import com.snorlon.sevestralkingdoms.GameTools.Gesture;
import com.snorlon.sevestralkingdoms.GameTools.InterfaceElement;
import com.snorlon.sevestralkingdoms.Housing.Floor;
import com.snorlon.sevestralkingdoms.Housing.House;
import com.snorlon.sevestralkingdoms.Buildings.HouseBuilding;
import com.snorlon.sevestralkingdoms.Items.Furniture;
import com.snorlon.sevestralkingdoms.Items.Item;
import com.snorlon.sevestralkingdoms.Places.Town;

public class InterfaceHouse extends GenericInterface
{	
	boolean reset = true;
	boolean resetObject = true;
	
	int	x = 1;
	int y = 1;
	
	float scale = 1.0f;
	
	int floor_width = 10;
	int floor_height = 8;
	

	InterfaceElement zoomIn = null;
	InterfaceElement zoomOut = null;
	InterfaceElement floorUp = null;
	InterfaceElement floorDown = null;
	InterfaceElement cancel = null;
	InterfaceElement confirm = null;
	InterfaceElement rotateCW = null;
	InterfaceElement rotateCCW = null;
	InterfaceElement modify = null;
	InterfaceElement remove = null;
	InterfaceElement build = null;
	
	InterfaceElement title = null;
	
	InterfaceElement mapBox = null;

	InterfaceElement[][] tiles;
	InterfaceElement[][] furnitureS;
	InterfaceElement[][] furnitureML;
	InterfaceElement[][] furnitureFW;//floor, wall
	
	int currentFloor = 1;
	int maxFloors = 1;

	Furniture currentObject = null;//new Furniture("Wooden Chair", "DICKS", "Wooden Chair.png", 0, 1, 2, false, Furniture.FURNITURE_MEDIUM, 1);
	//Furniture currentObject = new Furniture("Wooden Table", "DICKS", "Wooden Table.png", 0, 2, 2, false, Furniture.FURNITURE_BIG, 2);
	
	int priorX = -1;
	int priorY = -1;
	
	boolean placingFurniture = false;
	boolean fromInventory = true;
	
	House currentHouse = null;

	public InterfaceHouse(Core newCore)
	{
		super(newCore);
		
		id = "InterfaceHouse";
	}
	
	//used to pull up the particular screen in question
	public void activate()
	{
		if(reset)
		{
			//load our house
			if(gameModule.partyData.getMainCharacter().currentLocation!=null && gameModule.partyData.getMainCharacter().currentLocation.getMap() != null && gameModule.partyData.getMainCharacter().currentLocation.getMap() instanceof Town)
			{
				Town t = (Town) gameModule.partyData.getMainCharacter().currentLocation.getMap();
				
				if(t.getBuilding("House")!=null)
				{
					currentHouse = ((HouseBuilding) t.getBuilding("House")).getHouse();
				}
			}
			
			if(currentHouse!=null)
			{
				scale = 1.0f;
				maxFloors = currentHouse.getFloorCount();
				floor_height = currentHouse.getHeight();
				floor_width = currentHouse.getWidth();
				y = floor_height/2;
				x = floor_width/2;
				tiles = new InterfaceElement[floor_height+2][floor_width];
				furnitureS = new InterfaceElement[floor_height+2][floor_width];
				furnitureML = new InterfaceElement[floor_height+2][floor_width];
				furnitureFW = new InterfaceElement[floor_height+2][floor_width];
			}
		}
		
		if(resetObject && reset)
		{
			currentObject = null;
			placingFurniture = false;
			fromInventory = false;
			currentFloor = 1;
		}
		
		if(currentHouse == null)
		{
			System.out.println("InterfaceHouse error: Invalid House");
			return;
		}
		
		//create a gui container
		newElement = new InterfaceElement(coreModule, "InterfaceContainer", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 720, 1180, 0, 0);
		renderModule.addElement("CenterNode", newElement);
		
		//bg
		newElement = new InterfaceElement(coreModule, "bg", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 718, 1280, 24, 51, 1.0f, 1.0f, "menu/menubg.png");
		renderModule.addElement("InterfaceContainer", newElement);
		newElement = new InterfaceElement(coreModule, "background_bg", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 718, 1280, -24, -50, 1.0f, 1.0f, "interfaces/house/background2.png");
		renderModule.addElement("bg", newElement);

		mapBox = new InterfaceElement(coreModule, "MapBoxMoveable", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 531, 569, 0 ,0);
		renderModule.addElement("bg", mapBox);
		newElement = new InterfaceElement(coreModule, "MapBox", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 531, 569, -24, 75);
		renderModule.addElement("MapBoxMoveable", newElement);
		newElement = new InterfaceElement(coreModule, "MapFurnitureFloorBox", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 531, 569, -24, 75);
		renderModule.addElement("MapBoxMoveable", newElement);
		newElement = new InterfaceElement(coreModule, "MapFurnitureMediumBox", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 531, 569, -24, 75);
		renderModule.addElement("MapBoxMoveable", newElement);
		newElement = new InterfaceElement(coreModule, "MapFurnitureSmallBox", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 531, 569, -24, 75);
		renderModule.addElement("MapBoxMoveable", newElement);
		newElement = new InterfaceElement(coreModule, "MapUIBox", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 531, 569, -24, 75);
		renderModule.addElement("bg", newElement);
		
		newElement = new InterfaceElement(coreModule, "background_bg", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 718, 1280, -24, -50, 1.0f, 1.0f, "interfaces/house/background.png");
		renderModule.addElement("bg", newElement);
		

		newElement = new InterfaceElement(coreModule, "MapOverlayBox", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 531, 569, -24, 75);
		renderModule.addElement("bg", newElement);
		newElement = new InterfaceElement(coreModule, "MapFurnitureBox", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 531, 569, -24, 75);
		renderModule.addElement("bg", newElement);

		
		/*
		 * 
		newElement = new InterfaceElement(coreModule, "house_bg", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 1024, 320, (x-floor_width/2)*-32 + 16, (y-floor_height/2)*32 + 16, 1.0f, 1.0f, "furniture/houses/small_simple_8x8.png");
		newElement.generateFrames(256, 320, 0, rotation, 3, false);
		renderModule.addElement("MapBox", newElement);
		 */
		
		renderTiles();
		
		newElement = new InterfaceElement(coreModule, "tile", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 34, 34, 0,0, scale, scale, "interfaces/house/cursor.png");
		newElement.setColor(255, 255, 255, 0.5f);
		renderModule.addElement("MapOverlayBox", newElement);

		floorUp = new InterfaceElement(coreModule, "map_floor_up", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 200, 68, 200, -240, 1.0f, 1.0f, "interfaces/house/floor.png");
		new Gesture("Click", "interface~action~InterfaceHouse:Upfloor", floorUp);
		floorUp.generateFrames(100, 68, 2, 0, 1, false);
		if(currentFloor == maxFloors)
			floorUp.hide();
		renderModule.addElement("MapUIBox", floorUp);

		floorDown = new InterfaceElement(coreModule, "map_floor_down", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 200, 68, 67, -240, 1.0f, 1.0f, "interfaces/house/floor.png");
		new Gesture("Click", "interface~action~InterfaceHouse:Downfloor", floorDown);
		floorDown.generateFrames(100, 68, 2, 1, 1, false);
		if(currentFloor == 1)
			floorDown.hide();
		renderModule.addElement("MapUIBox", floorDown);
		
		zoomIn = new InterfaceElement(coreModule, "map_zoom_in", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 200, 68, -67, -240, 1.0f, 1.0f, "interfaces/house/zoom.png");
		new Gesture("Click", "interface~action~InterfaceHouse:Zoom", zoomIn);
		//zoomIn.setColor(255, 255, 255, 0.5f);
		zoomIn.generateFrames(100, 68, 2, 0, 1, false);
		if(scale == 2.0f)
			zoomIn.hide();
		renderModule.addElement("MapUIBox", zoomIn);

		zoomOut = new InterfaceElement(coreModule, "map_zoom_out", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 200, 68, -200, -240, 1.0f, 1.0f, "interfaces/house/zoom.png");
		new Gesture("Click", "interface~action~InterfaceHouse:Unzoom", zoomOut);
		//zoomOut.setColor(255, 255, 255, 0.5f);
		zoomOut.generateFrames(100, 68, 2, 1, 1, false);
		if(scale == 1.0f)
			zoomOut.hide();
		renderModule.addElement("MapUIBox", zoomOut);

		cancel = new InterfaceElement(coreModule, "map_cancel", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 233, 68, 133, -240, 1.0f, 1.0f, "interfaces/house/cancel.png");
		new Gesture("Click", "interface~action~InterfaceHouse:cancel", cancel);
		renderModule.addElement("MapUIBox", cancel);

		remove = new InterfaceElement(coreModule, "map_cancel", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 266, 68, 133, -240, 1.0f, 1.0f, "interfaces/house/remove.png");
		remove.generateFrames(233, 68, 0, 0, 1, false);
		new Gesture("Click", "interface~action~InterfaceHouse:cancel", remove);
		renderModule.addElement("MapUIBox", remove);

		build = new InterfaceElement(coreModule, "map_build", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 233, 68, -130, 240, 1.0f, 1.0f, "interfaces/house/build.png");
		new Gesture("Click", "interface~action~InterfaceHouse:build", build);
		renderModule.addElement("MapUIBox", build);

		confirm = new InterfaceElement(coreModule, "map_confirm", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 233, 68, -130, 240, 1.0f, 1.0f, "interfaces/house/confirm.png");
		new Gesture("Click", "interface~action~InterfaceHouse:confirm", confirm);
		renderModule.addElement("MapUIBox", confirm);
		
		modify = new InterfaceElement(coreModule, "map_modify", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 233, 68, 130, 240, 1.0f, 1.0f, "interfaces/house/modify.png");
		new Gesture("Click", "interface~action~InterfaceHouse:modify", modify);
		renderModule.addElement("MapUIBox", modify);

		rotateCCW = new InterfaceElement(coreModule, "map_rccw_in", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 140, 100, -220, 0, 1.0f, 1.0f, "interfaces/house/rotate.png");
		new Gesture("Click", "interface~action~InterfaceHouse:RotateCCW", rotateCCW);
		rotateCCW.generateFrames(70, 100, 2, 0, 1, false);
		renderModule.addElement("MapUIBox", rotateCCW);
		
		rotateCW = new InterfaceElement(coreModule, "map_rcw_in", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 140, 100, 220, 0, 1.0f, 1.0f, "interfaces/house/rotate.png");
		new Gesture("Click", "interface~action~InterfaceHouse:RotateCW", rotateCW);
		rotateCW.generateFrames(70, 100, 2, 1, 1, false);
		renderModule.addElement("MapUIBox", rotateCW);
		
		if(!placingFurniture)
		{
			rotateCW.hide();
			rotateCCW.hide();
			cancel.hide();
			confirm.hide();
		}
		else
		{
			cancel.show();
			confirm.show();
			build.hide();
		}
		
		modify.hide();
		remove.hide();

		newElement = new InterfaceElement(coreModule, "map_image_arrowu", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 243, 113, 0, -370);
		new Gesture("Click", "interface~action~InterfaceHouse:Up", newElement);
		renderModule.addElement("MapOverlayBox", newElement);
		
		newElement = new InterfaceElement(coreModule, "map_image_arrowd", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 243, 113, 0, -493);
		new Gesture("Click", "interface~action~InterfaceHouse:Down", newElement);
		renderModule.addElement("MapOverlayBox", newElement);
		
		newElement = new InterfaceElement(coreModule, "map_image_arrowl", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 133, 236, -202, -430);
		new Gesture("Click", "interface~action~InterfaceHouse:Left", newElement);
		renderModule.addElement("MapOverlayBox", newElement);
		
		newElement = new InterfaceElement(coreModule, "map_image_arrowr", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 133, 236, 202, -430);
		new Gesture("Click", "interface~action~InterfaceHouse:Right", newElement);
		renderModule.addElement("MapOverlayBox", newElement);
					
		
		//return text
		newElement = new InterfaceElement(coreModule, "return_button", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 560, 60, -24, -554);
		new Gesture("Click", "interface~open~InterfaceTownOverview", newElement);
		renderModule.addElement("bg", newElement);
		
		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 546, 70, 147, 10, 1.2f, "Return to Town", coreModule.renderModule.FONT_IMPACT);
		renderModule.addElement("return_button", newElement);
		
		moveBox();
		
		//auto reset ourselves
		reset = true;
	}
	
	public void destroy()
	{
		super.destroy();
		
		mapBox = null;
		zoomIn = null;
		zoomOut = null;
		floorUp = null;
		floorDown = null;
		cancel = null;
		confirm = null;
		rotateCW = null;
		rotateCCW = null;
		modify = null;
		remove = null;
		build = null;
		
		title = null;
		

		if(tiles!=null)
		{
			for(int i=0; i<floor_height+2; i++)
			{
				for(int j=0; j<floor_width; j++)
				{
					tiles[i][j] = null;
					furnitureS[i][j] = null;
					furnitureML[i][j] = null;
					furnitureFW[i][j] = null;
				}
			}
		}
		
		if(reset)
		{
			currentHouse = null;
		}
	}

	public void Up()
	{
		if(y > -1)
			y--;
	}
	public void Down()
	{
		if(y<floor_height)
			y++;
	}
	public void Right()
	{
		if(x > 1)
			x--;
	}
	public void Left()
	{
		if(x < floor_width)
			x++;
	}

	//given a string of input, it will perform something upon the interface
	public void processInput(String inputString, String additionalInput)
	{
		if(inputString.equals("Right"))
		{
			Right();
		}
		
		else if(inputString.equals("Left"))
		{
			Left();
		}
		else if(inputString.equals("Up"))
		{
			Up();
		}
		else if(inputString.equals("Down"))
		{
			Down();
		}
		
		else if(inputString.equals("Upfloor"))
		{
			if(currentFloor<maxFloors)
				currentFloor++;

			renderTiles();
		}
		
		else if(inputString.equals("Downfloor"))
		{
			if(currentFloor>1)
				currentFloor--;

			renderTiles();
		}
		
		else if(inputString.equals("Zoom"))
		{
			if(scale<=1.5f)
				scale+=0.5f;
			else
				scale = 2.0f;
			
			renderTiles();
		}
		
		else if(inputString.equals("Unzoom"))
		{
			if(scale>=1.5f)
				scale-=0.5f;
			else
				scale = 1.0f;

			renderTiles();
		}
		
		else if(inputString.equals("RotateCCW"))
		{
			if(currentObject!=null)
			{
				int rotation = currentObject.getRotation();
				rotation--;
				
				if(rotation < 0)
					rotation = 3;
				
				currentObject.setRotation(rotation);
			}
		}
		
		else if(inputString.equals("RotateCW"))
		{
			if(currentObject!=null)
			{
				int rotation = currentObject.getRotation();
				rotation++;
				
				if(rotation > 3)
					rotation = 0;
				
				currentObject.setRotation(rotation);
			}
		}
		
		else if(inputString.equals("confirm"))
		{
			if(placingFurniture)
			{
				Floor f = currentHouse.getFloor(currentFloor);
				
				if(currentObject!=null && f.canPlaceFurniture(currentObject, (floor_width+1)-x, y))
				{
					f.placeFurniture(currentObject, (floor_width+1)-x, y, currentObject.getRotation());
					
					//attempt to remove it from our inventory by its address
					gameModule.playerInventory.removeItem(currentObject);
					
					currentObject = null;
				}
				
				placingFurniture = false;

				renderTiles();
			}
		}
		
		else if(inputString.equals("cancel"))
		{
			if(placingFurniture)
			{
				if(currentObject!=null)
				{
					Floor f = currentHouse.getFloor(currentFloor);
					if(!fromInventory)
					{
						//store the item
						if(gameModule.playerInventory.getRemainingCapacity() > 0)
						{
							gameModule.playerInventory.moveItem(currentObject);
							
							System.out.println("Player took furniture!");
						}
						else
						{
							f.placeFurniture(currentObject, (floor_width+1)-priorX, priorY, currentObject.getRotation());
							
							System.out.println("Returning furniture!");
						}
						fromInventory = true;
						
						currentObject = null;
						
						placingFurniture = false;
						
						renderTiles();
					}
					else
					{						
						placingFurniture = false;
						currentObject = null;
					}
					
				}
			}
		}
		
		else if(inputString.equals("modify"))
		{
			Floor f = currentHouse.getFloor(currentFloor);
			if(!placingFurniture && f.canRemoveFurniture((floor_width+1)-x, y))
			{
				currentObject = f.removeFurniture((floor_width+1)-x, y);
				
				System.out.println("Moving object "+currentObject.getName());
				
				placingFurniture = true;
				
				fromInventory = false;
				
				priorX = x;
				priorY = y;


				renderTiles();
			}
		}
		
		else if(inputString.equals("build"))
		{
			reset = false;
			gameModule.activateInterface("InterfaceHouseBuild");
			return;
		}
		
		else if(inputString.equals("return"))
		{
			reset = false;
			resetObject = false;
			currentObject = null;
			placingFurniture = false;
			gameModule.activateInterface("InterfaceHouse");
			return;
		}
		
		else if(inputString.equals("buildItem"))
		{
			if(additionalInput.length() > 0)
			{
				int index = Integer.parseInt(additionalInput.trim());

				if(index < gameModule.playerInventory.getContents().size() && index >= 0)
				{
					Item i = gameModule.playerInventory.getContents().get(index);
					
					if(i instanceof Furniture)
					{
						currentObject = (Furniture) i;
					}
				
					
					resetObject = false;
					placingFurniture = true;
					fromInventory = true;
					gameModule.activateInterface("InterfaceHouse");
					resetObject = true;
				}
			}
			
			return;
		}
		
		moveBox();//update the positions on everything
	}
	
	public int getHorizontal()
	{
		return floor_width;
	}
	
	public int getVertical()
	{
		return floor_height;
	}
	
	public int calculateXOffset()
	{
		return x-getHorizontal()/2;
	}
	
	public int calculateYOffset()
	{
		return y-(int) (Math.floor(getVertical()/2.0));
	}
	
	public void createTitle()
	{
		if(title!=null)
			renderModule.destroyElement(title);
		
		title = new InterfaceElement(coreModule, id, "Center", "Center", 700, 70, 110, 425, 2.0f, "Home - Floor "+currentFloor, coreModule.renderModule.FONT_IMPACT);
		renderModule.addElement("bg", title);
	}
	
	public void moveBox()
	{
		//update our mapBox position and all contained elements
		if(mapBox!=null)
		{
			mapBox.offset_x = (int) ((calculateXOffset())*32 * scale - 32*scale);
			mapBox.offset_y = (int) ((calculateYOffset())*32 * scale);
			mapBox.recalcChildren();
		}

		if(placingFurniture)
		{
			floorUp.hide();
			floorDown.hide();
			rotateCW.show();
			rotateCCW.show();
			build.hide();
			if(!fromInventory)
			{
				cancel.hide();
				remove.show();
				if(gameModule.playerInventory.getRemainingCapacity()>0)
				{
					remove.current_frame = 0;
				}
				else
				{
					remove.current_frame = 1;
				}
			}
			else
			{
				remove.hide();
				cancel.show();
			}
		}
		else
		{
			build.show();
			floorUp.show();
			floorDown.show();
			rotateCW.hide();
			rotateCCW.hide();
			cancel.hide();
			confirm.hide();
			remove.hide();
		}
		
		ArrayList< InterfaceElement[][] > stuffV2 = new ArrayList< InterfaceElement[][] >();
		ArrayList<InterfaceElement> hidden = new ArrayList<InterfaceElement>();

		stuffV2.add(tiles);
		stuffV2.add(furnitureS);
		stuffV2.add(furnitureML);
		stuffV2.add(furnitureFW);
		
		float scale2 = scale*scale;

		renderModule.destroyElement("FurnitureGhost");
		renderModule.destroyElement("CanPlace");
		
		Floor f = currentHouse.getFloor(currentFloor);
		if(placingFurniture)
		{
			modify.hide();
			
			if(currentObject!=null)
			{
				newElement = currentObject.generateImage(coreModule, 0, 0, scale, id, "FurnitureGhost");
				newElement.setColor(255, 255, 255, 0.9f);
				renderModule.addElement("MapFurnitureBox", newElement);

				
				if(f.canPlaceFurniture(currentObject, (floor_width+1)-x, y))
				{
					newElement = new InterfaceElement(coreModule, "CanPlace", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 256, 128, 200, 200, 1.0f, 1.0f, "interfaces/house/marking.png");
					newElement.generateFrames(128, 128, 2, 0, 1, false);
					newElement.setColor(255, 255, 255, 0.75f);
					renderModule.addElement("MapFurnitureBox", newElement);
					
					confirm.show();
				}
				else
				{
					
					newElement = new InterfaceElement(coreModule, "CanPlace", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 256, 128, 200, 200, 1.0f, 1.0f, "interfaces/house/marking.png");
					newElement.generateFrames(128, 128, 2, 1, 1, false);
					newElement.setColor(255, 255, 255, 0.75f);
					renderModule.addElement("MapFurnitureBox", newElement);
					
					confirm.hide();
				}
			}
		}
		else
		{
			if(f.canRemoveFurniture((floor_width+1)-x, y))
			{
				modify.show();
			}
			else
			{
				modify.hide();
			}
		}

		if(scale == 1.0f)
			zoomOut.hide();
		else
			zoomOut.show();

		if(scale == 1.5f)
			zoomIn.hide();
		else
			zoomIn.show();
		
		for(InterfaceElement[][] h : stuffV2)
		{
			//occlude tiles off-screen
			for(int i=0; i<floor_height+2; i++)
			{
				for(int j=0; j<floor_width; j++)
				{
					if(h[i][j]!=null)
					{
						InterfaceElement e = h[i][j];
						int tx = (j)+1;
						int ty = (i);
						
						if(Math.abs(tx-x) >  (6)/scale || ty-y >  Math.floor((8)/scale) || ty-y <  Math.floor(-6/scale2))
						{
							e.hide();
							hidden.add(e);
						}
						else
						{
							if(!hidden.contains(e))
							{
								e.show();	
							}
						}
					}
				}
			}
		}
		
		hidden.clear();
		stuffV2.clear();
	}
	
	public void purgeTiles()
	{
		//kill our kids
		renderModule.getElement("MapFurnitureFloorBox").destroyChildren();
		renderModule.getElement("MapFurnitureMediumBox").destroyChildren();
		renderModule.getElement("MapFurnitureSmallBox").destroyChildren();
		renderModule.getElement("MapBox").destroyChildren();
		
		for(int i=0; i<getVertical()+2; i++)
		{
			for(int j=0; j<getHorizontal(); j++)
			{
				tiles[i][j] = null;
				furnitureFW[i][j] = null;
				furnitureML[i][j] = null;
				furnitureS[i][j] = null;
			}
		}
	}
	
	public void renderTiles()
	{
		purgeTiles();
		
		int offsetX = getHorizontal()/2;
		int offsetY = getVertical()/2;
		
		int[][] tileData = currentHouse.getFloor(currentFloor).getTiles();
		
		//alternative, tile-based house design
		for(int i=0; i<2; i++)
		{
			for(int j=0; j<getHorizontal(); j++)
			{
				//store ALL tiles in the tile holder
				newElement = new InterfaceElement(coreModule, "tile", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 320, 320, ((j - offsetX)*-32)*scale, (i - offsetY - 1)*-32*scale, scale, scale, "interfaces/house/house_tiles.png");
				newElement.generateFrames(32, 32, 0, tileData[i][getHorizontal()-j-1], 399, false);
				renderModule.addElement("MapBox", newElement);
				tiles[i][j] = newElement;
			}
		}
		
		for(int i=2; i<getVertical()+2; i++)
		{
			for(int j=0; j<getHorizontal(); j++)
			{
				newElement = new InterfaceElement(coreModule, "tile", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 320, 320, ((j - offsetX)*-32)*scale, (i - offsetY - 1)*-32*scale, scale, scale, "interfaces/house/house_tiles.png");
				newElement.generateFrames(32, 32, 0, tileData[i][getHorizontal()-j-1], 399, false);
				renderModule.addElement("MapBox", newElement);
				tiles[i][j] = newElement;
			}
		}
		
		Floor f = currentHouse.getFloor(currentFloor);
		
		for(int i=0; i<getVertical()+2; i++)
		{
			for(int j=0; j<getHorizontal(); j++)
			{
				int y = i;
				int x = getHorizontal()-j-1;//draw furniture if we have it for this tile
				
				Furniture[][] floorFurnitureFloorWall = f.getFurnitureFloorWall();
				
				if(floorFurnitureFloorWall[y][x] != null && !f.hasDud(floorFurnitureFloorWall[y][x]))
				{
					newElement = floorFurnitureFloorWall[y][x].generateImage(coreModule, ((j - offsetX)*-32)*scale, (i - offsetY - 1)*-32*scale, scale, id, "furniture"+(i-2)+" "+j);
					renderModule.addElement("MapFurnitureFloorBox", newElement);
					
					int tx = 1;
					int ty = 1;
					
					while(ty <= floorFurnitureFloorWall[y][x].getEHeight())
					{
						while(tx <= floorFurnitureFloorWall[y][x].getWidth())
						{
							int nx = j-tx+1;
							int ny = ((i+1)-ty);
							furnitureFW[ny][nx] = newElement;
							
							tx++;
						}
						tx = 1;
						ty++;
					}
				}
			}
		}
		
		for(int i=2; i<getVertical()+2; i++)
		{
			for(int j=0; j<getHorizontal(); j++)
			{
				int y = i-2;
				int x = getHorizontal()-j-1;//draw furniture if we have it for this tile

				
				Furniture[][] floorFurnitureMediumLarge = f.getFurnitureMediumLarge();
				
				if(floorFurnitureMediumLarge[y][x] != null && !f.hasDud(floorFurnitureMediumLarge[y][x]))
				{
					newElement = floorFurnitureMediumLarge[y][x].generateImage(coreModule, ((j - offsetX)*-32)*scale, (i - offsetY - 1)*-32*scale, scale, id, "furniture"+(i-2)+" "+j);
					renderModule.addElement("MapFurnitureMediumBox", newElement);
					
					int tx = 1;
					int ty = 1;
					
					while(ty <= floorFurnitureMediumLarge[y][x].getEHeight())
					{
						while(tx <= floorFurnitureMediumLarge[y][x].getWidth())
						{
							int nx = j-tx+1;
							int ny = ((i+1)-ty);
							furnitureML[ny][nx] = newElement;
							//System.out.println(floorFurnitureMediumLarge[y][x].getName()+" "+ny+"|"+nx);
							
							tx++;
						}
						tx = 1;
						ty++;
					}
				}
			}
		}
		
		for(int i=2; i<getVertical()+2; i++)
		{
			for(int j=0; j<getHorizontal(); j++)
			{
				int y = i-2;
				int x = getHorizontal()-j-1;//draw furniture if we have it for this tile

				Furniture[][] floorFurnitureSmall = f.getFurnitureSmall();
				
				if(floorFurnitureSmall[y][x] != null && !f.hasDud(floorFurnitureSmall[y][x]))
				{
					newElement = floorFurnitureSmall[y][x].generateImage(coreModule, ((j - offsetX)*-32)*scale, (int) ((i - offsetY - 1 - 0.40)*-32*scale), scale, id, "furniture"+(i-2)+" "+j);
					renderModule.addElement("MapFurnitureSmallBox", newElement);
					
					int tx = 1;
					int ty = 1;
					
					while(ty <= floorFurnitureSmall[y][x].getEHeight())
					{
						while(tx <= floorFurnitureSmall[y][x].getWidth())
						{
							int nx = j-tx+1;
							int ny = ((i+1)-ty);
							furnitureS[ny][nx] = newElement;
							//System.out.println(floorFurnitureSmall[y][x].getName()+" "+ny+"|"+nx);
							
							tx++;
						}
						tx = 1;
						ty++;
					}
				}
			}
		}
		
		createTitle();
	}
}
