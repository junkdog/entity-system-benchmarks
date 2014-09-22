package com.github.esfbench.ashley.system;//

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.IntMap;
import com.github.esfbench.ashley.component.Comp1;
import com.github.esfbench.ashley.component.Comp2;
import com.github.esfbench.ashley.component.Comp3;
import com.github.esfbench.ashley.component.Comp4;
import com.github.esfbench.ashley.component.Comp5;
import com.github.esfbench.ashley.component.Comp6;
import com.github.esfbench.ashley.component.Comp7;
import com.github.esfbench.ashley.component.Comp8;
import com.github.esfbench.ashley.component.Comp9;

public final class EntityManglerSystem extends EntitySystem {

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
	
	private Engine engine;
	private IntMap<Entity> entities = new IntMap<Entity>();

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
		
		permutations = new Array[entityPermutations];
//		for (int i = 0; permutations.length > i; i++) {
//			Array<Class<? extends Component>> components = new Array<Class<? extends Component>>();
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
	public void addedToEngine(Engine engine) {
		for (int i = 0; permutations.length > i; i++) {
			Array<Class<? extends Component>> components = new Array<Class<? extends Component>>();
			for (int classIndex = 0, s = (int)(rng.nextFloat() * 7); s > classIndex; classIndex++) {
				components.add(types.get((int)(rng.nextFloat() * types.size)));
			}
			permutations[i] = components;
		}
		
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
	
	int lastDeleted;
	
	@Override
	public void update(float deltaTime) {
		counter++;

		if (counter % 2 == 1) {
			for (int i = 0; RENEW > i; i++) {
//				Entity e = entities.get(ids[index++]);
				Entity e = entities.get(lastDeleted++);
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
