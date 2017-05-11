package dns.records;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class MXRecordTest {

	@Test
	public void testOf() throws Exception {
		//Given
		String expectedDestinationDomain = "example.com";
		long expectedTtl = 3600;
		int expectedPreferenceNumber = 1;
		String expectedMailServerName = "test.example.com";

		//When
		MXRecord generatedRecord = MXRecord.of(expectedDestinationDomain, expectedTtl, expectedPreferenceNumber, expectedMailServerName);

		//Then
		assertThat(generatedRecord.destinationDomain()).isEqualTo(expectedDestinationDomain);
		assertThat(generatedRecord.clazz()).isEqualTo("IN");
		assertThat(generatedRecord.mailServerName()).isEqualTo(expectedMailServerName);
		assertThat(generatedRecord.preferenceNumber()).isEqualTo(expectedPreferenceNumber);
		assertThat(generatedRecord.ttl()).isEqualTo(expectedTtl);
	}
}
