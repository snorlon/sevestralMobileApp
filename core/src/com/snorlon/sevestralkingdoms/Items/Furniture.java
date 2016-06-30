package com.snorlon.sevestralkingdoms.Items;

import java.io.PrintWriter;

import com.snorlon.sevestralkingdoms.GameTools.Core;
import com.snorlon.sevestralkingdoms.GameTools.InterfaceElement;

public class Furniture extends Item
{		
	public static final int FURNITURE_FLOOR = 0;//can hold anything but floor and wall
	public static final int FURNITURE_BIG = 1;//can hold small items
	public static final int FURNITURE_MEDIUM = 4;//can hold nothing
	public static final int FURNITURE_SMALL = 2;//can hold nothing
	public static final int FURNITURE_WALL = 3;//can hold nothing
	
	public static final int ROTATION_0 = 0;
	public static final int ROTATION_90 = 1;
	public static final int ROTATION_180 = 2;
	public static final int ROTATION_270 = 3;
	
	//sprite is the same as the item name, with a .png on the end
	
	int rotation = ROTATION_0;
	
	int effective_height = 1;//this is how many tiles tall the object is in terms of density
	
	//center of object is bottom left tile, always
	
	int width = 1;//in tile units
	int height = 1;//pixel size is 32*this
	
	int furnitureType = FURNITURE_MEDIUM;
	
	boolean animated = false;
	
	//constructor for one-use items, no stats by default
	public Furniture(String nname, int newPath, int baseValue, int nwidth, int nheight, boolean anim, int nfurnitureType, int eheight)
	{
		super(nname, newPath, ITEM_TYPE_FURNITURE, ITEM_EFFECT_NONE, baseValue);
		
		width = nwidth;
		height = nheight;
		
		animated = anim;
		
		furnitureType = nfurnitureType;
		
		effective_height = eheight;
	}
	
	public Furniture(Furniture template)
	{
		super(template);
		
		width = template.width;
		height = template.height;
		
		animated = template.animated;
		
		furnitureType = template.furnitureType;
		
		effective_height = template.effective_height;
	}
	
	public void save(PrintWriter out, int layer, Core coreModule)
	{
		//print out tabs for every layer
		for(int i=0; i<layer; i++)
		{
			out.write('\t');
		}
		out.write("[FURNITURE]\t"+name+"\n");
	}
	
	public void save_house(PrintWriter out, int layer, Core coreModule, int floor, int x, int y)
	{
		//print out tabs for every layer
		for(int i=0; i<layer; i++)
		{
			out.write('\t');
		}
		out.write("[FURNITUREINTERNAL]\t"+name+"\t"+floor+"\t"+x+"\t"+y+"\t"+rotation+"\n");
	}
	
	public static int stringToFurniture(String t)
	{
		if(t.equals("Floor"))
			return FURNITURE_FLOOR;
		else if(t.equals("Big"))
			return FURNITURE_BIG;
		else if(t.equals("Small"))
			return FURNITURE_SMALL;
		else if(t.equals("Wall"))
			return FURNITURE_WALL;
		else if(t.equals("Medium"))
			return FURNITURE_MEDIUM;
		
		
		return FURNITURE_FLOOR;//default
	}

	public InterfaceElement generateImage(Core coreModule, float x2, float y2, float scale, String id, String rname)
	{
		int startFrame = rotation;
		
		if(animated)
		{
			InterfaceElement furnitureImage = 
					new InterfaceElement(coreModule, rname, id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", width*32*8, height*32*4, x2 + (width-1)*16*scale, y2 + (height-1)*16*scale, scale, scale, "furniture/objects/"+name+".png");
			
			startFrame = startFrame*8;
			furnitureImage.generateFrames(width*32, height*32, 1, startFrame, 7+rotation*8, true, true, false);
			
			return furnitureImage;
		}
		else
		{
			InterfaceElement furnitureImage = 
					new InterfaceElement(coreModule, rname, id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", width*32*4, height*32, x2 + (width-1)*16*scale, y2 + (height-1)*16*scale, scale, scale, "furniture/objects/"+name+".png");
			
			furnitureImage.generateFrames(width*32, height*32, 1, startFrame, 3, false);
			
			return furnitureImage;
		}
		
		
	}
	
	public void setRotation(int newRotation)
	{
		rotation = newRotation;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	public int getEHeight()
	{
		return effective_height;
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public String getName()
	{
		return name;
	}
	
	public int getFurnitureType()
	{
		return furnitureType;
	}
	
	public int getRotation()
	{
		return rotation;
	}
}
