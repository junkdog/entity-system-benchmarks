package com.artemis.system;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.Filter;
import com.artemis.component.Comp6;
import com.artemis.component.Comp7;
import com.artemis.component.Comp8;
import com.artemis.component.Comp9;
import com.artemis.systems.EntityProcessingSystem;
import org.openjdk.jmh.infra.Blackhole;

public class CompSystemD extends EntityProcessingSystem {
	
	@SuppressWarnings("unchecked")
	public CompSystemD() {
		super(Filter.allComponents(Comp6.class, Comp7.class, Comp8.class).exclude(Comp9.class));
	}

	@Override
	protected void process(Entity e) {
	}
}
