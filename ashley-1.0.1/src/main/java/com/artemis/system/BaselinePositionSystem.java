package com.artemis.system;

import org.openjdk.jmh.infra.Blackhole;

import com.artemis.component.PlainPosition;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

public class BaselinePositionSystem extends IteratingSystem {
	
	Blackhole voidness = new Blackhole();
	
	@SuppressWarnings("unchecked")
	public BaselinePositionSystem() {
		super(Family.getFamilyFor(PlainPosition.class));
	}

	@Override
	public void processEntity(Entity entity, float deltaTime) {
		voidness.consume(entity);
	}
}
