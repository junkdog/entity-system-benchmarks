package com.github.esfbench.ashley.system;

import static com.badlogic.ashley.core.ComponentType.getBitsFor;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.utils.Bits;
import com.github.esfbench.ashley.component.Comp1;
import com.github.esfbench.ashley.component.Comp2;
import com.github.esfbench.ashley.component.Comp7;
import com.github.esfbench.ashley.component.Comp9;
import org.openjdk.jmh.infra.Blackhole;

public class CompSystemC extends IteratingSystem {

	@SuppressWarnings("unchecked")
	public CompSystemC() {
		super(Family.all(Comp1.class, Comp7.class, Comp9.class).one(Comp2.class).get());
	}

	@Override
	public void processEntity(Entity entity, float deltaTime) {
	}
}
