package com.oceanworld.actors;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL11;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.oceanworld.OceanWorld;


public class Sun extends Group
{
	private AssetManager assetManager;
	
	private Image core1,core2,core3,light1,light2,light3;
	
	public Sun(AssetManager manager)
	{
		this.assetManager = manager;
		TextureAtlas atlas =  assetManager.get("data/misc/misc.pack", TextureAtlas.class);
		core1 = new Image(atlas.findRegion("suncore"));
		core2 = new Image(atlas.findRegion("suncore"));
		core3 = new Image(atlas.findRegion("suncore"));
		
		light1 = new Image(atlas.findRegion("sunlightone"));
		light2 = new Image(atlas.findRegion("sunlightwo"));
		light3 = new Image(atlas.findRegion("sunlightthree"));
		
		core1.setSize(core1.getWidth()*OceanWorld.IMAGES_RATIO, core1.getHeight()*OceanWorld.IMAGES_RATIO);
		core2.setSize(core2.getWidth()*OceanWorld.IMAGES_RATIO, core2.getHeight()*OceanWorld.IMAGES_RATIO);
		core3.setSize(core3.getWidth()*OceanWorld.IMAGES_RATIO, core3.getHeight()*OceanWorld.IMAGES_RATIO);
		
		light1.setSize(light1.getWidth()*OceanWorld.IMAGES_RATIO, light1.getHeight()*OceanWorld.IMAGES_RATIO);
		light2.setSize(light2.getWidth()*OceanWorld.IMAGES_RATIO, light2.getHeight()*OceanWorld.IMAGES_RATIO);
		light3.setSize(light3.getWidth()*OceanWorld.IMAGES_RATIO, light3.getHeight()*OceanWorld.IMAGES_RATIO);
		
		this.setSize(light2.getWidth(), light2.getHeight());
		
		core1.setPosition(this.getWidth()/2f - core1.getWidth()/2f,getHeight()/2f - core1.getHeight()/2f);
		core2.setPosition(this.getWidth()/2f - core2.getWidth()/2f - (core2.getWidth()*0.1f),getHeight()/2f - core2.getHeight()/2f);
		core3.setPosition(this.getWidth()/2f - core3.getWidth()/2f + (core2.getWidth()*0.1f),getHeight()/2f - core3.getHeight()/2f);
		
		light1.setPosition(this.getWidth()/2f - light1.getWidth()/2f,getHeight()/2f - light1.getHeight()/2f);
		light2.setPosition(this.getWidth()/2f - light2.getWidth()/2f,getHeight()/2f - light2.getHeight()/2f);
		light3.setPosition(this.getWidth()/2f - light3.getWidth()/2f,getHeight()/2f - light3.getHeight()/2f);
		
		//Set origin to center for perfect rotation
		light1.setOrigin(light1.getWidth()/2f, light1.getHeight()/2f);
		light2.setOrigin(light2.getWidth()/2f,light2.getHeight()/2f);
		core1.setOrigin(core1.getWidth()/2f,core1.getHeight()/2f);
		core2.setOrigin(core2.getWidth()/2f,core2.getHeight()/2f);
		core3.setOrigin(core3.getWidth()/2f,core3.getHeight()/2f);
		
		light1.addAction(Actions.forever(Actions.parallel(Actions.rotateBy(360f,10f),Actions.sequence(Actions.scaleTo(2f,2f,5f),Actions.scaleTo(1f,1f,5f)))));
		light1.getColor().a = 0.4f;
		light2.addAction(Actions.forever(Actions.parallel(Actions.rotateBy(360f,20f),Actions.sequence(Actions.scaleTo(2f,2f,5f),Actions.scaleTo(1f,1f,5f)))));
		light2.getColor().a = 0.4f;
		
		core1.addAction(Actions.forever(Actions.rotateBy(360f,25f)));
		core2.addAction(Actions.forever(Actions.rotateBy(360f,30f)));
		core3.addAction(Actions.forever(Actions.rotateBy(360f,15f)));
		
		addActor(light1);
		//
		addActor(light2);
		//addActor(light3);
		addActor(core1);
		addActor(core2);
		addActor(core3);
	}
}
