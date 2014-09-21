package com.github.esfbench.ashley.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.github.esfbench.ashley.component.Comp1;
import com.github.esfbench.ashley.component.Comp2;
import com.github.esfbench.ashley.component.Comp7;
import com.github.esfbench.ashley.component.Comp9;

public class CompSystemC extends IteratingSystem {
	
//	Blackhole voidness = new Blackhole();
	
	@SuppressWarnings("unchecked")
	public CompSystemC() {
		super(Family.getFamilyFor(Comp1.class, Comp7.class, Comp9.class)); //.exclude(Comp2.class));
	}

	@Override
	public void processEntity(Entity entity, float deltaTime) {}
}
