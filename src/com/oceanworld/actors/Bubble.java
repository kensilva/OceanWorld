package com.oceanworld.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pool.Poolable;

public class Bubble extends Actor implements Poolable
{
	private ParticleEmitter emitter;
	
	private static Pool<Bubble> pool = new Pool<Bubble>(1,5)
			{

				@Override
				protected Bubble newObject() {
					return new Bubble();
				}
		
			};
			
	private Bubble()
	{

	}
	
	public void set(ParticleEffect effect)
	{
		emitter = new ParticleEmitter(effect.findEmitter("bubbles"));
		
		emitter.start();
	}
	
	public static Bubble obtainBubble(float x, float y,ParticleEffect effect)
	{
		Bubble bubble = pool.obtain();
		bubble.set(effect);
		if(bubble != null)
		{
			bubble.emitter.setPosition(x, y);
		}
		return bubble;
	}
	
	public void act(float delta)
	{
		super.act(delta);
		emitter.update(delta);
		if(emitter.isComplete())
		{
			this.remove();
			pool.free(this);
		}
	}
	
	public void draw(SpriteBatch batch,float parentAlpha)
	{
		emitter.draw(batch);
	}
	
	@Override
	public void reset() 
	{
		emitter.reset();
	}
	
}
