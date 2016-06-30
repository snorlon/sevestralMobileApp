package com.snorlon.sevestralkingdoms.Housing;

import java.util.ArrayList;

public class House_Template
{
	String name = "error";
	
	int id = 0;//the level that this template is associated with
	
	int width = 12;//in tiles
	int height = 8;//in tiles
	
	int wallIndex = 0;
	int floorIndex = 20;
	
	int floorCount = 2;
	
	int doorIndex = 0;
	
	double value = 1.0;//value multiplier for the cost of building this house
	
	int objectLimit = 9;//this is to avoid overcluttering the view AND to keep the CPU from dieing in the phone

	ArrayList<Integer[]> blockedTiles = new ArrayList<Integer[]>();
	
	public House_Template(String nname, int nwidth, int nheight, int limit, int ndoor, int nwall, int nfloor, double nvalue, int nid, int floorC)
	{
		name = nname;
		width = nwidth;
		height = nheight;
		objectLimit = limit;
		doorIndex = ndoor;
		floorIndex = nfloor;
		wallIndex = nwall;
		value = nvalue;
		id = nid;
		floorCount = floorC;
	}
	
	public void blockTile(int x, int y)
	{
		Integer[] pair = {x,y};
		blockedTiles.add(pair);
	}
	
	public void dispose()
	{
		blockedTiles.clear();
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	public int getFloorCount()
	{
		return floorCount;
	}
	
	public int getID()
	{
		return id;
	}
}
