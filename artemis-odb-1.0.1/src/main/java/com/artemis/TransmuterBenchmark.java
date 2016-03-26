package com.artemis;

import com.artemis.system.iterating.*;
import com.github.esfbench.JmhSettings;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.OptionsBuilder;

public class TransmuterBenchmark extends JmhSettings {
	
	private World worldEdit;
	private World worldTransmuter;

	@Setup
	public void init() {
		worldEdit = new World(new WorldConfiguration()
				.setSystem(new BaselinePositionSystem())
				.setSystem(new BaselinePositionSystem2())
				.setSystem(new BaselinePositionSystem3())
				.setSystem(new CompositionManglerSystem(SEED, entityCount)));

		worldTransmuter = new World(new WorldConfiguration()
				.setSystem(new BaselinePositionSystem())
				.setSystem(new BaselinePositionSystem2())
				.setSystem(new BaselinePositionSystem3())
				.setSystem(new TransmutingCompositionManglerSystem(SEED, entityCount)));
	}
	
	@Benchmark
	public void entity_edit() {
		worldEdit.process();
	}

	@Benchmark
	public void transmuter() {
		worldTransmuter.process();
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
