package com.github.antag99.retinazer.system;//

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import com.github.antag99.retinazer.EntitySystem;
import com.github.antag99.retinazer.Mapper;
import com.github.antag99.retinazer.SkipWire;
import com.github.antag99.retinazer.component.Comp1;
import com.github.antag99.retinazer.component.Comp10;
import com.github.antag99.retinazer.component.Comp11;
import com.github.antag99.retinazer.component.Comp12;
import com.github.antag99.retinazer.component.Comp2;
import com.github.antag99.retinazer.component.Comp3;
import com.github.antag99.retinazer.component.Comp4;
import com.github.antag99.retinazer.component.Comp5;
import com.github.antag99.retinazer.component.Comp6;
import com.github.antag99.retinazer.component.Comp7;
import com.github.antag99.retinazer.component.Comp8;
import com.github.antag99.retinazer.component.Comp9;

public final class EntityManglerSystem extends EntitySystem {

    @SkipWire
    int[] ids; // = new int[ENTITY_COUNT];
    @SkipWire
    int[] cmp; // = new int[ENTITY_COUNT];

    private static int ENTITY_COUNT = 0;
    private static int RENEW = 0;

    @SkipWire
    int counter;
    @SkipWire
    int index;

    @SkipWire
    private Random rng;

    private Mapper<Comp1> mComp1;
    private Mapper<Comp2> mComp2;
    private Mapper<Comp3> mComp3;
    private Mapper<Comp4> mComp4;
    private Mapper<Comp5> mComp5;
    private Mapper<Comp6> mComp6;
    private Mapper<Comp7> mComp7;
    private Mapper<Comp8> mComp8;
    private Mapper<Comp9> mComp9;
    private Mapper<Comp10> mComp10;
    private Mapper<Comp11> mComp11;
    private Mapper<Comp12> mComp12;

    @SkipWire
    int cmpIndex = 0;

    @SkipWire
    private int[] permutations;

    public EntityManglerSystem(long seed, int entityCount) {
	    // 4096 entities = 256 compositions, 262144 = 2048
	    int entityPermutations = (int)Math.sqrt(entityCount * 16);
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
        // ids[i] = (int)(rng.nextFloat() * ENTITY_COUNT);

        permutations = new int[entityPermutations];

        cmp = new int[ENTITY_COUNT * 4];
        for (int i = 0; cmp.length > i; i++)
            cmp[i] = (int) (rng.nextFloat() * permutations.length);
    }

    @Override
    protected void initialize() {
        for (int i = 0; permutations.length > i; i++) {
            permutations[i] = 0;
            for (int classIndex = 0, s = (int) (rng.nextFloat() * 7) + 3; s > classIndex; classIndex++) {
                permutations[i] |= 1 << (int) (rng.nextFloat() * 9);
            }
        }

        for (int i = 0; ENTITY_COUNT > i; i++)
            createEntity();
    }

    @Override
    protected void update() {
        counter++;

        if (counter % 2 == 1) {
            for (int i = 0; RENEW > i; i++) {
                int e = ids[index++];
                engine.destroyEntity(e);
                index = index % ENTITY_COUNT;
            }
        } else {
            for (int i = 0; RENEW > i; i++) {
                createEntity();
            }
        }
    }

    private final void createEntity() {
        int permutation = permutations[cmp[cmpIndex++]];
        int entity = engine.createEntity();
        if ((permutation & 1) != 0)
            mComp1.create(entity);
        if ((permutation & 2) != 0)
            mComp2.create(entity);
        if ((permutation & 4) != 0)
            mComp3.create(entity);
        if ((permutation & 8) != 0)
            mComp4.create(entity);
        if ((permutation & 16) != 0)
            mComp5.create(entity);
        if ((permutation & 32) != 0)
            mComp6.create(entity);
        if ((permutation & 64) != 0)
            mComp7.create(entity);
        if ((permutation & 128) != 0)
            mComp8.create(entity);
        if ((permutation & 256) != 0)
            mComp9.create(entity);
        if ((permutation & 512) != 0)
            mComp10.create(entity);
        if ((permutation & 1024) != 0)
            mComp11.create(entity);
        if ((permutation & 2048) != 0)
            mComp12.create(entity);
        if (cmpIndex == cmp.length)
            cmpIndex = 0;
    }
}
