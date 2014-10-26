package com.github.esfbench.chartgen;

import static com.github.esfbench.chartgen.BenchmarkUtil.toDatasets;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.openjdk.jmh.runner.RunnerException;

import com.github.esfbench.chartgen.json.JmhResult;
import com.github.esfbench.chartgen.model.Benchmark;
import com.github.esfbench.chartgen.model.Benchmark.BenchmarkGroup;
import com.github.esfbench.chartgen.model.BenchmarkCollection;

public final class ChartWriterFactory {
	private ChartWriterFactory() {}
	
	private static void generateChart(String benchmark, DefaultCategoryDataset dataset) {
		JFreeChart chart = ChartFactory.createBarChart(
				benchmark, "framework", "throughput", dataset,
				PlotOrientation.HORIZONTAL, true, false, false);
		
		CategoryPlot plot = chart.getCategoryPlot();
		BarRenderer renderer = (BarRenderer) plot.getRenderer();
		renderer.setItemMargin(0);
		
		
		String pngFile = getOutputName(benchmark);
		
		try {
			ChartUtilities.saveChartAsPNG(new File(pngFile), chart, 600, 500);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void generate(String benchmark, Map<Integer, DefaultCategoryDataset> data) {
		
		for (Entry<Integer, DefaultCategoryDataset> entry : data.entrySet()) {
			String title = benchmark + ": " + entry.getKey() + " entities";
			generateChart(title, entry.getValue());
		}
	}
	
	private static String getOutputName(String benchmark) {
		return benchmark.replaceAll("[: /]", "_") + ".png";
	}
	
	public static void main(String[] args) {
		File logs = new File("/home/junkdog/opt/dev/entity-system-benchmarks/results");
		BenchmarkCollection bc = new BenchmarkCollection(logs);
		
		for (BenchmarkGroup group : BenchmarkGroup.values()) {
			System.out.println("processing: " + group);
			
			Map<Integer, Set<Benchmark>> benchmarks = bc.getBenchmarks(group);
			Map<Integer, DefaultCategoryDataset> datasets = toDatasets(benchmarks);
			for (Entry<Integer, DefaultCategoryDataset> entry : datasets.entrySet()) {
				System.out.printf("\t%d: %s benchmarks\n", entry.getKey(), benchmarks.get(entry.getKey()).size());
				generateChart(title(group, entry.getKey()), entry.getValue());
			}
		}
	}

	private static String title(BenchmarkGroup group, int entityCount) {
		switch (group) {
			case ITERATION:
				return "iteration: " + entityCount + " entities";
			case PROCESSING:
				return "insert/remove: " + entityCount + " entities";
			case THRESHOLD:
				return "baseline: " + entityCount + " entities";
			default:
				throw new RuntimeException(group.name());
		}
	}
}
