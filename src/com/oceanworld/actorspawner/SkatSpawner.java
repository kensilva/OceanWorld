package com.oceanworld.actorspawner;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.oceanworld.OceanWorld;
import com.oceanworld.actors.AnimatedSwimmingActor;


public class SkatSpawner extends Spawner
{
	private AnimatedSwimmingActor skat1;
	private AnimatedSwimmingActor skat2;
	
	private AnimatedSwimmingActor swimmingActor;

	protected float stateTime;
	protected float delayToNextSpawn = 100f;
	
	public SkatSpawner(Array<? extends TextureRegion> skat1Regions, Array<? extends TextureRegion> skat2Regions)
	{
		skat1 = new AnimatedSwimmingActor(skat1Regions);
		skat2 = new AnimatedSwimmingActor(skat2Regions);
		stateTime = MathUtils.random(0,100f);
		swimmingActor = MathUtils.randomBoolean()?skat1:skat2;

	}

	@Override
	public void update(float delta) 
	{
		stateTime += delta;
		if(stateTime >= delayToNextSpawn)
		{

			if(swimmingActor.getStage() == null)
			{
				stateTime =  stateTime - delayToNextSpawn;
				delayToNextSpawn = MathUtils.random(50f,100f);
				if(markToRemove)
				{
					this.stateTime = 0;
					spawnManager.unregisterSpawner(this);
				}
				else
				{
					resetSwimmingActor();
					nearGroup.addActor(swimmingActor);
				}
			}
			
		}
		
	}
	
	public void resetSwimmingActor()
	{
		swimmingActor = MathUtils.randomBoolean()?skat1:skat2;
		
		int randomizer = MathUtils.random(1, 2);
		
		switch(randomizer)
		{
			case 1:
			{
				path1();
				break;
			}
			case 2:
			{
				path2();
				break;
			}
		
		}
	}
	
	private void path1()
	{
		float startX =-swimmingActor.getWidth();
		float startY = OceanWorld.SCREEN_HEIGHT* 0.5f;
		
		swimmingActor.setSpeed(MathUtils.random(5f, 15f));
		swimmingActor.setFlipped(true);
		swimmingActor.setPosition(startX, startY);
		swimmingActor.setRotation(0f);

		
	}
	
	private void path2()
	{
		float startX = OceanWorld.SCREEN_WIDTH;
		float startY = OceanWorld.SCREEN_HEIGHT * 0.25f;
		
		swimmingActor.setSpeed(MathUtils.random(5f, 15f));
		swimmingActor.setPosition(startX, startY);
		swimmingActor.setFlipped(false);
		swimmingActor.setRotation(160f);

	}

}
