package com.github.esfbench.ashley.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.github.esfbench.ashley.component.Comp2;
import com.github.esfbench.ashley.component.Comp8;
import com.github.esfbench.ashley.component.Comp9;
import org.openjdk.jmh.infra.Blackhole;

public class CompSystemB extends IteratingSystem {

	@SuppressWarnings("unchecked")
	public CompSystemB() {
		super(Family.all(Comp2.class, Comp8.class, Comp9.class).get());
	}

	@Override
	public void processEntity(Entity entity, float deltaTime) {
	}
}
