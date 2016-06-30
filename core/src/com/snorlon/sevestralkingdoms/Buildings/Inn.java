package com.snorlon.sevestralkingdoms.Buildings;

import java.util.ArrayList;

import com.snorlon.sevestralkingdoms.Ability.Ability;
import com.snorlon.sevestralkingdoms.GameTools.Core;
import com.snorlon.sevestralkingdoms.GameTools.Gesture;
import com.snorlon.sevestralkingdoms.GameTools.InterfaceElement;
import com.snorlon.sevestralkingdoms.NPC.HumanUnit;
import com.snorlon.sevestralkingdoms.NPC.Unit;
import com.snorlon.sevestralkingdoms.Places.Town;

public class Inn extends BuildingBase
{
	
	ArrayList<Unit> hirableUnits = new ArrayList<Unit>();
	
	//generate 1-6 units based on the building level

	public Inn(Core gameCore, Town nTown, int nlevel)
	{
		super(gameCore, nTown, nlevel);
		
		name = "Inn";
		
		sign_offset = 40;
	}
	
	public void initialize()
	{
		super.initialize();
		
		//generate some units
		if(hirableUnits.size() == 0)
			updateHirable();
	}
	
	public InterfaceElement GenerateImage(String containerName, String id, float scale)
	{
		buildingImage = new InterfaceElement(coreModule, "SpriteMap_Inn", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 192, 192, 0, 0, scale, scale, "map/buildings/inn.png");
		coreModule.renderModule.addElement(containerName, buildingImage);
		
		return buildingImage;
	}
	
	public void GenerateLink(String containerName, String id)
	{
		//some won't generate a link
		InterfaceElement newElement = new InterfaceElement(coreModule, "SpriteMap_Inn_Text", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 218, 58, 0, 30, 1.0f, 1.0f, "interfaces/town_overview/name_label.png");
		coreModule.renderModule.addElement(containerName, newElement);

		newElement = new InterfaceElement(coreModule, "Inventory", "Center", "Center", 310, 70, 140, 12, 0.9f, "Inn", coreModule.renderModule.FONT_IMPACT);
		coreModule.renderModule.addElement("SpriteMap_Inn_Text", newElement);
		

		newElement = new InterfaceElement(coreModule, "SpriteMap_Inn_Button", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 234, 66, 0, -120, 1.0f, 1.0f, "interfaces/town_overview/button.png");
		new Gesture("Click", "interface~open~InterfaceBuildingInn", newElement);
		coreModule.renderModule.addElement(containerName, newElement);

		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 210, 70, 72, 12, 1.0f, "Enter", coreModule.renderModule.FONT_IMPACT);
		coreModule.renderModule.addElement("SpriteMap_Inn_Button", newElement);
	}
	
	public void GenerateLink()
	{
		//some won't generate a link
		link = new InterfaceElement(coreModule, "SpriteMap_Inn_Text", "SpriteMap", InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 192, 168, 0, 0, 1.0f, 1.0f, "map/gui/building_box.png");
		new Gesture("Click", "interface~open~InterfaceBuildingInn", link);
		coreModule.renderModule.addElement("InterfaceContainerInline", link);

		InterfaceElement newElement = new InterfaceElement(coreModule, "Inventory", "Center", "Center", 192, 96, 65, -10, 1.5f, "Inn", coreModule.renderModule.FONT_IMPACT);
		coreModule.renderModule.addElement("SpriteMap_Inn_Text", newElement);
		newElement = new InterfaceElement(coreModule, "Inventory", "Center", "Center", 210, 96, 41, -31, 0.75f, "Tap to enter", coreModule.renderModule.FONT_IMPACT);
		coreModule.renderModule.addElement("SpriteMap_Inn_Text", newElement);
		
		
	}
	
	public void DestroyImage()
	{
		super.DestroyImage();
	}
	
	public void Enter()
	{
		super.Enter();
	}
	
	public void updateHirable()
	{
		//determine if we need to destroy any
		int desiredSize = (this.level/2) + 1;
		
		if(desiredSize <= hirableUnits.size())
		{
			//randomly decide to remove a hirable unit
			//30% chance per update
			if(coreModule.random.nextInt(10) <= 2)
			{
				//randomly decide which to remove
				int index = coreModule.random.nextInt(hirableUnits.size());
				
				hirableUnits.remove(index);
			}
		}
		
		//generate until all slots are filled
		while(hirableUnits.size() < desiredSize)
		{
			//they should be people, so assume people are being made here
			HumanUnit newUnit = new HumanUnit("Human"+coreModule.random.nextInt(), coreModule.random.nextInt(99)+1, new float[]{coreModule.random.nextInt(20)+5,coreModule.random.nextInt(20)+5,coreModule.random.nextInt(20)+5,coreModule.random.nextInt(20)+5,coreModule.random.nextInt(20)+5,coreModule.random.nextInt(20)+5,coreModule.random.nextInt(20)+5,coreModule.random.nextInt(20)+5}, new float[]{coreModule.random.nextFloat()*3,coreModule.random.nextFloat()*3,coreModule.random.nextFloat()*3,coreModule.random.nextFloat()*3,coreModule.random.nextFloat()*3,coreModule.random.nextFloat()*3,coreModule.random.nextFloat()*3,coreModule.random.nextFloat()*3}, new float[]{1.0f,1.0f,1.0f,1.0f,1.0f,1.0f,1.0f,1.0f,1.0f}, "None", 5.0f, "caucasian.png", true, 1, coreModule, true);
			
			newUnit.setGender(coreModule.random.nextBoolean());
			
			newUnit.dress(coreModule.gameModule.fashionModule.getOutfit(coreModule, "Random"),coreModule.gameModule.fashionModule.getHair(newUnit.isMale),coreModule.gameModule.fashionModule.getFace());

			
			//randomly give them 3 abilities that are low quality
			ArrayList<String> abilityOptions = new ArrayList<String>();
			abilityOptions.add("Scorch");
			abilityOptions.add("Squirt");
			abilityOptions.add("Tremble");
			abilityOptions.add("Breeze");
			for(int i=0; i<2; i++)
			{
				String s = abilityOptions.get(coreModule.random.nextInt(abilityOptions.size()));
				
				abilityOptions.remove(s);
				
				newUnit.addSpellAbility(coreModule.ability_templates, s);
			}
			
			System.out.println("Inn spawned unit named "+newUnit.name);
			//System.out.println(""+newUnit.Strength+", "+newUnit.Toughness+", "+newUnit.Agility+", "+newUnit.Dexterity+", "+newUnit.Intelligence+", "+newUnit.Willpower+", "+newUnit.Charisma+", "+newUnit.Luck);
			//System.out.println(""+newUnit.Strength_growth+", "+newUnit.Toughness_growth+", "+newUnit.Agility_growth+", "+newUnit.Dexterity_growth+", "+newUnit.Intelligence_growth+", "+newUnit.Willpower_growth+", "+newUnit.Charisma_growth+", "+newUnit.Luck_growth);
			
			hirableUnits.add(newUnit);
		}
		
	}
	
	public void Exit()
	{
		super.Exit();
	}
	
	public void stepTurn()
	{
		updateHirable();
	}
	
	public ArrayList<Unit> getHirable()
	{
		return hirableUnits;
	}
}
