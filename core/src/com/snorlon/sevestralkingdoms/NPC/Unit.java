package com.snorlon.sevestralkingdoms.NPC;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.snorlon.sevestralkingdoms.Ability.Ability;
import com.snorlon.sevestralkingdoms.Ability.SpellAbility;
import com.snorlon.sevestralkingdoms.Ability.WeaponAbility;
import com.snorlon.sevestralkingdoms.Buildings.BuildingBase;
import com.snorlon.sevestralkingdoms.Buildings.GeneralStore;
import com.snorlon.sevestralkingdoms.GameComponents.Common;
import com.snorlon.sevestralkingdoms.GameTools.Core;
import com.snorlon.sevestralkingdoms.GameTools.InterfaceElement;
import com.snorlon.sevestralkingdoms.GameTools.Renderer;
import com.snorlon.sevestralkingdoms.Items.Armor;
import com.snorlon.sevestralkingdoms.Items.Item;
import com.snorlon.sevestralkingdoms.Items.Shop;
import com.snorlon.sevestralkingdoms.Items.Spell;
import com.snorlon.sevestralkingdoms.Items.Weapon;
import com.snorlon.sevestralkingdoms.Places.Location;
import com.snorlon.sevestralkingdoms.Places.Town;

public class Unit implements Comparable
{	
	public boolean generateName = false;
	
	int rarity = 0;//used for determining overall likeliness to spawn
	
	//battle states
	public static final int STATE_ALIVE = 1;
	public static final int STATE_DIEING = 2;
	public static final int STATE_DEAD = 3;
	
	
	public int unit_state = STATE_ALIVE;
	
	//maitenance data
	public String imageID = "";
	public Core coreModule = null;
	
	//unit non-specific
	public double hp = 0.0f;
	public double max_hp;
	public double mp = 0.0f;
	public double max_mp = 100.0f;

	public int level = 0;
	public int display_level = 0;
	public double exp = 0;
	public double display_exp = 0.0f;
	public double display_max_exp = 1.0f;
	public double max_exp = 1;
	
	//unit data
	public String name = "Dummy";
	public String title = "No Title";
	private String filename = "error.png";//sprites/
	
	public boolean isHuman = false;
	
	
	public int Morale = 50;
	
	public int Personality = Common.PERSONALITY_NEUTRAL;
	
	public double Strength = 5.0;
	public double Toughness = 5.0;
	public double Agility = 5.0;
	public double Dexterity = 5.0;
	public double Intelligence = 5.0;
	public double Willpower = 5.0;
	public double Charisma = 5.0;
	public double Luck = 5.0;
	
	public double Strength_growth = 1.0;
	public double Toughness_growth = 1.0;
	public double Agility_growth = 1.0;
	public double Dexterity_growth = 1.0;
	public double Intelligence_growth = 1.0;
	public double Willpower_growth = 1.0;
	public double Charisma_growth = 1.0;
	public double Luck_growth = 1.0;
	
	int money = 0;
	
	public double resists[] = {1.0f,1.0f,1.0f,1.0f,1.0f,1.0f,1.0f,1.0f,1.0f};
	
	public float animation_speed = 5.0f;
	
	public String faction = "Wild";
	
	public int x = 0;
	public int y = 0;
	
	//equip slots
	public Weapon weaponSlot = null;
	
	public Armor helmetSlot = null;
	public Armor chestpieceSlot = null;
	public Armor leggingsSlot = null;
	
	public int spellSlotCount = 3;//can be used to restrict spell count artificially
	public Spell spellSlots[] = {null,null,null,null,null,null};
	
	String levelLabel = "NULLED";
	
	public ArrayList<Ability> attacks = new ArrayList<Ability>();
	public ArrayList<Item> items = new ArrayList<Item>();
	
	public Location currentLocation = null;
	
	public Unit()
	{
		
	}
	
	public Unit(String nname, int nlevel, float baseStats[], float statGrowth[], float resist[], String nfaction, float animationSpeed, String nfilename, boolean genName, int nrarity, Core ncoreModule, boolean genStats)
	{
		Random r = new Random();

		//generate name?
		generateName = genName;
		
		if(generateName)
		{
			generate_name(r);
		}
		else
			name = nname;
		
		imageID = name + r.nextInt();
		
		money = 0;

		Strength = baseStats[0];
		Toughness = baseStats[1];
		Agility = baseStats[2];
		Dexterity = baseStats[3];
		Intelligence = baseStats[4];
		Willpower = baseStats[5];
		Charisma = baseStats[6];
		Luck = baseStats[7];
		
		Strength_growth = statGrowth[0];
		Toughness_growth = statGrowth[1];
		Agility_growth = statGrowth[2];
		Dexterity_growth = statGrowth[3];
		Intelligence_growth = statGrowth[4];
		Willpower_growth = statGrowth[5];
		Charisma_growth = statGrowth[6];
		Luck_growth = statGrowth[7];
		
		coreModule = ncoreModule;
		
		if(genStats)
		{
			//give them a random stat distribution
			generateStats(30, 13.0f, 0.5f, 2.5f);
		}
		
		level_to(nlevel);
		
		faction = nfaction;
		
		for(int i=0; i<9; i++)
			resists[i] = resist[i];
		
		animation_speed = animationSpeed;

		//roll a personality
		Personality = r.nextInt(Common.MAX_PERSONALITY+1);
		
		levelup();//to force a maxexp update
		
		heal(100);
		
		unit_state = STATE_ALIVE;
		
		setFilename(nfilename);
		
		rarity = nrarity;
	}
	
	public Unit(Core gCore, String nname, int spawn_rate, Hashtable<String, CreatureData > creatureDB)
	{
		//System.out.println("Loading creature: "+nname);
		
		rarity = spawn_rate;
		
		money = 0;
		
		Random r = new Random();
		
		coreModule = gCore;
		
		name = nname;
		imageID = name + r.nextInt();
		
		//roll a personality
		Personality = r.nextInt(Common.MAX_PERSONALITY+1);
		
		if(!creatureDB.containsKey(name))
		{
			System.out.println("Creature "+name+" not found!");
		}
		else
		{
			CreatureData data = creatureDB.get(name);
			
			Strength = data.Strength;
			Toughness = data.Toughness;
			Agility = data.Agility;
			Dexterity = data.Dexterity;
			Intelligence = data.Intelligence;
			Willpower = data.Willpower;
			Charisma = data.Charisma;
			Luck = data.Luck;
			
			Strength_growth = data.Strength_Gain;
			Toughness_growth = data.Toughness_Gain;
			Agility_growth = data.Agility_Gain;
			Dexterity_growth = data.Dexterity_Gain;
			Intelligence_growth = data.Intelligence_Gain;
			Willpower_growth = data.Willpower_Gain;
			Charisma_growth = data.Charisma_Gain;
			Luck_growth = data.Luck_Gain;
			
			filename = data.filename;
			animation_speed = data.animationSpeed;

			resists[Common.TYPE_NONE] = data.Resist_Neutral;
			resists[Common.TYPE_FIRE] = data.Resist_Fire;
			resists[Common.TYPE_WATER] = data.Resist_Water;
			resists[Common.TYPE_EARTH] = data.Resist_Earth;
			resists[Common.TYPE_WIND] = data.Resist_Wind;
			resists[Common.TYPE_LIFE] = data.Resist_Life;
			resists[Common.TYPE_TIME] = data.Resist_Time;
			resists[Common.TYPE_SPACE] = data.Resist_Space;
			resists[Common.TYPE_VOID] = data.Resist_Void;

			if(!data.ability_1.equals("None"))
			{
				addSpellAbility(coreModule.ability_templates, data.ability_1);
			}
			if(!data.ability_2.equals("None"))
			{
				addSpellAbility(coreModule.ability_templates, data.ability_2);
			}
			if(!data.ability_3.equals("None"))
			{
				addSpellAbility(coreModule.ability_templates, data.ability_3);
			}
			if(!data.ability_4.equals("None"))
			{
				addSpellAbility(coreModule.ability_templates, data.ability_4);
			}
			if(!data.spell_1.equals("None"))
			{
				addSpellAbility(coreModule.ability_templates, data.spell_1);
			}
			if(!data.spell_2.equals("None"))
			{
				addSpellAbility(coreModule.ability_templates, data.spell_2);
			}
			if(!data.spell_3.equals("None"))
			{
				addSpellAbility(coreModule.ability_templates, data.spell_3);
			}
			if(!data.weapon.equals("None"))
			{
				addWeaponAbility(coreModule.ability_templates, data.weapon);
			}

			//public String weapon = "None";
			//public String helmet = "None";
			//public String chest = "None";
			//public String pants = "None";
			//System.out.println("Done loading creature: "+nname);
		}
		
	}
	
