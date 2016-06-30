package com.snorlon.sevestralkingdoms.Items;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import com.snorlon.sevestralkingdoms.Ability.Ability;
import com.snorlon.sevestralkingdoms.Ability.WeaponAbility;
import com.snorlon.sevestralkingdoms.GameComponents.Common;
import com.snorlon.sevestralkingdoms.GameTools.Core;
import com.snorlon.sevestralkingdoms.NPC.Unit;


//stores like an item but allows gaining/losing an ability it holds
//switch extends to Armor when armor added
public class Weapon extends Item
{
	public static final int TYPE_SWORD = 0;
	//slash
	public static final int TYPE_GIANT_SWORD = 1;
	//slash
	public static final int TYPE_BOW = 2;
		//bullet
	public static final int TYPE_KNIFE = 6;//casual stabbing knife
		//slash
	public static final int TYPE_HAMMER = 7;
		//smash
	public static final int TYPE_KNUCKLES = 8;
		//neutral
	public static final int TYPE_RIFLE = 15;//pew pew
		//bullet
	public static final int TYPE_PENTAGRAM = 16;//Quickly summon dark creatures to attack foe
		//sparkle
	public static final int TYPE_STAFF = 17;//summon large generic magic attacks
		//sparkle
	public static final int TYPE_WAND = 18;//summon small generic magic attacks
		//sparkle
	
	
	//Particles needed:
	//Giant Sword
	
	//stats needed
	boolean equipped = false;
	
	int rank = 1;//higher rank = better effect, but bigger flaw
	int formula = 0;//need formulas here!
	int weaponType = TYPE_SWORD;//need weapon types!
	
	public WeaponAbility wepAbility = null;

	public Weapon(String nname, int newPath, int baseValue, int newWepType, int type1, int nrank)
	{

		this(nname, newPath, baseValue, newWepType, type1, nrank, false);
	}
	
	public Weapon(String nname, int newPath, int baseValue, int newWepType, int type1, int nrank, boolean isTemplate)
	{
		super(nname, newPath, Item.ITEM_TYPE_WEAPON, Item.ITEM_EFFECT_NONE,
				baseValue, isTemplate);
		
		weaponType = newWepType;
		
		String iconName = "error.png";
		
		int nattackType = Common.PHYSICAL;
		
		rank = nrank;
		
		//dmg multiplier, stamina multiplier set by weapon as well BALANCE HERE
		switch(weaponType)
		{
			case TYPE_HAMMER:
				break;
			case TYPE_SWORD:
				break;
			case TYPE_GIANT_SWORD:
				break;
			case TYPE_BOW:
				nattackType = Common.AGILE;
				break;
			case TYPE_KNUCKLES:
				nattackType = Common.AGILE;
				break;
			case TYPE_RIFLE:
				nattackType = Common.AGILE;
				break;
			case TYPE_KNIFE:
				nattackType = Common.AGILE;
				break;
			case TYPE_PENTAGRAM:
				nattackType = Common.MAGICAL;
				break;
			case TYPE_STAFF:
				nattackType = Common.MAGICAL;
				break;
			case TYPE_WAND:
				nattackType = Common.MAGICAL;
				break;
		}
		
		//create an ability to store data for in us
		wepAbility = new WeaponAbility(null, nname, iconName, 1.0f, nattackType, type1, "None", rank);

		//calculate stamina cost
		//wepAbility.staminaCost;
	}
	
	public Weapon(Item template)
	{
		super(template);
		
		if(template instanceof Weapon)
		{
			Weapon w = ((Weapon) template);
			
			rank = w.rank;
			
			formula = w.formula;
			
			weaponType = w.weaponType;
			
			wepAbility = new WeaponAbility(null, w.wepAbility);

		}
	}
	
	public static boolean isWeapon(String value)
	{
		if(StringToWeapon(value)!=-1)
		{
			return true;
		}
		
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public void equip(Unit target)
	{
		if(equipped)
			return;//don't equip if already equipped
		
		target.weaponSlot = this;
		

		if(wepAbility != null)
		{			
			//if unequiped, equip ourself
			equipped = true;
			
			//if we are now wearing it, grant the ability contained
			if(equipped)
			{
				target.attacks.add(wepAbility);
				
				//sort abilities! (innate, weapon, magic) order
				Collections.sort(target.attacks);
			}
		}
	}
	
	public void unequip(Unit target)
	{
		if(!equipped)
			return;//abort if already unequipped
		
		//if equipped, unequip ourselve
		//if unequiped, equip ourself
		equipped = false;
		
		
		target.weaponSlot = null;

		//if no ability, no way to unequip it, right?
		if(wepAbility != null)
		{
			//if we are NOT wearing it, remove the ability if the user has it
			target.attacks.remove(wepAbility);
		}
	}
	
	public boolean isWearable()
	{
		return true;
	}
	
	public boolean isEquipped()
	{
		return equipped;
	}
	
	public String getSlot()
	{
		return "Weapon";
	}
	
	public static int StringToWeapon(String value)
	{
		if(value.equals("Sword"))
			return TYPE_SWORD;
		else if(value.equals("Giant Sword"))
			return TYPE_GIANT_SWORD;
		else if(value.equals("Bow"))
			return TYPE_BOW;
		else if(value.equals("Knife"))
			return TYPE_KNIFE;
		else if(value.equals("Hammer"))
			return TYPE_HAMMER;
		else if(value.equals("Knuckles"))
			return TYPE_KNUCKLES;
		else if(value.equals("Rifle"))
			return TYPE_RIFLE;
		else if(value.equals("Pentagram"))
			return TYPE_PENTAGRAM;
		else if(value.equals("Polearm"))
			return TYPE_STAFF;
		else if(value.equals("Wand"))
			return TYPE_WAND;
		
		return -1;
	}
	
	public String getWeaponType()
	{		
		switch(weaponType)
		{
			case TYPE_SWORD:
				return "Sword";
			case TYPE_GIANT_SWORD:
				return "Giant Sword";
			case TYPE_BOW:
				return "Bow";
			case TYPE_KNIFE:
				return "Knife";
			case TYPE_HAMMER:
				return "Hammer";
			case TYPE_KNUCKLES:
				return "Knuckles";
			case TYPE_RIFLE:
				return "Rifle";
			case TYPE_PENTAGRAM:
				return "Pentagram";
			case TYPE_STAFF:
				return "Polearm";
			case TYPE_WAND:
				return "Wand";
			default:
				return "Error";
		}
	}
	
	public void setRank(int value)
	{
		rank = value;
	}

	public int getRank()
	{
		return rank;
	}

	public int combatType()
	{
		return wepAbility.attackType;
	}
	
	public void save(PrintWriter out, int layer, Core coreModule)
	{
		//print out tabs for every layer
		for(int i=0; i<layer; i++)
		{
			out.write('\t');
		}
		out.write("[WEAPON]\t"+name);
		
		out.write("\n");
	}
}
