package com.artemis.system;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.Filter;
import com.artemis.component.Comp1;
import com.artemis.component.Comp2;
import com.artemis.component.Comp7;
import com.artemis.component.Comp9;
import com.artemis.systems.EntityProcessingSystem;
import org.openjdk.jmh.infra.Blackhole;

public class CompSystemC extends EntityProcessingSystem {
	
	Blackhole voidness = new Blackhole();
	
	@SuppressWarnings("unchecked")
	public CompSystemC() {
		super(Filter.allComponents(Comp1.class, Comp7.class, Comp9.class).exclude(Comp2.class));
	}

	@Override
	protected void process(Entity e) {
		voidness.consume(e);
	}
}
