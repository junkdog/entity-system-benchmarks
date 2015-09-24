package com.artemis.system.iterating;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.component.Comp1;
import com.artemis.component.Comp2;
import com.artemis.component.Comp7;
import com.artemis.component.Comp9;
import com.artemis.systems.IteratingSystem;
import org.openjdk.jmh.infra.Blackhole;

public class CompSystemC extends IteratingSystem {
	
	Blackhole voidness = new Blackhole();
	
	@SuppressWarnings("unchecked")
	public CompSystemC() {
		super(Aspect.all(Comp1.class, Comp7.class, Comp9.class).exclude(Comp2.class));
	}

	@Override
	protected void process(int e) {
		voidness.consume(e);
	}
}
