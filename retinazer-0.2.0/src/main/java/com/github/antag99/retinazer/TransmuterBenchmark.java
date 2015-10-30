package com.github.antag99.retinazer;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Setup;

import com.github.antag99.retinazer.system.BaselinePositionSystem;
import com.github.antag99.retinazer.system.BaselinePositionSystem2;
import com.github.antag99.retinazer.system.BaselinePositionSystem3;
import com.github.antag99.retinazer.system.CompositionManglerSystem;
import com.github.esfbench.JmhSettings;

public class TransmuterBenchmark extends JmhSettings {
    private Engine engine;

    @Setup
    public void init() {
        engine = new Engine(new EngineConfig()
                .addSystem(new BaselinePositionSystem())
                .addSystem(new BaselinePositionSystem2())
                .addSystem(new BaselinePositionSystem3())
                .addSystem(new CompositionManglerSystem(SEED, entityCount)));
    }

    @Benchmark
    public void entity_edit() {
        engine.update();
    }
}
