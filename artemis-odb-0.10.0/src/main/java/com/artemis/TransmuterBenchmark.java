package com.artemis;

import com.artemis.system.*;
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
		worldEdit = new World();
		worldEdit.setSystem(new BaselinePositionSystem());
		worldEdit.setSystem(new BaselinePositionSystem2());
		worldEdit.setSystem(new BaselinePositionSystem3());
		worldEdit.setSystem(new CompositionManglerSystem(SEED, entityCount));
		worldEdit.initialize();

		worldTransmuter = new World();
		worldTransmuter.setSystem(new BaselinePositionSystem());
		worldTransmuter.setSystem(new BaselinePositionSystem2());
		worldTransmuter.setSystem(new BaselinePositionSystem3());
		worldTransmuter.setSystem(new TransmutingCompositionManglerSystem(SEED, entityCount));
		worldTransmuter.initialize();
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
