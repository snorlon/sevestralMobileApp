package com.snorlon.sevestralkingdoms.NPC;

import java.util.Hashtable;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.snorlon.sevestralkingdoms.GameComponents.Common;
import com.snorlon.sevestralkingdoms.GameTools.Core;
import com.snorlon.sevestralkingdoms.GameTools.InterfaceElement;
import com.snorlon.sevestralkingdoms.GameTools.Renderer;

public class HumanUnit extends Unit
{
	String outfitFilename = "platemail.png";
	String hairFilename = "brown calm.png";
	String faceFilename = "neutral blue.png";
	
	public boolean isMale = true;
	public boolean genderLocked = false;

	public HumanUnit(String nname, int nlevel, float baseStats[], float statGrowth[], float resist[], String nfaction, float animationSpeed, String nfilename, boolean genName, int nrarity, Core ncoreModule, boolean genStats)
	{
		super(nname, nlevel, baseStats, statGrowth, resist, nfaction, animationSpeed, nfilename, genName, nrarity, ncoreModule, genStats);
		
		isHuman = true;
	}
	
	public HumanUnit(Unit template) {
		super(template);
		
		if(template instanceof HumanUnit)
		{			
			genderLocked = ((HumanUnit) template).genderLocked;
			
			isMale = ((HumanUnit) template).isMale;	
		};
		
		isHuman = true;
	}

	public HumanUnit(Core gCore,String nname, int spawn_rate, Hashtable<String, CreatureData > creatureDB)
	{
		super(gCore,nname, spawn_rate, creatureDB);
		//System.out.println("Loading creature: "+nname);
		
		if(!creatureDB.containsKey(name))
		{
			System.out.println("Creature "+name+" not found!");
		}
		else
		{
			CreatureData data = creatureDB.get(name);

			if(data.gender.equals("Male"))
			{
				isMale = true;
				genderLocked = true;
			}
			else if(data.gender.equals("Female"))
			{
				isMale = false;
				genderLocked = true;
			}
		}
		
		isHuman = true;

		//System.out.println("Done loading creature: "+nname);
	}
	
	public void dress(String outfitFilenamen, String hairFilenamen, String faceFilenamen)
	{
		if(outfitFilenamen.length() > 0)
			outfitFilename = outfitFilenamen;
		if(hairFilenamen.length() > 0)
			hairFilename = hairFilenamen;
		if(faceFilenamen.length() > 0)
			faceFilename = faceFilenamen;
	}
	
	public InterfaceElement generateImage(Core coreModule, Renderer renderModule, String containerName, String containerID, float scale, int xOffset, int yOffset)
	{
		float animSpeed = generateAnimationSpeed();
		int genderOffset = 0;
		
		if(!isMale)
			genderOffset = 2;
		
		//sprite
		InterfaceElement newElementa = new InterfaceElement(coreModule, imageID+containerID, containerID, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", ((int) (98)), ((int) (98)), xOffset, yOffset+2);
		renderModule.addElement(containerName, newElementa);
		
		InterfaceElement newElement = new InterfaceElement(coreModule, imageID+"spritea", containerID, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 256, 64, 0, 0, scale, scale, "humanoids/base sheet/"+getFilename());
		newElement.generateFrames(64, 64, animSpeed, genderOffset, 1+genderOffset, true);
		renderModule.addElement(imageID+containerID, newElement);
		
		newElement = new InterfaceElement(coreModule, imageID+"spriteo", containerID, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 256, 64, 0, 0, scale, scale, "humanoids/outfit/"+outfitFilename);
		newElement.generateFrames(64, 64, animSpeed, 0+genderOffset, 1+genderOffset, true);
		renderModule.addElement(imageID+containerID, newElement);
		
		newElement = new InterfaceElement(coreModule, imageID+"sprited", containerID, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 128, 64, 0, 0, scale, scale, "humanoids/face/"+faceFilename);
		newElement.generateFrames(64, 64, animSpeed, 0, 1, true);
		renderModule.addElement(imageID+containerID, newElement);
		
		newElement = new InterfaceElement(coreModule, imageID+"spritee", containerID, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 128, 64, 0, 0, scale, scale, "humanoids/hair/"+hairFilename);
		newElement.generateFrames(64, 64, animSpeed, 0, 1, true);
		renderModule.addElement(imageID+containerID, newElement);
		
		/*InterfaceElement newElement2 = new InterfaceElement(coreModule, imageID+"sprite", containerID, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 144, 72, 0, 15, scale, scale, "sprites/"+getFilename());
		newElement2.generateFrames(72, 72, generateAnimationSpeed(), 0, 1, true);
		renderModule.addElement(imageID+containerID, newElement2);*/
		
		return newElementa;
	}
	
	public void setGender(boolean male)
	{
		isMale = male;
	}
	
	public String getOutfit()
	{
		return outfitFilename;
	}
	
	public String getHair()
	{
		return hairFilename;
	}
	
	public String getFace()
	{
		return faceFilename;
	}

}
