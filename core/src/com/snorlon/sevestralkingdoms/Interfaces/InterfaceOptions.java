package com.snorlon.sevestralkingdoms.Interfaces;

import com.snorlon.sevestralkingdoms.GameTools.Core;
import com.snorlon.sevestralkingdoms.GameTools.Gesture;
import com.snorlon.sevestralkingdoms.GameTools.InterfaceElement;

public class InterfaceOptions extends GenericInterface
{

	public InterfaceOptions(Core newCore)
	{
		super(newCore);
		// TODO Auto-generated constructor stub
		
		id = "InterfaceOptions";
	}
	
	//used to pull up the particular screen in question
	public void activate()
	{
		//create a gui container
		newElement = new InterfaceElement(coreModule, "InterfaceContainer", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 720, 1180, 0, 0);
		renderModule.addElement("CenterNode", newElement);
		
		//bg
		newElement = new InterfaceElement(coreModule, "bg", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 718, 1280, 24, 51, 1.0f, 1.0f, "menu/menubg.png");
		renderModule.addElement("InterfaceContainer", newElement);
		newElement = new InterfaceElement(coreModule, "overview_background_bg", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 718, 1280, -24, -50, 1.0f, 1.0f, "interfaces/options/background.png");
		renderModule.addElement("bg", newElement);
		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 700, 70, 220, 425, 2.0f, "Options", coreModule.renderModule.FONT_IMPACT);
		renderModule.addElement("bg", newElement);
		
		//spectate button
		/*newElement = new InterfaceElement(coreModule, "spectate_button_on", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 165, 170, 0, 325, 1.0f, 1.0f, "interfaces/options/button.png");
		newElement.generateFrames(165, 85, 0, gameModule.gameConfig.getSpectateFrameOn(), 1, false);
		new Gesture("Click", "interface~action~InterfaceOptions:SpectateOn", newElement);
		renderModule.addElement("InterfaceContainer", newElement);
		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 120, 70, 38, 0, 1.4f, "On", coreModule.renderModule.FONT_IMPACT);
		renderModule.addElement("spectate_button_on", newElement);		
		newElement = new InterfaceElement(coreModule, "spectate_button_off", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 165, 170, 0, 240, 1.0f, 1.0f, "interfaces/options/button.png");
		newElement.generateFrames(165, 85, 0, gameModule.gameConfig.getSpectateFrameOff(), 1, false);
		new Gesture("Click", "interface~action~InterfaceOptions:SpectateOff", newElement);
		renderModule.addElement("InterfaceContainer", newElement);
		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 120, 70, 32, 0, 1.4f, "Off", coreModule.renderModule.FONT_IMPACT);
		renderModule.addElement("spectate_button_off", newElement);
		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 210, 70, 45, 400, 1.0f, "Spectate", coreModule.renderModule.FONT_IMPACT);
		renderModule.addElement("InterfaceContainer", newElement);
		
		//autobattle button
		newElement = new InterfaceElement(coreModule, "autobattle_button_on", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 165, 170, -185, 325, 1.0f, 1.0f, "interfaces/options/button.png");
		newElement.generateFrames(165, 85, 0, gameModule.gameConfig.getAutobattleFrameOn(), 1, false);
		new Gesture("Click", "interface~action~InterfaceOptions:AutobattleOn", newElement);
		renderModule.addElement("InterfaceContainer", newElement);
		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 120, 70, 38, 0, 1.4f, "On", coreModule.renderModule.FONT_IMPACT);
		renderModule.addElement("autobattle_button_on", newElement);		
		newElement = new InterfaceElement(coreModule, "autobattle_button_off", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 165, 170, -185, 240, 1.0f, 1.0f, "interfaces/options/button.png");
		newElement.generateFrames(165, 85, 0, gameModule.gameConfig.getAutobattleFrameOff(), 1, false);
		new Gesture("Click", "interface~action~InterfaceOptions:AutobattleOff", newElement);
		renderModule.addElement("InterfaceContainer", newElement);
		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 120, 70, 32, 0, 1.4f, "Off", coreModule.renderModule.FONT_IMPACT);
		renderModule.addElement("autobattle_button_off", newElement);
		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 300, 70, -108, 400, 1.0f, "Auto-Battle", coreModule.renderModule.FONT_IMPACT);
		renderModule.addElement("InterfaceContainer", newElement);*/
		
