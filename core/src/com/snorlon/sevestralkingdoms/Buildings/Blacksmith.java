package com.snorlon.sevestralkingdoms.Buildings;

import com.snorlon.sevestralkingdoms.GameTools.Core;
import com.snorlon.sevestralkingdoms.GameTools.Gesture;
import com.snorlon.sevestralkingdoms.GameTools.InterfaceElement;
import com.snorlon.sevestralkingdoms.Items.Shop;
import com.snorlon.sevestralkingdoms.Places.Town;

public class Blacksmith extends BuildingBase
{
	
	Shop smithShop = null;

	public Blacksmith(Core gameCore, Town nTown, int nlevel)
	{
		super(gameCore, nTown, nlevel);
		
		name = "Blacksmith";

		smithShop = new Shop(coreModule, associatedTown, "Blacksmith");
		
		sign_offset = -20;
	}
	
	public void initialize()
	{
		super.initialize();
		
		//give the shop some initial stock
		if(smithShop.getMerchandise().length == 0)
			smithShop.changeMerchandise(level);
	}
	
	public InterfaceElement GenerateImage(String containerName, String id, float scale)
	{
		buildingImage = new InterfaceElement(coreModule, "SpriteMap_Blacksmith", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 192, 192, 0, 0, scale, scale, "map/buildings/blacksmith.png");
		coreModule.renderModule.addElement(containerName, buildingImage);
		
		return buildingImage;
	}
	
	public void GenerateLink(String containerName, String id)
	{
		//some won't generate a link
		InterfaceElement newElement = new InterfaceElement(coreModule, "SpriteMap_Blacksmith_Text", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 218, 58, 0, 30, 1.0f, 1.0f, "interfaces/town_overview/name_label.png");
		coreModule.renderModule.addElement(containerName, newElement);

		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 310, 70, 91, 12, 0.9f, "Blacksmith", coreModule.renderModule.FONT_IMPACT);
		coreModule.renderModule.addElement("SpriteMap_Blacksmith_Text", newElement);
		

		newElement = new InterfaceElement(coreModule, "SpriteMap_Blacksmith_Button", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 234, 66, 0, -120, 1.0f, 1.0f, "interfaces/town_overview/button.png");
		new Gesture("Click", "interface~open~InterfaceBuildingGeneralStoreShop:Blacksmith", newElement);
		coreModule.renderModule.addElement(containerName, newElement);

		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 210, 70, 72, 12, 1.0f, "Enter", coreModule.renderModule.FONT_IMPACT);
		coreModule.renderModule.addElement("SpriteMap_Blacksmith_Button", newElement);
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
	
	public Shop getShop()
	{
		return smithShop;
	}
	
	public void stepTurn()
	{
		if(smithShop!=null)
			smithShop.stepTurn(level);
	}
}
