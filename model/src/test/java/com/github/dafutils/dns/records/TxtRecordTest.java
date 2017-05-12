package com.github.dafutils.dns.records;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

public class TxtRecordTest {

	@Test
	public void testOf() throws Exception {
		//Given
		String expectedDestinationDomain = "example.com";
		int expectedTtl = 3600;

		List<String> expectedText = Arrays.asList("key1", "value1");

		//When
		TxtRecord generatedRecord = TxtRecord.of(expectedDestinationDomain, expectedTtl, expectedText);

		//Then
		assertThat(generatedRecord.name()).isEqualTo(expectedDestinationDomain);
		assertThat(generatedRecord.ttl()).isEqualTo(expectedTtl);
		assertThat(generatedRecord.text()).containsExactlyElementsOf(expectedText);
	}
}
