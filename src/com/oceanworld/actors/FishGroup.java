package com.oceanworld.actors;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.oceanworld.OceanWorld;

public class FishGroup extends Group
{

	private AnimatedSwimmingActor swimmingActor1;
	private AnimatedSwimmingActor swimmingActor2;
	private AnimatedSwimmingActor swimmingActor3;
	private AnimatedSwimmingActor swimmingActor4;
	
	protected float stateTime;
	protected float delayToNextSpawn = 100f;
	
	protected GroupRemoveListener listener;
	

	private boolean oneSpecie = false;

	private SpeedChanger speedChanger1,speedChanger2,speedChanger3,speedChanger4;
	
	public FishGroup(Array<? extends TextureRegion> regions1,Array<? extends TextureRegion> regions2)
	{
		
		if(regions1 == null && regions2 == null)
		{
			throw new GdxRuntimeException("This' cannot be. Must have atleast one arrayOfRegions");
		}
		
		swimmingActor1 = new AnimatedSwimmingActor(0.1f,regions1);
		swimmingActor2 = new AnimatedSwimmingActor(0.1f,regions1);
		swimmingActor3 = new AnimatedSwimmingActor(0.1f,regions1);
		if(regions1 != null && regions2 == null)
		{
			oneSpecie = true;
			swimmingActor4 = new AnimatedSwimmingActor(0.1f,regions1);
		}
		else
		{
			swimmingActor4 = new AnimatedSwimmingActor(regions2);
		}
		
		swimmingActor1.setAutoRemove(false);
		swimmingActor2.setAutoRemove(false);
		swimmingActor3.setAutoRemove(false);
		swimmingActor4.setAutoRemove(false);
		
		addActor(swimmingActor1);
		addActor(swimmingActor2);
		addActor(swimmingActor3);
		addActor(swimmingActor4);
		
		speedChanger1 = new SpeedChanger();
		speedChanger2 = new SpeedChanger();
		speedChanger3 = new SpeedChanger();
		speedChanger4 = new SpeedChanger();
		
	}

	public GroupRemoveListener getListener() {
		return listener;
	}

	public void setListener(GroupRemoveListener listener) {
		this.listener = listener;
	}
	
	protected void fireRemoved()
	{
		if(listener != null)
		{
			listener.removed(this);
		}
	}
	
	public void resetSwimmers()
	{
		float startY = OceanWorld.SCREEN_HEIGHT * 0.50f;
		float startX = OceanWorld.SCREEN_WIDTH + (OceanWorld.SCREEN_WIDTH * 0.10f);
		
		float scale = 0.4f;
		swimmingActor1.setSize(swimmingActor1.getRegion().getRegionWidth() * 2 * OceanWorld.IMAGES_RATIO, swimmingActor1.getRegion().getRegionHeight() * 2 * OceanWorld.IMAGES_RATIO);
		swimmingActor1.setSize(swimmingActor1.getWidth() * scale, swimmingActor1.getHeight() * scale );
		
		swimmingActor2.setSize(swimmingActor2.getRegion().getRegionWidth() * 2 * OceanWorld.IMAGES_RATIO, swimmingActor2.getRegion().getRegionHeight() * 2 * OceanWorld.IMAGES_RATIO);
		swimmingActor2.setSize(swimmingActor2.getWidth() * scale, swimmingActor2.getHeight() * scale );
		
		swimmingActor3.setSize(swimmingActor3.getRegion().getRegionWidth() * 2 * OceanWorld.IMAGES_RATIO, swimmingActor3.getRegion().getRegionHeight() * 2 * OceanWorld.IMAGES_RATIO);
		swimmingActor3.setSize(swimmingActor3.getWidth() * scale, swimmingActor3.getHeight() * scale );
		
		swimmingActor4.setSize(swimmingActor4.getRegion().getRegionWidth() * 2 * OceanWorld.IMAGES_RATIO, swimmingActor4.getRegion().getRegionHeight() * 2 * OceanWorld.IMAGES_RATIO);
		swimmingActor4.setSize(swimmingActor4.getWidth() * scale, swimmingActor4.getHeight() * scale );
		
		swimmingActor1.setPosition(startX, startY);
		swimmingActor2.setPosition(startX, startY + swimmingActor1.getHeight()* 0.25f);
		swimmingActor3.setPosition(startX, startY - swimmingActor1.getHeight()* 0.25f);
		swimmingActor4.setPosition(startX, startY - swimmingActor1.getHeight());
		

		
		float speed = 15f;
		swimmingActor1.setSpeed(0);
		swimmingActor2.setSpeed(0);
		swimmingActor3.setSpeed(0);
		swimmingActor4.setSpeed(0);
		swimmingActor1.stepStateTime(MathUtils.random(0f,5f));
		swimmingActor2.stepStateTime(MathUtils.random(0f,5f));
		swimmingActor3.stepStateTime(MathUtils.random(0f,5f));
		swimmingActor1.setDelayForCleaningCheck(20f);
		swimmingActor2.setDelayForCleaningCheck(20f);
		swimmingActor3.setDelayForCleaningCheck(20f);
		swimmingActor4.setDelayForCleaningCheck(20f);
		
		swimmingActor1.addAction(Actions.delay(3f, Actions.run(speedChanger1.set(swimmingActor1, speed))));
		swimmingActor2.addAction(Actions.delay(5f, Actions.run(speedChanger2.set(swimmingActor2, speed))));
		swimmingActor3.addAction(Actions.delay(1.5f, Actions.run(speedChanger3.set(swimmingActor3, speed))));
		if(!oneSpecie)
		{
			swimmingActor4.addAction(Actions.delay(0f, Actions.run(speedChanger4.set(swimmingActor4, speed - (speed*0.4f)))));
		}
		else
		{
			swimmingActor4.addAction(Actions.delay(0f, Actions.run(speedChanger4.set(swimmingActor4, speed))));
		}
		float rotation = 160f;
		swimmingActor1.setRotation(rotation);
		swimmingActor2.setRotation(rotation);
		swimmingActor3.setRotation(rotation);
		swimmingActor4.setRotation(rotation);
		
		addAction(Actions.delay(100f,Actions.run(new Runnable(){public void run(){fireRemoved();}})));
		
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
	
	public static interface GroupRemoveListener
	{
		public void removed(FishGroup group);
	}
}
