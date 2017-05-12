package com.github.dafutils.dns.records;

import java.util.List;

public interface TxtRecord {

	String name();

	int ttl();

	String clazz();

	List<String> text();

	String type();

	static TxtRecord of(String name,
						int ttl,
						List<String> text) {

		return new TxtRecordImpl(name, ttl, text);
	}
}
