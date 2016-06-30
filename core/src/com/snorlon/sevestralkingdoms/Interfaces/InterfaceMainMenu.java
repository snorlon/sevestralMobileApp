package com.snorlon.sevestralkingdoms.Interfaces;

import java.io.PrintWriter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.snorlon.sevestralkingdoms.DataManagement.LoadSaveManager;
import com.snorlon.sevestralkingdoms.GameTools.Core;
import com.snorlon.sevestralkingdoms.GameTools.Gesture;
import com.snorlon.sevestralkingdoms.GameTools.InterfaceElement;
import com.snorlon.sevestralkingdoms.Places.Location;

public class InterfaceMainMenu extends GenericInterface
{
	
	boolean hasSave = false;
	boolean loadedSave = false;

	public InterfaceMainMenu(Core newCore)
	{
		super(newCore);
		// TODO Auto-generated constructor stub
		
		id = "InterfaceMainMenu";
	}
	
	//used to pull up the particular screen in question
	public void activate()
	{
		//redraw the wallpaper if we open this page
		//adjust to be dynamic when more main menu screens are completed
		gameModule.redrawWallpaper("fireball.png");
		
		//create a gui container
		newElement = new InterfaceElement(coreModule, "InterfaceContainer", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 720, 1180, 0, 0);
		renderModule.addElement("CenterNode", newElement);
		
		//pull in the savefiles basic data
		/*
		 * We need:
		 * 	Coordinates
		 * 	Planet
		 * 	Map name(From coordinates?)
		 * 	Main char name
		 * 	Main char lvl
		 * 	Money (from main char)
		 * 
		 */
		int x=8;
		int y=4;
		String planet = "Fire";
		String mainName = "No Savefile";
		int mainLvl = 0;
		int mainMoney = 0;
		
		hasSave = false;
		
		String locationName = "ErrorVille";
		boolean isTown = false;
		loadedSave = false;
		
		//pull data from our savefile

		//load in the game save data
		String localDir = Gdx.files.getLocalStoragePath();
		System.out.println(localDir);
		
		System.out.println("");
		System.out.println("Previewing save!");
		
		FileHandle handle = Gdx.files.local(gameModule.savefileName);
		
		if(handle.exists())
		{
			System.out.println("Savefile found!");
			hasSave = true;
			
			String r = handle.readString();
			int startIndex = 0;
			int endIndex = 0;
			
			boolean gotMCN = false;
			boolean gotMCM = false;
			boolean gotMCL = false;
			boolean gotLoc = false;
			boolean pastParty = false;
			
			for(int stringIndex = 0; stringIndex < r.length()&&(!gotMCN || !gotMCM || !gotMCL || !gotLoc); stringIndex++)
			{
				if(r.charAt(stringIndex) == '\n')
				{
					endIndex = stringIndex;
					
					if(endIndex - startIndex <= 0)
						continue;
					
					String partString = r.substring(startIndex , endIndex).trim();
					
					if(partString.length() <= 0)
						continue;
					
					startIndex = stringIndex;
					endIndex = stringIndex;

					//truncate up to the first non tab
					String parts[] = (partString).split("\t");
					
					if(!pastParty && parts[0].equals("[PARTY]"))
					{
						pastParty = true;
					}
					else if(pastParty)
					{
						if(parts[0].equals("[LOCATION]"))
						{
							planet = parts[1];
							x = Integer.parseInt(parts[2]);
							y = Integer.parseInt(parts[3]);
							gotLoc = true;
						}
						else if(parts[0].equals("[STAT]") && !gotMCN && parts.length >= 2 && parts[1].equals("Name"))
						{
							gotMCN = true;
							mainName = parts[2];
						}
						else if(parts[0].equals("[STAT]") && !gotMCM && parts.length >= 2 && parts[1].equals("Money"))
						{
							gotMCM = true;
							mainMoney = Integer.parseInt(parts[2]);
						}
						else if(parts[0].equals("[STAT]") && !gotMCL && parts.length >= 2 && parts[1].equals("Level"))
						{
							gotMCL = true;
							mainLvl = Integer.parseInt(parts[2]);
						}
					}
					
				}
			}
		}
		
		System.out.println("Done loading save preview!");
		System.out.println("");
		//end preprocessing savefile
		
		
		newElement = new InterfaceElement(coreModule, "overview_bg", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 718, 1280, 24, 51);
		renderModule.addElement("InterfaceContainer", newElement);
		newElement = new InterfaceElement(coreModule, "overview_background_bg", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 718, 1280, -24, -50, 1.0f, 1.0f, "interfaces/main_menu/background.png");
		renderModule.addElement("overview_bg", newElement);
		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 700, 70, 62, 425, 2.0f, "Sevestral Kingdoms", renderModule.FONT_IMPACT);
		renderModule.addElement("overview_bg", newElement);
		
