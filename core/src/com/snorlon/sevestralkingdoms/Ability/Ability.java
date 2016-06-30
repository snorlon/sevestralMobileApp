package com.snorlon.sevestralkingdoms.Ability;

import java.util.ArrayList;
import java.util.Random;

import com.snorlon.sevestralkingdoms.GameComponents.Common;
import com.snorlon.sevestralkingdoms.GameTools.Core;
import com.snorlon.sevestralkingdoms.GameTools.InterfaceElement;
import com.snorlon.sevestralkingdoms.GameTools.Renderer;
import com.snorlon.sevestralkingdoms.NPC.Unit;

@SuppressWarnings("rawtypes")
public class Ability implements Comparable
{
	public String name = "Breath of Air";
	public String iconName = "error.png";
	
	double base_damage = 1.0;//multiplier of calculation that is applied
	
	public int attackType = Common.PHYSICAL;
	
	public int type1 = Common.TYPE_NONE;
	public int type2 = Common.TYPE_NONE;

	public int particleID = 0;//default
	
	int rank = 1;
	
	public Ability(ArrayList<Ability> container, String nname, String iconn, double dmg, int nattackType, int t1, int t2, int nrank)
	{
		name = nname;
		
		iconName = iconn;
		
		base_damage = Math.floor(dmg*100)/100.0;
		attackType = nattackType;
		
		type1 = t1;
		type2 = t2;
		
		rank = nrank;
		
		particleID = t1;
		
		//add ourself to the container if provided
		if(container!=null)
			container.add(this);
	}
	
	public Ability(ArrayList<Ability> container, Ability template)
	{

		name = template.name;
		
		iconName = template.iconName;
		
		base_damage = template.base_damage;
		attackType = template.attackType;
		
		type1 = template.type1;
		type2 = template.type2;
		
		//add ourself to the container if provided
		if(container!=null)
		{
			container.add(this);
		}
	}
	
	public void calculateParticleFX()
	{
		if(type1 != Common.TYPE_NONE)
			particleID = type1;
		else
			particleID = type2;
	}
	
	public static double getStat(Unit source, int stat)
	{
		switch(stat)
		{
		case Common.STRENGTH:
			return source.Strength;
		case Common.TOUGHNESS:
			return source.Toughness;
		case Common.INTELLIGENCE:
			return source.Intelligence;
		case Common.WILLPOWER:
			return source.Willpower;
		case Common.AGILITY:
			return source.Agility;
		case Common.DEXTERITY:
			return source.Dexterity;
		case Common.CHARISMA:
			return source.Charisma;
		case Common.LUCK:
			return source.Luck;
		}
		
		return 0.0f;
	}
	
	public int getRank()
	{
		return rank;
	}
	
	public float getCritChance(Unit source)
	{
		float critRate = 0.05f;
		
		//luck factor
		//caps at 1.05f
		critRate += (Ability.getStat(source, Common.LUCK)) / 1000.0f;
		
		if(critRate > 0.99f)
			critRate = 0.99f;
		
		return critRate;
	}
	
	public float getCritMult(Unit source)
	{
		float critMult = 1.25f;
		
		//luck factor
		//caps at 1.75f
		critMult += (Ability.getStat(source, Common.LUCK)) / 2000.0f;
		
		return critMult;
	}
	
	public String getTargetString()
	{
		if(base_damage <= 0.0f)
			return "Ally";
		else
			return "Foe";
	}
	
	public String getDisplayType()
	{
		return "Error";
	}
	
	public double processResists(double returnDamage, Unit target)
	{
		returnDamage *= target.getResist(type1);
		
		if(type1!=type2)
			returnDamage *= target.getResist(type2);
		
		return returnDamage;
	}
	
	public double calculateDamage(Unit source, Unit target)
	{
		double returnDamage = 0;

		//primary stat
		returnDamage = 1.8f * getStat(source, getOffensiveStat());
		
		//defensive stat
		returnDamage = returnDamage -  getStat(target, getDefensiveStat());
		
		//up it again
		returnDamage *= 1.4f;
		
		//skill modifier
		returnDamage*=getDamage();
		
		//luck modifier
		Random r = new Random();
		float min = 80f;
		float max = 121f;
		
		//TWEAK FOR LUCK VALUES
		max += getStat(source, Common.LUCK)/10.0f;//0.1 to 99.9% max damage increase
		returnDamage *= ((r.nextFloat() * (max-min))+min)/100f;//make sure to divide by 100f on the result

		returnDamage = processResists(returnDamage, target);
		
		//check if it's too low
		if(returnDamage<1.0f)
			returnDamage = 1.0f;//cap it at a minimum number
		
		//System.out.println("Damage: "+returnDamage);
		
		return returnDamage;
	}
	
