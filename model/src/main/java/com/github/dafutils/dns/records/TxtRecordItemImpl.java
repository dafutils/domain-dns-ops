package com.github.dafutils.dns.records;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public final class TxtRecordItemImpl implements TxtRecordItem {
	private final String key;
	private final String value;

	public TxtRecordItemImpl(String key, String value) {
		this.key = key;
		this.value = value;
	}

	@Override
	public String key() {
		return key;
	}

	@Override
	public String value() {
		return value;
	}
}
