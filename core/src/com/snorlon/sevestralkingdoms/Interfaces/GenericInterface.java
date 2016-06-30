package com.snorlon.sevestralkingdoms.Interfaces;

import com.snorlon.sevestralkingdoms.GameComponents.GameModule;
import com.snorlon.sevestralkingdoms.GameTools.Core;
import com.snorlon.sevestralkingdoms.GameTools.InterfaceElement;
import com.snorlon.sevestralkingdoms.GameTools.Renderer;

public class GenericInterface
{
	Core coreModule = null;
	GameModule gameModule = null;
	Renderer renderModule = null;
	protected InterfaceElement newElement = null;
	
	String id = "GenericInterface";
	
	public GenericInterface(Core newCore)
	{
		coreModule = newCore;
		renderModule = newCore.renderModule;
		gameModule = coreModule.gameModule;
	}
	
	//used to pull up the particular screen in question
	public void activate()
	{
				
	}
	
	
	//for explicitly getting rid of the interface
	public void destroy()
	{
		renderModule.destroyGroup(id);
	}
	
	//given a string of input, it will perform something upon the interface
	public void processInput(String inputString, String additionalInput)
	{
		
		
	}

	public String getID()
	{
		return id;
	}
	
	public void Tick(float dt)
	{
		//do nothing here
	}
}
