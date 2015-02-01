package com.artemis.system;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.component.Comp1;
import com.artemis.component.Comp2;
import com.artemis.component.Comp7;
import com.artemis.component.Comp9;
import com.artemis.systems.EntityProcessingSystem;

public class CompSystemC extends EntityProcessingSystem {
	
//	Blackhole voidness = new Blackhole();
	
	@SuppressWarnings("unchecked")
	public CompSystemC() {
		super(Aspect.getAspectForAll(Comp1.class, Comp7.class, Comp9.class).exclude(Comp2.class));
	}

	@Override
	protected void process(Entity e) {
//		voidness.consume(e);
	}
}
