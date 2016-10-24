package com.github.esfbench.dustArtemis;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Setup;

import com.artemis.Aspect;
import com.artemis.World;
import com.github.esfbench.JmhSettings;
import com.github.esfbench.dustArtemis.component.PlainPosition;
import com.github.esfbench.dustArtemis.system.BaselinePositionSystem;
import com.github.esfbench.dustArtemis.system.BaselinePositionSystem2;
import com.github.esfbench.dustArtemis.system.BaselinePositionSystem3;
import com.github.esfbench.dustArtemis.system.CompositionManglerSystem;

public class TransmuterBenchmark extends JmhSettings
{
	private World engine;

	@Setup
	public void init ()
	{
		// Pass the config file with reasonable size for the benchmark.
		CfgUtils.setDustArtemisConfig( entityCount );

		engine = World.builder()
				.componentType( PlainPosition.class )
				.observer( BaselinePositionSystem::new, Aspect.all( PlainPosition.class ) )
				.observer( BaselinePositionSystem2::new, Aspect.all( PlainPosition.class ) )
				.observer( BaselinePositionSystem3::new, Aspect.all( PlainPosition.class ) )
				.observer( () -> new CompositionManglerSystem( SEED, entityCount ) )
				.build();
	}

	@Benchmark
	public void entity_edit ()
	{
		engine.process();
	}
}
