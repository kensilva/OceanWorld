package com.oceanworld.utils;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.Serializer;

public class Settings 
{
	private final static Json json = new Json();
	
	private final static String SWIMMERS 			= "swimmers";
	private final static String BG_COLOR 			= "color";
	private final static String DESIGN	 			= "design";
	private final static String WATERRIPPLE			= "waterripple";
	private final static String CUSTOMBACKGROUND	= "custombg";
	private final static String SPEED				= "speed";
	private final static String LOADCUSTOM			= "loadcustombg";
	private final static String SHOWSETTINGS		= "showsettings";
	
	private static void initializeJon()
	{
		
		json.setSerializer(Settings.class,new Serializer<Settings>()
				{

					@Override
					public void write(Json json, Settings object,
							Class knownType) {
						
						json.writeObjectStart();
						json.writeValue(BG_COLOR, object.bgColor);
						json.writeValue(CUSTOMBACKGROUND, object.custombg);
						json.writeValue(DESIGN, object.design);
						json.writeValue(SPEED, object.isSlowSpeed);
						json.writeValue(LOADCUSTOM, object.isCustomBG);
						json.writeValue(WATERRIPPLE, object.isWaterRipplesOn);
						json.writeValue(SWIMMERS, object.combination);
						json.writeValue(SHOWSETTINGS,object.showSettings);
						json.writeObjectEnd();
						
					}

					@Override
					public Settings read(Json json, Object jsonData, Class type) {
						// TODO Auto-generated method stub
						Settings settings  	= new Settings();
						settings.bgColor   	= json.readValue(BG_COLOR, String.class, jsonData);
						settings.custombg	= json.readValue(CUSTOMBACKGROUND, String.class, jsonData);
						settings.design		= json.readValue(DESIGN, String.class, jsonData);
						settings.isCustomBG	= json.readValue(LOADCUSTOM, boolean.class, jsonData);
						settings.isSlowSpeed	= json.readValue(SPEED, boolean.class, jsonData);
						settings.combination		= json.readValue(SWIMMERS, int.class, jsonData);
						settings.isWaterRipplesOn	= json.readValue(Settings.WATERRIPPLE, boolean.class, jsonData);
						settings.showSettings       = json.readValue(SHOWSETTINGS, boolean.class, jsonData);
						return settings;
					}
			
				});
		
		
	}
	
	private int combination;
	public int getCombination() {
		return combination;
	}


	public void setCombination(int combination) {
		this.combination = combination;
	}

	private boolean isWaterRipplesOn = false;
	private String design;
	private String custombg;
	private boolean isCustomBG;
	private boolean isSlowSpeed;
	private String bgColor;
	private boolean showSettings;
	
	private Settings()
	{
    	combination = 1;
    	isWaterRipplesOn = false;
    	design 			 ="design1";
    	custombg		 = "";
    	isCustomBG		 = false;	
    	isSlowSpeed		 = false;
    	bgColor			 = "b";
    	showSettings 	 = true;
	}
	
	
	public boolean isShowSettings() {
		return showSettings;
	}


	public void setShowSettings(boolean showSettings) {
		this.showSettings = showSettings;
	}



	public boolean isWaterRipplesOn() {
		return isWaterRipplesOn;
	}


	public String getDesign() {
		return design;
	}


	public String getCustombg() {
		return custombg;
	}


	public boolean isCustomBG() {
		return isCustomBG;
	}


	public boolean isSlowSpeed() {
		return isSlowSpeed;
	}


	public String getBgColor() {
		return bgColor;
	}


	public void setWaterRipplesOn(boolean isWaterRipplesOn) {
		this.isWaterRipplesOn = isWaterRipplesOn;
	}


	public void setDesign(String design) {
		this.design = design;
	}


	public void setCustombg(String custombg) {
		this.custombg = custombg;
	}


	public void setCustomBG(boolean isCustomBG) {
		this.isCustomBG = isCustomBG;
	}


	public void setSlowSpeed(boolean isSlowSpeed) {
		this.isSlowSpeed = isSlowSpeed;
	}


	public void setBgColor(String bgColor) {
		this.bgColor = bgColor;
	}


	public static Settings loadSettings(FileHandle handle)
	{
		if(handle.exists())
		{
			return json.fromJson(Settings.class, handle);
		}
		else
		{
			return new Settings();
		}
	}
	
	public static void saveSettings(Settings settings, FileHandle handle)
	{
		json.toJson(settings, handle);
	}
}
