package com.snorlon.sevestralkingdoms.GameComponents;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.snorlon.sevestralkingdoms.Ability.Ability;
import com.snorlon.sevestralkingdoms.Ability.SpellAbility;
import com.snorlon.sevestralkingdoms.GameTools.Core;

public class Common {
	//elemental types
	public final static int TYPE_NONE = 0;
	public final static int TYPE_FIRE = 1;
	public final static int TYPE_WATER = 2;
	public final static int TYPE_EARTH = 3;
	public final static int TYPE_WIND = 4;
	public final static int TYPE_LIFE = 5;
	public final static int TYPE_TIME = 6;
	public final static int TYPE_SPACE = 7;
	public final static int TYPE_VOID = 8;
	
	//stat ids
	public final static int STRENGTH = 0;
	public final static int TOUGHNESS = 1;
	public final static int AGILITY = 4;
	public final static int DEXTERITY = 5;
	public final static int INTELLIGENCE = 2;
	public final static int WILLPOWER = 3;
	public final static int CHARISMA = 6;
	public final static int LUCK = 7;
	
	//combat types
	public final static int PHYSICAL = 0;
	public final static int MAGICAL = 1;
	public final static int AGILE = 2;
	
	//Item IDs
	public final static int ITEM_MISC = 0;
	public final static int ITEM_WEAPON = 1;
	public final static int ITEM_HELMET = 2;
	public final static int ITEM_CHESTPIECE = 3;
	public final static int ITEM_LEGGINGS = 4;
	public final static int ITEM_GLOVES = 5;
	public final static int ITEM_BOOTS = 6;
	public final static int ITEM_CAPE = 7;
	public final static int ITEM_POTION = 8;
	public final static int ITEM_CRAFTING = 9;
	public final static int ITEM_FOOD = 10;
	public final static int ITEM_CURRENCY = 11;
	
	//Personality IDs
	public final static int PERSONALITY_NEUTRAL = 0;
	public final static int PERSONALITY_GREEDY = 1;
	public final static int PERSONALITY_SURVIVALIST = 2;
	public final static int PERSONALITY_COWARD = 3;
	public final static int PERSONALITY_ADVENTURER = 4;
	public final static int PERSONALITY_WARRIOR = 5;
	public final static int PERSONALITY_SELFLESS = 6;
	public final static int PERSONALITY_PARANOID = 7;
	public final static int PERSONALITY_ROMANTIC = 8;
	public final static int PERSONALITY_RECKLESS = 9;
	public final static int PERSONALITY_COURAGEOUS = 10;
	public final static int MAX_PERSONALITY = 10;

	public final static String FACTION_NONE = "Independent";
	public final static String FACTION_PLAYER = "Player";
	public final static String FACTION_DAIMINA = "Daimina Order";
	
	public static int StringToStat(String input)
	{
		if(input.equals("Strength"))
		{
			return STRENGTH;
		}
		else if(input.equals("Toughness"))
		{
			return TOUGHNESS;
		}
		else if(input.equals("Intelligence"))
		{
			return INTELLIGENCE;
		}
		else if(input.equals("Willpower"))
		{
			return WILLPOWER;
		}
		else if(input.equals("Dexterity"))
		{
			return DEXTERITY;
		}
		else if(input.equals("Agility"))
		{
			return AGILITY;
		}
		else if(input.equals("Luck"))
		{
			return LUCK;
		}
		else if(input.equals("Charisma"))
		{
			return CHARISMA;
		}
		else if(input.equals("Physical"))
		{
			return PHYSICAL;
		}
		else if(input.equals("Magical"))
		{
			return MAGICAL;
		}
		else if(input.equals("Agile"))
		{
			return AGILE;
		}
			
		return 0;//default
	}
	
	public static String StatToString(int typeNum)
	{
		switch(typeNum)
		{
			case STRENGTH:
				return "Strength";
			case TOUGHNESS:
				return "Toughness";
			case INTELLIGENCE:
				return "Intelligence";
			case WILLPOWER:
				return "Willpower";
			case DEXTERITY:
				return "Dexterity";
			case AGILITY:
				return "Agility";
			case LUCK:
				return "Luck";
			default:
				return "Charisma";
		}
	}
	
	public static String AttackTypeToString(int attackType)
	{
		switch(attackType)
		{
			case PHYSICAL:
				return "Physical";
			case MAGICAL:
				return "Magical";
			case AGILE:
				return "Agile";
		}
		
		return "Error";
	}
	
	public static String PersonalityToString(int personality)
	{
		switch(personality)
		{
			case Common.PERSONALITY_RECKLESS:
				return "Reckless";
			case Common.PERSONALITY_GREEDY:
				return "Greedy";
			case Common.PERSONALITY_SURVIVALIST:
				return "Survivalist";
			case Common.PERSONALITY_COWARD:
				return "Coward";
			case Common.PERSONALITY_ADVENTURER:
				return "Adventurer";
			case Common.PERSONALITY_WARRIOR:
				return "Warrior";
			case Common.PERSONALITY_SELFLESS:
				return "Selfless";
			case Common.PERSONALITY_PARANOID:
				return "Paranoid";
			case Common.PERSONALITY_ROMANTIC:
				return "Romantic";
			case Common.PERSONALITY_COURAGEOUS:
				return "Courageous";
			default:
				return "Neutral";
		}
	}
	
