package com.oceanworld.actorspawner;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.oceanworld.OceanWorld;
import com.oceanworld.actors.Dolphin;

public class DolphinSpawner extends Spawner
{

	protected float delayToNextSpawn = 15f;
	protected float stateTime;
	
	private Dolphin dolphin;
	
	public DolphinSpawner(TextureRegion region)
	{
		dolphin = new Dolphin(region);
		stateTime = MathUtils.random(0, 15f);
	}
	
	@Override
	public void update(float delta) 
	{
		stateTime += delta;
		if(stateTime >= delayToNextSpawn)
		{
			stateTime = stateTime - delayToNextSpawn;
			delayToNextSpawn = MathUtils.random(15f, 30f);
			if(dolphin.getStage() == null)
			{
				if(markToRemove)
				{
					this.stateTime = 0;
					spawnManager.unregisterSpawner(this);
				}
				else
				{
					resetDolphin();
					nearGroup.addActor(dolphin);
				}
			}
		}
		
	}

	public void resetDolphin()
	{
		int randomizer = MathUtils.random(1,2);
		
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
		float startX = OceanWorld.SCREEN_WIDTH - dolphin.getWidth();
		float startY = 100f;
		dolphin.setPosition(startX, startY);
		
		dolphin.setOrigin(dolphin.getWidth()/2f,-(dolphin.getHeight()*3f));
		dolphin.setRotation(270f);
		dolphin.addAction(Actions.sequence(Actions.rotateBy(360f, 8f),Actions.removeActor()));
	}
	
	private void path2()
	{
		float startX = -(dolphin.getWidth()/2f);
		float startY = OceanWorld.SCREEN_HEIGHT + dolphin.getHeight();
		dolphin.setPosition(startX, startY);
		
		dolphin.setOrigin(dolphin.getWidth()/2f,-(dolphin.getHeight()*2f));
		dolphin.setRotation(0f);
		dolphin.addAction(Actions.sequence(Actions.rotateBy(360f, 8f),Actions.removeActor()));
	}
	
	public void markToRemove()
	{
		
	}
}
