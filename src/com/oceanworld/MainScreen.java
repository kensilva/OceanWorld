package com.oceanworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureLoader.TextureParameter;

import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.oceanworld.actors.Bubble;
import com.oceanworld.actors.Sun;
import com.oceanworld.actorspawner.CombinationOne;
import com.oceanworld.actorspawner.CombinationThree;
import com.oceanworld.actorspawner.CombinationTwo;
import com.oceanworld.actorspawner.DolphinSpawner;
import com.oceanworld.actorspawner.FishSpawner;
import com.oceanworld.actorspawner.HammerheadSpawner;
import com.oceanworld.actorspawner.JellyFishSpawner;
import com.oceanworld.actorspawner.ManSpawner;
import com.oceanworld.actorspawner.MermaidSpawner;
import com.oceanworld.actorspawner.SharkSkatSpawner;
import com.oceanworld.actorspawner.SkatSharkSpawner;
import com.oceanworld.actorspawner.SkatSpawner;
import com.oceanworld.actorspawner.SmallFishSpawner;
import com.oceanworld.actorspawner.SharkSwordFishSpawner;
import com.oceanworld.actorspawner.Spawner;
import com.oceanworld.actorspawner.TurtleSpawner;

import com.oceanworld.utils.Settings;
import com.oceanworld.utils.SpawnManager;

public class MainScreen implements Screen
{
	Stage stage;

	private OceanWorld oceanWorld;
	private float screenWidth, screenHeight;
	
	private String backgroundcolor;
	private String design;
	private String bgDesign;
	private String fgDesign;
	
	private Group holderGrp;
	private Group nearGroup,farGroup;
	
	private Image btnSettings;
	private boolean showSettings;

	private Texture textureWater,textureBackground,textureBgDesign,textureFgDesign,textureDistance;
	
	private AssetManager assetManager;
	
	private Image water, background,bgDesignImg,fgDesignImg,distance;
	
	private Sun sun;
	
	private boolean isAssetsLoaded;
	private boolean oneFrameRendered;
	
	private SpawnManager spawnManager;
	
	//spawners
	private int selectedCombination;
	private CombinationOne combinationOne;
	private CombinationTwo combinationTwo;
	private CombinationThree combinationThree;
	
	private Spawner selectedSpawner;
	
	private ParticleEffect bubbleEffect;

	private Group bkHolder;
	private Group holderBgDesign;
	private Group holderFgDesign;
	
	
	private boolean reloadPref = false;
	
