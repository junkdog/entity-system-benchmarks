package com.artemis.system.iterating;


import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.component.PooledPosition;
import com.artemis.systems.IteratingSystem;
import org.openjdk.jmh.infra.Blackhole;

@Wire
public class PooledPositionSystem3 extends IteratingSystem {

	Blackhole voidness = new Blackhole();
	ComponentMapper<PooledPosition> positionMapper;
	
	@SuppressWarnings("unchecked")
	public PooledPositionSystem3() {
		super(Aspect.all(PooledPosition.class));
	}

	@Override
	protected void process(int e) {
		PooledPosition pos = positionMapper.get(e);
		pos.x += 0.5f;
		pos.y -= 0.5f;
		
		voidness.consume(e);
	}
}
