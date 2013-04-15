package com.oceanworld.actorspawner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.oceanworld.OceanWorld;
import com.oceanworld.actors.AnimatedSwimmingActor;
import com.oceanworld.actors.AnimatedSwimmingActor.DoneSwimmingListener;
import com.oceanworld.actors.SmallGroupFish2;
import com.oceanworld.actors.SwimmingActor;

public class CombinationTwo extends Spawner
{
	private AnimatedSwimmingActor jellyfish;
	private AnimatedSwimmingActor mermaid;
	private AnimatedSwimmingActor turtle;
	private AnimatedSwimmingActor fish2;
	
	private SmallGroupFish2 smallFish2;
	
	private DoneSwimmingListener listener;
	
	private TextureRegion smallfishRegion;
	
	private int iterMan = 0;
	private int iterTurtle = 0;
	private int iterJellyfish = 0;
	
	private float stateTime = 0;
	private boolean startTurtle = false;
	private boolean startMan 	= false;
	private boolean startManIter3 = false;
	private float manIter3StateTime = 0;
	
	private float turtleStateTime = 0;
	private float manStateTime    = 0;
	private boolean startJellyfish = false;
	private float jellyfishStateTime = 0;
	private boolean startFish = false;
	
	private boolean startResetAll = false;
	private float resetStateTime = 0;
	
	public CombinationTwo()
	{
		listener = new DoneSwimmingImpl();
	}
	
	public void setAndRequestAssetManager(AssetManager manager)
	{
		super.setAndRequestAssetManager(manager);
		manager.load(SwimmingActor.mermaidLocation, TextureAtlas.class);
		manager.load(SwimmingActor.turtleLocation,TextureAtlas.class);
		manager.load(SwimmingActor.fish2, TextureAtlas.class);
		manager.load(SwimmingActor.jellyfishLocation, TextureAtlas.class);
		manager.load(SwimmingActor.otherLocation,TextureAtlas.class);
	}
	
	public void init()
	{
		Array<AtlasRegion> manRegions = assetManager.get(SwimmingActor.mermaidLocation,TextureAtlas.class).findRegions(SwimmingActor.mermaidRegionName);
		mermaid = new AnimatedSwimmingActor(manRegions);
		
		Array<AtlasRegion> turtleRegions	= assetManager.get(SwimmingActor.turtleLocation, TextureAtlas.class).findRegions(SwimmingActor.turtleName);
		turtle = new AnimatedSwimmingActor(turtleRegions);
		
		Array<AtlasRegion> jellyRegions	= assetManager.get(SwimmingActor.jellyfishLocation, TextureAtlas.class).findRegions(SwimmingActor.jellyfishName);
		jellyfish = new AnimatedSwimmingActor(jellyRegions);
		
		Array<AtlasRegion> fish2Regions =  assetManager.get(SwimmingActor.fish2, TextureAtlas.class).findRegions(SwimmingActor.fish2Name);
		
		fish2 =  new AnimatedSwimmingActor(fish2Regions);
		
		smallfishRegion	= assetManager.get(SwimmingActor.otherLocation,TextureAtlas.class).findRegion("small-fish-1");
		
		smallFish2 = new SmallGroupFish2(smallfishRegion);
		
		resetMermaid();
		nearGroup.addActor(mermaid);
		mermaid.setSwimmingListener(listener);
		turtle.setSwimmingListener(listener);
		jellyfish.setSwimmingListener(listener);
	}
	
