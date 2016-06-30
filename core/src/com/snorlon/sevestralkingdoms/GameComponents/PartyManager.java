package com.snorlon.sevestralkingdoms.GameComponents;

import java.util.ArrayList;

import com.snorlon.sevestralkingdoms.Ability.Ability;
import com.snorlon.sevestralkingdoms.GameTools.Core;
import com.snorlon.sevestralkingdoms.GameTools.InterfaceElement;
import com.snorlon.sevestralkingdoms.GameTools.Renderer;
import com.snorlon.sevestralkingdoms.NPC.Unit;

public class PartyManager
{
	public final static int PARTY_OVERVIEW_SWAP = 1;
	
	public Unit party_members[] = {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null};
	
	Unit main_character = null;//this is effectively the player's character
	private Ability current_ability = null;
	
	//for display purposes
	int selectedUnit = 1;//index of selected unit
	
	Core coreModule;
	
	int state = PARTY_OVERVIEW_SWAP;
	
	public PartyManager()
	{
		
	}
	
	public void init(Core core)
	{
		coreModule = core;
	}
	
	public Unit getMainCharacter()
	{
		return main_character;
	}
	
	public Unit getUnit(int index)
	{		
		return party_members[index];
	}
	
	public void rollPosition(Unit newUnit)
	{
		//find them a good position
		int x = 1;
		int y = 1;
		
		ArrayList<String> positions = new ArrayList<String>();
		ArrayList<String> invalid = new ArrayList<String>();
		
		for(int j=1; j<=5; j++)
		{
			for(int k=1; k<=3; k++)
			{
				positions.add(j+","+k);
			}
		}
		
		for(Unit u : party_members)
		{
			if(u!=null)
			{
				for(String s : positions)
				{
					if(s.equals(u.x+","+u.y))
					{
						invalid.add(s);
					}
				}
			}
		}
		
		for(String s : invalid)
		{
			positions.remove(s);
		}
		
		invalid.clear();
		
		//randomly pick a location
		String roll = positions.get(coreModule.random.nextInt(positions.size()));
		String[] values = roll.split(",");

		x = Integer.parseInt(values[0]);
		y = Integer.parseInt(values[1]);
		
		newUnit.x = x;
		newUnit.y = y;
		
		System.out.println(x+","+y+" has been rolled for "+newUnit.name+"\n\n\n\n\n\n\n\n\n\n");
	}
	
	public boolean addUnit(Unit newUnit)
	{
		//abort if no room
		if(countFree() <= 0)
			return false;
		
		//add them to the first open spot, if any, if their desired spot is not available
		for(int i=0; i<15; i++)
		{
			if(party_members[i] == null)
			{
				party_members[i] = newUnit;

				newUnit.Strength = Math.round(newUnit.Strength*10)/10.0;
				newUnit.Toughness = Math.round(newUnit.Toughness*10)/10.0;
				newUnit.Intelligence = Math.round(newUnit.Intelligence*10)/10.0;
				newUnit.Willpower = Math.round(newUnit.Willpower*10)/10.0;
				newUnit.Agility = Math.round(newUnit.Agility*10)/10.0;
				newUnit.Dexterity = Math.round(newUnit.Dexterity*10)/10.0;
				newUnit.Luck = Math.round(newUnit.Luck*10)/10.0;
				newUnit.Charisma = Math.round(newUnit.Charisma*10)/10.0;
				
				newUnit.faction = Common.FACTION_PLAYER;
				
				if(main_character == null)
					main_character = newUnit;
				
				return true;
			}
		}
		
		return false;//return false if failed to add
	}
	
	//input: string of raw data for party loading
	//turns data into current party members with abilities, equipment, stats, etc
	public void loadPartyData(String input)
	{
		/*FORMAT
		 * First Unit is mainCharacter
		 * 
		 * [PARTY]
		 * 		[UNIT]
		 * 			[STAT]	Level 99
		 * 			[STAT]	Strength 21
		 * 		[/UNIT]
		 * 		[UNIT]
		 * 			[STAT]	Level 99
		 * 			[STAT]	Strength 21
		 * 			[STAT]	Slot	5,3
		 * 			[EQUIP]
		 * 				[STAT]	DmgFormula 6
		 * 				[STAT]	Strength 51
		 * 			[/EQUIP]
		 * 			[ABILITY]
		 * 				[STAT]	Damage	1.5f
		 * 				[STAT]	Type1	Fire
		 * 			[/ABILITY]
		 * 		[/UNIT]
		 * [/PARTY]
		 * 
		 */
		
		//process it
	}
	
