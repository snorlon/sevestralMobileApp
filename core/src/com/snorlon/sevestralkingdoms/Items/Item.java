package com.snorlon.sevestralkingdoms.Items;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;

import com.snorlon.sevestralkingdoms.GameComponents.Common;
import com.snorlon.sevestralkingdoms.GameTools.Core;
import com.snorlon.sevestralkingdoms.GameTools.Gesture;
import com.snorlon.sevestralkingdoms.GameTools.InterfaceElement;
import com.snorlon.sevestralkingdoms.GameTools.Renderer;
import com.snorlon.sevestralkingdoms.NPC.Unit;

public class Item
{	
	public final static int ITEM_TYPE_NONE = 0;
	public final static int ITEM_TYPE_FOOD = 1;
	public final static int ITEM_TYPE_COSTUME = 2;
	public final static int ITEM_TYPE_POTION = 3;
	public final static int ITEM_TYPE_FURNITURE = 4;
	public final static int ITEM_TYPE_WEAPON = 5;
	public final static int ITEM_TYPE_SPELL = 6;
	public final static int ITEM_TYPE_ARMOR = 7;

	public final static int ITEM_EFFECT_NONE = 0;
	public final static int ITEM_EFFECT_HEAL_HP = 1;
	public final static int ITEM_EFFECT_STAT_STRENGTH = 2;
	public final static int ITEM_EFFECT_STAT_TOUGHNESS = 3;
	public final static int ITEM_EFFECT_STAT_INTELLIGENCE = 4;
	public final static int ITEM_EFFECT_STAT_WILLPOWER = 5;
	public final static int ITEM_EFFECT_STAT_AGILITY = 6;
	public final static int ITEM_EFFECT_STAT_DEXTERITY = 7;
	public final static int ITEM_EFFECT_STAT_CHARISMA = 8;
	public final static int ITEM_EFFECT_STAT_LUCK = 9;
	public final static int ITEM_EFFECT_STAT_ALL = 10;

	public final static int ITEM_EFFECT_FOOD_COMMON = 11;
	public final static int ITEM_EFFECT_FOOD_UNCOMMON = 12;
	public final static int ITEM_EFFECT_FOOD_RARE = 13;
	public final static int ITEM_EFFECT_FOOD_LEGENDARY = 14;
	public final static int ITEM_EFFECT_FOOD_UNIQUE = 15;

	public final static int ITEM_STAT_NONE = 0;
	public final static int ITEM_STAT_STRENGTH = 1;
	public final static int ITEM_STAT_TOUGHNESS = 2;
	public final static int ITEM_STAT_AGILITY = 3;
	public final static int ITEM_STAT_DEXTERITY = 4;
	public final static int ITEM_STAT_INTELLIGENCE = 5;
	public final static int ITEM_STAT_WILLPOWER = 6;
	public final static int ITEM_STAT_CHARISMA = 7;
	public final static int ITEM_STAT_LUCK = 8;

	public final static int RARITY_COMMON = 0;
	public final static int RARITY_UNCOMMON = 1;
	public final static int RARITY_SCARCE = 2;
	public final static int RARITY_RARE = 3;
	public final static int RARITY_UNIQUE = 4;
	public final static int RARITY_TREASURE = 5;
	public final static int RARITY_LEGENDARY = 6;
	public final static int RARITY_ANCIENT = 7;
	public final static int RARITY_RELIC = 8;
	public final static int RARITY_DIVINE_RELIC = 9;
	public final static int RARITY_GODLIKE = 10;
	
	public String name;
	
	public int type = Common.ITEM_MISC;
	
	public int base_value = 0;
	
	int rarity = RARITY_COMMON;

	boolean equipped = false;//may not be used
	public boolean isTemplate = false;
	int effect_id;//for things like potions and other one-use things
	
	public String imageID;
	public int imagePath = 0;
	
	//constructor for one-use items, no stats by default
	public Item(String nname, int pathIndex, int ntype, int neffect, int baseValue)
	{
		Random r = new Random();
		name = nname;
		
		imageID = name + r.nextInt();
		
		type = ntype;
		effect_id = neffect;
		
		imagePath = pathIndex;
		
		base_value = baseValue;
	}
	