	public void initialize(int nlevel, int nx, int ny)
	{
		//set its position
		x = nx;
		y = ny;
		
		//generate a random deviation
		level_to(nlevel);
	}
	
	//spawn a unit from a template
	public Unit(Unit template)
	{
		Random r = new Random();
		
		//generate name?
		if(template.generateName)
		{
			generate_name(r);
		}
		else
			name = template.name;
		
		money = template.money;
		
		coreModule = template.coreModule;
		
		setFilename(template.getFilename());
		
		imageID = name + r.nextInt();
		
		level = 1;
		
		exp = 0;
		
		Morale = template.Morale;
		
		Strength = template.Strength;		
		Toughness = template.Toughness;
		Agility = template.Agility;
		Dexterity = template.Dexterity;
		Intelligence = template.Intelligence;
		Willpower = template.Willpower;
		Charisma = template.Charisma;
		Luck = template.Luck;
		
		Strength_growth = template.Strength_growth;
		Toughness_growth = template.Toughness_growth;
		Agility_growth = template.Agility_growth;
		Dexterity_growth = template.Dexterity_growth;
		Intelligence_growth = template.Intelligence_growth;
		Willpower_growth = template.Willpower_growth;
		Charisma_growth = template.Charisma_growth;
		Luck_growth = template.Luck_growth;
		
		for(int i=0; i<9; i++)
			resists[i] = template.resists[i];
		
		faction = template.faction;
		

		animation_speed = template.animation_speed;
		animation_speed += (((r.nextFloat()*0.2f)-0.1f)*animation_speed);
		
		//copy over traits
		//traits = new ArrayList<Trait>();
		
		//copy over attacks
		attacks = new ArrayList<Ability>();
		for( Ability element : template.attacks)
		{
			Ability newAbility = null;
			
			//make a copy and add it
			if(element instanceof WeaponAbility)
				newAbility = new WeaponAbility(attacks, element);
			else
				newAbility = new SpellAbility(attacks, element);
			
			System.out.println("Ability gained: "+newAbility.name);
		}
		
		//generate drops if applicable
		//items = new ArrayList<Item>();
		
		
		//generate a random personality
		Personality = r.nextInt(11);
		
		levelup();//to force a maxexp update
		
		heal(100);
	}
	
	public void addSpellAbility(ArrayList<Ability> ability_templates, String abilityName)
	{
		if(attacks.size() >= 8)
			return;//cannot add if full
		
		SpellAbility newAbility = null;
		
		for(Ability e : ability_templates)
		{
			if(e.name.equals(abilityName))
			{
				//give ourself this ability!
				newAbility = new SpellAbility(attacks, e);
				//System.out.println("Copied ability "+newAbility.name+"!");
			}
		}
		
		if(newAbility == null)
		{
			System.out.println("Failed to find ability "+abilityName+"!");
		}
	}
	
	public void addWeaponAbility(ArrayList<Ability> ability_templates, String abilityName)
	{
		if(attacks.size() >= 9)
			return;//cannot add if full
		
		WeaponAbility newAbility = null;
		
		for(Ability e : ability_templates)
		{
			if(e.name.equals(abilityName))
			{
				//give ourself this ability!
				newAbility = new WeaponAbility(attacks, e);
				//System.out.println("Copied ability "+newAbility.name+"!");
			}
		}
		
		if(newAbility == null)
		{
			System.out.println("Failed to find ability "+abilityName+"!");
		}
	}
	
	public void addAbility(Ability newAbility)
	{
		if(attacks.size() >= 9)
			return;//cannot add if full
		
		attacks.add(newAbility);
	}
	
	public void heal(float amount)
	{
		//up our HP by amount%
		heal_hp(amount);
		heal_mp(amount);
	}
	
	public void heal_hp(float amount)
	{
		float multiplier = 1.0f;
		
		//up our HP by amount%
		hp += multiplier * (amount/100.0f) * max_hp;
		
		//revive ourselfs
		if(hp > 0.0f && unit_state != STATE_ALIVE)
		{
			unit_state = STATE_ALIVE;
		}
		
		//ensure it doesn't go over the max hp
		if(hp>max_hp)
			hp = max_hp;
	}
	
	public void heal_mp(float amount)
	{
		//up our HP by amount%
		mp += amount;
		
		//ensure it doesn't go over the max hp
		if(mp>max_mp)
			mp = max_mp;
	}
	
	public int get_draw_x()
	{
		float offset = -286 + x * 96;
		
		
		return (int) offset;
	}
	
	public int get_draw_y()
	{		
		if(!faction.equals("Player"))
		{
			return (int) ((y * 72) + 118);
		}
		else
		{
			return (int) ((y * 72) - 322);
		}
	}
	
	//give us a name!
	public void generate_name(Random r)
	{
		name = Common.generate_name(r);
	}
	
	public int calcExpGain()
	{
		int expGained = 1;

		expGained+= Strength;
		expGained+= Toughness;
		expGained+= Intelligence;
		expGained+= Willpower;
		expGained+= Agility;
		expGained+= Dexterity;
		expGained+= Charisma;
		expGained+= Luck;
		
		expGained = (int) Math.ceil(Math.sqrt(expGained));
		
		return expGained;
	}
	
	public void recalcMaxExp()
	{
		max_exp = recalcMaxExp(level);
	}
	
	public void recalcDisplayMaxExp(int nlevel)
	{
		display_max_exp = recalcMaxExp(nlevel);
	}
	
	public int recalcMaxExp(int cLevel)
	{
		//currently, 41150 max max exp possible needed
		int nmax_exp = 0;
		
		for(int i=0; i<Math.min(cLevel, 30); i++)
		{
			nmax_exp += 10;
		}
		
		for(int i=30; i<Math.min(cLevel, 100); i++)
		{
			nmax_exp += 20;
		}
		
		for(int i=100; i<Math.min(cLevel, 250); i++)
		{
			nmax_exp += 30;
		}
		
		for(int i=250; i<Math.min(cLevel, 500); i++)
		{
			nmax_exp += 40;
		}
		
		for(int i=500; i<Math.min(cLevel, 999); i++)
		{
			nmax_exp += 50;
		}
		
		return nmax_exp;
	}
	
