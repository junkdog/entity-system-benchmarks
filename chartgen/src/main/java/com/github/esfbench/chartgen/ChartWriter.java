package com.github.esfbench.chartgen;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import com.github.esfbench.chartgen.json.JmhResult;

public final class ChartWriter {
	private ChartWriter() {}
	
	public static void writeChart(String benchmark, InputStream input) throws IOException {
		JmhResult[] results = BenchmarkUtil.fromJson(benchmark, input);
		DefaultCategoryDataset dataset = BenchmarkUtil.toDataset(results);
		JFreeChart chart = ChartFactory.createBarChart(
				"title", "categoryAxisLabel", "valueAxisLabel", dataset,
				PlotOrientation.HORIZONTAL, true, false, false);
		
		String pngFile = benchmark.substring(0, benchmark.lastIndexOf(".")) + ".png";
		
		ChartUtilities.saveChartAsPNG(new File(pngFile), chart, 500, 300);
	}
	
	public static void writeChart(File file) throws IOException {
		try (FileInputStream fis = new FileInputStream(file)) {
			writeChart(file.getName(), fis);
		}
	}
}
