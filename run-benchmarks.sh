#/bin/bash

# for debugging:
# PARAMS="-i 1 -wi 1 -r 1 -p entityCount=4096"

function run_bench() {
	java -jar $1/target/microbenchmarks.jar -rf json -rff results/$1.json $PARAMS | tee results/$1.log
}

mvn clean install
run_bench artemis-odb-0.4.0
run_bench artemis-odb-0.6.5
run_bench artemis-odb-0.7.0
run_bench ashley-1.0.1
run_bench ashley-1.2.0
run_bench gdx-artemis-0.5.0
mvn -Pfast clean install -f artemis-odb-0.7.0/pom.xml
run_bench artemis-odb-0.7.0
