package com.github.esfbench.ashley.system;


import org.openjdk.jmh.infra.Blackhole;

import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.github.esfbench.ashley.component.PlainPosition;

public class PlainPositionSystem3 extends IteratingSystem {

	Blackhole voidness = new Blackhole();
	
	@SuppressWarnings("unchecked")
	public PlainPositionSystem3() {
		super(Family.getFamilyFor(PlainPosition.class));
	}
	
	@Override
	public void processEntity (com.badlogic.ashley.core.Entity e, float deltaTime) {
		PlainPosition pos = e.getComponent(PlainPosition.class);
		pos.x += 0.5f;
		pos.y -= 0.5f;
		
		voidness.consume(e);
	}
}
