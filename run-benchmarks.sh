#/bin/bash
java -jar ashley-1.0.1/target/microbenchmarks.jar -o results/ashley-bench.csv -rf csv 
java -jar artemis-odb-0.4.0/target/microbenchmarks.jar -o results/artemis-0.4.0-bench.csv -rf csv
java -jar artemis-odb-0.6.5/target/microbenchmarks.jar -o results/artemis-0.6.5-bench.csv -rf csv

