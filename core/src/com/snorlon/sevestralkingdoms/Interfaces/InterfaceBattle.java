package com.snorlon.sevestralkingdoms.Interfaces;

import java.util.ArrayList;
import java.util.Collections;

import com.snorlon.sevestralkingdoms.GameTools.Core;
import com.snorlon.sevestralkingdoms.GameTools.Gesture;
import com.snorlon.sevestralkingdoms.GameTools.InterfaceElement;
import com.snorlon.sevestralkingdoms.GameTools.Renderer;
import com.snorlon.sevestralkingdoms.NPC.Unit;
import com.snorlon.sevestralkingdoms.Places.Location;

public class InterfaceBattle extends GenericInterface
{
	public static int TURN_NONE = 0;
	public static int TURN_START = 1;
	public static int TURN_END = 20;
	public static int TURN_ESCAPE_SELECT = 32;
	public static int TURN_WAIT = 90;
	
	
	boolean active = false;

	public ArrayList<Unit> units = new ArrayList<Unit>();

	public Unit leaderUnit = null;

	InterfaceElement turnText = null;
	
	public ArrayList<Unit> turnOrder = new ArrayList<Unit>();
	int turnNumber = 1;
	int activeIndex = -1;//subtract one when the current unit dies, increment each "turn"
	boolean turnComplete = true;//true when it's time to step to the next turn
	float turnTimer = 0.0f;//when players turn, handle states, otherwise 0.5s delay when AI attacks
	Unit currentTurnUnit = null;
	
	boolean paused = false;
	
	float turnDelay = 1.0f;
	
	float initialDelay;
	
	int turnState = TURN_START;

	int expGained = 0;//track exp gained off of kills
	int goldGained = 0;//track gold gained off of kills

	public InterfaceBattle(Core newCore)
	{
		super(newCore);
		// TODO Auto-generated constructor stub
		
		id = "InterfaceBattle";
	}
	
	//used to pull up the particular screen in question
	@SuppressWarnings("unchecked")
	public void activate()
	{
		System.out.println("Called");
		
		if(gameModule.partyData.getMainCharacter() == null || gameModule.partyData.getMainCharacter().currentLocation == null)
		{
			return;//can't work with nothing, now can we?
		}
		
		initialDelay = 1.0f;
		
		//preparation stuff//create the terrain container
		newElement = new InterfaceElement(coreModule, "TerrainContainer", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 720, 1180, 0, 0);
		renderModule.addElement("CenterNode", newElement);
		
		//create a gui container
		newElement = new InterfaceElement(coreModule, "MainContainer", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 720, 1180, 0, 0);
		renderModule.addElement("CenterNode", newElement);
		
		//create a map for the battle
		leaderUnit = gameModule.partyData.party_members[0];
		
		
		
		//create a gui container
		newElement = new InterfaceElement(coreModule, "MainContainer", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 720, 1180, 0, 0);
		renderModule.addElement("CenterNode", newElement);
		
		//bg
		newElement = new InterfaceElement(coreModule, "bg", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 718, 1280, 24, 51);
		renderModule.addElement("MainContainer", newElement);
		
		newElement = new InterfaceElement(coreModule, "interface_layer", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 718, 1280, 24, 51);
		renderModule.addElement("MainContainer", newElement);
		
		newElement = new InterfaceElement(coreModule, "battle_map", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 687, 678, 0, 0, 1.0f, 1.0f, "map/battles/"+gameModule.partyData.getMainCharacter().currentLocation.planet+".png");
		renderModule.addElement("bg", newElement);
		
		newElement = new InterfaceElement(coreModule, "morale_bar_bg", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 780, 200, 0, 0, 1.0f, 1.0f, "interfaces/battle/morale bar bg.png");
		renderModule.addElement("bg", newElement);
			
		//activate if inactive
		if(!active)
		{			
			active = true;
			turnDelay = 1.0f;
			
			//create units
			Location loc = gameModule.partyData.getMainCharacter().currentLocation;
			//random spawn count based on difficulty, danger level
			
			int minNum = loc.getMinSpawn();
			int maxNum = loc.getMaxSpawn();
			
			if(minNum >= maxNum)
				minNum = maxNum-1;
			
			Location l = gameModule.partyData.getMainCharacter().currentLocation;
			l.spawns.spawnUnits(coreModule, units, coreModule.random.nextInt(maxNum - minNum) + minNum, loc.level+loc.danger_level, coreModule.random.nextInt(3), l);
			
			//add units, foe and friendly, to turnOrder
			for(Unit e : units)
				turnOrder.add(e);

			for(Unit e : coreModule.gameModule.partyData.party_members)
			{			
				//don't add unused members
				if(e != null)
				{
					//make sure their levels are up to date
					for(int i=0; i<100; i++)
						e.tickExp(500.0f);
					
					turnOrder.add(e);
				}
			}
			
			//generate our turn order
			Collections.sort((ArrayList<Unit>) turnOrder);
			
			//start bgm
			coreModule.soundModule.playMusic("Battle");
		}
		
		
		renderUnits();
		
		//create our turn indicator
		newElement = new InterfaceElement(coreModule, "wait_text_background", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 170, 170, -250, 430, 1.0f, 1.0f, "interfaces/battle/turn_bg.png");
		renderModule.addElement("interface_layer", newElement);
		
		String extra = "";
		
		int val = turnNumber;
		while(val < 1000)
		{
			extra+="0";
			val*=10;
		}
		
		turnText = new InterfaceElement(coreModule, id, "Center", "Center", 240, 50, 79, -56, 1.4f, extra+turnNumber, coreModule.renderModule.FONT_IMPACT);
		renderModule.addElement("wait_text_background", turnText);
		
		//assume we came from turn end, show buttons, nothing else
		newElement = drawButton(1);
		
		newElement = drawButton(3);

		newElement = drawButton(4);
	}
	
