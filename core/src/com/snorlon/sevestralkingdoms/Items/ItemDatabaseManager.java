package com.snorlon.sevestralkingdoms.Items;

import java.util.ArrayList;
import java.util.Hashtable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.snorlon.sevestralkingdoms.Ability.Ability;
import com.snorlon.sevestralkingdoms.Ability.SpellAbility;
import com.snorlon.sevestralkingdoms.GameComponents.Common;
import com.snorlon.sevestralkingdoms.GameTools.Core;

public class ItemDatabaseManager
{	
	Hashtable<String, Item> database = new Hashtable<String, Item>();
	Hashtable<String, Item> database_common = new Hashtable<String, Item>();
	Hashtable<String, Item> database_uncommon = new Hashtable<String, Item>();
	Hashtable<String, Item> database_scarce = new Hashtable<String, Item>();
	Hashtable<String, Item> database_rare = new Hashtable<String, Item>();
	Hashtable<String, Item> database_unique = new Hashtable<String, Item>();
	Hashtable<String, Item> database_treasure = new Hashtable<String, Item>();
	Hashtable<String, Item> database_legendary = new Hashtable<String, Item>();
	Hashtable<String, Item> database_ancient = new Hashtable<String, Item>();
	Hashtable<String, Item> database_relic = new Hashtable<String, Item>();
	Hashtable<String, Item> database_divine_relic = new Hashtable<String, Item>();
	Hashtable<String, Item> database_godlike = new Hashtable<String, Item>();
	Core coreModule = null;
	
	public ItemDatabaseManager(Core ncore)
	{
		coreModule = ncore;
	}
	
