package com.github.esfbench.chartgen;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Arrays;

import com.esotericsoftware.jsonbeans.Json;
import com.esotericsoftware.jsonbeans.OutputType;

public class ResultUtil {
	private ResultUtil() {}
	
	public static JmhResult[] fromJson(File file) throws IOException {
		try (FileInputStream fis = new FileInputStream(file)) {
			Json json = new Json(OutputType.json);
			json.setIgnoreUnknownFields(true);
			
			JmhResult[] results = json.fromJson(JmhResult[].class, fis);
			for (int i = 0; results.length > i; i++) {
				if (results[i] == null) {
					results = Arrays.copyOf(results, i); 
					break;
				}
			}
			return results;
		}
	}
	
}
