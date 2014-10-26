package com.github.esfbench.chartgen.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import com.github.esfbench.chartgen.BenchmarkUtil;
import com.github.esfbench.chartgen.json.JmhResult;
import com.github.esfbench.chartgen.model.Benchmark.BenchmarkGroup;

public class BenchmarkCollection {
	
	private final Map<Integer, Set<Benchmark>> benchmarks = new TreeMap<>();
	
	public BenchmarkCollection() {}
	
	public BenchmarkCollection(File logDir) {
		List<File> logFiles = new ArrayList<>();
		for (String file : logDir.list()) {
			if (file.endsWith(".json"))
				logFiles.add(new File(logDir, file));
		}
		
		for (File f : logFiles) {
			for (JmhResult result : BenchmarkUtil.fromJson(f)) {
				String name = f.getName();
				Benchmark benchmark = result.toBenchmark(name.substring(0, name.lastIndexOf(".")));
				add(benchmark);
			}
		}
	}
	
	
	private void add(Benchmark benchmark) {
		Set<Benchmark> benchmarks = benchmarkSet(benchmark.entityCount);
		benchmarks.add(benchmark);
	}
	
	private Set<Benchmark> benchmarkSet(int entityCount) {
		Set<Benchmark> set = benchmarks.get(entityCount);
		if (set == null) {
			set = new TreeSet<>();
			benchmarks.put(entityCount, set);
		}
		return set;
	}
	
	public int[] getEntityCounts() {
		int[] counts = new int[benchmarks.size()];
		int i = 0;
		for (Integer count : benchmarks.keySet())
			counts[i++] = count;
		
		Arrays.sort(counts);
		return counts;
	}
	
	public Map<Integer, Set<Benchmark>> getBenchmarks(BenchmarkGroup group) {
		BenchmarkCollection bc = new BenchmarkCollection();
		
		for (Set<Benchmark> benchmarks : this.benchmarks.values()) {
			for (Benchmark benchmark : benchmarks) {
				if (benchmark.type.group == group)
					bc.add(benchmark);
			}
		}
		
		return bc.benchmarks;
	}
}
