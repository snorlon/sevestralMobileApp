package com.snorlon.sevestralkingdoms.Items;

import java.io.PrintWriter;
import java.util.Collections;
import java.util.Random;

import com.snorlon.sevestralkingdoms.Ability.Ability;
import com.snorlon.sevestralkingdoms.Ability.SpellAbility;
import com.snorlon.sevestralkingdoms.Ability.WeaponAbility;
import com.snorlon.sevestralkingdoms.GameComponents.Common;
import com.snorlon.sevestralkingdoms.GameTools.Core;
import com.snorlon.sevestralkingdoms.NPC.Unit;


//stores like an item but allows gaining/losing an ability it holds
//switch extends to Armor when armor added
public class Armor extends Item
{
	public static final int TYPE_NONE = 0;
	public static final int TYPE_HELMET = 1;
	public static final int TYPE_CHESTPIECE = 2;
	public static final int TYPE_LEGGINGS = 3;
	
	
	boolean equipped = false;
	
	private int atype = Common.TYPE_NONE;
	
	double statVal = 0;
	
	int rank = 1;//total stat points
	public int armorType = TYPE_HELMET;//need weapon types!

	public Armor(String nname, int newPath, int newArmorType, int rankMult, int ntype)
	{
		super(nname, newPath, Item.ITEM_TYPE_ARMOR, Item.ITEM_EFFECT_NONE,
				rankMult*500);
		
		armorType = newArmorType;

		rank = rankMult;
		
		setType(ntype);
		
		//calculate a random stat distribution
		switch(armorType)
		{
			case TYPE_HELMET:
				statVal = 25;
				break;
			case TYPE_CHESTPIECE:
				statVal = 40;
				break;
			case TYPE_LEGGINGS:
				statVal = 30;
				break;
		}
		
		statVal *= Math.sqrt((double) rankMult);
		statVal = Math.max(1.0-(statVal/100.0),0.05);
	}
	
	public Armor(Item template)
	{
		super(template);
		
		if(template instanceof Armor)
		{
			Armor w = ((Armor) template);
			
			rank = w.rank;
			
			statVal = w.getStatValue();
			
			setType(w.getType());
			
			armorType = w.armorType;

		}
	}
	
	public double getStatValue()
	{
		return statVal;
	}
	
	public static int StringToType(String value)
	{
		if(value.equals("Helmet"))
			return TYPE_HELMET;
		else if(value.equals("Chestpiece"))
			return TYPE_CHESTPIECE;
		else if(value.equals("Leggings"))
			return TYPE_LEGGINGS;
		
		return -1;
	}
	
	@SuppressWarnings("unchecked")
	public void equip(Unit target)
	{
		if(equipped)
			return;//don't equip if already equipped
		

		equipped = true;
	}
	
	public static boolean isArmor(String value)
	{
		if(value.equals("Helmet"))
			return true;
		else if(value.equals("Chestpiece"))
			return true;
		else if(value.equals("Leggings"))
			return true;
		
		return false;
	}
	
	public void unequip(Unit target)
	{
		if(!equipped)
			return;//abort if already unequipped
		
		//if equipped, unequip ourselve
		equipped = false;
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
		if(armorType == TYPE_HELMET)
			return "Head";
		if(armorType == TYPE_CHESTPIECE)
			return "Chest";
		if(armorType == TYPE_LEGGINGS)
			return "Legs";
		
		return "Error";
	}

	public int getRank()
	{
		return rank;
	}
	
	public void save(PrintWriter out, int layer, Core coreModule)
	{
		//print out tabs for every layer
		for(int i=0; i<layer; i++)
		{
			out.write('\t');
		}
		out.write("[ARMOR]\t"+name+"\n");
	}

	public int getType() {
		return atype;
	}

	public void setType(int type) {
		atype = type;
	}
}
