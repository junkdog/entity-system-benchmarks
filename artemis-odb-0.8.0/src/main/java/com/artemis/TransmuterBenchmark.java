package com.artemis;

import com.artemis.system.BaselinePositionSystem;
import com.artemis.system.BaselinePositionSystem2;
import com.artemis.system.BaselinePositionSystem3;
import com.artemis.system.CompositionManglerSystem;
import com.github.esfbench.JmhSettings;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.OptionsBuilder;

public class TransmuterBenchmark extends JmhSettings {
	
	private World world;

	@Setup
	public void init() {
		world = new World();
		world.setSystem(new BaselinePositionSystem());
		world.setSystem(new BaselinePositionSystem2());
		world.setSystem(new BaselinePositionSystem3());
		world.setSystem(new CompositionManglerSystem(SEED, entityCount));
		world.initialize();
	}		
	
	@Benchmark
	public void entity_edit() {
		world.process();
	}

	public static void main(String[] args) throws Exception {
		new Runner(
			new OptionsBuilder()
				.include(TransmuterBenchmark.class.getName() + ".*")
				.param("entityCount", "1024", "4096")
				.build())
		.run();
	}
}
