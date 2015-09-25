#/bin/bash

# for debugging:
#PARAMS="-i 1 -wi 1 -r 1 -p entityCount=4096,16384"
#PARAMS="-f 1 -p entityCount=4096,16384"
#PARAMS="-f 1 -p entityCount=16384,65535"
#PARAMS="-i 1 -r 3 -f 1 -p entityCount=65535"

function run_bench() {
	# java -jar $1/target/microbenchmarks.jar ".*\.(ins|pla|ent|tra).*" -rf json -rff results/$1$2.json $PARAMS | tee results/$1$2.log
	java -jar $1/target/microbenchmarks.jar ".*" -e ".*\.packed.*" -rf json -rff results/$1$2.json $PARAMS | tee results/$1$2.log
}

function run_all() {
	# running basic benchmarks
	mvn clean install
	run_bench artemis-odb-0.4.0
	run_bench artemis-odb-0.9.0
	run_bench artemis-odb-0.13.1
	run_bench artemis-odb-1.0.0
	run_bench ashley-1.0.1
	run_bench ashley-1.6.0
	run_bench gdx-artemis-0.5.0

	# recompiling with bytecode optimizations
	mvn -Pfast clean install
	run_bench artemis-odb-0.9.0 _fast
	run_bench artemis-odb-0.13.1 _fast
	run_bench artemis-odb-1.0.0 _fast
}


function run_dev() {
	# running basic benchmarks
#	run_bench artemis-odb-0.9.0
#	mvn clean install -f artemis-odb-1.0.0
#	mvn clean install -f artemis-odb-0.13.1
#	run_bench artemis-odb-0.10.0

	# recompoling with bytecode optimizations
#	mvn -Pfast clean install
#	run_bench artemis-odb-0.9.0 _fast
#	run_bench artemis-odb-0.10.2
#	run_bench artemis-odb-0.11.1 _fast
#	run_bench artemis-odb-0.11.4 _fast
#	run_bench artemis-odb-0.11.4
#	run_bench artemis-odb-0.13.1
	run_bench artemis-odb-1.0.0
}

run_dev

