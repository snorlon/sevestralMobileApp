package com.snorlon.sevestralkingdoms.GameComponents;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Hashtable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.snorlon.sevestralkingdoms.DataManagement.LoadSaveManager;
import com.snorlon.sevestralkingdoms.DataManagement.SaveSaveManager;
import com.snorlon.sevestralkingdoms.GameTools.Core;
import com.snorlon.sevestralkingdoms.GameTools.InterfaceElement;
import com.snorlon.sevestralkingdoms.Housing.House_Template;
import com.snorlon.sevestralkingdoms.Interfaces.GenericInterface;
import com.snorlon.sevestralkingdoms.Interfaces.InterfaceBattle;
import com.snorlon.sevestralkingdoms.Interfaces.InterfaceBattleResults;
import com.snorlon.sevestralkingdoms.Interfaces.InterfaceBuildingGeneralStoreShop;
import com.snorlon.sevestralkingdoms.Interfaces.InterfaceBuildingGuild;
import com.snorlon.sevestralkingdoms.Interfaces.InterfaceBuildingInn;
import com.snorlon.sevestralkingdoms.Interfaces.InterfaceCommandMissions;
import com.snorlon.sevestralkingdoms.Interfaces.InterfaceCommandOverview;
import com.snorlon.sevestralkingdoms.Interfaces.InterfaceHouse;
import com.snorlon.sevestralkingdoms.Interfaces.InterfaceHouseBuild;
import com.snorlon.sevestralkingdoms.Interfaces.InterfaceInventory;
import com.snorlon.sevestralkingdoms.Interfaces.InterfaceLocalMap;
import com.snorlon.sevestralkingdoms.Interfaces.InterfaceMainMenu;
import com.snorlon.sevestralkingdoms.Interfaces.InterfaceMenuConfirm;
import com.snorlon.sevestralkingdoms.Interfaces.InterfaceMenuOverview;
import com.snorlon.sevestralkingdoms.Interfaces.InterfaceOptions;
import com.snorlon.sevestralkingdoms.Interfaces.InterfacePartyEquipmentDefensive;
import com.snorlon.sevestralkingdoms.Interfaces.InterfacePartyEquipmentOffensive;
import com.snorlon.sevestralkingdoms.Interfaces.InterfacePartyMemberAbilities;
import com.snorlon.sevestralkingdoms.Interfaces.InterfacePartyOverview;
import com.snorlon.sevestralkingdoms.Interfaces.InterfaceQuestDetailed;
import com.snorlon.sevestralkingdoms.Interfaces.InterfaceQuests;
import com.snorlon.sevestralkingdoms.Interfaces.InterfaceTownOverview;
import com.snorlon.sevestralkingdoms.Interfaces.InterfaceViewMap;
import com.snorlon.sevestralkingdoms.Interfaces.InterfaceWorldMap;
import com.snorlon.sevestralkingdoms.Items.Armor;
import com.snorlon.sevestralkingdoms.Items.Bag;
import com.snorlon.sevestralkingdoms.Items.Furniture;
import com.snorlon.sevestralkingdoms.Items.Item;
import com.snorlon.sevestralkingdoms.Items.ItemDatabaseManager;
import com.snorlon.sevestralkingdoms.Items.Spell;
import com.snorlon.sevestralkingdoms.Items.Weapon;
import com.snorlon.sevestralkingdoms.NPC.CreatureDB;
import com.snorlon.sevestralkingdoms.NPC.Unit;
import com.snorlon.sevestralkingdoms.Places.Location;
import com.snorlon.sevestralkingdoms.Places.LocationManager;
import com.snorlon.sevestralkingdoms.Places.Map;
import com.snorlon.sevestralkingdoms.Quests.Quest;
import com.snorlon.sevestralkingdoms.Quests.QuestList;

public class GameModule {
	public final static int STATE_BATTLE = 2;

	public final static int STATE_HELP = 9;
	public final static int STATE_OPTIONS = 10;
	public final static int STATE_QUIT = 11;

