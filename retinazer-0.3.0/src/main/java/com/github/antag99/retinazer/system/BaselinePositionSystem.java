package com.github.antag99.retinazer.system;

import org.openjdk.jmh.infra.Blackhole;

import com.github.antag99.retinazer.EntityProcessorSystem;
import com.github.antag99.retinazer.Family;
import com.github.antag99.retinazer.SkipWire;
import com.github.antag99.retinazer.component.PlainPosition;

public class BaselinePositionSystem extends EntityProcessorSystem {
    @SkipWire
    Blackhole voidness = new Blackhole();

    @SuppressWarnings("unchecked")
    public BaselinePositionSystem() {
        super(Family.with(PlainPosition.class));
    }

    @Override
    protected void process(int e) {
        voidness.consume(e);
    }
}