	public void Tick(float dt)
	{
		if(active)
		{
			if(initialDelay > 0)
			{
				initialDelay -= dt;
				return;
			}
			
			int enemyAliveCount = 0;
			int playerAliveCount = 0;
			ArrayList<Unit> corpse_cleanup = new ArrayList<Unit>();
			
			//give the active unit a turn
			if(!turnComplete && currentTurnUnit!=null)
			{
				turnComplete = true;
				
				//make our move!
				currentTurnUnit.AI_Attack(turnOrder);
				
				System.out.println("Turn taken!");
			}
			
			//tick each of the enemy units we have
			for(Unit element : turnOrder)
			{
				if(element.faction.equals("Player"))
				{
					boolean success = element.tick(dt, coreModule);
					//if they fail to tick, kill them
					if(element.unit_state == Unit.STATE_ALIVE)
					{
						playerAliveCount++;
						if(!success)
						{
							//make their sprite fade
							corpse_cleanup.add(element);
						}
					}
					else
						playerAliveCount++;
				}
				else
				{
					boolean success = element.tick(dt, coreModule);
					//if they fail to tick, kill them
					if(element.unit_state == Unit.STATE_ALIVE)
					{
						enemyAliveCount++;
						if(!success)
						{
							//make their sprite fade
							
							//add their exp to the total
							expGained += element.calcExpGain();
							
							//update our quests
							int finished = gameModule.progressQuest(element.name);

							//indicate either progress or completion with our mission
							if(finished == 2)
							{
								newElement = new InterfaceElement(coreModule, id, "Center", "Center", 400, 50, 0, -420, 2, "Quest Complete", coreModule.renderModule.FONT_IMPACT);
								newElement.setColor(0, 255, 0, 1);
								newElement.generateTransparencyAnimation(1.0f, 0.25f);
								renderModule.addElement("interface_layer", newElement);
							}
							else if(finished == 1)
							{
								newElement = new InterfaceElement(coreModule, id, "Center", "Center", 400, 50, 0, -420, 2, "Quest Progress", coreModule.renderModule.FONT_IMPACT);
								newElement.setColor(255, 106, 0, 1);
								newElement.generateTransparencyAnimation(1.0f, 0.25f);
								renderModule.addElement("interface_layer", newElement);
							}
							
							
							//calc drops
							int goldAmt = element.getGoldValue();
							System.out.println("Gold dropped: "+goldAmt+"");
							goldGained += goldAmt;
							
							corpse_cleanup.add(element);
						}
					}
					else
						enemyAliveCount++;
				}
			}
			
			//cleanup the dead
			for(Unit element : corpse_cleanup)
			{
				InterfaceElement e = renderModule.getElement(element.imageID+element.imageID+"container");
				if(e!=null)
				{
					e.generateTransparencyAnimation(1.0f, 0.4f);
				}
				
				//renderModule.destroyGroup(element.imageID+"container");
				//attempt to remove from the turn order
				if(activeIndex > turnOrder.indexOf(element))
				{
					activeIndex--;
				}
				else if(activeIndex == turnOrder.indexOf(element))
				{
					activeIndex--;
				}
				turnOrder.remove(element);
			}
			
			corpse_cleanup.clear();
			
			if(activeIndex + 1>=turnOrder.size())
			{
				turnNumber++;
				String extra = "";
				
				int val = turnNumber;
				while(val < 1000)
				{
					extra+="0";
					val*=10;
				}
				
				turnText.updateText(extra+turnNumber);
				
				System.out.println("Advanced to turn "+turnNumber+"!");
				activeIndex = -1;
			}
			
			//if no enemies are alive, the player wins the battle, reflect that
			if(enemyAliveCount==0)
			{
				//show a message for the player having won
				System.out.println("Won due to no enemy units remaining!");
				gameModule.giveInterfaceInput("InterfaceBattleResults", "Winner", "Wild:"+expGained+":"+goldGained);
				gameModule.activateInterface("InterfaceBattleResults");
				resetBattle();
				return;
			}
			
			//otherwise if no players are alive, the player loses the battle, reflect that instead
			else if(playerAliveCount==0)
			{
				//show a message for the player having won
				System.out.println("Lost due to no units remaining!");
				gameModule.giveInterfaceInput("InterfaceBattleResults", "Loser", "Wild:"+expGained+":"+goldGained);
				gameModule.activateInterface("InterfaceBattleResults");
				resetBattle();
				return;
			}
			
			//last possible step is to advance in turn
			else if(turnTimer<=0 && turnComplete)
			{
				//if turn is done, advance to next unit in queue
				//checking for looping of turn already handled
					
				stepActive();

				renderSelectionIcon();
				
				//let them regenerate stamina
				currentTurnUnit.tickStamina();
				
				System.out.println(currentTurnUnit.name+" has recieved a turn!");
				
				//reset turn status
				turnComplete = false;
				
				//check if they're dead and we should skip their turn!
				if(currentTurnUnit.hp <= 0.0f)
					turnComplete = true;
				
				//set the timer again
				turnTimer = turnDelay;
			}
			else if(turnComplete && !paused)
				turnTimer -= dt;
		}
		else//ensure we at least tick ourselves while waiting
		{
			for(Unit element : turnOrder)
			{
				element.tick(dt, coreModule);
			}
		}
	}
	
