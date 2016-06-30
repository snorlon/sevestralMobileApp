package com.snorlon.sevestralkingdoms.Buildings;

import com.snorlon.sevestralkingdoms.GameTools.Core;
import com.snorlon.sevestralkingdoms.GameTools.Gesture;
import com.snorlon.sevestralkingdoms.GameTools.InterfaceElement;
import com.snorlon.sevestralkingdoms.Items.Shop;
import com.snorlon.sevestralkingdoms.Places.Town;

public class GeneralStore extends BuildingBase
{
	
	Shop generalShop = null;

	public GeneralStore(Core gameCore, Town nTown, int nlevel)
	{
		super(gameCore, nTown, nlevel);
		
		name = "General Store";

		generalShop = new Shop(coreModule, associatedTown, "General Store");
		
		sign_offset = -20;
	}
	
	public void initialize()
	{
		super.initialize();
		
		//give the shop some initial stock
		if(generalShop.getMerchandise().length == 0)
			generalShop.changeMerchandise(level);
	}
	
	public InterfaceElement GenerateImage(String containerName, String id, float scale)
	{
		buildingImage = new InterfaceElement(coreModule, "SpriteMap_GeneralStore", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 192, 192, 0, 0, scale, scale, "map/buildings/general store.png");
		coreModule.renderModule.addElement(containerName, buildingImage);
		
		return buildingImage;
	}
	
	public void GenerateLink(String containerName, String id)
	{
		//some won't generate a link
		InterfaceElement newElement = new InterfaceElement(coreModule, "SpriteMap_GeneralStore_Text", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 218, 58, 0, 30, 1.0f, 1.0f, "interfaces/town_overview/name_label.png");
		coreModule.renderModule.addElement(containerName, newElement);

		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 310, 70, 69, 12, 0.9f, "General Store", coreModule.renderModule.FONT_IMPACT);
		coreModule.renderModule.addElement("SpriteMap_GeneralStore_Text", newElement);
		

		newElement = new InterfaceElement(coreModule, "SpriteMap_GeneralStore_Button", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 234, 66, 0, -120, 1.0f, 1.0f, "interfaces/town_overview/button.png");
		new Gesture("Click", "interface~open~InterfaceBuildingGeneralStoreShop:General Store", newElement);
		coreModule.renderModule.addElement(containerName, newElement);

		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 210, 70, 72, 12, 1.0f, "Enter", coreModule.renderModule.FONT_IMPACT);
		coreModule.renderModule.addElement("SpriteMap_GeneralStore_Button", newElement);
	}
	
	public void GenerateLink()
	{
		//some won't generate a link
		link = new InterfaceElement(coreModule, "SpriteMap_GeneralStore_Text", "SpriteMap", InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 192, 168, 0, 0, 1.0f, 1.0f, "map/gui/building_box.png");
		new Gesture("Click", "interface~open~InterfaceBuildingGeneralStoreShop", link);
		coreModule.renderModule.addElement("InterfaceContainerInline", link);

		InterfaceElement newElement = new InterfaceElement(coreModule, "Inventory", "Center", "Center", 310, 96, 64, 0, 1.0f, "General Store", coreModule.renderModule.FONT_IMPACT);
		coreModule.renderModule.addElement(link.id, newElement);
		newElement = new InterfaceElement(coreModule, "Inventory", "Center", "Center", 210, 96, 41, -31, 0.75f, "Tap to enter", coreModule.renderModule.FONT_IMPACT);
		coreModule.renderModule.addElement(link.id, newElement);
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
		return generalShop;
	}
	
	public void stepTurn()
	{
		if(generalShop!=null)
			generalShop.stepTurn(level);
	}
}
