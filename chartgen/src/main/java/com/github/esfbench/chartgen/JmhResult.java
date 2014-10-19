package com.github.esfbench.chartgen;

public class JmhResult {
	public String benchmark;
	public Measurement primaryMetric = new Measurement();
	public Params params = new Params();
	
	public static class Params {
		public String entityCount;
	}
	
	public static class Measurement {
		public float score;
		public float scoreError;
	}
}