	//attempt to load game items
	public void load()
	{
		//start NEW item loading system here
		Item newItem = null;
		Spell newSpell = null;
		
		
		//spells
			int simgIndex = 200;//the base index for the book images

			/*
			 * RARITY 0 Spell
			 * 
			 */
			//neutral
			newSpell = new Spell("Scratch", simgIndex, 0.25f, Common.PHYSICAL, Common.TYPE_NONE, Common.TYPE_NONE, 1, true);
			initSpell(newSpell, Item.RARITY_COMMON);
			newSpell = new Spell("Panic", simgIndex, 0.5f, Common.PHYSICAL, Common.TYPE_NONE, Common.TYPE_NONE, 1, true);
			initSpell(newSpell, Item.RARITY_COMMON);
			newSpell = new Spell("Peck", simgIndex, 0.5f, Common.PHYSICAL, Common.TYPE_NONE, Common.TYPE_NONE, 1, true);
			initSpell(newSpell, Item.RARITY_COMMON);
			newSpell = new Spell("Bite", simgIndex, 0.75f, Common.PHYSICAL, Common.TYPE_NONE, Common.TYPE_NONE, 1, true);
			initSpell(newSpell, Item.RARITY_COMMON);

			//fire
			newSpell = new Spell("Scorch", simgIndex, 0.75f, Common.MAGICAL, Common.TYPE_FIRE, Common.TYPE_NONE, 1, true);
			initSpell(newSpell, Item.RARITY_COMMON);
			newSpell = new Spell("Fireball", simgIndex, 1f, Common.AGILE, Common.TYPE_FIRE, Common.TYPE_NONE, 1, true);
			initSpell(newSpell, Item.RARITY_COMMON);
			newSpell = new Spell("Steam Cloud", simgIndex, 0.25f, Common.MAGICAL, Common.TYPE_FIRE, Common.TYPE_WATER, 1, true);
			initSpell(newSpell, Item.RARITY_COMMON);

			//water
			newSpell = new Spell("Squirt", simgIndex, 0.75f, Common.MAGICAL, Common.TYPE_WATER, Common.TYPE_NONE, 1, true);
			initSpell(newSpell, Item.RARITY_COMMON);
			newSpell = new Spell("Bubble", simgIndex, 1f, Common.AGILE, Common.TYPE_WATER, Common.TYPE_NONE, 1, true);
			initSpell(newSpell, Item.RARITY_COMMON);
			newSpell = new Spell("Cold Cloud", simgIndex, 0.25f, Common.MAGICAL, Common.TYPE_WATER, Common.TYPE_WIND, 1, true);
			initSpell(newSpell, Item.RARITY_COMMON);
			
			//earth
			newSpell = new Spell("Rock Drop", simgIndex, 1f, Common.PHYSICAL, Common.TYPE_EARTH, Common.TYPE_NONE, 1, true);
			initSpell(newSpell, Item.RARITY_COMMON);
			
			newSpell = new Spell("Tremble", simgIndex, 0.75f, Common.MAGICAL, Common.TYPE_EARTH, Common.TYPE_NONE, 1, true);
			initSpell(newSpell, Item.RARITY_COMMON);
			newSpell = new Spell("Dust Cloud", simgIndex, 0.25f, Common.MAGICAL, Common.TYPE_EARTH, Common.TYPE_NONE, 1, true);
			initSpell(newSpell, Item.RARITY_COMMON);
			
			//wind
			newSpell = new Spell("Breeze", simgIndex, 0.75f, Common.MAGICAL, Common.TYPE_WIND, Common.TYPE_NONE, 1, true);
			initSpell(newSpell, Item.RARITY_COMMON);
			newSpell = new Spell("Gust", simgIndex, 1f, Common.MAGICAL, Common.TYPE_WIND, Common.TYPE_NONE, 1, true);
			initSpell(newSpell, Item.RARITY_COMMON);
			
			//life
			newSpell = new Spell("Infection", simgIndex, 1f, Common.PHYSICAL, Common.TYPE_LIFE, Common.TYPE_NONE, 1, true);
			initSpell(newSpell, Item.RARITY_COMMON);
			
			//time
			newSpell = new Spell("Fast Forward", simgIndex, 1f, Common.AGILE, Common.TYPE_TIME, Common.TYPE_NONE, 1, true);
			initSpell(newSpell, Item.RARITY_COMMON);
			
			//void
			newSpell = new Spell("Hallucination", simgIndex, 1f, Common.MAGICAL, Common.TYPE_VOID, Common.TYPE_NONE, 1, true);
			initSpell(newSpell, Item.RARITY_COMMON);

			/*
			 * RARITY 1 Spell
			 * 
			 */
			
			//neutral
			newSpell = new Spell("Slash", simgIndex, 1.2f, Common.AGILE, Common.TYPE_NONE, Common.TYPE_NONE, 1, true);
			initSpell(newSpell, Item.RARITY_UNCOMMON);
			
			//fire
			newSpell = new Spell("Firebreath", simgIndex, 1f, Common.MAGICAL, Common.TYPE_FIRE, Common.TYPE_NONE, 1, true);
			initSpell(newSpell, Item.RARITY_UNCOMMON);
			
			//water
			newSpell = new Spell("Light Mist", simgIndex, 0.7f, Common.MAGICAL, Common.TYPE_WATER, Common.TYPE_NONE, 1, true);
			initSpell(newSpell, Item.RARITY_UNCOMMON);
			newSpell = new Spell("Frosty Breath", simgIndex, 1f, Common.MAGICAL, Common.TYPE_WATER, Common.TYPE_WIND, 1, true);
			initSpell(newSpell, Item.RARITY_UNCOMMON);
			newSpell = new Spell("Root", simgIndex, 1.1f, Common.AGILE, Common.TYPE_WATER, Common.TYPE_LIFE, 1, true);
			initSpell(newSpell, Item.RARITY_UNCOMMON);
			
			//wind
			newSpell = new Spell("Strong Breath", simgIndex, 1f, Common.AGILE, Common.TYPE_WIND, Common.TYPE_NONE, 1, true);
			initSpell(newSpell, Item.RARITY_UNCOMMON);
			newSpell = new Spell("Nostalgia", simgIndex, 0.5f, Common.MAGICAL, Common.TYPE_WIND, Common.TYPE_TIME, 1, true);
			initSpell(newSpell, Item.RARITY_UNCOMMON);
			
			//life
			newSpell = new Spell("Regenerate", simgIndex, 0.75f, Common.MAGICAL, Common.TYPE_LIFE, Common.TYPE_NONE, 1, true);
			initSpell(newSpell, Item.RARITY_UNCOMMON);
			newSpell = new Spell("Infection", simgIndex, 1.2f, Common.PHYSICAL, Common.TYPE_LIFE, Common.TYPE_SPACE, 1, true);
			initSpell(newSpell, Item.RARITY_UNCOMMON);
			
			//void
			newSpell = new Spell("Roar", simgIndex, 1.2f, Common.MAGICAL, Common.TYPE_VOID, Common.TYPE_NONE, 1, true);
			initSpell(newSpell, Item.RARITY_UNCOMMON);

			/*
			 * RARITY 2 Spell
			 * 
			 */
			
			//neutral
			newSpell = new Spell("Crush", simgIndex, 1.2f, Common.PHYSICAL, Common.TYPE_NONE, Common.TYPE_NONE, 2, true);
			initSpell(newSpell, Item.RARITY_SCARCE);
			
			//fire
			newSpell = new Spell("Phoenix Split", simgIndex, 0.7f, Common.MAGICAL, Common.TYPE_FIRE, Common.TYPE_LIFE, 2, true);
			initSpell(newSpell, Item.RARITY_SCARCE);
			newSpell = new Spell("Lava Spit", simgIndex, 0.8f, Common.MAGICAL, Common.TYPE_FIRE, Common.TYPE_EARTH, 2, true);
			initSpell(newSpell, Item.RARITY_SCARCE);
			newSpell = new Spell("Blastfire", simgIndex, 1.2f, Common.MAGICAL, Common.TYPE_FIRE, Common.TYPE_WIND, 2, true);
			initSpell(newSpell, Item.RARITY_SCARCE);
			
			//water
			newSpell = new Spell("Cleansing Water", simgIndex, 0.5f, Common.MAGICAL, Common.TYPE_WATER, Common.TYPE_NONE, 2, true);
			initSpell(newSpell, Item.RARITY_SCARCE);
			newSpell = new Spell("Frost Dust", simgIndex, 1.5f, Common.MAGICAL, Common.TYPE_WATER, Common.TYPE_WIND, 2, true);
			initSpell(newSpell, Item.RARITY_SCARCE);
			newSpell = new Spell("Frost Blast", simgIndex, 0.7f, Common.AGILE, Common.TYPE_WATER, Common.TYPE_WIND, 2, true);
			initSpell(newSpell, Item.RARITY_SCARCE);
			
			//earth
			newSpell = new Spell("Burrow", simgIndex, 0.5f, Common.AGILE, Common.TYPE_EARTH, Common.TYPE_NONE, 2, true);
			initSpell(newSpell, Item.RARITY_SCARCE);
			
			//void
			newSpell = new Spell("Engulf", simgIndex, 1.5f, Common.PHYSICAL, Common.TYPE_VOID, Common.TYPE_NONE, 2, true);
			initSpell(newSpell, Item.RARITY_SCARCE);
			newSpell = new Spell("Noxious Breath", simgIndex, 1.0f, Common.MAGICAL, Common.TYPE_VOID, Common.TYPE_NONE, 2, true);
			initSpell(newSpell, Item.RARITY_SCARCE);
			newSpell = new Spell("Fatal Venom", simgIndex, 1.8f, Common.PHYSICAL, Common.TYPE_VOID, Common.TYPE_LIFE, 2, true);
			initSpell(newSpell, Item.RARITY_SCARCE);

			/*
			 * RARITY 3 Spell
			 * 
			 */
			
			//neutral
			newSpell = new Spell("Megaton Crush", simgIndex, 4f, Common.PHYSICAL, Common.TYPE_NONE, Common.TYPE_NONE, 3, true);
			initSpell(newSpell, Item.RARITY_RARE);
			
			//fire
			newSpell = new Spell("Damnation", simgIndex, 1.5f, Common.MAGICAL, Common.TYPE_FIRE, Common.TYPE_VOID, 3, true);
			initSpell(newSpell, Item.RARITY_RARE);
			
			//water
			newSpell = new Spell("Heavy Rain", simgIndex, 1f, Common.MAGICAL, Common.TYPE_WATER, Common.TYPE_NONE, 3, true);
			initSpell(newSpell, Item.RARITY_RARE);
			newSpell = new Spell("Frostbite", simgIndex, 4f, Common.PHYSICAL, Common.TYPE_WATER, Common.TYPE_FIRE, 3, true);
			initSpell(newSpell, Item.RARITY_RARE);
			newSpell = new Spell("Typhoon", simgIndex, 1.4f, Common.AGILE, Common.TYPE_WATER, Common.TYPE_WIND, 3, true);
			initSpell(newSpell, Item.RARITY_RARE);
			
			//earth
			newSpell = new Spell("Sandnado", simgIndex, 1f, Common.AGILE, Common.TYPE_EARTH, Common.TYPE_WIND, 3, true);
			initSpell(newSpell, Item.RARITY_RARE);
			
			//wind
			newSpell = new Spell("Tornado", simgIndex, 1.2f, Common.AGILE, Common.TYPE_WIND, Common.TYPE_NONE, 3, true);
			initSpell(newSpell, Item.RARITY_RARE);
			
			//life
			newSpell = new Spell("Plague", simgIndex, 3f, Common.MAGICAL, Common.TYPE_LIFE, Common.TYPE_NONE, 3, true);
			initSpell(newSpell, Item.RARITY_RARE);
			
			//void
			newSpell = new Spell("Agony Venom", simgIndex, 2.4f, Common.MAGICAL, Common.TYPE_VOID, Common.TYPE_NONE, 3, true);
			initSpell(newSpell, Item.RARITY_RARE);
			newSpell = new Spell("Sonar Scream", simgIndex, 1.4f, Common.MAGICAL, Common.TYPE_VOID, Common.TYPE_NONE, 3, true);
			initSpell(newSpell, Item.RARITY_RARE);

			/*
			 * RARITY 4 Spell
			 * 
			 */
			
			//neutral
			newSpell = new Spell("Lacerate", simgIndex, 2f, Common.PHYSICAL, Common.TYPE_NONE, Common.TYPE_NONE, 4, true);
			initSpell(newSpell, Item.RARITY_UNIQUE);
			
			//fire
			newSpell = new Spell("Phoenix Flare", simgIndex, 4f, Common.AGILE, Common.TYPE_FIRE, Common.TYPE_NONE, 4, true);
			initSpell(newSpell, Item.RARITY_UNIQUE);
			newSpell = new Spell("Magma Wave", simgIndex, 2f, Common.AGILE, Common.TYPE_FIRE, Common.TYPE_EARTH, 4, true);
			initSpell(newSpell, Item.RARITY_UNIQUE);
			
			//water
			newSpell = new Spell("Frostocalypse", simgIndex, 2.4f, Common.MAGICAL, Common.TYPE_WATER, Common.TYPE_WIND, 4, true);
			initSpell(newSpell, Item.RARITY_UNIQUE);
			newSpell = new Spell("Dark Seas", simgIndex, 2.5f, Common.MAGICAL, Common.TYPE_WATER, Common.TYPE_VOID, 4, true);
			initSpell(newSpell, Item.RARITY_UNIQUE);
			
			//earth
			newSpell = new Spell("Terraform", simgIndex, 0.8f, Common.PHYSICAL, Common.TYPE_EARTH, Common.TYPE_SPACE, 4, true);
			initSpell(newSpell, Item.RARITY_UNIQUE);
			newSpell = new Spell("Noxious Swamp", simgIndex, 1.3f, Common.MAGICAL, Common.TYPE_EARTH, Common.TYPE_VOID, 4, true);
			initSpell(newSpell, Item.RARITY_UNIQUE);
			
			//wind
			
			//life
			
			//time
			
			//space
			newSpell = new Spell("Intense Gravity", simgIndex, 2.2f, Common.AGILE, Common.TYPE_SPACE, Common.TYPE_NONE, 4, true);
			initSpell(newSpell, Item.RARITY_UNIQUE);
			newSpell = new Spell("Meteor Shower", simgIndex, 1.5f, Common.MAGICAL, Common.TYPE_SPACE, Common.TYPE_FIRE, 4, true);
			initSpell(newSpell, Item.RARITY_UNIQUE);
			
			//void
			newSpell = new Spell("Darkness Shroud", simgIndex, 1.2f, Common.PHYSICAL, Common.TYPE_VOID, Common.TYPE_NONE, 4, true);
			initSpell(newSpell, Item.RARITY_UNIQUE);

			/*
			 * RARITY 5 Spell
			 * 
			 */
			
			//neutral
			
			//fire
			newSpell = new Spell("Eruption", simgIndex, 4f, Common.PHYSICAL, Common.TYPE_FIRE, Common.TYPE_EARTH, 5, true);
			initSpell(newSpell, Item.RARITY_TREASURE);
			
			//water
			newSpell = new Spell("Tsunami", simgIndex, 5f, Common.AGILE, Common.TYPE_WATER, Common.TYPE_NONE, 5, true);
			initSpell(newSpell, Item.RARITY_TREASURE);
			newSpell = new Spell("Red Ice", simgIndex, 5f, Common.PHYSICAL, Common.TYPE_WATER, Common.TYPE_WIND, 5, true);
			initSpell(newSpell, Item.RARITY_TREASURE);
			newSpell = new Spell("Ancient Sea", simgIndex, 4f, Common.PHYSICAL, Common.TYPE_WATER, Common.TYPE_TIME, 5, true);
			initSpell(newSpell, Item.RARITY_TREASURE);
			
			//earth
			newSpell = new Spell("Earthquake", simgIndex, 5f, Common.AGILE, Common.TYPE_EARTH, Common.TYPE_NONE, 5, true);
			initSpell(newSpell, Item.RARITY_TREASURE);
			newSpell = new Spell("Erosion", simgIndex, 4f, Common.AGILE, Common.TYPE_EARTH, Common.TYPE_VOID, 5, true);
			initSpell(newSpell, Item.RARITY_TREASURE);
			
			//wind
			
			//life
			
			//time
			
			//space
			newSpell = new Spell("Black Hole", simgIndex, 5f, Common.MAGICAL, Common.TYPE_SPACE, Common.TYPE_NONE, 5, true);
			initSpell(newSpell, Item.RARITY_TREASURE);
			
			//void
			newSpell = new Spell("Nightmare", simgIndex, 5f, Common.MAGICAL, Common.TYPE_VOID, Common.TYPE_NONE, 5, true);
			initSpell(newSpell, Item.RARITY_TREASURE);

			/*
			 * RARITY 6 Spell
			 * 
			 */
			
			//neutral
			
			//fire
			
			//water
			
			//earth
			
			//wind
			
			//life
			newSpell = new Spell("Severance", simgIndex, 5f, Common.PHYSICAL, Common.TYPE_LIFE, Common.TYPE_VOID, 6, true);
			initSpell(newSpell, Item.RARITY_LEGENDARY);
			
			//time
			
			//space
			
			//void

			/*
			 * RARITY 7 Spell
			 * 
			 */
			
			//neutral
			
			//fire
			
			//water
			
			//earth
			
			//wind
			
			//life
			
			//time
			newSpell = new Spell("Paradox", simgIndex, 10f, Common.MAGICAL, Common.TYPE_TIME, Common.TYPE_NONE, 7, true);
			initSpell(newSpell, Item.RARITY_ANCIENT);
			
			//space
			
			//void

			/*
			 * RARITY 8 Spell
			 * 
			 */
			
			//neutral
			
			//fire
			newSpell = new Spell("Phoenix Cannon", simgIndex, 10f, Common.MAGICAL, Common.TYPE_FIRE, Common.TYPE_VOID, 8, true);
			initSpell(newSpell, Item.RARITY_RELIC);
			
			//water
			
			//earth
			
			//wind
			
			//life
			
			//time
			newSpell = new Spell("The Beginning", simgIndex, 15f, Common.PHYSICAL, Common.TYPE_TIME, Common.TYPE_FIRE, 8, true);
			initSpell(newSpell, Item.RARITY_RELIC);
			
			//space
			newSpell = new Spell("The End", simgIndex, 15f, Common.PHYSICAL, Common.TYPE_SPACE, Common.TYPE_VOID, 8, true);
			initSpell(newSpell, Item.RARITY_RELIC);
			
			//void

			/*
			 * RARITY 9 Spell
			 * 
			 */
			
			//neutral
			
			//fire
			newSpell = new Spell("Phoenix Rebirth", simgIndex, 1f, Common.PHYSICAL, Common.TYPE_FIRE, Common.TYPE_WATER, 9, true);
			initSpell(newSpell, Item.RARITY_GODLIKE);
			
			//water
			newSpell = new Spell("Frostspawn", simgIndex, 2f, Common.MAGICAL, Common.TYPE_WATER, Common.TYPE_WIND, 9, true);
			initSpell(newSpell, Item.RARITY_GODLIKE);
			
			//earth
			
			//wind
			
			//life
			
			//time
			
			//space
			newSpell = new Spell("Big Bang", simgIndex, 25f, Common.PHYSICAL, Common.TYPE_SPACE, Common.TYPE_TIME, 9, true);
			initSpell(newSpell, Item.RARITY_GODLIKE);
			
			//void
			
			
			//lets print all of the abilities so that we can easily get them later!
			if(coreModule.gameModule.gameConfig.isDebugging())
			{
				for(int t : new int[]{Common.TYPE_NONE,Common.TYPE_FIRE,Common.TYPE_WATER,Common.TYPE_EARTH,Common.TYPE_WIND,Common.TYPE_LIFE,Common.TYPE_TIME,Common.TYPE_SPACE,Common.TYPE_VOID})
				{
					System.out.println("\n\n"+Common.TypeToString(t)+" abilities:");
					for(int i=1; i<=9; i++)
					{
						System.out.println("\n\n\tRank "+i+" abilities:");
						for(Ability s : coreModule.ability_templates)
						{
							if((s.type1 == t || s.type2 == t) && i==s.getRank())
							{
								System.out.println("\n\n\t\t"+s.name+" "+s.getRawDamageMultiplier());
							}
						}
					}
				}
			}
			
			
		Weapon newWep = null;
		//weapons
		/*
		 * Rarity 0 Weapon
		 * 
		 */
		newWep = new Weapon("Wk. Sword", 280, 50, Weapon.TYPE_SWORD, Common.TYPE_NONE, 1, true);
		initWeapon(newWep, Item.RARITY_COMMON);
		newWep = new Weapon("Wk. Pentagram", 281, 50, Weapon.TYPE_PENTAGRAM, Common.TYPE_NONE, 1, true);
		initWeapon(newWep, Item.RARITY_COMMON);
		newWep = new Weapon("Wk. Big Swrd", 282, 50, Weapon.TYPE_GIANT_SWORD, Common.TYPE_NONE, 1, true);
		initWeapon(newWep, Item.RARITY_COMMON);
		newWep = new Weapon("Wk. Rifle", 283, 50, Weapon.TYPE_RIFLE, Common.TYPE_NONE, 1, true);
		initWeapon(newWep, Item.RARITY_COMMON);
		newWep = new Weapon("Wk. Hammer", 284, 50, Weapon.TYPE_HAMMER, Common.TYPE_NONE, 1, true);
		initWeapon(newWep, Item.RARITY_COMMON);
		newWep = new Weapon("Wk. Bow", 285, 50, Weapon.TYPE_BOW, Common.TYPE_NONE, 1, true);
		initWeapon(newWep, Item.RARITY_COMMON);
		newWep = new Weapon("Wk. Wand", 286, 50, Weapon.TYPE_WAND, Common.TYPE_NONE, 1, true);
		initWeapon(newWep, Item.RARITY_COMMON);
		newWep = new Weapon("Wk. Dagger", 287, 50, Weapon.TYPE_KNIFE, Common.TYPE_NONE, 1, true);
		initWeapon(newWep, Item.RARITY_COMMON);
		newWep = new Weapon("Wk. Gloves", 288, 50, Weapon.TYPE_KNUCKLES, Common.TYPE_NONE, 1, true);
		initWeapon(newWep, Item.RARITY_COMMON);
		newWep = new Weapon("Wk. Polearm", 289, 50, Weapon.TYPE_STAFF, Common.TYPE_NONE, 1, true);
		initWeapon(newWep, Item.RARITY_COMMON);
		
		/*
		 * Rarity 2 Weapon
		 * 
		 */
		newWep = new Weapon("Std. Sword", 280, 500, Weapon.TYPE_SWORD, Common.TYPE_NONE, 2, true);
		initWeapon(newWep, Item.RARITY_SCARCE);
		newWep = new Weapon("Std. Pentagram", 281, 500, Weapon.TYPE_PENTAGRAM, Common.TYPE_NONE, 2, true);
		initWeapon(newWep, Item.RARITY_SCARCE);
		newWep = new Weapon("Std. Big Swrd", 282, 500, Weapon.TYPE_GIANT_SWORD, Common.TYPE_NONE, 2, true);
		initWeapon(newWep, Item.RARITY_SCARCE);
		newWep = new Weapon("Std. Rifle", 283, 500, Weapon.TYPE_RIFLE, Common.TYPE_NONE, 2, true);
		initWeapon(newWep, Item.RARITY_SCARCE);
		newWep = new Weapon("Std. Hammer", 284, 500, Weapon.TYPE_HAMMER, Common.TYPE_NONE, 2, true);
		initWeapon(newWep, Item.RARITY_SCARCE);
		newWep = new Weapon("Std. Bow", 285, 500, Weapon.TYPE_BOW, Common.TYPE_NONE, 2, true);
		initWeapon(newWep, Item.RARITY_SCARCE);
		newWep = new Weapon("Std. Wand", 286, 500, Weapon.TYPE_WAND, Common.TYPE_NONE, 2, true);
		initWeapon(newWep, Item.RARITY_SCARCE);
		newWep = new Weapon("Std. Dagger", 287, 500, Weapon.TYPE_KNIFE, Common.TYPE_NONE, 2, true);
		initWeapon(newWep, Item.RARITY_SCARCE);
		newWep = new Weapon("Std. Gloves", 288, 500, Weapon.TYPE_KNUCKLES, Common.TYPE_NONE, 2, true);
		initWeapon(newWep, Item.RARITY_SCARCE);
		newWep = new Weapon("Std. Polearm", 289, 500, Weapon.TYPE_STAFF, Common.TYPE_NONE, 2, true);
		initWeapon(newWep, Item.RARITY_SCARCE);
		
		/*
		 * Rarity 3 Weapon
		 * 
		 */
		newWep = new Weapon("H. Sword", 280, 1500, Weapon.TYPE_SWORD, Common.TYPE_FIRE, 3, true);
		initWeapon(newWep, Item.RARITY_RARE);
		newWep = new Weapon("H. Pentagram", 281, 1500, Weapon.TYPE_PENTAGRAM, Common.TYPE_FIRE, 3, true);
		initWeapon(newWep, Item.RARITY_RARE);
		newWep = new Weapon("H. Big Swrd", 282, 1500, Weapon.TYPE_GIANT_SWORD, Common.TYPE_FIRE, 3, true);
		initWeapon(newWep, Item.RARITY_RARE);
		newWep = new Weapon("H. Rifle", 283, 1500, Weapon.TYPE_RIFLE, Common.TYPE_FIRE, 3, true);
		initWeapon(newWep, Item.RARITY_RARE);
		newWep = new Weapon("H. Hammer", 284, 1500, Weapon.TYPE_HAMMER, Common.TYPE_FIRE, 3, true);
		initWeapon(newWep, Item.RARITY_RARE);
		newWep = new Weapon("H. Bow", 285, 1500, Weapon.TYPE_BOW, Common.TYPE_FIRE, 3, true);
		initWeapon(newWep, Item.RARITY_RARE);
		newWep = new Weapon("H. Wand", 286, 1500, Weapon.TYPE_WAND, Common.TYPE_FIRE, 3, true);
		initWeapon(newWep, Item.RARITY_RARE);
		newWep = new Weapon("H. Dagger", 287, 1500, Weapon.TYPE_KNIFE, Common.TYPE_FIRE, 3, true);
		initWeapon(newWep, Item.RARITY_RARE);
		newWep = new Weapon("H. Gloves", 288, 1500, Weapon.TYPE_KNUCKLES, Common.TYPE_FIRE, 3, true);
		initWeapon(newWep, Item.RARITY_RARE);
		newWep = new Weapon("H. Polearm", 289, 1500, Weapon.TYPE_STAFF, Common.TYPE_FIRE, 3, true);
		initWeapon(newWep, Item.RARITY_RARE);
		newWep = new Weapon("W. Sword", 280, 1500, Weapon.TYPE_SWORD, Common.TYPE_WATER, 3, true);
		initWeapon(newWep, Item.RARITY_RARE);
		newWep = new Weapon("W. Pentagram", 281, 1500, Weapon.TYPE_PENTAGRAM, Common.TYPE_WATER, 3, true);
		initWeapon(newWep, Item.RARITY_RARE);
		newWep = new Weapon("W. Big Swrd", 282, 1500, Weapon.TYPE_GIANT_SWORD, Common.TYPE_WATER, 3, true);
		initWeapon(newWep, Item.RARITY_RARE);
		newWep = new Weapon("W. Rifle", 283, 1500, Weapon.TYPE_RIFLE, Common.TYPE_WATER, 3, true);
		initWeapon(newWep, Item.RARITY_RARE);
		newWep = new Weapon("W. Hammer", 284, 1500, Weapon.TYPE_HAMMER, Common.TYPE_WATER, 3, true);
		initWeapon(newWep, Item.RARITY_RARE);
		newWep = new Weapon("W. Bow", 285, 1500, Weapon.TYPE_BOW, Common.TYPE_WATER, 3, true);
		initWeapon(newWep, Item.RARITY_RARE);
		newWep = new Weapon("W. Wand", 286, 1500, Weapon.TYPE_WAND, Common.TYPE_WATER, 3, true);
		initWeapon(newWep, Item.RARITY_RARE);
		newWep = new Weapon("W. Dagger", 287, 1500, Weapon.TYPE_KNIFE, Common.TYPE_WATER, 3, true);
		initWeapon(newWep, Item.RARITY_RARE);
		newWep = new Weapon("W. Gloves", 288, 1500, Weapon.TYPE_KNUCKLES, Common.TYPE_WATER, 3, true);
		initWeapon(newWep, Item.RARITY_RARE);
		newWep = new Weapon("W. Polearm", 289, 1500, Weapon.TYPE_STAFF, Common.TYPE_WATER, 3, true);
		initWeapon(newWep, Item.RARITY_RARE);
		newWep = new Weapon("C. Sword", 280, 1500, Weapon.TYPE_SWORD, Common.TYPE_EARTH, 3, true);
		initWeapon(newWep, Item.RARITY_RARE);
		newWep = new Weapon("C. Pentagram", 281, 1500, Weapon.TYPE_PENTAGRAM, Common.TYPE_EARTH, 3, true);
		initWeapon(newWep, Item.RARITY_RARE);
		newWep = new Weapon("C. Big Swrd", 282, 1500, Weapon.TYPE_GIANT_SWORD, Common.TYPE_EARTH, 3, true);
		initWeapon(newWep, Item.RARITY_RARE);
		newWep = new Weapon("C. Rifle", 283, 1500, Weapon.TYPE_RIFLE, Common.TYPE_EARTH, 3, true);
		initWeapon(newWep, Item.RARITY_RARE);
		newWep = new Weapon("C. Hammer", 284, 1500, Weapon.TYPE_HAMMER, Common.TYPE_EARTH, 3, true);
		initWeapon(newWep, Item.RARITY_RARE);
		newWep = new Weapon("C. Bow", 285, 1500, Weapon.TYPE_BOW, Common.TYPE_EARTH, 3, true);
		initWeapon(newWep, Item.RARITY_RARE);
		newWep = new Weapon("C. Wand", 286, 1500, Weapon.TYPE_WAND, Common.TYPE_EARTH, 3, true);
		initWeapon(newWep, Item.RARITY_RARE);
		newWep = new Weapon("C. Dagger", 287, 1500, Weapon.TYPE_KNIFE, Common.TYPE_EARTH, 3, true);
		initWeapon(newWep, Item.RARITY_RARE);
		newWep = new Weapon("C. Gloves", 288, 1500, Weapon.TYPE_KNUCKLES, Common.TYPE_EARTH, 3, true);
		initWeapon(newWep, Item.RARITY_RARE);
		newWep = new Weapon("C. Polearm", 289, 1500, Weapon.TYPE_STAFF, Common.TYPE_EARTH, 3, true);
		initWeapon(newWep, Item.RARITY_RARE);
		newWep = new Weapon("B. Sword", 280, 1500, Weapon.TYPE_SWORD, Common.TYPE_WIND, 3, true);
		initWeapon(newWep, Item.RARITY_RARE);
		newWep = new Weapon("B. Pentagram", 281, 1500, Weapon.TYPE_PENTAGRAM, Common.TYPE_WIND, 3, true);
		initWeapon(newWep, Item.RARITY_RARE);
		newWep = new Weapon("B. Big Swrd", 282, 1500, Weapon.TYPE_GIANT_SWORD, Common.TYPE_WIND, 3, true);
		initWeapon(newWep, Item.RARITY_RARE);
		newWep = new Weapon("B. Rifle", 283, 1500, Weapon.TYPE_RIFLE, Common.TYPE_WIND, 3, true);
		initWeapon(newWep, Item.RARITY_RARE);
		newWep = new Weapon("B. Hammer", 284, 1500, Weapon.TYPE_HAMMER, Common.TYPE_WIND, 3, true);
		initWeapon(newWep, Item.RARITY_RARE);
		newWep = new Weapon("B. Bow", 285, 1500, Weapon.TYPE_BOW, Common.TYPE_WIND, 3, true);
		initWeapon(newWep, Item.RARITY_RARE);
		newWep = new Weapon("B. Wand", 286, 1500, Weapon.TYPE_WAND, Common.TYPE_WIND, 3, true);
		initWeapon(newWep, Item.RARITY_RARE);
		newWep = new Weapon("B. Dagger", 287, 1500, Weapon.TYPE_KNIFE, Common.TYPE_WIND, 3, true);
		initWeapon(newWep, Item.RARITY_RARE);
		newWep = new Weapon("B. Gloves", 288, 1500, Weapon.TYPE_KNUCKLES, Common.TYPE_WIND, 3, true);
		initWeapon(newWep, Item.RARITY_RARE);
		newWep = new Weapon("B. Polearm", 289, 1500, Weapon.TYPE_STAFF, Common.TYPE_WIND, 3, true);
		initWeapon(newWep, Item.RARITY_RARE);
		newWep = new Weapon("R. Sword", 280, 1500, Weapon.TYPE_SWORD, Common.TYPE_LIFE, 3, true);
		initWeapon(newWep, Item.RARITY_RARE);
		newWep = new Weapon("R. Pentagram", 281, 1500, Weapon.TYPE_PENTAGRAM, Common.TYPE_LIFE, 3, true);
		initWeapon(newWep, Item.RARITY_RARE);
		newWep = new Weapon("R. Big Swrd", 282, 1500, Weapon.TYPE_GIANT_SWORD, Common.TYPE_LIFE, 3, true);
		initWeapon(newWep, Item.RARITY_RARE);
		newWep = new Weapon("R. Rifle", 283, 1500, Weapon.TYPE_RIFLE, Common.TYPE_LIFE, 3, true);
		initWeapon(newWep, Item.RARITY_RARE);
		newWep = new Weapon("R. Hammer", 284, 1500, Weapon.TYPE_HAMMER, Common.TYPE_LIFE, 3, true);
		initWeapon(newWep, Item.RARITY_RARE);
		newWep = new Weapon("R. Bow", 285, 1500, Weapon.TYPE_BOW, Common.TYPE_LIFE, 3, true);
		initWeapon(newWep, Item.RARITY_RARE);
		newWep = new Weapon("R. Wand", 286, 1500, Weapon.TYPE_WAND, Common.TYPE_LIFE, 3, true);
		initWeapon(newWep, Item.RARITY_RARE);
		newWep = new Weapon("R. Dagger", 287, 1500, Weapon.TYPE_KNIFE, Common.TYPE_LIFE, 3, true);
		initWeapon(newWep, Item.RARITY_RARE);
		newWep = new Weapon("R. Gloves", 288, 1500, Weapon.TYPE_KNUCKLES, Common.TYPE_LIFE, 3, true);
		initWeapon(newWep, Item.RARITY_RARE);
		newWep = new Weapon("R. Polearm", 289, 1500, Weapon.TYPE_STAFF, Common.TYPE_LIFE, 3, true);
		initWeapon(newWep, Item.RARITY_RARE);
		newWep = new Weapon("T. Sword", 280, 1500, Weapon.TYPE_SWORD, Common.TYPE_TIME, 3, true);
		initWeapon(newWep, Item.RARITY_RARE);
		newWep = new Weapon("T. Pentagram", 281, 1500, Weapon.TYPE_PENTAGRAM, Common.TYPE_TIME, 3, true);
		initWeapon(newWep, Item.RARITY_RARE);
		newWep = new Weapon("T. Big Swrd", 282, 1500, Weapon.TYPE_GIANT_SWORD, Common.TYPE_TIME, 3, true);
		initWeapon(newWep, Item.RARITY_RARE);
		newWep = new Weapon("T. Rifle", 283, 1500, Weapon.TYPE_RIFLE, Common.TYPE_TIME, 3, true);
		initWeapon(newWep, Item.RARITY_RARE);
		newWep = new Weapon("T. Hammer", 284, 1500, Weapon.TYPE_HAMMER, Common.TYPE_TIME, 3, true);
		initWeapon(newWep, Item.RARITY_RARE);
		newWep = new Weapon("T. Bow", 285, 1500, Weapon.TYPE_BOW, Common.TYPE_TIME, 3, true);
		initWeapon(newWep, Item.RARITY_RARE);
		newWep = new Weapon("T. Wand", 286, 1500, Weapon.TYPE_WAND, Common.TYPE_TIME, 3, true);
		initWeapon(newWep, Item.RARITY_RARE);
		newWep = new Weapon("T. Dagger", 287, 1500, Weapon.TYPE_KNIFE, Common.TYPE_TIME, 3, true);
		initWeapon(newWep, Item.RARITY_RARE);
		newWep = new Weapon("T. Gloves", 288, 1500, Weapon.TYPE_KNUCKLES, Common.TYPE_TIME, 3, true);
		initWeapon(newWep, Item.RARITY_RARE);
		newWep = new Weapon("T. Polearm", 289, 1500, Weapon.TYPE_STAFF, Common.TYPE_TIME, 3, true);
		initWeapon(newWep, Item.RARITY_RARE);
		newWep = new Weapon("S. Sword", 280, 1500, Weapon.TYPE_SWORD, Common.TYPE_SPACE, 3, true);
		initWeapon(newWep, Item.RARITY_RARE);
		newWep = new Weapon("S. Pentagram", 281, 1500, Weapon.TYPE_PENTAGRAM, Common.TYPE_SPACE, 3, true);
		initWeapon(newWep, Item.RARITY_RARE);
		newWep = new Weapon("S. Big Swrd", 282, 1500, Weapon.TYPE_GIANT_SWORD, Common.TYPE_SPACE, 3, true);
		initWeapon(newWep, Item.RARITY_RARE);
		newWep = new Weapon("S. Rifle", 283, 1500, Weapon.TYPE_RIFLE, Common.TYPE_SPACE, 3, true);
		initWeapon(newWep, Item.RARITY_RARE);
		newWep = new Weapon("S. Hammer", 284, 1500, Weapon.TYPE_HAMMER, Common.TYPE_SPACE, 3, true);
		initWeapon(newWep, Item.RARITY_RARE);
		newWep = new Weapon("S. Bow", 285, 1500, Weapon.TYPE_BOW, Common.TYPE_SPACE, 3, true);
		initWeapon(newWep, Item.RARITY_RARE);
		newWep = new Weapon("S. Wand", 286, 1500, Weapon.TYPE_WAND, Common.TYPE_SPACE, 3, true);
		initWeapon(newWep, Item.RARITY_RARE);
		newWep = new Weapon("S. Dagger", 287, 1500, Weapon.TYPE_KNIFE, Common.TYPE_SPACE, 3, true);
		initWeapon(newWep, Item.RARITY_RARE);
		newWep = new Weapon("S. Gloves", 288, 1500, Weapon.TYPE_KNUCKLES, Common.TYPE_SPACE, 3, true);
		initWeapon(newWep, Item.RARITY_RARE);
		newWep = new Weapon("S. Polearm", 289, 1500, Weapon.TYPE_STAFF, Common.TYPE_SPACE, 3, true);
		initWeapon(newWep, Item.RARITY_RARE);
		newWep = new Weapon("V. Sword", 280, 1500, Weapon.TYPE_SWORD, Common.TYPE_VOID, 3, true);
		initWeapon(newWep, Item.RARITY_RARE);
		newWep = new Weapon("V. Pentagram", 281, 1500, Weapon.TYPE_PENTAGRAM, Common.TYPE_VOID, 3, true);
		initWeapon(newWep, Item.RARITY_RARE);
		newWep = new Weapon("V. Big Swrd", 282, 1500, Weapon.TYPE_GIANT_SWORD, Common.TYPE_VOID, 3, true);
		initWeapon(newWep, Item.RARITY_RARE);
		newWep = new Weapon("V. Rifle", 283, 1500, Weapon.TYPE_RIFLE, Common.TYPE_VOID, 3, true);
		initWeapon(newWep, Item.RARITY_RARE);
		newWep = new Weapon("V. Hammer", 284, 1500, Weapon.TYPE_HAMMER, Common.TYPE_VOID, 3, true);
		initWeapon(newWep, Item.RARITY_RARE);
		newWep = new Weapon("V. Bow", 285, 1500, Weapon.TYPE_BOW, Common.TYPE_VOID, 3, true);
		initWeapon(newWep, Item.RARITY_RARE);
		newWep = new Weapon("V. Wand", 286, 1500, Weapon.TYPE_WAND, Common.TYPE_VOID, 3, true);
		initWeapon(newWep, Item.RARITY_RARE);
		newWep = new Weapon("V. Dagger", 287, 1500, Weapon.TYPE_KNIFE, Common.TYPE_VOID, 3, true);
		initWeapon(newWep, Item.RARITY_RARE);
		newWep = new Weapon("V. Gloves", 288, 1500, Weapon.TYPE_KNUCKLES, Common.TYPE_VOID, 3, true);
		initWeapon(newWep, Item.RARITY_RARE);
		newWep = new Weapon("V. Polearm", 289, 1500, Weapon.TYPE_STAFF, Common.TYPE_VOID, 3, true);
		initWeapon(newWep, Item.RARITY_RARE);
		
		/*
		 * Rarity 5 Weapon
		 * 
		 */
		newWep = new Weapon("I. Sword", 280, 2500, Weapon.TYPE_SWORD, Common.TYPE_FIRE, 5, true);
		initWeapon(newWep, Item.RARITY_TREASURE);
		newWep = new Weapon("I. Pentagram", 281, 2500, Weapon.TYPE_PENTAGRAM, Common.TYPE_FIRE, 5, true);
		initWeapon(newWep, Item.RARITY_TREASURE);
		newWep = new Weapon("I. Big Swrd", 282, 2500, Weapon.TYPE_GIANT_SWORD, Common.TYPE_FIRE, 5, true);
		initWeapon(newWep, Item.RARITY_TREASURE);
		newWep = new Weapon("I. Rifle", 283, 2500, Weapon.TYPE_RIFLE, Common.TYPE_FIRE, 5, true);
		initWeapon(newWep, Item.RARITY_TREASURE);
		newWep = new Weapon("I. Hammer", 284, 2500, Weapon.TYPE_HAMMER, Common.TYPE_FIRE, 5, true);
		initWeapon(newWep, Item.RARITY_TREASURE);
		newWep = new Weapon("I. Bow", 285, 2500, Weapon.TYPE_BOW, Common.TYPE_FIRE, 5, true);
		initWeapon(newWep, Item.RARITY_TREASURE);
		newWep = new Weapon("I. Wand", 286, 2500, Weapon.TYPE_WAND, Common.TYPE_FIRE, 5, true);
		initWeapon(newWep, Item.RARITY_TREASURE);
		newWep = new Weapon("I. Dagger", 287, 2500, Weapon.TYPE_KNIFE, Common.TYPE_FIRE, 5, true);
		initWeapon(newWep, Item.RARITY_TREASURE);
		newWep = new Weapon("I. Gloves", 288, 2500, Weapon.TYPE_KNUCKLES, Common.TYPE_FIRE, 5, true);
		initWeapon(newWep, Item.RARITY_TREASURE);
		newWep = new Weapon("I. Polearm", 289, 2500, Weapon.TYPE_STAFF, Common.TYPE_FIRE, 5, true);
		initWeapon(newWep, Item.RARITY_TREASURE);
		newWep = new Weapon("M. Sword", 280, 2500, Weapon.TYPE_SWORD, Common.TYPE_WATER, 5, true);
		initWeapon(newWep, Item.RARITY_TREASURE);
		newWep = new Weapon("M. Pentagram", 281, 2500, Weapon.TYPE_PENTAGRAM, Common.TYPE_WATER, 5, true);
		initWeapon(newWep, Item.RARITY_TREASURE);
		newWep = new Weapon("M. Big Swrd", 282, 2500, Weapon.TYPE_GIANT_SWORD, Common.TYPE_WATER, 5, true);
		initWeapon(newWep, Item.RARITY_TREASURE);
		newWep = new Weapon("M. Rifle", 283, 2500, Weapon.TYPE_RIFLE, Common.TYPE_WATER, 5, true);
		initWeapon(newWep, Item.RARITY_TREASURE);
		newWep = new Weapon("M. Hammer", 284, 2500, Weapon.TYPE_HAMMER, Common.TYPE_WATER, 5, true);
		initWeapon(newWep, Item.RARITY_TREASURE);
		newWep = new Weapon("M. Bow", 285, 2500, Weapon.TYPE_BOW, Common.TYPE_WATER, 5, true);
		initWeapon(newWep, Item.RARITY_TREASURE);
		newWep = new Weapon("M. Wand", 286, 2500, Weapon.TYPE_WAND, Common.TYPE_WATER, 5, true);
		initWeapon(newWep, Item.RARITY_TREASURE);
		newWep = new Weapon("M. Dagger", 287, 2500, Weapon.TYPE_KNIFE, Common.TYPE_WATER, 5, true);
		initWeapon(newWep, Item.RARITY_TREASURE);
		newWep = new Weapon("M. Gloves", 288, 2500, Weapon.TYPE_KNUCKLES, Common.TYPE_WATER, 5, true);
		initWeapon(newWep, Item.RARITY_TREASURE);
		newWep = new Weapon("M. Polearm", 289, 2500, Weapon.TYPE_STAFF, Common.TYPE_WATER, 5, true);
		initWeapon(newWep, Item.RARITY_TREASURE);
		newWep = new Weapon("G. Sword", 280, 2500, Weapon.TYPE_SWORD, Common.TYPE_EARTH, 5, true);
		initWeapon(newWep, Item.RARITY_TREASURE);
		newWep = new Weapon("G. Pentagram", 281, 2500, Weapon.TYPE_PENTAGRAM, Common.TYPE_EARTH, 5, true);
		initWeapon(newWep, Item.RARITY_TREASURE);
		newWep = new Weapon("G. Big Swrd", 282, 2500, Weapon.TYPE_GIANT_SWORD, Common.TYPE_EARTH, 5, true);
		initWeapon(newWep, Item.RARITY_TREASURE);
		newWep = new Weapon("G. Rifle", 283, 2500, Weapon.TYPE_RIFLE, Common.TYPE_EARTH, 5, true);
		initWeapon(newWep, Item.RARITY_TREASURE);
		newWep = new Weapon("G. Hammer", 284, 2500, Weapon.TYPE_HAMMER, Common.TYPE_EARTH, 5, true);
		initWeapon(newWep, Item.RARITY_TREASURE);
		newWep = new Weapon("G. Bow", 285, 2500, Weapon.TYPE_BOW, Common.TYPE_EARTH, 5, true);
		initWeapon(newWep, Item.RARITY_TREASURE);
		newWep = new Weapon("G. Wand", 286, 2500, Weapon.TYPE_WAND, Common.TYPE_EARTH, 5, true);
		initWeapon(newWep, Item.RARITY_TREASURE);
		newWep = new Weapon("G. Dagger", 287, 2500, Weapon.TYPE_KNIFE, Common.TYPE_EARTH, 5, true);
		initWeapon(newWep, Item.RARITY_TREASURE);
		newWep = new Weapon("G. Gloves", 288, 2500, Weapon.TYPE_KNUCKLES, Common.TYPE_EARTH, 5, true);
		initWeapon(newWep, Item.RARITY_TREASURE);
		newWep = new Weapon("G. Polearm", 289, 2500, Weapon.TYPE_STAFF, Common.TYPE_EARTH, 5, true);
		initWeapon(newWep, Item.RARITY_TREASURE);
		newWep = new Weapon("L. Sword", 280, 2500, Weapon.TYPE_SWORD, Common.TYPE_WIND, 5, true);
		initWeapon(newWep, Item.RARITY_TREASURE);
		newWep = new Weapon("L. Pentagram", 281, 2500, Weapon.TYPE_PENTAGRAM, Common.TYPE_WIND, 5, true);
		initWeapon(newWep, Item.RARITY_TREASURE);
		newWep = new Weapon("L. Big Swrd", 282, 2500, Weapon.TYPE_GIANT_SWORD, Common.TYPE_WIND, 5, true);
		initWeapon(newWep, Item.RARITY_TREASURE);
		newWep = new Weapon("L. Rifle", 283, 2500, Weapon.TYPE_RIFLE, Common.TYPE_WIND, 5, true);
		initWeapon(newWep, Item.RARITY_TREASURE);
		newWep = new Weapon("L. Hammer", 284, 2500, Weapon.TYPE_HAMMER, Common.TYPE_WIND, 5, true);
		initWeapon(newWep, Item.RARITY_TREASURE);
		newWep = new Weapon("L. Bow", 285, 2500, Weapon.TYPE_BOW, Common.TYPE_WIND, 5, true);
		initWeapon(newWep, Item.RARITY_TREASURE);
		newWep = new Weapon("L. Wand", 286, 2500, Weapon.TYPE_WAND, Common.TYPE_WIND, 5, true);
		initWeapon(newWep, Item.RARITY_TREASURE);
		newWep = new Weapon("L. Dagger", 287, 2500, Weapon.TYPE_KNIFE, Common.TYPE_WIND, 5, true);
		initWeapon(newWep, Item.RARITY_TREASURE);
		newWep = new Weapon("L. Gloves", 288, 2500, Weapon.TYPE_KNUCKLES, Common.TYPE_WIND, 5, true);
		initWeapon(newWep, Item.RARITY_TREASURE);
		newWep = new Weapon("L. Polearm", 289, 2500, Weapon.TYPE_STAFF, Common.TYPE_WIND, 5, true);
		initWeapon(newWep, Item.RARITY_TREASURE);
		newWep = new Weapon("Z. Sword", 280, 2500, Weapon.TYPE_SWORD, Common.TYPE_LIFE, 5, true);
		initWeapon(newWep, Item.RARITY_TREASURE);
		newWep = new Weapon("Z. Pentagram", 281, 2500, Weapon.TYPE_PENTAGRAM, Common.TYPE_LIFE, 5, true);
		initWeapon(newWep, Item.RARITY_TREASURE);
		newWep = new Weapon("Z. Big Swrd", 282, 2500, Weapon.TYPE_GIANT_SWORD, Common.TYPE_LIFE, 5, true);
		initWeapon(newWep, Item.RARITY_TREASURE);
		newWep = new Weapon("Z. Rifle", 283, 2500, Weapon.TYPE_RIFLE, Common.TYPE_LIFE, 5, true);
		initWeapon(newWep, Item.RARITY_TREASURE);
		newWep = new Weapon("Z. Hammer", 284, 2500, Weapon.TYPE_HAMMER, Common.TYPE_LIFE, 5, true);
		initWeapon(newWep, Item.RARITY_TREASURE);
		newWep = new Weapon("Z. Bow", 285, 2500, Weapon.TYPE_BOW, Common.TYPE_LIFE, 5, true);
		initWeapon(newWep, Item.RARITY_TREASURE);
		newWep = new Weapon("Z. Wand", 286, 2500, Weapon.TYPE_WAND, Common.TYPE_LIFE, 5, true);
		initWeapon(newWep, Item.RARITY_TREASURE);
		newWep = new Weapon("Z. Dagger", 287, 2500, Weapon.TYPE_KNIFE, Common.TYPE_LIFE, 5, true);
		initWeapon(newWep, Item.RARITY_TREASURE);
		newWep = new Weapon("Z. Gloves", 288, 2500, Weapon.TYPE_KNUCKLES, Common.TYPE_LIFE, 5, true);
		initWeapon(newWep, Item.RARITY_TREASURE);
		newWep = new Weapon("Z. Polearm", 289, 2500, Weapon.TYPE_STAFF, Common.TYPE_LIFE, 5, true);
		initWeapon(newWep, Item.RARITY_TREASURE);
		newWep = new Weapon("F. Sword", 280, 2500, Weapon.TYPE_SWORD, Common.TYPE_TIME, 5, true);
		initWeapon(newWep, Item.RARITY_TREASURE);
		newWep = new Weapon("F. Pentagram", 281, 2500, Weapon.TYPE_PENTAGRAM, Common.TYPE_TIME, 5, true);
		initWeapon(newWep, Item.RARITY_TREASURE);
		newWep = new Weapon("F. Big Swrd", 282, 2500, Weapon.TYPE_GIANT_SWORD, Common.TYPE_TIME, 5, true);
		initWeapon(newWep, Item.RARITY_TREASURE);
		newWep = new Weapon("F. Rifle", 283, 2500, Weapon.TYPE_RIFLE, Common.TYPE_TIME, 5, true);
		initWeapon(newWep, Item.RARITY_TREASURE);
		newWep = new Weapon("F. Hammer", 284, 2500, Weapon.TYPE_HAMMER, Common.TYPE_TIME, 5, true);
		initWeapon(newWep, Item.RARITY_TREASURE);
		newWep = new Weapon("F. Bow", 285, 2500, Weapon.TYPE_BOW, Common.TYPE_TIME, 5, true);
		initWeapon(newWep, Item.RARITY_TREASURE);
		newWep = new Weapon("F. Wand", 286, 2500, Weapon.TYPE_WAND, Common.TYPE_TIME, 5, true);
		initWeapon(newWep, Item.RARITY_TREASURE);
		newWep = new Weapon("F. Dagger", 287, 2500, Weapon.TYPE_KNIFE, Common.TYPE_TIME, 5, true);
		initWeapon(newWep, Item.RARITY_TREASURE);
		newWep = new Weapon("F. Gloves", 288, 2500, Weapon.TYPE_KNUCKLES, Common.TYPE_TIME, 5, true);
		initWeapon(newWep, Item.RARITY_TREASURE);
		newWep = new Weapon("F. Polearm", 289, 2500, Weapon.TYPE_STAFF, Common.TYPE_TIME, 5, true);
		initWeapon(newWep, Item.RARITY_TREASURE);
		newWep = new Weapon("Q. Sword", 280, 2500, Weapon.TYPE_SWORD, Common.TYPE_SPACE, 5, true);
		initWeapon(newWep, Item.RARITY_TREASURE);
		newWep = new Weapon("Q. Pentagram", 281, 2500, Weapon.TYPE_PENTAGRAM, Common.TYPE_SPACE, 5, true);
		initWeapon(newWep, Item.RARITY_TREASURE);
		newWep = new Weapon("Q. Big Swrd", 282, 2500, Weapon.TYPE_GIANT_SWORD, Common.TYPE_SPACE, 5, true);
		initWeapon(newWep, Item.RARITY_TREASURE);
		newWep = new Weapon("Q. Rifle", 283, 2500, Weapon.TYPE_RIFLE, Common.TYPE_SPACE, 5, true);
		initWeapon(newWep, Item.RARITY_TREASURE);
		newWep = new Weapon("Q. Hammer", 284, 2500, Weapon.TYPE_HAMMER, Common.TYPE_SPACE, 5, true);
		initWeapon(newWep, Item.RARITY_TREASURE);
		newWep = new Weapon("Q. Bow", 285, 2500, Weapon.TYPE_BOW, Common.TYPE_SPACE, 5, true);
		initWeapon(newWep, Item.RARITY_TREASURE);
		newWep = new Weapon("Q. Wand", 286, 2500, Weapon.TYPE_WAND, Common.TYPE_SPACE, 5, true);
		initWeapon(newWep, Item.RARITY_TREASURE);
		newWep = new Weapon("Q. Dagger", 287, 2500, Weapon.TYPE_KNIFE, Common.TYPE_SPACE, 5, true);
		initWeapon(newWep, Item.RARITY_TREASURE);
		newWep = new Weapon("Q. Gloves", 288, 2500, Weapon.TYPE_KNUCKLES, Common.TYPE_SPACE, 5, true);
		initWeapon(newWep, Item.RARITY_TREASURE);
		newWep = new Weapon("Q. Polearm", 289, 2500, Weapon.TYPE_STAFF, Common.TYPE_SPACE, 5, true);
		initWeapon(newWep, Item.RARITY_TREASURE);
		newWep = new Weapon("D. Sword", 280, 2500, Weapon.TYPE_SWORD, Common.TYPE_VOID, 5, true);
		initWeapon(newWep, Item.RARITY_TREASURE);
		newWep = new Weapon("D. Pentagram", 281, 2500, Weapon.TYPE_PENTAGRAM, Common.TYPE_VOID, 5, true);
		initWeapon(newWep, Item.RARITY_TREASURE);
		newWep = new Weapon("D. Big Swrd", 282, 2500, Weapon.TYPE_GIANT_SWORD, Common.TYPE_VOID, 5, true);
		initWeapon(newWep, Item.RARITY_TREASURE);
		newWep = new Weapon("D. Rifle", 283, 2500, Weapon.TYPE_RIFLE, Common.TYPE_VOID, 5, true);
		initWeapon(newWep, Item.RARITY_TREASURE);
		newWep = new Weapon("D. Hammer", 284, 2500, Weapon.TYPE_HAMMER, Common.TYPE_VOID, 5, true);
		initWeapon(newWep, Item.RARITY_TREASURE);
		newWep = new Weapon("D. Bow", 285, 2500, Weapon.TYPE_BOW, Common.TYPE_VOID, 5, true);
		initWeapon(newWep, Item.RARITY_TREASURE);
		newWep = new Weapon("D. Wand", 286, 2500, Weapon.TYPE_WAND, Common.TYPE_VOID, 5, true);
		initWeapon(newWep, Item.RARITY_TREASURE);
		newWep = new Weapon("D. Dagger", 287, 2500, Weapon.TYPE_KNIFE, Common.TYPE_VOID, 5, true);
		initWeapon(newWep, Item.RARITY_TREASURE);
		newWep = new Weapon("D. Gloves", 288, 2500, Weapon.TYPE_KNUCKLES, Common.TYPE_VOID, 5, true);
		initWeapon(newWep, Item.RARITY_TREASURE);
		newWep = new Weapon("D. Polearm", 289, 2500, Weapon.TYPE_STAFF, Common.TYPE_VOID, 5, true);
		initWeapon(newWep, Item.RARITY_TREASURE);
		
		/*
		 * Rarity 10 Weapon
		 * 
		 */
		newWep = new Weapon("Phoenix Blade", 189, 1000000, Weapon.TYPE_SWORD, Common.TYPE_FIRE, 10, true);
		initWeapon(newWep, Item.RARITY_GODLIKE);
		newWep = new Weapon("Frost Summoner", 188, 1000000, Weapon.TYPE_PENTAGRAM, Common.TYPE_WATER, 10, true);
		initWeapon(newWep, Item.RARITY_GODLIKE);
		

		Armor newArmor = null;
		//Armor
		/*
		 * Rarity 0 Armor
		 * 
		 */
		newArmor = new Armor("I. Hat", 240, Armor.TYPE_HELMET, 1, Common.TYPE_NONE);
		initArmor(newArmor, Item.RARITY_COMMON);
		newArmor = new Armor("H. Hat", 240, Armor.TYPE_HELMET, 1, Common.TYPE_FIRE);
		initArmor(newArmor, Item.RARITY_COMMON);
		newArmor = new Armor("S. Hat", 240, Armor.TYPE_HELMET, 1, Common.TYPE_WATER);
		initArmor(newArmor, Item.RARITY_COMMON);
		newArmor = new Armor("C. Hat", 240, Armor.TYPE_HELMET, 1, Common.TYPE_EARTH);
		initArmor(newArmor, Item.RARITY_COMMON);
		newArmor = new Armor("B. Hat", 240, Armor.TYPE_HELMET, 1, Common.TYPE_WIND);
		initArmor(newArmor, Item.RARITY_COMMON);
		newArmor = new Armor("V. Hat", 240, Armor.TYPE_HELMET, 1, Common.TYPE_LIFE);
		initArmor(newArmor, Item.RARITY_COMMON);
		newArmor = new Armor("T. Hat", 240, Armor.TYPE_HELMET, 1, Common.TYPE_TIME);
		initArmor(newArmor, Item.RARITY_COMMON);
		newArmor = new Armor("G. Hat", 240, Armor.TYPE_HELMET, 1, Common.TYPE_SPACE);
		initArmor(newArmor, Item.RARITY_COMMON);
		newArmor = new Armor("N. Hat", 240, Armor.TYPE_HELMET, 1, Common.TYPE_VOID);
		initArmor(newArmor, Item.RARITY_COMMON);

		/*
		 * Rarity 1 Armor
		 * 
		 */
		newArmor = new Armor("I. Tunic", 241, Armor.TYPE_CHESTPIECE, 2, Common.TYPE_NONE);
		initArmor(newArmor, Item.RARITY_UNCOMMON);
		newArmor = new Armor("H. Tunic", 241, Armor.TYPE_CHESTPIECE, 2, Common.TYPE_FIRE);
		initArmor(newArmor, Item.RARITY_UNCOMMON);
		newArmor = new Armor("S. Tunic", 241, Armor.TYPE_CHESTPIECE, 2, Common.TYPE_WATER);
		initArmor(newArmor, Item.RARITY_UNCOMMON);
		newArmor = new Armor("C. Tunic", 241, Armor.TYPE_CHESTPIECE, 2, Common.TYPE_EARTH);
		initArmor(newArmor, Item.RARITY_UNCOMMON);
		newArmor = new Armor("B. Tunic", 241, Armor.TYPE_CHESTPIECE, 2, Common.TYPE_WIND);
		initArmor(newArmor, Item.RARITY_UNCOMMON);
		newArmor = new Armor("V. Tunic", 241, Armor.TYPE_CHESTPIECE, 2, Common.TYPE_LIFE);
		initArmor(newArmor, Item.RARITY_UNCOMMON);
		newArmor = new Armor("T. Tunic", 241, Armor.TYPE_CHESTPIECE, 2, Common.TYPE_TIME);
		initArmor(newArmor, Item.RARITY_UNCOMMON);
		newArmor = new Armor("G. Tunic", 241, Armor.TYPE_CHESTPIECE, 2, Common.TYPE_SPACE);
		initArmor(newArmor, Item.RARITY_UNCOMMON);
		newArmor = new Armor("N. Tunic", 241, Armor.TYPE_CHESTPIECE, 2, Common.TYPE_VOID);
		initArmor(newArmor, Item.RARITY_UNCOMMON);

		/*
		 * Rarity 2 Armor
		 * 
		 */
		newArmor = new Armor("I. Boots", 242, Armor.TYPE_LEGGINGS, 3, Common.TYPE_NONE);
		initArmor(newArmor, Item.RARITY_SCARCE);
		newArmor = new Armor("H. Boots", 242, Armor.TYPE_LEGGINGS, 3, Common.TYPE_FIRE);
		initArmor(newArmor, Item.RARITY_SCARCE);
		newArmor = new Armor("S. Boots", 242, Armor.TYPE_LEGGINGS, 3, Common.TYPE_WATER);
		initArmor(newArmor, Item.RARITY_SCARCE);
		newArmor = new Armor("C. Boots", 242, Armor.TYPE_LEGGINGS, 3, Common.TYPE_EARTH);
		initArmor(newArmor, Item.RARITY_SCARCE);
		newArmor = new Armor("B. Boots", 242, Armor.TYPE_LEGGINGS, 3, Common.TYPE_WIND);
		initArmor(newArmor, Item.RARITY_SCARCE);
		newArmor = new Armor("V. Boots", 242, Armor.TYPE_LEGGINGS, 3, Common.TYPE_LIFE);
		initArmor(newArmor, Item.RARITY_SCARCE);
		newArmor = new Armor("T. Boots", 242, Armor.TYPE_LEGGINGS, 3, Common.TYPE_TIME);
		initArmor(newArmor, Item.RARITY_SCARCE);
		newArmor = new Armor("G. Boots", 242, Armor.TYPE_LEGGINGS, 3, Common.TYPE_SPACE);
		initArmor(newArmor, Item.RARITY_SCARCE);
		newArmor = new Armor("N. Boots", 242, Armor.TYPE_LEGGINGS, 3, Common.TYPE_VOID);
		initArmor(newArmor, Item.RARITY_SCARCE);
		
		/*
		 * Rarity 3 Armor
		 * 
		 */
		newArmor = new Armor("I. Helm", 240, Armor.TYPE_HELMET, 4, Common.TYPE_NONE);
		initArmor(newArmor, Item.RARITY_RARE);
		newArmor = new Armor("H. Helm", 240, Armor.TYPE_HELMET, 4, Common.TYPE_FIRE);
		initArmor(newArmor, Item.RARITY_RARE);
		newArmor = new Armor("S. Helm", 240, Armor.TYPE_HELMET, 4, Common.TYPE_WATER);
		initArmor(newArmor, Item.RARITY_RARE);
		newArmor = new Armor("C. Helm", 240, Armor.TYPE_HELMET, 4, Common.TYPE_EARTH);
		initArmor(newArmor, Item.RARITY_RARE);
		newArmor = new Armor("B. Helm", 240, Armor.TYPE_HELMET, 4, Common.TYPE_WIND);
		initArmor(newArmor, Item.RARITY_RARE);
		newArmor = new Armor("V. Helm", 240, Armor.TYPE_HELMET, 4, Common.TYPE_LIFE);
		initArmor(newArmor, Item.RARITY_RARE);
		newArmor = new Armor("T. Helm", 240, Armor.TYPE_HELMET, 4, Common.TYPE_TIME);
		initArmor(newArmor, Item.RARITY_RARE);
		newArmor = new Armor("G. Helm", 240, Armor.TYPE_HELMET, 4, Common.TYPE_SPACE);
		initArmor(newArmor, Item.RARITY_RARE);
		newArmor = new Armor("N. Helm", 240, Armor.TYPE_HELMET, 4, Common.TYPE_VOID);
		initArmor(newArmor, Item.RARITY_RARE);

		/*
		 * Rarity 4 Armor
		 * 
		 */
		newArmor = new Armor("I. Chestplate", 241, Armor.TYPE_CHESTPIECE, 5, Common.TYPE_NONE);
		initArmor(newArmor, Item.RARITY_UNIQUE);
		newArmor = new Armor("H. Chestplate", 241, Armor.TYPE_CHESTPIECE, 5, Common.TYPE_FIRE);
		initArmor(newArmor, Item.RARITY_UNIQUE);
		newArmor = new Armor("S. Chestplate", 241, Armor.TYPE_CHESTPIECE, 5, Common.TYPE_WATER);
		initArmor(newArmor, Item.RARITY_UNIQUE);
		newArmor = new Armor("C. Chestplate", 241, Armor.TYPE_CHESTPIECE, 5, Common.TYPE_EARTH);
		initArmor(newArmor, Item.RARITY_UNIQUE);
		newArmor = new Armor("B. Chestplate", 241, Armor.TYPE_CHESTPIECE, 5, Common.TYPE_WIND);
		initArmor(newArmor, Item.RARITY_UNIQUE);
		newArmor = new Armor("V. Chestplate", 241, Armor.TYPE_CHESTPIECE, 5, Common.TYPE_LIFE);
		initArmor(newArmor, Item.RARITY_UNIQUE);
		newArmor = new Armor("T. Chestplate", 241, Armor.TYPE_CHESTPIECE, 5, Common.TYPE_TIME);
		initArmor(newArmor, Item.RARITY_UNIQUE);
		newArmor = new Armor("G. Chestplate", 241, Armor.TYPE_CHESTPIECE, 5, Common.TYPE_SPACE);
		initArmor(newArmor, Item.RARITY_UNIQUE);
		newArmor = new Armor("N. Chestplate", 241, Armor.TYPE_CHESTPIECE, 5, Common.TYPE_VOID);
		initArmor(newArmor, Item.RARITY_UNIQUE);

		/*
		 * Rarity 5 Armor
		 * 
		 */
		newArmor = new Armor("I. Grieves", 242, Armor.TYPE_LEGGINGS, 6, Common.TYPE_NONE);
		initArmor(newArmor, Item.RARITY_TREASURE);
		newArmor = new Armor("H. Grieves", 242, Armor.TYPE_LEGGINGS, 6, Common.TYPE_FIRE);
		initArmor(newArmor, Item.RARITY_TREASURE);
		newArmor = new Armor("S. Grieves", 242, Armor.TYPE_LEGGINGS, 6, Common.TYPE_WATER);
		initArmor(newArmor, Item.RARITY_TREASURE);
		newArmor = new Armor("C. Grieves", 242, Armor.TYPE_LEGGINGS, 6, Common.TYPE_EARTH);
		initArmor(newArmor, Item.RARITY_TREASURE);
		newArmor = new Armor("B. Grieves", 242, Armor.TYPE_LEGGINGS, 6, Common.TYPE_WIND);
		initArmor(newArmor, Item.RARITY_TREASURE);
		newArmor = new Armor("V. Grieves", 242, Armor.TYPE_LEGGINGS, 6, Common.TYPE_LIFE);
		initArmor(newArmor, Item.RARITY_TREASURE);
		newArmor = new Armor("T. Grieves", 242, Armor.TYPE_LEGGINGS, 6, Common.TYPE_TIME);
		initArmor(newArmor, Item.RARITY_TREASURE);
		newArmor = new Armor("G. Grieves", 242, Armor.TYPE_LEGGINGS, 6, Common.TYPE_SPACE);
		initArmor(newArmor, Item.RARITY_TREASURE);
		newArmor = new Armor("N. Grieves", 242, Armor.TYPE_LEGGINGS, 6, Common.TYPE_VOID);
		initArmor(newArmor, Item.RARITY_TREASURE);
		
		
		//Consumables
			//rarity 0
		newItem = new Item("Apple", 3, Item.ITEM_TYPE_FOOD, Item.ITEM_EFFECT_NONE, 20, true);
		database_common.put(newItem.name, newItem);
		database.put(newItem.name, newItem);
		
			//rarity 3
		newItem = new Item("Strong Potion", 120, Item.ITEM_TYPE_POTION, Item.ITEM_EFFECT_STAT_STRENGTH, 500, true);
		database_rare.put(newItem.name, newItem);
		database.put(newItem.name, newItem);
		newItem = new Item("Tough Potion", 121, Item.ITEM_TYPE_POTION, Item.ITEM_EFFECT_STAT_TOUGHNESS, 500, true);
		database_rare.put(newItem.name, newItem);
		database.put(newItem.name, newItem);
		newItem = new Item("Smart Potion", 122, Item.ITEM_TYPE_POTION, Item.ITEM_EFFECT_STAT_INTELLIGENCE, 500, true);
		database_rare.put(newItem.name, newItem);
		database.put(newItem.name, newItem);
		newItem = new Item("Willful Potion", 123, Item.ITEM_TYPE_POTION, Item.ITEM_EFFECT_STAT_WILLPOWER, 500, true);
		database_rare.put(newItem.name, newItem);
		database.put(newItem.name, newItem);
		newItem = new Item("Fast Potion", 124, Item.ITEM_TYPE_POTION, Item.ITEM_EFFECT_STAT_AGILITY, 500, true);
		database_rare.put(newItem.name, newItem);
		database.put(newItem.name, newItem);
		newItem = new Item("Nimble Potion", 125, Item.ITEM_TYPE_POTION, Item.ITEM_EFFECT_STAT_DEXTERITY, 500, true);
		database_rare.put(newItem.name, newItem);
		database.put(newItem.name, newItem);
		newItem = new Item("Charisma Potion", 126, Item.ITEM_TYPE_POTION, Item.ITEM_EFFECT_STAT_CHARISMA, 500, true);
		database_rare.put(newItem.name, newItem);
		database.put(newItem.name, newItem);
		newItem = new Item("Lucky Potion", 127, Item.ITEM_TYPE_POTION, Item.ITEM_EFFECT_STAT_LUCK, 500, true);
		database_rare.put(newItem.name, newItem);
		database.put(newItem.name, newItem);
		
			//rarity 5
		newItem = new Item("All Stat Potion", 128, Item.ITEM_TYPE_POTION, Item.ITEM_EFFECT_STAT_ALL, 500, true);
		database_treasure.put(newItem.name, newItem);
		database.put(newItem.name, newItem);
		
		
		//furniture
		Furniture newFurniture = null;
		
		/*
		 * RARITY 0 Furniture
		 * 
		 */
		newFurniture = new Furniture("Wooden Chair", 340, 250, 1, 2, false, Furniture.FURNITURE_MEDIUM, 1);
		initFurniture(newFurniture, Item.RARITY_COMMON);
		
		/*
		 * RARITY 1 Furniture
		 * 
		 */
		newFurniture = new Furniture("Wooden Table", 342, 1000, 2, 2, false, Furniture.FURNITURE_BIG, 2);
		initFurniture(newFurniture, Item.RARITY_UNCOMMON);
		
		/*
		 * RARITY 2 Furniture
		 * 
		 */
		newFurniture = new Furniture("Wooden End Table", 341, 650, 1, 1, false, Furniture.FURNITURE_BIG, 1);
		initFurniture(newFurniture, Item.RARITY_SCARCE);
		
		/*
		 * RARITY 3 Furniture
		 * 
		 */
		newFurniture = new Furniture("Wooden Wall Clock", 381, 1200, 1, 1, true, Furniture.FURNITURE_WALL, 2);
		initFurniture(newFurniture, Item.RARITY_RARE);
		
		/*
		 * RARITY 4 Furniture
		 * 
		 */
		newFurniture = new Furniture("Pyrash Rug", 360, 1500, 2, 2, false, Furniture.FURNITURE_FLOOR, 2);
		initFurniture(newFurniture, Item.RARITY_UNIQUE);
		
		newFurniture = new Furniture("Serdralus Rug", 361, 1500, 2, 2, false, Furniture.FURNITURE_FLOOR, 2);
		initFurniture(newFurniture, Item.RARITY_UNIQUE);
		
		/*
		 * RARITY 5 Furniture
		 * 
		 */
		newFurniture = new Furniture("Heatseed Plant", 380, 2500, 1, 1, true, Furniture.FURNITURE_SMALL, 1);
		initFurniture(newFurniture, Item.RARITY_TREASURE);
		
		
		Item.stringToItemType("");
		Item.stringToItemEffect("");
	}
	
