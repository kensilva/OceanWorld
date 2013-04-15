package com.oceanworld.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.Array;
import com.oceanworld.OceanWorld;

public class AnimatedSwimmingActor extends SwimmingActor
{
	
	private float delayForCleaningCheck = 8f;

	
	public float getDelayForCleaningCheck() {
		return delayForCleaningCheck;
	}

	public void setDelayForCleaningCheck(float delayForCleaningCheck) {
		this.delayForCleaningCheck = delayForCleaningCheck;
	}


	protected Animation animation;
	protected Animation flippedAnim;
	protected Animation unflippedAnim;
		
	protected boolean isSlowSpeed;

	
	private final Rectangle rectangle = new Rectangle(); //shared rectangle for all swimmers
	
	protected boolean isAutoRemove = true;
	
	private static final InputImpl touchListener = new InputImpl();

	private final static float defaultRotation = 180f;
	
	protected Array<? extends TextureRegion> regions;
	
	private DoneSwimmingListener swimmingListener;
	
	public AnimatedSwimmingActor(Array<? extends TextureRegion> regions)
	{
		this(0.03f,regions);
	}
	
	public AnimatedSwimmingActor(float delay,Array<? extends TextureRegion> regions)
	{
		this.regions = regions;
		Array<AtlasRegion> regionsUnFlipped = (Array<AtlasRegion>) regions;
		TextureRegion regionsFlipped[] = new TextureRegion[regionsUnFlipped.size];
		for(int i = 0; i < regionsUnFlipped.size; i++)
		{
			TextureRegion region = new TextureRegion(regionsUnFlipped.get(i));
			region.flip(false, true);
			regionsFlipped[i] = region;
		}
		animation = new Animation(delay,regionsUnFlipped);
		unflippedAnim = animation;
		animation.setPlayMode(Animation.LOOP);
		flippedAnim = new Animation(0.03f,regionsFlipped);
		flippedAnim.setPlayMode(Animation.LOOP);
		
		setSize((animation.getKeyFrame(0).getRegionWidth()*2) * OceanWorld.IMAGES_RATIO,(animation.getKeyFrame(0).getRegionHeight()*2) * OceanWorld.IMAGES_RATIO);
		setOrigin(getWidth()/2f,getHeight()/2f);
		isSlowSpeed = true;
		speed = generateSpeed();
		this.addListener(touchListener);
	}
	
	public void setSwimmingListener(DoneSwimmingListener listener)
	{
		this.swimmingListener = listener;
	}
	
	protected void fireDoneSwimming()
	{
		if(swimmingListener != null)
		{
			swimmingListener.swimmingDone(this);
		}
	}
	
	public void setAutoRemove(boolean isAutoremoved)
	{
		this.isAutoRemove = isAutoremoved;
	}
	
	public void setWidth(float width)
	{
		super.setWidth(width);
		recomputeBounds();
	}
	
	public void setHeight(float height)
	{
		super.setHeight(height);
		recomputeBounds();
	}
	
	public void act(float delta)
	{
		stateTime += delta;
		
		
		super.act(delta);

		this.setX(getX() + speed* delta * velocityX);
		this.setY(getY() + speed * delta * velocityY);
		
		
		if(isAutoRemove)
		{
			if(stateTime >= delayForCleaningCheck)
			{
				rectangle.setX(getX());
				rectangle.setY(getY());
				
				if(!Intersector.overlapRectangles(rectangle, OceanWorld.SCREEN_BOUNDS))
				{
					stateTime = 0;
					this.remove();
					speed = generateSpeed();
					this.fireDoneSwimming();
				}
			}
		}
	}
	

	/*
	 * The setRotation method is overriden to set the velocity according to rotation
	 * 
	 */
	private final Vector2 angularVelocity = new Vector2();
	public void setRotation(float rotation)
	{
		angularVelocity.set(-1, 0);
		angularVelocity.rotate(rotation + defaultRotation);
		
		velocityX = angularVelocity.x;
		velocityY = angularVelocity.y;
		super.setRotation(rotation - defaultRotation);
	}
	
	public TextureRegion getRegion()
	{
		return animation.getKeyFrame(stateTime);
	}
	
	protected float generateSpeed()
	{
		if(isSlowSpeed)
		{
			return MathUtils.random(50f, 30f);
		}
		else
		{
			return MathUtils.random(201f, 400f);
		}
	}
	

	/*
	 * For out of bounds detection;
	 */
	protected void recomputeBounds()
	{
		float len = Math.max(getWidth(), getHeight());
		rectangle.setWidth(len);
		rectangle.setHeight(len);
	}
	
	/**
	 * @return the sPEED
	 */
	public float getSpeed() {
		return speed;
	}

	/**
	 * @param sPEED the sPEED to set
	 */
	public void setSpeed(float speed) {
		this.speed = speed;
	}

	//
	//
	private static class InputImpl extends InputListener
	{
		public boolean touchDown(InputEvent e,float x, float y, int pointer, int button)
		{
			if(e.getListenerActor() instanceof SwimmingActor)
			{
				SwimmingActor swimmingActor = (SwimmingActor) e.getListenerActor();
				swimmingActor.speed += swimmingActor.speed*0.5f;
				return true;
			}
			return false;
			
		}
	}
	
	/*
	 * this would be used in order to set animation stateTime different keyframes
	 */
	public void stepStateTime(float seconds)
	{
		stateTime += seconds;
	}
	
	public void setFlipped(boolean isFlipped)
	{
		this.flipped = isFlipped;
		animation = isFlipped?flippedAnim:unflippedAnim;
	}
	
	public static interface DoneSwimmingListener
	{
		public void swimmingDone(AnimatedSwimmingActor actor);
	}
	
}
