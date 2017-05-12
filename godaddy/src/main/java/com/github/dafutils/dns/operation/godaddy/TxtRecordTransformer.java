package com.github.dafutils.dns.operation.godaddy;

import static java.util.stream.Collectors.joining;

import java.util.function.Function;

import com.github.dafutils.dns.records.TxtRecord;

class TxtRecordTransformer implements Function<TxtRecord, DNSRecord> {
	@Override
	public DNSRecord apply(TxtRecord record) {
		String name = extractRecordName(record);
		String text = extractText(record);

		return DNSRecord.builder()
				.type(record.type())
				.name(name)
				.data(text)
				.ttl(record.ttl())
				.build();
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
