package com.oceanworld.actorspawner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.oceanworld.OceanWorld;
import com.oceanworld.actors.AnimatedSwimmingActor;

public class ManSpawner extends Spawner
{

	protected float stateTime;
	
	protected float delayToNextSpawn = 10f;
	
	protected AnimatedSwimmingActor man;
	
	public ManSpawner(Array<? extends TextureRegion> regions)
	{
		man = new AnimatedSwimmingActor(regions);
		stateTime = MathUtils.random(0, 15f);
	}
	
	@Override
	public void update(float delta) 
	{
		stateTime += delta;
		
		if(stateTime >= delayToNextSpawn)
		{

			if(man.getStage() == null)
			{
				stateTime = stateTime - delayToNextSpawn;
				delayToNextSpawn = MathUtils.random(15f, 30f);
				if(markToRemove)
				{
					this.stateTime = 0;
					spawnManager.unregisterSpawner(this);
				}
				else
				{
					resetMan();
					nearGroup.addActor(man);
				}
			}
			
			
		}
		
	}
	
	public void resetMan()
	{
		int randomizer =  MathUtils.random(1, 2);
		
		
		randomizer = 1;
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
		float startX = -man.getWidth();
		float startY = -man.getHeight();
		
		man.setSpeed(50f);
		man.setPosition(startX,startY);
		man.setFlipped(true);
		man.setRotation(50f);

		
	}
	
	private void path2()
	{
		float startX = OceanWorld.SCREEN_WIDTH;
		float startY = -man.getHeight();
		
		man.setSpeed(50f);
		man.setPosition(startX, startY);
		man.setFlipped(false);
		man.setRotation(135f);

	}

}
