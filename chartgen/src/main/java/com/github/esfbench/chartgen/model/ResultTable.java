package com.github.esfbench.chartgen.model;

import java.io.PrintStream;
import java.util.*;

public class ResultTable {
	private Map<String,FrameworkSummary> frameworks;

	public ResultTable(Set<Benchmark> benchmarks) {
		frameworks = new TreeMap<>();
		
		for (Benchmark benchmark : benchmarks) {
			transpose(benchmark, frameworkSummary(benchmark));
		}
	}
	
	private static void transpose(Benchmark benchmark, FrameworkSummary summary) {
		switch (benchmark.type) {
			case BASELINE:
				summary.baseline = benchmark.score;
				break;
			case CREATE_DELETE:
				summary.insertRemove = benchmark.score;
				break;
			case PACKED:
				summary.packed = benchmark.score;
				break;
			case PLAIN:
				summary.plain = benchmark.score;
				break;
			case POOLED:
				summary.pooled = benchmark.score;
				break;
			case ENTITY_EDIT:
				summary.entityEdit = benchmark.score;
				break;
			case ENTITY_TRANSMUTE:
				summary.entityTransmute = benchmark.score;
				break;
			default:
				if (!benchmark.type.name().endsWith("_LEGACY"))
					throw new RuntimeException("missing case: " + benchmark.type);
			
		}
	}

	private FrameworkSummary frameworkSummary(Benchmark benchmark) {
		FrameworkSummary summary = frameworks.get(benchmark.framework);
		if (summary == null) {
			summary = new FrameworkSummary();
			summary.framework = benchmark.framework;
			frameworks.put(benchmark.framework, summary);
		}
		
		return summary;
	}

	public void printTable(PrintStream out) {
		out.println("| ECS                     |  baseline | plain     | pooled    | packed    | insert/remove | edit      | transmute |");
		out.println("|-------------------------|-----------|-----------|-----------|-----------|---------------|-----------|-----------|");
		Collection<FrameworkSummary> values = new ArrayList<>(frameworks.values());
		for (FrameworkSummary summary : values) {
			String row = String.format(
				"| %-23s | %9.2f | %9.2f | %9.2f | %9.2f | %13.2f | %9.2f | %9.2f |",
					summary.framework,
					summary.baseline,
					summary.plain,
					summary.pooled,
					summary.packed,
					summary.insertRemove,
					summary.entityEdit,
					summary.entityTransmute);
			
			out.println(row.replaceAll("0\\.00", "    "));
		}
		
	}

	private static class FrameworkSummary implements Comparator<FrameworkSummary> {
		public String framework;
		public float baseline;
		public float plain;
		public float pooled;
		public float packed;
		public float insertRemove;
		public float entityEdit;
		public float entityTransmute;

		@Override
		public String toString() {
			return super.toString();
		}


		@Override
		public int compare(FrameworkSummary o1, FrameworkSummary o2) {
			return compareString().compareTo(o2.compareString());
		}

		String compareString() {
			return framework.replaceAll("\\.([0-9])\\.", ". $1.");
		}
	}
}
