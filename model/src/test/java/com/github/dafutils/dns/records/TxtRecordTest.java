package com.github.dafutils.dns.records;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class TxtRecordTest {

	@Test
	public void testOf() throws Exception {
		//Given
		String expectedDestinationDomain = "example.com";
		int expectedTtl = 3600;

		Set<TxtRecordItem> expectedTextItems = new HashSet<>();
		TxtRecordItemImpl item1 = new TxtRecordItemImpl("key1", "value1");
		TxtRecordItemImpl item2 = new TxtRecordItemImpl("key2", "value2");
		TxtRecordItemImpl item3 = new TxtRecordItemImpl("key3", "value3");
		expectedTextItems.add(item1);
		expectedTextItems.add(item2);
		expectedTextItems.add(item3);

		//When
		TxtRecord generatedRecord = TxtRecord.of(expectedDestinationDomain, expectedTtl, expectedTextItems);

		//Then
		assertThat(generatedRecord.name()).isEqualTo(expectedDestinationDomain);
		assertThat(generatedRecord.ttl()).isEqualTo(expectedTtl);
		assertThat(generatedRecord.text()).containsOnly(item1, item2, item3);
	}
}
