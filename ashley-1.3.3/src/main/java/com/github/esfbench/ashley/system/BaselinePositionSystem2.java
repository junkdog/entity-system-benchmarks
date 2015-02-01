package com.github.esfbench.ashley.system;

import org.openjdk.jmh.infra.Blackhole;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.github.esfbench.ashley.component.PlainPosition;

public class BaselinePositionSystem2 extends IteratingSystem {
	
	Blackhole voidness = new Blackhole();
	
	@SuppressWarnings("unchecked")
	public BaselinePositionSystem2() {
		super(Family.all(PlainPosition.class).get());
	}

	@Override
	public void processEntity(Entity entity, float deltaTime) {
		voidness.consume(entity);
		voidness.consume(deltaTime + 1);
	}
}
