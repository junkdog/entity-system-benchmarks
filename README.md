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

| ECS                    |  baseline | plain     | pooled    | packed    | insert/remove |
|------------------------|-----------|-----------|-----------|-----------|---------------|
| artemis-odb-0.4.0      |  61175.32 |  34647.63 |           |           |       3292.48 |
| artemis-odb-0.6.5      |  60967.15 |  40631.96 |  44571.27 |  44311.12 |      11538.80 |
| artemis-odb-0.7.0      |  62050.34 |  44247.37 |  46363.48 |  45099.71 |      19898.82 |
| artemis-odb-0.7.0_fast | 124938.34 |  68800.17 |  72538.12 |  73406.73 |      20254.61 |
| artemis-odb-0.8.0_fast | 124466.97 |  69857.21 |  73179.98 |  74353.98 |      20033.31 |
| ashley-1.0.1           |  26942.38 |  12636.55 |           |           |       8322.38 |
| ashley-1.2.0           |  56800.80 |  20069.31 |           |           |         63.04 |
| ashley-1.3.1           |  56136.22 |  17477.16 |           |           |         64.36 |
| gdx-artemis-0.5.0      |  60081.48 |           |  35972.88 |           |       6317.69 |


#### Benchmarks: 4096 entites

 ![it4k][it4k] ![ir4k][ir4k]

| ECS                    |  baseline | plain     | pooled    | packed    | insert/remove |
|------------------------|-----------|-----------|-----------|-----------|---------------|
| artemis-odb-0.4.0      |  14376.68 |   7998.63 |           |           |        756.55 |
| artemis-odb-0.6.5      |  15431.03 |   9382.12 |  10473.96 |  10215.41 |       2294.93 |
| artemis-odb-0.7.0      |  15725.84 |  10515.59 |  10576.04 |  11229.46 |       4391.83 |
| artemis-odb-0.7.0_fast |  30697.97 |  14761.34 |  16935.83 |  17907.72 |       4494.91 |
| artemis-odb-0.8.0_fast |  30800.86 |  14716.75 |  16698.96 |  17821.35 |       4507.65 |
| ashley-1.0.1           |   7221.68 |   2324.94 |           |           |       1415.94 |
| ashley-1.2.0           |  11809.98 |   4056.74 |           |           |         16.82 |
| ashley-1.3.1           |  11843.76 |   4059.21 |           |           |         17.22 |
| gdx-artemis-0.5.0      |  13779.55 |           |   7854.56 |           |       1409.98 |


#### Benchmarks: 16384 entites

 ![it16k][it16k] ![ir16k][ir16k]

| ECS                    |  baseline | plain     | pooled    | packed    | insert/remove |
|------------------------|-----------|-----------|-----------|-----------|---------------|
| artemis-odb-0.4.0      |   3377.64 |   1850.21 |           |           |        161.01 |
| artemis-odb-0.6.5      |   3922.94 |   2822.15 |   2908.78 |   2787.10 |        357.51 |
| artemis-odb-0.7.0      |   3983.98 |   2892.15 |   2782.90 |   2906.48 |       1114.61 |
| artemis-odb-0.7.0_fast |   7869.90 |   4682.28 |   4789.78 |   4826.28 |       1126.15 |
| artemis-odb-0.8.0_fast |   7871.96 |   4624.92 |   4735.83 |   4805.10 |       1136.84 |
| ashley-1.0.1           |   1829.45 |    214.05 |           |           |        189.47 |
| ashley-1.2.0           |   1512.03 |    339.71 |           |           |          4.24 |
| ashley-1.3.1           |   2182.99 |    338.22 |           |           |               |
| gdx-artemis-0.5.0      |   3566.84 |           |   2295.99 |           |        355.05 |


#### Benchmarks: 65536 entites

 ![it64k][it64k] ![ir64k][ir64k]

| ECS                    |  baseline | plain     | pooled    | packed    | insert/remove |
|------------------------|-----------|-----------|-----------|-----------|---------------|
| artemis-odb-0.4.0      |    385.63 |    260.70 |           |           |         20.17 |
| artemis-odb-0.6.5      |    992.99 |    679.05 |    696.92 |    666.39 |         31.61 |
| artemis-odb-0.7.0      |    966.16 |    654.89 |    663.08 |    657.64 |         79.79 |
| artemis-odb-0.7.0_fast |   1950.28 |   1076.50 |   1018.77 |   1055.38 |         81.48 |
| artemis-odb-0.8.0_fast |   1939.72 |    898.97 |    929.32 |   1113.98 |         79.98 |
| ashley-1.0.1           |    199.64 |     53.04 |           |           |         13.73 |
| ashley-1.2.0           |    263.84 |     56.04 |           |           |          0.97 |
| ashley-1.3.1           |    261.16 |     58.64 |           |           |               |
| gdx-artemis-0.5.0      |    469.48 |           |    347.79 |           |         45.70 |



 [fast]: https://github.com/junkdog/artemis-odb/wiki/Optimizing-Entity-Systems
 [it1k]: http://junkdog.github.io/images/ecs-bench/iteration__1024.png
 [it4k]: http://junkdog.github.io/images/ecs-bench/iteration__4096.png
 [it16k]: http://junkdog.github.io/images/ecs-bench/iteration__16384.png
 [it65k]: http://junkdog.github.io/images/ecs-bench/iteration__65536.png
 [ir1k]: http://junkdog.github.io/images/ecs-bench/insert_remove__1024.png
 [ir4k]: http://junkdog.github.io/images/ecs-bench/insert_remove__4096.png
 [ir16k]: http://junkdog.github.io/images/ecs-bench/insert_remove__16384.png
 [ir65k]: http://junkdog.github.io/images/ecs-bench/insert_remove__65536.png
 
