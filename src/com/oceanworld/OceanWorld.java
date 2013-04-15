package com.oceanworld;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.input.RemoteInput;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.oceanworld.utils.Settings;

public class OceanWorld implements ApplicationListener
{

	private Screen screen;
	
	public FileHandle handle;
	
	public static float SCREEN_WIDTH;
	public static float SCREEN_HEIGHT;
	private final static String colors[] = {"b","y","g","mg","p","v","o"};
	private final static String designs[] = {"1","2","3"};
	public static float IMAGES_RATIO;
	
	int swimmers[] = {1,2,3,4,5}; 
	public static final Rectangle SCREEN_BOUNDS = new Rectangle();
	
	public static String customBackgroundPath = "";
	public static boolean showCustomImage = false;
	
	public ActionListener listener; 
	
	private boolean resized = false;
	@Override
	public void create() {		
		setScreen(new MainScreen(this));
		RemoteInput input;
	}

	public void setScreen(Screen screen)
	{
		if (this.screen != null) this.screen.hide();
		this.screen = screen;
		screen.show();
		screen.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());	
	}
	
	@Override
	public void dispose() {
		Gdx.app.log("ocean_world", "dispose");
		screen.dispose();
	}

	@Override
	public void render() {		
		screen.render(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
	}

	@Override
	public void resize(int width, int height) 
	{
		/*Gdx.app.log("ocean_world", "resize");
		if(!resized)
		{
			resized = true;
		}
		else
		{
			if(handle == null)
			{
				String FILENAME = "internalStorageFile.txt";
				handle = Gdx.files.local(FILENAME);
			}
			Settings settings = Settings.loadSettings(handle);
			settings.setBgColor(colors[MathUtils.random(0, colors.length-1)]);
			settings.setDesign(designs[MathUtils.random(0,designs.length-1)]);
			List list =  Arrays.asList(swimmers);
			Collections.shuffle(list);
			Object[] objs = list.toArray();
			System.out.println(list.size());
			boolean arr[] = new boolean[12];
			System.arraycopy(objs, 0, arr, 0, objs.length);
			settings.setCombination(1);
			Settings.saveSettings(settings, handle);
			pause();
			resume();
			resized = false;
		}*/
		
		screen.resize(width, height);
	
	}

	public void offsetChange(float xOffset,
			float yOffset, float xOffsetStep, float yOffsetStep,
			int xPixelOffset, int yPixelOffset)
	{
		((MainScreen) screen).offsetChange(xOffset,yOffset,xOffsetStep,yOffsetStep,xPixelOffset,yPixelOffset);
	}
	
	
	@Override
	public void pause() {
		Gdx.app.log("ocean_world", "pause");
		screen.pause();
	}

	@Override
	public void resume() {
		Gdx.app.log("ocean_world", "resume");
		screen.resume();
	}
}