	public boolean levelup()
	{
		//update max exp
		recalcMaxExp();
		
		//calculate our hp & mp
		max_hp = Strength + Toughness + Agility + Dexterity + Intelligence + Willpower + Charisma + Luck;
		
		if(level >= 999)
			exp = 0;
		
		if(exp>=max_exp && level<999)
		{
			exp -= max_exp;
			
			//recalculate new level
			grantStat(Common.STRENGTH, Strength_growth);
			grantStat(Common.TOUGHNESS, Toughness_growth);
			grantStat(Common.AGILITY, Agility_growth);
			grantStat(Common.DEXTERITY, Dexterity_growth);
			grantStat(Common.INTELLIGENCE, Intelligence_growth);
			grantStat(Common.WILLPOWER, Willpower_growth);
			grantStat(Common.CHARISMA, Charisma_growth);
			grantStat(Common.LUCK, Luck_growth);


			Strength = Math.round(Strength*10)/10.0;
			Toughness = Math.round(Toughness*10)/10.0;
			Intelligence = Math.round(Intelligence*10)/10.0;
			Willpower = Math.round(Willpower*10)/10.0;
			Agility = Math.round(Agility*10)/10.0;
			Dexterity = Math.round(Dexterity*10)/10.0;
			Luck = Math.round(Luck*10)/10.0;
			Charisma = Math.round(Charisma*10)/10.0;
			
			level++;
		}
		
		//calculate our hp & mp
		max_hp = Strength + Toughness + Agility + Dexterity + Intelligence + Willpower + Charisma + Luck;
		
		return false;
	}
	
	public void level_to(int goalLevel)
	{
		if(goalLevel>999)
			return;//don't level past cap
		
		if(goalLevel<level)
			return;//can't level down!
		
		while(level<goalLevel)
		{
			exp = max_exp;
			levelup();
		}
		
		//heal up boys
		heal(100);
	}
	
	public boolean tick(float dt, Core game_core)
	{
		//check if we're dead
		if(hp<=0)
		{
			return false;
		}
		
		if(Morale < 0)
			Morale = 0;
		
		if(Morale > 100)
			Morale = 100;
		
		//update level if exp >= max_exp
		if(exp > max_exp)
		{
			levelup();
		}
		
		//proc display level increasing if it's too low
		tickExp(dt);
		
		return true;//default
	}
	
	public void tickExp(float dt)
	{
		if(display_level < level)
		{
			if(display_exp >= display_max_exp)
			{
				//level up display
				display_level++;
				
				display_max_exp = recalcMaxExp(display_level);
				display_exp = 0;
			}
			else
			{
				//edge towards max_exp, we need to reach next level
				display_exp += (display_max_exp / 2.0f)*dt;
				
				if(display_exp > display_max_exp)
				{
					display_exp = display_max_exp;
				}
				
			}
		}
		else if(display_exp < exp)
		{
			//edge towards current exp
			display_exp += (display_max_exp / 2.0f)*dt;
			
			if(display_exp > exp)
			{
				display_exp = exp;
			}
		}
	}
	
	public float generateAnimationSpeed()
	{
		return animation_speed;
	}
	
	public void generateImageID()
	{
		Random r = new Random();
		
		imageID = name + r.nextInt();
	}
	
	public float healPriority(boolean isSource)//calculate how much interest I should have in healing this target
	{
		float returnValue = 0.0f;
		
		returnValue = (float) ((1.0f - hp / max_hp) * threatLevel());;
		
		if(isSource)
			returnValue *= 1;
		else
			returnValue *= 0.25;
		
		returnValue = (float) Math.pow(returnValue, 2.0);
		
		return returnValue;
	}
	
	public float threatLevel()
	{
		//calculate our overall threat level
		float score = 0;
		
		//add up all of our stats first!
		//raw stats are the bulk of our power
		score+= (Strength + Toughness + Intelligence + Willpower + Dexterity + Agility + Luck + Charisma);
		
		//now add in a score of our attacks
		//primary stat * damage multiplier * 2.0x
		for(Ability a : attacks)
		{
			//use an inverse of the stamina cost for a base multiplier
			float mult = 100.0f - a.getStaminaCost(this);
			//multiply it by the damage
			mult *= a.getDamage() * Ability.getStat(this, a.getOffensiveStat()) / 10.0f;
			//factor in loyalty and morale
			if(mp < a.getStaminaCost(this))
			{
				//factor in morale only if we don't have enough mana
				mult = 0;
			}
			
			
			score+= mult;
		}
		
		//factor in equipment, if any
		
		//return the total		
		return score;
	}
	
	public float attackDesire(ArrayList<Unit> targets)
	{
		float score = 0.0f;
		
		//0 without damaging attacks available
		//check how much damage we do to each target
		//scale based on % of hp left for each & threat
		for(Unit e : targets)
		{
			if(e != this)
			{
				score += (1.1f - (e.hp / e.max_hp)) * e.threatLevel();
			}
		}

		return score*2;
	}
	
	public float panicDesire(ArrayList<Unit> targets)
	{
		float score = -1.0f;
		
		//based on morale, enemies, higher than flee almost always
		
		//sum enemy score
		float totalEnemyThreat = 0;
		float avgEnemyThreat = 0;
		float totalEnemyNum = 0;
		
		for(Unit u : targets)
		{
			if(u.unit_state == STATE_ALIVE && u.faction != faction)
			{
				totalEnemyThreat += u.threatLevel();
				totalEnemyNum++;
			}
		}
				
		//sum our score
		float totalFriendThreat = 0;
		float avgFriendThreat = 0;
		float totalFriendNum = 0;
		
		for(Unit u : targets)
		{
			if(u.unit_state == STATE_ALIVE && u.faction == faction)
			{
				totalFriendThreat += u.threatLevel();
				totalFriendNum++;
			}
		}

		avgEnemyThreat = totalEnemyThreat / totalEnemyNum;
		avgFriendThreat = totalFriendThreat / totalFriendNum;
		System.out.println(Morale);
		score = (avgEnemyThreat / avgFriendThreat) * (100.0f / Morale) * 10.0f * totalEnemyNum/totalFriendNum;

		return score;
	}
	
