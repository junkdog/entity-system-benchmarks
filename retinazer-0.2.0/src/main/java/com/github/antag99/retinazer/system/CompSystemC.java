package com.github.antag99.retinazer.system;

import org.openjdk.jmh.infra.Blackhole;

import com.github.antag99.retinazer.EntityProcessorSystem;
import com.github.antag99.retinazer.Family;
import com.github.antag99.retinazer.SkipWire;
import com.github.antag99.retinazer.component.Comp1;
import com.github.antag99.retinazer.component.Comp2;
import com.github.antag99.retinazer.component.Comp7;
import com.github.antag99.retinazer.component.Comp9;

public class CompSystemC extends EntityProcessorSystem {
    @SkipWire
    Blackhole voidness = new Blackhole();

    @SuppressWarnings("unchecked")
    public CompSystemC() {
        super(Family.with(Comp1.class, Comp7.class, Comp9.class).exclude(Comp2.class));
    }

    @Override
    protected void process(int e) {
        voidness.consume(e);
    }
}
