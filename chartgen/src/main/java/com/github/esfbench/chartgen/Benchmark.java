package com.github.esfbench.chartgen;

import com.github.esfbench.chartgen.json.JmhResult;

public class Benchmark {
	
	private int iterationCount;

	public Benchmark(int iterationCount) {
		this.iterationCount = iterationCount;
	}
	
	public void add(String framework, JmhResult... results) {
		for (JmhResult result : results)
			add(framework, result);
	}
	
	public void add(String framework, JmhResult result) {
		
	}
}