	public Item getItem(String input)
	{
		if(database.containsKey(input))
			return database.get(input);
		
		return null;
		
	}
	
	public Item getRandomItem()
	{
			if(database.size() <= 0)
				return null;//can't generate what we don't have!
			
			String tag = ((String) database.keySet().toArray()[coreModule.random.nextInt(database.keySet().size())]);
			
			return getItem(tag);
	}
	
	public Item generateItem(String input)
	{
		//generate a list of possible items dependent on the type of filtering we need
		//General Store stocks potions, food, materials, and occasionally weapons and armor
		//Blacksmith stocks weapons, armor, and occasionally rarer weapons and armor
		//Alchemist stocks various spells
		//Mechanist stocks vehicles and robotic tools
		//Tailor stocks costumes ONLY
		//All holds all of the above
		
		String selectedItem = "HP Potion";
		
		Item newItem = null;
		
		if(input.equals("All"))
		{
			if(database.size() <= 0)
				return null;//can't generate what we don't have!
			
			selectedItem = ((String) database.keySet().toArray()[coreModule.random.nextInt(database.keySet().size())]);
		}
		else
		{
			ArrayList<String> options = new ArrayList<String>();
			
			//first decide what rarity we want for our items
			int rarity = Item.RARITY_COMMON;
			for(int i=0; i<4; i++)
			{
				if(coreModule.random.nextInt(10)<4)
					rarity++;
				else
					break;
			}
			
			Hashtable<String, Item> searchTable = null;

			switch(rarity)
			{
				case Item.RARITY_COMMON:
					searchTable = database_common;
					break;
				case Item.RARITY_UNCOMMON:
					searchTable = database_uncommon;
					break;
				case Item.RARITY_SCARCE:
					searchTable = database_scarce;
					break;
				case Item.RARITY_RARE:
					searchTable = database_rare;
					break;
				case Item.RARITY_UNIQUE:
					searchTable = database_unique;
					break;
			}
			
			if(searchTable != null)
			{
				for(String key : searchTable.keySet())
				{
					if(searchTable.get(key).type == Item.ITEM_TYPE_ARMOR)
					{
						if(input.equals("General Store"))
						{
							if(rarity <= Item.RARITY_UNCOMMON)
								options.add(key);
						}
						else if(input.equals("Blacksmith"))
							options.add(key);
					}
					else if(searchTable.get(key).type == Item.ITEM_TYPE_WEAPON)
					{
						if(input.equals("General Store"))
						{
							if(rarity <= Item.RARITY_UNCOMMON)
								options.add(key);
						}
						else if(input.equals("Blacksmith"))
							options.add(key);
					}
					else if(searchTable.get(key).type == Item.ITEM_TYPE_SPELL)
					{
						if(input.equals("General Store"))
						{
							if(rarity <= Item.RARITY_UNCOMMON)
								options.add(key);
						}
						else if(input.equals("Spellshop"))
							options.add(key);
					}
					else if(searchTable.get(key).type == Item.ITEM_TYPE_COSTUME)
					{
						
					}
					else if(searchTable.get(key).type == Item.ITEM_TYPE_FURNITURE)
					{
						if(input.equals("Market"))
							options.add(key);
					}
					else if(searchTable.get(key).type == Item.ITEM_TYPE_FOOD)
					{
						if(input.equals("General Store"))
							options.add(key);
					}
					else if(searchTable.get(key).type == Item.ITEM_TYPE_POTION)
					{
						if(input.equals("General Store"))
							options.add(key);
					}
				}
			}
			
			if(options.size() > 0)
				selectedItem = options.get(coreModule.random.nextInt(options.size()));
			else
				System.out.println("No items found for rarity "+rarity);
			
			
			options.clear();
		}
		
		Item i = coreModule.gameModule.itemLookupModule.getItem(selectedItem);

		if(i==null)
			return null;
		
		if(i instanceof Weapon)
		{
			newItem = new Weapon(i);
		}
		else if(i instanceof Armor)
		{
			newItem = new Armor(i);
		}
		else if(i instanceof Spell)
		{
			newItem = new Spell(i);
		}
		else if(i instanceof Furniture)
		{
			newItem = new Furniture((Furniture) i);
		}
		else
			newItem = new Item(i);
		
		return newItem;
	}
	
