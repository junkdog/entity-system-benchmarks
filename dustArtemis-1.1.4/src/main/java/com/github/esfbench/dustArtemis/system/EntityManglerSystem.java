package com.github.esfbench.dustArtemis.system;//

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import com.artemis.ComponentHandler;
import com.artemis.EntityObserver;
import com.github.esfbench.dustArtemis.component.*;

public final class EntityManglerSystem extends EntityObserver
{

	int[] ids; // = new int[ENTITY_COUNT];
	int[] cmp; // = new int[ENTITY_COUNT];

	private ComponentHandler<Comp1> mComp1;
	private ComponentHandler<Comp2> mComp2;
	private ComponentHandler<Comp3> mComp3;
	private ComponentHandler<Comp4> mComp4;
	private ComponentHandler<Comp5> mComp5;
	private ComponentHandler<Comp6> mComp6;
	private ComponentHandler<Comp7> mComp7;
	private ComponentHandler<Comp8> mComp8;
	private ComponentHandler<Comp9> mComp9;
	private ComponentHandler<Comp10> mComp10;
	private ComponentHandler<Comp11> mComp11;
	private ComponentHandler<Comp12> mComp12;

	private static int ENTITY_COUNT = 0;
	private static int RENEW = 0;

	int counter;
	int index;

	private Random rng;
	int cmpIndex = 0;
	private int[] permutations;

	@SuppressWarnings( "unchecked" )
	public EntityManglerSystem ( long seed, int entityCount )
	{
		// 4096 entities = 256 compositions, 262144 = 2048
		int entityPermutations = (int) Math.sqrt( entityCount * 16 );
		rng = new Random( seed );
		ENTITY_COUNT = entityCount;
		RENEW = ENTITY_COUNT / 4;

		ArrayList<Integer> idsList = new ArrayList<Integer>();
		for ( int i = 0; ENTITY_COUNT > i; i++ )
			idsList.add( i );
		Collections.shuffle( idsList, rng );

		ids = new int[ENTITY_COUNT];
		for ( int i = 0; ids.length > i; i++ )
			ids[i] = idsList.get( i );
		// ids[i] = (int)(rng.nextFloat() * ENTITY_COUNT);

		permutations = new int[entityPermutations];

		cmp = new int[ENTITY_COUNT * 4];
		for ( int i = 0; cmp.length > i; i++ )
			cmp[i] = (int) (rng.nextFloat() * permutations.length);
	}

	@Override
	public void init ()
	{
		for ( int i = 0; permutations.length > i; i++ )
		{
			permutations[i] = 0;
			for ( int classIndex = 0, s = (int) (rng.nextFloat() * 7) + 3; s > classIndex; classIndex++ )
			{
				permutations[i] |= 1 << (int) (rng.nextFloat() * 9);
			}
		}

		for ( int i = 0; ENTITY_COUNT > i; i++ )
			createEntity();
	}

	@Override
	public void process ()
	{
		counter++;

		if ( counter % 2 == 1 )
		{
			for ( int i = 0; RENEW > i; i++ )
			{
				int e = ids[index++];
				world().deleteEntity( e );
				index = index % ENTITY_COUNT;
			}
		}
		else
		{
			for ( int i = 0; RENEW > i; i++ )
			{
				createEntity();
			}
		}
	}

	private final void createEntity ()
	{
		int permutation = permutations[cmp[cmpIndex++]];
		int entity = world().createEntity();
		if ( (permutation & 1) != 0 )
			mComp1.add( entity, new Comp1() );
		if ( (permutation & 2) != 0 )
			mComp2.add( entity, new Comp2() );
		if ( (permutation & 4) != 0 )
			mComp3.add( entity, new Comp3() );
		if ( (permutation & 8) != 0 )
			mComp4.add( entity, new Comp4() );
		if ( (permutation & 16) != 0 )
			mComp5.add( entity, new Comp5() );
		if ( (permutation & 32) != 0 )
			mComp6.add( entity, new Comp6() );
		if ( (permutation & 64) != 0 )
			mComp7.add( entity, new Comp7() );
		if ( (permutation & 128) != 0 )
			mComp8.add( entity, new Comp8() );
		if ( (permutation & 256) != 0 )
			mComp9.add( entity, new Comp9() );
		if ( (permutation & 512) != 0 )
			mComp10.add( entity, new Comp10() );
		if ( (permutation & 1024) != 0 )
			mComp11.add( entity, new Comp11() );
		if ( (permutation & 2048) != 0 )
			mComp12.add( entity, new Comp12() );
		if ( cmpIndex == cmp.length )
			cmpIndex = 0;
	}
}
