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
import com.oceanworld.actors.FishGroup;
import com.oceanworld.actors.SmallGroupFish2;
import com.oceanworld.actors.SwimmingActor;

public class CombinationThree extends Spawner
{
	private AnimatedSwimmingActor man;
	private AnimatedSwimmingActor mermaid;
	private AnimatedSwimmingActor seahorse;
	private FishGroup fishGroup;
	
	private SmallGroupFish2 smallFish;
	
	private int iterMan = 0;
	private int iterMermaid = 0;
	private int iterSeahorse = 0;
	
	private float stateTimeMan = 0;
	private boolean manStarted = false;
	
	private float stateTimeMermaid = 0;
	private boolean mermaidStarted = false;
	
	private float stateTimeSmallfish = 0;
	private boolean smallFishStarted = false;
	
	
	private DoneSwimmingListener listener;
	
	private TextureRegion smallfishRegion;

	public CombinationThree()
	{
		listener = new DoneSwimmingImpl();
	}
	
	public void setAndRequestAssetManager(AssetManager manager)
	{
		super.setAndRequestAssetManager(manager);
		manager.load(SwimmingActor.manLocation, TextureAtlas.class);
		manager.load(SwimmingActor.seahorseLocation,TextureAtlas.class);
		manager.load(SwimmingActor.fish1Location, TextureAtlas.class);
		manager.load(SwimmingActor.mermaidLocation,TextureAtlas.class);
		manager.load(SwimmingActor.otherLocation,TextureAtlas.class);
	}
	
	public void init()
	{
		Array<AtlasRegion> mermaidRegions = assetManager.get(SwimmingActor.mermaidLocation,TextureAtlas.class).findRegions(SwimmingActor.mermaidRegionName);
		mermaid = new AnimatedSwimmingActor(mermaidRegions);
		
		Array<AtlasRegion> seahorseRegions	= assetManager.get(SwimmingActor.seahorseLocation, TextureAtlas.class).findRegions(SwimmingActor.seahorseName);
		seahorse = new AnimatedSwimmingActor(seahorseRegions);
		
		Array<AtlasRegion> manRegions = assetManager.get(SwimmingActor.manLocation, TextureAtlas.class).findRegions(SwimmingActor.manName);
		man = new AnimatedSwimmingActor(manRegions);
		
		Array<AtlasRegion> fish1Regions =  assetManager.get(SwimmingActor.fish1Location, TextureAtlas.class).findRegions(SwimmingActor.fish1Name);
		
		fishGroup =  new FishGroup(fish1Regions,null);
		
		smallfishRegion	= assetManager.get(SwimmingActor.otherLocation,TextureAtlas.class).findRegion("small-fish-1");
		
		smallFish = new SmallGroupFish2(smallfishRegion);
		
		resetMan(false);
		nearGroup.addActor(man);
		man.setSwimmingListener(listener);
		mermaid.setSwimmingListener(listener);
		seahorse.setSwimmingListener(listener);
	}
	
