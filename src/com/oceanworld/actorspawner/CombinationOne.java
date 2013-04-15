package com.oceanworld.actorspawner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Array;
import com.oceanworld.OceanWorld;
import com.oceanworld.actors.AnimatedSwimmingActor;
import com.oceanworld.actors.AnimatedSwimmingActor.DoneSwimmingListener;
import com.oceanworld.actors.Dolphin;
import com.oceanworld.actors.SmallGroupFish2;
import com.oceanworld.actors.SwimmingActor;

public class CombinationOne extends Spawner
{
	private Dolphin dolphin;
	private AnimatedSwimmingActor man;
	private AnimatedSwimmingActor turtle;
	private AnimatedSwimmingActor fish1;
	
	private SmallGroupFish2 smallFish2;
	
	private DoneSwimmingListener listener;
	
	private TextureRegion smallfishRegion;
	
	private int iterMan = 0;
	private int iterTurtle = 0;
	
	private float stateTime = 0;
	private boolean startTurtle = false;
	private boolean startMan 	= false;
	private boolean startManIter3 = false;
	private float manIter3StateTime = 0;
	
	private float turtleStateTime = 0;
	private float manStateTime    = 0;
	private boolean startDolphin  = false;
	private float dolphinStateTime = 0;
	private float dolphinEndTime  = 0;
	private boolean startFish = false;
	
	private boolean startResetAll = false;
	private float resetStateTime = 0;
	
	public CombinationOne()
	{
		listener = new DoneSwimmingImpl();
	}
	
	public void setAndRequestAssetManager(AssetManager manager)
	{
		super.setAndRequestAssetManager(manager);
		manager.load(SwimmingActor.manLocation, TextureAtlas.class);
		manager.load(SwimmingActor.turtleLocation,TextureAtlas.class);
		manager.load(SwimmingActor.fish1Location, TextureAtlas.class);
		manager.load(SwimmingActor.otherLocation,TextureAtlas.class);
	}
	
	public void init()
	{
		Array<AtlasRegion> manRegions = assetManager.get(SwimmingActor.manLocation,TextureAtlas.class).findRegions(SwimmingActor.manName);
		man = new AnimatedSwimmingActor(manRegions);
		
		Array<AtlasRegion> turtleRegions	= assetManager.get(SwimmingActor.turtleLocation, TextureAtlas.class).findRegions(SwimmingActor.turtleName);
		turtle = new AnimatedSwimmingActor(turtleRegions);
		
		TextureRegion dolphinRegion = assetManager.get(SwimmingActor.otherLocation, TextureAtlas.class).findRegion("dolphin");
		dolphin = new Dolphin(dolphinRegion);
		
		Array<AtlasRegion> fish1Regions =  assetManager.get(SwimmingActor.fish1Location, TextureAtlas.class).findRegions(SwimmingActor.fish1Name);
		
		fish1 =  new AnimatedSwimmingActor(fish1Regions);
		
		smallfishRegion	= assetManager.get(SwimmingActor.otherLocation,TextureAtlas.class).findRegion("small-fish-1");
		
		smallFish2 = new SmallGroupFish2(smallfishRegion);
		
		resetMan();
		nearGroup.addActor(man);
		man.setSwimmingListener(listener);
		turtle.setSwimmingListener(listener);
	}
	
	public void resetMan()
	{
		int randomizer =  MathUtils.random(1, 2);
		
		man.setSize((man.getRegion().getRegionWidth()*2f) * OceanWorld.IMAGES_RATIO,( man.getRegion().getRegionHeight()*2f) * OceanWorld.IMAGES_RATIO);

		switch(randomizer)
		{
			case 1:
			{
				manpath1();
				break;
			}
			case 2:
			{
				manpath2();
				break;
			}
		}
	}
	
	private void manpath1()
	{
		float startX = -man.getWidth();
		float startY = -man.getHeight();
		
		man.setSpeed(50f);
		man.setPosition(startX,startY);
		man.setFlipped(true);
		man.setRotation(50f);

		
	}
	
	private void manpath2()
	{
		float startX = OceanWorld.SCREEN_WIDTH;
		float startY = -man.getHeight();
		
		man.setSpeed(50f);
		man.setPosition(startX, startY);
		man.setFlipped(false);
		man.setRotation(135f);

	}
	
