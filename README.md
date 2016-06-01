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
| artemis-odb-0.4.0       |  10609.16 |   6673.70 |           |        774.12 |           |           |
| artemis-odb-0.9.0       |  11776.13 |  10949.46 |  11476.16 |       3219.62 |   5435.99 |   6912.18 |
| artemis-odb-0.9.0_fast  |  13440.43 |  11988.66 |  12502.18 |       3245.35 |   5566.49 |   7144.00 |
| artemis-odb-1.0.1       |  12395.24 |  10373.89 |  11563.60 |       3060.74 |   7827.03 |   9362.37 |
| artemis-odb-1.0.1_fast  |  15000.39 |  11546.26 |  12953.15 |       3172.14 |   7995.48 |  10208.75 |
| artemis-odb-2.0.0-RC1   |  12442.58 |  11098.03 |  12209.97 |       6217.78 |   8585.09 |  10495.03 |
| artemis-odb-2.0.0-RC1_fast |  15010.39 |  11920.99 |  13146.79 |       6225.45 |   9274.83 |  11296.83 |
| artemis-odb-2.0.0-RC2   |  12426.67 |  11093.07 |  12232.79 |       6101.39 |   8692.16 |  10723.12 |
| artemis-odb-2.0.0-RC2_fast |  15061.05 |  12022.86 |  13247.54 |       6300.21 |   9326.63 |  11568.29 |
| ashley-1.6.0            |   9621.74 |   4992.19 |           |       1710.13 |           |           |
| gdx-artemis-0.5.0       |   9974.36 |           |   7545.31 |       1545.58 |           |           |
| retinazer-0.2.0         |  19965.98 |  10485.84 |           |       8589.60 |  21339.54 |           |


#### Benchmarks: 16384 entites

 ![it16k][it16k] ![ir16k][ir16k] ![arc16k][arc16k]

| ECS                     |  baseline | plain     | pooled    | insert/remove | edit      | transmute |
|-------------------------|-----------|-----------|-----------|---------------|-----------|-----------|
| artemis-odb-0.4.0       |   2613.93 |   2027.73 |           |        156.16 |           |           |
| artemis-odb-0.9.0       |   2945.44 |   2867.71 |   2871.12 |        519.20 |   1248.99 |   1523.36 |
| artemis-odb-0.9.0_fast  |   3361.41 |   3115.78 |   3098.69 |        521.04 |   1273.50 |   1578.94 |
| artemis-odb-1.0.1       |   3111.22 |   2915.28 |   2950.20 |        536.95 |   1831.17 |   2424.73 |
| artemis-odb-1.0.1_fast  |   3762.55 |   3199.09 |   3292.84 |        541.52 |   2012.89 |   2614.00 |
| artemis-odb-2.0.0-RC1   |   3052.38 |   3036.99 |   3088.56 |       1495.83 |   2109.25 |   2620.57 |
| artemis-odb-2.0.0-RC1_fast |   3768.35 |   3256.18 |   3282.56 |       1513.29 |   2299.33 |   2805.50 |
| artemis-odb-2.0.0-RC2   |   3063.18 |   3025.16 |   3088.51 |       1495.83 |   2201.62 |   2581.02 |
| artemis-odb-2.0.0-RC2_fast |   3770.17 |   3280.94 |   3280.77 |       1543.03 |   2349.88 |   2817.17 |
| ashley-1.6.0            |   2357.24 |    747.36 |           |        157.02 |           |           |
| gdx-artemis-0.5.0       |   2518.07 |           |   2257.00 |        375.60 |           |           |
| retinazer-0.2.0         |   4989.42 |   2779.12 |           |       1832.26 |   5237.94 |           |


#### Benchmarks: 65536 entites

 ![it64k][it64k] ![ir64k][ir64k] ![arc64k][arc64k]

| ECS                     |  baseline | plain     | pooled    | insert/remove | edit      | transmute |
|-------------------------|-----------|-----------|-----------|---------------|-----------|-----------|
| artemis-odb-0.4.0       |    321.93 |    231.24 |           |         23.06 |           |           |
| artemis-odb-0.9.0       |    643.59 |    715.33 |    641.39 |         94.49 |    257.62 |    342.33 |
| artemis-odb-0.9.0_fast  |    837.87 |    778.02 |    779.91 |         93.59 |    266.31 |    353.99 |
| artemis-odb-1.0.1       |    777.32 |    721.85 |    722.76 |         96.40 |    433.83 |    587.22 |
| artemis-odb-1.0.1_fast  |    940.23 |    804.48 |    803.49 |         95.85 |    440.08 |    630.28 |
| artemis-odb-2.0.0-RC1   |    754.81 |    761.89 |    764.03 |        324.07 |    531.41 |    623.48 |
| artemis-odb-2.0.0-RC1_fast |    942.25 |    798.78 |    818.32 |        334.10 |    570.92 |    688.43 |
| artemis-odb-2.0.0-RC2   |    755.53 |    743.70 |    747.61 |        325.39 |    546.60 |    642.08 |
| artemis-odb-2.0.0-RC2_fast |    941.18 |    815.75 |    818.53 |        336.52 |    582.39 |    694.83 |
| ashley-1.6.0            |    272.72 |     74.12 |           |         12.58 |           |           |
| gdx-artemis-0.5.0       |    341.71 |           |    374.17 |         67.38 |           |           |
| retinazer-0.2.0         |   1136.29 |    670.11 |           |        279.42 |   1262.34 |           |


#### Benchmarks: 262144 entites

 ![it256k][it256k] ![ir256k][ir256k] ![arc256k][arc256k]

| ECS                     |  baseline | plain     | pooled    | insert/remove | edit      | transmute |
|-------------------------|-----------|-----------|-----------|---------------|-----------|-----------|
| artemis-odb-0.4.0       |     71.62 |     55.36 |           |          2.24 |           |           |
| artemis-odb-0.9.0       |    160.87 |    132.16 |    132.82 |         12.82 |     32.40 |     54.80 |
| artemis-odb-0.9.0_fast  |    209.12 |    159.65 |    153.97 |         13.03 |     31.91 |     55.51 |
| artemis-odb-1.0.1       |    175.67 |    137.73 |    136.02 |         14.48 |     88.26 |    135.43 |
| artemis-odb-1.0.1_fast  |    235.38 |    156.37 |    156.06 |         14.56 |     91.62 |    148.32 |
| artemis-odb-2.0.0-RC1   |    175.42 |    137.92 |    137.77 |         58.08 |    123.32 |    153.13 |
| artemis-odb-2.0.0-RC1_fast |    235.92 |    145.90 |    145.43 |         62.89 |    124.02 |    159.13 |
| artemis-odb-2.0.0-RC2   |    177.21 |    138.00 |    136.71 |         63.99 |     98.55 |    151.73 |
| artemis-odb-2.0.0-RC2_fast |    232.51 |    147.59 |    146.88 |         62.76 |    134.52 |    163.33 |
| ashley-1.6.0            |     47.45 |     15.78 |           |          0.85 |           |           |
| gdx-artemis-0.5.0       |     96.69 |           |     75.71 |         12.85 |           |           |
| retinazer-0.2.0         |    311.30 |    159.43 |           |         26.61 |    282.48 |           |



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