	public void resetMermaid(boolean isFar)
	{
		int randomizer =  MathUtils.random(1, 2);
		
		mermaid.setSize((mermaid.getRegion().getRegionWidth()*2f) * OceanWorld.IMAGES_RATIO,( mermaid.getRegion().getRegionHeight()*2f) * OceanWorld.IMAGES_RATIO);

		if(isFar)
		{
			mermaid.setSize(mermaid.getWidth() * 0.4f, mermaid.getHeight() * 0.4f);
		}
		
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
	
	public void resetMan(boolean isFar)
	{
		int randomizer =  MathUtils.random(1, 2);
		
		
		man.setSize((man.getRegion().getRegionWidth()*2f) * OceanWorld.IMAGES_RATIO,( man.getRegion().getRegionHeight()*2f) * OceanWorld.IMAGES_RATIO);
		
		if(isFar)
		{
			man.setSize(man.getWidth() * 0.4f, man.getHeight() * 0.4f);
		}
		
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
		float startX = -man.getWidth();
		float startY = OceanWorld.SCREEN_HEIGHT;
		
		man.setSpeed(50f);
		man.setPosition(startX, startY);
		man.setFlipped(true);
		man.setRotation(310f);

	}
	
	@Override
	public void update(float delta) 
	{
		
		if(mermaidStarted)
		{
			stateTimeMermaid += delta;
			
			if(iterMermaid == 0)
			{
				if(stateTimeMermaid >= 2f)
				{
					mermaidStarted = false;
					stateTimeMermaid = 0;
					
					resetMan(true);
					man.setSpeed(20f);
					farGroup.addActor(man);
					
					manStarted = true;
				}
			}
			else if(iterMermaid == 1)
			{
				if(stateTimeMermaid >=4f)
				{
					resetMan(false);
					nearGroup.addActor(man);
					stateTimeMermaid = 0;
					mermaidStarted = false;
					manStarted = true;
				}
			}
		}
		
		if(manStarted)
		{
			stateTimeMan += delta;
			if(iterMan == 1)
			{
				if(stateTimeMan > 6f)
				{
					stateTimeMan = 0;
					manStarted = false;
					
					smallFish.resetSmallFish();
					farGroup.addActor(smallFish);
					this.smallFishStarted = true;
				}
			}
			
			else if(iterMan == 2)
			{
				if(stateTimeMan >= 5f)
				{
					fishGroup.resetSwimmers();
					farGroup.addActor(fishGroup);
					manStarted = false;
					stateTimeMan = 0;
					nearGroup.addAction(Actions.delay(6f,Actions.run(new Runnable()
					{
						public void run()
						{
							Gdx.app.log("Seahorsing", "");
							resetSeahorse();
							nearGroup.addActor(seahorse);
							nearGroup.addAction(Actions.delay(6f,Actions.run(new Runnable()
							{
								public void run()
								{
									smallFish.resetSmallFish();
									farGroup.addActor(smallFish);
								}
							})));
							
							
						}
					})));
				
				}
			}
			
		}
		
		if(smallFishStarted)
		{
			this.stateTimeSmallfish+= delta;
			if(this.stateTimeSmallfish >= 3f)
			{
				this.stateTimeSmallfish = 0;
				this.smallFishStarted = false;
				
				this.fishGroup.resetSwimmers();
				farGroup.addActor(fishGroup);
			}
		}
	}
	
	public void resetAll()
	{
		Gdx.app.log("ResetALL", "");
		iterMan = 0;
		iterMermaid = 0;
		iterSeahorse = 0;
		
		stateTimeMan = 0;
		manStarted = false;
		
		stateTimeMermaid = 0;
		mermaidStarted = false;
		
		stateTimeSmallfish = 0;
		smallFishStarted = false;
		boolean isFar = MathUtils.randomBoolean();
		resetMan(isFar);
		if(isFar)
		{
			farGroup.addActor(man);
			man.setSpeed(20f);
		}
		else
		{
			nearGroup.addActor(man);
		}
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
	
	public void resetSeahorse()
	{
		int randomizer = MathUtils.random(1, 2);
		
		seahorse.setSize(seahorse.getRegion().getRegionWidth() * OceanWorld.IMAGES_RATIO, seahorse.getRegion().getRegionHeight() * OceanWorld.IMAGES_RATIO);

		switch(randomizer)
		{
			case 1:
			{
				seahorsepath1();
				break;
			}
			case 2:
			{
				seahorsepath2();
				break;
			}
		}
		
	}
	
	private void seahorsepath1()
	{
		float startX = -seahorse.getWidth() * 2f;
		float startY = OceanWorld.SCREEN_HEIGHT * 0.3f;
		
		seahorse.setSpeed(30f);
		seahorse.setPosition(startX, startY);
		seahorse.setRotation(40f);
		seahorse.setFlipped(true);
		
	}
	
	private void seahorsepath2()
	{
		float startX = OceanWorld.SCREEN_WIDTH;
		float startY = OceanWorld.SCREEN_HEIGHT * 0.75f;
		
		seahorse.setSpeed(30f);
		seahorse.setPosition(startX, startY);
		seahorse.setFlipped(false);
		seahorse.setRotation(215f);

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
					resetMermaid(false);
					nearGroup.addActor(mermaid);
					mermaidStarted = true;
				}
				else if(iterMan == 1)
				{
					resetSeahorse();
					nearGroup.addActor(seahorse);
					
				}

				iterMan++;
			}
			else if(actor.equals(seahorse))
			{
				if(iterSeahorse == 0)
				{
				resetMermaid(true);
				farGroup.addActor(mermaid);
				mermaid.setSpeed(15f);
				mermaidStarted = true;
				}
				iterSeahorse++;
			}
			else if(actor.equals(mermaid))
			{
				Gdx.app.log("itermermaid ", iterMermaid+"");
				if(iterMermaid == 1)
				{
					resetAll();
				}
				else
				{
					iterMermaid++;
				}
			}
		}
		
	}
	

	

}