	@Override
	public void update(float delta) 
	{
		if(this.startTurtle)
		{
			this.turtleStateTime+= delta;
			if(turtleStateTime >= 10f)
			{
				startTurtle = false;
				turtleStateTime = 0;
				resetMan();
				if(iterMan == 1)
				{
					farGroup.addActor(man);
					man.setSpeed(man.getSpeed()*0.4f);
					man.setSize(man.getWidth() * 0.5f, man.getHeight() * 0.5f);
					man.setDelayForCleaningCheck(10f);
					startMan = true;
				}
			}
		}
		
		if(startMan)
		{
			manStateTime += delta;
			if(manStateTime >= 15f)
			{
				this.smallFish2.resetSmallFish();
				farGroup.addActor(smallFish2);
				startMan = false;
				manStateTime = 0;
			}
		}
		
		if(startDolphin)
		{
			dolphinStateTime += delta;
			if(dolphinStateTime >= dolphinEndTime)
			{
				dolphinStateTime = 0;
				startDolphin = false;
				resetTurtle();
				farGroup.addActor(turtle);
				turtle.setSize(turtle.getWidth() * 0.5f,turtle.getHeight() * 0.5f);
				turtle.setSpeed(turtle.getSpeed()/2f);
				turtle.setDelayForCleaningCheck(15f);
			}
			if(!startFish && dolphinStateTime >= 5f)
			{
				resetFish();
				startFish = true;
				if(MathUtils.randomBoolean())
				{
					nearGroup.addActor(fish1);
				}
				else
				{
					farGroup.addActor(fish1);
					fish1.setSpeed(fish1.getSpeed()/2f);
					fish1.setSize(fish1.getWidth() * 0.5f, fish1.getHeight() * 0.5f);
				}
			}
		}
		
		if(startManIter3)
		{
			this.manIter3StateTime += delta;
			
			if(this.manIter3StateTime >= 7f)
			{
				startManIter3 = false;
				resetDolphin(true);
				resetTurtle();
				nearGroup.addActor(turtle);
				
				farGroup.addActor(dolphin);
				
			}
		}
		
		if(startResetAll)
		{
			resetStateTime += delta;
			
			if(resetStateTime >= 16f)
			{
				startResetAll = false;
				randomizeAll();
			}
		}
	}

	public void randomizeAll()
	{
		Gdx.app.log("Should spawn all", "");
		resetMan();
		nearGroup.addActor(man);
		resetTurtle();
		turtle.setSpeed(turtle.getSpeed()/2f);
		turtle.setSize(turtle.getWidth() * 0.5f, turtle.getHeight() * 0.5f);
		farGroup.addActor(turtle);
		resetFish();
		nearGroup.addActor(fish1);
		this.resetDolphin(true);
		farGroup.addActor(dolphin);
		this.smallFish2.resetSmallFish();
		farGroup.addActor(this.smallFish2);

	}
	
	public void resetAll()
	{
		iterMan = 0;
		iterTurtle = 0;
		
		stateTime = 0;
		startTurtle = false;
		startMan 	= false;
		startManIter3 = false;
		manIter3StateTime = 0;
		
		turtleStateTime = 0;
		manStateTime    = 0;
		startDolphin  = false;
		dolphinStateTime = 0;
		dolphinEndTime  = 0;
		startFish = false;
		
		startResetAll = false;
		resetStateTime = 0;
		
		resetMan();
		nearGroup.addActor(man);
	}
	
	public void resetDolphin(boolean isFar)
	{
		int randomizer = MathUtils.random(1,2);
		
		dolphin.setSize(dolphin.getRegion().getRegionWidth() * OceanWorld.IMAGES_RATIO, dolphin.getRegion().getRegionHeight() * OceanWorld.IMAGES_RATIO);
		switch(randomizer)
		{
			case 1:
			{
				dolphinpath1(isFar);
				break;
			}
			case 2:
			{
				dolphinpath2(isFar);
				break;
			}
		}

	}
	private void dolphinpath1(boolean isFar)
	{
		if(!isFar)
		{
			float startX = OceanWorld.SCREEN_WIDTH - dolphin.getWidth();
			float startY = 100f;
			dolphin.setPosition(startX, startY);

			dolphin.setOrigin(dolphin.getWidth()/2f,-(dolphin.getHeight()*3f));
			dolphin.setRotation(270f);
			dolphin.addAction(Actions.sequence(Actions.rotateBy(360f, 8f),Actions.removeActor()));
			this.dolphinEndTime = 8f;
		}
		else
		{
			float startX = OceanWorld.SCREEN_WIDTH - dolphin.getWidth();
			float startY = 100f;
			dolphin.setPosition(startX, startY);
			dolphin.setSize(dolphin.getWidth() * 0.5f, dolphin.getHeight() * 0.5f);
			dolphin.setOrigin(dolphin.getWidth()/2f,-(dolphin.getHeight()*3f));
			dolphin.setRotation(270f);
			dolphin.addAction(Actions.sequence(Actions.rotateBy(360f, 16f),Actions.removeActor()));
			this.dolphinEndTime = 16f;
		}
	}
	