	public void resetMermaid()
	{
		int randomizer =  MathUtils.random(1, 2);
		
		mermaid.setSize((mermaid.getRegion().getRegionWidth()*2f) * OceanWorld.IMAGES_RATIO,( mermaid.getRegion().getRegionHeight()*2f) * OceanWorld.IMAGES_RATIO);

		switch(randomizer)
		{
			case 1:
			{
				mermaidpath1();
				break;
			}
			case 2:
			{
				mermaidpath2();
				break;
			}
		}
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
				resetMermaid();
				if(iterMan == 1)
				{
					farGroup.addActor(mermaid);
					mermaid.setSpeed(mermaid.getSpeed()*0.4f);
					mermaid.setSize(mermaid.getWidth() * 0.5f, mermaid.getHeight() * 0.5f);
					mermaid.setDelayForCleaningCheck(10f);
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
		
		if(startJellyfish)
		{
			jellyfishStateTime += delta;
			if(!startFish && jellyfishStateTime >= 5f)
			{
				jellyfishStateTime = 0;
				resetFish();
				startFish = true;
				if(MathUtils.randomBoolean())
				{
					nearGroup.addActor(fish2);
				}
				else
				{
					farGroup.addActor(fish2);
					fish2.setSpeed(fish2.getSpeed()/2f);
					fish2.setSize(fish2.getWidth() * 0.5f, fish2.getHeight() * 0.5f);
				}
			}
		}
		
		if(startManIter3)
		{
			this.manIter3StateTime += delta;
			
			if(this.manIter3StateTime >= 7f)
			{
				startManIter3 = false;
				this.resetJellyFish();
				resetTurtle();
				nearGroup.addActor(turtle);
				
				farGroup.addActor(jellyfish);
				
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
		resetMermaid();
		nearGroup.addActor(mermaid);
		resetTurtle();
		turtle.setSpeed(turtle.getSpeed()/2f);
		turtle.setSize(turtle.getWidth() * 0.5f, turtle.getHeight() * 0.5f);
		farGroup.addActor(turtle);
		resetFish();
		nearGroup.addActor(fish2);
		this.resetJellyFish();
		farGroup.addActor(jellyfish);
		this.smallFish2.resetSmallFish();
		farGroup.addActor(this.smallFish2);

	}
	
	public void resetAll()
	{
		iterMan = 0;
		iterTurtle = 0;
		iterJellyfish = 0;
		
		stateTime = 0;
		startTurtle = false;
		startMan 	= false;
		startManIter3 = false;
		manIter3StateTime = 0;
		
		turtleStateTime = 0;
		manStateTime    = 0;
		startJellyfish  = false;
		jellyfishStateTime = 0;
		startFish = false;
		
		startResetAll = false;
		resetStateTime = 0;
		
		resetMermaid();
		nearGroup.addActor(mermaid);
	}
	
	public void resetJellyFish()
	{
		float startX = MathUtils.random(0, OceanWorld.SCREEN_WIDTH - jellyfish.getWidth());
		float startY = -jellyfish.getHeight();
		
		jellyfish.setSpeed(MathUtils.random(10f,20f));
		jellyfish.setPosition(startX, startY);
		jellyfish.setRotation(90);

	}
	
	private void mermaidpath1()
	{
		float startX = -mermaid.getWidth();
		float startY = -mermaid.getHeight();
		
		mermaid.setSpeed(50f);
		mermaid.setPosition(startX,startY);
		mermaid.setFlipped(true);
		mermaid.setRotation(50f);

		
	}
	
	private void mermaidpath2()
	{
		float startX = OceanWorld.SCREEN_WIDTH;
		float startY = -mermaid.getHeight();
		
		mermaid.setSpeed(50f);
		mermaid.setPosition(startX, startY);
		mermaid.setFlipped(false);
		mermaid.setRotation(135f);

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
		
				if(actor.equals(mermaid))
				{
					if(iterMan == 0)
					{

						resetTurtle();
						nearGroup.addActor(turtle);
						startTurtle = true;
					}
					else if(iterMan == 1)
					{
			
						resetJellyFish();
						startJellyfish = true;
						nearGroup.addActor(jellyfish);
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
						resetMermaid();
						nearGroup.addActor(mermaid);
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
				else if(actor.equals(jellyfish))
				{
					if(iterJellyfish == 0)
					{

						startJellyfish = false;
						resetTurtle();
						farGroup.addActor(turtle);
						turtle.setSize(turtle.getWidth() * 0.5f,turtle.getHeight() * 0.5f);
						turtle.setSpeed(turtle.getSpeed()/2f);
						turtle.setDelayForCleaningCheck(15f);
					}
					iterJellyfish++;
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
		float startX = -fish2.getWidth();
		float startY = OceanWorld.SCREEN_HEIGHT* 0.5f;
		
		fish2.setSpeed(MathUtils.random(50f, 100f));
		fish2.setFlipped(true);
		fish2.setPosition(startX, startY);
		fish2.setRotation(0f);

		
	}
	
	private void fishpath2()
	{
		float startX = OceanWorld.SCREEN_WIDTH;
		float startY = OceanWorld.SCREEN_HEIGHT * 0.75f;
		
		fish2.setSpeed(MathUtils.random(50f, 100f));
		fish2.setPosition(startX, startY);
		fish2.setFlipped(false);
		fish2.setRotation(200f);

	}
}