	public static int StringToType(String input)
	{
		if(input.equals("None"))
		{
			return TYPE_NONE;
		}
		else if(input.equals("Fire"))
		{
			return TYPE_FIRE;
		}
		else if(input.equals("Water"))
		{
			return TYPE_WATER;
		}
		else if(input.equals("Earth"))
		{
			return TYPE_EARTH;
		}
		else if(input.equals("Wind"))
		{
			return TYPE_WIND;
		}
		else if(input.equals("Life"))
		{
			return TYPE_LIFE;
		}
		else if(input.equals("Time"))
		{
			return TYPE_TIME;
		}
		else if(input.equals("Space"))
		{
			return TYPE_SPACE;
		}
		else if(input.equals("Void"))
		{
			return TYPE_VOID;
		}
			
		return TYPE_NONE;//default
	}
	
	public static String TypeToString(int typeNum)
	{
		switch(typeNum)
		{
			case TYPE_FIRE:
				return "Fire";
			case TYPE_WATER:
				return "Water";
			case TYPE_EARTH:
				return "Earth";
			case TYPE_WIND:
				return "Wind";
			case TYPE_LIFE:
				return "Life";
			case TYPE_TIME:
				return "Time";
			case TYPE_SPACE:
				return "Space";
			case TYPE_VOID:
				return "Void";
			case TYPE_NONE:
				return "Neutral";
			default:
				return "None";
		}
	}
	
	public static String generate_name(Random r)
	{		
		int syllableMin = 1;
		int syllableMax = 4;
		int roll = r.nextInt((syllableMax-syllableMin)+1) + syllableMin;
		int maxLength = 14;
		
		//we'll assume that it's just one dialect (for now)
		String consonants[] = {"b","br","bl","c","cr","cl","ch","d","dr","dw","f","fl","fr","fy","g","gr","gl","gh","h","j","k","kr","kl","l","m","mr","n","nr","p","ph","pr","pl","qu","r","rh","s","sr","sl","sh","t","th","tr","tw","v","w","wr","wh","x","z"};
		String vowels[] = {"a","a","a","a","ae","ai","ao","au","e","e","e","e","e","ee","ea","ei","eo","eu","i","i","i","i","i","ia","ie","io","iu","o","o","o","o","o","oa","oe","oi","oo","ou","u","u","u","u","ua","ue","ui","uo"};

		//have between X and Y syllables in the first half of name
		String firstName = "";
		
		//determine if we're ending with a consonant
		int secondRoll = r.nextInt(100);
		boolean endWithConsonant = false;
		
		if(secondRoll<75 && roll>1)
		{
			endWithConsonant = true;
			roll--;
		}

		secondRoll = r.nextInt(100);
		//potentially start with vowel at 40% chance
		if(roll>1 && secondRoll < 40 && firstName.length() < maxLength)
		{
			//generate a position for vowels
			int position = r.nextInt(vowels.length);

			//append it
			firstName += vowels[position];
			
			roll--;
		}
		
		for(int i=0; i<roll && firstName.length() < maxLength; i++)
		{
			//potentially skip first consonant at 15% chance
			secondRoll = r.nextInt(100);
			
			//generate a position for consonants
			int position = r.nextInt(consonants.length);

			//append it
			firstName += consonants[position];

			secondRoll = r.nextInt(100);
			//generate a position for vowels
			position = r.nextInt(vowels.length);

			//append it
			firstName += vowels[position];
		}
		
		if(endWithConsonant)
		{
			//generate a position for consonants
			int position = r.nextInt(consonants.length);

			//append it
			firstName += consonants[position];
		}
		
		//capitalize the first letter
		firstName = Character.toUpperCase(firstName.charAt(0)) + firstName.substring(1);
		
		//have between X and Y syllables in the second half of name
		/*roll = r.nextInt((syllableMax-syllableMin)+1) + syllableMin;
		String lastName = "";
		
		secondRoll = r.nextInt(100);
		endWithConsonant = false;
		
		if(secondRoll<75 && roll>1)
		{
			endWithConsonant = true;
			roll--;
		}

		secondRoll = r.nextInt(100);
		//potentially start with vowel at 40% chance
		if(roll>1 && secondRoll < 40)
		{
			//generate a position for vowels
			int position = r.nextInt(vowels.length);

			//append it
			lastName += vowels[position];
			
			roll--;
		}
		
		for(int i=0; i<roll; i++)
		{
			//potentially skip first consonant at 15% chance
			secondRoll = r.nextInt(100);
			
			//generate a position for consonants
			int position = r.nextInt(consonants.length);

			//append it
			lastName += consonants[position];

			secondRoll = r.nextInt(100);
			//generate a position for vowels
			position = r.nextInt(vowels.length);

			//append it
			lastName += vowels[position];
		}
		
		if(endWithConsonant)
		{
			//generate a position for consonants
			int position = r.nextInt(consonants.length);

			//append it
			lastName += consonants[position];
		}
		
		
		
		//capitalize the first letter
		lastName = Character.toUpperCase(lastName.charAt(0)) + lastName.substring(1);*/
		
		//assign new name!
		return (firstName);// + " " + lastName);		
	}

	public static int[] getColor(String owner)
	{
		if(owner.equals(FACTION_PLAYER))
		{
			return new int[]{255,0,0};
		}
		else if(owner.equals(FACTION_DAIMINA))
		{
			return new int[]{255,123,0};
		}
		
		return new int[]{0,0,0};
	}
}
