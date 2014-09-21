package com.github.esfbench.ashley.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.github.esfbench.ashley.component.Comp2;
import com.github.esfbench.ashley.component.Comp8;
import com.github.esfbench.ashley.component.Comp9;

public class CompSystemB extends IteratingSystem {
	
//	Blackhole voidness = new Blackhole();
	
	@SuppressWarnings("unchecked")
	public CompSystemB() {
		super(Family.getFamilyFor(Comp2.class, Comp8.class, Comp9.class));
	}

	@Override
	public void processEntity(Entity entity, float deltaTime) {}
}
