package com.github.esfbench.chartgen.model;


public class Benchmark implements Comparable<Benchmark> {
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

	@Override
	public int compareTo(Benchmark other) {
		String s = compareString();
		return compareString().compareTo(other.compareString());
	}

	private String compareString() {
		String s = type.ordinal() + framework.replaceAll("\\.([0-9])\\.", ". $1.") + entityCount;
		return s;
	}
	
	@Override
	public String toString() {
		return String.format("Benchmark[%s (%s) %d entities]",
				this.type, this.framework, this.entityCount);
	}
	
	public enum BenchmarkGroup {
		THRESHOLD, ITERATION, INSERT_REMOVE, TRANSMUTE;
	}
}
