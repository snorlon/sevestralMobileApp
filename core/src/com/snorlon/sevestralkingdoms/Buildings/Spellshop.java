package com.snorlon.sevestralkingdoms.Buildings;

import com.snorlon.sevestralkingdoms.GameTools.Core;
import com.snorlon.sevestralkingdoms.GameTools.Gesture;
import com.snorlon.sevestralkingdoms.GameTools.InterfaceElement;
import com.snorlon.sevestralkingdoms.Items.Shop;
import com.snorlon.sevestralkingdoms.Places.Town;

public class Spellshop extends BuildingBase
{
	
	Shop spellShop = null;

	public Spellshop(Core gameCore, Town nTown, int nlevel)
	{
		super(gameCore, nTown, nlevel);
		
		name = "Spellshop";

		spellShop = new Shop(coreModule, associatedTown, "Spellshop");
		
		sign_offset = -20;
	}
	
	public void initialize()
	{
		super.initialize();
		
		//give the shop some initial stock
		if(spellShop.getMerchandise().length == 0)
			spellShop.changeMerchandise(level);
	}
	
	public InterfaceElement GenerateImage(String containerName, String id, float scale)
	{
		buildingImage = new InterfaceElement(coreModule, "SpriteMap_Spellshop", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 192, 192, 0, 0, scale, scale, "map/buildings/spellshop.png");
		coreModule.renderModule.addElement(containerName, buildingImage);
		
		return buildingImage;
	}
	
	public void GenerateLink(String containerName, String id)
	{
		//some won't generate a link
		InterfaceElement newElement = new InterfaceElement(coreModule, "SpriteMap_Spellshop_Text", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 218, 58, 0, 30, 1.0f, 1.0f, "interfaces/town_overview/name_label.png");
		coreModule.renderModule.addElement(containerName, newElement);

		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 310, 70, 96, 12, 0.9f, "Spellshop", coreModule.renderModule.FONT_IMPACT);
		coreModule.renderModule.addElement("SpriteMap_Spellshop_Text", newElement);
		

		newElement = new InterfaceElement(coreModule, "SpriteMap_Spellshop_Button", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 234, 66, 0, -120, 1.0f, 1.0f, "interfaces/town_overview/button.png");
		new Gesture("Click", "interface~open~InterfaceBuildingGeneralStoreShop:Spellshop", newElement);
		coreModule.renderModule.addElement(containerName, newElement);

		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 210, 70, 72, 12, 1.0f, "Enter", coreModule.renderModule.FONT_IMPACT);
		coreModule.renderModule.addElement("SpriteMap_Spellshop_Button", newElement);
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
		return spellShop;
	}
	
	public void stepTurn()
	{
		if(spellShop!=null)
			spellShop.stepTurn(level);
	}
}
