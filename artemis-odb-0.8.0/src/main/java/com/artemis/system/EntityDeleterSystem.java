package com.artemis.system;

import java.util.Random;

import com.artemis.Archetype;
import com.artemis.ArchetypeBuilder;
import com.artemis.Component;
import com.artemis.Entity;
import com.artemis.systems.VoidEntitySystem;

public final class EntityDeleterSystem extends VoidEntitySystem {

	int[] ids; // = new int[ENTITY_COUNT];
	
	private static int ENTITY_COUNT = 0;
	
	int counter;
	int index;

	private Class<? extends Component> c1;
	private Class<? extends Component> c2;

	private Archetype archetype;

	public EntityDeleterSystem(long seed, int entityCount, Class<? extends Component> c1, Class<? extends Component> c2) {
		this.c1 = c1;
		this.c2 = c2;
		Random rng = new Random(seed);
		ENTITY_COUNT = entityCount;
		ids = new int[ENTITY_COUNT];
		for (int i = 0; ids.length > i; i++)
			ids[i] = (int)(rng.nextFloat() * ENTITY_COUNT);
	}
	
	@Override
	protected void initialize() {
		archetype = new ArchetypeBuilder().add(c1).add(c2).build(world);
		
		for (int i = 0; ENTITY_COUNT > i; i++)
			createEntity();
	}
	
	@Override
	protected void begin() {
		counter++;
	}

	@Override
	protected void processSystem() {
		if (counter == 100) {
			Entity e = world.getEntity(ids[index++]);
			e.deleteFromWorld();
			index = index % ENTITY_COUNT;
			counter = 0;
		} else if (counter == 1) { // need to wait one round to reclaim entities
			createEntity();
		}
	}
	
	protected final void createEntity() {
		world.createEntity(archetype);
	}
}
