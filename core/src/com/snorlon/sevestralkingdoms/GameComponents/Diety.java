package com.snorlon.sevestralkingdoms.GameComponents;

import java.util.ArrayList;

import com.snorlon.sevestralkingdoms.GameTools.Core;
import com.snorlon.sevestralkingdoms.GameTools.InterfaceElement;
import com.snorlon.sevestralkingdoms.GameTools.Renderer;
import com.snorlon.sevestralkingdoms.Places.Location;

public class Diety
{
	String name = "Error";
	String planet = "Fire";//default
	boolean leftSide = true;
	boolean defeated = false;
	boolean active = false;
	int x = -1;
	int y = -1;
	
	public Diety(String nname, String nplanet, boolean isLeft)
	{
		name = nname;
		planet = nplanet;
		leftSide = isLeft;
	}
	
	public void defeat()
	{
		defeated = true;
		active = false;
	}
	
	public void enable()
	{
		active = true;
		
		System.out.println(name+" has been enabled.");
	}
	
	public void disable()
	{
		active = false;

		System.out.println(name+" has been disabled.");
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getFilename()
	{
		return name.toLowerCase()+".png";
	}
	
	public void move(Core coreModule)
	{
		if(!active)
			return;
		
		//60% chance to move
		if(coreModule.random.nextInt(10) < 6)
		{
			//pick an adjacent location next to us
			Location[][] planetData = coreModule.gameModule.locationModule.getPlanet(planet);
			
			if(planetData == null)
				return;
			
			ArrayList<int[]> options = new ArrayList<int[]>();
			
			int maxX = 15;
			int minX = 0;
			
			if(leftSide)
				maxX = 7;
			else
				minX = 8;
			
			if(x >= minX && x<= maxX && y >= 0 && y < 8)
			{
				if(x+1 <= maxX)
				{
					if(planetData[y][x+1]!=null)
						options.add(new int[]{y,x+1});
				}
				if(x-1 >= minX)
				{
					if(planetData[y][x-1]!=null)
						options.add(new int[]{y,x-1});
				}
				if(y+1 < 8)
				{
					if(planetData[y+1][x]!=null)
						options.add(new int[]{y+1,x});
				}
				if(y-1 >= 0)
				{
					if(planetData[y-1][x]!=null)
						options.add(new int[]{y-1,x});
				}
			}
			
			//if no adjacent locations or we have few options to move around, move to a random location on the map
			if(options.size() > 0 && (options.size() > 2 || coreModule.random.nextInt(5) < options.size()+2))
			{
				int[] pair = options.get(coreModule.random.nextInt(options.size()));
				
				System.out.println(name+" has moved to ("+pair[1]+","+pair[0]+")");
				
				x = pair[1];
				y = pair[0];
			}
			else
			{
				//grab a random location in that range
				Location l = coreModule.gameModule.locationModule.getRandomLocation(planet, minX, maxX, 0, 7);
				
				if(l!=null)
				{
					x = l.getX();
					y = l.getY();
				
					System.out.println(name+" has randomly moved to ("+x+","+y+")");
				}
				else
				{
					System.out.println(name+" is unable to move anywhere in their territory!");
				}
			}
			
			
			options.clear();
		}
		
		//x = 12;
		//y = 5;
	}
	
	public InterfaceElement generateImage(Core coreModule, Renderer renderModule, String containerName, String containerID)
	{
		return generateImage(coreModule, renderModule, containerName, containerID, 0, 0);
	}
	
	public InterfaceElement generateImage(Core coreModule, Renderer renderModule, String containerName, String containerID, int offsetX, int offsetY)
	{
		coreModule.renderModule.destroyElement(name+containerID);
		
		//sprite
		InterfaceElement newElement1 = new InterfaceElement(coreModule, name+containerID, containerID, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", ((int) (98)), ((int) (98)), offsetX, offsetY);
		renderModule.addElement(containerName, newElement1);
		
		InterfaceElement newElement2 = new InterfaceElement(coreModule, name+"sprite", containerID, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 144, 72, 0, 15, 1.0f, 1.0f, "sprites/"+getFilename());
		newElement2.generateFrames(72, 72, 5, 0, 1, true);
		renderModule.addElement(name+containerID, newElement2);
		
		return newElement1;
	}
	
	public int getLevel(Core coreModule)
	{
		return (int) (150*coreModule.gameModule.gameConfig.getLevelMultiplier());
	}
}
