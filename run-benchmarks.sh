#/bin/bash

# for debugging:
#PARAMS="-i 1 -wi 1 -r 1 -p entityCount=4096,16384"
#PARAMS="-f 1 -p entityCount=4096,16384"
#PARAMS="-f 1 -p entityCount=16384,65535"
#PARAMS="-i 1 -r 3 -f 1 -p entityCount=65535"
#PARAMS="-i 3 -wi 10 -r 10 -p entityCount=4096,16384,65536,262144"
#PARAMS="-i 3 -wi 5 -r 5 -p entityCount=65356,262144"
#PARAMS="-i 3 -wi 3 -r 5 -p entityCount=16384,262144"
#PARAMS="-i 3 -wi 5 -r 5 -p entityCount=16384,65536,262144"
#PARAMS="-i 5 -wi 5 -r 5 -p entityCount=16384,65536,262144"
#PARAMS="-i 3 -wi 5 -r 5 -p entityCount=65536,262144"
#PARAMS="-i 1 -wi 1 -r 1 -p entityCount=262144"


function run_bench() {
#	java -jar $1/target/microbenchmarks.jar ".*\.(ins|pla|ent|tra).*" -rf json -rff results/$1$2.json $PARAMS | tee results/$1$2.log
#	java -XX:+UnlockCommercialFeatures -XX:+FlightRecorder -jar $1/target/microbenchmarks.jar ".*" -e ".*(baseline|packed|plain|legacy).*" -rf json -rff results/$1$2.json $PARAMS | tee results/$1$2.log
#		-prof stack -prof hs_rt \
	java -jar $1/target/microbenchmarks.jar \
		".*" \
		-rf json -rff results/$1$2.json $PARAMS | tee results/$1$2.log
#		-e ".*(baseline|packed|plain|legacy).*" \

#	java -XX:+UnlockCommercialFeatures \
#		-XX:+FlightRecorder \
#		-XX:StartFlightRecording=duration=60s,filename=./profiling-data.jfr,name=FULL,settings=profile \
#		-jar $1/target/microbenchmarks.jar \
#		".*insert.*" \
#		-e ".*(baseline|packed|plain|pool|trans|edit|legacy).*" \
#		-rf json -rff results/$1$2.json $PARAMS | tee results/$1$2.log
#	java -jar $1/target/microbenchmarks.jar ".*" -e ".*(\.packed|_legacy|baseline).*" -rf json -rff results/$1$2.json $PARAMS | tee results/$1$2.log
	#java -XX:-UseCompressedOops -jar $1/target/microbenchmarks.jar ".*" -e ".*(\.packed|_legacy|baseline).*" -rf json -rff results/$1$2.json $PARAMS | tee results/$1$2.log
	# java -jar $1/target/microbenchmarks.jar ".*\.(plain|pooled).*" -rf json -rff results/$1$2.json $PARAMS | tee results/$1$2.log
}

function run_all() {
	# running basic benchmarks
	mvn clean install
	run_bench artemis-odb-0.4.0
	run_bench artemis-odb-0.9.0
	run_bench artemis-odb-1.0.1
	run_bench artemis-odb-2.0.0
	run_bench artemis-odb-2.0.0-RC1 
	run_bench artemis-odb-2.0.0-RC2
	run_bench ashley-1.6.0
	run_bench gdx-artemis-0.5.0
	run_bench retinazer-0.2.0

	# recompiling with bytecode optimizations
	mvn -P fast clean install
	run_bench artemis-odb-0.9.0 _fast
	run_bench artemis-odb-1.0.1 _fast
	run_bench artemis-odb-2.0.0-RC1 _fast
	run_bench artemis-odb-2.0.0-RC2 _fast
}


function run_dev() {
	mvn -P fast clean install -DskipTests
#	run_bench artemis-odb-2.0.0-RC1 _fast
	run_bench artemis-odb-2.0.0-RC2 _fast
}

run_all
#run_dev