	public void AI_Attack(ArrayList<Unit> targets)
	{
		String desiredTask = "Do Nothing";//default is no desired task
		float desiredScore = 0;
		float calculatedScore = 0;
		
		//calculate our desire to do each of the objectives
		//check if it's superior to the current decision
		calculatedScore = attackDesire(targets);
		if(calculatedScore > desiredScore)
		{
			desiredTask = "Attack";
			desiredScore = calculatedScore;
		}
		calculatedScore = panicDesire(targets);
		if(calculatedScore > desiredScore)
		{
			desiredTask = "Panic";
			desiredScore = calculatedScore;
		}
		
		if(desiredTask.equals("Attack") || desiredTask.equals("Heal") || desiredTask.equals("Cripple"))
		{
			float bestScore = 0;
			Ability bestAbility = null;
			Unit bestTarget = null;
			
			//iterate across all valid targets
			for(Unit u : targets)
			{			
				//only test units we aren't allied with
				if(u.unit_state == STATE_ALIVE)
				{
					if(!(u.faction.equals(faction)) && !desiredTask.equals("Heal"))
					{

						//pre-try all of our abilities!
						for(Ability e : attacks)
						{
							//test each ability for each target
							//score based on effectiveness
							
							float damage = (float) ((e.calculateDamage(this, u) / u.max_hp)*500.0f);//damage rating is portion of the enemies hp removed
							float score = 0;
							
							//calculate our score based on various factors
							if(desiredTask.equals("Attack"))
							{
								score += damage*2 + ((1.1f - u.hp/u.max_hp)*u.threatLevel())/50.0f;//factor in threat level;

								boolean bad = false;
								
								if(bad)
									score = -500;
							}
							
							//if cannot cast, give 0 weight
							if(e.getStaminaCost(this) > mp)
								score = 0;
							
							//System.out.println("|Name: "+u.name+" Score: "+score);
							
							if(score >= bestScore)
							{
								bestAbility = e;
								bestTarget = u;
								bestScore += score;
							}
						}
					}
				}
			}
			
			//enact the best option for us!
			if(bestTarget!=null && bestAbility!=null)
			{
				//System.out.println("Best target: "+bestTarget.name+" Lv "+bestTarget.level);
				//System.out.println("Best ability: "+bestAbility.name);
				//System.out.println("Best score: "+bestScore);
				
				bestAbility.applySkill(this, bestTarget);
			}
		}
		else if(desiredTask.equals("Panic"))
		{
			Random r = new Random();
			ArrayList<Unit> options = new ArrayList<Unit>();
			
			for(Unit u : targets)
			{
				if(u.faction != faction)
					options.add(u);
			}
			
			if(options.size() > 0 && attacks.size() > 0)
			{
				Ability bestAbility = null;
				Unit bestTarget = null;
				
				//random attack, random target
				bestAbility = attacks.get((r.nextInt(attacks.size())));
				
				//equal chance to hit allies
				bestTarget = options.get((r.nextInt(options.size())));

				if(bestTarget!=null && bestAbility!=null)
				{
					bestAbility.applySkill(this, bestTarget);
				}
			}
			else if(attacks.size() == 0)
			{
				System.out.println(name+" doesn't have an attack! "+faction);
			}
		}
		
	}
	
	public int getMorale()
	{
		int returnMorale = Morale;
		//return what our percieved morale is, not what it is at raw
		
		return returnMorale;
	}
	
	@Override
	public int compareTo(Object compareUnit) {
		double compareQuantity = ((Unit)compareUnit).Agility*100; 		
		double ourAgility = this.Agility*100;
 
		//ascending order
		return (int) (compareQuantity - ourAgility);
	}

	public void tickStamina()
	{
		float rate = 5 + (Morale/100.0f * 45);
		
		heal_mp((float) Math.ceil(rate));
	}

	public double getResist(int type)
	{
		if(type > 8)
			return 0;
		if(type < 0)
			return 0;
		
		double baseResist = resists[type];
		
		
		if(helmetSlot!=null)
			if(helmetSlot.getType() == type)
				baseResist*=helmetSlot.getStatValue();
		if(chestpieceSlot!=null)
			if(chestpieceSlot.getType() == type)
				baseResist*=chestpieceSlot.getStatValue();
		if(leggingsSlot!=null)
			if(leggingsSlot.getType() == type)
				baseResist*=leggingsSlot.getStatValue();
		
		return baseResist;
	}	
	
	public boolean equipWeapon(Weapon equippedWeapon)
	{
		unequipWeapon();
		
		if(equippedWeapon != null)
		{
			//now equip this new weapon
			weaponSlot = equippedWeapon;
			
			equippedWeapon.equip(this);
			
			return true;
		}
		
		return false;
	}
	
	public Weapon unequipWeapon()
	{
		Weapon wep = weaponSlot;
		
		//if we have a weapon equipped already, unequip it
		if(weaponSlot!=null)
		{
			weaponSlot.unequip(this);
			
			weaponSlot = null;
		}
		
		return wep;
	}
	
	public boolean equipArmor(Armor equippedArmor)
	{
		if(equippedArmor == null)
			return false;//can't equip nothing
		
		//determine what to equip depending on what this item is
		if(equippedArmor.armorType == Armor.TYPE_HELMET)
		{
			return equipHelmet(equippedArmor);
		}
		else if(equippedArmor.armorType == Armor.TYPE_CHESTPIECE)
		{
			return equipChestpiece(equippedArmor);
		}
		else if(equippedArmor.armorType == Armor.TYPE_LEGGINGS)
		{
			return equipLeggings(equippedArmor);
		}
		
		return false;
	}
	
	boolean equipHelmet(Armor equippedArmor)
	{
		unequipHelmet();
		
		if(equippedArmor != null)
		{
			//now equip this new weapon
			helmetSlot = equippedArmor;
			
			equippedArmor.equip(this);
			
			return true;
		}
		
		return false;
	}
	
	public Armor unequipHelmet()
	{
		Armor armor = helmetSlot;
		
		if(helmetSlot!=null)
		{
			helmetSlot.unequip(this);
			
			helmetSlot = null;
		}
		
		return armor;
	}
	
	boolean equipChestpiece(Armor equippedArmor)
	{
		unequipChestpiece();
		
		if(equippedArmor != null)
		{
			//now equip this new weapon
			chestpieceSlot = equippedArmor;
			
			equippedArmor.equip(this);
			
			return true;
		}
		
		return false;
	}
	
	public Armor unequipChestpiece()
	{
		Armor armor = chestpieceSlot;
		
		if(chestpieceSlot!=null)
		{
			chestpieceSlot.unequip(this);
			
			chestpieceSlot = null;
		}
		
		return armor;
	}
	
	boolean equipLeggings(Armor equippedArmor)
	{
		unequipLeggings();
		
		if(equippedArmor != null)
		{
			//now equip this new weapon
			leggingsSlot = equippedArmor;
			
			equippedArmor.equip(this);
			
			return true;
		}
		
		return false;
	}
	
	public Armor unequipLeggings()
	{
		Armor armor = leggingsSlot;
		
		if(leggingsSlot!=null)
		{
			leggingsSlot.unequip(this);
			
			leggingsSlot = null;
		}
		
		return armor;
	}
	
	//need a slot to try to place the spell
	public boolean equipSpell(Spell equippedSpell, int slot)
	{
		if(slot >= spellSlotCount || slot < 0)
			return false;//can't fit it!
		
		//attempt to put it in that spot
		if(spellSlots[slot] == null)
		{
			spellSlots[slot] = equippedSpell;
			
			//equip dat spell!
			spellSlots[slot].equip(this);
			
			return true;
		}
		else
		{
			//try to unequip spell in that slot
			if(unequipSpell(slot) != null)
			{
				spellSlots[slot] = equippedSpell;
				
				//equip dat spell!
				spellSlots[slot].equip(this);
				
				return true;
			}
			else
				return false;//can't equip, slot fully occupied
		}
	}
	
	public Spell unequipSpell(int slot)
	{
		Spell spell = spellSlots[slot];
		
		if(slot >= spellSlotCount || slot < 0)
			return null;//can't fit it!
		
		if(spellSlots[slot] == null)
			return null;//already equipped!
		
		if(spellSlots[slot].locked)
			return null;//can't unequip a locked spell
		
		
		//otherwise, assume it is unequippable
		spellSlots[slot].unequip(this);
		
		spellSlots[slot] = null;
		
		return spell;
	}
	
