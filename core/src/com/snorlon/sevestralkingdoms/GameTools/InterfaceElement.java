package com.snorlon.sevestralkingdoms.GameTools;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class InterfaceElement {

	public static final int TYPE_ERROR = 0;
	public static final int TYPE_IMAGE = 1;
	public static final int TYPE_TEXT = 2;
	public static final int TYPE_TEXT_IMAGE = 4;
	public static final int TYPE_CONTAINER = 3;
	
	public static final int ABSOLUTE = 0;
	public static final int RELATIVE = 1;
	public static final int INHERITED = 2;
	
	//System data
	Core coreModule;
	
	//label exclusive stuff
	String text = "Error";
	float font_size = 1.0f;
	int font_index = 0;
	List<Character> textData = new ArrayList<Character>();
	
	//image exclusive stuff
	Texture imageTexture;
	//frame data
	List<Sprite> frames = new ArrayList<Sprite>();
	public int current_frame = 0;
	int original_frame = 0;
	int max_frame = 0;
	public int original_width = 1;
	public int original_height = 1;
	float time_delay = 10;
	boolean animated = false;
	boolean loops = false;
	boolean ended = false;
	boolean temporary = false;
	
	boolean flipped = false;
	
	//general stuff
	int cached_x = 0;
	int cached_y = 0;
	
	
	public int offset_x = 0;
	public int offset_y = 0;
	public int orig_offset_x = 0;
	public int orig_offset_y = 0;
	public float scale_x = 1.0f;
	public float scale_y = 1.0f;
	private int width = 1;
	private int height = 1;
	float red = 1.0f;
	float green = 1.0f;
	float blue = 1.0f;
	float update_delay = 0.0f;
	
	List<Gesture> events = new ArrayList<Gesture>();
	
	int priorX = -1;
	int priorY = -1;
	
	float timer = 10;
	
	String horizontal_align = "Left";
	
	String vertical_align = "Top";
	
	public String id = "Error";
	String group = "Error";
	int type = TYPE_ERROR;
	int positioning = ABSOLUTE;
	
	public InterfaceElement parent = null;
	
	float transparency = 1.0f;
	float fadeAmount = 0;
	boolean oscillatingTransparency = false;
	int oscillatingDirection = 1;
	float oscillationRate = 1.0f;

	List<InterfaceElement> containedElements = new ArrayList<InterfaceElement>();
	
	boolean visible = true;
	
	public InterfaceElement(Core gameCore, String nid, String ngroup, int ntype, int npositioning, String nhalign, String nvalign, int nwidth, int nheight, int noffset_x, int noffset_y)
	{
		coreModule = gameCore;
				
		id = nid;
		group = ngroup;
		type = ntype;
		positioning = npositioning;
		horizontal_align = nhalign;
		vertical_align = nvalign;
		
		width = (int) (nwidth);
		height = (int) (nheight);
		original_width = width;
		original_height = height;

		offset_x = (int) (noffset_x);
		offset_y = (int) (noffset_y);
		orig_offset_x = offset_x;
		orig_offset_y = offset_y;
		
		recalcEndX();
		recalcEndY();
	}
	
	//label contructor
	public InterfaceElement(Core gameCore, String ngroup, String nhalign, String nvalign, int nwidth, int nheight, int noffset_x, int noffset_y, float fontSize, String newtext, int font_id)
	{
		coreModule = gameCore;
		
		id = text;
		group = ngroup;
		type = TYPE_TEXT;
		positioning = RELATIVE;
		horizontal_align = nhalign;
		vertical_align = nvalign;
		
		width = (int) (nwidth);
		height = (int) (nheight);
		original_width = width;
		original_height = height;

		offset_x = (int) (noffset_x);
		offset_y = (int) (noffset_y);
		orig_offset_x = offset_x;
		orig_offset_y = offset_y;
		
		text = newtext;
        font_size = fontSize;
        font_index = font_id;
		
		recalcEndX();
		recalcEndY();
	}

	//image constructor
	public InterfaceElement(Core gameCore, String nid, String ngroup, int ntype, int npositioning, String nhalign, String nvalign, float nwidth, float nheight, float noffset_x, float noffset_y, float nscalex, float nscaley, String fileName)
	{
		coreModule = gameCore;
		
		id = nid;
		group = ngroup;
		type = ntype;
		positioning = npositioning;
		horizontal_align = nhalign;
		vertical_align = nvalign;
		
		width = (int) (nwidth);
		height = (int) (nheight);
		original_width = width;
		original_height = height;

		scale_x = nscalex;
		scale_y = nscaley;
		
		//get texture from renderer
		imageTexture = gameCore.renderModule.getTexture(fileName);
		imageTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		//generate a single frame
		Sprite newFrame = new Sprite(imageTexture);
		frames.add(newFrame);

		offset_x = (int) (noffset_x);
		offset_y = (int) (noffset_y);
		orig_offset_x = offset_x;
		orig_offset_y = offset_y;
		
		recalcEndX();
		recalcEndY();
	}
	
	public int getPositioning()
	{
		if(positioning!=INHERITED)
			return positioning;
		else if(parent!=null)
			return parent.getPositioning();
		else
			return ABSOLUTE;//when all else fails, return an absolute
	}
	
	public int getEndX()
	{
		return cached_x;
	}
	
	public void recalcEndX()
	{
		priorX = cached_x;
		float value = getOffsetX();
		int w = Gdx.graphics.getWidth();
		
		if(parent!=null)
		{
			if(positioning == RELATIVE || parent.getPositioning() == RELATIVE)
			{
				value += parent.getEndX();
			}
			
			if(horizontal_align == "Center")
			{
				//if we are aligned to center and have no parent, we should offset ourself
				value += (parent.getWidth()*parent.scale_x / 2) - (getWidth()*scale_x/2);
			}
			else if(horizontal_align == "Right")
			{
				//if we are aligned to center and have no parent, we should offset ourself
				value += parent.getWidth()*parent.scale_x - getWidth()*scale_x + (getWidth()*(scale_x-1.0f))/2;
			}
			else
			{
				//if we are aligned to center and have no parent, we should offset ourself
				value += 0;
			}
		}
		else
		{
			if(horizontal_align == "Center")
			{
				//if we are aligned to center and have no parent, we should offset ourself
				value += (w / 2) - (getWidth()*scale_x/2);
			}
			else if(horizontal_align == "Right")
			{
				//right positions so that we fit neatly on the right side
				value += w - getWidth()*scale_x;
			}
			else if(horizontal_align == "Left")
			{
				//left is no offset
			}
		}
		
		cached_x = (int) value;
	}
	
	public int getEndY()
	{
		return cached_y;
	}
	
	public void recalcEndY()
	{
		priorY = cached_y;
		float value = getOffsetY();

		if(parent!=null)
		{
			if(positioning == RELATIVE || parent.getPositioning() == RELATIVE)
			{
				value += parent.getEndY();
			}
			
			if(vertical_align == "Center")
			{
				//if we are aligned to center and have no parent, we should offset ourself
				value += (parent.getHeight()*parent.scale_y / 2) - (getHeight()*scale_y/2);
			}
			else if(vertical_align == "Top")
			{
				//if we are aligned to center and have no parent, we should offset ourself
				value += parent.getHeight()*parent.scale_y - height*scale_y + (getHeight()*(scale_y-1.0f))/2;
			}
			else
			{
				//bottom is no offset
			}
		}
		cached_y = (int) value;
	}
	
	public void recalcChildren()
	{
		recalcEndX();
		recalcEndY();
		
		for(InterfaceElement e : this.containedElements)
		{
			e.recalcChildren();
		}
	}
	
	public boolean addElement(String parentID, InterfaceElement newElement)
	{				
		//check to see if we can find it
		//first see if we are it
		if(id.equals(parentID))
		{
			//if so, store it in our container
			containedElements.add(newElement);
			//tell them who their parent is
			newElement.parent = this;
			
			//force text to regenerate here
			if(newElement.type == TYPE_TEXT)
				newElement.processText();
			
			return true;
		}
		//check our children
		for (InterfaceElement element : containedElements)
		{
			//if added, return true
			if(element.addElement(parentID, newElement))
			{
				return true;
			}
		}
		
		return false;
	}
	
	public InterfaceElement getElement(String searchID)
	{
		InterfaceElement returnElement = null;
		
		//check to see if we can find it
		//first see if we are it
		if(id.equals(searchID))
			returnElement = this;
		//check our children
		for (InterfaceElement element : containedElements)
		{
			if(returnElement == null)
			{
				//if found, return it
				InterfaceElement temp = element.getElement(searchID);
				if(temp!=null)
					returnElement = temp;
			}
		}
		
		return returnElement;
	}
	
	public int destroyElement(String searchID)
	{		
		//check to see if we can find it
		//if we are it, return 2 so caller can destroy us
		if(id == searchID)
			return 2;
		//check our children
		for (InterfaceElement element : containedElements)
		{
			//if found, return it
			if(element.destroyElement(searchID) == 2)
			{				
				//remove it
				containedElements.remove(element);
				
				//dispose it
				element.dispose();
				
				//return 1 for success
				return 1;
			}
		}
		
		return 0;//return 0 for failure
	}

	public int destroyElement(InterfaceElement e)
	{
		//check to see if we can find it
		//if we are it, return 2 so caller can destroy us
		if(this == e)
			return 2;
		//check our children
		for (InterfaceElement element : containedElements)
		{
			//if found, return it
			if(element.destroyElement(e) == 2)
			{				
				//remove it
				containedElements.remove(element);
				
				//dispose it
				element.dispose();
				
				//return 1 for success
				return 1;
			}
		}
		
		return 0;//return 0 for failure
	}
	
	public void destroyChildren()
	{
		ArrayList<InterfaceElement> disposal = new ArrayList<InterfaceElement>();
		
		for (InterfaceElement element : containedElements)
		{
			//remove it
			disposal.add(element);
			
			//dispose it
			element.dispose();
		}
		
		for(InterfaceElement element : disposal)
		{
			containedElements.remove(element);
		}
		
		disposal.clear();
	}
	
	public int destroyGroup(String searchGroup)
	{		
		//check to see if we can find it
		//if we are it, return 2 so caller can destroy us
		if(group.equals(searchGroup))
		{
			//destroy our contents
			for(InterfaceElement element : containedElements)
				element.dispose();
			
			//AND clear our elements
			containedElements.clear();
			return 2;
		}
		//check our children, remove them if they match
		for (Iterator<InterfaceElement> element = containedElements.iterator(); element.hasNext(); )
		{
			InterfaceElement nextElement = element.next();
			if(nextElement.destroyGroup(searchGroup) == 2)
			{
				//remove it
				element.remove();
				
				nextElement.dispose();
			}
		}
		
		return 0;//return 0 by default
	}
	
	//colors 0 to 255
	public void setColor(int r, int g, int b, float a)
	{
		red = r/255.0f;
		green = g/255.0f;
		blue = b/255.0f;
		transparency = a;
	}
	
	//break the texture into spaced frames

	public void generateFrames(int widthPerFrame, int heightPerFrame, float timeDelay, int startFrame, int mFrame, boolean isAnimated)
	{
		//call it with defaults of looping and permanent
		generateFrames(widthPerFrame, heightPerFrame, timeDelay, startFrame, mFrame, isAnimated, true, false);
	}
	
	public void generateFrames(int widthPerFrame, int heightPerFrame, float timeDelay, int startFrame, int mFrame, boolean isAnimated, boolean loop, boolean temp)
	{
		//first, clear out all of those silly frames
		frames.clear();
		
		current_frame = startFrame;
		original_frame = startFrame;
		max_frame = 0;
		
		loops = loop;
		temporary = temp;
		
		//generate frames to fit within it
		for(int i=0; i<original_height; i+=heightPerFrame)
		{
			for(int j=0; j<original_width; j+=widthPerFrame)
			{
				Sprite newSprite = new Sprite(new TextureRegion(imageTexture, j, i, widthPerFrame, heightPerFrame));
				frames.add(newSprite);

				max_frame++;
			}
		}
		
		if(mFrame<max_frame && mFrame!=0)
			max_frame = mFrame;
		
		//update the width and height to the frame size
		height = heightPerFrame;
		width = widthPerFrame;
		
		time_delay = timeDelay;
		animated = isAnimated;
		
		if(animated)
		{
			timer = time_delay;
		}
	}
	
	public int getWidth()
	{
		return (int) (width * coreModule.g_scale);
	}
	
	public int getHeight()
	{
		return (int) (height * coreModule.g_scale);
	}
	
	public int getOffsetX()
	{
		return (int) (offset_x * coreModule.g_scale);
	}
	
	public int getOffsetY()
	{
		return (int) (offset_y * coreModule.g_scale);
	}
	
	public void updateText(String newtext)
	{
		if(type == TYPE_TEXT)
		{
			text = newtext;
	        
	        processText();
		}
	}
	
	public void generateTransparencyAnimation(float newTransparency, float transparencyRate)
	{
		if(transparencyRate > 0)
		{
			fadeAmount = transparencyRate;
			transparency = newTransparency;
		}
		
		if(transparency < 0)
			transparency = 0;
		if(transparency > 1)
			transparency = 1;
		
		if(fadeAmount > 1.0f)
			fadeAmount = 1.0f;
		
		//apply to children!
		for(InterfaceElement element : containedElements)
		{
			element.generateTransparencyAnimation(newTransparency, transparencyRate);
		}
		
		temporary = true;
	}
	
	public void toggleOscilatingTransparency(float newRate)
	{
		oscillatingDirection = 1;
		transparency = 1.0f;
		oscillationRate = newRate;
		
		if(oscillatingTransparency)
		{
			oscillatingTransparency = false;
		}
		else
		{
			oscillatingTransparency = true;
		}
	}
	
	public boolean handleGesture(Gesture currentGesture)
	{
		if(!visible)
			return false;//can't recieve input if we aren't visible
		
		//check if our children can use it
		for ( int i = containedElements.size() - 1; i>= 0; i--)
		{
			InterfaceElement element = containedElements.get(i);
			
			if(element.handleGesture(currentGesture))
			{
				return true;
			}
		}
		
		//check if we can use it by checking each of our stored gestures
		for (int i = events.size() - 1; i>= 0; i--)
		{
			Gesture element = events.get(i);
			//check if we can use the gesture
			if(element.procced(coreModule, currentGesture))
			{
				//perform its function if we can
				coreModule.inputModule.processCommand(element);
				
				return true;
			}
		}
		
		return false;
	}
	
	public void tick(float dt)
	{
		//check if we should recalculate our position
		if(update_delay <= 0)
		{
			recalcEndX();
			recalcEndY();
			if(priorX != cached_x || priorY != cached_y)
				update_delay = 0.5f;
			else
				update_delay = 30.0f + coreModule.random.nextInt(11)+coreModule.random.nextFloat();
		}
		else
			update_delay -= dt;
		
		if(animated)
		{
			timer-=(dt*10);//adjust decrement amount
			if(timer<0.0f)
			{
				timer = time_delay;
				//loop through frames
				current_frame++;
				if(current_frame > max_frame)
				{
					if(loops)
						current_frame = original_frame;
					else//do not loop if we aren't supposed to, disable our animation when we hit end frame
					{
						current_frame--;
						animated = false;
						ended = true;
					}
				}
			}
		}
		if(oscillatingTransparency)
		{
			transparency -= oscillatingDirection * dt * oscillationRate;
			
			if(transparency < 0.5f && oscillatingDirection == 1)
			{
				oscillatingDirection = -1;
			}
			else if(transparency > 0.95f && oscillatingDirection == -1)
			{
				oscillatingDirection = 1;
			}
			
			if(transparency < 0)
				transparency = 0;
			if(transparency > 1)
				transparency = 1;
		}
		else
		{
			if(fadeAmount > 0 && fadeAmount <= 1.0f && transparency > 0)
			{
				transparency -= fadeAmount*dt;
				if(transparency < 0)
				{
					transparency = 0;
					ended = true;
				}
			}
		}
		
		ArrayList<InterfaceElement> garbage = new ArrayList<InterfaceElement>();
		
		for (InterfaceElement element : containedElements)
		{
			element.tick(dt);//tick those elements
			
			//remove them if they have ended
			if(ended && temporary)
			{
				garbage.add(element);
			}
		}
		
		for(InterfaceElement u : garbage)
		{
			this.destroyElement(u.id);
		}
		
		garbage.clear();
	}
	
	public void flip()
	{
		flipped = true;
	}
	
	public void unflip()
	{
		flipped = false;
	}
	
	public void processText()//text only
	{
		float x = 0;
		float y = 0;
		
		float g_scale = coreModule.g_scale;
		
		float y_gap = -32*font_size;
		
		Renderer renderModule = coreModule.renderModule;
		
		textData.clear();
		
		//create a copy with separations by spaces for word divisions
		String[] words = text.split(" ");
		
		for(int j=0; j<words.length; j++)
		{
			//calculate word predicted length
			float wordWidth = words[j].length()*23*scale_x;
			
			if((x+wordWidth)*g_scale >= getWidth())//reset our offset if we can't fit it
			{
				y+=y_gap;
				x = 0;
			}
			
			for(int i=0; i<words[j].length(); i++)
			{
				char c = words[j].charAt(i);
				int index = renderModule.char_to_int(c);
				
				if(index != Renderer.NUM_CHARS - 1)
				{
					//generate a character at this position in the character list
					Character characterImg = renderModule.fonts.get(font_index)[index];
					Character newImage = null;
					
					if(characterImg != null)
					{
						newImage = new Character(characterImg);
					}
					
					newImage.text_x = (int) x;
					newImage.text_y = (int) y;
					//add it to the list of characters
					textData.add(newImage);
					
					x+=characterImg.width*font_size;
				}
				else if(c == '\n')//for new lines
				{
					y+=y_gap;
					x = 0;
				}
			}
			
			//add an empty gap for spaces
			x+=(17*font_size);
		}
		
    	//draw a letter here
		/*int index = char_to_int('?');
    	fonts.get(0)[index].setPosition(30, 50);
    	fonts.get(0)[index].draw(renderImage);*/
	}

	public void render()
	{
		if(!visible)
			return;//don't draw us or our babies!
		//draw us
		if(type == TYPE_IMAGE || type == TYPE_TEXT_IMAGE)
		{
			//render image things
			if(frames.size()>current_frame)
			{
				Sprite currentFrameImage = frames.get(current_frame);
				
				//only draw if we have a current frame image
				if(currentFrameImage!=null)
				{
					//update key parts
					currentFrameImage.setOrigin(0,0);
					currentFrameImage.setPosition(getEndX(), getEndY());
					currentFrameImage.setColor(new Color(red, green, blue, transparency));
					currentFrameImage.setScale(scale_x*coreModule.g_scale, scale_y*coreModule.g_scale);
					if(flipped)
						currentFrameImage.flip(true, false);
					currentFrameImage.draw(coreModule.renderModule.renderImage, 1.0f);
					if(flipped)
						currentFrameImage.flip(true, false);//flip it back!
				}
			}
		}
		if(type == TYPE_TEXT || type == TYPE_TEXT_IMAGE)
		{			
			for(Character image : textData)
			{
				image.setOrigin(0,0);
				image.setPosition(image.text_x*coreModule.g_scale+getEndX(), image.text_y*coreModule.g_scale+getEndY());
				image.setColor(new Color(red, green, blue, transparency));
				image.setScale(font_size*coreModule.g_scale);
				image.draw(coreModule.renderModule.renderImage, 1.0f);
			}
		}
		
		//then draw our children
		for (InterfaceElement element : containedElements)
		{
			element.render();//render those elements
		}
	}

	public void dispose()
	{
		
		//then destroy our children
		for (InterfaceElement element : containedElements)
		{
			element.dispose();
		}
	}
	
	public void hide()
	{
		visible = false;
		
		for (InterfaceElement element : containedElements)
		{
			element.hide();
		}
	}
	
	public void show()
	{
		visible = true;
		
		for (InterfaceElement element : containedElements)
		{
			element.show();
		}
	}
}
