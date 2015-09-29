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


#### Benchmarks: 1024 entites

 ![it1k][it1k] ![ir1k][ir1k] ![arc1k][arc1k]

| ECS                     |  baseline | plain     | pooled    | packed    | insert/remove | edit      | transmute |
|-------------------------|-----------|-----------|-----------|-----------|---------------|-----------|-----------|
| artemis-odb-0.4.0       |  67677.76 |  37960.22 |           |           |       3222.62 |           |           |
| artemis-odb-0.6.5       |  67173.48 |  44840.97 |  47074.15 |  46251.20 |      11822.42 |           |           |
| artemis-odb-0.7.0       |  67754.19 |  46476.73 |  49863.50 |  48729.38 |      22573.34 |  12187.27 |           |
| artemis-odb-0.7.0_fast  | 119366.48 |  74640.34 |  78774.10 |  80456.23 |      22702.57 |  12809.12 |           |
| artemis-odb-0.8.0       |  70114.11 |  47749.93 |  49868.64 |  49222.79 |      22295.75 |  12457.79 |           |
| artemis-odb-0.8.0_fast  | 119497.13 |  74550.80 |  79082.70 |  81291.20 |      22887.00 |  12804.93 |           |
| artemis-odb-0.9.0       |  78220.48 |  49217.35 |  52845.34 |  50275.54 |      21717.55 |  23281.06 |  26683.50 |
| artemis-odb-0.9.0_fast  | 120792.32 |  79048.16 |  86220.05 |  80442.89 |      22338.58 |  24658.21 |  28508.24 |
| artemis-odb-0.10.2      |  78635.45 |  50160.69 |  52927.66 |  49008.95 |      21834.09 |  28012.76 |  38779.91 |
| artemis-odb-0.10.2_fast | 123081.16 |  71660.06 |  76439.79 |  72021.66 |      21962.40 |  29960.31 |  42329.86 |
| artemis-odb-0.11.1      |  80413.35 |  49853.79 |  53503.38 |  50328.11 |      21521.84 |  28603.76 |  35801.68 |
| artemis-odb-0.11.1_fast | 123110.97 |  71021.13 |  74275.36 |  72353.70 |      21899.13 |  30134.11 |  41784.45 |
| ashley-1.0.1            |  26794.29 |  13352.33 |           |           |       8817.64 |           |           |
| ashley-1.2.0            |  60770.48 |  20625.60 |           |           |         69.57 |           |           |
| ashley-1.3.1            |  58619.97 |  25866.10 |  25222.99 |           |       7713.85 |           |           |
| ashley-1.3.3            |  60138.39 |  21895.68 |           |           |       6775.25 |           |           |
| ashley-1.6.0            |  58762.43 |  21040.68 |           |           |       8262.27 |           |           |
| gdx-artemis-0.5.0       |  64804.08 |           |  38614.99 |           |       6314.44 |           |           |


#### Benchmarks: 4096 entites

 ![it4k][it4k] ![ir4k][ir4k] ![arc4k][arc4k]