	public InterfaceElement generateImage(Core coreModule, Renderer renderModule, String containerName, String containerID)
	{
		return generateImage(coreModule, renderModule, containerName, containerID, 1.0f, 0, 0);
	}
	
	public InterfaceElement generateImage(Core coreModule, Renderer renderModule, String containerName, String containerID, int xOffset, int yOffset)
	{
		return generateImage(coreModule, renderModule, containerName, containerID, 1.0f, xOffset, yOffset);
	}
	
	public InterfaceElement generateImage(Core coreModule, Renderer renderModule, String containerName, String containerID, float scale, int xOffset, int yOffset, boolean renderMP)
	{
		InterfaceElement newElement1 = generateImage(coreModule, renderModule, containerName, containerID, scale, xOffset, yOffset);
		
		InterfaceElement newElement = null;
		
		int drawY = -50;
		
		//create their interface image
		newElement = new InterfaceElement(coreModule, imageID+containerID+"smallbarbgbot", containerID, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Left", "Center", 89, 48, 0, drawY, 1.0f, 1.0f, "battle/interface/minibarbackgroundbottom.png");
		renderModule.addElement(newElement1.id, newElement);
		
		if(renderMP)
		{
			newElement = new InterfaceElement(coreModule, imageID+"smallmpbar", containerID, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Left", "Center", 75, 7, 7, drawY-13, (float) (exp/max_exp), 1.0f, "battle/interface/minibarmp.png");
			renderModule.addElement(newElement1.id, newElement);
		}
		else
		{
			newElement = new InterfaceElement(coreModule, imageID+"smallexpbar", containerID, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Left", "Center", 75, 7, 7, drawY-13, (float) (mp/max_mp), 1.0f, "battle/interface/minibarexp.png");
			renderModule.addElement(newElement1.id, newElement);
		}
		
		newElement = new InterfaceElement(coreModule, imageID+"smallhpbar", containerID, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Left", "Center", 75, 7, 7, drawY-3, (float) (hp/max_hp), 1.0f, "battle/interface/minibarhp.png");
		renderModule.addElement(newElement1.id, newElement);

		newElement = new InterfaceElement(coreModule, imageID+containerID+"smallbarbgtop", containerID, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Left", "Center", 89, 48, 0, drawY, 1.0f, 1.0f, "battle/interface/minibarbackground.png");
		renderModule.addElement(newElement1.id, newElement);
		
		//draw our level
		newElement = new InterfaceElement(coreModule, containerID, "Center", "Center", 140, 50, 34, -30, 0.7f, "L"+level, renderModule.FONT_IMPACT);
		newElement.id = imageID+"level_label";
		levelLabel = newElement.id;
		renderModule.addElement(newElement1.id, newElement);
		
		update_render_bars(coreModule);
		
		return newElement1;
	}
	
	public InterfaceElement generateImage(Core coreModule, Renderer renderModule, String containerName, String containerID, float scale, int xOffset, int yOffset)
	{
		coreModule.renderModule.destroyElement(imageID+containerID);
		
		//sprite
		InterfaceElement newElement = new InterfaceElement(coreModule, imageID+containerID, containerID, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", ((int) (98)), ((int) (98)), xOffset, yOffset);
		renderModule.addElement(containerName, newElement);
		
		InterfaceElement newElement2 = new InterfaceElement(coreModule, imageID+"sprite", containerID, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 144, 72, 0, 15, scale, scale, "sprites/"+getFilename());
		newElement2.generateFrames(72, 72, generateAnimationSpeed(), 0, 1, true);
		renderModule.addElement(imageID+containerID, newElement2);
		
		return newElement;
	}

	public void dress(String outfitFilenamen, String hairFilenamen, String faceFilenamen)
	{
		
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	public void setGender(boolean male)
	{
	}
	
	public void update_render_bars(Core coreModule)
	{		
		Renderer renderModule = coreModule.renderModule;

		InterfaceElement element = renderModule.getElement(imageID+"smallhpbar");
		InterfaceElement element2 = renderModule.getElement(imageID+"smallexpbar");
		InterfaceElement element3 = renderModule.getElement(imageID+"smallmpbar");
		InterfaceElement element4 = renderModule.getElement(levelLabel);
		
		if(element != null)
		{
			element.scale_x = (float) (hp/max_hp);

			if(element.scale_x < 0)
				element.scale_x = 0;
		}
		
		if(element2 != null)
		{
			element2.scale_x = (float) (display_exp/max_exp);

			if(element2.scale_x < 0)
				element2.scale_x = 0;
			
			if(element != null)
			{
				element.scale_x = 1.0f;
			}
		}
		
		if(element3 != null)
		{
			element3.scale_x = (float) (mp/max_mp);

			if(element3.scale_x < 0)
				element3.scale_x = 0;
		}
		
		
		if(element4 != null)
		{
			element4.updateText("L"+display_level);
		}
	}
	
	public int getGoldValue()
	{
		int value = 0;

		value+= Strength;
		value+= Toughness;
		value+= Intelligence;
		value+= Willpower;
		value+= Agility;
		value+= Dexterity;
		value+= Charisma;
		value+= Luck;
		
		value = (int) Math.ceil(Math.sqrt(value/16)) + 1;
		
		return value;
	}
	
	public void grantStat(int statID, double strength_growth2)
	{
		switch(statID)
		{
			case Common.STRENGTH:
				Strength += strength_growth2;
				if(Strength>999)
					Strength = 999;
				break;
			case Common.TOUGHNESS:
				Toughness += strength_growth2;
				if(Toughness>999)
					Toughness = 999;
				break;
			case Common.AGILITY:
				Agility += strength_growth2;
				if(Agility>999)
					Agility = 999;
				break;
			case Common.DEXTERITY:
				Dexterity += strength_growth2;
				if(Dexterity>999)
					Dexterity = 999;
				break;
			case Common.INTELLIGENCE:
				Intelligence += strength_growth2;
				if(Intelligence>999)
					Intelligence = 999;
				break;
			case Common.WILLPOWER:
				Willpower += strength_growth2;
				if(Willpower>999)
					Willpower = 999;
				break;
			case Common.CHARISMA:
				Charisma += strength_growth2;
				if(Charisma>999)
					Charisma = 999;
				break;
			case Common.LUCK:
				Luck += strength_growth2;
				if(Luck>999)
					Luck = 999;
				break;
			case -1:
				grantStat(Common.STRENGTH, strength_growth2);
				grantStat(Common.TOUGHNESS, strength_growth2);
				grantStat(Common.AGILITY, strength_growth2);
				grantStat(Common.DEXTERITY, strength_growth2);
				grantStat(Common.INTELLIGENCE, strength_growth2);
				grantStat(Common.WILLPOWER, strength_growth2);
				grantStat(Common.CHARISMA, strength_growth2);
				grantStat(Common.LUCK, strength_growth2);
				break;
		}
	}

	public String getItemChanges(int type)
	{
		int mode = 0;
		int statBefore = 0;
		int statAfter = 0;
		
		String returnString = "No changes made.";
		
		switch(type)
		{
			case Item.ITEM_EFFECT_STAT_STRENGTH:
				statBefore = (int) Strength;
				statAfter = statBefore + 1;
				mode = 1;
				break;
			case Item.ITEM_EFFECT_STAT_TOUGHNESS:
				statBefore = (int) Toughness;
				statAfter = statBefore + 1;
				mode = 1;
				break;
			case Item.ITEM_EFFECT_STAT_AGILITY:
				statBefore = (int) Agility;
				statAfter = statBefore + 1;
				mode = 1;
				break;
			case Item.ITEM_EFFECT_STAT_DEXTERITY:
				statBefore = (int) Dexterity;
				statAfter = statBefore + 1;
				mode = 1;
				break;
			case Item.ITEM_EFFECT_STAT_INTELLIGENCE:
				statBefore = (int) Intelligence;
				statAfter = statBefore + 1;
				mode = 1;
				break;
			case Item.ITEM_EFFECT_STAT_WILLPOWER:
				statBefore = (int) Willpower;
				statAfter = statBefore + 1;
				mode = 1;
				break;
			case Item.ITEM_EFFECT_STAT_CHARISMA:
				statBefore = (int) Charisma;
				statAfter = statBefore + 1;
				mode = 1;
				break;
			case Item.ITEM_EFFECT_STAT_LUCK:
				statBefore = (int) Luck;
				statAfter = statBefore + 1;
				mode = 1;
				break;
			case Item.ITEM_EFFECT_STAT_ALL:
				return "";
			case Item.ITEM_EFFECT_FOOD_COMMON:
				statBefore = Morale;
				statAfter = Morale+5;
				mode = 2;
				break;
			case Item.ITEM_EFFECT_FOOD_UNCOMMON:
				statBefore = Morale;
				statAfter = Morale+10;
				mode = 2;
				break;
			case Item.ITEM_EFFECT_FOOD_RARE:
				statBefore = Morale;
				statAfter = Morale+15;
				mode = 2;
				break;
			case Item.ITEM_EFFECT_FOOD_LEGENDARY:
				statBefore = Morale;
				statAfter = Morale+20;
				mode = 2;
				break;
			case Item.ITEM_EFFECT_FOOD_UNIQUE:
				statBefore = Morale;
				statAfter = Morale+25;
				mode = 2;
				break;
		}
		
		
		if(mode == 1)
		{
			if(statAfter > 999)
				statAfter = 999;
			
			returnString = "Before: "+statBefore+", After: "+statAfter;
		}
		else if(mode == 2)
		{
			if(statAfter > 100)
				statAfter = 100;
			
			returnString = "Before: "+statBefore+", After: "+statAfter;
		}
		
		return returnString;
	}

	public void useItem(int type)
	{
		switch(type)
		{
			case Item.ITEM_EFFECT_STAT_STRENGTH:
				grantStat(Common.STRENGTH, 1);
				break;
			case Item.ITEM_EFFECT_STAT_TOUGHNESS:
				grantStat(Common.TOUGHNESS, 1);
				break;
			case Item.ITEM_EFFECT_STAT_AGILITY:
				grantStat(Common.AGILITY, 1);
				break;
			case Item.ITEM_EFFECT_STAT_DEXTERITY:
				grantStat(Common.DEXTERITY, 1);
				break;
			case Item.ITEM_EFFECT_STAT_INTELLIGENCE:
				grantStat(Common.INTELLIGENCE, 1);
				break;
			case Item.ITEM_EFFECT_STAT_WILLPOWER:
				grantStat(Common.WILLPOWER, 1);
				break;
			case Item.ITEM_EFFECT_STAT_CHARISMA:
				grantStat(Common.CHARISMA, 1);
				break;
			case Item.ITEM_EFFECT_STAT_LUCK:
				grantStat(Common.LUCK, 1);
				break;
			case Item.ITEM_EFFECT_STAT_ALL:
				grantStat(-1, 1);
				break;
			case Item.ITEM_EFFECT_FOOD_COMMON:
				Morale+=5;
				if(Morale>100)
					Morale = 100;
				break;
			case Item.ITEM_EFFECT_FOOD_UNCOMMON:
				Morale+=10;
				if(Morale>100)
					Morale = 100;
				break;
			case Item.ITEM_EFFECT_FOOD_RARE:
				Morale+=15;
				if(Morale>100)
					Morale = 100;
				break;
			case Item.ITEM_EFFECT_FOOD_LEGENDARY:
				Morale+=20;
				if(Morale>100)
					Morale = 100;
				break;
			case Item.ITEM_EFFECT_FOOD_UNIQUE:
				Morale+=25;
				if(Morale>100)
					Morale = 100;
				break;
		}
	}
	
	public int getMoney()
	{
		return money;
	}
	
	public void setMoney(int amount)
	{
		money = amount;
	}
	
	public boolean canGiveMoney(int amount)
	{
		return money>=amount;
	}
	
	public void giveMoney(int amount)
	{
		money+= amount;
		if(money > 999999)
			money = 999999;
	}
	
	public void takeMoney(int amount)
	{
		if(!canGiveMoney(amount))
			return;
		
		money -= amount;
		
		if(money < 0)
			money = 0;
	}

	public void stepTurn(boolean isLeader)
	{
		//spend our moneys if we want to
		if(currentLocation!=null && !isLeader)
		{
			//handle the shop at our current location (if any)
			if(currentLocation.getMap()!=null)
			{
				if(currentLocation.getMap() instanceof Town)
				{					
					Town t = (Town) currentLocation.getMap();
					BuildingBase b = t.getBuilding("General Store");
					Shop generalShop = null;
					if(b!=null)
					{
						generalShop = ((GeneralStore) b).getShop();
					}
					
					//roll for food
					int roll = 0;
					int rollMax = 10;//odds up to 10000, so 10 = 0.1%
					
					if(Morale >= 30)
					{
						rollMax = 100-Morale;
					}
					else
					{
						rollMax = 100 + (int)(Math.pow(2, 30-Morale));
					}

					int bestWeaponIndex = -1;
					int bestWeaponRating = scoreWeapon(weaponSlot);
					Shop bestWeaponShop = null;
					
					//default to weakest spell
					int bestSpellIndex = -1;
					int bestSpellRating = 9999999;
					for(int i=0; i<spellSlotCount; i++)
					{
						int rating = scoreSpell(spellSlots[i], i);
						
						if(rating < bestSpellRating)
							bestSpellRating = rating;
					}
					Shop bestSpellShop = null;
					
					int helmetIndex = -1;
					int helmetRank = 0;
					Shop helmetShop = null;
					int chestIndex = -1;
					int chestRank = 0;
					Shop chestShop = null;
					int pantsIndex = -1;
					int pantsRank = 0;
					Shop pantsShop = null;
					
					if(helmetSlot != null)
						helmetRank = helmetSlot.getRank();
					if(chestpieceSlot != null)
						chestRank = chestpieceSlot.getRank();
					if(leggingsSlot != null)
						pantsRank = leggingsSlot.getRank();

					if(generalShop!=null)
					{
						//decide if we want food
						roll = coreModule.random.nextInt(10000);
						if(roll < rollMax)
						{
							//attempt to buy food!
							//check through the shop for something we can eat
							
							int index = 0;
							for(Item i : generalShop.getMerchandise())
							{
								if(i!=null)
								{
									//check if it's food and fills us
									if(i.type == Item.ITEM_TYPE_FOOD)
									{
										if(generalShop.canBuyItem(money, index))
										{
											money -= generalShop.getCost(index);
											Item boughtItem = generalShop.takeItem(index);
											
											if(boughtItem!=null)
											{
												useItem(boughtItem.type);
											}
											System.out.println(name+" bought a "+boughtItem.name+"!");
										}
									}
								}
								
								index++;
							}
						}
						
						//now check out the gear
						int index = 0;
						for(Item i : generalShop.getMerchandise())
						{
							if(i!=null)
							{
								if(i.type == Item.ITEM_TYPE_WEAPON)
								{
									if(generalShop.canBuyItem(money, index))
									{
										int score = scoreWeapon((Weapon) i);
										
										if(score > bestWeaponRating)
										{
											bestWeaponIndex = index;
											bestWeaponRating = score;
											bestWeaponShop = generalShop;
										}
									}
								}
								else if(i.type == Item.ITEM_TYPE_SPELL)
								{
									if(generalShop.canBuyItem(money, index))
									{
										int score = 0;
										for(int j=0; j<spellSlotCount; j++)
										{
											int v = scoreSpell((Spell) i,  j);
											if(v > score)
												score = v;
										}
										
										if(score > bestSpellRating)
										{
											bestSpellIndex = index;
											bestSpellRating = score;
											bestSpellShop = generalShop;
										}
									}
								}
								else if(i.type == Item.ITEM_TYPE_ARMOR)
								{
									if(generalShop.canBuyItem(money, index))
									{
										Armor a = (Armor) i;

										if(a.armorType == Armor.TYPE_HELMET)
										{
											if(a.getRank() > helmetRank)
											{
												helmetRank = a.getRank();
												helmetIndex = index;
												helmetShop = generalShop;
											}
										}
										else if(a.armorType == Armor.TYPE_CHESTPIECE)
										{
											if(a.getRank() > chestRank)
											{
												chestRank = a.getRank();
												chestIndex = index;
												chestShop = generalShop;
											}
										}
										else if(a.armorType == Armor.TYPE_LEGGINGS)
										{
											if(a.getRank() > pantsRank)
											{
												pantsRank = a.getRank();
												pantsIndex = index;
												pantsShop = generalShop;
											}
										}
									}
								}
							}
							
							index++;
						}
						
						
						//only consider buying if the weapon is actually available
						if(bestWeaponIndex != -1 && bestWeaponShop != null)
						{
							//make the exchange if we can
							if(bestWeaponShop.canBuyItem(money, bestWeaponIndex))
							{
								money -= bestWeaponShop.getCost(bestWeaponIndex);
								Item boughtItem = bestWeaponShop.takeItem(bestWeaponIndex);
								
								if(boughtItem!=null)
								{
									Weapon w = (Weapon) boughtItem;
									
									//if we had a weapon equipped, ditch it
									if(weaponSlot!=null)
									{
										//this weapon should fall into irrelevance and into garbage disposal
										unequipWeapon();
									}
									
									equipWeapon(w);
								}
								
								System.out.println(name+" bought a "+boughtItem.name+" weapon!");
							}
						}
						
						
						//only consider buying if the weapon is actually available
						if(bestSpellIndex != -1 && bestSpellShop != null)
						{
							//make the exchange if we can
							if(bestSpellShop.canBuyItem(money, bestSpellIndex))
							{
								money -= bestSpellShop.getCost(bestSpellIndex);
								Item boughtItem = bestSpellShop.takeItem(bestSpellIndex);
								
								if(boughtItem!=null)
								{
									Spell s = (Spell) boughtItem;
									
									int bestSlot = -1;
									
									for(int i=0; i<spellSlotCount; i++)
									{
										if(spellSlots[i]==null)
										{
											bestSlot = i;
											break;
										}
									}
									
									//need room if the best spell slot is still -1
									int bestScore = 0;
									int bestSlotT = 0;
									if(bestSlot < 0)
									{
										for(int i=0; i<spellSlotCount; i++)
										{
											int tScore = scoreSpell(s, i);
											
											if(tScore > bestScore)
											{
												bestScore = tScore;
												bestSlotT = i;
											}
										}
										
										unequipSpell(bestSlotT);
										bestSlot = bestSlotT;
									}
									
									equipSpell(s, bestSlot);
								}
								
								System.out.println(name+" bought a "+boughtItem.name+" spell!");
							}
						}
						if(helmetIndex > -1)
						{
							//that means we're going for it
							if(helmetShop.canBuyItem(money, helmetIndex))
							{
								money -= helmetShop.getCost(helmetIndex);
								Item boughtItem = helmetShop.takeItem(helmetIndex);
								
								//unequip what we have!
								//it'll disappear into the abyss
								unequipHelmet();
								
								
								//equip the new armor
								equipHelmet((Armor) boughtItem);

								System.out.println(name+" bought a "+boughtItem.name+" helmet!");
							}
						}
						if(chestIndex > -1)
						{
							//that means we're going for it
							if(chestShop.canBuyItem(money, chestIndex))
							{
								money -= chestShop.getCost(chestIndex);
								Item boughtItem = chestShop.takeItem(chestIndex);
								
								//unequip what we have!
								//it'll disappear into the abyss
								unequipChestpiece();
								
								
								//equip the new armor
								equipChestpiece((Armor) boughtItem);

								System.out.println(name+" bought a "+boughtItem.name+" chestpiece!");
							}
						}
						if(pantsIndex > -1)
						{
							//that means we're going for it
							if(pantsShop.canBuyItem(money, pantsIndex))
							{
								money -= pantsShop.getCost(pantsIndex);
								Item boughtItem = pantsShop.takeItem(pantsIndex);
								
								//unequip what we have!
								//it'll disappear into the abyss
								unequipLeggings();
								
								
								//equip the new armor
								equipLeggings((Armor) boughtItem);

								System.out.println(name+" bought a "+boughtItem.name+" pants!");
							}
						}
					}
				}
			}
		}
	}
	
	public int scoreWeapon(Weapon w)
	{
		if(w == null)
			return -1;
		
		int score = 400;
		
		//compare it to our other stuff
		//for every spell with same typing, -30
		int typePenalty = -30;
		for(int i=0; i< spellSlotCount; i++)
		{
			if(spellSlots[i]!=null)
			{
				if(spellSlots[i].spellAbility.type1 != Common.TYPE_NONE)
				{
					if(spellSlots[i].spellAbility.type1 == w.wepAbility.type1)
						score += typePenalty;
					if(spellSlots[i].spellAbility.type1 == w.wepAbility.type2)
						score += typePenalty;
				}
				if(spellSlots[i].spellAbility.type2 != Common.TYPE_NONE)
				{
					if(spellSlots[i].spellAbility.type2 == w.wepAbility.type1)
						score += typePenalty;
					if(spellSlots[i].spellAbility.type2 == w.wepAbility.type2)
						score += typePenalty;
				}
			}
		}
			
		//for every spell with same attack type, -15
		for(int i=0; i< spellSlotCount; i++)
		{
			if(spellSlots[i]!=null)
			{
				if(spellSlots[i].spellAbility.attackType == w.wepAbility.attackType)
					score -= 15;
			}
		}
		
		//bonus points for rank
		score += w.getRank()*20;
		
		return score;
	}
	
	public int scoreSpell(Spell s, int slotIgnored)
	{
		if(s == null)
			return -1;
		
		int score = 400;
		
		//compare it to our other stuff
		//for every spell with same typing, -30
		int typePenalty = -30;
		for(int i=0; i< spellSlotCount; i++)
		{
			if(spellSlots[i]!=null && spellSlots[i] != s && i!=slotIgnored)
			{
				if(spellSlots[i].spellAbility.type1 != Common.TYPE_NONE)
				{
					if(spellSlots[i].spellAbility.type1 == s.spellAbility.type1)
						score += typePenalty;
					if(spellSlots[i].spellAbility.type1 == s.spellAbility.type2)
						score += typePenalty;
				}
				if(spellSlots[i].spellAbility.type2 != Common.TYPE_NONE)
				{
					if(spellSlots[i].spellAbility.type2 == s.spellAbility.type1)
						score += typePenalty;
					if(spellSlots[i].spellAbility.type2 == s.spellAbility.type2)
						score += typePenalty;
				}
			}
		}
		
		//compare to our equipped weapon
		if(weaponSlot!=null)
		{
			if(s.spellAbility.type1 != Common.TYPE_NONE)
			{
				if(s.spellAbility.type1 == weaponSlot.wepAbility.type1)
					score += typePenalty;
				if(s.spellAbility.type1 == weaponSlot.wepAbility.type2)
					score += typePenalty;
			}
			if(s.spellAbility.type2 != Common.TYPE_NONE)
			{
				if(s.spellAbility.type2 == weaponSlot.wepAbility.type1)
					score += typePenalty;
				if(s.spellAbility.type2 == weaponSlot.wepAbility.type2)
					score += typePenalty;
			}
		}
			
		//for every spell with same attack type, -15
		for(int i=0; i< spellSlotCount; i++)
		{
			if(spellSlots[i]!=null && spellSlots[i] != s && i!=slotIgnored)
			{
				if(spellSlots[i].spellAbility.attackType == s.spellAbility.attackType)
					score -= 15;
			}
		}
		
		if(weaponSlot!=null)
		{
			if(weaponSlot.wepAbility.attackType == s.spellAbility.attackType)
				score -= 15;
		}
		
		//bonus points for rank
		score += s.getRank()*20;
		
		return score;
	}

	public void addMorale(int moralegain)
	{
		Morale += moralegain;
		if(Morale > 100)
			Morale = 100;
		if(Morale < 0)
			Morale = 0;
	}
	
	public void resetStats()
	{
		Strength = 0;
		Toughness = 0;
		Agility = 0;
		Dexterity = 0;
		Intelligence = 0;
		Willpower = 0;
		Luck = 0;
		Charisma = 0;
		
		Strength_growth = 0.0f;
		Toughness_growth = 0.0f;
		Intelligence_growth = 0.0f;
		Willpower_growth = 0.0f;
		Agility_growth = 0.0f;
		Dexterity_growth = 0.0f;
		Luck_growth = 0.0f;
		Charisma_growth = 0.0f;
	}
	
	public void addStatGrowth(int index, float amount)
	{
		switch(index)
		{
			case Common.STRENGTH:
				Strength_growth+=amount;
				break;
			case Common.TOUGHNESS:
				Toughness_growth+=amount;
				break;
			case Common.AGILITY:
				Agility_growth+=amount;
				break;
			case Common.DEXTERITY:
				Dexterity_growth+=amount;
				break;
			case Common.INTELLIGENCE:
				Intelligence_growth+=amount;
				break;
			case Common.WILLPOWER:
				Willpower_growth+=amount;
				break;
			case Common.LUCK:
				Luck_growth+=amount;
				break;
			case Common.CHARISMA:
				Charisma_growth+=amount;
				break;
		}
	}
	
	public void addStat(int index, int amount)
	{
		switch(index)
		{
			case Common.STRENGTH:
				Strength+=amount;
				break;
			case Common.TOUGHNESS:
				Toughness+=amount;
				break;
			case Common.AGILITY:
				Agility+=amount;
				break;
			case Common.DEXTERITY:
				Dexterity+=amount;
				break;
			case Common.INTELLIGENCE:
				Intelligence+=amount;
				break;
			case Common.WILLPOWER:
				Willpower+=amount;
				break;
			case Common.LUCK:
				Luck+=amount;
				break;
			case Common.CHARISMA:
				Charisma+=amount;
				break;
		}
	}
	
	public void generateStats(int baseStatTotal, float growthStatTotal, float growthStatMin, float growthStatMax)
	{
		//randomly distribute base stats
		//add to every stat a base amount
		int minimum = Math.min(baseStatTotal/8, 5);
		
		resetStats();
		
		for(int i=0; i<8; i++)
		{
			addStat(i,minimum);
			baseStatTotal-=minimum;
		}
		
		while(baseStatTotal>0)
		{
			addStat(coreModule.random.nextInt(8),1);
			baseStatTotal--;
		}
		
		//randomly distribute growth stat total
			//discard extra if all stats are capped
		for(int i=0; i<8; i++)
		{
			addStatGrowth(i,growthStatMin);
			growthStatTotal-=growthStatMin;
		}
		
		ArrayList<Integer> possible = new ArrayList<Integer>();
		
		for(int i=0; i<8; i++)
			possible.add(i);
		
		while(growthStatTotal>0 && possible.size() > 0)
		{
			int roll = possible.get(coreModule.random.nextInt(possible.size()));
			
			addStatGrowth(roll,0.1f);
			growthStatTotal-=0.1f;
			
			switch(roll)
			{
				case Common.STRENGTH:
					if(Strength_growth > growthStatMax && possible.contains(roll))
						possible.remove(possible.indexOf(roll));
					break;
				case Common.TOUGHNESS:
					if(Toughness_growth > growthStatMax && possible.contains(roll))
						possible.remove(possible.indexOf(roll));
					break;
				case Common.INTELLIGENCE:
					if(Intelligence_growth > growthStatMax && possible.contains(roll))
						possible.remove(possible.indexOf(roll));
					break;
				case Common.WILLPOWER:
					if(Willpower_growth > growthStatMax && possible.contains(roll))
						possible.remove(possible.indexOf(roll));
					break;
				case Common.AGILITY:
					if(Agility_growth > growthStatMax && possible.contains(roll))
						possible.remove(possible.indexOf(roll));
					break;
				case Common.DEXTERITY:
					if(Dexterity_growth > growthStatMax && possible.contains(roll))
						possible.remove(possible.indexOf(roll));
					break;
				case Common.LUCK:
					if(Luck_growth > growthStatMax && possible.contains(roll))
						possible.remove(possible.indexOf(roll));
					break;
				case Common.CHARISMA:
					if(Charisma_growth > growthStatMax && possible.contains(roll))
						possible.remove(possible.indexOf(roll));
					break;
			}
		}

		Strength_growth = Math.floor(Strength_growth*10)/10.0;
		Toughness_growth = Math.floor(Toughness_growth*10)/10.0;
		Intelligence_growth = Math.floor(Intelligence_growth*10)/10.0;
		Willpower_growth = Math.floor(Willpower_growth*10)/10.0;
		Agility_growth = Math.floor(Agility_growth*10)/10.0;
		Dexterity_growth = Math.floor(Dexterity_growth*10)/10.0;
		Luck_growth = Math.floor(Luck_growth*10)/10.0;
		Charisma_growth = Math.floor(Charisma_growth*10)/10.0;

		Strength = Math.floor(Strength*10)/10.0;
		Toughness = Math.floor(Toughness*10)/10.0;
		Intelligence = Math.floor(Intelligence*10)/10.0;
		Willpower = Math.floor(Willpower*10)/10.0;
		Agility = Math.floor(Agility*10)/10.0;
		Dexterity = Math.floor(Dexterity*10)/10.0;
		Luck = Math.floor(Luck*10)/10.0;
		Charisma = Math.floor(Charisma*10)/10.0;
	}
}
