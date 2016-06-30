package com.snorlon.sevestralkingdoms.Buildings;

import com.snorlon.sevestralkingdoms.GameTools.Core;
import com.snorlon.sevestralkingdoms.GameTools.InterfaceElement;
import com.snorlon.sevestralkingdoms.Places.Town;

public class Hospital extends BuildingBase
{

	public Hospital(Core gameCore, Town nTown, int nlevel)
	{
		super(gameCore, nTown, nlevel);
		
		name = "Hospital";
		
		sign_offset = 50;
	}
	
	public void initialize()
	{
		super.initialize();
	}
	
	public InterfaceElement GenerateImage(String containerName, String id, float scale)
	{
		buildingImage = new InterfaceElement(coreModule, "SpriteMap_Hospital", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 192, 192, 0, 0, scale, scale, "map/buildings/hospital.png");
		coreModule.renderModule.addElement(containerName, buildingImage);
		
		return buildingImage;
	}
	
	public void GenerateLink(String containerName, String id)
	{
		//some won't generate a link
		InterfaceElement newElement = new InterfaceElement(coreModule, "SpriteMap_Hospital_Text", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 218, 58, 0, 30, 1.0f, 1.0f, "interfaces/town_overview/name_label.png");
		coreModule.renderModule.addElement(containerName, newElement);

		newElement = new InterfaceElement(coreModule, "Inventory", "Center", "Center", 310, 70, 107, 12, 0.9f, "Hospital", coreModule.renderModule.FONT_IMPACT);
		coreModule.renderModule.addElement("SpriteMap_Hospital_Text", newElement);
		

		newElement = new InterfaceElement(coreModule, "SpriteMap_Hospital_Button", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 234, 66, 0, -120, 1.0f, 1.0f, "interfaces/town_overview/button.png");
		//new Gesture("Click", "interface~open~InterfaceBuildingGeneralStoreShop", newElement);
		coreModule.renderModule.addElement(containerName, newElement);

		//newElement = new InterfaceElement(coreModule, id, "Center", "Center", 210, 70, 72, 12, 1.0f, "Enter", coreModule.renderModule.FONT_IMPACT);
		//coreModule.renderModule.addElement("SpriteMap_Hospital_Button", newElement);
	}
	
	public void GenerateLink()
	{
		//some won't generate a link
		link = new InterfaceElement(coreModule, "SpriteMap_Hospital_Text", "SpriteMap", InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 192, 168, 0, 0, 1.0f, 1.0f, "map/gui/building_box.png");
		//new Gesture("Click", "do nothing", link);
		coreModule.renderModule.addElement("InterfaceContainerInline", link);

		InterfaceElement newElement = new InterfaceElement(coreModule, "Inventory", "Center", "Center", 310, 96, 105, 0, 1.0f, "Hospital", coreModule.renderModule.FONT_IMPACT);
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
}
