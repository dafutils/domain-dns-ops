package com.github.dafutils.dns.operation.godaddy;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalToJson;
import static com.github.tomakehurst.wiremock.client.WireMock.givenThat;
import static com.github.tomakehurst.wiremock.client.WireMock.patch;
import static com.github.tomakehurst.wiremock.client.WireMock.patchRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.put;
import static com.github.tomakehurst.wiremock.client.WireMock.putRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static com.google.common.io.Resources.getResource;
import static java.lang.String.format;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Collections.singletonList;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Set;
import java.util.function.Supplier;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.dafutils.dns.operations.DomainDnsOperationsClient;
import com.github.dafutils.dns.records.MxRecordImpl;
import com.github.dafutils.dns.records.TxtRecord;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.google.common.collect.Sets;
import com.google.common.io.Resources;

@RunWith(MockitoJUnitRunner.class)
public class GoDaddyDomainDnsClientTest {

	private DomainDnsOperationsClient testedService;

	@Mock
	private Supplier<String> shopperIdSupplier;

	@Rule
	public WireMockRule wireMock = new WireMockRule(
			options().dynamicPort()
	);

	@Before
	public void setUp() throws Exception {
		String goDaddyBaseUrl = format("http://localhost:%s", wireMock.port());
		testedService = GoDaddyDomainOpsFactory.createClientFor(goDaddyBaseUrl, shopperIdSupplier);
	}

	@Test
	public void testAddTextRecord_whenRecordPassed_itMatchesExpectedFormat() throws Exception {
		//Given
		String testDomainName = "example.com";
		TxtRecord testTxtRecord = TxtRecord.of("joe", 3600, singletonList("key=value"));
		String addRecordUrl = format("/v1/domains/%s/records", testDomainName);
		String expectedShopperId = "someString";
		when(shopperIdSupplier.get()).thenReturn(expectedShopperId);

		String expectedRequestPayload = givenRequestPayload("godaddyApiPayloads/addTxtRecordRequestBody.json");

		givenThat(
				patch(urlEqualTo(addRecordUrl))
						.willReturn(
								aResponse().withStatus(200)
						));

		//When
		testedService.addTextRecord(testDomainName, testTxtRecord);

		//Then
		verify(
				patchRequestedFor(urlEqualTo(addRecordUrl))
						.withRequestBody(equalToJson(expectedRequestPayload)));
	}

	@Test
	public void testAddTextRecord_whenRecordWithNoDestinationDomainIsPassed_AtSignIsReplacedInstead() throws Exception {
		//Given
		String testDomainName = "example.com";
		TxtRecord testTxtRecord = TxtRecord.of("", 3600, singletonList("key=value"));//givenATxtRecordWithOneItem("", "key", "value");
		String addRecordUrl = format("/v1/domains/%s/records", testDomainName);
		String expectedShopperId = "someString";
		when(shopperIdSupplier.get()).thenReturn(expectedShopperId);

		String expectedRequestPayload = givenRequestPayload("godaddyApiPayloads/addTxtRecordWithNoNameRequestBody.json");

		givenThat(
				patch(urlEqualTo(addRecordUrl))
						.willReturn(
								aResponse().withStatus(200)
						));

		//When
		testedService.addTextRecord(testDomainName, testTxtRecord);

		//Then
		verify(
				patchRequestedFor(urlEqualTo(addRecordUrl))
						.withRequestBody(equalToJson(expectedRequestPayload)));
	}

	@Test
	public void testConfigureDomainEmailRouting_whenASetOfMxRecordsIsToBeSetTo_theCorrespondingCallIsMAdeToGoDaddy() throws Exception {
		//Given
		String testDomainName = "example.com";
		MxRecordImpl record1 = new MxRecordImpl("something", 600, 1, "aspmx.l.google.com");
		MxRecordImpl record2 = new MxRecordImpl("something", 1200, 5, "alt1.aspmx.l.google.com");
		MxRecordImpl record3 = new MxRecordImpl("something", 3600, 10, "alt2.aspmx.l.google.com");
		Set<MxRecordImpl> testMxRecordsToSet = Sets.newHashSet(record1, record2, record3);
		String replaceMxRecordsUrl = format("/v1/domains/%s/records/MX", testDomainName);

		givenThat(
			put(urlEqualTo(replaceMxRecordsUrl))
				.willReturn(aResponse().withStatus(200))
		);

		String expectedRequestPayload = givenRequestPayload("godaddyApiPayloads/replaceMxRecordsRequestBody.json");

		//When
		testedService.configureDomainEmailRouting(testDomainName, testMxRecordsToSet);

		//Then
		verify(
				putRequestedFor(urlEqualTo(replaceMxRecordsUrl))
						.withRequestBody(equalToJson(expectedRequestPayload))
		);
	}

	private String givenRequestPayload(String resourcePath) throws IOException {
		return Resources.toString(getResource(resourcePath), UTF_8);
	}
}