	public boolean stepActive()
	{
		//if so, move to next index
		activeIndex++;
		
		if(activeIndex >= turnOrder.size())
		{
			activeIndex = 0;
			return true;
		}
		
		//grab its unit
		currentTurnUnit = turnOrder.get(activeIndex);
		
		if(currentTurnUnit.hp <= 0)
			return stepActive();
		
		return false;
	}
	
	public void renderUnits()
	{		
		//add each of them to the scene
		//generate in order of things
		for(int i=3; i>0; i--)
		{
			for(int j=1; j<=5; j++)
			{
				for(Unit element : turnOrder)
				{
					if(element != null && element.x == j && element.y == i)
					{
						element.generateImage(coreModule, renderModule, "bg", element.imageID+"container",2, element.get_draw_x() , element.get_draw_y(), true);
					}
				}
			}
		}
	}
	
	public InterfaceElement drawButton(int buttonID)
	{
		Renderer renderModule = coreModule.renderModule;
		InterfaceElement newElement = null;
		InterfaceElement newElement2 = null;
		
		if(buttonID==1)//attack button
		{
			renderModule.destroyGroup(id+"b1");
			
			newElement = new InterfaceElement(coreModule, "battle_menu_attack", id+"b1", InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 320, 130, -180, 0, 1.0f, 1.0f, "battle/interface/menu_button_blue.png");
			newElement.generateFrames(160, 130, 0, 0, 1, false);
			new Gesture("Click", "interface~action~InterfaceBattle:pause", newElement);
			renderModule.addElement("interface_layer", newElement);
			
			newElement2 = new InterfaceElement(coreModule, id, "Center", "Center", 180, 50, 41, -36, 1.2f, "Pause", renderModule.FONT_IMPACT);
			
			renderModule.addElement("battle_menu_attack", newElement2);
		}
		if(buttonID==2)//attack button
		{
			renderModule.destroyGroup(id+"b1");
			newElement = new InterfaceElement(coreModule, "battle_menu_attack", id+"b1", InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 320, 130, -180, 0, 1.0f, 1.0f, "battle/interface/menu_button_blue.png");
			newElement.generateFrames(160, 130, 0, 0, 1, false);
			new Gesture("Click", "interface~action~InterfaceBattle:pause", newElement);
			renderModule.addElement("interface_layer", newElement);
			
			newElement2 = new InterfaceElement(coreModule, id, "Center", "Center", 180, 50, 25, -36, 1.2f, "Unpause", renderModule.FONT_IMPACT);
			
			renderModule.addElement("battle_menu_attack", newElement2);
		}
		
		else if(buttonID==3)
		{
			renderModule.destroyGroup(id+"b2");
			newElement = new InterfaceElement(coreModule, "battle_menu_tactics", id+"b2", InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 320, 130, 0, 0, 1.0f, 1.0f, "battle/interface/menu_button_green.png");
			newElement.generateFrames(160, 130, 0, 0, 1, false);
			new Gesture("Click", "interface~action~InterfaceBattle:fastforward", newElement);
			renderModule.addElement("interface_layer", newElement);
			
			newElement2 = new InterfaceElement(coreModule, id, "Center", "Center", 180, 50, 43, -36, 1.2f, "Faster", renderModule.FONT_IMPACT);
			renderModule.addElement("battle_menu_tactics", newElement2);
		}
		
		else if(buttonID==5)
		{
			renderModule.destroyGroup(id+"b2");
			newElement = new InterfaceElement(coreModule, "battle_menu_tactics", id+"b2", InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 320, 130, 0, 0, 1.0f, 1.0f, "battle/interface/menu_button_green.png");
			newElement.generateFrames(160, 130, 0, 0, 1, false);
			new Gesture("Click", "interface~action~InterfaceBattle:fastforward", newElement);
			renderModule.addElement("interface_layer", newElement);
			
			newElement2 = new InterfaceElement(coreModule, id, "Center", "Center", 180, 50, 43, -36, 1.2f, "Slower", renderModule.FONT_IMPACT);
			renderModule.addElement("battle_menu_tactics", newElement2);
		}
		
		else if(buttonID==4)
		{
			newElement = new InterfaceElement(coreModule, "battle_menu_escape", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 320, 130, 180, 0, 1.0f, 1.0f, "battle/interface/menu_button_red.png");
			newElement.generateFrames(160, 130, 0, 0, 1, false);
			new Gesture("Click", "interface~action~InterfaceBattle:escape", newElement);
			renderModule.addElement("interface_layer", newElement);
			
			newElement2 = new InterfaceElement(coreModule, id, "Center", "Center", 180, 50, 59, -36, 1.2f, "Run", renderModule.FONT_IMPACT);
			renderModule.addElement("battle_menu_escape", newElement2);
		}

		newElement.recalcEndX();
		newElement.recalcEndY();
		
		return newElement;
	}

