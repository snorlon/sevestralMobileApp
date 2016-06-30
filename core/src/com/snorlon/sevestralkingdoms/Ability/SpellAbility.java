package com.snorlon.sevestralkingdoms.Ability;

import java.util.ArrayList;
import java.util.Random;

import com.snorlon.sevestralkingdoms.GameComponents.Common;
import com.snorlon.sevestralkingdoms.GameTools.Core;
import com.snorlon.sevestralkingdoms.GameTools.InterfaceElement;
import com.snorlon.sevestralkingdoms.Items.Spell;
import com.snorlon.sevestralkingdoms.NPC.Unit;

public class SpellAbility extends Ability
{	
	boolean permanent = false;//if false, cannot be removed EVER
	public boolean equipped = false;
	
	public SpellAbility(ArrayList<Ability> container, String nname,
			String iconn, double dmg, int nattackType, int t1, int t2, int nrank)
	{
		super(container, nname, iconn, dmg, nattackType,
				t1, t2, nrank);
		
		calculateParticleFX();
	}
	
	public SpellAbility(ArrayList<Ability> container, Ability template)
	{
		super(container, template);
		calculateParticleFX();
	}
	
	public SpellAbility(ArrayList<Ability> container, Spell s)
	{
		this(container, s.name, s.spellAbility.iconName, s.spellAbility.getRawDamageMultiplier(), s.spellAbility.attackType, s.spellAbility.type1, s.spellAbility.type2, s.getRank());
	}

	
	public String getDisplayType()
	{
		return "Spell";
	}
	
	public double getDamage()
	{
		//factor in the rank bonus damage
		//5% per rank above 1
		return base_damage + ((rank-1)*0.1)*base_damage;
	}
	
	public void applySkill(Unit source, Unit target)
	{
		//check for mana, cooldowns, other requirements
		float totalCost = getStaminaCost(source);
		
		if(source.mp < totalCost)
			return;//abort if we can't pay the price
		else
			source.mp -= totalCost;
			
		//calculate damage
		double damage = calculateDamage(source, target);

		int xOffset = source.coreModule.random.nextInt(36)-18;
		int yOffset = source.coreModule.random.nextInt(36)-18;
		
		//draw the damage amount
		InterfaceElement newElement = new InterfaceElement(source.coreModule, "Battle Damage", "Center", "Center", 100, 60, target.get_draw_x()+48+xOffset, target.get_draw_y()+80+yOffset, 2.0f, "-"+((int) damage), source.coreModule.renderModule.FONT_IMPACT);
		if(target.faction.equals(Common.FACTION_PLAYER))
			newElement.setColor(255, 0, 0, 1.0f);
		newElement.generateTransparencyAnimation(1.0f, 0.75f);
		source.coreModule.renderModule.addElement("GFXLayer", newElement);
		newElement.recalcEndX();
		newElement.recalcEndY();
		
		//deal damage
		if(target.hp>0)
			target.hp -= damage;

		float multiplier = 0.25f;
		
		if(target.coreModule.gameModule.partyData.getMainCharacter() == source || target.coreModule.gameModule.partyData.getMainCharacter() == target)
			multiplier = 0.7f;
			
		target.coreModule.soundModule.playSound("Magic", multiplier);

		source.update_render_bars(source.coreModule);
		target.update_render_bars(source.coreModule);
	}

}