	public void applyAnimation(Unit target, Core coreModule)
	{
		String file1 = Common.TypeToString(type1);
		String file2 = Common.TypeToString(type2);
		float transparency1 = 1.0f;
		float transparency2 = 1.0f;
		
		float scale = 2.5f;
		
		int offsetX = 0;
		int offsetY = 0;

		offsetX = coreModule.random.nextInt(48) - 24;
		offsetY = coreModule.random.nextInt(48) - 24;
		
		if(type1 == type2)
		{
			file2 = "";
			transparency2 = 0.0f;
		}
		else
		{
			transparency1 = 0.75f;
			transparency2 = 0.75f;
		}
		
		if(file1.length() > 2)
		{
			InterfaceElement newElement = new InterfaceElement(coreModule, "Animation"+target.imageID, "BattleGFX", InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 256, 32, target.get_draw_x()+18+offsetX, target.get_draw_y()+90+offsetY, scale, scale, "animations/attacks/"+file1.toLowerCase()+".png");
			newElement.generateFrames(32, 32, 0.5f, 0, 7, true, false, true);
			newElement.setColor(255, 255, 255, transparency1);
			coreModule.renderModule.addElement("GFXLayer", newElement);
			newElement.recalcEndX();
			newElement.recalcEndY();
		}
		if(file2.length() > 2)
		{
			InterfaceElement newElement = new InterfaceElement(coreModule, "Animation"+target.imageID, "BattleGFX", InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 256, 32, target.get_draw_x()+18+offsetX, target.get_draw_y()+90+offsetY, scale, scale, "animations/attacks/"+file2.toLowerCase()+".png");
			newElement.generateFrames(32, 32, 0.5f, 0, 7, true, false, true);
			newElement.setColor(255, 255, 255, transparency2);
			coreModule.renderModule.addElement("GFXLayer", newElement);
			newElement.recalcEndX();
			newElement.recalcEndY();
		}
	}
	
	public void applySkill(Unit source, Unit target)
	{
		//check for mana, cooldowns, other requirements
		float totalCost = getStaminaCost(source);
		
		if(source.mp < totalCost)
			return;//abort if we can't pay the price
		else
			source.mp -= totalCost;
		
		//calculate chance to miss, don't attack if we fail the roll		
		//calculate damage
		double damage = calculateDamage(source, target);
		
		//deal damage
		if(target.hp>0)
			target.hp -= damage;

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
		
		target.update_render_bars(source.coreModule);
	}

	public int getStaminaCost(Unit source)
	{
		float sum = (float) (base_damage * 10);
		
		if(sum > 100.0f)
			sum = 100.0f;
		
		return ((int) Math.ceil(sum));
	}
	
	//sort data!
	@Override
	public int compareTo(Object arg0) {
		int ourPriority = 5;//default is 5 for innate ability
		int theirPriority = 5;
		
		if(this instanceof WeaponAbility)
		{
			ourPriority = 4;
		}
		
		if(this instanceof SpellAbility)
		{
			ourPriority = 3;
		}
		
		if(arg0 instanceof WeaponAbility)
		{
			theirPriority = 4;
		}
		else if(arg0 instanceof SpellAbility)
		{
			theirPriority = 3;
		}
		
		return (theirPriority - ourPriority);
	}

	public double getDamage()
	{
		return base_damage;
	}
	
	public int getOffensiveStat()
	{
		switch(attackType)
		{
			case Common.PHYSICAL:
				return Common.STRENGTH;
			case Common.MAGICAL:
				return Common.INTELLIGENCE;
			case Common.AGILE:
				return Common.DEXTERITY;
		}
		
		//default
		return Common.STRENGTH;
	}
	
	public int getDefensiveStat()
	{
		switch(attackType)
		{
			case Common.PHYSICAL:
				return Common.TOUGHNESS;
			case Common.MAGICAL:
				return Common.WILLPOWER;
			case Common.AGILE:
				return Common.AGILITY;
		}
		
		//default
		return Common.TOUGHNESS;
	}

	public void createRender(Core coreModule, String containerName, String containerID)
	{
		InterfaceElement newElement = null;
		Renderer renderModule = coreModule.renderModule;
		
		newElement = new InterfaceElement(coreModule, containerName+"b", containerID, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 200, 144, 0, 0, 1.0f, 1.0f, "battle/abilities/"+iconName);
		renderModule.addElement(containerName, newElement);
		
		//generate the stat icons
		newElement = new InterfaceElement(coreModule, containerName+"o", containerID, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 180, 90, 0, 17, 1.0f, 1.0f, "interfaces/battle/move attack type.png");
		newElement.generateFrames(180, 30, 0, attackType, 2, false);
		renderModule.addElement(containerName, newElement);
		
		//generate the type icons
		newElement = new InterfaceElement(coreModule, containerName+"st1", containerID, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 720, 28, -50, -51, 1.0f, 1.0f, "battle/abilities/types.png");
		newElement.generateFrames(80, 28, 0, type1, 8, false);
		renderModule.addElement(containerName, newElement);
		
		
		newElement = new InterfaceElement(coreModule, containerName+"st2", containerID, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 720, 28, 50, -51, 1.0f, 1.0f, "battle/abilities/types.png");
		newElement.generateFrames(80, 28, 0, type2, 8, false);
		renderModule.addElement(containerName, newElement);
		
		newElement = new InterfaceElement(coreModule, containerName+"t", containerID, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 200, 144, 0, 0, 1.0f, 1.0f, "battle/abilities/frame.png");
		renderModule.addElement(containerName, newElement);
	}
	
	public String getDamageMultiplier()
	{
		if(getDamage() < 10)
			return ""+Math.floor(getDamage()*10)/10;
		else
			return ""+((int) getDamage());
	}
	
	public double getRawDamageMultiplier()
	{
		return base_damage;
	}
	
	public void setRawDamageMultiplier(double val)
	{
		base_damage = val;
	}
	
	public String getAttackType()
	{
		switch(attackType)
		{
			case Common.PHYSICAL:
				return "Physical";
			case Common.MAGICAL:
				return "Magical";
			case Common.AGILE:
				return "Agile";
		}
		
		return "Error";
	}
}
