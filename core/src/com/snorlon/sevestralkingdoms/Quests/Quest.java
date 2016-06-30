package com.snorlon.sevestralkingdoms.Quests;

public class Quest
{
	public final static int REWARD_MONEY = 0;
	public final static int REWARD_ITEM = 1;
	public final static int REWARD_ACHIEVEMENT = 2;// unused currently
	public final static int REWARD_MILITARY = 3;
	public final static int REWARD_MYSTERY = 4;
	
	//item reward
	String itemReward = "None";
	//money reward
	int moneyReward = 0;
	
	int militaryReward = 0;
	
	//reward type
	int rewardType = REWARD_MONEY;
	
	//monster name to be culled
	String target = "Error";
	
	//monster count to be killed
	int targetQty = 5;
	
	//current killed count
	int targetCurrQty = 0;
	
	int targetLevel = 1;
	
	//difficulty rating
	int difficulty = 1;//on a scale of 1 to 5
	
	//planet it's for
	String planet = "Fire";
	
	//coordinates of town that issued it
		//for giving loyalty to player for finishing it
	int x = 1;
	int y = 1;
	
	public Quest(int ntype, int ndifficulty, String nplanet, int nx, int ny, String ntarget, int nLv, int nTargetQty)
	{
		rewardType = ntype;
		difficulty = ndifficulty;
		planet = nplanet;
		x = nx;
		y = ny;
		
		targetLevel = nLv;
		
		target = ntarget;
		targetQty = nTargetQty;
		targetCurrQty = 0;
	}
	
	public boolean isComplete()
	{
		if(targetCurrQty>=targetQty)
			return true;
		
		return false;
	}
	
	public boolean validTarget(String tName)
	{
		if(tName.toLowerCase().equals(target.toLowerCase()))
			return true;
		return false;
	}
	
	public int getTargetLevel()
	{
		return targetLevel;
	}
	
	public int getDifficulty()
	{
		return difficulty;
	}
	
	public String getItem()
	{
		return itemReward;
	}
	
	public int getAmount()
	{
		if(rewardType == REWARD_MONEY)
		{
			return moneyReward;
		}
		else
			return militaryReward;
	}
	
	public int getRewardType()
	{
		return rewardType;
	}
	
	public int getY()
	{
		return y;
	}
	
	public int getX()
	{
		return x;
	}
	
	public String getPlanet()
	{
		return planet;
	}
	
	public int getQtyCurr()
	{
		return targetCurrQty;
	}
	
	public int getQtyMax()
	{
		return targetQty;
	}
	
	public String getCreature()
	{
		return target;
	}
	
	public void setReward(int amount)
	{
		if(rewardType == REWARD_MONEY)
			moneyReward = amount;
		else
			militaryReward = amount;
	}
	
	public void setReward(String nitem)
	{
		itemReward = nitem;
	}
	
	public String getDataString()
	{
		return ""+rewardType+"\t"+difficulty+"\t"+planet+"\t"+x+"\t"+y+"\t"+target+"\t"+targetQty+"\t"+targetCurrQty+"\t"+moneyReward+"\t"+militaryReward+"\t"+itemReward+"\t"+targetLevel;
		//[Quest]	QuestList	RewardType	Difficulty	Planet	X	Y	Target	Target#	TargetCurr#	GoldAmt	ArmyAmt	ItemName
	}

	public void setCurrQty(int tC)
	{
		targetCurrQty = tC;
	}

	public void incQty()
	{
		targetCurrQty++;
	}
}
