package com.github.esfbench.chartgen;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import org.jfree.data.category.DefaultCategoryDataset;

import com.esotericsoftware.jsonbeans.Json;
import com.esotericsoftware.jsonbeans.OutputType;
import com.github.esfbench.chartgen.json.JmhResult;

public class BenchmarkUtil {
	private BenchmarkUtil() {}
	
	public static JmhResult[] fromJson(File file) throws IOException {
		try (FileInputStream fis = new FileInputStream(file)) {
			return fromJson(file.getName(), fis);
		}
	}
	
	public static JmhResult[] fromJson(String framework, InputStream is) throws IOException {
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
	
	public static DefaultCategoryDataset toDataset(JmhResult... results) {
		DefaultCategoryDataset data= new DefaultCategoryDataset();
		for (JmhResult result : results) {
			System.out.println(result);
			data.addValue(
					result.primaryMetric.score,
					"throughput",
					result.benchmark);
		}
		
		return data;
	}
}
