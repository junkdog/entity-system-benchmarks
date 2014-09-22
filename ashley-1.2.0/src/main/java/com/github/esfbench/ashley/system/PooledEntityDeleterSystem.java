package com.github.esfbench.ashley.system;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.utils.ImmutableArray;
import com.github.esfbench.JmhSettings;
import com.github.esfbench.ashley.component.PlainPosition;

public final class PooledEntityDeleterSystem extends EntitySystem {

	public static int ENTITY_COUNT;
	
	int[] ids;
	
	int counter;
	int index;

	private Class<? extends Component> c1;
	private Class<? extends Component> c2;

	private PooledEngine engine;
	private ImmutableArray<Entity> entities;
	
	public PooledEntityDeleterSystem(long seed, int entityCount, Class<? extends Component> c1, Class<? extends Component> c2) {
		this.c1 = c1;
		this.c2 = c2;
	
		ENTITY_COUNT = entityCount;
		ids = new int[ENTITY_COUNT];
		
		// ashley is backed up am IntMap, hence we only need to track ids
//		for (int i = 0; ids.length > i; i++)
	}
	
	@Override
	public void addedToEngine(Engine engine) {
		this.engine = (PooledEngine) engine;
		entities = engine.getEntitiesFor(Family.getFor(PlainPosition.class));
//		engine.addEntityListener(new EntityListener() {
//			
//			@Override
//			public void entityRemoved(Entity entity) {
//				entities.remove(entity.getIndex());
//			}
//			
//			@Override
//			public void entityAdded(Entity entity) {
//				entities.put(entity.getIndex(), entity);
//			}
//		});
		for (int i = 0; ENTITY_COUNT > i; i++)
			createEntity();
	}

	@Override
	public void update(float deltaTime) {
		begin();
		processSystem();
	}
	
	protected void begin() {
		counter++;
	}

	protected void processSystem() {
		if (counter == 100) {

//			Entity e = entities.get(ids[index++]);
			Entity e = entities.get(0);
			
			engine.removeEntity(e);
			index = index % JmhSettings.ENTITY_COUNT;
			counter = 0;
		} else if (counter == 1) { // need to wait one round to reclaim entities
			createEntity();
		}
	}
	
	private int creationIndex;
	
	protected final void createEntity() {
		
		PooledEngine pooledEngine = (PooledEngine)engine;
		Entity e = pooledEngine.createEntity();
		pooledEngine.createComponent(c1);
		pooledEngine.createComponent(c2);
		engine.addEntity(e);
		ids[creationIndex++] = e.getIndex();
		
		creationIndex = creationIndex % JmhSettings.ENTITY_COUNT;
	}
}