| ECS                     |  baseline | plain     | pooled    | packed    | insert/remove | edit      | transmute |
|-------------------------|-----------|-----------|-----------|-----------|---------------|-----------|-----------|
| artemis-odb-0.4.0       |  15688.29 |   7152.30 |           |           |        761.99 |           |           |
| artemis-odb-0.6.5       |  15298.71 |   9866.44 |  10948.55 |  10757.04 |       2403.91 |           |           |
| artemis-odb-0.7.0       |  17405.67 |  10823.11 |  12231.26 |  12126.54 |       4899.81 |   1294.21 |           |
| artemis-odb-0.7.0_fast  |  29878.70 |  15250.38 |  18325.47 |  19041.24 |       4992.10 |   1314.25 |           |
| artemis-odb-0.8.0       |  17335.98 |  10837.63 |  12170.52 |  12138.17 |       5065.63 |   1311.00 |           |
| artemis-odb-0.8.0_fast  |  29652.76 |  15203.70 |  18500.39 |  19126.73 |       5120.16 |   1325.35 |           |
| artemis-odb-0.9.0       |  19612.13 |  12531.81 |  13342.80 |  12644.05 |       5104.48 |   5329.56 |   6545.84 |
| artemis-odb-0.9.0_fast  |  30302.76 |  20948.02 |  21901.03 |  21096.15 |       5283.83 |   5431.95 |   7092.19 |
| artemis-odb-0.10.2      |  20153.43 |  11177.59 |  13116.43 |  11538.06 |       5020.76 |   5968.30 |   9247.36 |
| artemis-odb-0.10.2_fast |  30906.16 |  16184.22 |  18022.74 |  16492.13 |       5143.26 |   6416.64 |   9987.74 |
| artemis-odb-0.11.1      |  20236.50 |  11260.82 |  13041.24 |  11353.09 |       5057.65 |   6638.95 |   8774.99 |
| artemis-odb-0.11.1_fast |  30863.02 |  16240.11 |  18151.37 |  16409.11 |       5239.35 |   6937.42 |   9659.34 |
| ashley-1.0.1            |   7941.69 |   4183.74 |           |           |       1639.27 |           |           |
| ashley-1.2.0            |  13803.77 |   5145.07 |           |           |         18.62 |           |           |
| ashley-1.3.1            |  13270.89 |   6550.83 |   6430.09 |           |       1375.91 |           |           |
| ashley-1.3.3            |  13465.86 |   5497.57 |           |           |       1169.97 |           |           |
| ashley-1.6.0            |  13722.68 |   5408.82 |           |           |       1591.10 |           |           |
| gdx-artemis-0.5.0       |  13855.94 |           |   7904.29 |           |       1550.09 |           |           |


#### Benchmarks: 16384 entites

 ![it16k][it16k] ![ir16k][ir16k] ![arc16k][arc16k]

| ECS                     |  baseline | plain     | pooled    | packed    | insert/remove | edit      | transmute |
|-------------------------|-----------|-----------|-----------|-----------|---------------|-----------|-----------|
| artemis-odb-0.4.0       |   3764.14 |   2090.44 |           |           |        159.58 |           |           |
| artemis-odb-0.6.5       |   3901.90 |   2716.00 |   2794.57 |   2854.71 |        386.54 |           |           |
| artemis-odb-0.7.0       |   4389.18 |   3040.66 |   3089.38 |   3145.14 |       1224.87 |     96.69 |           |
| artemis-odb-0.7.0_fast  |   7518.77 |   4484.12 |   4649.38 |   5090.04 |       1241.02 |     99.20 |           |
| artemis-odb-0.8.0       |   4153.27 |   3035.22 |   3089.48 |   3145.62 |       1210.36 |     95.69 |           |
| artemis-odb-0.8.0_fast  |   7513.09 |   4573.10 |   4638.82 |   5125.80 |       1216.29 |     98.68 |           |
| artemis-odb-0.9.0       |   4940.71 |   3282.79 |   3331.73 |   3322.78 |       1247.41 |   1207.34 |   1532.13 |
| artemis-odb-0.9.0_fast  |   7581.12 |   5403.38 |   5303.85 |   5425.94 |       1279.12 |   1241.81 |   1642.14 |
| artemis-odb-0.10.2      |   4992.70 |   3228.51 |   3212.56 |   3344.63 |       1275.07 |   1484.18 |   2090.28 |
| artemis-odb-0.10.2_fast |   7730.37 |   4521.51 |   4567.37 |   4649.93 |       1302.09 |   164     |   2259.21 |
| artemis-odb-0.11.1      |   5081.79 |   3173.59 |   3321.51 |   3349.28 |       1406.03 |   1554.03 |   2009.08 |
| artemis-odb-0.11.1_fast |   7738.82 |   4538.48 |   4572.60 |   4691.03 |       1412.47 |   1539.95 |   2241.94 |
| ashley-1.0.1            |   1989.47 |    444.85 |           |           |        193.73 |           |           |
| ashley-1.2.0            |   3312.30 |    865.33 |           |           |          4.60 |           |           |
| ashley-1.3.1            |   3249.69 |   1075.58 |   1152.18 |           |        176.19 |           |           |
| ashley-1.3.3            |   3239.54 |    833.00 |           |           |        157.41 |           |           |
| ashley-1.6.0            |   3203.17 |    799.95 |           |           |        155.57 |           |           |
| gdx-artemis-0.5.0       |   3569.23 |           |   2460.61 |           |        426.24 |           |           |


