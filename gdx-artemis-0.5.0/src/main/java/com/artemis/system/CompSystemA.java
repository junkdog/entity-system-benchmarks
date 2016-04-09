package com.artemis.system;

import com.artemis.Entity;
import com.artemis.Filter;
import com.artemis.component.Comp1;
import com.artemis.component.Comp4;
import com.artemis.component.Comp5;
import com.artemis.systems.EntityProcessingSystem;
import org.openjdk.jmh.infra.Blackhole;

public class CompSystemA extends EntityProcessingSystem {
	
	@SuppressWarnings("unchecked")
	public CompSystemA() {
		super(Filter.allComponents(Comp1.class, Comp4.class, Comp5.class));
	}

	@Override
	protected void process(Entity e) {
	}
}
