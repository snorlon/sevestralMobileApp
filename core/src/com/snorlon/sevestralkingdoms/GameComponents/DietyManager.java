package com.snorlon.sevestralkingdoms.GameComponents;

import java.util.ArrayList;

import com.snorlon.sevestralkingdoms.GameTools.Core;

public class DietyManager
{
	Core coreModule;
	
	ArrayList<Diety> dieties = new ArrayList<Diety>();

	public DietyManager(Core nCore)
	{
		coreModule = nCore;
	}
	
	public void initialize()
	{
		//add the dieties if they aren't already present
		Diety newDiety = null;
		
		newDiety = new Diety("Pyracious","Fire",true);
		dieties.add(newDiety);
		
		newDiety = new Diety("Vroson","Water",true);
		dieties.add(newDiety);
		
		//give them a random initial position and enable them
		for(Diety d : dieties)
		{
			d.move(coreModule);
			d.enable();
			
			for(int i=0; i<30; i++)
			{
				d.move(coreModule);
			}
		}
	}
	
	public void stepTurn()
	{
		for(Diety d : dieties)
		{
			d.move(coreModule);
		}
	}
	
	public Diety getDiety(int x, int y, String planet)
	{
		for(Diety d : dieties)
		{
			if(d.planet.equals(planet) && d.x == x && d.y == y && d.active)
				return d;
		}
		
		return null;
	}
}
