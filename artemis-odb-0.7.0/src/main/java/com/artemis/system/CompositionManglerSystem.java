package com.artemis.system;//

import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.component.PlainPosition;
import com.artemis.component.Position;
import com.artemis.systems.VoidEntitySystem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

@Wire
public final class CompositionManglerSystem extends VoidEntitySystem {

	int[] ids; // = new int[ENTITY_COUNT];

	private static int ENTITY_COUNT = 0;
	private static int RENEW = 0;

	private int index;

	private Random rng;

	ComponentMapper<PlainPosition> positionMapper;

	@SuppressWarnings("unchecked")
	public CompositionManglerSystem(long seed, int entityCount) {
		rng = new Random(seed);
		ENTITY_COUNT = entityCount;
		RENEW = ENTITY_COUNT / 4;

		ArrayList<Integer> idsList = new ArrayList<Integer>();
		for (int i = 0; ENTITY_COUNT > i; i++)
			idsList.add(i);
		Collections.shuffle(idsList);
		
		ids = new int[ENTITY_COUNT];
		for (int i = 0; ids.length > i; i++)
			ids[i] = idsList.get(i);
	}

	@Override
	protected void initialize() {
		for (int i = 0; ENTITY_COUNT > i; i++)
			world.createEntity();
	}
	
	@Override
	protected void processSystem() {

		for (int i = 0; RENEW > i; i++) {
			Entity e = world.getEntity(ids[index++]);
			if (positionMapper.has(e)) {
				e.edit().remove(PlainPosition.class);
			} else {
				e.edit().create(PlainPosition.class);
			}
			index = index % ENTITY_COUNT;
		}
	}
}