	public String getRandom(int limit)
	{
		//we need to spawn an item as diminishingly rarer points
		int rarity = 1;
		
		for(int i=0; i<limit; i++)
		{
			if(coreModule.random.nextInt(5)==0)
			{
				rarity++;
			}
		}
		
		Hashtable<String, Item> searchTable = null;

		switch(rarity)
		{
			case Item.RARITY_COMMON:
				searchTable = database_common;
				break;
			case Item.RARITY_UNCOMMON:
				searchTable = database_uncommon;
				break;
			case Item.RARITY_SCARCE:
				searchTable = database_scarce;
				break;
			case Item.RARITY_RARE:
				searchTable = database_rare;
				break;
			case Item.RARITY_UNIQUE:
				searchTable = database_unique;
				break;
			case Item.RARITY_TREASURE:
				searchTable = database_treasure;
				break;
			case Item.RARITY_LEGENDARY:
				searchTable = database_legendary;
				break;
			case Item.RARITY_ANCIENT:
				searchTable = database_ancient;
				break;
			case Item.RARITY_RELIC:
				searchTable = database_relic;
				break;
			case Item.RARITY_DIVINE_RELIC:
				searchTable = database_divine_relic;
				break;
			case Item.RARITY_GODLIKE:
				searchTable = database_godlike;
				break;
		}
		Object[] key = searchTable.keySet().toArray();
		
		if(key.length <= 0)
		{
			return getRandom(limit-1);
		}
		
		String itemName = (String) key[coreModule.random.nextInt(key.length)];
		
		return itemName;
	}
	
