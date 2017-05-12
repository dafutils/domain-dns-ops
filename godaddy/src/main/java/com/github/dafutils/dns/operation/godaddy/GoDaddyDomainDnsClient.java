package com.github.dafutils.dns.operation.godaddy;

import static java.lang.String.format;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.joining;

import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

import lombok.SneakyThrows;

import com.github.dafutils.dns.operations.DomainDnsOperationsClient;
import com.github.dafutils.dns.records.MxRecord;
import com.github.dafutils.dns.records.TxtRecord;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mashape.unirest.http.Unirest;

public class GoDaddyDomainDnsClient implements DomainDnsOperationsClient {

	private final String goDaddyBaseUrl;
	private final Supplier<String> shopperIdSupplier;
	private final Gson gson;

	public GoDaddyDomainDnsClient(String goDaddyBaseUrl, Supplier<String> shopperIdSupplier, Gson gson) {
		this.goDaddyBaseUrl = goDaddyBaseUrl;
		this.shopperIdSupplier = shopperIdSupplier;
		this.gson = gson;
	}

	@Override
	public void configureDomainEmailRouting(String domainName, Set<MxRecord> mxRecords) {

	}

	@SneakyThrows
	@Override
	public void addTextRecord(String domainName, TxtRecord record) {
		String name = extractRecordName(record);
		String text = extractText(record);

		DNSRecord recordForGoDaddy = DNSRecord.builder()
				.type(record.type())
				.name(name)
				.data(text)
				.ttl(record.ttl())
				.build();

		String serializedRecords = gson.toJson(
				singletonList(recordForGoDaddy),
				new TypeToken<List<DNSRecord>>() {
				}.getType()
		);

		Unirest
			.patch(format("%s/v1/domains/%s/records", goDaddyBaseUrl, domainName))
			.header("X-Shopper-Id", shopperIdSupplier.get())
			.body(serializedRecords)
			.asString();
	}

	private String extractText(TxtRecord record) {
		return record.text()
				.stream()
				.collect(joining(" "));
	}

	private String extractRecordName(TxtRecord record) {
		return record.name().isEmpty()
				? "@"
				: record.name();
	}

}
