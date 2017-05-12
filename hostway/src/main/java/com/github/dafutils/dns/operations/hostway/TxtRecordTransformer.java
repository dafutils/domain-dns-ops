package com.github.dafutils.dns.operations.hostway;

import static java.util.stream.Collectors.joining;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.github.dafutils.dns.records.TxtRecord;

public class TxtRecordTransformer implements BiFunction<TxtRecord, String, HostwayTxtRecord> {

	@Override
	public HostwayTxtRecord apply(TxtRecord txtRecord, String domainName) {
		String data = txtRecord.text()
				.stream()
				.collect(joining(" "));

		return new HostwayTxtRecord(domainName, "TXT", data, txtRecord.ttl());
	}
}
