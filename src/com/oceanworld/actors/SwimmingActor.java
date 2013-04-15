package com.oceanworld.actors;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


import com.badlogic.gdx.scenes.scene2d.Actor;


public abstract class SwimmingActor extends Actor
{
	public static enum Type
	{
		man,
		hammerhead,
		mermaid,
		seahorse,
		dolphin
	}
	

	private static final String rootDir = "data/swimmers/";
	public static final String mermaidLocation = rootDir+"mermaid";
	public static final String mermaidRegionName = "meramaid";
	public static final String manLocation = rootDir+"man";
	public static final String manName	   = "man2";
	public static final String hammerheadLocation = rootDir+"hammerhead";
	public static final String hammerheadName	  = "Hammerhead shark";
	public static final String sharkLocation = rootDir+"shark1";
	public static final String sharkName	 = "shark";
	public static final String swordfishLocation = rootDir+"swordfish";
	public static final String sworfishName	 = "Swordfish";
	public static final String jellyfishLocation = rootDir+"jellyfish";
	public static final String jellyfishName	 = "jellyfish";
	public static final String skat1Location = rootDir+"skat1";
	public static final String skat1Name	 = "skat1";
	public static final String skat2Location = rootDir+"skat2";
	public static final String skat2Name	 = "skat2";
	public static final String turtleLocation = rootDir+"turtle";
	public static final String turtleName	  = "turtle";
	public static final String fish1Location  = rootDir+"fish1";
	public static final String fish1Name	  = "fish1";
	public static final String fish2		  = rootDir+"fish2";
	public static final String fish2Name	  = "fish2";
	public static final String seahorseLocation = rootDir+"seahorse";
	public static final String seahorseName		= "sea horse";
	public static final String otherLocation	= rootDir+"nonanimated";
	
	
	protected float speed;
	protected float velocityX;
	protected float velocityY;
	protected float stateTime;
	protected boolean flipped;

	public void draw(SpriteBatch batch, float parentAlpha)
	{
		batch.setColor(getColor().r, getColor().g, getColor().b, getColor().a * parentAlpha);
		
		float scaleX = getScaleX();
		float scaleY = getScaleY();
		float rotation = getRotation();
		if(scaleX == 1 && scaleY == 1 && rotation == 0 )
		{
			batch.draw(getRegion(), getX(), getY(), getWidth(),getHeight());
			
		}
		else
		{
			
			batch.draw(getRegion(), getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), scaleX, scaleY, rotation);
		}
	}
	
	public void setFlipped(boolean isFlipped)
	{
		this.flipped = isFlipped;
		getRegion().flip(false, isFlipped);
	}
	
	public boolean isFlipped() 
	{
		return flipped;
	}
	
	
	
	public abstract TextureRegion getRegion();
	
}
