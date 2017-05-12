package com.github.dafutils.dns.operation.godaddy;

import static java.lang.String.format;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;

import lombok.SneakyThrows;

import com.github.dafutils.dns.operations.DomainDnsOperationsClient;
import com.github.dafutils.dns.records.MxRecord;
import com.github.dafutils.dns.records.TxtRecord;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mashape.unirest.http.Unirest;

class GoDaddyDomainDnsClient implements DomainDnsOperationsClient {

	private final String goDaddyBaseUrl;
	private final Supplier<String> shopperIdSupplier;
	private final Gson gson;
	private final Function<MxRecord, DNSRecord> mxRecordToGoDaddyFormat;
	private final Function<TxtRecord, DNSRecord> txtRecordToGoDaddyFormat;

	GoDaddyDomainDnsClient(String goDaddyBaseUrl, 
						   Supplier<String> shopperIdSupplier, 
						   Gson gson, 
						   Function<MxRecord, DNSRecord> mxRecordToGoDaddyFormat, 
						   Function<TxtRecord, DNSRecord> txtRecordToGoDaddyFormat) {

		this.goDaddyBaseUrl = goDaddyBaseUrl;
		this.shopperIdSupplier = shopperIdSupplier;
		this.gson = gson;
		this.mxRecordToGoDaddyFormat = mxRecordToGoDaddyFormat;
		this.txtRecordToGoDaddyFormat = txtRecordToGoDaddyFormat;
	}

	@SneakyThrows
	@Override
	public void configureDomainEmailRouting(String domainName, Set<? extends MxRecord> mxRecords) {
		List<DNSRecord> goDaddyRecords = mxRecords.stream()
				.map(mxRecordToGoDaddyFormat)
				.collect(toList());

		String serializedRecords = gson.toJson(
				goDaddyRecords,
				new TypeToken<List<DNSRecord>>() {
				}.getType()
		);

		Unirest
				.put(format("%s/v1/domains/%s/records/MX", goDaddyBaseUrl, domainName))
				.header("X-Shopper-Id", shopperIdSupplier.get())
				.body(serializedRecords)
				.asJson();
	}

	@SneakyThrows
	@Override
	public void addTextRecord(String domainName, TxtRecord record) {
		DNSRecord recordForGoDaddy = txtRecordToGoDaddyFormat.apply(record);

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
}
