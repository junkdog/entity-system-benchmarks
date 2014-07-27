package com.artemis.system;


import org.openjdk.jmh.infra.Blackhole;

import com.artemis.component.PlainPosition;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

public class PlainPositionSystem extends IteratingSystem {

	Blackhole voidness = new Blackhole();
	
	@SuppressWarnings("unchecked")
	public PlainPositionSystem() {
		super(Family.getFamilyFor(PlainPosition.class));
	}
	
	@Override
	public void processEntity (com.badlogic.ashley.core.Entity e, float deltaTime) {
		PlainPosition pos = e.getComponent(PlainPosition.class);
		pos.x += 0.1f % 100000;
		pos.y -= 0.1f % 100000;
		
		voidness.consume(e);
	}
}
