package com.snorlon.sevestralkingdoms.GameTools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Renderer {

	public final int FONT_IMPACT = 0;
	
	public static int NUM_CHARS = 150;

	InterfaceElement interfaceModule;
	InterfaceElement gfxModule;
	Core gameCore;
    public Skin skin;
    
    public int w = 1;
    public int h = 1;
    
    ArrayList<Character[]> fonts = new ArrayList<Character[]>();
    ArrayList<Texture> font_textures = new ArrayList<Texture>();
    
    Map<String, Texture> texture_cache = new HashMap<String, Texture>();//keep all textures here
    
    SpriteBatch renderImage;
    Stage gameStage;

    int frame = 0;
    public int lastFrame = 0;
    float timer = 1.0f;
    
	public Renderer(Core coreModule) {
		
		generate_font("Impact");
		

		gameCore = coreModule;
		renderImage = new SpriteBatch();
		gameStage = new Stage();
		
		//grab the core elements needed
		skin = gameCore.skin;
		
		//calculate a scale for everything to operate within
		w = Gdx.graphics.getWidth();
		h = Gdx.graphics.getHeight();
		float ratio = ((float) h)/w;
		float ratio_compare = 1280.0f/720.0f;
		
		//calculate new width and height, larger compared to ratio is restricted in ratio
		if(ratio > ratio_compare)//height is too big
		{
			coreModule.g_scale = w/720.0f;
		}
		else
		{
			coreModule.g_scale = h/1280.0f;
		}
		
		//print out some data to analyze
		System.out.println("Height: "+h+"px Width: "+w+"px");
		System.out.println("SCALE: "+coreModule.g_scale+"px");
		
		//core of the tree has no parent
		interfaceModule = new InterfaceElement(coreModule, "CenterNode", "NONE", InterfaceElement.TYPE_IMAGE, InterfaceElement.ABSOLUTE, "Center", "Center", 720, 1280, 0, 0);
		gfxModule =  new InterfaceElement(coreModule, "GFXLayer", "NONE", InterfaceElement.TYPE_CONTAINER, InterfaceElement.ABSOLUTE, "Center", "Center", 720, 1180, 0, 0);
	}
	
	public void clean()
	{
	}
	
	
	public int char_to_int(char character)
	{
		int return_index = NUM_CHARS - 1;

		if(character == ' ')
			return_index = 26;
		else if(character <= 'Z' && character >= 'A')
			return_index = character - 'A';
		else if(character <= 'z' && character >= 'a')
			return_index = character + 26 - 'a';
		else if(character <= '9' && character >= '0')
			return_index = character + 52 - '0';
		else if(character == '.')
			return_index = 62;
		else if(character == ',')
			return_index = 63;
		else if(character == ';')
			return_index = 64;
		else if(character == '!')
			return_index = 65;
		else if(character == '?')
			return_index = 66;
		else if(character == '"')
			return_index = 67;
		else if(character == '\'')
			return_index = 68;
		else if(character == ':')
			return_index = 69;
		else if(character == '$')
			return_index = 70;
		else if(character == '#')
			return_index = 71;
		else if(character == '-')
			return_index = 72;
		else if(character == '+')
			return_index = 73;
		else if(character == '=')
			return_index = 74;
		else if(character == '[')
			return_index = 75;
		else if(character == ']')
			return_index = 76;
		else if(character == '{')
			return_index = 77;
		else if(character == '}')
			return_index = 78;
		else if(character == '(')
			return_index = 79;
		else if(character == ')')
			return_index = 80;
		else if(character == '/')
			return_index = 81;
		else if(character == '&')
			return_index = 82;
		else if(character == '%')
			return_index = 83;
		
		return return_index;
	}
	
	private void generate_font(String fontName)
	{
		//load and generate some fonts yo
		//load texture
		Texture newFontTexture = new Texture(Gdx.files.internal("fonts/Impact.png"));
		newFontTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		//store it!
		font_textures.add(newFontTexture);
		
		FileHandle handle = Gdx.files.internal("Game Data/Fonts/"+fontName+".font");
		
		int widthOffset = 0;
		int heightOffset = 0;
		
		int startX = 0;//x coordinate starting point on texture
		int startY = 0;//y coordinate starting point on texture
		
		int width = 0;//where x ends on the image
		int height = 0;//where y ends on the image
		
		int characterIndex = 0;//for our position in our font character list

		String r = handle.readString();
		String parameters[] = r.split("\n");
		
		//get some preliminary data
		String fontData[] = (parameters[0].trim()).split("\t");
		
		//we need: spacing vertically and horizontally
		widthOffset = Integer.parseInt(fontData[1].trim());
		heightOffset = Integer.parseInt(fontData[2].trim());
		
		if(newFontTexture!=null)
		{
			Character charArray[] = new Character[NUM_CHARS];
			
			//run through all character data
			for(String p : parameters)
			{
				startX = 0;
				startY = 0;
				width = 1;
				height = 1;
				
				//if content on that line, break it down and store it
				if(p.length()>0 && (p.trim().charAt(0) != '|'))//skip the first few lines (start with |)
				{
					String params[] = (p.trim()).split("\t");
					
					//grab our data from the parameters
					char c = (params[0].trim()).charAt(0);//character we represent
					
					//turn it into an index!
					characterIndex = char_to_int(c);
					
					//get its start x
					startX = Integer.parseInt(params[1].trim());
					
					//start y
					startY = Integer.parseInt(params[2].trim());
					
					//width
					width = Integer.parseInt(params[3].trim());
					
					//height
					height = Integer.parseInt(params[4].trim());
					
					//turn it into a texture region
					TextureRegion newRegion = new TextureRegion(newFontTexture, startX, startY, width, height);
					
					//turn that into a sprite
					Sprite newSprite = new Sprite(newRegion);
					
					//turn that into a character
					Character newCharacter = new Character(newSprite, width-widthOffset, height-heightOffset);
					
					//System.out.println("Character: "+c+" Data: "+startX+" "+startY+" "+width+" "+height+" ");
					
					//store that character
					charArray[characterIndex] = newCharacter;
				}
			}
			
			//add array to the arraylist
			fonts.add(charArray);
		}
		
	}
	
	public void destroyElement(String id)
	{
		interfaceModule.destroyElement(id);
		gfxModule.destroyElement(id);
	}
	
	public InterfaceElement getElement(String id)
	{
		InterfaceElement e = gfxModule.getElement(id);
		if(e==null)
		{
			e = interfaceModule.getElement(id);
		}
		
		return e;
	}
	
	public void purgeGFX()
	{
		gfxModule.destroyChildren();
	}
	
	public void addElement(InterfaceElement parent, InterfaceElement newElement)
	{
		if(parent!=null)
		{
			addElement(parent.id, newElement);
		}
	}
	
	public void addElement(String parentID, InterfaceElement newElement)
	{
		//recalc all the way down the chain
		boolean found = interfaceModule.addElement(parentID, newElement);
		
		if(!found)
		{
			found = gfxModule.addElement(parentID,  newElement);
		}
		
		if(!found)
		{
			System.out.println("UI Element "+parentID+" not found!");
			newElement.dispose();//trash it if we failed to add it, stops error buildup in memory
		}
	}
	
	public void destroyGroup(String searchGroup)
	{
		interfaceModule.destroyGroup(searchGroup);
		gfxModule.destroyGroup(searchGroup);
	}
	
	public void render(float dt)
	{
		frame++;
		timer-=dt;
		if(timer<=0)
		{
			lastFrame = frame;
			//only print if there is less than 60 fps
			if(frame < 60)
				System.out.println("FPS: "+frame);
			frame = 0;
			timer = 1.0f;
		}
        
		gameStage.draw();
        
		renderImage.enableBlending();
        renderImage.begin();
        
    	//render the interface stuff
		interfaceModule.render();
		gfxModule.render();
			
		//render our particles
		//gameCore.particleModule.render(renderImage, dt);
        	
    	renderImage.end();
    	
        
	}
	
	public void dispose()
	{
		//destroy the interface
		interfaceModule.dispose();
		gfxModule.dispose();
		
		//destroy our fonts
	    for (Texture value : font_textures)
	    {
	    	value.dispose();//destroy the font
	    }
	    
	    //dispose of our texture cache
	    for(Texture value : texture_cache.values())
	    {
	    	value.dispose();
	    }
	}
	
	public void tick(float dt)
	{
		interfaceModule.tick(dt);
		gfxModule.tick(dt);
	}

	public Texture getTexture(String fileName) {
		Texture texture = null;

		//check if we already have it
		
		texture = texture_cache.get(fileName);
		
		if(texture == null)
		{
			//if not, make it, add it to texture list
			texture = new Texture(Gdx.files.internal(fileName));
			
			texture.setFilter(TextureFilter.Nearest,TextureFilter.Nearest);
			
			//cache it!
			texture_cache.put(fileName, texture);
		}
		
		//else, return the existing texture
		
		
		return texture;
	}

	public void destroyElement(InterfaceElement e)
	{
		interfaceModule.destroyElement(e);
		gfxModule.destroyElement(e);
	}
}
