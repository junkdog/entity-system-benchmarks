package com.github.esfbench.chartgen;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.util.Arrays;

import org.junit.Test;

import com.esotericsoftware.jsonbeans.Json;
import com.esotericsoftware.jsonbeans.OutputType;

public class ResultToJsonTest {
	
	@Test
	public void read_json_result() {
		Json json = new Json(OutputType.json);
		InputStream stream = getClass().getResourceAsStream("/test-single.json");
		assertNotNull(stream);
		
		json.setIgnoreUnknownFields(true);
		JmhResult result = json.fromJson(JmhResult.class, stream);
		
		assertEquals("com.artemis.BaselineBenchmark.baseline", result.benchmark);
		assertEquals("16384", result.params.entityCount);
		assertEquals(4206.2258333006175, result.primaryMetric.score, 0.1f);
		assertEquals(126.78278579635376, result.primaryMetric.scoreError, 0.1f);
	}
	
	@Test
	public void read_json_list_result() {
		Json json = new Json(OutputType.json);
		InputStream stream = getClass().getResourceAsStream("/test.json");
		assertNotNull(stream);
		
		json.setIgnoreUnknownFields(true);
		JmhResult[] results = json.fromJson(JmhResult[].class, stream);
		for (int i = 0; results.length > i; i++) {
			if (results[i] == null) {
				results = Arrays.copyOf(results, i); 
				break;
			}
		}
		
		assertEquals(10, results.length);
	}
}
