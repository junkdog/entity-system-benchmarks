package com.github.antag99.retinazer;

import com.github.antag99.retinazer.system.*;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Setup;

import com.github.esfbench.JmhSettings;

public class InsertRemoveBenchmark extends JmhSettings {
    private Engine engine;

    @Setup
    public void init() throws Exception {
        engine = new Engine(new EngineConfig()
                .addSystem(new EntityManglerSystem(SEED, entityCount))
                .addSystem(new CompSystemA())
                .addSystem(new CompSystemB())
                .addSystem(new CompSystemC())
                .addSystem(new CompSystemD())
                .addSystem(new CompSystemE()));
    }

    @Benchmark
    public void insert_remove() {
        engine.update();
    }
}
