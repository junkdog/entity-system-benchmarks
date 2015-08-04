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
  - `SE Runtime Environment (build 1.7.0_65-b17)`
  - `Intel(R) Core(TM) i7-3540M CPU @ 3.00GHz`

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

| ECS                    |  baseline | plain     | pooled    | packed    | insert/remove | edit      | transmute |
|------------------------|-----------|-----------|-----------|-----------|---------------|-----------|-----------|
| artemis-odb-0.4.0      |  67184.23 |  38930.49 |           |           |       3242.87 |           |           |
| artemis-odb-0.6.5      |  66996.75 |  44846.45 |  47145.59 |  45959.94 |      11784.88 |           |           |
| artemis-odb-0.7.0      |  67008.73 |  46821.55 |  49260.80 |  48448.45 |      22192.77 |  12260.46 |           |
| artemis-odb-0.7.0_fast | 118996.73 |  74446.34 |  78519.52 |  80549.19 |      22117.52 |  12507.95 |           |
| artemis-odb-0.8.0      |  68322.88 |  47438.36 |  50050.22 |  49117.23 |      22553.07 |  12402.53 |           |
| artemis-odb-0.8.0_fast | 118965.18 |  73726.02 |  79108.24 |  81174.70 |      22829.18 |  12843.13 |           |
| artemis-odb-0.9.0      |  78406.64 |  49676.18 |  52952.75 |  49696.44 |      21853.32 |  22972.31 |  26662.45 |
| artemis-odb-0.9.0_fast | 120399.74 |  79525.02 |  86235.73 |  79921.34 |      21832.89 |  24485.47 |  28160.66 |
| artemis-odb-0.10.2     |  80636.16 |  49976.07 |  52830.59 |  50156.86 |      21696.35 |  27992.17 |  38385.45 |
| artemis-odb-0.10.2_fast | 122654.86 |  70775.27 |  75244.58 |  71683.93 |      22096.52 |  30016.74 |  42613.61 |
| ashley-1.0.1           |  26889.80 |  13038.17 |           |           |       8590.26 |           |           |
| ashley-1.2.0           |  60742.40 |  20495.18 |           |           |         69.06 |           |           |
| ashley-1.3.1           |  58588.49 |  26062.15 |  25370.25 |           |       7550.69 |           |           |
| ashley-1.3.3           |  60330.08 |  21999.78 |           |           |       6530.10 |           |           |
| ashley-1.6.0           |  60165.13 |  20975.64 |           |           |       8230.97 |           |           |
| gdx-artemis-0.5.0      |  59759.43 |           |  38467.59 |           |       6350.10 |           |           |


#### Benchmarks: 4096 entites

 ![it4k][it4k] ![ir4k][ir4k] ![arc4k][arc4k]

| ECS                    |  baseline | plain     | pooled    | packed    | insert/remove | edit      | transmute |
|------------------------|-----------|-----------|-----------|-----------|---------------|-----------|-----------|
| artemis-odb-0.4.0      |  15071.12 |   6982.99 |           |           |        761.24 |           |           |
| artemis-odb-0.6.5      |  15427.62 |   9750.22 |  11166.67 |  10743.87 |       2393.04 |           |           |
| artemis-odb-0.7.0      |  17267.73 |  10765.51 |  12094.17 |  12018.70 |       4900.40 |   1298.04 |           |
| artemis-odb-0.7.0_fast |  29786.55 |  15369.48 |  18222.92 |  19012.07 |       5042.80 |   1320.41 |           |
| artemis-odb-0.8.0      |  17283.30 |  10768.55 |  12212.52 |  12077.33 |       4897.72 |   1297.86 |           |
| artemis-odb-0.8.0_fast |  29748.37 |  15338.18 |  18386.43 |  18983.28 |       5138.32 |   1311.22 |           |
| artemis-odb-0.9.0      |  19558.33 |  11705.43 |  13315.33 |  12670.53 |       5010.42 |   5393.27 |   6572.89 |
| artemis-odb-0.9.0_fast |  30195.49 |  20906.13 |  21781.55 |  20895.19 |       5135.27 |   5672.29 |   6926.20 |
| artemis-odb-0.10.2     |  20010.78 |  11187.16 |  12786.58 |  11364.52 |       4903.53 |   6224.02 |   9158.89 |
| artemis-odb-0.10.2_fast |  30716.60 |  16270.68 |  18156.91 |  16470.50 |       5072.76 |   6524.56 |   9902.27 |
| ashley-1.0.1           |   7946.38 |   4166.46 |           |           |       1632.37 |           |           |
| ashley-1.2.0           |  13946.86 |   5326.98 |           |           |         18.47 |           |           |
| ashley-1.3.1           |  13496.41 |   6483.11 |   6290.83 |           |       1391.96 |           |           |
| ashley-1.3.3           |  13473.16 |   5564.40 |           |           |       1101.78 |           |           |
| ashley-1.6.0           |  13689.72 |   5401.77 |           |           |       1615.33 |           |           |
| gdx-artemis-0.5.0      |  13647.35 |           |   7882.39 |           |       1561.59 |           |           |


