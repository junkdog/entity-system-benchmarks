package com.github.esfbench.chartgen;

import java.io.File;
import java.io.IOException;

import com.github.esfbench.chartgen.json.JmhResult;

public class Framework {
	private final JmhResult[] results;

	public Framework(File file) throws IOException {
		this.results = BenchmarkUtil.fromJson(file);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (JmhResult result : results)
			sb.append(result).append("\n");
		
		return sb.toString();
	}
}
