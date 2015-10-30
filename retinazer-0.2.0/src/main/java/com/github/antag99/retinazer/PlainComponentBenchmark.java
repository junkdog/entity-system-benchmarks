package com.github.antag99.retinazer;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Setup;

import com.github.antag99.retinazer.component.PlainPosition;
import com.github.antag99.retinazer.component.PlainStructComponentA;
import com.github.antag99.retinazer.system.EntityDeleterSystem;
import com.github.antag99.retinazer.system.PlainPositionSystem;
import com.github.antag99.retinazer.system.PlainPositionSystem2;
import com.github.antag99.retinazer.system.PlainPositionSystem3;
import com.github.esfbench.JmhSettings;

public class PlainComponentBenchmark extends JmhSettings {
    private Engine engine;

    @Setup
    public void init() {
        engine = new Engine(new EngineConfig()
                .addSystem(new PlainPositionSystem())
                .addSystem(new PlainPositionSystem2())
                .addSystem(new PlainPositionSystem3())
                .addSystem(new EntityDeleterSystem(
                        JmhSettings.SEED, entityCount, PlainPosition.class, PlainStructComponentA.class)));
    }

    @Benchmark
    public void plain() {
        engine.update();
    }
}
