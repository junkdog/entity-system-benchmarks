package com.github.esfbench.ashley.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.github.esfbench.ashley.component.Comp1;
import com.github.esfbench.ashley.component.Comp4;
import com.github.esfbench.ashley.component.Comp5;

public class CompSystemA extends IteratingSystem {
	
//	Blackhole voidness = new Blackhole();
	
	@SuppressWarnings("unchecked")
	public CompSystemA() {
		super(Family.getFor(Comp1.class, Comp4.class, Comp5.class));
	}

	@Override
	public void processEntity(Entity entity, float deltaTime) {}
}
