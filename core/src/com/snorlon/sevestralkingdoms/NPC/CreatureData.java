package com.snorlon.sevestralkingdoms.NPC;

//simple struct to hold creature information and have a constructor to create it
public class CreatureData
{
	public String name = "Error";

	public double Strength = 1.0;
	public double Toughness = 1.0;
	public double Agility = 1.0;
	public double Dexterity = 1.0;
	public double Intelligence = 1.0;
	public double Willpower = 1.0;
	public double Charisma = 1.0;
	public double Luck = 1.0;

	public double Strength_Gain = 1.0;
	public double Toughness_Gain = 1.0;
	public double Agility_Gain = 1.0;
	public double Dexterity_Gain = 1.0;
	public double Intelligence_Gain = 1.0;
	public double Willpower_Gain = 1.0;
	public double Charisma_Gain = 1.0;
	public double Luck_Gain = 1.0;

	public double Resist_Neutral = 1.0;
	public double Resist_Fire = 1.0;
	public double Resist_Water = 1.0;
	public double Resist_Earth = 1.0;
	public double Resist_Wind = 1.0;
	public double Resist_Life = 1.0;
	public double Resist_Time = 1.0;
	public double Resist_Space = 1.0;
	public double Resist_Void = 1.0;

	public String filename = "error.png";
	public float animationSpeed = 2.0f;

	public String ability_1 = "None";
	public String ability_2 = "None";
	public String ability_3 = "None";
	public String ability_4 = "None";

	public String spell_1 = "None";
	public String spell_2 = "None";
	public String spell_3 = "None";

	public String weapon = "None";
	public String helmet = "None";
	public String chest = "None";
	public String pants = "None";
	
	public String gender = "None";
	
	public boolean hasMap = false;
	public boolean human = false;
	public int spawnRate = 0;
	public String habitat = "Fire";
	public int habitatX = 0;
	public int habitatY = 0;
	
	public CreatureData(String nname, String fname, float nanimSpeed)
	{
		name = nname;
		filename = fname;
		animationSpeed = nanimSpeed;
	}
	
	public void gender(String ngender)
	{
		gender = ngender;
	}
	
	public void addSpawn(String nhabitat, int nx, int ny, int rate)
	{
		habitat = nhabitat;
		habitatX = nx;
		habitatY = ny;
		spawnRate = rate;
		hasMap = true;
	}
	
	public void baseStats(double nStr, double nTou, double nInt, double nWil, double nAgi, double nDex, double nCha, double nLuk)
	{
		Strength = nStr;
		Toughness = nTou;
		Intelligence = nInt;
		Willpower = nWil;
		Agility = nAgi;
		Dexterity = nDex;
		Charisma = nCha;
		Luck = nLuk;
	}
	
	public void statGain(double nStr, double nTou, double nInt, double nWil, double nAgi, double nDex, double nCha, double nLuk)
	{
		Strength_Gain = nStr;
		Toughness_Gain = nTou;
		Intelligence_Gain = nInt;
		Willpower_Gain = nWil;
		Agility_Gain = nAgi;
		Dexterity_Gain = nDex;
		Charisma_Gain = nCha;
		Luck_Gain = nLuk;
	}
	
	public void resists(double nNeu, double nFir, double nWat, double nEar, double nWin, double nLif, double nTim, double nSpa, double nVoi)
	{
		Resist_Neutral = nNeu;
		Resist_Fire = nFir;
		Resist_Water = nWat;
		Resist_Earth = nEar;
		Resist_Wind = nWin;
		Resist_Life = nLif;
		Resist_Time = nTim;
		Resist_Space = nSpa;
		Resist_Void = nVoi;
	}
	
	public void addAbility(String newAbility)
	{
		if(ability_1.equals("None"))
		{
			ability_1 = newAbility;
		}
		else if(ability_2.equals("None"))
		{
			ability_2 = newAbility;
		}
		else if(ability_3.equals("None"))
		{
			ability_3 = newAbility;
		}
		else if(ability_4.equals("None"))
		{
			ability_4 = newAbility;
		}
	}
	
	public void addSpell(String nspell)
	{
		if(spell_1.equals("None"))
		{
			spell_1 = nspell;
		}
		else if(spell_2.equals("None"))
		{
			spell_2 = nspell;
		}
		else if(spell_3.equals("None"))
		{
			spell_3 = nspell;
		}
	}
	
	public void addWeapon(String nwep)
	{
		weapon = nwep;
	}
	
	public void addHelmet(String helm)
	{
		helmet = helm;
	}
	
	public void addChest(String ch)
	{
		chest = ch;
	}
	
	public void addPants(String p)
	{
		pants = p;
	}
}
