package com.github.esfbench.chartgen;

import java.io.File;
import java.util.Arrays;

import com.esotericsoftware.jsonbeans.Json;
import com.esotericsoftware.jsonbeans.OutputType;

public class Framework {
	private JmhResult[] results;

	public Framework(File file) {
		Json json = new Json(OutputType.json);
		json.setIgnoreUnknownFields(true);
		JmhResult[] results = json.fromJson(JmhResult[].class, file);
		for (int i = 0; results.length > i; i++) {
			if (results[i] == null) {
				results = Arrays.copyOf(results, i); 
				break;
			}
		}
		
		this.results = results;
	}
}