	//input: string of raw data for party loading
	//turns data into current party members with abilities, equipment, stats, etc
	public String savePartyData()
	{
		//generate a string of data to save to the file, this is our own special string only we touch
		String returnString = "";
		
		return returnString;
	}
	
	public int unitCount()
	{
		//count each non-null
		int count = 0;
		
		for(int i=0; i<15; i++)
		{
			if(party_members[i] != null)
				count++;
		}
		
		return count;
	}
	
	public int countFree()
	{
		return (15 - unitCount());
	}
	
	public boolean spotFree(int x, int y)
	{
		if(x - 1 + (5*y) > 14)
			return false;
		
		if(x - 1 + (5*y) < 0)
			return false;
		
		if(party_members[x - 1 + (5*y)] != null)
			return false;
		
		return true;
	}
	
	public void change_state(int newState)
	{
		state = newState;
		
		rebuild();//it's assumed we should rebuild when we change state
	}

	public void rebuild()
	{
		Renderer renderModule = coreModule.renderModule;
		InterfaceElement newElement = null;
		
		//purge the party
		renderModule.destroyGroup("Party");
		

		Unit currUnit = null;
		
		if(party_members[selectedUnit-1]!=null)
			currUnit = party_members[selectedUnit-1];
		else
			return;
		
		coreModule.gameModule.activateInterface("");
		
	}

	public void tick(float dt)
	{
	}
	
	void selectDisplayUnit(Unit u)
	{
		//find the index that corresponds to them
		for(int i=0; i<15; i++)
		{
			if(party_members[i] != null)
			{
				if(party_members[i] == main_character)
				{
					selectDisplayUnit(i + 1);
				}
			}
		}
	}
	
	public void selectUnit(int index)
	{
		if(index < 1 || index > 15)
			return;

		selectedUnit = index;
	}
	
	public void selectDisplayUnit(int index)
	{
		selectUnit(index);
	}
	
	public void selectAbility(int index)
	{
		Unit currUnit = null;
		
		if(party_members[selectedUnit-1]!=null)
			currUnit = party_members[selectedUnit-1];
		else
			return;
		
		//attempt to set the ability
		if(currUnit!=null)
			if(index < currUnit.attacks.size() && index >= 0)
				setCurrentAbility(currUnit.attacks.get(index));
	}
	
	public void swapUnit(int x, int y)
	{
		Unit currUnit = null;
		
		if(party_members[selectedUnit-1]!=null)
			currUnit = party_members[selectedUnit-1];
		else
		{
			return;
		}
		
		//check if someone else is there
		for(int i=0; i<15; i++)
		{
			//if so, swap positions and return
			if(party_members[i] != null)
			{
				if(party_members[i].x == x && party_members[i].y == y)
				{
					party_members[i].x = currUnit.x;
					party_members[i].y = currUnit.y;
					
					currUnit.x = x;
					currUnit.y = y;
					
					coreModule.gameModule.activateInterface("InterfacePartyOverview");
					
					return;
				}
			}
		}
		
		//otherwise, just set our position to it and reload the screen
		currUnit.x = x;
		currUnit.y = y;

		coreModule.gameModule.activateInterface("InterfacePartyOverview");
	}
	
	public void cancelSwap()
	{
		
		//destroy all swap elements
		for(int y=1; y<=3; y++)
		{
			for(int x=1; x<=5; x++)
			{
				coreModule.renderModule.destroyGroup("Party-Swap");
			}
		}

		coreModule.gameModule.activateInterface("InterfacePartyOverview");
	}

	public Ability getCurrentAbility() {
		return current_ability;
	}

	public void setCurrentAbility(Ability current_ability) {
		this.current_ability = current_ability;
	}

	public Unit[] getPartyMembers()
	{
		return party_members;
	}

	public int getSelectedUnit()
	{
		return selectedUnit;
	}
	
	public Unit getCurrentUnit()
	{

		Unit currUnit = null;
		
		if(party_members[selectedUnit-1]!=null)
			currUnit = party_members[selectedUnit-1];
		
		//abort if it's no good
		if(currUnit == null)
			return null;
		
		return currUnit;
	}

	public void stepTurn()
	{
		for(Unit u : party_members)
		{
			if(u!=null)
			{
				System.out.println(u.name+" acting!");
				u.stepTurn(u == getMainCharacter());
			}
		}
	}
}