	public Item(String nname, int pathIndex, int ntype, int neffect, int baseValue, boolean template)
	{
		this(nname, pathIndex, ntype, neffect, baseValue);
		
		isTemplate = true;
	}
	

	
	public Item(Item template)
	{
		//generate a new item based on what we got passed
		Random r = new Random();
		name = template.name;
		
		rarity = template.rarity;
		
		imageID = name + r.nextInt();
		
		equipped = template.equipped;//may not be used
		
		base_value = template.base_value;
		
		type = template.type;
		effect_id = template.effect_id;
		
		imagePath = template.imagePath;
		
	}
	
	public static String itemTypeToString(int value)
	{
		//return depending on what the string says the craft is
		if(value == ITEM_TYPE_COSTUME)
		{
			return "Costume";
		}
		else if(value == ITEM_TYPE_POTION)
		{
			return "Potion";
		}
		else if(value == ITEM_TYPE_FURNITURE)
		{
			return "Furniture";
		}
		else if(value == ITEM_TYPE_ARMOR)
		{
			return "Armor";
		}
		else if(value == ITEM_TYPE_WEAPON)
		{
			return "Weapon";
		}
		else if(value == ITEM_TYPE_SPELL)
		{
			return "Spell";
		}
		else if(value == ITEM_TYPE_FOOD)
		{
			return "Food";
		}
		
		return "None";
	}
	
	public static int stringToItemType(String value)
	{
		//return depending on what the string says the craft is
		if(value.equals("Costume"))
		{
			return ITEM_TYPE_COSTUME;
		}
		else if(value.equals("Potion"))
		{
			return ITEM_TYPE_POTION;
		}
		else if(value.equals("Furniture"))
		{
			return ITEM_TYPE_FURNITURE;
		}
		else if(value.equals("Armor"))
		{
			return ITEM_TYPE_ARMOR;
		}
		else if(value.equals("Weapon"))
		{
			return ITEM_TYPE_WEAPON;
		}
		else if(value.equals("Spell"))
		{
			return ITEM_TYPE_SPELL;
		}
		else if(value.equals("Food"))
		{
			return ITEM_TYPE_FOOD;
		}
		
		return ITEM_TYPE_NONE;
	}

	public static int stringToItemEffect(String value)
	{
		//return depending on what the string says the effect is
		if(value.equals("Heal HP"))
		{
			return ITEM_EFFECT_HEAL_HP;
		}
		else if(value.equals("Strength"))
		{
			return ITEM_EFFECT_STAT_STRENGTH;
		}
		else if(value.equals("Toughness"))
		{
			return ITEM_EFFECT_STAT_TOUGHNESS;
		}
		else if(value.equals("Intelligence"))
		{
			return ITEM_EFFECT_STAT_INTELLIGENCE;
		}
		else if(value.equals("Willpower"))
		{
			return ITEM_EFFECT_STAT_WILLPOWER;
		}
		else if(value.equals("Agility"))
		{
			return ITEM_EFFECT_STAT_AGILITY;
		}
		else if(value.equals("Dexterity"))
		{
			return ITEM_EFFECT_STAT_DEXTERITY;
		}
		else if(value.equals("Charisma"))
		{
			return ITEM_EFFECT_STAT_CHARISMA;
		}
		else if(value.equals("Luck"))
		{
			return ITEM_EFFECT_STAT_LUCK;
		}
		else if(value.equals("All Stats"))
		{
			return ITEM_EFFECT_STAT_ALL;
		}
		
		return ITEM_EFFECT_NONE;
	}