	public Hashtable<String, Item> getTable(int rarity)
	{

		switch(rarity)
		{
			case Item.RARITY_COMMON:
				return database_common;
			case Item.RARITY_UNCOMMON:
				return database_uncommon;
			case Item.RARITY_SCARCE:
				return database_scarce;
			case Item.RARITY_RARE:
				return database_rare;
			case Item.RARITY_UNIQUE:
				return database_unique;
			case Item.RARITY_TREASURE:
				return database_treasure;
			case Item.RARITY_LEGENDARY:
				return database_legendary;
			case Item.RARITY_ANCIENT:
				return database_ancient;
			case Item.RARITY_RELIC:
				return database_relic;
			case Item.RARITY_DIVINE_RELIC:
				return database_divine_relic;
			case Item.RARITY_GODLIKE:
				return database_godlike;
				
		}
		return database_common;
	}
	
	public void initFurniture(Furniture s, int rarity)
	{
		new Furniture(s);
		(getTable(rarity)).put(s.name, s);
		database.put(s.name, s);
	}
	
	public void initSpell(Spell s, int rarity)
	{
		new SpellAbility(coreModule.ability_templates, s);
		(getTable(rarity)).put(s.name, s);
		database.put(s.name, s);
	}
	
	public void initWeapon(Weapon s, int rarity)
	{
		(getTable(rarity)).put(s.name, s);
		database.put(s.name, s);
	}
	
	public void initArmor(Armor s, int rarity)
	{
		(getTable(rarity)).put(s.name, s);
		database.put(s.name, s);
	}
}
