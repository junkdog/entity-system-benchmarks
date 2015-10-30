package com.github.antag99.retinazer.system;//

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectSet;
import com.github.antag99.retinazer.Component;
import com.github.antag99.retinazer.EntitySystem;
import com.github.antag99.retinazer.Mapper;
import com.github.antag99.retinazer.SkipWire;
import com.github.antag99.retinazer.component.Comp1;
import com.github.antag99.retinazer.component.Comp2;
import com.github.antag99.retinazer.component.Comp3;
import com.github.antag99.retinazer.component.Comp4;
import com.github.antag99.retinazer.component.Comp5;
import com.github.antag99.retinazer.component.Comp6;
import com.github.antag99.retinazer.component.Comp7;
import com.github.antag99.retinazer.component.Comp8;
import com.github.antag99.retinazer.component.Comp9;

@SkipWire
public final class EntityManglerSystem extends EntitySystem {

    int[] ids; // = new int[ENTITY_COUNT];
    int[] cmp; // = new int[ENTITY_COUNT];

    private Array<Class<? extends Component>> types;

    private static int ENTITY_COUNT = 0;
    private static int RENEW = 0;

    int counter;
    int index;

    private Random rng;

    @SuppressWarnings("unchecked")
    // public EntityManglerSystem(long seed, int entityCount, int entityPermutations) {
    public EntityManglerSystem(long seed, int entityCount, int entityPermutations) {
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

        types = new Array<Class<? extends Component>>();
        types.add(Comp1.class);
        types.add(Comp2.class);
        types.add(Comp3.class);
        types.add(Comp4.class);
        types.add(Comp5.class);
        types.add(Comp6.class);
        types.add(Comp7.class);
        types.add(Comp8.class);
        types.add(Comp9.class);

        permutations = new Mapper<?>[entityPermutations][];

        cmp = new int[ENTITY_COUNT * 4];
        for (int i = 0; cmp.length > i; i++)
            cmp[i] = (int) (rng.nextFloat() * permutations.length);
    }

    @Override
    protected void initialize() {
        for (int i = 0; permutations.length > i; i++) {
            ObjectSet<Mapper<?>> set = new ObjectSet<>();
            for (int classIndex = 0, s = (int) (rng.nextFloat() * 7) + 3; s > classIndex; classIndex++) {
                set.add(engine.getMapper(types.get((int) (rng.nextFloat() * types.size))));
            }

            permutations[i] = set.iterator().toArray().toArray(Mapper.class);
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

    int cmpIndex = 0;

    private Mapper<?>[][] permutations;

    private final void createEntity() {
        Mapper<?>[] mappers = permutations[cmp[cmpIndex++]];
        int entity = engine.createEntity();
        for (int i = 0; i < mappers.length; i++)
            mappers[i].create(entity);
        if (cmpIndex == cmp.length)
            cmpIndex = 0;
    }
}
