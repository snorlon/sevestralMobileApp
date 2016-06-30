/*package com.snorlon.sevestralkingdoms;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Sevestral extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();
	}
}*/

package com.snorlon.sevestralkingdoms;

import com.badlogic.gdx.Game;
import com.snorlon.sevestralkingdoms.GameComponents.DeviceFeatures;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;


public class Sevestral extends Game {

    public SpriteBatch batch;
    public BitmapFont font;
    public AssetManager manager = new AssetManager();
    public TextureAtlas atlas;
    public Skin skin;
    
    final DeviceFeatures features;
    
    public Sevestral(DeviceFeatures ourFeatures)
    {
    	this.features = ourFeatures;
    }

    public void create() {
        batch = new SpriteBatch();
        //Use LibGDX's default Arial font.
        font = new BitmapFont();
        

        manager.load("data/testing.atlas", TextureAtlas.class);  
        
        manager.finishLoading();
        
        atlas = manager.get("data/testing.atlas", TextureAtlas.class);  
        skin = new Skin(Gdx.files.internal("skin.json"), atlas);
        
        this.setScreen(new MainMenuScreen(this, true));
    }

    public void render() {
        super.render(); //important!
    }

    public void dispose() {
        batch.dispose();
        font.dispose();
        skin.dispose();
        atlas.dispose();
        pause();
    }

}
