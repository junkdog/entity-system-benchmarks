package com.github.esfbench.chartgen.json;

import com.github.esfbench.chartgen.model.Benchmark;
import com.github.esfbench.chartgen.model.BenchmarkType;

public class JmhResult {
	public String benchmark;
	public Measurement primaryMetric = new Measurement();
	public Params params = new Params();
	
	@Override
	public String toString() {
		return "JmhResult [benchmark=" + benchmark + ", primaryMetric="
				+ primaryMetric + ", params=" + params + "]";
	}

	public static class Params {
		public String entityCount;

		@Override
		public String toString() {
			return "Params [entityCount=" + entityCount + "]";
		}
	}
	
	public Benchmark toBenchmark(String frameworkId) {
		Benchmark benchmark = new Benchmark();
		benchmark.entityCount = Integer.parseInt(params.entityCount);
		benchmark.framework = frameworkId;
		benchmark.score = primaryMetric.score;
		benchmark.type = BenchmarkType.parse(this.benchmark);
		
		return benchmark;
	}
	
	public static class Measurement {
		public float score;
		public float scoreError;
		
		@Override
		public String toString() {
			return "Measurement [score=" + score + ", scoreError=" + scoreError
					+ "]";
		}
	}
}
