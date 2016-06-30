package com.snorlon.sevestralkingdoms.NPC;

import java.util.Hashtable;

import com.snorlon.sevestralkingdoms.GameTools.Core;
import com.snorlon.sevestralkingdoms.Places.Location;

//class purely for getting creature data for loading
public class CreatureDB
{
	public Hashtable<String, CreatureData > database = new Hashtable<String, CreatureData>();
	
	public CreatureDB()
	{
		loadCreatures();
	}
	
	public void loadCreatures()
	{
		CreatureData newCreature;
		
		newCreature = new CreatureData("Abyss Walker", "abyss walker.png", 7);
		newCreature.baseStats(7,5,2,2,12,8,3,1);
		newCreature.statGain(3, 1, 0.5, 0.5, 3.5, 1.5, 0.5, 0.5);
		newCreature.resists(1, 1, 1, 1.5, 1.5, 0, 1.5, 1, 0.5);
		newCreature.addSpawn("Void", 14, 3, 150);
		newCreature.addAbility("Bite");
		newCreature.addAbility("Roar");
		newCreature.addAbility("Noxious Breath");
		newCreature.addAbility("Severance");
		database.put(newCreature.name, newCreature);

		newCreature = new CreatureData("Blazlin", "blazlin.png", 4);
		newCreature.baseStats(2,4,2,4,10,2,4,12);
		newCreature.statGain(0.5, 1, 1, 1.5, 2, 1, 1, 2.5);
		newCreature.resists(1.5, 0.25, 1.75, 1, 1, 1, 1, 1, 0.5);
		newCreature.addSpawn("Fire", 3, 5, 90);
		newCreature.addAbility("Bite");
		newCreature.addAbility("Scorch");
		newCreature.addAbility("Steam Cloud");
		database.put(newCreature.name, newCreature);
		
		newCreature = new CreatureData("Blubberfish", "blubberfish.png", 12);
		newCreature.baseStats(4,8,2,3,8,6,4,5);
		newCreature.statGain(1, 3, 0.5, 1, 3, 1.5, 1, 1);
		newCreature.resists(1, 0, 0, 1.5, 1, 1.5, 1.5, 1.5, 1);
		newCreature.addSpawn("Water", 1, 2, 100);
		newCreature.addAbility("Panic");
		newCreature.addAbility("Crush");
		newCreature.addAbility("Squirt");
		database.put(newCreature.name, newCreature);

		newCreature = new CreatureData("Bosaris", "bosaris.png", 12);
		newCreature.baseStats(14,6,2,5,4,1,6,2);
		newCreature.statGain(3.5, 2, 0.5, 1, 0.5, 0.5, 1.5, 1);
		newCreature.resists(1, 0.25, 0.25, 0.25, 0.25, 1.75, 1.75, 1.75, 1.75);
		newCreature.addSpawn("Water", 4, 1, 60);
		newCreature.addAbility("Bite");
		newCreature.addAbility("Roar");
		newCreature.addAbility("Frosty Breath");
		newCreature.addAbility("Lacerate");
		database.put(newCreature.name, newCreature);

		newCreature = new CreatureData("Breplex", "breplex.png", 15);
		newCreature.baseStats(1,2,2,3,10,16,4,2);
		newCreature.statGain(0.5, 1, 0.5, 1, 3, 2.5, 1, 1);
		newCreature.resists(2, 3, 1, 1, 0.25, 1, 1, 1, 0.25);
		newCreature.addSpawn("Wind", 8, 7, 100);
		newCreature.addAbility("Bite");
		newCreature.addAbility("Sonar Scream");
		newCreature.addAbility("Burrow");
		newCreature.addAbility("Dust Cloud");
		database.put(newCreature.name, newCreature);

		newCreature = new CreatureData("Bubba Snail", "bubba snail.png", 15);
		newCreature.baseStats(1,30,1,1,1,1,5,1);
		newCreature.statGain(0.5, 8, 0.5, 0.5, 0.5, 0.5, 1, 0.5);
		newCreature.resists(0, 2, 1, 2, 1, 1, 0.5, 1, 0.5);
		newCreature.addSpawn("Water", 1, 7, 150);
		newCreature.addAbility("Fast Forward");
		newCreature.addAbility("Bubble");
		newCreature.addAbility("Heavy Rain");
		newCreature.addAbility("Ancient Sea");
		database.put(newCreature.name, newCreature);

		newCreature = new CreatureData("Buovlo", "buovlo.png", 10);
		newCreature.baseStats(6,10,10,6,1,1,6,2);
		newCreature.statGain(1.5, 2.5, 2, 1.5, 1, 1, 1.5, 1);
		newCreature.resists(1, 1, 0.5, 1.5, 1, 1, 0.5, 1.5, 1);
		newCreature.addSpawn("Water",12,5, 80);
		newCreature.addAbility("Heavy Rain");
		newCreature.addAbility("Squirt");
		newCreature.addAbility("Cleansing Water");
		database.put(newCreature.name, newCreature);

		newCreature = new CreatureData("Clapeye", "clapeye.png", 5);
		newCreature.baseStats(2,18,8,6,2,2,1,1);
		newCreature.statGain(1, 3.5, 2, 1.5, 1, 1, 1, 1);
		newCreature.resists(1, 3.5, 0, 1.75, 1.75, 0.25, 0.25, 0.25, 0.25);
		newCreature.addSpawn("Water",16,7, 120);
		newCreature.addAbility("Engulf");
		newCreature.addAbility("Hallucination");
		newCreature.addAbility("Bite");
		newCreature.addAbility("Dark Seas");
		database.put(newCreature.name, newCreature);

		newCreature = new CreatureData("Clesh", "clesh.png", 10);
		newCreature.baseStats(4,6,2,7,6,2,8,5);
		newCreature.statGain(0.5, 2, 1, 1.5, 1, 1, 3, 0.5);
		newCreature.resists(1, 0.5, 0.5, 1, 0.75, 1, 1.5, 1.5, 1.25);
		newCreature.addSpawn("Fire", 4, 4, 90);
		newCreature.addAbility("Bite");
		newCreature.addAbility("Lava Spit");
		newCreature.addAbility("Steam Cloud");
		database.put(newCreature.name, newCreature);

		newCreature = new CreatureData("Crast", "crast.png", 7);
		newCreature.baseStats(4,4,2,16,4,2,4,4);
		newCreature.statGain(2, 2, 2, 2, 1, 1, 1, 1);
		newCreature.resists(1.25, 0.75, 0, 2, 2, 0.5, 0.5, 1, 1);
		newCreature.addSpawn("Water",15,5, 70);
		newCreature.addAbility("Regenerate");
		newCreature.addAbility("Engulf");
		newCreature.addAbility("Squirt");
		database.put(newCreature.name, newCreature);
		
		newCreature = new CreatureData("Crustlin", "crustlin.png", 8);
		newCreature.baseStats(4,10,2,4,2,2,4,12);
		newCreature.statGain(1, 2, 1, 1.5, 0.5, 1, 1, 2.5);
		newCreature.resists(1.5, 1, 1, 0.25, 1.75, 1, 1, 1, 0.5);
		newCreature.addSpawn("Earth", 13, 6, 120);
		newCreature.addAbility("Bite");
		newCreature.addAbility("Tremble");
		newCreature.addAbility("Dust Cloud");
		newCreature.addAbility("Burrow");
		database.put(newCreature.name, newCreature);
		
		newCreature = new CreatureData("Daimina Order Soldier", "caucasian.png", 5);
		newCreature.gender("Female");
		newCreature.baseStats(8,8,10,4,4,8,4,4);
		newCreature.statGain(0.5, 1, 1, 1.5, 1, 2, 1, 2.5);
		newCreature.resists(1, 1, 1, 1, 1, 1, 0, 2, 1);
		newCreature.addAbility("Panic");
		database.put(newCreature.name, newCreature);

		newCreature = new CreatureData("Deep Serpent", "deep serpent.png", 4);
		newCreature.baseStats(10,6,1,2,10,6,2,4);
		newCreature.statGain(2, 1.5, 1, 1, 2.5, 1, 1, 2);
		newCreature.resists(1, 0.5, 0.5, 0.5, 1.5, 1.5, 1.5, 1, 1);
		newCreature.addSpawn("Water", 12, 6, 15);
		newCreature.addAbility("Fireball");
		newCreature.addAbility("Firebreath");
		newCreature.addAbility("Typhoon");
		newCreature.addAbility("Tsunami");
		database.put(newCreature.name, newCreature);

		newCreature = new CreatureData("Druttle", "druttle.png", 20);
		newCreature.baseStats(1,14,9,7,1,1,3,2);
		newCreature.statGain(1, 3.5, 1.5, 1.5, 0.5, 0.5, 1, 1);
		newCreature.resists(0.5, 2.5, 0.5, 1.5, 0.5, 1, 0, 1.5, 1);
		newCreature.addSpawn("Wind", 7, 2, 75);
		newCreature.addAbility("Burrow");
		newCreature.addAbility("Strong Breath");
		newCreature.addAbility("Panic");
		newCreature.addAbility("Megaton Crush");
		database.put(newCreature.name, newCreature);
		
		newCreature = new CreatureData("Earth Golem", "earth golem.png", 8);
		newCreature.baseStats(14,12,1,6,3,3,1,2);
		newCreature.statGain(3, 3, 0.5, 2, 1, 1, 0.5, 1);
		newCreature.resists(0, 2, 1, 0, 1, 1, 1, 1, 1);
		newCreature.addSpawn("Earth", 6, 4, 90);
		newCreature.addAbility("Crush");
		newCreature.addAbility("Burrow");
		newCreature.addAbility("Megaton Crush");
		newCreature.addAbility("Earthquake");
		database.put(newCreature.name, newCreature);
		
		newCreature = new CreatureData("Ember Dragon", "ember dragon.png", 14);
		newCreature.baseStats(8,8,8,8,8,8,8,8);
		newCreature.statGain(2, 2, 2, 2, 2, 2, 2, 2);
		newCreature.resists(0, 0, 2, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5);
		newCreature.addSpawn("Fire", 11, 4, 60);
		newCreature.addAbility("Crush");
		newCreature.addAbility("Blastfire");
		newCreature.addAbility("Firebreath");
		newCreature.addAbility("Eruption");
		database.put(newCreature.name, newCreature);
		
		newCreature = new CreatureData("Enigma", "enigma.png", 0.4f);//
		newCreature.baseStats(5,5,5,5,5,5,6,6);
		newCreature.statGain(1, 1, 3, 1, 1, 1, 2, 2);
		newCreature.resists(0, 1, 1, 1, 1, 0, 2.0, 2.0, 0);
		newCreature.addSpawn("Void",13,6, 300);
		newCreature.addAbility("Hallucination");
		newCreature.addAbility("Severance");
		newCreature.addAbility("Erosion");
		newCreature.addAbility("Nightmare");
		database.put(newCreature.name, newCreature);
		
		newCreature = new CreatureData("Fire Golem", "fire golem.png", 2);
		newCreature.baseStats(1,6,3,3,14,12,2,1);
		newCreature.statGain(0.5, 2, 1, 1, 3, 3, 1, 0.5);
		newCreature.resists(0, 0.5, 2, 1, 1, 0.5, 1, 0.5, 1.5);
		newCreature.addSpawn("Fire", 7, 4, 120);
		newCreature.addAbility("Scorch");
		newCreature.addAbility("Firebreath");
		newCreature.addAbility("Blastfire");
		newCreature.addAbility("Phoenix Flare");
		database.put(newCreature.name, newCreature);
		
		newCreature = new CreatureData("Flitfly", "flitfly.png", 2);
		newCreature.baseStats(1,1,4,4,12,12,5,3);
		newCreature.statGain(1, 1, 1, 1, 3, 3, 1, 1);
		newCreature.resists(1.5, 1.75, 0.5, 1, 0.25, 1, 1, 1, 1);
		newCreature.addSpawn("Wind", 15, 6, 80);
		newCreature.addAbility("Cold Cloud");
		newCreature.addAbility("Gust");
		newCreature.addAbility("Breeze");
		database.put(newCreature.name, newCreature);
		
		newCreature = new CreatureData("Flufkay", "flufkay.png", 6);
		newCreature.baseStats(3,3,12,9,2,2,1,10);
		newCreature.statGain(1, 1, 3, 2, 1, 1, 0.5, 2.5);
		newCreature.resists(1, 1, 1, 1, 1, 1, 1, 1, 1);
		newCreature.addSpawn("Water", 2, 6, 60);
		newCreature.addAbility("Crush");
		newCreature.addAbility("Plague");
		newCreature.addAbility("Infection");
		newCreature.addAbility("Severance");
		database.put(newCreature.name, newCreature);
		
		newCreature = new CreatureData("Frost Crab", "frost crab.png", 7);
		newCreature.baseStats(1,15,2,4,6,5,2,3);
		newCreature.statGain(0.5, 3.5, 0.5, 0.5, 1, 0.5, 0.5, 2);
		newCreature.resists(1, 0.5, 0.25, 1, 0.75, 1, 2, 0.5, 2);
		newCreature.addSpawn("Water", 2, 2, 100);
		newCreature.addAbility("Squirt");
		newCreature.addAbility("Frosty Breath");
		newCreature.addAbility("Lacerate");
		database.put(newCreature.name, newCreature);
		
		newCreature = new CreatureData("Frost Urchin", "frost urchin.png", 5);
		newCreature.baseStats(4,6,2,4,2,2,12,8);
		newCreature.statGain(1, 3, 1, 2, 0.25, 1, 1.75, 2);
		newCreature.resists(1, 0.75, 0.25, 1, 0.5, 1, 2, 2, 0.5);
		newCreature.addSpawn("Water", 15, 1, 150);
		newCreature.addAbility("Cold Cloud");
		newCreature.addAbility("Frosty Breath");
		newCreature.addAbility("Squirt");
		database.put(newCreature.name, newCreature);
		
		newCreature = new CreatureData("Ghost Pirate", "ghost pirate.png", 4);
		newCreature.baseStats(10,4,2,3,5,9,4,3);
		newCreature.statGain(3, 2, 0.5, 0.5, 1.5, 2, 1, 1.5);
		newCreature.resists(0, 1, 0.5, 1.5, 0.75, 1.75, 1, 2, 0.5);
		newCreature.addSpawn("Water",12,7, 150);
		newCreature.addAbility("Slash");
		newCreature.addAbility("Infection");
		newCreature.addAbility("Erosion");
		newCreature.addAbility("Lacerate");
		database.put(newCreature.name, newCreature);

		newCreature = new CreatureData("Ghost Tree", "ghost tree.png", 9);
		newCreature.baseStats(4,11,6,3,4,8,2,2);
		newCreature.statGain(1, 3, 2, 0.5, 1, 2.5, 0.5, 0.5);
		newCreature.resists(3, 2, 0, 0, 0.5, 0, 0.75, 1.75, 1);
		newCreature.addSpawn("Earth", 4, 8, 120);
		newCreature.addAbility("Root");
		newCreature.addAbility("Scratch");
		newCreature.addAbility("Crush");
		newCreature.addAbility("Nightmare");
		database.put(newCreature.name, newCreature);
		
		newCreature = new CreatureData("Gruumlo", "gruumlo.png", 4);
		newCreature.baseStats(2,4,3,4,20,5,1,1);
		newCreature.statGain(1, 2, 0.5, 1, 4, 1.5, 0.5, 0.5);
		newCreature.resists(0, 1.5, 1, 1, 1, 1.5, 1.25, 1, 0.75);
		newCreature.addSpawn("Void", 3, 7, 70);
		newCreature.addAbility("Panic");
		newCreature.addAbility("Nostalgia");
		newCreature.addAbility("Plague");
		newCreature.addAbility("Nightmare");
		database.put(newCreature.name, newCreature);
		
		newCreature = new CreatureData("Gulper", "gulper.png", 7);
		newCreature.baseStats(4,10,2,6,2,2,4,10);
		newCreature.statGain(1, 4, 0.5, 2.5, 1, 1, 1, 1);
		newCreature.resists(1, 0.75, 0, 1, 1, 1.5, 1, 1.5, 0.25);
		newCreature.addSpawn("Water",16,5, 70);
		newCreature.addAbility("Bite");
		newCreature.addAbility("Engulf");
		newCreature.addAbility("Squirt");
		database.put(newCreature.name, newCreature);
		
		newCreature = new CreatureData("Gustlin", "gustlin.png", 7);
		newCreature.baseStats(2,2,2,4,4,10,4,12);
		newCreature.statGain(0.5, 1, 1, 1.5, 1, 2, 1, 2.5);
		newCreature.resists(1, 1.75, 1, 1, 0.25, 1, 1, 1, 1);
		newCreature.addAbility("Bite");
		newCreature.addAbility("Breeze");
		newCreature.addAbility("Cold Cloud");
		database.put(newCreature.name, newCreature);
		
		newCreature = new CreatureData("Hexfish", "hexfish.png", 3);
		newCreature.baseStats(3,6,8,11,1,1,8,2);
		newCreature.statGain(0.5, 1, 2.5, 3, 0.5, 0.5, 2, 0.5);
		newCreature.resists(1, 2, 0.5, 1.25, 1.25, 1, 1, 1, 0);
		newCreature.addSpawn("Water", 14, 3, 80);
		newCreature.addAbility("Bite");
		newCreature.addAbility("Fatal Venom");
		newCreature.addAbility("Burrow");
		newCreature.addAbility("Agony Venom");
		database.put(newCreature.name, newCreature);
		
		newCreature = new CreatureData("Hriigal", "hriigal.png", 3);
		newCreature.baseStats(2,4,10,6,2,10,2,4);
		newCreature.statGain(0.5, 1.5, 2, 1.5, 1, 2, 1, 1);
		newCreature.resists(1.25, 1, 0.75, 1.25, 0.25, 1, 1, 1, 1.25);
		newCreature.addSpawn("Water", 8, 7, 200);
		newCreature.addAbility("Peck");
		newCreature.addAbility("Frost Dust");
		newCreature.addAbility("Cold Cloud");
		database.put(newCreature.name, newCreature);
		
		newCreature = new CreatureData("Ice Golem", "ice golem.png", 2);
		newCreature.baseStats(1,6,14,12,3,3,2,1);
		newCreature.statGain(0.5, 2, 3, 3, 1, 1, 1, 0.5);
		newCreature.resists(0, 2, 0.5, 1, 1, 0.5, 0.5, 1, 1.5);
		newCreature.addSpawn("Water", 5, 1, 70);
		newCreature.addAbility("Frost Dust");
		newCreature.addAbility("Cold Cloud");
		newCreature.addAbility("Frosty Breath");
		newCreature.addAbility("Red Ice");
		database.put(newCreature.name, newCreature);
		
		newCreature = new CreatureData("Iron Golem", "iron golem.png", 8);
		newCreature.baseStats(14,12,1,6,3,3,2,1);
		newCreature.statGain(3, 3, 0.5, 2, 1, 1, 1, 0.5);
		newCreature.resists(0, 2, 2, 0, 0, 1, 1, 1, 1);
		newCreature.addSpawn("Earth", 15, 2, 90);
		newCreature.addAbility("Crush");
		newCreature.addAbility("Tremble");
		newCreature.addAbility("Megaton Crush");
		newCreature.addAbility("Erosion");
		database.put(newCreature.name, newCreature);
		
		newCreature = new CreatureData("Magma Golem", "magma golem.png", 8);
		newCreature.baseStats(14,12,1,6,3,3,2,1);
		newCreature.statGain(3, 3, 0.5, 2, 1, 1, 1, 0.5);
		newCreature.resists(0, 0.5, 2, 1.5, 0.5, 1, 1, 1, 0.5);
		newCreature.addSpawn("Fire", 6, 1, 90);
		newCreature.addAbility("Rock Drop");
		newCreature.addAbility("Lava Spit");
		newCreature.addAbility("Magma Wave");
		newCreature.addAbility("Eruption");
		database.put(newCreature.name, newCreature);
		
		newCreature = new CreatureData("Mistball", "mistball.png", 8);
		newCreature.baseStats(1,1,5,5,13,12,2,1);
		newCreature.statGain(0.5, 0.5, 1.5, 2, 3, 3, 1, 0.5);
		newCreature.resists(0, 1.75, 0, 1.75, 0, 1.75, 1, 1.75, 1);
		newCreature.addSpawn("Wind", 3, 3, 110);
		newCreature.addAbility("Breeze");
		newCreature.addAbility("Light Mist");
		newCreature.addAbility("Frost Dust");
		database.put(newCreature.name, newCreature);
		
		newCreature = new CreatureData("Mist Golem", "mist golem.png", 3);
		newCreature.baseStats(1,1,5,6,14,12,2,1);
		newCreature.statGain(0.5, 0.5, 1.5, 2, 3, 3, 1, 0.5);
		newCreature.resists(0, 1.5, 0, 1.5, 0, 1.5, 1, 1.5, 1);
		newCreature.addSpawn("Wind", 3, 7, 80);
		newCreature.addAbility("Frosty Breath");
		newCreature.addAbility("Cold Cloud");
		newCreature.addAbility("Frost Dust");
		newCreature.addAbility("Heavy Rain");
		database.put(newCreature.name, newCreature);
		
		newCreature = new CreatureData("Mud Golem", "mud golem.png", 8);
		newCreature.baseStats(14,12,1,6,3,3,2,1);
		newCreature.statGain(3, 3, 0.5, 2, 1, 1, 1, 0.5);
		newCreature.resists(0, 2, 0.5, 0.5, 1.5, 1, 1, 1, 0.5);
		newCreature.addSpawn("Earth", 13, 1, 90);
		newCreature.addAbility("Rock Drop");
		newCreature.addAbility("Tremble");
		newCreature.addAbility("Noxious Swamp");
		database.put(newCreature.name, newCreature);

		newCreature = new CreatureData("Omnisloth", "omnisloth.png", 3);
		newCreature.baseStats(1,1,1,1,1,1,33,1);
		newCreature.statGain(2, 1, 0.5, 0.5, 4, 1, 1.5, 1);
		newCreature.resists(1, 1.25, 1.25, 0.75, 0.75, 0, 1, 1, 2);
		newCreature.addSpawn("Wind", 5, 2, 300);
		newCreature.addAbility("Roar");
		newCreature.addAbility("Scratch");
		newCreature.addAbility("Engulf");
		newCreature.addAbility("Lacerate");
		database.put(newCreature.name, newCreature);
		
		newCreature = new CreatureData("Peroomf", "peroomf.png", 4);
		newCreature.baseStats(10,6,2,4,2,2,2,12);
		newCreature.statGain(3, 3, 0.5, 1, 0.75, 0.75, 0.75, 0.75);
		newCreature.resists(1.25, 0.25, 3.25, 0.5, 0.5, 1, 0.75, 1, 0.5);
		newCreature.addSpawn("Fire", 3, 4, 60);
		newCreature.addAbility("Bite");
		newCreature.addAbility("Firebreath");
		newCreature.addAbility("Lava Spit");
		database.put(newCreature.name, newCreature);
		
		newCreature = new CreatureData("Pyrwolf", "pyrwolf.png", 6);
		newCreature.baseStats(10,4,1,5,6,2,1,1);
		newCreature.statGain(2.5, 0.7, 1, 1.5, 1.2, 1.1, 1, 1);
		newCreature.resists(1, 0.5, 1.5, 1, 1, 1, 1, 1, 1);
		newCreature.addSpawn("Fire", 4, 5, 80);
		newCreature.addAbility("Bite");
		newCreature.addAbility("Firebreath");
		newCreature.addAbility("Regenerate");
		database.put(newCreature.name, newCreature);

		newCreature = new CreatureData("Rock Crawler", "rock crawler.png", 7);
		newCreature.baseStats(4,10,2,2,4,6,8,4);
		newCreature.statGain(1, 2.5, 1, 1, 1, 1.5, 2, 1);
		newCreature.resists(1, 1.5, 0.5, 0.5, 1, 1, 1.5, 1, 1);
		newCreature.addSpawn("Earth", 6, 8, 120);
		newCreature.addAbility("Bite");
		newCreature.addAbility("Rock Drop");
		newCreature.addAbility("Terraform");
		newCreature.addAbility("Erosion");
		database.put(newCreature.name, newCreature);
		
		newCreature = new CreatureData("Skyfish", "skyfish.png", 2);
		newCreature.baseStats(2, 4, 7, 4, 8, 2, 3, 10);
		newCreature.statGain(0.5, 1, 1.5, 1, 2, 1, 1, 4);
		newCreature.resists(1, 1, 0, 1, 0, 1, 1.5, 1.5, 2);
		newCreature.addSpawn("Wind", 10, 5, 90);
		newCreature.addAbility("Breeze");
		newCreature.addAbility("Tornado");
		newCreature.addAbility("Squirt");
		database.put(newCreature.name, newCreature);

		newCreature = new CreatureData("Terrorfly", "terrorfly.png", 2);
		newCreature.baseStats(2,4,8,4,6,8,4,4);
		newCreature.statGain(1,1.5,2,1,1,2,1.5,2);
		newCreature.resists(1.5, 1.5, 1, 1, 0, 1.5, 1.25, 1.5, 0);
		newCreature.addSpawn("Wind", 8, 3, 160);
		newCreature.addAbility("Bite");
		newCreature.addAbility("Breeze");
		newCreature.addAbility("Noxious Breath");
		newCreature.addAbility("Agony Venom");
		database.put(newCreature.name, newCreature);
		
		newCreature = new CreatureData("Trematay", "trematay.png", 5);
		newCreature.baseStats(8,11,2,3,10,2,2,2);
		newCreature.statGain(1.5, 2, 0.5, 1, 4, 0.5, 0.5, 0.5);
		newCreature.resists(0.5, 1, 2, 0.5, 0.25, 1, 1.25, 1.5, 1);
		newCreature.addSpawn("Wind", 7, 7, 50);
		newCreature.addAbility("Bite");
		newCreature.addAbility("Tremble");
		newCreature.addAbility("Burrow");
		newCreature.addAbility("Dust Cloud");
		database.put(newCreature.name, newCreature);
		
		newCreature = new CreatureData("Uglosh", "uglosh.png", 9);
		newCreature.baseStats(9,12,2,3,4,1,2,7);
		newCreature.statGain(2.5, 3, 0.5, 1, 1, 0.5, 0.5, 1.5);
		newCreature.resists(0.5, 1, 1, 1, 0.5, 0.5, 2, 2, 0.5);
		newCreature.addSpawn("Void", 10, 3, 70);
		newCreature.addAbility("Bite");
		newCreature.addAbility("Engulf");
		newCreature.addAbility("Noxious Breath");
		newCreature.addAbility("Darkness Shroud");
		database.put(newCreature.name, newCreature);
		
		newCreature = new CreatureData("Water Golem", "water golem.png", 1);
		newCreature.baseStats(1,6,3,3,14,12,2,1);
		newCreature.statGain(0.5, 2, 1, 1, 3, 3, 1, 0.5);
		newCreature.resists(0, 1, 0.5, 1, 2, 0.5, 1, 0.5, 1.5);
		newCreature.addSpawn("Water", 11, 6, 120);
		newCreature.addAbility("Squirt");
		newCreature.addAbility("Typhoon");
		newCreature.addAbility("Heavy Rain");
		newCreature.addAbility("Tsunami");
		database.put(newCreature.name, newCreature);
		
		newCreature = new CreatureData("Wattlin", "wattlin.png", 6);
		newCreature.baseStats(2,4,10,4,2,2,4,12);
		newCreature.statGain(0.5, 1, 2, 1.5, 1, 1, 1, 2.5);
		newCreature.resists(1, 1, 0.25, 1.75, 1, 1, 1, 1, 1);
		newCreature.addSpawn("Water",1,3, 100);
		newCreature.addAbility("Bite");
		newCreature.addAbility("Squirt");
		newCreature.addAbility("Steam Cloud");
		newCreature.addAbility("Cold Cloud");
		database.put(newCreature.name, newCreature);
		
		newCreature = new CreatureData("Wlen", "wlen.png", 10);
		newCreature.baseStats(4,6,2,8,2,2,4,12);
		newCreature.statGain(1, 2, 0.5, 1.5, 1, 1, 1, 2.5);
		newCreature.resists(2, 0.75, 1.25, 1, 0.75, 0, 1, 1, 1.25);
		newCreature.addSpawn("Fire",12,3, 70);
		newCreature.addAbility("Peck");
		newCreature.addAbility("Firebreath");
		newCreature.addAbility("Regenerate");
		database.put(newCreature.name, newCreature);
		
		/*
		 * Diety creature entities
		 * 
		 * 
		 */
		newCreature = new CreatureData("Pyracious", "pyracious.png", 3);
		newCreature.baseStats(16,12,6,2,12,10,6,6);
		newCreature.statGain(5, 4, 2.5, 1, 5, 4, 1.5, 2);
		newCreature.resists(0.5, 0, 0.5, 1, 1.5, 1, 1, 1.25, 1.25);
		newCreature.addAbility("Phoenix Split");
		newCreature.addAbility("Phoenix Flare");
		newCreature.addAbility("Phoenix Cannon");
		newCreature.addAbility("Phoenix Rebirth");
		newCreature.addSpell("Meteor Shower");
		newCreature.addSpell("Magma Wave");
		newCreature.addSpell("Blastfire");
		newCreature.addWeapon("Phoenix Blade");
		
		database.put(newCreature.name, newCreature);
		
		newCreature = new CreatureData("Vroson", "vroson.png", 5);
		newCreature.baseStats(16,12,6,2,12,10,6,6);
		newCreature.statGain(5, 4, 2.5, 1, 5, 4, 1.5, 2);
		newCreature.resists(0.5, 0, 0.5, 1, 1.5, 1, 1, 1.25, 1.25);
		newCreature.addAbility("Frostbite");
		newCreature.addAbility("Frost Blast");
		newCreature.addSpell("Frostspawn");
		newCreature.addSpell("Frostocalypse");
		newCreature.addSpell("Red Ice");
		newCreature.addAbility("Frosty Breath");
		newCreature.addAbility("Terraform");
		newCreature.addWeapon("Snowflake Defense");
		
		database.put(newCreature.name, newCreature);
	}
	
