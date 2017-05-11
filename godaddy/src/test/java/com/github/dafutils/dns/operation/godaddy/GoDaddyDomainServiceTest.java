package com.github.dafutils.dns.operation.godaddy;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.dafutils.dns.operations.DomainOperationsService;
import com.github.dafutils.dns.records.TxtRecord;
import com.github.tomakehurst.wiremock.junit.WireMockRule;

@RunWith(MockitoJUnitRunner.class)
public class GoDaddyDomainServiceTest {

	DomainOperationsService testedService;

	@Rule
	public WireMockRule wireMock = new WireMockRule(
			options().dynamicPort()
	);

	@Before
	public void setUp() throws Exception {
		testedService = new GoDaddyDomainService();
	}

	@Test
	public void name() throws Exception {
		//Given
		String testDomainName = "example.com";
		TxtRecord testTxtRecord = TxtRecord.of("@", 3600, null);

		
		//When
		testedService.addTextRecord(testDomainName, testTxtRecord);
		//Then
	}
}
