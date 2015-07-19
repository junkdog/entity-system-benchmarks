#/bin/bash

# for debugging:
# PARAMS="-i 1 -wi 1 -r 1 -p entityCount=4096,16384"
PARAMS="-p entityCount=4096,16384"

function run_bench() {
	java -jar $1/target/microbenchmarks.jar ".*\.p[l].*" -rf json -rff results/$1$2.json $PARAMS | tee results/$1$2.log
}

function run_all() {
	# running basic benchmarks
	mvn clean install
	run_bench artemis-odb-0.4.0
	run_bench artemis-odb-0.6.5
	run_bench artemis-odb-0.7.0
	run_bench artemis-odb-0.8.0
	run_bench artemis-odb-0.9.0
	run_bench artemis-odb-0.10.0
	run_bench ashley-1.0.1
	run_bench ashley-1.2.0
	run_bench ashley-1.3.1
	run_bench ashley-1.3.3
	run_bench gdx-artemis-0.5.0


	# recompoling with bytecode optimizations
	mvn -Pfast clean install
	run_bench artemis-odb-0.7.0 _fast
	run_bench artemis-odb-0.8.0 _fast
	run_bench artemis-odb-0.9.0 _fast
	run_bench artemis-odb-0.10.0 _fast
}


function run_dev() {
	# running basic benchmarks
#	mvn clean install
#	run_bench artemis-odb-0.9.0
#	run_bench artemis-odb-0.10.0

	# recompoling with bytecode optimizations
#	mvn -Pfast clean install
#	run_bench artemis-odb-0.9.0 _fast
	run_bench artemis-odb-0.10.0 _fast
}

run_dev

