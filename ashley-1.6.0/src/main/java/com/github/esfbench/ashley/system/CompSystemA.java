package com.github.esfbench.ashley.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.github.esfbench.ashley.component.Comp1;
import com.github.esfbench.ashley.component.Comp4;
import com.github.esfbench.ashley.component.Comp5;

public class CompSystemA extends IteratingSystem {

	@SuppressWarnings("unchecked")
	public CompSystemA() {
		super(Family.all(Comp1.class, Comp4.class, Comp5.class).get());
	}

	@Override
	public void processEntity(Entity entity, float deltaTime) {
	}
}