	public static String itemEffectDesc(int value)
	{
		//return depending on what the string says the effect is
		switch(value)
		{
			case ITEM_EFFECT_HEAL_HP:
				return "Heals 10% HP";
			case ITEM_EFFECT_STAT_STRENGTH:
				return "+Base Str";
			case ITEM_EFFECT_STAT_TOUGHNESS:
				return "+Base Tou";
			case ITEM_EFFECT_STAT_AGILITY:
				return "+Base Agi";
			case ITEM_EFFECT_STAT_DEXTERITY:
				return "+Base Dex";
			case ITEM_EFFECT_STAT_INTELLIGENCE:
				return "+Base Int";
			case ITEM_EFFECT_STAT_WILLPOWER:
				return "+Base Wil";
			case ITEM_EFFECT_STAT_CHARISMA:
				return "+Base Cha";
			case ITEM_EFFECT_STAT_LUCK:
				return "+Base Luk";
			case ITEM_EFFECT_STAT_ALL:
				return "+Base ALL";
			case ITEM_EFFECT_FOOD_COMMON:
				return "+5 Morale";
			case ITEM_EFFECT_FOOD_UNCOMMON:
				return "+10 Morale";
			case ITEM_EFFECT_FOOD_RARE:
				return "+15 Morale";
			case ITEM_EFFECT_FOOD_LEGENDARY:
				return "+20 Morale";
			case ITEM_EFFECT_FOOD_UNIQUE:
				return "+25 Morale";
		}
		
		return "";
	}

	public static int stringToItemStat(String value)
	{
		//return depending on what the string says the effect is
		if(value.equals("Strength"))
		{
			return ITEM_EFFECT_STAT_STRENGTH;
		}
		else if(value.equals("Toughness"))
		{
			return ITEM_EFFECT_STAT_TOUGHNESS;
		}
		else if(value.equals("Agility"))
		{
			return ITEM_EFFECT_STAT_AGILITY;
		}
		else if(value.equals("Dexterity"))
		{
			return ITEM_EFFECT_STAT_DEXTERITY;
		}
		else if(value.equals("Intelligence"))
		{
			return ITEM_EFFECT_STAT_INTELLIGENCE;
		}
		else if(value.equals("Willpower"))
		{
			return ITEM_EFFECT_STAT_WILLPOWER;
		}
		else if(value.equals("Charisma"))
		{
			return ITEM_EFFECT_STAT_CHARISMA;
		}
		else if(value.equals("Luck"))
		{
			return ITEM_EFFECT_STAT_LUCK;
		}
		
		return ITEM_STAT_NONE;
	}
	
	public static String getItemEffectString(int effectType)
	{
		switch(effectType)
		{
			case ITEM_EFFECT_HEAL_HP:
				return "Error";
			case ITEM_EFFECT_STAT_STRENGTH:
				return "+1 Base Strength";
			case ITEM_EFFECT_STAT_TOUGHNESS:
				return "+1 Base Toughness";
			case ITEM_EFFECT_STAT_INTELLIGENCE:
				return "+1 Base Intelligence";
			case ITEM_EFFECT_STAT_WILLPOWER:
				return "+1 Base Willpower";
			case ITEM_EFFECT_STAT_AGILITY:
				return "+1 Base Agility";
			case ITEM_EFFECT_STAT_DEXTERITY:
				return "+1 Base Dexterity";
			case ITEM_EFFECT_STAT_CHARISMA:
				return "+1 Base Charisma";
			case ITEM_EFFECT_STAT_LUCK:
				return "+1 Base Luck";
			case ITEM_EFFECT_STAT_ALL:
				return "+1 to Base of all stats";
			case ITEM_EFFECT_FOOD_COMMON:
				return "+5 Morale";
			case ITEM_EFFECT_FOOD_UNCOMMON:
				return "+10 Morale";
			case ITEM_EFFECT_FOOD_RARE:
				return "+15 Morale";
			case ITEM_EFFECT_FOOD_LEGENDARY:
				return "+20 Morale";
			case ITEM_EFFECT_FOOD_UNIQUE:
				return "+25 Morale";
		}

		return "None";
	}
	
	public boolean equals(Item comparison)
	{
		//Enhance for equipment later
		if(name.equals(comparison.name) && type==comparison.type)
		{
			return true;
		}
		
		return false;
	}
	
	public void equip(Unit target)
	{
		//by default do nothing
	}
	
