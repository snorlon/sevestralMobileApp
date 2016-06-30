package com.snorlon.sevestralkingdoms.Items;

import java.util.ArrayList;

import com.snorlon.sevestralkingdoms.GameTools.Core;
import com.snorlon.sevestralkingdoms.Places.Town;

public class Shop
{
	static int itemLimit = 25;
	
	Item[] stockedItems = new Item[itemLimit];
	int[] stockedCosts = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
	String shopType = "All";
	Town associatedTown = null;
	Core coreModule = null;
	
	public Shop(Core ncore, Town ntown, String nType)
	{
		coreModule = ncore;
		associatedTown = ntown;
		shopType = nType;
	}
	
	//this will randomly re-select stock that is appropriate for this type of shop
	public void changeMerchandise(int buildingLevel)
	{
		
		//first clear, our merchandise
		clearMerchandise();
		
		//stock should be dependent on the town terrain, planet, town level
		//higher town level = more items available and higher stock
		ArrayList<Integer> slots = new ArrayList<Integer>();
		
		for(int i=0; i<25; i++)
		{
			slots.add(i);
		}
		
		int stockNewAmount = Math.min(buildingLevel*2 + 5, 25);
		
		
		//now pick items dependent on how many we should stock
		for(int i=0; i<stockNewAmount && slots.size()>0; i++)
		{
			int slot = slots.get(coreModule.random.nextInt(slots.size()));
			
			if(coreModule.gameModule == null)
			{
				System.out.println("...");
			}
			
			stockedItems[slot] = coreModule.gameModule.itemLookupModule.generateItem(shopType);
			
			if(stockedItems[slot]!=null)
			{
				stockedCosts[slot] = associatedTown.getTaxedAmount(stockedItems[slot].base_value);
				
				//System.out.println("Shop generated "+stockedItems[slot].name+" in slot "+slot);
				
				slots.remove((Object) slot);
			}
		}
		
		slots.clear();
	}
	
	public Item[] getMerchandise()
	{
		return stockedItems;
	}
	
	public void pickItem()
	{
		
	}
	
	public boolean canBuyItem(int money, int index)
	{
		if(stockedItems[index]== null)
			return false;
		if(stockedCosts[index] <= money)
			return true;
		return false;
	}
	
	public int getCost(int index)
	{
		if(index < 0 || index >= itemLimit)
			return -1;
		
		return stockedCosts[index];
	}
	
	public void clearMerchandise()
	{
		for(int i=0; i<itemLimit; i++)
		{
			if(stockedItems[i] != null)
			{
				stockedItems[i] = null;
				stockedCosts[i] = 0;
			}
		}
		
		//System.out.println("Shop stock cleared!");
	}
	
	public Item getItem(int index)
	{
		if(index > 24)
			return null;
		if(index < 0)
			return null;
		
		return stockedItems[index];
	}

	public void takeItem(int itemIndex, Bag playerBag)
	{
		Item thisItem = stockedItems[itemIndex];
		if(thisItem!=null)
		{
			stockedItems[itemIndex] = null;
			
			playerBag.moveItem(thisItem);
		}
	}

	public Item takeItem(int itemIndex)
	{
		Item thisItem = stockedItems[itemIndex];
		if(thisItem!=null)
		{
			stockedItems[itemIndex] = null;
			
			return thisItem;
		}
		
		return null;
	}
	
	public void stepTurn(int level)
	{
		//random chance to update merchandise
		if(coreModule.random.nextInt(4) == 0)
		{
			System.out.println("Shop merchandise updated!");
			changeMerchandise(level);
		}
	}

	public void moveItem(Item newItem)
	{
		ArrayList<Integer> slots = new ArrayList<Integer>();
		//randomly pick a location to put the item
		for(int i=0; i<itemLimit; i++)
		{
			if(stockedItems[i]==null)
				slots.add(i);
		}
		int slot = slots.get(coreModule.random.nextInt(slots.size()));
		
		stockedItems[slot] = newItem;
		
		//generate its cost based on value and taxrate
		stockedCosts[slot] = associatedTown.getTaxedAmount(stockedItems[slot].base_value);
		
		//System.out.println("Loaded item "+newItem.name+" into slot "+slot);
		
		slots.clear();
	}

	public int countMerchandise()
	{
		int count = 0;
		
		for(Item i : stockedItems)
		{
			if(i!=null)
				count++;
		}
		
		return count;
	}
}
