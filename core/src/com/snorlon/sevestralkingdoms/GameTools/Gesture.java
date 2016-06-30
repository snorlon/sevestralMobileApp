package com.snorlon.sevestralkingdoms.GameTools;

public class Gesture
{
	String gestureName = "Click";
	String command = "None~";
	int startX = 0;
	int startY = 0;
	int endX = 0;
	int endY = 0;
	InterfaceElement parent;
	
	//for input manager
	public Gesture(String nname, int nsx, int nsy, int nex, int ney)
	{
		gestureName = nname;
		startX = nsx;
		startY = nsy;
		endX = nex;
		endY = ney;
	}
	
	//for the interface elements to check against
	public Gesture(String nname, String nCommand, InterfaceElement nparent)
	{
		gestureName = nname;
		command = nCommand;
		parent = nparent;
		
		//add us to the parents events
		parent.events.add(this);
	}
	
	public int distance()
	{
		return Math.abs(startX-endX)+Math.abs(startY-endY);
	}
	
	public int distance(int a, int b, int c, int d)
	{
		return Math.abs(a-b)+Math.abs(c-d);
	}
	
	public boolean withinBox(Core gameCore, int x, int y)
	{
		if(parent!=null)
		{			
			//if((x>parent.getEndX()*gameCore.g_scale) && (x<(parent.getEndX()+parent.getWidth())*gameCore.g_scale) && (y>parent.getEndY()*gameCore.g_scale) && (y<(parent.getEndY()+parent.getHeight())*gameCore.g_scale))
			if((x>parent.getEndX()) && (x<(parent.getEndX()+parent.getWidth())) && (y>parent.getEndY()) && (y<(parent.getEndY()+parent.getHeight())))
			{
				return true;
			}
		}
		
		return false;
	}
	
	public boolean procced(Core gameCore, Gesture testGesture)
	{
		if(testGesture==null)
			return false;//not procced if no gesture
		//check if within set boundaries
		if(withinBox(gameCore, testGesture.endX, testGesture.endY))
		{
			//System.out.println("Procced!");
			return true;
		}
		
		return false;
	}
}