	public void destroy()
	{
		super.destroy();
		
		if(!active)
		//only clear our stuff if we're inactive
		{
			units.clear();
		}
	}

	//given a string of input, it will perform something upon the interface
	public void processInput(String inputString, String additionalInput)
	{
		if(inputString.equals("pause"))
		{
			paused = !paused;

			if(paused)
				drawButton(2);
			else
				drawButton(1);
		}
		else if(inputString.equals("escape"))
		{
			System.out.println("Lost due to surrendering!");
			gameModule.giveInterfaceInput("InterfaceBattleResults", "Loser", "Wild:"+expGained+":"+goldGained);
			gameModule.activateInterface("InterfaceBattleResults");
			resetBattle();
		}
		else if(inputString.equals("fastforward"))
		{
			if(turnDelay == 0.05f)
				turnDelay = 0.5f;
			else
				turnDelay = 0.05f;

			if(turnDelay == 0.05f)
				drawButton(5);
			else
				drawButton(3);
		}
	}
	
	public void renderSelectionIcon()
	{
		renderModule.destroyElement("selection_icon");
		if(currentTurnUnit!=null)
		{
			//create our selection icon
			newElement = new InterfaceElement(coreModule, "selection_icon", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 432, 144, currentTurnUnit.get_draw_x(), currentTurnUnit.get_draw_y(), 1.0f, 1.0f, "battle/interface/selection.png");
			newElement.generateFrames(144, 144, 0, 0, 2, false);
			
			if(currentTurnUnit.faction.equals("Player"))
			{
				if(currentTurnUnit == leaderUnit)
					newElement.current_frame = 0;
				else
					newElement.current_frame = 1;
			}
			else
				newElement.current_frame = 2;
			
			renderModule.addElement("interface_layer", newElement);

			newElement.recalcEndX();
			newElement.recalcEndY();
		}
	}
	
	public void resetBattle()
	{
		active = false;
		
		turnOrder.clear();
		
		units.clear();


		leaderUnit = null;

		turnText = null;
		turnNumber = 1;
		activeIndex = -1;//subtract one when the current unit dies, increment each "turn"
		turnComplete = true;//true when it's time to step to the next turn
		turnTimer = 0.0f;//when players turn, handle states, otherwise 0.5s delay when AI attacks
		currentTurnUnit = null;
		
		turnState = TURN_START;

		
		//make sure our units are able to fight!
		for(Unit e : gameModule.partyData.party_members)
		{
			//make sure they have at least one health
			if(e != null)
			{
				e.heal(100);
			}
		}
		
		expGained = 0;
		goldGained = 0;
		
		//clear out the gfx layer we had
		renderModule.purgeGFX();;
	}
}
