package com.github.esfbench.ashley.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.github.esfbench.ashley.component.*;
import org.openjdk.jmh.infra.Blackhole;

public class CompSystemE extends IteratingSystem {

	Blackhole voidness = new Blackhole();

	@SuppressWarnings("unchecked")
	public CompSystemE() {
		super(Family.all(Comp10.class, Comp11.class, Comp12.class).exclude(Comp9.class).get());
	}

	@Override
	public void processEntity(Entity entity, float deltaTime) {
		voidness.consume(entity);
	}
}