	public MainScreen(OceanWorld oceanWorld)
	{
		this.oceanWorld = oceanWorld;
		
		loadPrefs();
		stage = new Stage();
		holderGrp = new Group();

		
		this.setScreenMeasurments();
		//SwimmingActor setAssetManager
		assetManager = new AssetManager();

		OceanWorld.SCREEN_HEIGHT = screenHeight;
		OceanWorld.SCREEN_WIDTH = screenWidth;
		
		OceanWorld.SCREEN_BOUNDS.set(0, 0, OceanWorld.SCREEN_WIDTH,OceanWorld.SCREEN_HEIGHT);
		
		textureWater = new Texture(Gdx.files.internal("data/textures/water.png"));
		textureWater.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		
		assetManager.load(this.backgroundcolor, Texture.class);
		assetManager.load(this.bgDesign, Texture.class);
		assetManager.load(this.fgDesign, Texture.class);
		assetManager.finishLoading();
		
		textureBackground = assetManager.get(this.backgroundcolor,Texture.class);
		textureBackground.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		textureBgDesign = assetManager.get(this.bgDesign, Texture.class);
		textureBgDesign.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		textureFgDesign = assetManager.get(this.fgDesign, Texture.class);
		textureFgDesign.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		textureDistance = new Texture(Gdx.files.internal("data/textures/distance.png"));
		textureDistance.setFilter(TextureFilter.Linear,TextureFilter.Linear);
		
		water = new Image(new TextureRegion(textureWater, 0, 0, 683, 1024));
		background = new Image(new TextureRegion(textureBackground, 0, 0, 683, 1024));
		bgDesignImg = new Image(new TextureRegion(textureBgDesign,0,0,683,1024));
		fgDesignImg = new Image(new TextureRegion(textureFgDesign,0,0,683,1024));
		distance	= new Image(new TextureRegion(textureDistance,0,0,683,1024));
		
		float SCREEN_ASPECT_RATIO = screenWidth/screenHeight;
		
		
		float BG_ASPECT_RATIO = 0.667f;

		if(SCREEN_ASPECT_RATIO < BG_ASPECT_RATIO)
		{ 
			background.setSize(screenHeight*BG_ASPECT_RATIO, screenHeight);
			OceanWorld.IMAGES_RATIO = screenHeight/1024f;
		}
		else 
		{
			background.setSize(screenWidth, screenWidth/BG_ASPECT_RATIO);
			OceanWorld.IMAGES_RATIO = screenWidth/683f;
		}
		
		background.setSize(screenWidth, screenHeight);
		
		water.setSize(water.getWidth()*OceanWorld.IMAGES_RATIO,water.getHeight()*OceanWorld.IMAGES_RATIO);
		bgDesignImg.setSize(bgDesignImg.getWidth()*OceanWorld.IMAGES_RATIO, bgDesignImg.getHeight()*OceanWorld.IMAGES_RATIO);
		fgDesignImg.setSize(fgDesignImg.getWidth()*OceanWorld.IMAGES_RATIO, fgDesignImg.getHeight()*OceanWorld.IMAGES_RATIO);
		distance.setSize(distance.getWidth() * OceanWorld.IMAGES_RATIO, distance.getHeight() * OceanWorld.IMAGES_RATIO);

		bkHolder =  new Group();
		bkHolder.addActor(background);
		holderGrp.addActor(bkHolder);
		holderGrp.addActor(water);
		
		
		assetManager.load("data/misc/misc.pack", TextureAtlas.class);
		assetManager.finishLoading();
		TextureAtlas miscPack = assetManager.get("data/misc/misc.pack", TextureAtlas.class);
		
		bubbleEffect = new ParticleEffect();
		bubbleEffect.load(Gdx.files.internal("data/misc/bubble.fx"),miscPack);
		
		btnSettings = new Image(miscPack.findRegion("config"));
		
		btnSettings.addListener(new InputListener()
		{
			public boolean touchDown(InputEvent evt,float x, float y, int pointer, int button)
			{

				boolean touched = super.touchDown(evt, x, y, pointer, button);
				if(MainScreen.this.oceanWorld.listener != null)
				{
					MainScreen.this.oceanWorld.listener.act();
					return true;
				}

				return touched;
			}
		});
		float size = Math.max(btnSettings.getWidth(),btnSettings.getHeight());
		size *= OceanWorld.IMAGES_RATIO;
		btnSettings.setSize(size,size);
		btnSettings.addAction(Actions.forever(Actions.rotateBy(360f, 3f)));
		btnSettings.setPosition((screenWidth - this.btnSettings.getWidth()), (this.screenHeight - this.btnSettings.getHeight()) - (size * 0.5f));
		btnSettings.setOrigin(btnSettings.getWidth()/2f, btnSettings.getHeight()/2f);
		
		
		sun = new Sun(assetManager);
		if(design.equalsIgnoreCase("2"))
		{
			sun.addAction(Actions.moveTo((screenWidth*0.35f) - sun.getWidth()/2f,(screenHeight * 0.6f) - this.sun.getHeight()/2f));
		}
		else if(design.equalsIgnoreCase("3"))
		{
			
			sun.addAction(Actions.moveTo(screenWidth/2f - sun.getWidth()/2f, (screenHeight * 0.6f) - this.sun.getHeight()/2f));

		}
		else
		{
			sun.addAction(Actions.moveTo((screenWidth*0.35f) - sun.getWidth()/2f, (this.screenHeight*0.85f) -sun.getHeight()/2f));
		}
		
		
		this.holderBgDesign = new Group(); //this will hold our background Design
		this.holderBgDesign.setSize(screenWidth, screenHeight);
		this.holderFgDesign = new Group(); //this will hold out foreground Design
		this.holderFgDesign.setSize(screenWidth, screenHeight);
		holderBgDesign.addActor(bgDesignImg);
		holderFgDesign.addActor(fgDesignImg);
		
		holderGrp.addActor(holderBgDesign); //first
		holderGrp.addActor(sun);		  //next
		holderGrp.addActor(holderFgDesign); //left last
		
		assetManager.setErrorListener(new AssetErrorListener()
		{

			@Override
			public void error(String fileName, Class type, Throwable throwable) {
				Gdx.app.log("error in"+fileName, "");
				
			}
			
		});
		
		stage.addActor(holderGrp);
		
		if(showSettings)
		{
			stage.addActor(this.btnSettings);
		}
		
		stage.addListener(new InputListener()
		{
			public boolean touchDown(InputEvent event,float x,float y,int pointer,int button)
			{
				
				spawnBubble(x,y);
				return super.touchDown(event, x, y, pointer, button);
			}
		});
		
		//stage.setViewport(screenWidth,screenHeight,true);
		Gdx.input.setInputProcessor(stage);
		
		initiateAssetLoading();
		stage.getRoot().setCullingArea(OceanWorld.SCREEN_BOUNDS);

	}
	
