package com.snorlon.sevestralkingdoms.Ability;

import java.util.ArrayList;
import java.util.Random;

import com.snorlon.sevestralkingdoms.GameComponents.Common;
import com.snorlon.sevestralkingdoms.GameTools.Core;
import com.snorlon.sevestralkingdoms.GameTools.InterfaceElement;
import com.snorlon.sevestralkingdoms.Items.Weapon;
import com.snorlon.sevestralkingdoms.NPC.Unit;

//effectively serves as both an item and an ability
public class WeaponAbility extends Ability
{
	int rank = 1;//higher rank = better effect, but bigger flaw
	int formula = 0;//need formulas here!
	String particleType = "slash";//need weapon types!

	//weapons do not have a second typing
	public WeaponAbility(ArrayList<Ability> container, String nname, String iconn, double dmg, int nattackType, int t1, String nPID, int nrank)
	{
		super(container, nname, iconn, dmg, nattackType,
				t1, Common.TYPE_NONE, nrank);
		
		particleType = nPID;
	}
	
	public WeaponAbility(ArrayList<Ability> container, Ability template)
	{
		super(container, template);
	}

	
	public double calculateRaw(Unit source)
	{
		double returnDamage = 0;
		
		//(1.2*stat1 - stat2) * skillmultiplier  1.25 for upper and 0.75
		//System.out.println("Damage: "+getStat(source, offensiveStat));

		//primary stat
		returnDamage = 1.8f * getStat(source, getOffensiveStat());
		returnDamage *= 1.4f;
		
		return returnDamage;
	}
	
	//overload the functions that change with weapons
	public double calculateDamage(Unit source, Unit target)
	{
		double returnDamage = 0;
		
		//(1.2*stat1 - stat2) * skillmultiplier  1.25 for upper and 0.75
		//System.out.println("Damage: "+getStat(source, offensiveStat));

		//primary stat
		returnDamage = 1.8f * getStat(source, getOffensiveStat());

		//System.out.println("Damage: "+returnDamage);
		
		//defensive stat
		returnDamage = returnDamage -  getStat(target, getDefensiveStat());
		
		//up it again
		returnDamage *= 1.4f;
		
		//reduce it slightly
		//returnDamage *= 0.6f;
		
		//System.out.println("Damage: "+returnDamage);
		
		//apply buffs
		
		
		//System.out.println("Damage: "+returnDamage);
		
		//skill modifier
		returnDamage*=base_damage;
		
		//System.out.println("Damage: "+returnDamage);
		
		//luck modifier
		Random r = new Random();
		float min = 80f;
		float max = 121f;
		
		//TWEAK FOR LUCK VALUES
		max += getStat(source, Common.LUCK)/10.0f;//0.1 to 99.9% max damage increase
		returnDamage *= ((r.nextFloat() * (max-min))+min)/100f;//make sure to divide by 100f on the result

		
		//System.out.println("Damage: "+returnDamage);
		//type multiplier
		//0 is regular resistance, for non elemental, otherwise resistance for each element
		returnDamage *= target.getResist(type1);
		
		//check if it's too low
		if(returnDamage<1.0f)
			returnDamage = 1.0f;//cap it at a minimum number
		
		//System.out.println("Damage: "+returnDamage);
		
		return returnDamage;
	}
	
	public String getDisplayType()
	{
		return "Normal";
	}
	
	public void applyAnimation(Unit target, Core coreModule)
	{
		String file1 = Common.TypeToString(type1);
		String file2 = Common.TypeToString(type2);
		String file3 = particleType;
		float transparency1 = 1.0f;
		float transparency2 = 1.0f;
		float transparency3 = 1.0f;
		
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
			transparency3 = 0.75f;
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
		if(file3.length() > 2)
		{
			InterfaceElement newElement = new InterfaceElement(coreModule, "Animation"+target.imageID, "BattleGFX", InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 256, 32, target.get_draw_x()+18+offsetX, target.get_draw_y()+90+offsetY, scale, scale, "animations/attacks/"+file3.toLowerCase()+".png");
			newElement.generateFrames(32, 32, 0.5f, 0, 7, true, false, true);
			newElement.setColor(255, 255, 255, transparency3);
			coreModule.renderModule.addElement("GFXLayer", newElement);
			newElement.recalcEndX();
			newElement.recalcEndY();
		}
	}
	
	public float processResists(float originalVal, Unit target)
	{
		double mult = 1;
		if(target.getResist(type1) < 1.0f)
			mult = 1-((1-target.getResist(type1))*(1-(rank-1)*0.1));
		else
			mult = target.getResist(type1);
		originalVal *= mult;
		
		if(type1!=type2 && type2!=Common.TYPE_NONE)
			originalVal *= target.getResist(type2);
		
		return originalVal;
	}
	
	public void applySkill(Unit source, Unit target)
	{		
		super.applySkill(source, target);
		
		//applyAnimation(target, source.coreModule);

		float multiplier = 0.25f;
		
		if(target.coreModule.gameModule.partyData.getMainCharacter() == source || target.coreModule.gameModule.partyData.getMainCharacter() == target)
			multiplier = 0.7f;
			
		target.coreModule.soundModule.playSound("Large Hit", multiplier);

		source.update_render_bars(source.coreModule);
		target.update_render_bars(source.coreModule);
	}

}
