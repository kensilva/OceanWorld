package com.oceanworld.actorspawner;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.oceanworld.OceanWorld;
import com.oceanworld.actors.AnimatedSwimmingActor;
import com.oceanworld.actors.SwimmingActor;


public class SharkSwordFishSpawner extends Spawner
{
	private AnimatedSwimmingActor swordfish;
	private AnimatedSwimmingActor shark;
	
	private AnimatedSwimmingActor swimmingActor;
	
	private boolean hasSword;
	private boolean hasShark;
	
	public SharkSwordFishSpawner(Array<? extends TextureRegion> sharkRegions,Array<? extends TextureRegion> swordFishRegions,boolean hasShark, boolean hasSword)
	{
		swordfish = new AnimatedSwimmingActor(swordFishRegions);
		shark = new AnimatedSwimmingActor(sharkRegions);
		stateTime = MathUtils.random(0,10f);
		this.hasSword = hasSword;
		this.hasShark = hasShark;
		
		setSwimmingActor();
	}
	

	private void setSwimmingActor()
	{
		if(hasSword && hasShark)
		{
			swimmingActor = MathUtils.randomBoolean()?shark:swordfish;
		}
		else if(hasShark)
		{
			swimmingActor = shark;
		}
		else
		{
			swimmingActor = swordfish;
		}
	}
	
	protected float stateTime;
	protected float delayToNextSpawn = 10f;

	
	public void setSpawning(boolean hasShark, boolean hashSword)
	{
		this.hasShark = hasShark;
		this.hasSword = hashSword;
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
				delayToNextSpawn = MathUtils.random(10f, 30f);
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
		setSwimmingActor();

		
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
		float startX = -(swimmingActor.getWidth()* 1.5f);
		float startY = OceanWorld.SCREEN_HEIGHT-(swimmingActor.getHeight()*2);
		
		swimmingActor.setSpeed(100f);
		swimmingActor.setFlipped(true);
		swimmingActor.setPosition(startX, startY);
		swimmingActor.setRotation(0f);

		
	}
	
	private void path2()
	{
		float startX = OceanWorld.SCREEN_WIDTH;
		float startY = OceanWorld.SCREEN_HEIGHT-(swimmingActor.getHeight()*2.5f);
		
		swimmingActor.setSpeed(100f);
		swimmingActor.setPosition(startX, startY);
		swimmingActor.setFlipped(false);
		swimmingActor.setRotation(0);

	}

}
