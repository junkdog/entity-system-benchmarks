package com.github.esfbench.dustArtemis.system;

import org.openjdk.jmh.infra.Blackhole;

import com.artemis.Aspect;
import com.artemis.ComponentHandler;
import com.artemis.EntitySystem;
import com.artemis.utils.ImmutableIntBag;
import com.github.esfbench.dustArtemis.component.PlainPosition;

public class PlainPositionSystem3 extends EntitySystem
{

	public PlainPositionSystem3 ( Aspect aspect )
	{
		super( aspect );
	}

	ComponentHandler<PlainPosition> positionMapper;

	Blackhole voidness = new Blackhole();

	@Override
	protected void processEntities ( ImmutableIntBag entities )
	{
		for ( int i = 0; i < entities.size(); i++ )
		{
			final int e = entities.getUnsafe( i );
			PlainPosition pos = positionMapper.get( e );
			pos.x += 0.5f;
			pos.y -= 0.5f;
			voidness.consume( e );
		}
	}
}
