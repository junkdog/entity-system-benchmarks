package com.github.antag99.retinazer.system;

import java.util.Random;

import com.github.antag99.retinazer.Component;
import com.github.antag99.retinazer.EntitySystem;
import com.github.antag99.retinazer.Mapper;
import com.github.antag99.retinazer.SkipWire;

@SkipWire
public final class EntityDeleterSystem extends EntitySystem {
    int[] ids; // = new int[ENTITY_COUNT];

    private static int ENTITY_COUNT = 0;

    int counter;
    int index;

    private Mapper<?> m1;
    private Mapper<?> m2;

    private Class<? extends Component> c1;
    private Class<? extends Component> c2;

    public EntityDeleterSystem(long seed, int entityCount,
            Class<? extends Component> c1, Class<? extends Component> c2) {
        this.c1 = c1;
        this.c2 = c2;
        Random rng = new Random(seed);
        ENTITY_COUNT = entityCount;
        ids = new int[ENTITY_COUNT];
        for (int i = 0; ids.length > i; i++)
            ids[i] = (int) (rng.nextFloat() * ENTITY_COUNT);
    }

    @Override
    protected void initialize() {
        this.m1 = engine.getMapper(c1);
        this.m2 = engine.getMapper(c2);

        for (int i = 0; ENTITY_COUNT > i; i++) {
            createEntity();
        }
    }

    private void createEntity() {
        int entity = engine.createEntity();
        m1.create(entity);
        m2.create(entity);
    }

    @Override
    protected void update() {
        counter++;
        if (counter == 100) {
            int e = ids[index++];
            engine.destroyEntity(e);
            index = index % ENTITY_COUNT;
            counter = 0;
        } else if (counter == 1) { // need to wait one round to reclaim entities
            createEntity();
        }
    }
}
