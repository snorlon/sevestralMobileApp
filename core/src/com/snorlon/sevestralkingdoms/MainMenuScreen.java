package com.snorlon.sevestralkingdoms;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.snorlon.sevestralkingdoms.GameComponents.DeviceFeatures;
import com.snorlon.sevestralkingdoms.GameTools.Core;

public class MainMenuScreen implements Screen, InputProcessor {
	
    final Sevestral game;

    OrthographicCamera camera;
    
    //create our game core
    Core gameCore;
    
    Stage stage;
    MainMenuScreen menu;
    
    DeviceFeatures features;
    
    String testString = "HI";
    
    public MainMenuScreen(final Sevestral gam, boolean firstOperation) {    
        game = gam;
        
        features = game.features;
        
        gameCore = new Core(features);
        
        menu = this;
        
        
        camera = new OrthographicCamera();
        
        stage = new Stage();
        
        stage.getCamera().position.set(0,0,0);

        Gdx.input.setInputProcessor(this);   
        
        //pause();

    }
    

    public MainMenuScreen( Sevestral gam)
    {
    	//call normal operations
    	this(gam, false);
    }

	@Override
	public void render(float delta)
		{
	        
	        Gdx.gl.glClearColor(0.337f, 0.149f, 0.294f, 1);
	        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	        float deltaTime = Gdx.graphics.getDeltaTime();
	        stage.act(Math.min(deltaTime, 1 / 30f));
	        
	        //tick the core
	        gameCore.tick(deltaTime);
	        
	        //run ONLY this for now
	        gameCore.render(deltaTime); // render the game core
		}


	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return gameCore.touchUp(screenX, screenY, pointer, button);
	}


	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button)
	{
		return gameCore.touchDown(screenX, screenY, pointer, button);
	}


	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {		
		return gameCore.touchDragged(screenX, screenY, pointer);
	}

	@Override
	public void show()
		{
			// TODO Auto-generated method stub
			
		}

	@Override
	public void hide()
		{
			// TODO Auto-generated method stub
			
		}

	@Override
	public void pause()
		{
			
			
		}

	@Override
	public void resume()
		{
			// TODO Auto-generated method stub
			
		}
	
	@Override
    public void resize (int width, int height) {
        
    }
 
    @Override
    public void dispose () {
        stage.dispose();
        //mapFrameImg.dispose();
        
        //special toolset disposal
        gameCore.dispose();
    }


	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

        //...Rest of class omitted for succinctness.

}