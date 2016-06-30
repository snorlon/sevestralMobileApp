package com.snorlon.sevestralkingdoms.GameTools;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class Character extends Sprite {
	int text_x = 0;
	int text_y = 0;
	
	Sprite original = null;

	int width = 0;//this is the width of the raw character
	int height = 0;//this is the height of the raw csharacter

	public Character(Sprite characterImg, int nwidth, int nheight) {
		super(characterImg);
		
		original = characterImg;
		
		width = nwidth;
		height = nheight;
	}
	
	public Character(Character oldCharacter) {
		super(oldCharacter.original);
		
		width = oldCharacter.width;
		height = oldCharacter.height;
	}
}
