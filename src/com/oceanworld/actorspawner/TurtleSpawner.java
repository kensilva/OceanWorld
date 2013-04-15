package com.oceanworld.actorspawner;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.oceanworld.OceanWorld;
import com.oceanworld.actors.AnimatedSwimmingActor;


public class TurtleSpawner extends Spawner
{
	protected float stateTime;
	protected float delayToNextSpawn = 10f;
	
	private AnimatedSwimmingActor turtle;
	
	public TurtleSpawner(Array<? extends TextureRegion> turtleRegions)
	{
		turtle = new AnimatedSwimmingActor(turtleRegions);
		stateTime = MathUtils.random(0,10f);
	}
	
	@Override
	public void update(float delta) 
	{
		stateTime += delta;
		if(stateTime >= delayToNextSpawn)
		{
	
			if(turtle.getStage() == null)
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
					resetTurtle();
					nearGroup.addActor(turtle);
				}
			}
			
		}
		
	}
	
	public void resetTurtle()
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
		float startY = -turtle.getHeight();
		
		turtle.setSpeed(10f);
		turtle.setPosition(startX, startY);
		turtle.setRotation(100f);
		turtle.setFlipped(false);
		
	}
	
	private void path2()
	{
		float startX = OceanWorld.SCREEN_WIDTH;
		float startY = OceanWorld.SCREEN_HEIGHT * 0.75f;
		
		turtle.setSpeed(10f);
		turtle.setPosition(startX, startY);
		turtle.setFlipped(false);
		turtle.setRotation(215f);

	}

}
