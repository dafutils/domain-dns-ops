package com.github.dafutils.dns.operations.hostway;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalToJson;
import static com.github.tomakehurst.wiremock.client.WireMock.givenThat;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static com.google.common.io.Resources.getResource;
import static java.lang.String.format;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Collections.singletonList;

import java.io.IOException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.dafutils.dns.operations.DomainDnsOperationsClient;
import com.github.dafutils.dns.records.TxtRecord;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.google.common.io.Resources;

@RunWith(MockitoJUnitRunner.class)
public class HostwayDomainDnsClientTest {

	private String hostwayBaseUrl;
	private String testUsername = "testUsername";
	private String testPassword = "testPassword";
	private DomainDnsOperationsClient testedClient;

	@Rule
	public WireMockRule wireMock = new WireMockRule(
			options().dynamicPort()
	);

	@Before
	public void setUp() throws Exception {
		hostwayBaseUrl = format("http://localhost:%s", wireMock.port());
		testedClient = HostwayDomainOpsFactory.hostwayClient(hostwayBaseUrl, testUsername, testPassword);
	}

	@Test
	public void testAddTextRecord() throws Exception {
		//Given
		String testDomainName = "example.com";
		TxtRecord testTxtRecord = TxtRecord.of("someStrangeName", 3600, singletonList("key=value"));
		String addRecordUrl = format("/dns/%s/records", testDomainName);

		String expectedRequestPayload = givenRequestPayload("hostwayApiRequests/hostwayTxtRecordInsertRequest.json");

		givenThat(
				post(
						urlEqualTo(addRecordUrl)
				).willReturn(
						aResponse().withStatus(200)
				)
		);
		//When
		testedClient.addTextRecord(testDomainName, testTxtRecord);

		//Then
		verify(
				postRequestedFor(
						urlEqualTo(addRecordUrl)
				).withRequestBody(
						equalToJson(expectedRequestPayload)
				)
		);
	}

	private String givenRequestPayload(String resourcePath) throws IOException {
		return Resources.toString(getResource(resourcePath), UTF_8);
	}
}