#### Benchmarks: 16384 entites

 ![it16k][it16k] ![ir16k][ir16k] ![arc16k][arc16k]

| ECS                    |  baseline | plain     | pooled    | packed    | insert/remove | edit      | transmute |
|------------------------|-----------|-----------|-----------|-----------|---------------|-----------|-----------|
| artemis-odb-0.4.0      |   3910.47 |   2107.53 |           |           |        159.29 |           |           |
| artemis-odb-0.6.5      |   3896.60 |   2765.19 |   2817.57 |   2851.24 |        384.23 |           |           |
| artemis-odb-0.7.0      |   4369.34 |   3017.20 |   3068.84 |   3136.09 |       1216.49 |     98.46 |           |
| artemis-odb-0.7.0_fast |   7505.24 |   4459.94 |   4629.05 |   5105.02 |       1232.13 |     98.13 |           |
| artemis-odb-0.8.0      |   4356.79 |   3017.01 |   3071.28 |   3134.44 |       1215.68 |     97.20 |           |
| artemis-odb-0.8.0_fast |   7501.97 |   4553.68 |   4604.28 |   5103.79 |       1225.41 |     99.99 |           |
| artemis-odb-0.9.0      |   4904.60 |   3283.43 |   3308.77 |   3311.61 |       1261.66 |   1149.09 |   1537.35 |
| artemis-odb-0.9.0_fast |   7569.80 |   5383.19 |   5407.72 |   5388.66 |       1291.77 |   1233.86 |   1617.58 |
| artemis-odb-0.10.2     |   4751.45 |   3217.27 |   3277.56 |   3331.87 |       1261.60 |   1446.57 |   2095.62 |
| artemis-odb-0.10.2_fast |   7708.02 |   4512.24 |   4582.95 |   4672.54 |       1288.99 |   1537.70 |   2174.77 |
| ashley-1.0.1           |   1986.64 |    460.48 |           |           |        193.66 |           |           |
| ashley-1.2.0           |   3302.57 |    833.69 |           |           |          4.63 |           |           |
| ashley-1.3.1           |   3247.47 |    945.86 |    954.95 |           |        175.24 |           |           |
| ashley-1.3.3           |   3281.10 |    813.38 |           |           |        142.98 |           |           |
| ashley-1.6.0           |   3184.84 |    739.18 |           |           |        156.39 |           |           |
| gdx-artemis-0.5.0      |   3601.17 |           |   2502.29 |           |        416.83 |           |           |


#### Benchmarks: 65536 entites

 ![it64k][it64k] ![ir64k][ir64k] ![arc64k][arc64k]

| ECS                    |  baseline | plain     | pooled    | packed    | insert/remove | edit      | transmute |
|------------------------|-----------|-----------|-----------|-----------|---------------|-----------|-----------|
| artemis-odb-0.4.0      |    410.33 |    232.87 |           |           |         20.91 |           |           |
| artemis-odb-0.6.5      |    432.71 |    384.23 |    378.43 |    454.63 |         33.03 |           |           |
| artemis-odb-0.7.0      |   1009.09 |    729.76 |    723.46 |    790.25 |        105.87 |      6.52 |           |
| artemis-odb-0.7.0_fast |   1759.48 |   1010.35 |   1036.85 |   1305.46 |        105.94 |      6.61 |           |
| artemis-odb-0.8.0      |    990.60 |    728.57 |    728.46 |    786.77 |        105.08 |      6.61 |           |
| artemis-odb-0.8.0_fast |   1737.42 |   1046.20 |   1008.35 |   1307.27 |        104.85 |      6.55 |           |
| artemis-odb-0.9.0      |   1211.78 |    820.07 |    819.20 |    825.86 |        224.74 |    245.48 |    335.81 |
| artemis-odb-0.9.0_fast |   1887.40 |   1348.08 |   1345.02 |   1345.59 |        237.64 |    253.45 |    332.41 |
| artemis-odb-0.10.2     |   1253.84 |    792.69 |    787.44 |    846.80 |        243.51 |    299.99 |    440.46 |
| artemis-odb-0.10.2_fast |   1926.96 |   1111.36 |   1107.50 |   1179.01 |        244.95 |    312.26 |    476.06 |
| ashley-1.0.1           |    242.75 |     58.89 |           |           |         14.72 |           |           |
| ashley-1.2.0           |    270.37 |     60.58 |           |           |          1.05 |           |           |
| ashley-1.3.1           |    221.14 |     87.87 |     90.81 |           |         14.76 |           |           |
| ashley-1.3.3           |    299.93 |     72.96 |           |           |         11.66 |           |           |
| ashley-1.6.0           |    309.41 |     74.87 |           |           |         11.07 |           |           |
| gdx-artemis-0.5.0      |    421.24 |           |    392.53 |           |         53.78 |           |           |




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
 
