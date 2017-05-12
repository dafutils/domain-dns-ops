package com.github.dafutils.dns.operation.godaddy;

import java.util.function.Function;

import com.github.dafutils.dns.records.MxRecord;

class MxRecordTransformer implements Function<MxRecord, DNSRecord> {
	@Override
	public DNSRecord apply(MxRecord mxRecord) {
		return DNSRecord.builder()
				.name("@")
				.priority(mxRecord.preferenceNumber())
				.ttl(mxRecord.ttlInSeconds())
				.data(mxRecord.mailServerName())
				.build();
	}
}
