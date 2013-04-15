package com.oceanworld.actors;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Pool;
import com.oceanworld.OceanWorld;

public class SmallFish extends SwimmingActor
{

	private static Pool<SmallFish> pool = new Pool<SmallFish>()
			{

				@Override
				protected SmallFish newObject() {
					// TODO Auto-generated method stub
					return new SmallFish();
				}
		
			};
	private TextureRegion region;
	
	public static SmallFish obtainSmallFish(TextureRegion region)
	{
		SmallFish fish = pool.obtain();
		fish.setRegion(region);
		fish.setPosition(0, 0);
		return fish;
	}
	
	private SmallFish()
	{
		
	}
	
	public void setRegion(TextureRegion region)
	{
		this.region = region;
		this.setSize(region.getRegionWidth()* OceanWorld.IMAGES_RATIO, region.getRegionHeight()*OceanWorld.IMAGES_RATIO);
	}
	
	@Override
	public TextureRegion getRegion() {
		// TODO Auto-generated method stub
		return region;
	}

	public boolean remove()
	{
		if(super.remove())
		{
			pool.free(this);
			return true;
		}
		return false;
		
	}
}