		//fps button
		newElement = new InterfaceElement(coreModule, "fps_button_on", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 165, 170, 185, 325, 1.0f, 1.0f, "interfaces/options/button.png");
		newElement.generateFrames(165, 85, 0, gameModule.gameConfig.getFPSFrameOn(), 1, false);
		new Gesture("Click", "interface~action~InterfaceOptions:FPSOn", newElement);
		renderModule.addElement("InterfaceContainer", newElement);
		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 120, 70, 38, 0, 1.4f, "On", coreModule.renderModule.FONT_IMPACT);
		renderModule.addElement("fps_button_on", newElement);		
		newElement = new InterfaceElement(coreModule, "fps_button_off", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 165, 170, 185, 240, 1.0f, 1.0f, "interfaces/options/button.png");
		newElement.generateFrames(165, 85, 0, gameModule.gameConfig.getFPSFrameOff(), 1, false);
		new Gesture("Click", "interface~action~InterfaceOptions:FPSOff", newElement);
		renderModule.addElement("InterfaceContainer", newElement);
		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 120, 70, 32, 0, 1.4f, "Off", coreModule.renderModule.FONT_IMPACT);
		renderModule.addElement("fps_button_off", newElement);
		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 170, 70, 208, 400, 1.0f, "Show FPS", coreModule.renderModule.FONT_IMPACT);
		renderModule.addElement("InterfaceContainer", newElement);
		
		//show the options for the sfx volume
		for(int i=0; i<10; i++)
		{
			int frame = 1;
			if(gameModule.gameConfig.getSFXVolume() > i*10)
				frame = 0;
			
			newElement = new InterfaceElement(coreModule, "sfx_volume_"+i, id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 70, 60, -30 + i*18, -110, 1.0f, 1.0f, "interfaces/options/sound.png");
			newElement.generateFrames(30, 70, 0, frame, 1, false);
			//new Gesture("Click", "interface~action~InterfaceOptions:FPSOff", newElement);
			renderModule.addElement("InterfaceContainer", newElement);
		}
		newElement = new InterfaceElement(coreModule, "sfx_up", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 115, 85, 204, -110, 1.0f, 1.0f, "interfaces/options/button_small.png");
		new Gesture("Click", "interface~action~InterfaceOptions:SFXup", newElement);
		renderModule.addElement("InterfaceContainer", newElement);
		newElement = new InterfaceElement(coreModule, "sfx_down", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 115, 85, -100, -110, 1.0f, 1.0f, "interfaces/options/button_small.png");
		new Gesture("Click", "interface~action~InterfaceOptions:SFXdown", newElement);
		renderModule.addElement("InterfaceContainer", newElement);
		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 30, 30, 0, -40, 2.5f, "-", coreModule.renderModule.FONT_IMPACT);
		renderModule.addElement("sfx_down", newElement);
		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 30, 30, -7, -46, 2.5f, "+", coreModule.renderModule.FONT_IMPACT);
		renderModule.addElement("sfx_up", newElement);
		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 210, 70, 15, -45, 1.75f, "Volume", coreModule.renderModule.FONT_IMPACT);
		renderModule.addElement("InterfaceContainer", newElement);
		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 210, 70, -147, -120, 1.75f, "SFX", coreModule.renderModule.FONT_IMPACT);
		renderModule.addElement("InterfaceContainer", newElement);

		//show the options for the bgm volume
		for(int i=0; i<10; i++)
		{
			int frame = 1;
			if(gameModule.gameConfig.getBGMVolume() > i*10)
				frame = 0;
			
			newElement = new InterfaceElement(coreModule, "bgm_volume_"+i, id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 70, 60, -30 + i*18, -204, 1.0f, 1.0f, "interfaces/options/sound.png");
			newElement.generateFrames(30, 70, 0, frame, 1, false);
			//new Gesture("Click", "interface~action~InterfaceOptions:FPSOff", newElement);
			renderModule.addElement("InterfaceContainer", newElement);
		}
		newElement = new InterfaceElement(coreModule, "bgm_up", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 115, 85, 204, -204, 1.0f, 1.0f, "interfaces/options/button_small.png");
		new Gesture("Click", "interface~action~InterfaceOptions:BGMup", newElement);
		renderModule.addElement("InterfaceContainer", newElement);
		newElement = new InterfaceElement(coreModule, "bgm_down", id, InterfaceElement.TYPE_IMAGE, InterfaceElement.RELATIVE, "Center", "Center", 115, 85, -100, -204, 1.0f, 1.0f, "interfaces/options/button_small.png");
		new Gesture("Click", "interface~action~InterfaceOptions:BGMdown", newElement);
		renderModule.addElement("InterfaceContainer", newElement);
		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 30, 30, 0, -40, 2.5f, "-", coreModule.renderModule.FONT_IMPACT);
		renderModule.addElement("bgm_down", newElement);
		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 30, 30, -7, -46, 2.5f, "+", coreModule.renderModule.FONT_IMPACT);
		renderModule.addElement("bgm_up", newElement);
		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 210, 70, -155, -215, 1.75f, "BGM", coreModule.renderModule.FONT_IMPACT);
		renderModule.addElement("InterfaceContainer", newElement);
		
		newElement = new InterfaceElement(coreModule, "reset_game", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 547, 98, 0, -390);
		new Gesture("Click", "interface~action~InterfaceOptions:Reset", newElement);
		renderModule.addElement("InterfaceContainer", newElement);
		
		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 546, 70, 130, 0, 1.7f, "Reset Game", coreModule.renderModule.FONT_IMPACT);
		renderModule.addElement("reset_game", newElement);
		
		//return text
		newElement = new InterfaceElement(coreModule, "return_button", id, InterfaceElement.TYPE_CONTAINER, InterfaceElement.RELATIVE, "Center", "Center", 560, 60, -24, -554);
		new Gesture("Click", "interface~open~InterfaceMenuOverview", newElement);
		renderModule.addElement("bg", newElement);
		
		newElement = new InterfaceElement(coreModule, id, "Center", "Center", 546, 70, 100, 10, 1.2f, "Return to Main Menu", coreModule.renderModule.FONT_IMPACT);
		renderModule.addElement("return_button", newElement);
	}

	//given a string of input, it will perform something upon the interface
	public void processInput(String inputString, String additionalInput)
	{
		if(inputString.equals("SpectateOn"))
		{
			//gameModule.gameConfig.enableSpectate();
		}
		else if(inputString.equals("SpectateOff"))
		{
			//gameModule.gameConfig.disableSpectate();
		}
		else if(inputString.equals("FPSOn"))
		{
			gameModule.gameConfig.enableFPS();
		}
		else if(inputString.equals("FPSOff"))
		{
			gameModule.gameConfig.disableFPS();
		}
		else if(inputString.equals("AutobattleOn"))
		{
			//gameModule.gameConfig.enableAutobattle();
		}
		else if(inputString.equals("AutobattleOff"))
		{
			//gameModule.gameConfig.disableAutobattle();
		}
		else if(inputString.equals("SFXup"))
		{
			gameModule.gameConfig.SFXUp();
		}
		else if(inputString.equals("SFXdown"))
		{
			gameModule.gameConfig.SFXDown();
		}
		else if(inputString.equals("BGMup"))
		{
			gameModule.gameConfig.BGMUp();
		}
		else if(inputString.equals("BGMdown"))
		{
			gameModule.gameConfig.BGMDown();
		}
		else if(inputString.equals("Reset"))
		{
			gameModule.giveInterfaceInput("InterfaceMenuConfirm","activate","reset");
			return;
		}
		
		gameModule.activateInterface(id);
		gameModule.saveSave();
	}

}

