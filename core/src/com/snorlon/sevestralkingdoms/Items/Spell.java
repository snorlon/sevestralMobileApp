package com.snorlon.sevestralkingdoms.Items;

import java.io.PrintWriter;
import java.util.Collections;

import com.snorlon.sevestralkingdoms.Ability.SpellAbility;
import com.snorlon.sevestralkingdoms.GameComponents.Common;
import com.snorlon.sevestralkingdoms.GameTools.Core;
import com.snorlon.sevestralkingdoms.NPC.Unit;


//stores like an item but allows gaining/losing an ability it holds
public class Spell extends Item
{
	int mastery = 1;
	
	int particleID = 0;//default
	
	boolean equipped = false;
	
	public boolean locked = false;//default abilities are locked on a creature!
	
	public SpellAbility spellAbility = null;
	
	public Spell(String nname, int newPath, float damage, int nattackType, int type1, int type2, int nrank, boolean template)
	{
		super(nname, newPath + type1, Item.ITEM_TYPE_SPELL, Item.ITEM_EFFECT_NONE,
				nrank*2000 + 2000, template);
		
		mastery = nrank;
		spellAbility = new SpellAbility(null, nname, generateAbilityIcon(type1, type2), damage, nattackType, type1, type2, mastery);
		spellAbility.equipped = true;
	}
	
	public Spell(String nname, int newPath, float damage, int nattackType, int type1, int type2, int nrank)
	{
		this(nname, newPath, damage, nattackType, type1, type2, nrank, false);
	}
	
	static public String generateAbilityIcon(int T1, int T2)
	{
		if(T1 == T2 && T1 == Common.TYPE_NONE)
			return "misc.png";
		
		else if(T2 == Common.TYPE_NONE)
		{
			return Common.TypeToString(T1).toLowerCase()+".png";
		}
		else
		{
			return Common.TypeToString(T1).toLowerCase()+Common.TypeToString(T2).toLowerCase()+".png";
		}
	}
	
	public Spell(Item template)
	{
		super(template);
		
		if(template instanceof Spell)
		{
			Spell w = ((Spell) template);
			
			mastery = w.mastery;
			
			particleID = w.particleID;
			
			spellAbility = new SpellAbility(null, w.spellAbility);
			spellAbility.equipped = true;
		}
	}
	
	@SuppressWarnings("unchecked")
	public void equip(Unit target)
	{
		if(equipped)
			return;//don't equip if already equipped
		

		if(spellAbility != null)
		{			
			//if unequiped, equip ourself
			equipped = true;
			
			//if we are now wearing it, grant the ability contained
			if(equipped)
			{
				target.attacks.add(spellAbility);
				
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
		

		//if no ability, no way to unequip it, right?
		if(spellAbility != null)
		{
			//if we are NOT wearing it, remove the ability if the user has it
			target.attacks.remove(spellAbility);
		}
	}
	
	public boolean isEquipped()
	{
		return equipped;
	}
	
	public int getRank()
	{
		return mastery;
	}

	public int combatType()
	{
		return spellAbility.attackType;
	}
	
	public void save(PrintWriter out, int layer, Core coreModule)
	{
		//print out tabs for every layer
		for(int i=0; i<layer; i++)
		{
			out.write('\t');
		}
		out.write("[SPELLBOOK]\t"+name);
		
		out.write("\n");
	}
}
