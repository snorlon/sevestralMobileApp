package com.snorlon.sevestralkingdoms.Quests;

import java.util.ArrayList;

public class QuestList
{
	int sizeLimit = 10;//maximum concurrent quests that can be stored
	ArrayList<Quest> quests = new ArrayList<Quest>();
	
	public QuestList(int size)
	{
		sizeLimit = size;
	}
	
	public void addQuest(Quest q)
	{
		if(quests.size() >= sizeLimit)
			return;//can't add quest then!
		
		quests.add(q);
	}
	
	public Quest addQuest(int ntype, int ndiff, String nplanet, int nx, int ny, String ntarget, int nLv, int nQty, int amount)
	{
		if(quests.size() >= sizeLimit)
			return null;//can't add quest then!
		
		Quest newQuest = new Quest(ntype, ndiff, nplanet, nx, ny, ntarget, nLv, nQty);
		
		newQuest.setReward(amount);
		
		quests.add(newQuest);
		
		return newQuest;
	}
	
	public Quest addQuest(int ntype, int ndiff, String nplanet, int nx, int ny, String ntarget, int nLv, int nQty, String nitem)
	{
		if(quests.size() >= sizeLimit)
			return null;//can't add quest then!
		
		Quest newQuest = new Quest(ntype, ndiff, nplanet, nx, ny, ntarget, nLv, nQty);
		
		newQuest.setReward(nitem);
		
		quests.add(newQuest);
		
		return newQuest;
	}
	
	public boolean isFull()
	{
		return quests.size() >= sizeLimit;
	}
	
	public ArrayList<Quest> getList()
	{
		return quests;
	}
}
