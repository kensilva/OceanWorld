package com.oceanworld.actorspawner;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.oceanworld.OceanWorld;
import com.oceanworld.action.BeizerAction;
import com.oceanworld.actors.SmallFish;
import com.oceanworld.actors.SwimmingActor;

public class SmallFishSpawner extends Spawner
{
	protected float delayToNextSpawn;
	protected float stateTime;
	
	private Group group;
	
	private TextureRegion fish1Region;
	private TextureRegion fish2Region;
	private TextureRegion fish3Region;
	
	public SmallFishSpawner(TextureRegion fish1, TextureRegion fish2, TextureRegion fish3)
	{
		
		group =  new Group();
		this.fish1Region = fish1;
		this.fish2Region = fish2;
		this.fish3Region = fish3;
		
		group.setSize(OceanWorld.SCREEN_WIDTH, OceanWorld.SCREEN_HEIGHT);
		delayToNextSpawn = MathUtils.random(10f, 15f);
		stateTime = MathUtils.random(0, 15f);
	}
	
	@Override
	public void update(float delta) 
	{
		stateTime+= delta;
		if(stateTime >= delayToNextSpawn)
		{

			if(group.getStage() == null)
			{
				stateTime =  stateTime - delayToNextSpawn;
				delayToNextSpawn = MathUtils.random(10f, 15f);
				
				if(markToRemove)
				{
					this.stateTime = 0;
					spawnManager.unregisterSpawner(this);
				}
				else
				{
					resetSmallFish();
					nearGroup.addActor(group);
				}
			}
			
		}
	}

	
	private final Vector2 p = new Vector2();
	public void resetSmallFish()
	{
		int numSmallFish = MathUtils.random(3, 5);
		
		float yOffset = MathUtils.random(OceanWorld.SCREEN_HEIGHT*0.1f,OceanWorld.SCREEN_HEIGHT * 0.8f);
		
		p.set(OceanWorld.SCREEN_WIDTH, yOffset);
		
		float delay = 0f;
		int type =  MathUtils.random(1, 3);
		for(int i = 0; i < numSmallFish; ++i)
		{
			SwimmingActor smallFish1 =  obtainFishOfType(type);
			float scale = MathUtils.random(0.4f, 0.6f);
			smallFish1.setSize(smallFish1.getWidth() * scale, smallFish1.getHeight() * scale);
			group.addActor(smallFish1);
			BeizerAction beizer = BeizerAction.obtainBeizer(p.x, MathUtils.random(p.y -50f, p.y+50f), MathUtils.random(p.x-50f, p.x+50f),MathUtils.random(p.y -50f, p.y + 50f),p.x - 100f,MathUtils.random(p.y-50f, p.y+50f),MathUtils.random(p.x - 200f, p.x - 250f),MathUtils.random(p.y-50f, p.y+50f), 4f);
			p.set(p.x, MathUtils.randomBoolean()?p.y - MathUtils.random(10f, 15f):p.y + MathUtils.random(10f, 15f));
			smallFish1.addAction(Actions.sequence(Actions.moveTo(-smallFish1.getWidth(), -smallFish1.getHeight()),Actions.delay(delay),beizer,Actions.moveTo(-smallFish1.getWidth(), MathUtils.random(p.y-15f,p.y +15f), 1.5f),Actions.removeActor()));
			delay+= MathUtils.random(0.5f, 1f);
		}
		
		group.addAction(Actions.delay(10f,Actions.removeActor()));
	}

	private SwimmingActor obtainFishOfType(int fishType)
	{
		switch(fishType)
		{
			case 3:
			{
				return SmallFish.obtainSmallFish(fish3Region);
			}
			case 2:
			{
				return SmallFish.obtainSmallFish(fish2Region);
			}
			default:
			{
				return SmallFish.obtainSmallFish(fish1Region);
			}
		}

	}
}