	public final static int STATE_MAP = 25;
	
	Core coreModule;

	public Bag playerInventory = null;
	public Bag armyInventory = null;
	
	public LocationManager locationModule;
	public Map currentPlace = null;
	public FashionManager fashionModule = null;
	public ItemDatabaseManager itemLookupModule = null;
	
	public CreatureDB creatureDatabase = new CreatureDB();
	
	public String savefileName = "Game Data/saves/save1.txt";
	
	InterfaceElement fps = null;
	
	public GenericInterface activeInterface = null;
	
	public PartyManager partyData = new PartyManager();
	public QuestList playerQuests = new QuestList(20);
	public QuestList dailyQuests = new QuestList(5);
	
	public Config gameConfig = null;
	
	int filter = Item.ITEM_TYPE_NONE;//Potion represents also Food
	
	Hashtable<String, GenericInterface > gameInterfaces = new Hashtable<String, GenericInterface>();
	
	public Hashtable<String, String > planetNames = new Hashtable<String, String>();
	
	public ArrayList<String> availableLocations = new ArrayList<String>();
	
	public ArrayList<House_Template> houseTemplates = new ArrayList<House_Template>();
	
	public DietyManager dietyModule;
	
	int turn = 1;
	
	boolean saveOverride = true;
	
	public GameModule(Core newCoreModule)
	{
		coreModule = newCoreModule;

		playerInventory = new Bag(coreModule, 90);
		armyInventory = new Bag(coreModule, 300);
		
		fashionModule = new FashionManager();
		
		itemLookupModule = new ItemDatabaseManager(coreModule);

		planetNames.put("Fire", "Pyrash");
		planetNames.put("Water", "Serdralus");
		planetNames.put("Earth", "Terkos");
		planetNames.put("Wind", "Falezia");
		planetNames.put("Time", "Daimina");
		planetNames.put("Space", "Kubredon");
		planetNames.put("Life", "Geminal");
		planetNames.put("Void", "Vorados");

		availableLocations.add("Fire");
		availableLocations.add("Water");
		availableLocations.add("Wind");
		availableLocations.add("Earth");
		availableLocations.add("Void");
	}
	
	public void postInit()
	{
		
		this.gameConfig = new Config(coreModule);
		
		itemLookupModule.load();//load in the game items here!
		
		loadHouses();
		
		fashionModule.init(coreModule);
		
		locationModule = new LocationManager(coreModule);
		
		locationModule.initialize(coreModule);
		
		dietyModule = new DietyManager(coreModule);
		dietyModule.initialize();
		
		partyData.init(coreModule);
		
		//create the game interfaces
		addUI(new InterfaceMainMenu(coreModule));
		addUI(new InterfaceLocalMap(coreModule));
		addUI(new InterfaceWorldMap(coreModule));
		addUI(new InterfaceViewMap(coreModule));
		addUI(new InterfaceMenuOverview(coreModule));
		addUI(new InterfaceMenuConfirm(coreModule));
		addUI(new InterfaceTownOverview(coreModule));
		addUI(new InterfaceBattle(coreModule));
		addUI(new InterfaceBattleResults(coreModule));
		addUI(new InterfaceInventory(coreModule));
		addUI(new InterfaceOptions(coreModule));
		addUI(new InterfaceQuests(coreModule));
		addUI(new InterfaceQuestDetailed(coreModule));
		
		//commandui
		addUI(new InterfaceCommandOverview(coreModule));
		addUI(new InterfaceCommandMissions(coreModule));
		
		//party UIs
		addUI(new InterfacePartyOverview(coreModule));
		addUI(new InterfacePartyMemberAbilities(coreModule));
		addUI(new InterfacePartyEquipmentOffensive(coreModule));
		addUI(new InterfacePartyEquipmentDefensive(coreModule));
		
		//building UIs
		addUI(new InterfaceBuildingGeneralStoreShop(coreModule));
		addUI(new InterfaceBuildingInn(coreModule));
		addUI(new InterfaceBuildingGuild(coreModule));
		addUI(new InterfaceHouse(coreModule));
		addUI(new InterfaceHouseBuild(coreModule));
		
		saveOverride = false;		
		
		/*
		for(int j=0; j<50; j++)
		{
			Item i = itemLookupModule.getRandomItem();
			if(i!=null)
			{
				if(i instanceof Weapon)
				{
					Weapon newItem = new Weapon(i);
					coreModule.gameModule.playerInventory.moveItem(newItem);
				}
				else if(i instanceof Spell)
				{
					Spell newItem = new Spell(i);
					coreModule.gameModule.playerInventory.moveItem(newItem);
				}
				else if(i instanceof Armor)
				{
					Armor newItem = new Armor(i);
					coreModule.gameModule.playerInventory.moveItem(newItem);
				}
				else if(i instanceof Furniture)
				{
				}
				else
				{
					Item newItem = new Item(i);
					coreModule.gameModule.playerInventory.moveItem(newItem);
				}
			}
		}*/
	}
	
