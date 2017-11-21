#!/bin/sh

echo 'Cleaning class directory...'
mvn clean

echo 'Compiling sources and building jar...'
mvn assembly:assembly

echo 'Copying jar to benchmark repository...'
cp target/benchmark-0.0.1-SNAPSHOT-jar-with-dependencies.jar $AS_BENCH/benchmark.jar

echo 'Benchmark manager for Autoscale updated' 


