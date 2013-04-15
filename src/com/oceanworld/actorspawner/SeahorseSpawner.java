package com.oceanworld.actorspawner;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.oceanworld.OceanWorld;
import com.oceanworld.actors.AnimatedSwimmingActor;

public class SeahorseSpawner extends Spawner
{

	private AnimatedSwimmingActor seahorse;

	protected float stateTime;
	protected float delayToNextSpawn = 10f;
	
	public SeahorseSpawner(Array<? extends TextureRegion> regions)
	{
		seahorse = new AnimatedSwimmingActor(regions);
		stateTime = MathUtils.random(0,10f);
	}

	@Override
	public void update(float delta) 
	{
		stateTime += delta;
		if(stateTime >= delayToNextSpawn)
		{

			if(seahorse.getStage() == null)
			{
				
				stateTime =  stateTime - delayToNextSpawn;
				delayToNextSpawn = MathUtils.random(25f, 45f);
				if(markToRemove)
				{
					this.stateTime = 0;
					spawnManager.unregisterSpawner(this);
				}
				else
				{
					resetSeahorse();
					nearGroup.addActor(seahorse);
				}
			}
			
		}
		
	}
	
	public void resetSeahorse()
	{
		boolean rand = MathUtils.randomBoolean();
		if(rand)
		{
			float startX = -seahorse.getWidth();
			float startY = OceanWorld.SCREEN_HEIGHT * 0.25f;
			
			seahorse.setSpeed(MathUtils.random(20f,30f));
			seahorse.setPosition(startX, startY);
			seahorse.setRotation(35f);
			seahorse.setFlipped(true);
		}
		else
		{
			float startX = -seahorse.getWidth();
			float startY = OceanWorld.SCREEN_HEIGHT * 0.65f;
			
			seahorse.setSpeed(MathUtils.random(20f,30f));
			seahorse.setPosition(startX, startY);
			seahorse.setRotation(340f);
			seahorse.setFlipped(true);
		}

	}
	
}
