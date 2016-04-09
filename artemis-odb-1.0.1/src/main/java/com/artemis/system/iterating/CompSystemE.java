package com.artemis.system.iterating;

import com.artemis.Aspect;
import com.artemis.component.*;
import com.artemis.systems.IteratingSystem;
import org.openjdk.jmh.infra.Blackhole;

public class CompSystemE extends IteratingSystem {

	Blackhole voidness = new Blackhole();

	@SuppressWarnings("unchecked")
	public CompSystemE() {
		super(Aspect.all(Comp10.class, Comp11.class, Comp12.class).exclude(Comp9.class));
	}

	@Override
	protected void process(int e) {
		voidness.consume(e);
	}
}
