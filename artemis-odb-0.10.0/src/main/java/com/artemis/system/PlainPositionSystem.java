package com.artemis.system;


import com.artemis.*;
import com.artemis.factory.Base;
import com.artemis.utils.ImmutableBag;
import com.artemis.utils.IntBag;
import org.openjdk.jmh.infra.Blackhole;

import com.artemis.annotations.Wire;
import com.artemis.component.PlainPosition;
import com.artemis.systems.EntityProcessingSystem;

@Wire
public class PlainPositionSystem extends BaseSystem {

	ComponentMapper<PlainPosition> positionMapper;

	private final Aspect.Builder aspectConfiguration;

	Blackhole voidness = new Blackhole();
	private Entity flyweight;
	private EntitySubscription subscription;
	private IntBag actives;


	@SuppressWarnings("unchecked")
	public PlainPositionSystem() {
		aspectConfiguration = Aspect.all(PlainPosition.class);
	}

	protected void setWorld(World world) {
		super.setWorld(world);

		subscription = getSubscription();
		flyweight = Entity.createFlyweight(world);
	}

	public EntitySubscription getSubscription() {
		AspectSubscriptionManager sm = world.getManager(AspectSubscriptionManager.class);
		return sm.get(aspectConfiguration);
	}

	@Override
	protected void processSystem() {
		actives = subscription.getEntities();
		processEntities();
	}

	private void processEntities() {
		int[] array = actives.getData();
		Entity e = flyweight;
		for (int i = 0, s = actives.size(); s > i; i++) {
			e.id = array[i];
			process(e);
		}
	}

	private void process(Entity e) {
		PlainPosition pos = positionMapper.get(e);
		pos.x += 1;
		pos.y -= 1;
		
		voidness.consume(e);
	}
}
