package com.github.antag99.retinazer.system;

import org.openjdk.jmh.infra.Blackhole;

import com.github.antag99.retinazer.EntityProcessorSystem;
import com.github.antag99.retinazer.Family;
import com.github.antag99.retinazer.SkipWire;
import com.github.antag99.retinazer.component.Comp6;
import com.github.antag99.retinazer.component.Comp7;
import com.github.antag99.retinazer.component.Comp8;
import com.github.antag99.retinazer.component.Comp9;

public class CompSystemD extends EntityProcessorSystem {
    @SkipWire
    Blackhole voidness = new Blackhole();

    @SuppressWarnings("unchecked")
    public CompSystemD() {
        super(Family.with(Comp6.class, Comp7.class, Comp8.class).exclude(Comp9.class));
    }

    @Override
    protected void process(int e) {
        voidness.consume(e);
    }
}
