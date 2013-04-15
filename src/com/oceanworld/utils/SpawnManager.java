package com.oceanworld.utils;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;
import com.oceanworld.actorspawner.Spawner;


public class SpawnManager
{
	
	private Array<Spawner> spawners = new Array<Spawner>();
	
	public SpawnManager()
	{
		
	}


	private Array<Spawner> spawnersRemoval =  new Array<Spawner>();
	private Group farGroup;
	private Group nearGroup;
	
	public void setFarGroup(Group group)
	{
		this.farGroup = group;
		for(Spawner spawner : spawners)
		{
			spawner.setFarGroup(group);
		}
	}
	
	public int getSize()
	{
		return spawners.size;
	}
	
	public Array<Spawner> getSpawners()
	{
		return spawners;
	}
	
	public void setNearGroup(Group group)
	{
		this.nearGroup = group;
		for(Spawner spawner : spawners)
		{
			spawner.setNearGroup(group);
		}
	}
	
	public void registerSpawner(Spawner spawner)
	{
		if(spawner != null)
		{
			spawner.setMarkToRemove(false);
			spawner.setSpawnManager(this);
			spawners.add(spawner);
			spawner.setFarGroup(farGroup);
			spawner.setNearGroup(nearGroup);
		}
	}
	
	public void unregisterSpawner(Spawner spawner)
	{
		if(spawners.contains(spawner, false))
		{
			spawnersRemoval.add(spawner);
			spawner.setSpawnManager(null);
		}
	}
	
	public void dispose()
	{
		spawners.clear();
	}
	
	public void update(float delta)
	{
		for(int i = 0,  n = spawners.size; i < n; i++)
		{
			spawners.get(i).update(delta);
		}
		
		spawners.removeAll(spawnersRemoval, false);
	}
	
}
