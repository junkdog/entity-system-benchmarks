package com.artemis.system;//

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import com.artemis.Component;
import com.artemis.Entity;
import com.artemis.component.*;
import com.artemis.systems.VoidEntitySystem;
import com.badlogic.gdx.utils.Array;

public final class EntityManglerSystem extends VoidEntitySystem {

	int[] ids; // = new int[ENTITY_COUNT];
	int[] cmp; // = new int[ENTITY_COUNT];

	private Array<Class<? extends Component>> types;
	
	private static int ENTITY_COUNT = 0;
	private static int RENEW = 0;
	
	int counter;
	int index;

	private Random rng;
	private int entityPermutations;
	private Array[] permutations;

	@SuppressWarnings("unchecked")
	public EntityManglerSystem(long seed, int entityCount) {
		// 4096 entities = 256 compositions, 262144 = 2048
		entityPermutations = (int)Math.sqrt(entityCount * 16);
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
//		ids[i] = (int)(rng.nextFloat() * ENTITY_COUNT);
		
		
		types = new Array<Class<? extends Component>>();
		types.add(Comp1.class);
		types.add(Comp2.class);
		types.add(Comp3.class);
		types.add(Comp4.class);
		types.add(Comp5.class);
		types.add(Comp6.class);
		types.add(Comp7.class);
		types.add(Comp8.class);
		types.add(Comp9.class);
		types.add(Comp10.class);
		types.add(Comp11.class);
		types.add(Comp12.class);

		permutations = new Array[entityPermutations];
//		for (int i = 0; permutations.length > i; i++) {
//			Bag<Class<? extends Component>> components = new Bag<Class<? extends Component>>();
//			for (int classIndex = 0, s = (int)(rng.nextFloat() * 7); s > classIndex; classIndex++) {
//				components.add(types.get((int)(rng.nextFloat() * types.size())));
//			}
//			permutations[i] = components;
//		}
//		permutations = new Archetype[entityPermutations];

		
		cmp = new int[ENTITY_COUNT * 4];
		for (int i = 0; cmp.length > i; i++)
			cmp[i] = (int)(rng.nextFloat() * permutations.length);
	}
	
	@Override
	public void initialize() {
		for (int i = 0; permutations.length > i; i++) {
			Array<Class<? extends Component>> components = new Array<Class<? extends Component>>();
			for (int classIndex = 0, s = (int)(rng.nextFloat() * 7) + 3; s > classIndex; classIndex++) {
				components.add(types.get((int)(rng.nextFloat() * types.size)));
			}
			permutations[i] = components;
		}
		
		for (int i = 0; ENTITY_COUNT > i; i++)
			createEntity();
	}
	
	@Override
	protected void begin() {
		counter++;
	}

	@Override
	protected void processSystem() {

		if (counter % 2 == 1) {
			for (int i = 0; RENEW > i; i++) {
				Entity e = world.getEntity(ids[index++]);
				e.deleteFromWorld();
				index = index % ENTITY_COUNT;
			}
		} else {
			for (int i = 0; RENEW > i; i++) {
				createEntity();
			}
		}
	}
	
	int cmpIndex = 0;
//	
	@SuppressWarnings("unchecked")
	private final void createEntity() {
		Entity e = world.createEntity();
		Array<Class<? extends Component>> components = permutations[cmp[cmpIndex++]];
		if (cmpIndex == cmp.length) cmpIndex = 0;
		
		Object[] data = components.items;
		for (int i = 0, s = components.size; s > i; i++) {
			e.addComponent(world.createComponent((Class<Component>) data[i]));
		}
		e.addToWorld();
	}
}
