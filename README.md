## entity-system-benchmarks

## Benchmarks description:
#### Tested frameworks
- [artemis-odb](https://github.com/junkdog/artemis-odb) 0.4.0, 0.6.5, 0.7.0
- [ashley](https://github.com/libgdx/ashley) 1.0.1, 1.2.0
- [gdx-artemis](https://github.com/apotapov/gdx-artemis) 0.5.0


### Benchmarks explained
- All benchmarks measure throughput; higher score is better.
- Attempt to be as similar as possible across frameworks.
- Run with `entityCount`: 1024, 4096, 16384 and 65536.
- Share the same random seed.
- Raw, unprocessed results, here: https://github.com/junkdog/entity-system-benchmarks/tree/master/results

#### Component iteration benchmarks:
- Have 3 entity systems updating a position component.
- An Entity Deleter system: each 100th tick an entity is deleted and a new entity is created the next tick.
- There are 4 types of iteration benchmarks:
  - **baseline:** position isn't updated, not included in charts.
  - **plain:** position component is the default component type, except gdx-artemis which always pools components.
  - **pooled:** pooling all components. Only artemis-odb >= 0.5.0 atm and gdx-artemis.
    - Ashley 1.0.1 pooled coming, failed to get working in Ashley 1.2.0
  - **packed:** packed components (backed by a Bytebuffer). Only artemis-odb >= 0.5.0 atm.

#### Insert/remove benchmarks:
- Precalculates 20 different entity compositions.
- Each tick, removes/inserts 1/4 of the total `entityCount`.
- When creating entities, a random composition is selected.
  - The composition is randomly chosen, but shared across all frameworks/benchmarks.


### Benchmark results

As usual, writing microbenchmarks is tricky business - take the numbers with a grain of salt.
These benchmarks should not be interpreted as benchmarking each framework as a whole, though
they aim to give a hint of each framework's performance.


#### Things that might concern

- Ashley 1.2.0's insertion/removal speed is suspiciously low: potentially an
  error in the benchmark.
- Overall, ashley-realted benchmarks may be improved; I'm not very familiar with
  the framework.
- I never got pooling working with Ashley-1.2.0. Ashley-1.0.1:s pooling benchmarks had an
  error, but considering it's now so old, I'd rather fix the benchmark for the most recent
  version.
- artemis-odb's PackedComponent benchmarks only have a single component implementation, skewing
  the benchmark results in its favor.

#### artemis-odb-0.7.0_fast benchmarks

Artemis-odb-0.7.0 added support for automatically rewriting the generated class files
of EntitySystems in order to achieve higher performance. The source code itself remains
identical. More about it on the artemis-odb wiki: [Optimizing Entity System][fast].

#### Benchmarks: 1024 entites

 ![it1k][it1k] ![ir1k][ir1k]

| ECS                    | baseline  | plain    | pooled   | packed   | insert/remove |
|------------------------|-----------|----------|----------|----------|---------------|
| Artemis-odb-0.4.0      |  69034.40 | 37357.63 |          |          |       2927.87 |
| Artemis-odb-0.6.5      |  67801.89 | 44438.36 | 46347.16 | 44172.01 |      18115.60 |
| Artemis-odb-0.7.0      |  68379.92 | 46214.97 | 48892.72 | 47690.41 |      32018.43 |
| Artemis-odb-0.7.0_fast | 116545.09 | 72740.33 | 77609.03 | 79323.38 |      31828.33 |
| Ashley-1.0.1           |  26542.09 | 12966.02 |          |          |      16094.70 |
| Ashley-1.2.0           |  59433.04 | 20265.84 |          |          |         72.88 |
| Gdx-artemis-0.5.0      |  60428.69 |          | 37134.86 |          |      15798.65 |


#### Benchmarks: 4096 entites

 ![it4k][it4k] ![ir4k][ir4k]

| ECS                    | baseline | plain    | pooled   | packed   | insert/remove |
|------------------------|----------|----------|----------|----------|---------------|
| Artemis-odb-0.4.0      | 15127.95 |  6981.05 |          |          |        683.30 |
| Artemis-odb-0.6.5      | 15135.81 |  9561.01 | 10997.69 | 10537.03 |       3056.89 |
| Artemis-odb-0.7.0      | 16941.63 | 10440.95 | 11847.78 | 11815.56 |       6338.45 |
| Artemis-odb-0.7.0_fast | 29154.68 | 14916.59 | 17988.76 | 18663.66 |       6535.65 |
| Ashley-1.0.1           |  7844.09 |  4057.65 |          |          |       2232.57 |
| Ashley-1.2.0           | 13743.01 |  5053.67 |          |          |         18.68 |
| Gdx-artemis-0.5.0      | 13974.25 |          |  8323.16 |          |       3407.19 |


#### Benchmarks: 16384 entites

 ![it16k][it16k] ![ir16k][ir16k]

| ECS                    | baseline | plain   | pooled  | packed  | insert/remove |
|------------------------|----------|---------|---------|---------|---------------|
| Artemis-odb-0.4.0      |  3814.62 | 2117.65 |         |         |        141.98 |
| Artemis-odb-0.6.5      |  3863.48 | 2747.10 | 2756.46 | 2824.85 |        415.08 |
| Artemis-odb-0.7.0      |  4271.34 | 2296.75 | 3016.39 | 3074.55 |       1465.47 |
| Artemis-odb-0.7.0_fast |  7340.84 | 4379.42 | 4563.54 | 5029.63 |       1444.43 |
| Ashley-1.0.1           |  1931.47 |  417.61 |         |         |        206.18 |
| Ashley-1.2.0           |  3231.84 |  816.37 |         |         |          4.61 |
| Gdx-artemis-0.5.0      |  3555.33 |         | 2482.01 |         |        835.41 |


#### Benchmarks: 65536 entites

 ![it65k][it65k] ![ir65k][ir65k]

| ECS                    | baseline | plain   | pooled  | packed  | insert/remove |
|------------------------|----------|---------|---------|---------|---------------|
| Artemis-odb-0.4.0      |   399.47 |  201.61 |         |         |         21.76 |
| Artemis-odb-0.6.5      |   440.57 |  347.35 |  345.39 |  419.33 |         35.33 |
| Artemis-odb-0.7.0      |   955.65 |  730.10 |  726.06 |  769.89 |        230.58 |
| Artemis-odb-0.7.0_fast |  1765.17 | 1069.29 | 1068.51 | 1279.90 |        230.10 |
| Ashley-1.0.1           |   263.81 |   61.49 |         |         |         15.38 |
| Ashley-1.2.0           |   282.95 |   68.34 |         |         |          1.05 |
| Gdx-artemis-0.5.0      |   430.84 |         |  337.78 |         |        133.60 |



 [fast]: https://github.com/junkdog/artemis-odb/wiki/Optimizing-Entity-Systems
 [it1k]: http://junkdog.github.io/images/ecs-bench/iteration-1024.png
 [it4k]: http://junkdog.github.io/images/ecs-bench/iteration-4096.png
 [it16k]: http://junkdog.github.io/images/ecs-bench/iteration-16384.png
 [it65k]: http://junkdog.github.io/images/ecs-bench/iteration-65536.png
 [ir1k]: http://junkdog.github.io/images/ecs-bench/insert_remove-1024.png
 [ir4k]: http://junkdog.github.io/images/ecs-bench/insert_remove-4096.png
 [ir16k]: http://junkdog.github.io/images/ecs-bench/insert_remove-16384.png
 [ir65k]: http://junkdog.github.io/images/ecs-bench/insert_remove-65536.png
 
