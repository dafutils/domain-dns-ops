package com.github.dafutils.dns.records;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class TXTRecordTest {

	@Test
	public void testOf() throws Exception {
		//Given
		String expectedDestinationDomain = "example.com";
		long expectedTtl = 3600;
		Map<String, String> expectedText = new HashMap<>();
		expectedText.put("key1", "value1");
		expectedText.put("key2", "value2");
		expectedText.put("key3", "value3");

		//When
		TXTRecord generatedRecord = TXTRecord.of(expectedDestinationDomain, expectedTtl, expectedText);

		//Then
		assertThat(generatedRecord.destinationDomain()).isEqualTo(expectedDestinationDomain);
		assertThat(generatedRecord.ttl()).isEqualTo(expectedTtl);
		assertThat(generatedRecord.text()).isEqualTo(expectedText);
	}
}
