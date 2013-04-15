package com.oceanworld.actorspawner;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.oceanworld.utils.SpawnManager;

public abstract class Spawner 
{
	public Group farGroup;
	public Group nearGroup;
	
	protected boolean markToRemove;
	protected SpawnManager spawnManager;
	protected AssetManager assetManager;
	
	public void setFarGroup(Group group)
	{
		farGroup = group;
	}
	
	public void init(){}
	public void setAndRequestAssetManager(AssetManager manager)
	{
		this.assetManager = manager;
	}
	
	public void setNearGroup(Group group)
	{
		nearGroup = group;
	}
	
	public boolean isMarkToRemove() {
		return markToRemove;
	}

	public void setMarkToRemove(boolean markToRemove) {
		this.markToRemove = markToRemove;
	}

	public abstract void update(float delta);
	
	public void markToRemove()
	{
		markToRemove = true;
	}

	public SpawnManager getSpawnManager() {
		return spawnManager;
	}

	public void setSpawnManager(SpawnManager spawnManager) {
		this.spawnManager = spawnManager;
	}
	
}
