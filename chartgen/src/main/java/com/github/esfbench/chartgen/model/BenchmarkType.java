package com.github.esfbench.chartgen.model;

import static com.github.esfbench.chartgen.model.Benchmark.*;
import static java.lang.String.format;

public enum BenchmarkType {
	BASELINE("baseline", MODE_BASELINE),
	
	PLAIN("plain", MODE_ITERATION),
	PACKED("packed", MODE_ITERATION),
	POOLED("pooled", MODE_ITERATION),
	
	INSERT_REMOVE("insert_remove", MODE_INSERT_REMOVE);
	
	private final String id;
	private final int group;
	
	private BenchmarkType(String id, int group) {
		this.id = id;
		this.group = group;
	}
	
	public static BenchmarkType parse(String benchmark) {
		
		String id = benchmark.substring(benchmark.lastIndexOf('.'));
		id = id.replaceAll("_fast$", "");
		
		for (BenchmarkType bt : values()) {
			if (id.equals(bt.id))
				return bt;
		}
		
		throw new RuntimeException(format("No type identified by '%s'", benchmark));
	}
}