	public void prepSave()
	{
		//called to do stuff to load the savefile
		//load in our save data last!
		loadSave();
		
		locationModule.postInit();
		
		for(int i=0; i<1; i++)
		{
			//playerInventory.moveItem(itemLookupModule.generateItem("All"));
		}
		
		redrawWallpaper();
		
		//generate a new unit ONLY if we have no units after loading
		if(partyData.unitCount() <= 0)
		{
			Unit newUnit = null;
			
			newUnit = new Unit(coreModule, "Blazlin", 0, creatureDatabase.database);
			newUnit.initialize(10, 2, 2);
			newUnit.faction = "Player";
			partyData.addUnit(newUnit);
			newUnit.setMoney(5000);
			
			newUnit = new Unit(coreModule, "Crustlin", 0, creatureDatabase.database);
			newUnit.initialize(5, 3, 2);
			newUnit.faction = "Player";
			partyData.addUnit(newUnit);
			
			newUnit = new Unit(coreModule, "Wattlin", 0, creatureDatabase.database);
			newUnit.initialize(5, 3, 3);
			newUnit.faction = "Player";
			partyData.addUnit(newUnit);
			
			newUnit = new Unit(coreModule, "Gustlin", 0, creatureDatabase.database);
			newUnit.initialize(5, 4, 2);
			newUnit.faction = "Player";
			partyData.addUnit(newUnit);
			
			travelLocation("Wind",8,6, true);
		}
	}

	public void redrawWallpaper()
	{
		redrawWallpaper(gameConfig.backgroundImage);
	}
	
	public void redrawWallpaper(String wpaper)
	{
		//clear our wallpaper
		coreModule.renderModule.destroyElement("BackgroundImage");
		coreModule.renderModule.destroyElement("BackgroundFPS");
		
		//give it our wallpaper
		InterfaceElement newElement = new InterfaceElement(coreModule, "BackgroundImage", "NONE", InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 1500, 1500, 0, 0, 1.0f, 1.0f, "wallpapers/"+wpaper);
		
		coreModule.renderModule.addElement("CenterNode", newElement);
		
		//draw FPS
		fps = new InterfaceElement(coreModule, "BackgroundFPS", "Center", "Center", 300, 55, 200, 600, 1.0f, ""+coreModule.renderModule.lastFrame, coreModule.renderModule.FONT_IMPACT);
		coreModule.renderModule.addElement("CenterNode", fps);
	}
	
	public void addUI(GenericInterface newUI)
	{
		gameInterfaces.put(newUI.getID(), newUI);
	}
	
	public void activateInterface(String id)
	{
		//clear all particles
		//if(!saveParticles)
			//coreModule.particleModule.clear();
		
		//destroy all other interfaces
		for(String key: gameInterfaces.keySet())
		{
			gameInterfaces.get(key).destroy();
		}
		
		activeInterface = null;
		
		if(gameInterfaces.containsKey(id))
		{
			gameInterfaces.get(id).activate();
			activeInterface = gameInterfaces.get(id);
		}
	}
	