	public boolean isWearable()
	{
		return false;
	}
	
	public String getSlot()
	{
		return "None";
	}

	public int getEffect()
	{
		if(type == Item.ITEM_TYPE_FOOD)
		{
			return ITEM_EFFECT_FOOD_COMMON+rarity;
		}
		else
			return effect_id;
	}
	
	public void setRank(int value)
	{
		rarity = value;
	}
	
	public void save(PrintWriter out, int layer, Core coreModule)
	{
		//print out tabs for every layer
		for(int i=0; i<layer; i++)
		{
			out.write('\t');
		}
		out.write("[ITEM]\t"+name+"\n");
	}

	public int getRank()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	public int getItemType() {
		// TODO Auto-generated method stub
		return type;
	}
	
	public InterfaceElement generateIcon(Core coreModule, String baseID, String id, String target, int x, int y)
	{
		return generateIcon(coreModule, baseID, id, target, x, y, false);
	}

	public InterfaceElement generateIcon(Core coreModule, String baseID, String id, String target, int x, int y, boolean hideUI)
	{
		Renderer renderModule = coreModule.renderModule;
		
		Item i = this;
		
		InterfaceElement rnewElement = new InterfaceElement(coreModule, baseID, id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center",	96, 96, x,y);
		renderModule.addElement(target, rnewElement);
		
		InterfaceElement newElement = new InterfaceElement(coreModule, baseID+"m", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center",	640, 640, 0, 0, 2.0f, 2.0f, "items/items.png");
		newElement.generateFrames(32, 32, 0, i.imagePath, 400, false);
		renderModule.addElement(baseID, newElement);
		
		if(hideUI)
			return rnewElement;
		
		newElement = new InterfaceElement(coreModule, baseID+"y", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center",	200, 60, -34, -34, 1.0f, 1.0f, "interfaces/inventory/accents/miniicons.png");
		newElement.generateFrames(20, 20, 0, i.getItemType()+10, 30, false);
		renderModule.addElement(baseID, newElement);
		
		if(i.getRank() > 0 && i.getRank() <= 10)
		{
			newElement = new InterfaceElement(coreModule, baseID+"r", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center",	960, 96, 0, 0, 1.0f, 1.0f, "interfaces/inventory/accents/rarity.png");
			newElement.generateFrames(96, 96, 0, i.getRank()-1, 10, false);
			renderModule.addElement(baseID, newElement);
			
			if(i instanceof Spell || i instanceof Weapon || i instanceof Armor)
			{
				int t1 = -1;
				int t2 = -1;
				
				//ascertain our type from the equipment
				if(i instanceof Spell)
				{
					Spell tSpell = ((Spell) i);
					t1 = tSpell.spellAbility.type1;
					if(tSpell.spellAbility.type2 != Common.TYPE_NONE)
						t2 = tSpell.spellAbility.type2;
					else
						t2 = -2;
				}
				else if(i instanceof Weapon)
				{
					Weapon tW = ((Weapon) i);
					t1 = tW.wepAbility.type1;
					t2 = -2;
				}
				else if(i instanceof Armor)
				{
					Armor tA = ((Armor) i);
					t1 = tA.getType();
					t2 = -2;
				}

				if(t1 != -1)
				{
					newElement = new InterfaceElement(coreModule, baseID+"t1", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center",	200, 60, 13, -34, 1.0f, 1.0f, "interfaces/inventory/accents/miniicons.png");
					newElement.generateFrames(20, 20, 0, t1+1, 30, false);
					renderModule.addElement(baseID, newElement);
				}
				if(t2 != -1)
				{
					int tInd = t2;
					
					if(tInd == -2)
						tInd = -1;
					
					newElement = new InterfaceElement(coreModule, baseID+"t2", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center",	200, 60, 34, -34, 1.0f, 1.0f, "interfaces/inventory/accents/miniicons.png");
					newElement.generateFrames(20, 20, 0, tInd+1, 30, false);
					renderModule.addElement(baseID, newElement);
				}
			}
		}
		
		return rnewElement;
	}
}
