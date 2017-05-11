package com.github.dafutils.dns.operation.godaddy;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalToJson;
import static com.github.tomakehurst.wiremock.client.WireMock.givenThat;
import static com.github.tomakehurst.wiremock.client.WireMock.patch;
import static com.github.tomakehurst.wiremock.client.WireMock.patchRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static com.google.common.io.Resources.getResource;
import static java.lang.String.format;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.dafutils.dns.operations.DomainOperationsService;
import com.github.dafutils.dns.records.TxtRecord;
import com.github.dafutils.dns.records.TxtRecordItem;
import com.github.dafutils.dns.records.TxtRecordItemImpl;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.google.common.io.Resources;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@RunWith(MockitoJUnitRunner.class)
public class GoDaddyDomainServiceTest {

	private DomainOperationsService testedService;

	@Mock
	private Supplier<String> shopperIdSupplier;

	private Gson gson = new GsonBuilder().create();

	@Rule
	public WireMockRule wireMock = new WireMockRule(
			options().dynamicPort()
	);

	@Before
	public void setUp() throws Exception {
		String goDaddyBaseUrl = format("http://localhost:%s", wireMock.port());
		testedService = new GoDaddyDomainService(goDaddyBaseUrl, shopperIdSupplier, gson);
	}

	@Test
	public void testAddTextRecord_whenRecordPassed_itMatchesExpectedFormat() throws Exception {
		//Given
		String testDomainName = "example.com";
		TxtRecord testTxtRecord = givenATxtRecordWithOneItem("joe","key", "value");
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
		TxtRecord testTxtRecord = givenATxtRecordWithOneItem("", "key", "value");
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
	
	private String givenRequestPayload(String resourcePath) throws IOException {
		return Resources.toString(getResource(resourcePath), UTF_8);
	}

	private TxtRecord givenATxtRecordWithOneItem(String destinationDomain, String itemKey, String itemValue) {
		TxtRecordItem txtRecordItem = new TxtRecordItemImpl(itemKey, itemValue);

		Set<TxtRecordItem> items = new HashSet<>();
		items.add(txtRecordItem);

		return TxtRecord.of(destinationDomain, 3600, Collections.unmodifiableSet(items));
	}
}
