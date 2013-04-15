package com.oceanworld.actors;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.oceanworld.OceanWorld;
import com.oceanworld.action.BeizerAction;

public class SmallGroupFish2 extends Group
{
	private TextureRegion smallFishRegion;

	public SmallGroupFish2(TextureRegion smallFishRegion)
	{
		this.smallFishRegion =  smallFishRegion;
	}

	private final Vector2 p = new Vector2();
	
	public void resetSmallFish()
	{
		int numSmallFish = MathUtils.random(3, 5);
		
		float yOffset = MathUtils.random(OceanWorld.SCREEN_HEIGHT*0.1f,OceanWorld.SCREEN_HEIGHT * 0.8f);
		
		p.set(OceanWorld.SCREEN_WIDTH, yOffset);
		
		this.clearChildren();
		float delay = 0f;
		int type =  MathUtils.random(1, 3);
		for(int i = 0; i < numSmallFish; ++i)
		{
			SwimmingActor smallFish1 =  SmallFish.obtainSmallFish(smallFishRegion);
			float scale = MathUtils.random(0.2f, 0.4f);
			smallFish1.setSize(smallFish1.getWidth() * scale, smallFish1.getHeight() * scale);
			addActor(smallFish1);
			BeizerAction beizer = BeizerAction.obtainBeizer(p.x, MathUtils.random(p.y -50f, p.y+50f), MathUtils.random(p.x-50f, p.x+50f),MathUtils.random(p.y -50f, p.y + 50f),p.x - 100f,MathUtils.random(p.y-50f, p.y+50f),MathUtils.random(p.x - 200f, p.x - 250f),MathUtils.random(p.y-50f, p.y+50f), 6f);
			p.set(p.x, MathUtils.randomBoolean()?p.y - MathUtils.random(10f, 15f):p.y + MathUtils.random(10f, 15f));
			smallFish1.addAction(Actions.sequence(Actions.moveTo(-smallFish1.getWidth(), -smallFish1.getHeight()),Actions.delay(delay),beizer,Actions.moveTo(-smallFish1.getWidth(), MathUtils.random(p.y-15f,p.y +15f), 2f),Actions.removeActor()));
			delay+= MathUtils.random(0.5f, 1f);
		}
		
		addAction(Actions.delay(15f,Actions.removeActor()));
	}
}
