package com.snorlon.sevestralkingdoms.Items;

import java.util.ArrayList;

import com.snorlon.sevestralkingdoms.GameTools.Core;

public class Bag
{
	Core coreModule = null;
	
	ArrayList<Item> items = new ArrayList<Item>();
	
	//this is the maximum number of items we can store in this bag
	int maxCapacity = 90;
	
	public Bag(Core newCore, int nMax)
	{
		coreModule = newCore;
		maxCapacity = nMax;
	}
	
	public boolean canGiveItem(Item newItem)
	{		
		if(items.size() < maxCapacity)
		{
			return true;
		}
		
		return false;
	}
	
	public void giveItem(String newItem)
	{
		Item itemTemplate = coreModule.gameModule.itemLookupModule.getItem(newItem);
		
		if(itemTemplate!=null)
		{
			this.giveItem(itemTemplate);
		}
	}
	
	public void moveItem(Item newItem)
	{
		if(!canGiveItem(newItem))
		{
			return;
		}
		
		items.add(newItem);
	}
	
	public void giveItem(Item newItem)
	{
		if(!canGiveItem(newItem))
		{
			return;
		}

		if(newItem instanceof Weapon)
			items.add(new Weapon(newItem));
		else if(newItem instanceof Spell)
			items.add(new Spell(newItem));
		else if(newItem instanceof Furniture)
			items.add(new Furniture((Furniture) newItem));
		else if(newItem instanceof Armor)
			items.add(new Armor(newItem));
		else
			items.add(new Item(newItem));
		System.out.println("Added item to bag.");
	}
	
	public ArrayList<Item> getContents()
	{
		return items;
	}

	public int getCapacity()
	{
		return maxCapacity;
	}

	public int getRemainingCapacity()
	{
		return maxCapacity-items.size();
	}

	public void removeItem(Furniture currentObject)
	{
		if(items.contains(currentObject))
			items.remove(currentObject);
	}
}
