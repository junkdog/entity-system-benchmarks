package com.github.antag99.retinazer;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Setup;

import com.github.antag99.retinazer.system.CompSystemA;
import com.github.antag99.retinazer.system.CompSystemB;
import com.github.antag99.retinazer.system.CompSystemC;
import com.github.antag99.retinazer.system.CompSystemD;
import com.github.antag99.retinazer.system.EntityManglerSystem;
import com.github.esfbench.JmhSettings;

public class InsertRemoveBenchmark extends JmhSettings {
    private Engine engine;

    @Setup
    public void init() throws Exception {
        engine = new Engine(new EngineConfig()
                .addSystem(new EntityManglerSystem(SEED, entityCount, 20))
                .addSystem(new CompSystemA())
                .addSystem(new CompSystemB())
                .addSystem(new CompSystemC())
                .addSystem(new CompSystemD()));
    }

    @Benchmark
    public void insert_remove() {
        engine.update();
    }
}
