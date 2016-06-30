package com.snorlon.sevestralkingdoms.Buildings;

import com.snorlon.sevestralkingdoms.GameTools.Core;
import com.snorlon.sevestralkingdoms.GameTools.Gesture;
import com.snorlon.sevestralkingdoms.GameTools.InterfaceElement;
import com.snorlon.sevestralkingdoms.Housing.House;
import com.snorlon.sevestralkingdoms.Items.Shop;
import com.snorlon.sevestralkingdoms.Places.Town;

public class HouseBuilding extends BuildingBase
{
	House house = null;
	
	public HouseBuilding(Core gameCore, Town nTown, int nlevel)
	{
		super(gameCore, nTown, nlevel);
		
		name = "House";
		
		sign_offset = -20;
	}
	
	public void initialize()
	{
		super.initialize();
		
		if(house==null)
		{
			//attempt to load the corresponding house to our level
			house = new House(coreModule, coreModule.gameModule.houseTemplates, level);
			
			if(house == null || house.getFloorCount() == 0)
			{
				house = null;//abort using the house if it's bogus, as this means that we failed to load it's template
				System.out.println("House has failed to be generated, template does not exist for level "+level+".");
			}
			else
			{
				System.out.println("House has been successfully generated! It is of design "+house.getDesign());
			}
		}
	}
	
	public InterfaceElement GenerateImage(String containerName, String id, float scale)
	{
		buildingImage = new InterfaceElement(coreModule, "SpriteMap_House", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 192, 192, 0, 0, scale, scale, "map/buildings/house_"+level+".png");
		coreModule.renderModule.addElement(containerName, buildingImage);
		
		return buildingImage;
	}
	
	public void GenerateLink(String containerName, String id)
	{
		//some won't generate a link
		InterfaceElement newElement = new InterfaceElement(coreModule, "SpriteMap_House_Text", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 218, 58, 0, 30, 1.0f, 1.0f, "interfaces/town_overview/name_label.png");
		coreModule.renderModule.addElement(containerName, newElement);

		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 310, 70, 91, 12, 0.9f, "Your House", coreModule.renderModule.FONT_IMPACT);
		coreModule.renderModule.addElement("SpriteMap_House_Text", newElement);
		

		newElement = new InterfaceElement(coreModule, "SpriteMap_House_Button", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 234, 66, 0, -120, 1.0f, 1.0f, "interfaces/town_overview/button.png");
		new Gesture("Click", "interface~open~InterfaceHouse", newElement);
		coreModule.renderModule.addElement(containerName, newElement);

		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 210, 70, 72, 12, 1.0f, "Enter", coreModule.renderModule.FONT_IMPACT);
		coreModule.renderModule.addElement("SpriteMap_House_Button", newElement);
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
	
	public House getHouse()
	{
		return house;
	}
}
