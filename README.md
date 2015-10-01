## entity-system-benchmarks

## Benchmarks description:
#### Tested frameworks
- [artemis-odb](https://github.com/junkdog/artemis-odb) 0.4.0, 0.6.5, 0.7.0, 0.8.0, 0.9.0, 0.10.2
- [ashley](https://github.com/libgdx/ashley) 1.0.1, 1.2.0, 1.3.1, 1.3.3, 1.6.0
- [gdx-artemis](https://github.com/apotapov/gdx-artemis) 0.5.0


### Benchmarks explained
- All benchmarks measure throughput; higher score is better.
- Attempt to be as similar as possible across frameworks.
- Run with `entityCount`: 1024, 4096, 16384 and 65536.
- Share the same random seed.
- Raw, unprocessed results, here: https://github.com/junkdog/entity-system-benchmarks/tree/master/results
- Additional data:
  - `OpenJDK Runtime Environment (IcedTea 2.5.5) (7u79-2.5.5-0ubuntu1)`
  - `Intel(R) Core(TM) i7-4710HQ CPU @ 2.50GHz`

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

#### Add/remove components benchmarks:
- Have 3 BaselinePositionSystems.
- Each tick, toggles the Position component for 1/4 all entities.
  - Entities are chosen from a shuffled list of entity ids.
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
| artemis-odb-0.4.0       |  11083.48 |   6570.84 |           |        770.30 |           |           |
| artemis-odb-0.9.0       |  12517.88 |  10987.68 |  11510.39 |       5119.11 |   5015.69 |   6070.02 |
| artemis-odb-0.9.0_fast  |  14349.14 |  12347.56 |  12740.81 |       5155.04 |   4986.82 |   6295.81 |
| artemis-odb-0.13.1      |  12439.36 |   9654.54 |  11436.45 |       5049.44 |   6160.31 |   7657.59 |
| artemis-odb-0.13.1_fast |  14127.14 |  10625.56 |  12141.62 |       5007.49 |   6386.25 |   7925.59 |
| artemis-odb-1.0.0       |  12697.62 |  10511.54 |  11707.52 |       5283.63 |   6856.19 |   8890.31 |
| artemis-odb-1.0.0_fast  |  15635.89 |  11647.35 |  13093.23 |       5332.26 |   7456.44 |   9339.31 |
| ashley-1.0.1            |   6023.37 |   3970.95 |           |       1659.90 |           |           |
| ashley-1.6.0            |   9274.63 |   4997.16 |           |       1634.38 |           |           |
| gdx-artemis-0.5.0       |  10122.95 |           |   7309.27 |       1498.93 |           |           |


#### Benchmarks: 16384 entites

 ![it16k][it16k] ![ir16k][ir16k] ![arc16k][arc16k]

| ECS                     |  baseline | plain     | pooled    | insert/remove | edit      | transmute |
|-------------------------|-----------|-----------|-----------|---------------|-----------|-----------|
| artemis-odb-0.4.0       |   2619.30 |   2002.61 |           |        160.13 |           |           |
| artemis-odb-0.9.0       |   3140.24 |   2830.77 |   2887.78 |       1288.25 |   1161.61 |   1432.41 |
| artemis-odb-0.9.0_fast  |   3574.92 |   3200.51 |   3155.41 |       1259.85 |   1174.60 |   1468.43 |
| artemis-odb-0.13.1      |   3046.69 |   2819.56 |   2889.95 |       1353.38 |   1442.99 |   1846.19 |
| artemis-odb-0.13.1_fast |   3425.45 |   3009.54 |   3058.61 |       1347.90 |   1444.72 |   1813.31 |
| artemis-odb-1.0.0       |   3177.29 |   2914.86 |   2954.46 |       1381.50 |   1731.79 |   2159.84 |
| artemis-odb-1.0.0_fast  |   4035.53 |   3279.99 |   3278.34 |       1382.71 |   1820.24 |   2313.46 |
| ashley-1.0.1            |   1526.68 |    446.67 |           |        193.39 |           |           |
| ashley-1.6.0            |   2315.60 |    738.14 |           |        150.35 |           |           |
| gdx-artemis-0.5.0       |   2650.44 |           |   2264.00 |        425.81 |           |           |


#### Benchmarks: 65536 entites

 ![it64k][it64k] ![ir64k][ir64k] ![arc64k][arc64k]

| ECS                     |  baseline | plain     | pooled    | insert/remove | edit      | transmute |
|-------------------------|-----------|-----------|-----------|---------------|-----------|-----------|
| artemis-odb-0.4.0       |    308.69 |    222.75 |           |         21.10 |           |           |
| artemis-odb-0.9.0       |    659.87 |    627.34 |    638.18 |        224.41 |    237.66 |    317.49 |
| artemis-odb-0.9.0_fast  |    791.72 |    779.82 |    784.33 |        231.08 |    239.06 |    323.03 |
| artemis-odb-0.13.1      |    678.81 |    698.08 |    699.11 |        253.55 |    315.95 |    427.43 |
| artemis-odb-0.13.1_fast |    871.69 |    734.45 |    745.34 |        274.42 |    326.98 |    421.15 |
| artemis-odb-1.0.0       |    746.20 |    719.30 |    707.97 |        272.81 |    410.22 |    503.87 |
| artemis-odb-1.0.0_fast  |   1009.51 |    807.49 |    813.27 |        272.52 |    438.62 |    564.14 |
| ashley-1.0.1            |    197.00 |     61.64 |           |         14.84 |           |           |
| ashley-1.6.0            |    255.97 |     72.60 |           |         13.61 |           |           |
| gdx-artemis-0.5.0       |    327.03 |           |    380.30 |         52.29 |           |           |


#### Benchmarks: 262144 entites

 ![it256k][it256k] ![ir256k][ir256k] ![arc256k][arc256k]

| ECS                     |  baseline | plain     | pooled    | insert/remove | edit      | transmute |
|-------------------------|-----------|-----------|-----------|---------------|-----------|-----------|
| artemis-odb-0.4.0       |     73.41 |     56.73 |           |          2.18 |           |           |
| artemis-odb-0.9.0       |    167.62 |    131.89 |    133.61 |         36.59 |     27.65 |     45.66 |
| artemis-odb-0.9.0_fast  |    224.28 |    150.54 |    153.94 |         34.63 |     26.30 |     49.20 |
| artemis-odb-0.13.1      |    171.66 |    127.80 |    127.17 |         56.18 |     41.70 |     65.82 |
| artemis-odb-0.13.1_fast |    221.68 |    146.77 |    148.03 |         55.37 |     44.14 |     69.90 |
| artemis-odb-1.0.0       |    186.29 |    142.96 |    134.94 |         54.74 |     80.35 |    114.24 |
| artemis-odb-1.0.0_fast  |    252.20 |    162.02 |    158.79 |         55.96 |     89.40 |    125.50 |
| ashley-1.0.1            |     37.73 |     13.83 |           |          0.81 |           |           |
| ashley-1.6.0            |     47.95 |     15.45 |           |          0.75 |           |           |
| gdx-artemis-0.5.0       |    101.02 |           |     70.13 |         11.66 |           |           |

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

