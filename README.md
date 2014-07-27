## entity-system-benchmarks


## Benchmarks description:
#### All benchmarks:
- Have a position update system.
- A Entity deleter system: each 100th tick an entity is deleted and a new entity is creted the next tick.
- Runs with 1024, 4096, 16384 and 65536 entities.
 
#### Benchmark types:
- *baseline:* position isn't updated
- *plain:* position component is the default component type
- *pooled:* pooling all components. Only artemis-odb > 0.5.0 atm.
- *packed:* packed components (backed by a Bytebuffer). Only artemis-odb > 0.5.0 atm.


## (Preliminary) Benchmarks

Lower score is better.

Still missing:
- Pooling with ashley
- More ESF:s - entreri, gdx-artemis, etc.
 
As usual, writing microbenchmarks is tricky business - take the numbers with a grain of salt. These benchmarks should not be interpreted as benchmarking each framework as a whole.


Benchmark | Framework | (entityCount) | Mode | Score | error | Units
----------|-----------|---------------|------|-------|-------|-------
baseline | artemis-0.4.0 | 1024 | avgt | 2.915 | 0.287 | us/op
baseline | artemis-0.6.5 | 1024 | avgt | 2.925 | 0.042 | us/op
pooled | artemis-0.6.5 | 1024 | avgt | 5.407 | 0.767 | us/op
plain | artemis-0.6.5 | 1024 | avgt | 5.885 | 0.311 | us/op
plain | artemis-0.4.0 | 1024 | avgt | 6.544 | 2.655 | us/op
baseline | ashley-1.0.1 | 1024 | avgt | 7.250 | 1.676 | us/op
packed | artemis-0.6.5 | 1024 | avgt | 8.397 | 0.334 | us/op
plain | ashley-1.0.1 | 1024 | avgt | 24.445 | 4.16 | us/op

Benchmark | Framework | (entityCount) | Mode | Score | error | Units
----------|-----------|---------------|------|-------|-------|-------
baseline | artemis-0.4.0 | 4096 | avgt | 11.525 | 1.14 | us/op
baseline | artemis-0.6.5 | 4096 | avgt | 11.800 | 0.55 | us/op
pooled | artemis-0.6.5 | 4096 | avgt | 25.946 | 0.418 | us/op
plain | artemis-0.6.5 | 4096 | avgt | 31.141 | 5.907 | us/op
packed | artemis-0.6.5 | 4096 | avgt | 32.997 | 1.05 | us/op
baseline | ashley-1.0.1 | 4096 | avgt | 33.353 | 47.442 | us/op
plain | artemis-0.4.0 | 4096 | avgt | 35.647 | 11.871 | us/op
plain | ashley-1.0.1 | 4096 | avgt | 76.840 | 8.491 | us/op

Benchmark | Framework | (entityCount) | Mode | Score | error | Units
----------|-----------|---------------|------|-------|-------|-------
baseline | artemis-0.4.0 | 16384 | avgt | 45.941 | 9.776 | us/op
baseline | artemis-0.6.5 | 16384 | avgt | 48.007 | 4.246 | us/op
pooled | artemis-0.6.5 | 16384 | avgt | 104.533 | 29.109 | us/op
plain | artemis-0.6.5 | 16384 | avgt | 106.709 | 43.646 | us/op
baseline | ashley-1.0.1 | 16384 | avgt | 127.311 | 8.656 | us/op
packed | artemis-0.6.5 | 16384 | avgt | 131.482 | 6.701 | us/op
plain | artemis-0.4.0 | 16384 | avgt | 141.881 | 117.303 | us/op
plain | ashley-1.0.1 | 16384 | avgt | 779.868 | 69.083 | us/op

Benchmark | Framework | (entityCount) | Mode | Score | error | Units
----------|-----------|---------------|------|-------|-------|-------
baseline | artemis-0.4.0 | 65536 | avgt | 326.183 | 105.805 | us/op
baseline | artemis-0.6.5 | 65536 | avgt | 329.999 | 90.766 | us/op
packed | artemis-0.6.5 | 65536 | avgt | 709.661 | 27.836 | us/op
pooled | artemis-0.6.5 | 65536 | avgt | 888.748 | 30.175 | us/op
plain | artemis-0.6.5 | 65536 | avgt | 908.817 | 54.062 | us/op
baseline | ashley-1.0.1 | 65536 | avgt | 1166.514 | 25.681 | us/op
plain | artemis-0.4.0 | 65536 | avgt | 1427.454 | 165.22 | us/op
plain | ashley-1.0.1 | 65536 | avgt | 5086.022 | 781.29 | us/op
