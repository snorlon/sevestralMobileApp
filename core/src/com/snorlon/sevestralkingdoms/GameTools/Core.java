package com.snorlon.sevestralkingdoms.GameTools;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.snorlon.sevestralkingdoms.Ability.Ability;
import com.snorlon.sevestralkingdoms.GameComponents.Common;
import com.snorlon.sevestralkingdoms.GameComponents.DeviceFeatures;
import com.snorlon.sevestralkingdoms.GameComponents.GameModule;
import com.snorlon.sevestralkingdoms.Items.Item;

public class Core
{
	public Renderer renderModule;
    public InputManager inputModule;
    public GameModule gameModule;
    //public ParticleManager particleModule; //disabled until further notice
    public SoundManager soundModule;
	
    public AssetManager assets = new AssetManager();
    public TextureAtlas atlas;
    public Skin skin;
	
	//loaded game data
	public ArrayList<Ability> ability_templates = new ArrayList<Ability>();

    public float g_scale = 1.0f;
    
    int version = 5;
    int minimumVersion = 5;
    
    DeviceFeatures features;
    
    public Random random = new Random();
	
	public Core(DeviceFeatures ourFeatures)
	{
		features = ourFeatures;
		
		//generate assets
		assets.load("data/testing.atlas", TextureAtlas.class);
		assets.finishLoading();
        atlas = assets.get("data/testing.atlas", TextureAtlas.class);  
        skin = new Skin(Gdx.files.internal("skin.json"), atlas);
        
        //particleModule = new ParticleManager(this);

        startGame();
	}
	
	public void startGame()
	{
		renderModule = new Renderer(this);
		inputModule = new InputManager(this);

		//Common.loadAbilities(this);
		
		//create the game later
		gameModule = new GameModule(this);
		gameModule.postInit();
		
		soundModule = new SoundManager(this);

		//gameModule.activateInterface("InterfaceHouse");
		//gameModule.activateInterface("InterfaceWorldMap");
		gameModule.activateInterface("InterfaceMainMenu");
		//gameModule.activateInterface("InterfaceMenuConfirm");
		//gameModule.activateInterface("InterfaceQuestDetailed");
		//gameModule.activateInterface("InterfaceBuildingGuild");
	}
	
	public void resetGame()
	{
		dispose();
		startGame();
	}
	
	public void dispose()
	{
		renderModule.dispose();
		skin.dispose();
		atlas.dispose();
		assets.dispose();
	}

	public void render(float dt)
	{
		renderModule.render(dt);
	}
	
	public boolean touchDown(int screenX, int screenY, int pointer, int button)
	{
		//flip the height
		screenY = Gdx.graphics.getHeight() - screenY;
		
		//log it in the input module, and return true
		//System.out.println("LOGGED "+screenX+"|"+screenY);
		Gesture newGesture = inputModule.touchDown(screenX, screenY, pointer, button);
		
		if(renderModule.interfaceModule.handleGesture(newGesture))//if someone used it, exit cleanly
		{
			return true;
		}
		else//if not, delete our gesture
		{
			
		}
		
		return true;
	}
	
	public boolean touchUp(int screenX, int screenY, int pointer, int button)
	{
		//flip the height
		screenY = Gdx.graphics.getHeight() - screenY;
		
		//generate a gesture
		Gesture newGesture = inputModule.touchUp(screenX, screenY, pointer, button);
		
		if(renderModule.interfaceModule.handleGesture(newGesture))//if someone used it, exit cleanly
		{
			return true;
		}
		else//if not, delete our gesture
		{
			
		}
		
		//assume failure if we reach here
		return false;
	}
	
	public boolean touchDragged(int screenX, int screenY, int pointer)
	{
		//flip the height
		screenY = Gdx.graphics.getHeight() - screenY;
		
		//generate a gesture
		Gesture newGesture = inputModule.touchDragged(screenX, screenY, pointer);
		
		if(renderModule.interfaceModule.handleGesture(newGesture))//if someone used it, exit cleanly
		{
			return true;
		}
		else//if not, delete our gesture
		{
			
		}
		
		//assume failure if we reach here
		return false;
	}
	
	public void tick(float dt)
	{		
		renderModule.tick(dt);
		
		inputModule.tick(dt);
				
		if(inputModule.activeInput)
		{
			//update the change in coordinates
			inputModule.changeX = (inputModule.currentX - renderModule.w/2);
			inputModule.changeY = (inputModule.currentY - renderModule.h/2);
			
		}
		
		gameModule.tick(dt);
		//particleModule.tick(dt);
		
		//features.checkNFC();
		
		soundModule.tick(dt);

	}

	public int getMinVersion()
	{
		return minimumVersion;
	}
	
	public int getVersion()
	{
		return version;
	}

	public Ability getAbility(String n)
	{
		for(Ability a : ability_templates)
		{
			if(a.name.equals(n))
				return a;
		}
		
		
		return null;
	}
}