#### Benchmarks: 65536 entites

 ![it64k][it64k] ![ir64k][ir64k] ![arc64k][arc64k]

| ECS                     |  baseline | plain     | pooled    | packed    | insert/remove | edit      | transmute |
|-------------------------|-----------|-----------|-----------|-----------|---------------|-----------|-----------|
| artemis-odb-0.4.0       |    420.20 |    236.87 |           |           |         21.06 |           |           |
| artemis-odb-0.6.5       |    446.90 |    395.72 |    385.58 |    467.06 |         33.44 |           |           |
| artemis-odb-0.7.0       |   1018.15 |    729.93 |    724.57 |    790.65 |        106.20 |      6.55 |           |
| artemis-odb-0.7.0_fast  |   1775.68 |   1029.90 |   1070.24 |   1309.76 |        106.62 |      6.61 |           |
| artemis-odb-0.8.0       |   1056.27 |    739.64 |    730.09 |    790.95 |        105.95 |      6.60 |           |
| artemis-odb-0.8.0_fast  |   1810.01 |   1061.38 |   1033.88 |   1312.06 |        106.74 |      6.64 |           |
| artemis-odb-0.9.0       |   1210.27 |    809.71 |    825.11 |    829.50 |        235.51 |    242.06 |    313.20 |
| artemis-odb-0.9.0_fast  |   1893.26 |   1354.60 |   1357.04 |   1354.65 |        236.25 |    264.29 |    348.09 |
| artemis-odb-0.10.2      |    953.54 |    792.89 |    805.93 |    842.60 |        245.10 |    303.61 |    452.25 |
| artemis-odb-0.10.2_fast |   1934.63 |   1122.69 |   1119.15 |   1180.71 |        249.33 |    311.57 |    479.59 |
| artemis-odb-0.11.1      |   1272.38 |    794.29 |    805.57 |    856.16 |        267.59 |    323.70 |    460.85 |
| artemis-odb-0.11.1_fast |   1934.39 |   1117.60 |   1092.72 |   1183.02 |        266.76 |    343.27 |    516.75 |
| ashley-1.0.1            |    245.85 |     62.16 |           |           |         14.86 |           |           |
| ashley-1.2.0            |    251.26 |     71.15 |           |           |          1.05 |           |           |
| ashley-1.3.1            |    255.10 |     90.49 |     88.85 |           |         14.85 |           |           |
| ashley-1.3.3            |    306.77 |     73.44 |           |           |         11.76 |           |           |
| ashley-1.6.0            |    316.13 |     80.24 |           |           |         11.10 |           |           |
| gdx-artemis-0.5.0       |    427.89 |           |    379.60 |           |         54.57 |           |           |



 [fast]: https://github.com/junkdog/artemis-odb/wiki/Hotspot-Optimization
 [it1k]: http://junkdog.github.io/images/ecs-bench/iteration__1024_entities.png
 [it4k]: http://junkdog.github.io/images/ecs-bench/iteration__4096_entities.png
 [it16k]: http://junkdog.github.io/images/ecs-bench/iteration__16384_entities.png
 [it64k]: http://junkdog.github.io/images/ecs-bench/iteration__65536_entities.png
 [ir1k]: http://junkdog.github.io/images/ecs-bench/insert_remove__1024_entities.png
 [ir4k]: http://junkdog.github.io/images/ecs-bench/insert_remove__4096_entities.png
 [ir16k]: http://junkdog.github.io/images/ecs-bench/insert_remove__16384_entities.png
 [ir64k]: http://junkdog.github.io/images/ecs-bench/insert_remove__65536_entities.png
 [arc1k]: http://junkdog.github.io/images/ecs-bench/add_remove_components__1024_entities.png
 [arc4k]: http://junkdog.github.io/images/ecs-bench/add_remove_components__4096_entities.png
 [arc16k]: http://junkdog.github.io/images/ecs-bench/add_remove_components__16384_entities.png
 [arc64k]: http://junkdog.github.io/images/ecs-bench/add_remove_components__65536_entities.png 
 
