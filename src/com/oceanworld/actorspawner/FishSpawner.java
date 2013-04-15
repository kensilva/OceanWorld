package com.oceanworld.actorspawner;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.oceanworld.OceanWorld;
import com.oceanworld.actors.AnimatedSwimmingActor;

public class FishSpawner extends Spawner
{
	private AnimatedSwimmingActor fish1;
	private AnimatedSwimmingActor fish2;
	
	private AnimatedSwimmingActor swimmingActor;
	
	public FishSpawner(Array<? extends TextureRegion> fish1Regions, Array<? extends TextureRegion> fish2Regions)
	{
		fish1 = new AnimatedSwimmingActor(fish1Regions);
		fish2 = new AnimatedSwimmingActor(fish2Regions);
		stateTime = MathUtils.random(0,10f);
		//swimmingActor = MathUtils.randomBoolean()?shark:swordfish;
		swimmingActor = fish1;
	}
	

	protected float stateTime;
	protected float delayToNextSpawn = 10f;

	
	@Override
	public void update(float delta) 
	{
		stateTime += delta;
		if(stateTime >= delayToNextSpawn)
		{

			
			if(swimmingActor.getStage() == null)
			{
				if(markToRemove)
				{
					this.stateTime = 0;
					spawnManager.unregisterSpawner(this);
				}
				else
				{
					stateTime =  stateTime - delayToNextSpawn;
					delayToNextSpawn = MathUtils.random(10f, 30f);
					resetSwimmingActor();
					nearGroup.addActor(swimmingActor);
				}
			}
			
		}
		
	}
	
	public void resetSwimmingActor()
	{
		swimmingActor = MathUtils.randomBoolean()?fish1:fish2;

		
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
		float startX = -swimmingActor.getWidth();
		float startY = OceanWorld.SCREEN_HEIGHT* 0.5f;
		
		swimmingActor.setSpeed(MathUtils.random(50f, 100f));
		swimmingActor.setFlipped(true);
		swimmingActor.setPosition(startX, startY);
		swimmingActor.setRotation(0f);

		
	}
	
	private void path2()
	{
		float startX = OceanWorld.SCREEN_WIDTH;
		float startY = OceanWorld.SCREEN_HEIGHT * 0.75f;
		
		swimmingActor.setSpeed(MathUtils.random(50f, 100f));
		swimmingActor.setPosition(startX, startY);
		swimmingActor.setFlipped(false);
		swimmingActor.setRotation(200f);

	}

}
