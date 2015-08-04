package com.github.esfbench.chartgen;

import static com.github.esfbench.chartgen.BenchmarkUtil.toDatasets;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.category.DefaultCategoryDataset;

import com.github.esfbench.chartgen.model.Benchmark;
import com.github.esfbench.chartgen.model.Benchmark.BenchmarkGroup;
import com.github.esfbench.chartgen.model.BenchmarkCollection;
import com.github.esfbench.chartgen.model.ResultTable;
import org.jfree.ui.RectangleInsets;

public final class ChartWriterFactory {
	private ChartWriterFactory() {}
	
	private static void generateChart(String benchmark, DefaultCategoryDataset dataset, int benchmarkCount) {
		JFreeChart chart = ChartFactory.createBarChart(
				benchmark, "framework", "throughput", dataset,
				PlotOrientation.HORIZONTAL, true, false, false);

		CategoryPlot plot = chart.getCategoryPlot();
		BarRenderer renderer = (BarRenderer) plot.getRenderer();
		renderer.setItemMargin(0);
		notSoUglyPlease(chart);
		
		String pngFile = getOutputName(benchmark);
		
		try {
			int height = 100 + benchmarkCount * 20;
			ChartUtilities.saveChartAsPNG(new File(pngFile), chart, 700, height);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	// based off http://stackoverflow.com/a/13345781/431535
	private static void notSoUglyPlease(JFreeChart chart) {
		String fontName = "Lucida Sans";

		StandardChartTheme theme = (StandardChartTheme)org.jfree.chart.StandardChartTheme.createJFreeTheme();

		theme.setTitlePaint( Color.decode("#4572a7") );
		theme.setExtraLargeFont(new Font(fontName, Font.BOLD, 14)); //title
		theme.setLargeFont(new Font(fontName, Font.BOLD, 15)); //axis-title
		theme.setRegularFont(new Font(fontName, Font.PLAIN, 11));
		theme.setRangeGridlinePaint(Color.decode("#C0C0C0"));
		theme.setPlotBackgroundPaint(Color.white);
		theme.setChartBackgroundPaint(Color.white);
		theme.setGridBandPaint(Color.red);
		theme.setAxisOffset(new RectangleInsets(0, 0, 0, 0));
		theme.setBarPainter(new StandardBarPainter());
		theme.setAxisLabelPaint(Color.decode("#666666"));
		theme.apply(chart);
		chart.getCategoryPlot().setOutlineVisible(false);
		chart.getCategoryPlot().getRangeAxis().setAxisLineVisible(false);
		chart.getCategoryPlot().getRangeAxis().setTickMarksVisible(false);
		chart.getCategoryPlot().setRangeGridlineStroke(new BasicStroke());
		chart.getCategoryPlot().getRangeAxis().setTickLabelPaint(Color.decode("#666666"));
		chart.getCategoryPlot().getDomainAxis().setTickLabelPaint(Color.decode("#666666"));
		chart.setTextAntiAlias(true);
		chart.setAntiAlias(true);
		BarRenderer rend = (BarRenderer) chart.getCategoryPlot().getRenderer();
		rend.setShadowVisible(true);
		rend.setShadowXOffset(2);
		rend.setShadowYOffset(0);
		rend.setShadowPaint(Color.decode("#C0C0C0"));
		rend.setMaximumBarWidth(0.1);
	}
	
	private static String getOutputName(String benchmark) {
		return benchmark.replaceAll("[: /]", "_") + ".png";
	}

	private static String title(BenchmarkGroup group, int entityCount) {
		switch (group) {
			case ITERATION:
				return "iteration: " + entityCount + " entities";
			case INSERT_REMOVE:
				return "insert/remove: " + entityCount + " entities";
			case THRESHOLD:
				return "baseline: " + entityCount + " entities";
			case TRANSMUTE:
				return "add/remove components: " + entityCount + " entities";
			default:
				throw new RuntimeException(group.name());
		}
	}
	
	public static void main(String[] args) {
		File logs = new File("../results");
		BenchmarkCollection bc = new BenchmarkCollection(logs);
		
		for (BenchmarkGroup group : BenchmarkGroup.values()) {
			System.out.println("processing: " + group);
			
			Map<Integer, Set<Benchmark>> benchmarks = bc.getBenchmarks(group);
			Map<Integer, DefaultCategoryDataset> datasets = toDatasets(benchmarks);
			for (Entry<Integer, DefaultCategoryDataset> entry : datasets.entrySet()) {
				int benchmarkCount = benchmarks.get(entry.getKey()).size();
				System.out.printf("\t%d: %s benchmarks\n", entry.getKey(), benchmarkCount);
				generateChart(title(group, entry.getKey()), entry.getValue(), benchmarkCount);
			}
		}

		System.out.println();
		
		for (Entry<Integer, Set<Benchmark>> entry : bc.getBenchmarks().entrySet()) {
			int entityCount = entry.getKey();
			System.out.println("#### Benchmarks: " + entityCount + " entites");
			System.out.println();
			System.out.printf(" ![it%1$dk][it%1$dk] ![ir%1$dk][ir%1$dk] ![arc%1$dk][arc%1$dk]\n", (entityCount / 1024));
			System.out.println();
			ResultTable table = new ResultTable(entry.getValue());
			table.printTable(System.out);
			System.out.println();
			System.out.println();
		}
	}
}
