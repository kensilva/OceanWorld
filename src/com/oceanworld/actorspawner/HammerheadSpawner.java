package com.oceanworld.actorspawner;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.oceanworld.OceanWorld;
import com.oceanworld.actors.AnimatedSwimmingActor;


public class HammerheadSpawner extends Spawner
{

	private AnimatedSwimmingActor hammerhead;
	public HammerheadSpawner(Array<? extends TextureRegion> regions)
	{
		hammerhead = new AnimatedSwimmingActor(regions);
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
			
			if(hammerhead.getStage() == null)
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
					resetHammerhead();
					nearGroup.addActor(hammerhead);
				}
			}
			
		}
		
	}
	
	public void resetHammerhead()
	{
		int randomizer = MathUtils.random(1, 2);
		
		//randomizer = 2;
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
		float startX = OceanWorld.SCREEN_WIDTH * 0.5f;
		float startY = -hammerhead.getHeight();
		
		hammerhead.setSpeed(80f);
		hammerhead.setPosition(startX, startY);
		hammerhead.setRotation(100f);
		hammerhead.setFlipped(false);
		
	}
	
	private void path2()
	{
		float startX = OceanWorld.SCREEN_WIDTH;
		float startY = OceanWorld.SCREEN_HEIGHT * 0.75f;
		
		hammerhead.setSpeed(80f);
		hammerhead.setPosition(startX, startY);
		hammerhead.setFlipped(false);
		hammerhead.setRotation(215f);

	}

}
