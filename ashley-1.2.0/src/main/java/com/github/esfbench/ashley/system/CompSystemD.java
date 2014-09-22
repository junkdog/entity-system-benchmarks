package com.github.esfbench.ashley.system;

import static com.badlogic.ashley.core.ComponentType.getBitsFor;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.utils.Bits;
import com.github.esfbench.ashley.component.Comp6;
import com.github.esfbench.ashley.component.Comp7;
import com.github.esfbench.ashley.component.Comp8;
import com.github.esfbench.ashley.component.Comp9;

public class CompSystemD extends IteratingSystem {
	
//	Blackhole voidness = new Blackhole();
	
	@SuppressWarnings("unchecked")
	public CompSystemD() {
		super(Family.getFor(
				getBitsFor(Comp6.class, Comp7.class, Comp8.class),
				getBitsFor(Comp9.class), new Bits()));
	}

	@Override
	public void processEntity(Entity entity, float deltaTime) {}
}
