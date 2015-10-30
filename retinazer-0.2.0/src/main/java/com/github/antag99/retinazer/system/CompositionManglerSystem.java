package com.github.antag99.retinazer.system;//

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import com.github.antag99.retinazer.EntitySystem;
import com.github.antag99.retinazer.Mapper;
import com.github.antag99.retinazer.SkipWire;
import com.github.antag99.retinazer.Wire;
import com.github.antag99.retinazer.component.PlainPosition;

@SkipWire
public final class CompositionManglerSystem extends EntitySystem {
    // NOTE: Ugly, as it depends on ID assignment order, which is not defined
    // by retinazer. Works with 0.2.0 though.

    int[] ids; // = new int[ENTITY_COUNT];

    private static int ENTITY_COUNT = 0;
    private static int RENEW = 0;

    private int index;

    private Random rng;

    @Wire
    Mapper<PlainPosition> positionMapper;

    @SuppressWarnings("unchecked")
    public CompositionManglerSystem(long seed, int entityCount) {
        rng = new Random(seed);
        ENTITY_COUNT = entityCount;
        RENEW = ENTITY_COUNT / 4;

        ArrayList<Integer> idsList = new ArrayList<Integer>();
        for (int i = 0; ENTITY_COUNT > i; i++)
            idsList.add(i);
        Collections.shuffle(idsList, rng);

        ids = new int[ENTITY_COUNT];
        for (int i = 0; ids.length > i; i++)
            ids[i] = idsList.get(i);
    }

    @Override
    protected void initialize() {
        for (int i = 0; ENTITY_COUNT > i; i++)
            engine.createEntity();
    }

    @Override
    protected void update() {
        for (int i = 0; RENEW > i; i++) {
            int e = ids[index++];
            if (positionMapper.has(e)) {
                positionMapper.remove(e);
            } else {
                positionMapper.create(e);
            }
            index = index % ENTITY_COUNT;
        }
    }
}