	public void spawnBubble(float x, float y)
	{
		if(isAssetsLoaded)
		{
			Bubble bubble = Bubble.obtainBubble(x, y,bubbleEffect);
			this.holderGrp.addActor(bubble);
		}
	}
	
	public void initiateAssetLoading() 
	{
		Spawner spawner = getSpawner(this.selectedCombination);
		spawner.setAndRequestAssetManager(this.assetManager);
		this.selectedSpawner = spawner;
		
		if(nearGroup == null)
		{
			nearGroup = new Group();
			nearGroup.setSize(stage.getWidth(), stage.getHeight());
			farGroup = new Group();
			farGroup.setSize(stage.getWidth(), stage.getHeight());
			
			holderGrp.addActor(farGroup);
			holderGrp.addActor(distance);
			holderGrp.addActor(nearGroup);
		}
		
	}
	
	public void offsetChange(float xOffset,
			float yOffset, float xOffsetStep, float yOffsetStep,
			int xPixelOffset, int yPixelOffset)
	{
		//690
		// 80;
		float percentage = (float)xPixelOffset/screenWidth;
		float actualXtoMove = (background.getWidth()/2f)*percentage;

		
		if(Math.abs((background.getWidth() + actualXtoMove)) >= screenWidth)
		{
			holderGrp.setX(actualXtoMove);
		}
	}
	
	public void processLoadedAssets()
	{


		spawnManager.registerSpawner(this.selectedSpawner);
		spawnManager.setNearGroup(nearGroup);
		spawnManager.setFarGroup(farGroup);
		this.selectedSpawner.init();
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0f, 0f, 0f,1f);
		Gdx.gl10.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		//stage.getCamera().position.set(-100f, stage.getCamera().position.y, 0);
		
