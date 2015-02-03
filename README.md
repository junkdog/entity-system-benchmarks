## entity-system-benchmarks

## Benchmarks description:
#### Tested frameworks
- [artemis-odb](https://github.com/junkdog/artemis-odb) 0.4.0, 0.6.5, 0.7.0, 0.8.0
- [ashley](https://github.com/libgdx/ashley) 1.0.1, 1.2.0, 1.3.1
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
| artemis-odb-0.4.0      |  56643.18 |  26187.49 |           |           |       3304.02 |           |           |
| artemis-odb-0.6.5      |  63240.77 |  41967.09 |  45199.82 |  44189.73 |      11263.31 |           |           |
| artemis-odb-0.7.0      |  63025.70 |  44636.02 |  46189.92 |  45917.32 |      20778.21 |  10287.51 |           |
| artemis-odb-0.7.0_fast | 124743.88 |  68564.35 |  73968.66 |  74594.16 |      20989.79 |  10600.56 |           |
| artemis-odb-0.8.0      |  63674.39 |  44866.55 |  45665.96 |  46068.08 |      20976.82 |  10543.32 |           |
| artemis-odb-0.8.0_fast | 125121.04 |  69852.11 |  72888.51 |  74644.19 |      21027.77 |  10842.78 |           |
| artemis-odb-0.9.0      |  70819.11 |  40221.96 |  49899.07 |  39994.55 |      20302.01 |  22806.01 |  25367.13 |
| artemis-odb-0.9.0_fast | 126864.88 |  59929.20 |  80561.86 |  59694.18 |      20326.28 |  24403.53 |  28671.33 |
| ashley-1.0.1           |  27085.86 |  13491.52 |           |           |       7836.70 |           |           |
| ashley-1.2.0           |  56411.96 |  19714.13 |           |           |         65.54 |           |           |
| ashley-1.3.1           |  57198.71 |  26956.27 |  27440.45 |           |       6847.31 |           |           |
| ashley-1.3.3           |  57144.87 |  21938.30 |           |           |       6208.95 |           |           |
| gdx-artemis-0.5.0      |  60284.09 |           |  35853.69 |           |       6208.49 |           |           |


#### Benchmarks: 4096 entites

 ![it4k][it4k] ![ir4k][ir4k] ![arc4k][arc4k]

| ECS                    |  baseline | plain     | pooled    | packed    | insert/remove | edit      | transmute |
|------------------------|-----------|-----------|-----------|-----------|---------------|-----------|-----------|
| artemis-odb-0.4.0      |  11834.55 |   5495.30 |           |           |        771.23 |           |           |
| artemis-odb-0.6.5      |  15524.37 |   9732.69 |  10433.00 |  10494.28 |       2260.59 |           |           |
| artemis-odb-0.7.0      |  15921.28 |  10514.76 |  11451.97 |  11361.73 |       4403.86 |   1055.99 |           |
| artemis-odb-0.7.0_fast |  30919.51 |  14863.62 |  17196.86 |  17993.49 |       4540.36 |   1070.45 |           |
| artemis-odb-0.8.0      |  15961.15 |  10579.49 |  11514.04 |  11213.87 |       4465.31 |   1056.34 |           |
| artemis-odb-0.8.0_fast |  30984.95 |  14840.07 |  17017.05 |  17915.89 |       4580.92 |   1088.97 |           |
| artemis-odb-0.9.0      |  17709.24 |  10428.21 |  12309.19 |  10500.35 |       4788.46 |   5391.70 |   6455.04 |
| artemis-odb-0.9.0_fast |  31952.74 |  16759.80 |  20430.08 |  16793.30 |       4850.74 |   5657.18 |   6967.24 |
| ashley-1.0.1           |   7453.63 |   2871.52 |           |           |       1461.58 |           |           |
| ashley-1.2.0           |  12167.34 |   5045.80 |           |           |         17.59 |           |           |
| ashley-1.3.1           |  11980.54 |   5941.24 |   5738.41 |           |       1321.02 |           |           |
| ashley-1.3.3           |  11770.49 |   5115.21 |           |           |       1019.77 |           |           |
| gdx-artemis-0.5.0      |  13933.54 |           |   8042.22 |           |       1473.26 |           |           |


#### Benchmarks: 16384 entites

 ![it16k][it16k] ![ir16k][ir16k] ![arc16k][arc16k]

| ECS                    |  baseline | plain     | pooled    | packed    | insert/remove | edit      | transmute |
|------------------------|-----------|-----------|-----------|-----------|---------------|-----------|-----------|
| artemis-odb-0.4.0      |   3088.49 |   1182.62 |           |           |        163.78 |           |           |
| artemis-odb-0.6.5      |   3143.26 |   2208.04 |   2266.28 |   2510.74 |        370.47 |           |           |
| artemis-odb-0.7.0      |   3998.46 |   2691.43 |   2895.82 |   2923.07 |       1101.89 |     77.32 |           |
| artemis-odb-0.7.0_fast |   7747.07 |   4099.48 |   4167.48 |   4805.38 |       1125.85 |     76.85 |           |
| artemis-odb-0.8.0      |   3997.93 |   2852.84 |   2892.77 |   2925.14 |       1117.11 |     77.23 |           |
| artemis-odb-0.8.0_fast |   7754.60 |   4104.32 |   4207.89 |   4778.39 |       1137.35 |     77.97 |           |
| artemis-odb-0.9.0      |   4466.04 |   3065.22 |   3118.42 |   3116.55 |       1217.95 |   1179.19 |   1420.44 |
| artemis-odb-0.9.0_fast |   7980.66 |   5072.16 |   5106.22 |   5123.86 |       1243.03 |   1260.45 |   1494.95 |
| ashley-1.0.1           |   1828.57 |    335.61 |           |           |        189.38 |           |           |
| ashley-1.2.0           |   2830.75 |    567.75 |           |           |          4.29 |           |           |
| ashley-1.3.1           |   2797.03 |    596.40 |    579.85 |           |        179.37 |           |           |
| ashley-1.3.3           |   2823.36 |    547.25 |           |           |        140.02 |           |           |
| gdx-artemis-0.5.0      |   3490.09 |           |   2102.99 |           |        383.26 |           |           |


#### Benchmarks: 65536 entites

 ![it64k][it64k] ![ir64k][ir64k] ![arc64k][arc64k]

| ECS                    |  baseline | plain     | pooled    | packed    | insert/remove | edit      | transmute |
|------------------------|-----------|-----------|-----------|-----------|---------------|-----------|-----------|
| artemis-odb-0.4.0      |    355.39 |    201.75 |           |           |         20.68 |           |           |
| artemis-odb-0.6.5      |    384.34 |    334.41 |    336.89 |    375.35 |         32.51 |           |           |
| artemis-odb-0.7.0      |    812.58 |    592.62 |    612.00 |    706.85 |         80.87 |      4.92 |           |
| artemis-odb-0.7.0_fast |   1212.26 |    759.79 |    775.49 |   1135.49 |         82.65 |      4.93 |           |
| artemis-odb-0.8.0      |    805.12 |    621.64 |    632.61 |    705.57 |         81.06 |      4.92 |           |
| artemis-odb-0.8.0_fast |   1218.19 |    699.00 |    764.11 |   1118.70 |         83.71 |      4.94 |           |
| artemis-odb-0.9.0      |   1104.93 |    758.36 |    757.73 |    772.03 |        207.09 |    220.09 |    252.25 |
| artemis-odb-0.9.0_fast |   1992.17 |   1234.69 |   1216.76 |   1250.53 |        210.95 |    220.61 |    255.19 |
| ashley-1.0.1           |    194.81 |     52.70 |           |           |         14.51 |           |           |
| ashley-1.2.0           |    240.34 |     53.16 |           |           |          1.00 |           |           |
| ashley-1.3.1           |    241.89 |     60.83 |     59.93 |           |         15.49 |           |           |
| ashley-1.3.3           |    244.96 |     52.82 |           |           |         10.19 |           |           |
| gdx-artemis-0.5.0      |    345.90 |           |    312.26 |           |         47.89 |           |           |





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
 
