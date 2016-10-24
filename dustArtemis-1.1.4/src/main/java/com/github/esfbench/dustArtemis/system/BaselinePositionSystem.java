package com.github.esfbench.dustArtemis.system;

import org.openjdk.jmh.infra.Blackhole;

import com.artemis.Aspect;
import com.artemis.EntitySystem;
import com.artemis.utils.ImmutableIntBag;

public class BaselinePositionSystem extends EntitySystem
{
	Blackhole voidness = new Blackhole();

	public BaselinePositionSystem ( Aspect aspect )
	{
		super( aspect );
	}

	@Override
	protected void processEntities ( ImmutableIntBag arg0 )
	{
		for ( int i = 0; i < arg0.size(); ++i )
		{
			voidness.consume( arg0.getUnsafe( i ) );
		}
	}
}
