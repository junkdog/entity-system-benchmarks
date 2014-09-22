#/bin/bash

# PARAMS="-i 1 -wi 1 -r 1 -p entityCount=4096"

java -jar artemis-odb-0.4.0/target/microbenchmarks.jar -o results/artemis-odb-0.4.0.csv -rf csv $PARAMS
java -jar artemis-odb-0.6.5/target/microbenchmarks.jar -o results/artemis-odb-0.6.5.csv -rf csv $PARAMS
java -jar artemis-odb-0.7.0/target/microbenchmarks.jar -o results/artemis-odb-0.7.0.csv -rf csv $PARAMS
java -jar ashley-1.0.1/target/microbenchmarks.jar -o results/ashley-1.0.1.csv -rf csv $PARAMS
java -jar ashley-1.2.0/target/microbenchmarks.jar -o results/ashley-1.2.0.csv -rf csv $PARAMS
java -jar gdx-artemis-0.5.0/target/microbenchmarks.jar -o results/gdx-artemis-0.5.0.csv -rf csv $PARAMS

