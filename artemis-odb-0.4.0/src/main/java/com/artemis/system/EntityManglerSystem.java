package com.artemis.system;//

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import org.openjdk.jmh.runner.RunnerException;

import com.artemis.Component;
import com.artemis.Entity;
import com.artemis.component.Comp1;
import com.artemis.component.Comp2;
import com.artemis.component.Comp3;
import com.artemis.component.Comp4;
import com.artemis.component.Comp5;
import com.artemis.component.Comp6;
import com.artemis.component.Comp7;
import com.artemis.component.Comp8;
import com.artemis.component.Comp9;
import com.artemis.systems.VoidEntitySystem;
import com.artemis.utils.Bag;

public final class EntityManglerSystem extends VoidEntitySystem {

	int[] ids; // = new int[ENTITY_COUNT];
	int[] cmp; // = new int[ENTITY_COUNT];

	private Bag<Class<? extends Component>> types;
	
	private static int ENTITY_COUNT = 0;
	private static int RENEW = 0;
	
	int counter;
	int index;

	private Random rng;
	private int entityPermutations;
	private Bag[] permutations;

	@SuppressWarnings("unchecked")
//	public EntityManglerSystem(long seed, int entityCount, int entityPermutations) {
	public EntityManglerSystem(long seed, int entityCount, int entityPermutations) {
		this.entityPermutations = entityPermutations;
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
		
		
		types = new Bag<Class<? extends Component>>();
		types.add(Comp1.class);
		types.add(Comp2.class);
		types.add(Comp3.class);
		types.add(Comp4.class);
		types.add(Comp5.class);
		types.add(Comp6.class);
		types.add(Comp7.class);
		types.add(Comp8.class);
		types.add(Comp9.class);
		
		permutations = new Bag[entityPermutations];
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
	protected void initialize() {
		for (int i = 0; permutations.length > i; i++) {
			Bag<Class<? extends Component>> components = new Bag<Class<? extends Component>>();
			for (int classIndex = 0, s = (int)(rng.nextFloat() * 7); s > classIndex; classIndex++) {
				components.add(types.get((int)(rng.nextFloat() * types.size())));
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
		Bag<Class<? extends Component>> components = permutations[cmp[cmpIndex++]];
		if (cmpIndex == cmp.length) cmpIndex = 0;
		
		Object[] data = components.getData();
		for (int i = 0, s = components.size(); s > i; i++) {
			e.addComponent(newInstance(data[i]));
		}
		e.addToWorld();
	}

	@SuppressWarnings("unchecked")
	private static Component newInstance(Object componentClass) {
		try {
			return ((Class<? extends Component>)componentClass).newInstance();
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
}
