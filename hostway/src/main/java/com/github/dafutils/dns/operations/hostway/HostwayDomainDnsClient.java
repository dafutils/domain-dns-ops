package com.github.dafutils.dns.operations.hostway;

import static java.lang.String.format;

import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import lombok.SneakyThrows;

import com.github.dafutils.dns.operations.DomainDnsOperationsClient;
import com.github.dafutils.dns.records.MxRecord;
import com.github.dafutils.dns.records.TxtRecord;
import com.google.gson.Gson;
import com.mashape.unirest.http.Unirest;

public class HostwayDomainDnsClient implements DomainDnsOperationsClient {

	private final Supplier<String> credentialsSupplier;
	private final String hostwayBaseUrl;
	private final BiFunction<TxtRecord, String, HostwayTxtRecord> txtRecordToHostwayFormat;
	private final Function<MxRecord, HostwayMxRecord> mxRecordToHostwayFormat;
	private final Gson gson;

	HostwayDomainDnsClient(Supplier<String> credentialsSupplier,
						   String hostwayBaseUrl,
						   BiFunction<TxtRecord, String, HostwayTxtRecord> txtRecordToHostwayFormat,
						   Function<MxRecord, HostwayMxRecord> mxRecordToHostwayFormat, Gson gson) {

		this.credentialsSupplier = credentialsSupplier;
		this.hostwayBaseUrl = hostwayBaseUrl;
		this.txtRecordToHostwayFormat = txtRecordToHostwayFormat;
		this.mxRecordToHostwayFormat = mxRecordToHostwayFormat;
		this.gson = gson;
	}

	@Override
	public void configureDomainEmailRouting(String domainName, Set<? extends MxRecord> mxRecords) {

	}

	@SneakyThrows
	@Override
	public void addTextRecord(String domainName, TxtRecord record) {

		HostwayTxtRecord hostwayTxtRecord = txtRecordToHostwayFormat.apply(record, domainName);

		String serializedRecords = gson.toJson(hostwayTxtRecord);

		Unirest
				.post(format("%s/dns/%s/records", hostwayBaseUrl, domainName))
				.header("Authorization", credentialsSupplier.get())
				.body(serializedRecords)
				.asJson();
	}
}
