package com.github.esfbench.dustArtemis.system;

import java.util.Random;

import com.artemis.ComponentHandler;
import com.artemis.EntityObserver;
import com.github.esfbench.dustArtemis.component.PlainPosition;
import com.github.esfbench.dustArtemis.component.PlainStructComponentA;

public final class EntityDeleterSystem extends EntityObserver
{
	int[] ids; // = new int[ENTITY_COUNT];

	private static int ENTITY_COUNT = 0;

	int counter;
	int index;

	private ComponentHandler<PlainPosition> m1;
	private ComponentHandler<PlainStructComponentA> m2;

	public EntityDeleterSystem ( long seed, int entityCount )
	{
		Random rng = new Random( seed );
		ENTITY_COUNT = entityCount;
		ids = new int[ENTITY_COUNT];
		for ( int i = 0; ids.length > i; i++ )
			ids[i] = (int) (rng.nextFloat() * ENTITY_COUNT);
	}

	@Override
	public void init ()
	{
		for ( int i = 0; ENTITY_COUNT > i; i++ )
			createEntity();
	}

	@Override
	public void process ()
	{
		counter++;
		if ( counter == 100 )
		{
			world().deleteEntity( ids[index++] );
			index = index % ENTITY_COUNT;
			counter = 0;
		}
		else if ( counter == 1 )
		{ // need to wait one round to reclaim entities
			createEntity();
		}
	}

	private final void createEntity ()
	{
		int e = world().createEntity();
		m1.add( e, new PlainPosition() );
		m2.add( e, new PlainStructComponentA() );
		world().addEntity( e );
	}
}