	private void dolphinpath2(boolean isFar)
	{
		if(!isFar)
		{
			float startX = -(dolphin.getWidth()/2f);
			float startY = OceanWorld.SCREEN_HEIGHT + dolphin.getHeight();
			dolphin.setPosition(startX, startY);

			dolphin.setOrigin(dolphin.getWidth()/2f,-(dolphin.getHeight()*2f));
			dolphin.setRotation(0f);
			dolphin.addAction(Actions.sequence(Actions.rotateBy(360f, 8f),Actions.removeActor()));
			this.dolphinEndTime = 8f;
		}
		else
		{
			float startX = -(dolphin.getWidth()/2f);
			float startY = OceanWorld.SCREEN_HEIGHT + dolphin.getHeight();
			dolphin.setPosition(startX, startY);
			dolphin.setSize(dolphin.getWidth() * 0.5f, dolphin.getHeight() * 0.5f);
			dolphin.setOrigin(dolphin.getWidth()/2f,-(dolphin.getHeight()*2f));
			dolphin.setRotation(0f);
			dolphin.addAction(Actions.sequence(Actions.rotateBy(360f, 16f),Actions.removeActor()));
			this.dolphinEndTime = 16f;
		}
	}
	

	public void resetTurtle()
	{
		int randomizer = MathUtils.random(1, 2);
		
		turtle.setSize(turtle.getRegion().getRegionWidth()*2 * OceanWorld.IMAGES_RATIO, turtle.getRegion().getRegionHeight() * 2 * OceanWorld.IMAGES_RATIO);
		switch(randomizer)
		{
			case 1:
			{
				turtlepath1();
				break;
			}
			case 2:
			{
				turtlepath2();
				break;
			}
		}
		
	}
	
	private void turtlepath1()
	{
		float startX = OceanWorld.SCREEN_WIDTH * 0.5f;
		float startY = -turtle.getHeight();
		
		turtle.setSpeed(30f);
		turtle.setPosition(startX, startY);
		turtle.setRotation(100f);
		turtle.setFlipped(false);
		
	}
	
	private void turtlepath2()
	{
		float startX = OceanWorld.SCREEN_WIDTH;
		float startY = OceanWorld.SCREEN_HEIGHT * 0.75f;
		
		turtle.setSpeed(30f);
		turtle.setPosition(startX, startY);
		turtle.setFlipped(false);
		turtle.setRotation(215f);

	}
	
	private class DoneSwimmingImpl implements DoneSwimmingListener
	{

		@Override
		public void swimmingDone(AnimatedSwimmingActor actor) 
		{
		
				if(actor.equals(man))
				{
					if(iterMan == 0)
					{

						resetTurtle();
						nearGroup.addActor(turtle);
						startTurtle = true;
					}
					else if(iterMan == 1)
					{
			
						resetDolphin(false);
						startDolphin = true;
						nearGroup.addActor(dolphin);
					}
					else if(iterMan == 2)
					{
						Gdx.app.log("should generate Small Fish","");
						smallFish2.resetSmallFish();
						farGroup.addActor(smallFish2);
					}
					Gdx.app.log("Man Iter", iterMan+"");
					iterMan++;
				}
				else if(actor.equals(turtle))
				{
					if(iterTurtle == 0)
					{
						
					}
					else if(iterTurtle == 1)
					{
						resetMan();
						nearGroup.addActor(man);
						Gdx.app.log("reset3","called");
						startManIter3 = true;
						
					}
					else if(iterTurtle == 2)
					{
						smallFish2.resetSmallFish();
						farGroup.addActor(smallFish2);
						startResetAll = true;
					}
					else if(iterTurtle == 3)
					{
						resetAll();
					}
					iterTurtle++;
				}
		}
		
	}
	
	public void resetFish()
	{
		
		int randomizer = MathUtils.random(1, 2);
		
		switch(randomizer)
		{
			case 1:
			{
				fishpath1();
				break;
			}
			case 2:
			{
				fishpath2();
				break;
			}
		
		}
	}
	
	private void fishpath1()
	{
		float startX = -fish1.getWidth();
		float startY = OceanWorld.SCREEN_HEIGHT* 0.5f;
		
		fish1.setSpeed(MathUtils.random(50f, 100f));
		fish1.setFlipped(true);
		fish1.setPosition(startX, startY);
		fish1.setRotation(0f);

		
	}
	
	private void fishpath2()
	{
		float startX = OceanWorld.SCREEN_WIDTH;
		float startY = OceanWorld.SCREEN_HEIGHT * 0.75f;
		
		fish1.setSpeed(MathUtils.random(50f, 100f));
		fish1.setPosition(startX, startY);
		fish1.setFlipped(false);
		fish1.setRotation(200f);

	}
}
