package com.oceanworld.actorspawner;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.oceanworld.OceanWorld;
import com.oceanworld.actors.AnimatedSwimmingActor;

public class MermaidSpawner extends Spawner
{

	protected float stateTime;
	protected float delayToNextSpawn = 30f;
	
	private AnimatedSwimmingActor mermaid;
	
	public MermaidSpawner(Array<? extends TextureRegion> regions)
	{
		mermaid = new AnimatedSwimmingActor(regions);
		stateTime = MathUtils.random(0, 15f);
	}
	
	@Override
	public void update(float delta) 
	{
	
		stateTime += delta;
		if(stateTime >= delayToNextSpawn)
		{

			if(mermaid.getStage() == null)
			{
				stateTime =  stateTime - delayToNextSpawn;
				delayToNextSpawn = MathUtils.random(15f, 30f);
				if(markToRemove)
				{
					this.stateTime = 0;
					spawnManager.unregisterSpawner(this);
				}
				else
				{
					resetMermaid();
					nearGroup.addActor(mermaid);
				}
			}
			
		}
		
	}

	public void resetMermaid()
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
		float startY = -mermaid.getHeight();
		
		mermaid.setSpeed(50f);
		mermaid.setPosition(startX, startY);
		mermaid.setRotation(100f);

		
	}
	
	private void path2()
	{
		float startX = OceanWorld.SCREEN_WIDTH;
		float startY = OceanWorld.SCREEN_HEIGHT * 0.75f;
		
		mermaid.setSpeed(50f);
		mermaid.setPosition(startX, startY);
		mermaid.setFlipped(false);
		mermaid.setRotation(215f);

	}

}
