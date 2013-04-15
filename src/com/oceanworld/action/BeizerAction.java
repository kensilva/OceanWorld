package com.oceanworld.action;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.badlogic.gdx.utils.Pool;

public class BeizerAction extends TemporalAction
{
	private static Pool<BeizerAction> beizerPool = new Pool<BeizerAction>(1,100)
			{

				@Override
				protected BeizerAction newObject() {
					// TODO Auto-generated method stub
					return new BeizerAction();
				}
		
			};
			
	private final Vector2 p0 = new Vector2();
	private final Vector2 p1 = new Vector2();
	private final Vector2 p2 = new Vector2();
	private final Vector2 p3 = new Vector2();

	private final Vector2 p0Org = new Vector2();
	private final Vector2 p1Org = new Vector2();
	private final Vector2 p2Org = new Vector2();
	private final Vector2 p3Org = new Vector2();
	
	public static BeizerAction obtainBeizer(Vector2 p0, Vector2 p1, Vector2 p2, Vector2 p3, float duration)
	{
		BeizerAction beizer = beizerPool.obtain();
		beizer.setPool(beizerPool);
		beizer.setVectors(p0,p1,p2,p3);
		beizer.setDuration(duration);
		return beizer;
	}
	
	public static BeizerAction obtainBeizer(float x0, float y0, float x1, float y1, float x2, float y2, float x3, float y3,float duration)
	{
		BeizerAction beizer = beizerPool.obtain();
		beizer.setPool(beizerPool);
		beizer.setVectors(x0,y0,x1,y1,x2,y2,x3,y3);
		beizer.setDuration(duration);
		return beizer;
	}
	
	private BeizerAction()
	{
		super();
		
	}
	
	public void setVectors(float x0, float y0, float x1, float y1, float x2, float y2, float x3, float y3)
	{
		this.p0Org.set(x0, y0);
		this.p1Org.set(x1, y1);
		this.p2Org.set(x2, y2);
		this.p3Org.set(x3, y3);
		
	}
	
	public void setVectors(Vector2 p0,Vector2 p1, Vector2 p2, Vector2 p3)
	{
		this.p0Org.set(p0);
		this.p1Org.set(p1);
		this.p2Org.set(p2);
		this.p3Org.set(p3);
		
	}
	
	private final Vector2 p = new Vector2();

	private void resetVectors()
	{
		this.p0.set(p0Org);
		this.p1.set(p1Org);
		this.p2.set(p2Org);
		this.p3.set(p3Org);
	}
	
	private void computeBeizer(float t)
	{
		  float u = 1- t;
		  float tt = t*t;
		  float uu = u*u;
		  float uuu = uu * u;
		  float ttt = tt * t;
		 
		  resetVectors();
		 
		  p.set(p0.mul(uuu)).add(p1.mul(3 * uu * t)).add(p2.mul(3 * u * tt)).add(p3.mul(ttt));
		 
		  //p = uuu * p0; //first term
		 /* //p += 3 * uu * t * p1; //second term
		  p.add(p1.mul(3 * uu * t));
		  
		 // p += 3 * u * tt * p2; //third term
		  p.add(p2.mul(3 * u * tt));
		  
		  //p += ttt * p3; //fourth term
		  p.add(p3.mul(ttt));*/
		 
	}
	
	@Override
	protected void update(float percent) 
	{
		
		computeBeizer(percent);
		getActor().setPosition(p.x, p.y);
	}
	
	protected void end()
	{
		p.set(0, 0);
	}

}
