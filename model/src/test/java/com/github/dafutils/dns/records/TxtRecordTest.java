package com.github.dafutils.dns.records;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TxtRecordTest {

	@Test
	public void testOf() throws Exception {
		//Given
		String expectedDestinationDomain = "example.com";
		long expectedTtl = 3600;

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
		assertThat(generatedRecord.destinationDomain()).isEqualTo(expectedDestinationDomain);
		assertThat(generatedRecord.ttl()).isEqualTo(expectedTtl);
		assertThat(generatedRecord.text()).containsExactly(item1, item2, item3);
	}
}
