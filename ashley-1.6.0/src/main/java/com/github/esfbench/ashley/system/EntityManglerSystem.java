package com.github.esfbench.ashley.system;//

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.utils.Array;
import com.github.esfbench.ashley.component.*;

public final class EntityManglerSystem extends EntitySystem {

	int[] ids; // = new int[ENTITY_COUNT];
	int[] cmp; // = new int[ENTITY_COUNT];

	private Array<Class<? extends Component>> types;
	
	private static int ENTITY_COUNT = 0;
	private static int RENEW = 0;
	
	int counter;
	int index;

	private Random rng;
	@SuppressWarnings("rawtypes")
	private Array[] permutations;
	
	private Engine engine;
	private ImmutableArray<Entity> entities;

	@SuppressWarnings("unchecked")
	public EntityManglerSystem(long seed, int entityCount) {
		// 4096 entities = 256 compositions, 262144 = 2048
		super(0);
		int entityPermutations = (int)Math.sqrt(entityCount * 16);
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
		for (int i = 0; permutations.length > i; i++) {
			Array<Class<? extends Component>> components = new Array<Class<? extends Component>>();
			for (int classIndex = 0, s = (int)(rng.nextFloat() * 7) + 3; s > classIndex; classIndex++) {
				components.add(types.get((int)(rng.nextFloat() * types.size)));
			}
			permutations[i] = components;
		}

		cmp = new int[ENTITY_COUNT * 4];
		for (int i = 0; cmp.length > i; i++)
			cmp[i] = (int)(rng.nextFloat() * permutations.length);
	}
	
	@Override
	public void addedToEngine(Engine engine) {
		for (int i = 0; permutations.length > i; i++) {
			Array<Class<? extends Component>> components = new Array<Class<? extends Component>>();
			for (int classIndex = 0, s = (int)(rng.nextFloat() * 7) + 3; s > classIndex; classIndex++) {
				components.add(types.get((int)(rng.nextFloat() * types.size)));
			}
			permutations[i] = components;
		}
		
		this.engine = engine;
		entities = engine.getEntitiesFor(Family.all().get());
		
		for (int i = 0; ENTITY_COUNT > i; i++)
			createEntity();
	}
	
	@Override
	public void update(float deltaTime) {
		counter++;

		if (counter % 2 == 1) {
			for (int i = 0; RENEW > i; i++) {
				Entity e = entities.get(i);
				engine.removeEntity(e);
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
		Entity e = new Entity();
		Array<Class<? extends Component>> components = permutations[cmp[cmpIndex++]];
		if (cmpIndex == cmp.length) cmpIndex = 0;
		
		Object[] data = components.items;
		for (int i = 0, s = components.size; s > i; i++) {
			e.add(newInstance(data[i]));
		}

		engine.addEntity(e);
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
