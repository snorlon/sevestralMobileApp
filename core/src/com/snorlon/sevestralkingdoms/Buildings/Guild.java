package com.snorlon.sevestralkingdoms.Buildings;
import java.util.ArrayList;

import com.snorlon.sevestralkingdoms.GameTools.Core;
import com.snorlon.sevestralkingdoms.GameTools.Gesture;
import com.snorlon.sevestralkingdoms.GameTools.InterfaceElement;
import com.snorlon.sevestralkingdoms.NPC.Unit;
import com.snorlon.sevestralkingdoms.Places.Location;
import com.snorlon.sevestralkingdoms.Places.Town;
import com.snorlon.sevestralkingdoms.Quests.Quest;
import com.snorlon.sevestralkingdoms.Quests.QuestList;

public class Guild extends BuildingBase
{
	public QuestList guildQuests = new QuestList(6);

	public Guild(Core gameCore, Town nTown, int nlevel)
	{
		super(gameCore, nTown, nlevel);
		
		name = "Guild";
		
		sign_offset = 40;
	}
	
	public void initialize()
	{
		super.initialize();
		
		
		if(guildQuests.getList().size() <= 0)
		{
			for(int i=0; i<2; i++)
			{
				generateQuest();
			}
		}
	}
	
	public InterfaceElement GenerateImage(String containerName, String id, float scale)
	{
		buildingImage = new InterfaceElement(coreModule, "SpriteMap_Guild", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 192, 192, 0, 0, scale, scale, "map/buildings/guild.png");
		coreModule.renderModule.addElement(containerName, buildingImage);
		
		return buildingImage;
	}
	
	public void GenerateLink(String containerName, String id)
	{
		//some won't generate a link
		InterfaceElement newElement = new InterfaceElement(coreModule, "SpriteMap_Guild_Text", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 218, 58, 0, 30, 1.0f, 1.0f, "interfaces/town_overview/name_label.png");
		coreModule.renderModule.addElement(containerName, newElement);

		newElement = new InterfaceElement(coreModule, "Inventory", "Center", "Center", 310, 70, 124, 12, 0.9f, "Guild", coreModule.renderModule.FONT_IMPACT);
		coreModule.renderModule.addElement("SpriteMap_Guild_Text", newElement);
		

		newElement = new InterfaceElement(coreModule, "SpriteMap_Guild_Button", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 234, 66, 0, -120, 1.0f, 1.0f, "interfaces/town_overview/button.png");
		new Gesture("Click", "interface~open~InterfaceBuildingGuild", newElement);
		coreModule.renderModule.addElement(containerName, newElement);

		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 210, 70, 72, 12, 1.0f, "Enter", coreModule.renderModule.FONT_IMPACT);
		coreModule.renderModule.addElement("SpriteMap_Guild_Button", newElement);
	}
	
	public void GenerateLink()
	{
		//some won't generate a link
		link = new InterfaceElement(coreModule, "SpriteMap_Guild_Text", "SpriteMap", InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 192, 168, 0, 0, 1.0f, 1.0f, "map/gui/building_box.png");
		new Gesture("Click", "interface~open~InterfaceBuildingGuild", link);
		coreModule.renderModule.addElement("InterfaceContainerInline", link);

		InterfaceElement newElement = new InterfaceElement(coreModule, "Inventory", "Center", "Center", 192, 96, 65, -10, 1.5f, "Guild", coreModule.renderModule.FONT_IMPACT);
		coreModule.renderModule.addElement("SpriteMap_Guild_Text", newElement);
		newElement = new InterfaceElement(coreModule, "Inventory", "Center", "Center", 210, 96, 41, -31, 0.75f, "Tap to enter", coreModule.renderModule.FONT_IMPACT);
		coreModule.renderModule.addElement("SpriteMap_Guild_Text", newElement);
		
		
	}
	
	public void DestroyImage()
	{
		super.DestroyImage();
	}
	
	public void Enter()
	{
		super.Enter();
	}
	
	public void Exit()
	{
		super.Exit();
	}
	
	public void stepTurn()
	{
		updateQuests();
	}
	
	public void updateQuests()
	{
		//this way the quest count ranges from 1 to 6
		int maxQuests = Math.min(Math.max((level/2)+1, 0),6);
		
		//this way level 10 always updates every turn
		//while level 1 only updates ~25% of the time
		int rate = 4-((level-1)/3);
		
		//first, risk to remove quest
		if(guildQuests.getList().size() > 0)
		{
			if(coreModule.random.nextInt(rate)==0)
			{
				removeQuest();
			}
		}
		
		for(int i=0; i<2; i++)
		{
			if(guildQuests.getList().size() < maxQuests && coreModule.random.nextInt(rate)==0)
			{
				generateQuest();
			}
		}
	}
	
	public void generateQuest()
	{
		//first determine the difficulty
			//min and max determined by guild level
		int minDifficulty = 1+(level/5);
		int maxDifficulty = 1+(level/3);
		
		int difficulty = coreModule.random.nextInt(1+maxDifficulty-minDifficulty)+minDifficulty;
		
		boolean isMoney = coreModule.random.nextBoolean();
		
		String creatureName = "Error";
		
		int killQty = difficulty*5;

		int x = associatedTown.ourLocation.getX();
		int y = associatedTown.ourLocation.getY();
		String planet = associatedTown.ourLocation.planet;
		
		//pick our target creature
			//aim for < players level+30
		ArrayList<Unit> possibleSpawns = new ArrayList<Unit>();
		ArrayList<Integer> possibleLevels = new ArrayList<Integer>();
		
		int maxLevel = 50;
		
		if(coreModule.gameModule.partyData.getMainCharacter()!=null)
			maxLevel = coreModule.gameModule.partyData.getMainCharacter().level+30;
		
		if(coreModule.gameModule.locationModule==null)
		{
			System.out.println("FUCK");
		}
		
		for(Location[] row : coreModule.gameModule.locationModule.getPlanet(planet))
		{
			for(Location l : row)
			{
				if(l!=null)
				{
					if(l.level < maxLevel && l.hasCreature)
					{
						possibleSpawns.add(l.spawns.spawns.get(0));
						possibleLevels.add(l.level);
					}
				}
			}
		}
		
		if(possibleSpawns.size() <= 0)
			return;//can't generate without a creature
		
		int index = coreModule.random.nextInt(possibleSpawns.size());
		creatureName = possibleSpawns.get(index).name;
		int level = possibleLevels.get(index);
		
		
		//now pick a reward and create the quest
		if(isMoney)
		{
			int reward = Math.max(difficulty*25*(level/5), 50);
			guildQuests.addQuest(Quest.REWARD_MONEY, difficulty, planet, x,y, creatureName, level, killQty, reward);
		}
		else
		{
			//we need to pick a random item for this quest
			guildQuests.addQuest(Quest.REWARD_ITEM, difficulty, planet, x,y, creatureName,level, killQty, coreModule.gameModule.itemLookupModule.getRandom(difficulty+(level/2)));
		}

		possibleSpawns.clear();
		possibleLevels.clear();
	}
	
	public void removeQuest()
	{
		
	}
}
