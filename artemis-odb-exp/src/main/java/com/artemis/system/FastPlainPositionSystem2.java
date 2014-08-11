package com.artemis.system;


import org.openjdk.jmh.infra.Blackhole;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.annotations.Wire;
import com.artemis.component.PlainPosition;
import com.artemis.utils.Bag;
import com.artemis.utils.ImmutableBag;

@Wire
public class FastPlainPositionSystem2 extends EntitySystem {

	ComponentMapper<PlainPosition> positionMapper;

	Blackhole voidness = new Blackhole();
	
	
	@SuppressWarnings("unchecked")
	public FastPlainPositionSystem2() {
		super(Aspect.getAspectForAll(PlainPosition.class));
	}

	protected void process(Entity e) {
		PlainPosition pos = positionMapper.get(e);
		pos.x += 0.1f % 100000;
		pos.y -= 0.1f % 100000;
		
		voidness.consume(e);
	}

	@Override
	protected void processEntities(ImmutableBag<Entity> entities) {
		Object[] array = ((Bag<Entity>)entities).getData();
		for (int i = 0, s = entities.size(); s > i; i++) {
			process((Entity)array[i]);
		}
	}

	@Override
	protected boolean checkProcessing() {
		return true;
	}
}
