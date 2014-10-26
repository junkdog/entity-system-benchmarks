package com.github.esfbench.chartgen.model;

import static com.github.esfbench.chartgen.model.Benchmark.BenchmarkGroup.ITERATION;
import static com.github.esfbench.chartgen.model.Benchmark.BenchmarkGroup.PROCESSING;
import static com.github.esfbench.chartgen.model.Benchmark.BenchmarkGroup.THRESHOLD;
import static java.lang.String.format;

import com.github.esfbench.chartgen.model.Benchmark.BenchmarkGroup;

public enum BenchmarkType {
	BASELINE("baseline", THRESHOLD),
	PLAIN("plain", ITERATION),
	POOLED("pooled", ITERATION),
	PACKED("packed", ITERATION),
	INSERT_REMOVE("insert_remove", PROCESSING);
	
	public final String id;
	public final BenchmarkGroup group;
	
	private BenchmarkType(String id, BenchmarkGroup group) {
		this.id = id;
		this.group = group;
	}
	
	public static BenchmarkType parse(String benchmark) {
		String id = benchmark.substring(benchmark.lastIndexOf('.') + 1);
		for (BenchmarkType bt : values()) {
			if (id.equals(bt.id))
				return bt;
		}
		
		throw new RuntimeException(format("No type identified by '%s'", benchmark));
	}
}