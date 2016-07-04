package com.github.antag99.retinazer.system;

import org.openjdk.jmh.infra.Blackhole;

import com.github.antag99.retinazer.EntityProcessorSystem;
import com.github.antag99.retinazer.Family;
import com.github.antag99.retinazer.Mapper;
import com.github.antag99.retinazer.SkipWire;
import com.github.antag99.retinazer.component.PlainPosition;

public class PlainPositionSystem3 extends EntityProcessorSystem {
    Mapper<PlainPosition> positionMapper;

    @SkipWire
    Blackhole voidness = new Blackhole();

    @SuppressWarnings("unchecked")
    public PlainPositionSystem3() {
        super(Family.with(PlainPosition.class));
    }

    @Override
    protected void process(int e) {
        PlainPosition pos = positionMapper.get(e);
        pos.x += 0.5f;
        pos.y -= 0.5f;

        voidness.consume(e);
    }
}
