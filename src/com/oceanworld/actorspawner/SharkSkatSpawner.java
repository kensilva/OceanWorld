package com.oceanworld.actorspawner;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Array;
import com.oceanworld.OceanWorld;
import com.oceanworld.actors.AnimatedSwimmingActor;


public class SharkSkatSpawner extends Spawner
{

	private AnimatedSwimmingActor skat;
	private AnimatedSwimmingActor shark1;
	private AnimatedSwimmingActor shark2;
	private AnimatedSwimmingActor shark3;
	
	private Group group;
	
	protected float stateTime;
	protected float delayToNextSpawn = 100f;
	
	
	private SpeedChanger speedChanger1,speedChanger2,speedChanger3,speedChanger4;
	
	public SharkSkatSpawner(Array<? extends TextureRegion> sharkRegions, Array<? extends TextureRegion> skatRegions)
	{
		group = new Group();
		skat = new AnimatedSwimmingActor(skatRegions);
		shark1 = new AnimatedSwimmingActor(sharkRegions);
		shark2 = new AnimatedSwimmingActor(sharkRegions);
		shark3 = new AnimatedSwimmingActor(sharkRegions);
		skat.setAutoRemove(false);
		shark1.setAutoRemove(false);
		shark2.setAutoRemove(false);
		shark3.setAutoRemove(false);
		
		group.addActor(skat);
		group.addActor(shark1);
		group.addActor(shark2);
		group.addActor(shark3);
		float scale = 0.3f;
		
		shark1.setSize(shark1.getWidth() * scale, shark1.getHeight() * scale);
		shark2.setSize(shark2.getWidth() * scale, shark2.getHeight() * scale);
		shark3.setSize(shark3.getWidth() * scale, shark3.getHeight() * scale);
		skat.setSize(skat.getWidth() * scale, skat.getHeight() * scale);
	
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
		float startY = OceanWorld.SCREEN_HEIGHT * 0.50f;
		float startX = OceanWorld.SCREEN_WIDTH + (OceanWorld.SCREEN_WIDTH * 0.10f);
		
		shark1.setPosition(startX, startY);
		shark2.setPosition(startX, startY + shark1.getHeight()* 0.25f);
		shark3.setPosition(startX, startY - shark1.getHeight()* 0.25f);
		skat.setPosition(startX, startY - shark1.getHeight());
		
		float speed = 15f;
		shark1.setSpeed(0);
		shark2.setSpeed(0);
		shark3.setSpeed(0);
		skat.setSpeed(0);
		shark1.stepStateTime(MathUtils.random(0f, 50f));
		shark2.stepStateTime(MathUtils.random(0f, 50f));
		shark3.stepStateTime(MathUtils.random(0f, 50f));
		
		shark1.addAction(Actions.delay(1f, Actions.run(speedChanger1.set(shark1, speed))));
		shark2.addAction(Actions.delay(3f, Actions.run(speedChanger2.set(shark2, speed))));
		shark3.addAction(Actions.delay(5f, Actions.run(speedChanger3.set(shark3, speed))));
		skat.addAction(Actions.delay(0f, Actions.run(speedChanger4.set(skat, speed - (speed*0.4f)))));
		
		float rotation = 160f;
		shark1.setRotation(rotation);
		shark2.setRotation(rotation);
		shark3.setRotation(rotation);
		skat.setRotation(rotation);
		
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