	public int typeToIndex(String habitat)
	{
		if(habitat.equals("Fire"))
		{
			return 0;
		}
		if(habitat.equals("Water"))
		{
			return 1;
		}
		if(habitat.equals("Earth"))
		{
			return 2;
		}
		if(habitat.equals("Wind"))
		{
			return 3;
		}
		if(habitat.equals("Life"))
		{
			return 4;
		}
		if(habitat.equals("Time"))
		{
			return 5;
		}
		if(habitat.equals("Space"))
		{
			return 6;
		}
		if(habitat.equals("Void"))
		{
			return 7;
		}
		
		return 0;
	}
	
	public void initialize(Core coreModule, Location[][][] planetHolders)
	{
		//add each creature to each planet
		for(String k : database.keySet())
		{
			CreatureData d = database.get(k);
			
			if(d.hasMap)
			{
				Location[][] planet = planetHolders[typeToIndex(d.habitat)];
				
				if(planet[d.habitatY-1][d.habitatX-1]!=null)
				{
					planet[d.habitatY-1][d.habitatX-1].spawns.load(coreModule, d.name, d.spawnRate, d.human);
					planet[d.habitatY-1][d.habitatX-1].hasCreature = true;
				}
			}
		}
		
		//give our creatures to our neighbors
		for(String k : database.keySet())
		{
			CreatureData d = database.get(k);
			
			if(d.hasMap)
			{
				Location[][] planet = planetHolders[typeToIndex(d.habitat)];
				
				for(int i=-1; i<2; i++)
				{
					for(int j=-1; j<2; j++)
					{
						if(Math.abs(i)!=Math.abs(j))
						{
							if(d.habitatY+i >= 1 && d.habitatY+i<=8 && d.habitatX+j >= 1 && d.habitatX+j<=16)
							{
								if(planet[d.habitatY+i-1][d.habitatX+j-1]!=null && planet[d.habitatY+i-1][d.habitatX+j-1].danger_level > 0)
									planet[d.habitatY+i-1][d.habitatX+j-1].spawns.load(coreModule, d.name, (int)(d.spawnRate/10.0), d.human);
							}
							else if(d.habitatY+i < 1)
							{
								if(planet[0][16-(d.habitatX)]!=null && planet[0][16-(d.habitatX)].danger_level > 0)
									planet[0][16-(d.habitatX)].spawns.load(coreModule, d.name, (int)(d.spawnRate/10.0), d.human);
							}
							else if(d.habitatY+i > 8)
							{
								if(planet[7][16-(d.habitatX)]!=null && planet[7][16-(d.habitatX)].danger_level > 0)
									planet[7][16-(d.habitatX)].spawns.load(coreModule, d.name, (int)(d.spawnRate/10.0), d.human);
							}
							else if(d.habitatX+j < 1)
							{
								if(planet[d.habitatY+i-1][15]!=null && planet[d.habitatY+i-1][15].danger_level > 0)
									planet[d.habitatY+i-1][15].spawns.load(coreModule, d.name, (int)(d.spawnRate/10.0), d.human);
							}
							else if(d.habitatX+j > 16)
							{
								if(planet[d.habitatY+i-1][0]!=null && planet[d.habitatY+i-1][0].danger_level > 0)
									planet[d.habitatY+i-1][0].spawns.load(coreModule, d.name, (int)(d.spawnRate/10.0), d.human);
							}
						}
					}
				}
			}
		}
	}
}
