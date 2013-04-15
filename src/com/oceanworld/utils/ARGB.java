package com.oceanworld.utils;

import com.badlogic.gdx.graphics.Color;

public class ARGB
{

	float red;
	float green;
	float blue;
	float alpha;
	
	//11111111000000001111111100000000
	//r = value >> 24 & 0x00ff;
	
	int color;
	public ARGB(int color)
	{
		setColor(color);

	}
	
	public void setColor(int color)
	{
		this.color = color;
		recompute();
	}
	
	public int getColor()
	{
		return color;
	}
	
	private float intToFloat(int value)
	{
		float fValue = (float)value/255f;
		return fValue;
		
	}
	
	private void recompute()
	{
		int a = (color & 0xff000000) >> 24;
		int r = (color & 0x00ff0000) >> 16;
		int g = (color & 0x0000ff00) >> 8;
		int b = (color & 0x000000ff);

		red = intToFloat(r);
		green = intToFloat(g);
		blue = intToFloat(b);
		alpha = intToFloat(a);
	}
	
	public float getRed()
	{
		return red;
	}
	
	public float getBlue()
	{
		return blue;
	}
	
	public float getGreen()
	{
		return green;
	}
	
	public float getAlpha()
	{
		return alpha;
	}
	
}
