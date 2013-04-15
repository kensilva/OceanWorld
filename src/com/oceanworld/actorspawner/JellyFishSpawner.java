package com.oceanworld.actorspawner;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.oceanworld.OceanWorld;
import com.oceanworld.actors.AnimatedSwimmingActor;


public class JellyFishSpawner extends Spawner
{

	private AnimatedSwimmingActor jellyFish;
	public JellyFishSpawner(Array<? extends TextureRegion> regions)
	{
		jellyFish = new AnimatedSwimmingActor(regions);
		stateTime = MathUtils.random(0,10f);
	}
	

	protected float stateTime;
	protected float delayToNextSpawn = 10f;

	
	@Override
	public void update(float delta) 
	{
		stateTime += delta;
		if(stateTime >= delayToNextSpawn)
		{

			if(jellyFish.getStage() == null)
			{
				
				stateTime =  stateTime - delayToNextSpawn;
				delayToNextSpawn = MathUtils.random(10f, 30f);
				if(markToRemove)
				{
					this.stateTime = 0;
					spawnManager.unregisterSpawner(this);
				}
				else
				{
					resetJellyFish();
					nearGroup.addActor(jellyFish);
				}
			}
			
		}
		
	}
	
	public void resetJellyFish()
	{
		float startX = MathUtils.random(0, OceanWorld.SCREEN_WIDTH - jellyFish.getWidth());
		float startY = -jellyFish.getHeight();
		
		jellyFish.setSpeed(MathUtils.random(10f,20f));
		jellyFish.setPosition(startX, startY);
		jellyFish.setRotation(90);

	}
	
	
}
