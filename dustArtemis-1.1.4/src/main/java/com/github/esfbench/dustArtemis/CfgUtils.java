package com.github.esfbench.dustArtemis;

import java.util.Properties;

import com.artemis.DAConstants;

public final class CfgUtils
{
	private CfgUtils ()
	{
		throw new RuntimeException( "Can't create this class." );
	}

	private static final int ENTITY_LEVEL_1 = 5000;
	private static final int ENTITY_LEVEL_2 = 17000;
	private static final int ENTITY_LEVEL_3 = 66000;
	private static final int ENTITY_LEVEL_4 = 263000;

	public static void setDustArtemisConfig ( int entityCount )
	{
		final Properties props = System.getProperties();

		if ( putIfBelow( entityCount, ENTITY_LEVEL_1, props ) )
		{
			return;
		}

		if ( putIfBelow( entityCount, ENTITY_LEVEL_2, props ) )
		{
			return;
		}

		if ( putIfBelow( entityCount, ENTITY_LEVEL_3, props ) )
		{
			return;
		}
		// Always set this one if it gets to it, biggest we have.
		putIfBelow( Integer.MIN_VALUE, ENTITY_LEVEL_4, props );
	}

	private static final boolean putIfBelow ( int count, int threshold, Properties dest )
	{
		if ( count < threshold )
		{
			dest.put( DAConstants.CFG_FILE_PROPERTY_NAME, "./dustArtemis_" + threshold + ".cfg" );
			return true;
		}

		return false;
	}

}
