package com.github.esfbench.dustArtemis.system;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import com.artemis.ComponentHandler;
import com.artemis.EntityObserver;
import com.github.esfbench.dustArtemis.component.PlainPosition;

public final class CompositionManglerSystem extends EntityObserver
{
	int[] ids; // = new int[ENTITY_COUNT];

	private static int ENTITY_COUNT = 0;
	private static int RENEW = 0;

	private int index;

	private Random rng;

	ComponentHandler<PlainPosition> positionMapper;

	public CompositionManglerSystem ( long seed, int entityCount )
	{
		rng = new Random( seed );
		ENTITY_COUNT = entityCount;
		RENEW = ENTITY_COUNT / 4;

		ids = new int[ENTITY_COUNT];

	}

	@Override
	public void init ()
	{
		ArrayList<Integer> idsList = new ArrayList<>();
		for ( int i = 0; ENTITY_COUNT > i; i++ )
			idsList.add( Integer.valueOf( world().createEntity() ) );

		Collections.shuffle( idsList, rng );

		for ( int i = 0; ids.length > i; i++ )
			ids[i] = idsList.get( i ).intValue();
	}

	@Override
	public void process ()
	{
		for ( int i = 0; RENEW > i; i++ )
		{
			int e = ids[index++];
			if ( positionMapper.has( e ) )
			{
				positionMapper.remove( e );
			}
			else
			{
				positionMapper.add( e, new PlainPosition() );
			}
			index = index % ENTITY_COUNT;
		}
	}
}
