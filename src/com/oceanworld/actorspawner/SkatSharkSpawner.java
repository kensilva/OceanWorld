package com.oceanworld.actorspawner;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Array;
import com.oceanworld.OceanWorld;
import com.oceanworld.actors.AnimatedSwimmingActor;


public class SkatSharkSpawner extends Spawner
{

	private AnimatedSwimmingActor shark;
	private AnimatedSwimmingActor skat1;
	private AnimatedSwimmingActor skat2;
	private AnimatedSwimmingActor skat3;
	
	private Group group;
	
	protected float stateTime;
	protected float delayToNextSpawn = 100f;
	
	
	private SpeedChanger speedChanger1,speedChanger2,speedChanger3,speedChanger4;
	
	public SkatSharkSpawner(Array<? extends TextureRegion> skatRegions, Array<? extends TextureRegion> sharkRegions)
	{
		group = new Group();
		shark = new AnimatedSwimmingActor(sharkRegions);
		skat1 = new AnimatedSwimmingActor(skatRegions);
		skat2 = new AnimatedSwimmingActor(skatRegions);
		skat3 = new AnimatedSwimmingActor(skatRegions);
		shark.setAutoRemove(false);
		skat1.setAutoRemove(false);
		skat2.setAutoRemove(false);
		skat3.setAutoRemove(false);
		
		group.addActor(shark);
		group.addActor(skat1);
		group.addActor(skat2);
		group.addActor(skat3);
		float scale = 0.3f;
		
		skat1.setSize(skat1.getWidth() * scale, skat1.getHeight() * scale);
		skat2.setSize(skat2.getWidth() * scale, skat2.getHeight() * scale);
		skat3.setSize(skat3.getWidth() * scale, skat3.getHeight() * scale);
		shark.setSize(shark.getWidth() * scale, shark.getHeight() * scale);
	
		speedChanger1 = new SpeedChanger();
		speedChanger2 = new SpeedChanger();
		speedChanger3 = new SpeedChanger();
		speedChanger4 = new SpeedChanger();
		stateTime =  MathUtils.random(0, 100f);
	}
	
	@Override
	public void update(float delta) {
		stateTime+= delta;
		if(stateTime >= delayToNextSpawn)
		{
			
			if(group.getStage() == null)
			{
				stateTime =  stateTime - delayToNextSpawn;
				delayToNextSpawn = MathUtils.random(100f, 200f);
				
				if(markToRemove)
				{
					this.stateTime = 0;
					spawnManager.unregisterSpawner(this);
				}
				else
				{
					resetSkatShark();
					farGroup.addActor(group);
				}
			}
			
		}
		
	}
	

	
	public void resetSkatShark()
	{
		float startY = OceanWorld.SCREEN_HEIGHT * 0.30f;
		float startX = OceanWorld.SCREEN_WIDTH + (OceanWorld.SCREEN_WIDTH * 0.10f);
		
		skat1.setPosition(startX, startY);
		skat2.setPosition(startX, startY + skat1.getHeight()* 0.25f);
		skat3.setPosition(startX, startY - skat1.getHeight()* 0.25f);
		shark.setPosition(startX, startY);
		
		float speed = 10f;
		skat1.setSpeed(0);
		skat2.setSpeed(0);
		skat3.setSpeed(0);
		shark.setSpeed(0);
		skat1.stepStateTime(MathUtils.random(0f, 50f));
		skat2.stepStateTime(MathUtils.random(0f, 50f));
		skat3.stepStateTime(MathUtils.random(0f, 50f));
		
		skat1.addAction(Actions.delay(1f, Actions.run(speedChanger1.set(skat1, speed))));
		skat2.addAction(Actions.delay(3f, Actions.run(speedChanger2.set(skat2, speed))));
		skat3.addAction(Actions.delay(5f, Actions.run(speedChanger3.set(skat3, speed))));
		shark.addAction(Actions.delay(0f, Actions.run(speedChanger4.set(shark, speed+(speed * 0.2f)))));
		
		float rotation = 160f;
		skat1.setRotation(rotation);
		skat2.setRotation(rotation);
		skat3.setRotation(rotation);
		shark.setRotation(rotation);
		
		group.addAction(Actions.delay(100f,Actions.removeActor()));
		
	}
	
	private static class SpeedChanger implements Runnable
	{
		private AnimatedSwimmingActor actor;
		private float speed;
		
		public SpeedChanger set(AnimatedSwimmingActor actor, float speed)
		{
			this.actor = actor;
			this.speed  = speed;
			return this;
		}
		
		public void run()
		{
			actor.setSpeed(speed);
		}
	}
}
