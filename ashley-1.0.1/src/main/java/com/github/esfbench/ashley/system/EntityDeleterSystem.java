package com.github.esfbench.ashley.system;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.utils.IntMap;
import com.github.esfbench.JmhSettings;

public final class EntityDeleterSystem extends EntitySystem {

	public static int ENTITY_COUNT;
	
	int[] ids;
	
	int counter;
	int index;

	private Class<? extends Component> c1;
	private Class<? extends Component> c2;

	private Engine engine;
	
	private IntMap<Entity> entities = new IntMap<Entity>();

	public EntityDeleterSystem(long seed, int entityCount, Class<? extends Component> c1, Class<? extends Component> c2) {
		this.c1 = c1;
		this.c2 = c2;
	
		ENTITY_COUNT = entityCount;
		ids = new int[ENTITY_COUNT];
		
		// ashley is backed up am IntMap, hence we only need to track ids
//		for (int i = 0; ids.length > i; i++)
	}
	
	@Override
	public void addedToEngine(Engine engine) {
		this.engine = engine;
		engine.addEntityListener(new EntityListener() {
			
			@Override
			public void entityRemoved(Entity entity) {
				entities.remove(entity.getIndex());
			}
			
			@Override
			public void entityAdded(Entity entity) {
				entities.put(entity.getIndex(), entity);
			}
		});
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

			Entity e = entities.get(ids[index++]);
			
			engine.removeEntity(e);
			index = index % JmhSettings.ENTITY_COUNT;
			counter = 0;
		} else if (counter == 1) { // need to wait one round to reclaim entities
			createEntity();
		}
	}
	
	private int creationIndex;
	
	protected final void createEntity() {
		
		Entity e = new Entity();
		try {
			e.add(c1.newInstance());
			e.add(c2.newInstance());
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		}
		engine.addEntity(e);
		ids[creationIndex++] = e.getIndex();
		creationIndex = creationIndex % JmhSettings.ENTITY_COUNT;
	}
}
