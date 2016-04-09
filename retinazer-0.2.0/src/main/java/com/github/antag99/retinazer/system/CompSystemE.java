package com.github.antag99.retinazer.system;

import com.github.antag99.retinazer.EntityProcessorSystem;
import com.github.antag99.retinazer.Family;
import com.github.antag99.retinazer.SkipWire;
import com.github.antag99.retinazer.component.*;
import org.openjdk.jmh.infra.Blackhole;

public class CompSystemE extends EntityProcessorSystem {
    @SkipWire
    Blackhole voidness = new Blackhole();

    @SuppressWarnings("unchecked")
    public CompSystemE() {
        super(Family.with(Comp10.class, Comp11.class, Comp12.class).exclude(Comp9.class));
    }

    @Override
    protected void process(int e) {
        voidness.consume(e);
    }
}