	public void deactivateInterface(String id)
	{
		currentPlace = null;//break this link if we're loading an interface!
		
		if(gameInterfaces.containsKey(id))
		{
			gameInterfaces.get(id).destroy();
		}
	}
	
	public GenericInterface getInterface(String id)
	{
		currentPlace = null;//break this link if we're loading an interface!
		
		if(gameInterfaces.containsKey(id))
		{
			return gameInterfaces.get(id);
		}
		
		return null;
	}
	
	public void giveInterfaceInput(String id, String input, String input2)
	{
		if(gameInterfaces.containsKey(id))
		{
			gameInterfaces.get(id).processInput(input, input2);
		}
	}
	
	public void loadSave()
	{
		//load in the game save data
		String localDir = Gdx.files.getLocalStoragePath();
		System.out.println(localDir);
		
		System.out.println("");
		System.out.println("Loading save!");
		
		FileHandle handle = Gdx.files.local(savefileName);
		
		if(!handle.exists())
		{
			//create it!
			PrintWriter out = new PrintWriter(handle.write(false));
			out.write("");
			System.out.println("No such save found, creating it!");
		}
		
		String r = handle.readString();
		int startIndex = 0;
		int endIndex = 0;
		
		for(int stringIndex = 0; stringIndex < r.length(); stringIndex++)
		{
			if(r.charAt(stringIndex) == '\n')
			{
				endIndex = stringIndex;
				
				if(endIndex - startIndex <= 0)
					continue;
				
				String partString = r.substring(startIndex , endIndex).trim();
				String remainder = r.substring(endIndex, r.length());
				
				if(partString.length() <= 0)
					continue;
				
				startIndex = stringIndex;
				endIndex = stringIndex;

				//truncate up to the first non tab
				String parts[] = (partString).split("\t");
				
				if(parts[0].equals("[SAVEFILE]"))
				{
					stringIndex = LoadSaveManager.processSave(remainder, coreModule) + stringIndex;
				}
			}
		}
		
		System.out.println("Done loading save!");
		System.out.println("");
	}
	
	public void saveSave()
	{		
		if(((InterfaceMainMenu) getInterface("InterfaceMainMenu")).hasSavefile())
		{
			//overwrite old file
			FileHandle handle = Gdx.files.local(savefileName);
			
			PrintWriter out = new PrintWriter(handle.write(false));
			
			out.write("[SAVEFILE]\n");
			
			//print out the save name
			SaveSaveManager.printTabs(out, 1);
			out.write("[STAT]\tSave 1\n\n");
			
			
			
			SaveSaveManager.processSave(out, 1, coreModule);
			
			out.write("[/SAVEFILE]");
			
			out.flush();
			out.close();
		}
	}
	
	public void tick(float dt)
	{
		//update fps
		if(fps!=null)
			fps.updateText("FPS: "+coreModule.renderModule.lastFrame);
		
		gameConfig.processFPS(fps);
		
		//tick our party manager
		partyData.tick(dt);
		
		if(currentPlace != null)
			currentPlace.Tick(dt);
		

		//tick all interfaces
		//for(String key: gameInterfaces.keySet())
		if(activeInterface!=null)
		{
		//	gameInterfaces.get(key).Tick(dt);
			activeInterface.Tick(dt);
		}
	}
	
	//this checks if you can travel or not
	public void travelLocation(String planet, int x, int y, boolean forced)
	{
		boolean valid = true;
		
		if(forced || valid)
		{
			//check if the map we gave exists
			Location loc = locationModule.getLocation(planet, x, y);
			
			if(loc!=null)
			{
				//make it our current map!
				//update all party members
				for(Unit u : partyData.getPartyMembers())
				{
					if(u!=null)
						u.currentLocation = loc;
				}
				
				//make sure to update the turn
				stepTurn();
				
				//reload our menu
				coreModule.gameModule.activateInterface("InterfaceMenuOverview");
			}
		}
	}
	
	public void setTurn(int newTurn)
	{
		turn = newTurn;
	}
	
