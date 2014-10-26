package com.github.esfbench.chartgen;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import org.jfree.data.category.DefaultCategoryDataset;

import com.esotericsoftware.jsonbeans.Json;
import com.esotericsoftware.jsonbeans.OutputType;
import com.github.esfbench.chartgen.json.JmhResult;
import com.github.esfbench.chartgen.model.Benchmark;

public class BenchmarkUtil {
	private BenchmarkUtil() {}
	
	public static JmhResult[] fromJson(File file) {
		try (FileInputStream fis = new FileInputStream(file)) {
			return fromJson(file.getAbsolutePath(), fis);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	static JmhResult[] fromJson(String framework, InputStream is) {
		Json json = new Json(OutputType.json);
		json.setIgnoreUnknownFields(true);
		
		JmhResult[] results = json.fromJson(JmhResult[].class, is);
		for (int i = 0; results.length > i; i++) {
			if (results[i] == null) {
				results = Arrays.copyOf(results, i); 
				break;
			}
		}
		return results;
	}
	
	public static Map<Integer, DefaultCategoryDataset> toDatasets(Map<Integer, Set<Benchmark>> benchmarks) {
		Map<Integer, DefaultCategoryDataset> data = new TreeMap<>();
		for (Entry<Integer, Set<Benchmark>> entry : benchmarks.entrySet()) {
			DefaultCategoryDataset dataset = new DefaultCategoryDataset();
			for (Benchmark benchmark : entry.getValue()) {
				dataset.addValue(benchmark.score, benchmark.type.name(), benchmark.framework);
			}
			data.put(entry.getKey(), dataset);
		}
		
		return data;
	}
}
