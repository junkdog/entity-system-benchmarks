package com.github.esfbench.chartgen.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class BenchmarkCollection {
	
	private Map<Integer, Set<Benchmark>> benchmarks = new HashMap<Integer, Set<Benchmark>>();
	
	
	public void add(Benchmark benchmark) {
		benchmarkSet(benchmark.entityCount).add(benchmark);
	}
	
	private Set<Benchmark> benchmarkSet(int entityCount) {
		Set<Benchmark> set = benchmarks.get(entityCount);
		if (set == null) {
			set = new HashSet<>();
			benchmarks.put(entityCount, set);
		}
		return set;
	}
}