		//new game BUTTON
		newElement = new InterfaceElement(coreModule, "new_button", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 546, 130, 0, 344);
		new Gesture("Click", "interface~action~InterfaceMainMenu:new game", newElement);
		renderModule.addElement("InterfaceContainer", newElement);

		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 600, 55, 164, -22, 2.0f, "New Game", renderModule.FONT_IMPACT);
		renderModule.addElement("new_button", newElement);
		
		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 500, 55, 27, 234, 1.3f, "This will delete all data and is irreversable!", renderModule.FONT_IMPACT);
		newElement.setColor(255, 0, 0, 1.0f);
		renderModule.addElement("InterfaceContainer", newElement);
		
		//continue BUTTON
		newElement = new InterfaceElement(coreModule, "continue_button", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 546, 130, 0, 71);
		new Gesture("Click", "interface~action~InterfaceMainMenu:continue", newElement);
		renderModule.addElement("InterfaceContainer", newElement);

		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 600, 55, 101, -22, 2.0f, "Continue Game", renderModule.FONT_IMPACT);
		renderModule.addElement("continue_button", newElement);
		
		//how to play BUTTON
		newElement = new InterfaceElement(coreModule, "how_to_button", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 546, 130, 0, -368);
		new Gesture("Click", "interface~action~InterfaceMainMenu:how to", newElement);
		renderModule.addElement("InterfaceContainer", newElement);

		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 600, 55, 130, -22, 2.0f, "How To Play", renderModule.FONT_IMPACT);
		renderModule.addElement("how_to_button", newElement);
		
		//power BUTTON
		newElement = new InterfaceElement(coreModule, "power_button", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 574, 72, 0, -505);
		new Gesture("Click", "Quit", newElement);//turn the app off
		renderModule.addElement("InterfaceContainer", newElement);
		

		if(gameModule.locationModule.getLocation(planet, x, y)!=null)
		{
			Location l = gameModule.locationModule.getLocation(planet, x, y);
			locationName = l.name;
			if(l.danger_level == 0)
				isTown = true;
		}
		
		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 400, 55, 155, -48, 1.4f, ""+mainName, renderModule.FONT_IMPACT);
		renderModule.addElement("InterfaceContainer", newElement);
		
		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 400, 55, 155, -135, 1.3f, "L"+mainLvl, renderModule.FONT_IMPACT);
		renderModule.addElement("InterfaceContainer", newElement);
		
		newElement = new InterfaceElement(coreModule, "money", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 40, 40, 90, -133, 1.0f, 1.0f, "interfaces/building_shop/money.png");
		coreModule.renderModule.addElement("InterfaceContainer", newElement);
		String mStr = ""+mainMoney;
		if(mainMoney > 10000)
			mStr = ""+(mainMoney/1000)+"k";
		
		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 400, 55, 314, -135, 1.3f, ""+mStr, renderModule.FONT_IMPACT);
		renderModule.addElement("InterfaceContainer", newElement);
		
		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 400, 55, -23, -246, 1.4f, ""+locationName, renderModule.FONT_IMPACT);
		renderModule.addElement("InterfaceContainer", newElement);
		
		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 190, 55, 210, -246, 1.4f, "("+x+","+y+")", renderModule.FONT_IMPACT);
		renderModule.addElement("InterfaceContainer", newElement);
		
		if(!planet.equals("None"))
		{
			newElement = new InterfaceElement(coreModule, "map_image", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 2400, 1200, -157, -87, 1.01f, 1.01f, "map/world/"+planet+".png");
			newElement.generateFrames(150, 150, 5, 0, 128, false);
			newElement.current_frame = (x-1) + (y-1)*16;
			renderModule.addElement("InterfaceContainer", newElement);
			
			if(isTown)
			{
				newElement = new InterfaceElement(coreModule, "map_image2", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 149, 149, 0, 0, 1.01f, 1.01f, "interfaces/world_map/towns.png");
				renderModule.addElement("map_image", newElement);
			}
		}
	}
	
	//given a string of input, it will perform something upon the interface
	public void processInput(String inputString, String additionalInput)
	{
		if(inputString.equals("continue"))
		{
			if(hasSave)
			{				
				gameModule.prepSave();
				
				gameModule.activateInterface("InterfaceMenuOverview");
				
				loadedSave = true;
				
				System.out.println("Continued prior game!");
			}
		}
		else if(inputString.equals("new game"))
		{
			System.out.println("Starting new game!");
		}
		else if(inputString.equals("how to"))
		{
			System.out.println("How to Play");
			System.out.println("\tOpening web browser!");
			Gdx.net.openURI("http://www.sevestral.com");
		}
	}
	
	public boolean hasSavefile()
	{
		return loadedSave;
	}

}
