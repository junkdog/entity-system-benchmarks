package com.github.antag99.retinazer.system;

import org.openjdk.jmh.infra.Blackhole;

import com.github.antag99.retinazer.EntityProcessorSystem;
import com.github.antag99.retinazer.Family;
import com.github.antag99.retinazer.SkipWire;
import com.github.antag99.retinazer.component.Comp2;
import com.github.antag99.retinazer.component.Comp8;
import com.github.antag99.retinazer.component.Comp9;

public class CompSystemB extends EntityProcessorSystem {
    @SkipWire
    Blackhole voidness = new Blackhole();

    @SuppressWarnings("unchecked")
    public CompSystemB() {
        super(Family.with(Comp2.class, Comp8.class, Comp9.class));
    }

    @Override
    protected void process(int e) {
        voidness.consume(e);
    }
}
