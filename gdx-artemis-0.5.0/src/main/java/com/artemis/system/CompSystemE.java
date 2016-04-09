package com.artemis.system;

import com.artemis.Entity;
import com.artemis.Filter;
import com.artemis.component.*;
import com.artemis.systems.EntityProcessingSystem;
import org.openjdk.jmh.infra.Blackhole;

public class CompSystemE extends EntityProcessingSystem {

	Blackhole voidness = new Blackhole();

	@SuppressWarnings("unchecked")
	public CompSystemE() {
		super(Filter.allComponents(Comp10.class, Comp11.class, Comp12.class).exclude(Comp9.class));
	}

	@Override
	protected void process(Entity e) {
		voidness.consume(e);
	}
}
