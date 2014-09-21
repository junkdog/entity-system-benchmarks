package com.artemis.system;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.component.Comp6;
import com.artemis.component.Comp7;
import com.artemis.component.Comp8;
import com.artemis.component.Comp9;
import com.artemis.systems.EntityProcessingSystem;

public class CompSystemD extends EntityProcessingSystem {
	
//	Blackhole voidness = new Blackhole();
	
	@SuppressWarnings("unchecked")
	public CompSystemD() {
		super(Aspect.getAspectForAll(Comp6.class, Comp7.class, Comp8.class).exclude(Comp9.class));
	}

	@Override
	protected void process(Entity e) {
//		voidness.consume(e);
	}
}
