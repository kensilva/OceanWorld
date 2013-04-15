package com.oceanworld.actors;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.oceanworld.OceanWorld;

public class Dolphin extends SwimmingActor
{

	private TextureRegion region;
	
	public Dolphin(TextureRegion region)
	{
		this.region = region;
		this.setSize(region.getRegionWidth() * OceanWorld.IMAGES_RATIO, region.getRegionHeight() * OceanWorld.IMAGES_RATIO);
	}

	public void act(float delta)
	{
		super.act(delta);
		
	}
	
	@Override
	public TextureRegion getRegion() {
		// TODO Auto-generated method stub
		return region;
	}
	
	
}
