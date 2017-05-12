package com.github.dafutils.dns.operations.hostway;

import java.util.function.Function;

import com.github.dafutils.dns.records.MxRecord;

public class MxRecordTransformer implements Function<MxRecord, HostwayMxRecord> {
	@Override
	public HostwayMxRecord apply(MxRecord mxRecord) {
		return null;
	}
}
