package com.github.esfbench.chartgen.model;

import org.openjdk.jmh.runner.RunnerException;

public class Benchmark {
	public static final int MODE_BASELINE = 0;
	public static final int MODE_ITERATION = 1;
	public static final int MODE_INSERT_REMOVE = 1;
	
	
	public String framework;
	public int entityCount;
	public BenchmarkType type;
	public float score;
	public boolean bytecodeOptimized;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (bytecodeOptimized ? 1231 : 1237);
		result = prime * result + entityCount;
		result = prime * result
				+ ((framework == null) ? 0 : framework.hashCode());
		result = prime * result + Float.floatToIntBits(score);
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Benchmark other = (Benchmark) obj;
		if (bytecodeOptimized != other.bytecodeOptimized)
			return false;
		if (entityCount != other.entityCount)
			return false;
		if (framework == null) {
			if (other.framework != null)
				return false;
		} else if (!framework.equals(other.framework))
			return false;
		if (Float.floatToIntBits(score) != Float.floatToIntBits(other.score))
			return false;
		if (type != other.type)
			return false;
		return true;
	}
	
	
}