	public int getTurn()
	{
		return turn;
	}
	
	//force transition in stuff
	public void stepTurn()
	{
		if(saveOverride)
			return;
		
		//allow map changes to occur
		
		//allow factions to take action
		
		//give every unit a chance to act except the players
		partyData.stepTurn();
		
		//update all of the locations
		locationModule.stepTurn();
		
		//let the dieties move around as they see fit
		dietyModule.stepTurn();
		
		turn++;
		if(turn > 999999999)
			turn = 1;//overflow protection
	}
	
	public void loadHouses()
	{
		//load up all house templates up to level 99 (for now), up if/when more than that is available

		System.out.println("Loading house templates!");
		
		String name = "Error";
		int width = 4;
		int height = 4;
		int objectLimit = 16;
		int doorIndex = 0;
		int wallIndex = 0;
		int floorIndex = 20;
		int floorCount = 2;
		double newValue = 1.0;
				
		FileHandle handle = Gdx.files.internal("housing/templates/houses.house");
		
		String r = handle.readString();
		String parameters[] = r.split("\n");
		
		int houseNum = 0;
		
		//run through all parameters
		for(int index=0; index<parameters.length; index++)
		{
			String parts[] = parameters[index].split("\t");
			
			if(parts[0].equals("Name:"))
			{
				name = parts[1];
			}
			else if(parts[0].equals("Floor Index:"))
			{
				floorIndex = Integer.parseInt(parts[1].trim());
			}
			else if(parts[0].equals("Wall Index:"))
			{
				wallIndex = Integer.parseInt(parts[1].trim());
			}
			else if(parts[0].equals("Door Index:"))
			{
				doorIndex = Integer.parseInt(parts[1].trim());
			}
			else if(parts[0].equals("Width:"))
			{
				width = Integer.parseInt(parts[1].trim());
			}
			else if(parts[0].equals("Height:"))
			{
				height = Integer.parseInt(parts[1].trim());
			}
			else if(parts[0].equals("Object Limit:"))
			{
				objectLimit = Integer.parseInt(parts[1].trim());
			}
			else if(parts[0].equals("Floor Count:"))
			{
				floorCount = Integer.parseInt(parts[1].trim());
			}
			else if(parts[0].equals("Floor Count:"))
			{
				floorCount = Integer.parseInt(parts[1].trim());
			}
			else if(parts[0].equals("Value:"))
			{
				newValue = Double.parseDouble(parts[1].trim());
			}
			else if(parts[0].equals("[/NEW HOUSE]"))
			{
				House_Template newTemplate = new House_Template(name, width, height, objectLimit, doorIndex, wallIndex, floorIndex, newValue, houseNum, floorCount);
				
				houseTemplates.add(newTemplate);
				
				System.out.println("Done loading house "+name+".");
				
				houseNum++;
			}
		}
		
		System.out.println("Done loading house templates.");
	}
	
	public void deleteSave()
	{
		if(Gdx.files.local(savefileName).exists())
		{
			Gdx.files.local(savefileName).delete();
		}
	}

	public int progressQuest(String name)
	{
		int returnVal = 0;
		
		for(Quest q : playerQuests.getList())
		{
			if(!q.isComplete() && q.validTarget(name))
			{
				q.incQty();
				
				if(q.isComplete())
					returnVal = 2;
				else
					returnVal = Math.max(returnVal, 1);
			}
		}
		for(Quest q : dailyQuests.getList())
		{
			if(!q.isComplete() && q.validTarget(name))
			{
				q.incQty();
				
				if(q.isComplete())
					returnVal = 2;
				else
					returnVal = Math.max(returnVal, 1);
			}
		}
		
		return returnVal;
	}

	public boolean hasQuest(String name)
	{
		
		//search through our quests for a creature
		for(Quest q : playerQuests.getList())
		{
			if(q.validTarget(name))
				return true;
		}
		
		for(Quest q : dailyQuests.getList())
		{
			if(q.validTarget(name))
				return true;
		}
		
		return false;
	}
}
