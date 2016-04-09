## entity-system-benchmarks

## Benchmarks description:
#### Tested frameworks
- [artemis-odb](https://github.com/junkdog/artemis-odb) 0.4.0, 0.9.0, 1.0.1, 1.5.0 (prel.)
- [ashley](https://github.com/libgdx/ashley) 1.6.0
- [gdx-artemis](https://github.com/apotapov/gdx-artemis) 0.5.0
- [retanizer](https://github.com/antag99/retinazer) 0.2.0


### Benchmarks explained
- All benchmarks measure throughput; higher score is better.
- Attempt to be as similar as possible across frameworks.
- Run with `entityCount`: 1024, 4096, 16384 and 65536.
- Share the same random seed.
- Raw, unprocessed results, here: https://github.com/junkdog/entity-system-benchmarks/tree/master/results
- Additional data:
  - `OpenJDK Runtime Environment (IcedTea 2.5.5) (7u79-2.5.5-0ubuntu1)`
  - `Intel(R) Core(TM) i7-4700HQ CPU @ 2.40GHz`

#### Component iteration benchmarks:
- Have 3 entity systems updating a position component.
- An Entity Deleter system: each 100th tick an entity is deleted and a new entity is created the next tick.
- There are 3 types of iteration benchmarks:
  - **baseline:** position isn't updated, not included in charts.
  - **plain:** position component is the default component type.
  - **pooled:** pooling all components.

#### Insert/remove benchmarks:
- Chooses from up to 144 different entity compositions.
- Each tick, removes/inserts 1/4 of the total `entityCount`.
- When creating entities, a random composition is selected.
  - The composition is randomly chosen, but shared across all frameworks/benchmarks.

#### Add/remove components benchmarks:
- Have 3 BaselinePositionSystems.
- Each tick, toggles the Position component for 1/4 all entities.
  - Entities are chosen from a pre-shuffled list of entity ids.
- Two types of benchmarks:
  - **entity_edit:** Toggling achieve by calling entity.edit().create/remove
  - **transmuter:** Toggles using the [EntityTransmuter][transmuter] class.

 [transmuter]: https://github.com/junkdog/artemis-odb/wiki/Entity#entity-transmuters

### Benchmark results

As usual, writing microbenchmarks is tricky business - take the numbers with a grain of salt.
These benchmarks should not be interpreted as benchmarking each framework as a whole, though
they aim to give a hint of each framework's performance.


#### Things that might concern

- Overall, ashley-realted benchmarks may be improved; I'm not very familiar with
  the framework.
- I never got pooling working with Ashley-1.2.0. Ashley-1.0.1:s pooling benchmarks had an
  error, but considering it's now so old, I'd rather fix the benchmark for the most recent
  version.
- artemis-odb's PackedComponent benchmarks only have a single component implementation, skewing
  the benchmark results in its favor.

#### artemis-odb_fast benchmarks

Artemis-odb-0.7.0 added support for automatically rewriting the generated class files
of EntitySystems in order to achieve higher performance. The source code itself remains
identical. More about it on the artemis-odb wiki: [Hotspot Optimization][fast].


#### Benchmarks: 4096 entites

 ![it4k][it4k] ![ir4k][ir4k] ![arc4k][arc4k]

| ECS                     |  baseline | plain     | pooled    | insert/remove | edit      | transmute |
|-------------------------|-----------|-----------|-----------|---------------|-----------|-----------|
| artemis-odb-0.4.0       |   9919.76 |   6359.41 |           |        743.94 |           |           |
| artemis-odb-0.9.0       |  10607.49 |  10483.83 |  10283.32 |       3051.22 |   4931.52 |   5988.65 |
| artemis-odb-1.0.1       |  11724.46 |   9408.95 |  11022.63 |       2724.41 |   6886.80 |   8402.81 |
| artemis-odb-1.0.1_fast  |  14348.36 |  11067.19 |  12305.48 |       2693.67 |   7421.09 |   9038.47 |
| artemis-odb-1.5.0       |  11697.84 |   9441.98 |  11020.07 |       5872.43 |   7309.56 |   9069.49 |
| artemis-odb-1.5.0_fast  |  14296.45 |  10963.31 |  11961.09 |       5901.32 |   7496.48 |   9618.08 |
| ashley-1.6.0            |   9156.92 |   4974.44 |           |       1585.46 |           |           |
| gdx-artemis-0.5.0       |   9694.78 |           |   7266.43 |       1480.13 |           |           |
| retinazer-0.2.0         |  17928.34 |   9921.35 |           |       8293.12 |  18963.23 |           |


#### Benchmarks: 16384 entites

 ![it16k][it16k] ![ir16k][ir16k] ![arc16k][arc16k]

| ECS                     |  baseline | plain     | pooled    | insert/remove | edit      | transmute |
|-------------------------|-----------|-----------|-----------|---------------|-----------|-----------|
| artemis-odb-0.4.0       |   2553.73 |   1952.88 |           |        151.21 |           |           |
| artemis-odb-0.9.0       |   2824.21 |   2318.40 |   2735.76 |        512.24 |   1147.41 |   1478.10 |
| artemis-odb-1.0.1       |   2685.05 |   2527.87 |   2452.54 |        504.92 |   1629.92 |   2066.43 |
| artemis-odb-1.0.1_fast  |   3656.70 |   3098.31 |   3135.06 |        509.68 |   1727.47 |   2194.73 |
| artemis-odb-1.5.0       |   2722.05 |   2370.65 |   2617.17 |       1456.37 |   1795.96 |   2030.69 |
| artemis-odb-1.5.0_fast  |   3592.42 |   3074.25 |   3095.54 |       1471.01 |   1910.55 |   2137.26 |
| ashley-1.6.0            |   2161.41 |    702.81 |           |        175.91 |           |           |
| gdx-artemis-0.5.0       |   2277.53 |           |   2120.16 |        353.44 |           |           |
| retinazer-0.2.0         |   4657.89 |   2668.25 |           |       1741.63 |   4596.69 |           |


#### Benchmarks: 65536 entites

 ![it64k][it64k] ![ir64k][ir64k] ![arc64k][arc64k]

| ECS                     |  baseline | plain     | pooled    | insert/remove | edit      | transmute |
|-------------------------|-----------|-----------|-----------|---------------|-----------|-----------|
| artemis-odb-0.4.0       |    322.33 |    201.34 |           |         22.44 |           |           |
| artemis-odb-0.9.0       |    617.00 |    607.05 |    599.07 |         78.16 |    244.19 |    296.58 |
| artemis-odb-1.0.1       |    687.65 |    638.31 |    643.17 |         80.35 |    401.11 |    505.87 |
| artemis-odb-1.0.1_fast  |    892.38 |    775.62 |    747.88 |         79.03 |    422.07 |    566.59 |
| artemis-odb-1.5.0       |    646.02 |    639.89 |    642.10 |        344.08 |    429.71 |    510.94 |
| artemis-odb-1.5.0_fast  |    868.75 |    618.33 |    767.65 |        346.01 |    457.94 |    565.47 |
| ashley-1.6.0            |    275.00 |     67.53 |           |         14.59 |           |           |
| gdx-artemis-0.5.0       |    323.47 |           |    324.70 |         66.75 |           |           |
| retinazer-0.2.0         |   1087.76 |    651.09 |           |        269.98 |   1175.90 |           |


#### Benchmarks: 262144 entites

 ![it256k][it256k] ![ir256k][ir256k] ![arc256k][arc256k]

| ECS                     |  baseline | plain     | pooled    | insert/remove | edit      | transmute |
|-------------------------|-----------|-----------|-----------|---------------|-----------|-----------|
| artemis-odb-0.4.0       |     72.49 |     53.68 |           |          2.17 |           |           |
| artemis-odb-0.9.0       |    156.06 |    125.21 |    133.13 |         12.33 |     32.64 |     48.68 |
| artemis-odb-1.0.1       |    171.30 |    131.06 |    132.80 |         13.36 |     75.62 |    112.08 |
| artemis-odb-1.0.1_fast  |    228.64 |    148.97 |    160.29 |         13.53 |     81.30 |    123.97 |
| artemis-odb-1.5.0       |    166.27 |    130.40 |    130.36 |         80.01 |    100.88 |    124.27 |
| artemis-odb-1.5.0_fast  |    226.91 |    154.56 |    148.75 |         77.81 |    107.61 |    131.48 |
| ashley-1.6.0            |     52.12 |     14.44 |           |          0.81 |           |           |
| gdx-artemis-0.5.0       |     99.37 |           |     77.78 |         13.01 |           |           |
| retinazer-0.2.0         |    271.86 |    132.68 |           |         26.01 |    267.61 |           |


 [fast]: https://github.com/junkdog/artemis-odb/wiki/Hotspot-Optimization
 [it4k]: http://junkdog.github.io/images/ecs-bench/iteration__4096_entities.png
 [it16k]: http://junkdog.github.io/images/ecs-bench/iteration__16384_entities.png
 [it64k]: http://junkdog.github.io/images/ecs-bench/iteration__65536_entities.png
 [it256k]: http://junkdog.github.io/images/ecs-bench/iteration__262144_entities.png
 [ir4k]: http://junkdog.github.io/images/ecs-bench/insert_remove__4096_entities.png
 [ir16k]: http://junkdog.github.io/images/ecs-bench/insert_remove__16384_entities.png
 [ir64k]: http://junkdog.github.io/images/ecs-bench/insert_remove__65536_entities.png
 [ir256k]: http://junkdog.github.io/images/ecs-bench/insert_remove__262144_entities.png
 [arc4k]: http://junkdog.github.io/images/ecs-bench/add_remove_components__4096_entities.png
 [arc16k]: http://junkdog.github.io/images/ecs-bench/add_remove_components__16384_entities.png
 [arc64k]: http://junkdog.github.io/images/ecs-bench/add_remove_components__65536_entities.png 
 [arc256k]: http://junkdog.github.io/images/ecs-bench/add_remove_components__262144_entities.png

