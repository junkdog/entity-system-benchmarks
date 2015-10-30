package com.github.antag99.retinazer;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Setup;

import com.github.antag99.retinazer.component.PlainPosition;
import com.github.antag99.retinazer.component.PlainStructComponentA;
import com.github.antag99.retinazer.system.BaselinePositionSystem;
import com.github.antag99.retinazer.system.BaselinePositionSystem2;
import com.github.antag99.retinazer.system.BaselinePositionSystem3;
import com.github.antag99.retinazer.system.EntityDeleterSystem;
import com.github.esfbench.JmhSettings;

public class BaselineBenchmark extends JmhSettings {
    private Engine engine;

    @Setup
    public void init() {
        engine = new Engine(new EngineConfig()
                .addSystem(new BaselinePositionSystem())
                .addSystem(new BaselinePositionSystem2())
                .addSystem(new BaselinePositionSystem3())
                .addSystem(new EntityDeleterSystem(
                        SEED, entityCount, PlainPosition.class, PlainStructComponentA.class)));
    }

    @Benchmark
    public void baseline() {
        engine.update();
    }
}
