package com.artemis.system.iterating;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.component.Comp2;
import com.artemis.component.Comp8;
import com.artemis.component.Comp9;
import com.artemis.systems.IteratingSystem;
import org.openjdk.jmh.infra.Blackhole;

public class CompSystemB extends IteratingSystem {
	
	Blackhole voidness = new Blackhole();
	
	@SuppressWarnings("unchecked")
	public CompSystemB() {
		super(Aspect.all(Comp2.class, Comp8.class, Comp9.class));
	}

	@Override
	protected void process(int e) {
		voidness.consume(e);
	}
}
