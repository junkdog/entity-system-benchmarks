package com.github.esfbench.chartgen;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import org.junit.Test;

import com.esotericsoftware.jsonbeans.Json;
import com.esotericsoftware.jsonbeans.OutputType;
import com.github.esfbench.chartgen.json.JmhResult;

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
	public void read_json_list_result() throws IOException {
		InputStream stream = getClass().getResourceAsStream("/test.json");
		JmhResult[] results = BenchmarkUtil.fromJson("test.json", stream);
		
		assertEquals(10, results.length);
	}
	
	@Test
	public void temp() throws IOException {
		ChartWriter.writeChart("test.json", getClass().getResourceAsStream("/test.json"));
	}
}