		if(!isAssetsLoaded)
		{
			
			stage.act(delta);
			stage.draw();
			if(oneFrameRendered){
				if(assetManager.update())
				{
			    	assetManager.finishLoading();
			    	Gdx.app.log("assets loaded", "True");
			    	isAssetsLoaded = true;
			    	processLoadedAssets();
			    }
			}
			else 
				oneFrameRendered = true;
		}
		else if(this.assetManager.update())
		{
			spawnManager.update(delta);
			stage.act(delta);
			stage.draw();
		}
		
	}

	@Override
	public void resize(int width, int height) {
		
		Gdx.app.log("resized","");
	}

	@Override
	public void show() {

		Gdx.app.log("show","");
	}

	@Override
	public void hide() {

		Gdx.app.log("hide","");
	}

	@Override
	public void pause() 
	{

	}

	@Override
	public void resume() 
	{
		Gdx.input.setInputProcessor(stage);
		if(isAssetsLoaded)
		{
			loadPrefs();
		}

	}

	@Override
	public void dispose() {

		Gdx.app.log("ocean_world","dispose screen");
		spawnManager.dispose();
		if(assetManager != null)
		{

			assetManager.dispose();
		}
		this.textureWater.dispose();
		bubbleEffect.dispose();
		stage.dispose();

	}
	
	public void setScreenMeasurments(){
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		
		Gdx.app.log("width"+w, "Height"+h);

		
		if(w<h){//portrait
			screenWidth = w;
			screenHeight = h;
			stage.setViewport(screenWidth, screenHeight, false);
			stage.getCamera().update();
			
			
		}
		else{//landscape
			screenWidth = h;
			screenHeight = w;
			stage.setViewport(screenWidth, screenHeight, false);
			holderGrp.setRotation(-90);
			
		}
		holderGrp.setSize(stage.getWidth(), stage.getHeight());
		holderGrp.setOrigin(stage.getWidth()/2f, stage.getHeight()/2f);

	}
	
	public void loadPrefs()
	{
		if(spawnManager == null)
		{
			spawnManager = new SpawnManager();
		}
		String FILENAME = "internalStorageFile.txt";
		Settings settings = Settings.loadSettings(Gdx.files.local(FILENAME));
		settings.setCombination(3);
		changeBackground(settings);
		changeSwimmers(settings);
		changeDesign(settings);
		
		if(btnSettings != null)
		{
			if(settings.isShowSettings())
			{
				stage.addActor(btnSettings);
			}
			else
			{
				btnSettings.remove();
			}
		}
		
		this.showSettings = settings.isShowSettings();
	}
	
	public void changeBackground(Settings settings)
	{
		String bgcolor = settings.getBgColor();
		String rootDir = "data/textures/";
		String bgName  = "background_blue.jpg";
	
		if(bgcolor.equals("y"))
		{
			bgName = "background_yellow.jpg";	
		}
		else if(bgcolor.equals("g"))
		{
			bgName = "background_green.jpg";
		}
		else if(bgcolor.equalsIgnoreCase("mg"))
		{
			bgName = "background_mint_green.jpg";
		}
		else if(bgcolor.equals("o"))
		{
			bgName = "background_orange.jpg";
		}
		else if(bgcolor.equals("p"))
		{
			bgName = "background_pink.jpg";
		}
		else if(bgcolor.equals("v"))
		{
			bgName = "background_violet.jpg";
		}


		String newBgColor = rootDir+bgName;

		if(this.assetManager != null)
		{
			if(this.assetManager.isLoaded(this.backgroundcolor))
			{
				if(!this.backgroundcolor.equalsIgnoreCase(newBgColor))
				{
					TextureParameter param = new TextureParameter();
					param.minFilter = TextureFilter.Linear;
					param.magFilter = TextureFilter.Linear;

					this.assetManager.load(newBgColor, Texture.class,param);
					assetManager.finishLoading();
					
					this.background.addAction(Actions.sequence(Actions.fadeOut(0.4f),Actions.run(new ResourceUnloader(assetManager).setResource(this.backgroundcolor)),Actions.removeActor()));
					Gdx.app.log("Changing Background to",newBgColor);
					this.textureBackground = assetManager.get(newBgColor,Texture.class);
					this.background = new Image(new TextureRegion(this.textureBackground,0,0,683,1024));
					this.background.addAction(Actions.sequence(Actions.fadeOut(0f),Actions.delay(1f),Actions.fadeIn(1f)));
					bkHolder.addActor(this.background);
				}
			}
		}
		this.backgroundcolor = newBgColor;
	}
	
	public void changeSwimmers(Settings settings)
	{	
		int newCombination = settings.getCombination();
		
		if(assetManager != null)
		{
			if(this.selectedCombination != newCombination)
			{
				Spawner combinationSpawner = this.getSpawner(newCombination);
				combinationSpawner.setAndRequestAssetManager(this.assetManager);
				this.assetManager.finishLoading();
				spawnManager.registerSpawner(combinationSpawner);
			}
		}
		this.selectedCombination = newCombination;
		
	}

	public void changeDesign(Settings settings)
	{
		
		String newDesign = settings.getDesign();
		String rootDir = "data/textures/";
		
		String newBgDesign = rootDir+"design_1_bg.png";
		String newFgDesign = rootDir+"design_1_fg.png";
		

		if(newDesign.equals("2"))
		{
			newBgDesign = rootDir+"design_2_bg.png";
			newFgDesign = rootDir+"design_2_fg.png";
		}
		else if(newDesign.equals("3"))
		{
			newBgDesign = rootDir+"design_3_bg.png";
			newFgDesign = rootDir+"design_3_fg.png";
		}
		
		if(assetManager != null)
		{
			if(assetManager.isLoaded(this.fgDesign))
			{
				if(!newFgDesign.equalsIgnoreCase(fgDesign))
				{
					
					TextureParameter param = new TextureParameter();
					param.minFilter = TextureFilter.Linear;
					param.magFilter = TextureFilter.Linear;
					this.assetManager.load(newFgDesign, Texture.class,param);
					
					assetManager.finishLoading();
					
					final float width = this.fgDesignImg.getWidth();
					final float height = this.fgDesignImg.getHeight();
					this.fgDesignImg.addAction(Actions.sequence(Actions.moveTo(OceanWorld.SCREEN_WIDTH, 0,0.5f),Actions.run(new ResourceUnloader(assetManager).setResource(this.fgDesign)),Actions.removeActor()));
		
					this.textureFgDesign = assetManager.get(newFgDesign,Texture.class);
				
					
					this.fgDesignImg = new Image(new TextureRegion(this.textureFgDesign,0,0,683,1024));
					
					this.fgDesignImg.setSize(width, height);
					this.fgDesignImg.addAction(Actions.sequence(Actions.moveTo(-this.fgDesignImg.getWidth(), 0),Actions.moveTo(0, 0,0.5f)));
					this.holderFgDesign.addActor(this.fgDesignImg);
					
				}
			}
			
			if(assetManager.isLoaded(this.bgDesign))
			{
				if(!newBgDesign.equalsIgnoreCase(bgDesign))
				{
					TextureParameter param = new TextureParameter();
					param.minFilter = TextureFilter.Linear;
					param.magFilter = TextureFilter.Linear;

					this.assetManager.load(newBgDesign, Texture.class,param);
					assetManager.finishLoading();
					
					
					final float width = this.bgDesignImg.getWidth();
					final float height = this.bgDesignImg.getHeight();

					this.bgDesignImg.addAction(Actions.sequence(Actions.moveTo(OceanWorld.SCREEN_WIDTH, 0,0.5f),Actions.run(new ResourceUnloader(assetManager).setResource(this.bgDesign)),Actions.removeActor()));
					
					
					this.textureBgDesign = assetManager.get(newBgDesign,Texture.class);
					this.bgDesignImg = new Image(new TextureRegion(this.textureBgDesign,0,0,683,1024));
					bgDesignImg.setSize(width, height);
					this.bgDesignImg.addAction(Actions.sequence(Actions.moveTo(-this.bgDesignImg.getWidth(), 0),Actions.moveTo(0, 0,0.5f)));
					this.holderBgDesign.addActor(bgDesignImg);

					
				}
			}
		
			if(newDesign.equalsIgnoreCase("2"))
			{
				sun.addAction(Actions.moveTo((screenWidth*0.35f) - sun.getWidth()/2f,(screenHeight * 0.6f) - this.sun.getHeight()/2f ,0.8f));
			}
			else if(newDesign.equalsIgnoreCase("3"))
			{
				
				sun.addAction(Actions.moveTo(screenWidth/2f - sun.getWidth()/2f, (screenHeight * 0.6f) - this.sun.getHeight()/2f,0.8f));

			}
			else
			{
				sun.addAction(Actions.moveTo((screenWidth*0.35f) - sun.getWidth()/2f, (this.screenHeight*0.85f) -sun.getHeight()/2f,0.8f));
			}
		}
		
		this.design = newDesign;

		this.bgDesign = newBgDesign;
		this.fgDesign = newFgDesign;
		
	}
	
	private Spawner getSpawner(int combination)
	{
		Spawner combinationSpawner;
		
		switch(combination)
		{
			case 1:
			{
				if(combinationOne == null)
				{
					combinationOne = new CombinationOne();
				}
				combinationSpawner = combinationOne;
				break;
			}
			case 2:
			{
				if(combinationTwo == null)
				{
					combinationTwo = new CombinationTwo();
				}
				combinationSpawner = combinationTwo;
				break;
			}
			case 3:
			{
				if(combinationThree == null)
				{
					combinationThree =  new CombinationThree();
				}
				combinationSpawner = combinationThree;
				break;
				
			}
			default:
			{
				if(combinationOne == null)
				{
					combinationOne = new CombinationOne();
				}
				combinationSpawner = combinationOne;
			}
		}
		return combinationSpawner;
		
	}
	
	private static class ResourceUnloader implements Runnable
	{
		private String resource;
		private AssetManager assetManager;
		
		public ResourceUnloader(AssetManager assetManager)
		{
			this.assetManager = assetManager;
		}
		
		public ResourceUnloader setResource(String resource)
		{
			this.resource = resource;
			return this;
		}
		
		@Override
		public void run() 
		{
			assetManager.unload(resource);
		}
		
	}
}
