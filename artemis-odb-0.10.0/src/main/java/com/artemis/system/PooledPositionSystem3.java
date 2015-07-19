package com.artemis.system;


import com.artemis.*;
import com.artemis.utils.IntBag;
import org.openjdk.jmh.infra.Blackhole;

import com.artemis.annotations.Wire;
import com.artemis.component.PooledPosition;
import com.artemis.systems.EntityProcessingSystem;

@Wire
public class PooledPositionSystem3 extends BaseSystem {

	ComponentMapper<PooledPosition> positionMapper;

	private final Aspect.Builder aspectConfiguration;

	Blackhole voidness = new Blackhole();
	private Entity flyweight;
	private EntitySubscription subscription;


	@SuppressWarnings("unchecked")
	public PooledPositionSystem3() {
		aspectConfiguration = Aspect.all(PooledPosition.class);
	}

	protected void setWorld(World world) {
		super.setWorld(world);

		flyweight = Entity.createFlyweight(world);
		subscription = getSubscription();
	}

	public EntitySubscription getSubscription() {
		AspectSubscriptionManager sm = world.getManager(AspectSubscriptionManager.class);
		return sm.get(aspectConfiguration);
	}

	@Override
	protected void processSystem() {
		IntBag actives = subscription.getEntities();
		int[] array = actives.getData();
		Entity e = flyweight;
		for (int i = 0, s = actives.size(); s > i; i++) {
			e.id = array[i];
			process(e);
		}
	}

	protected void process(Entity e) {
		PooledPosition pos = positionMapper.get(e);
		pos.x += 0.5f;
		pos.y -= 0.5f;
		
		voidness.consume(e);
	}
}
