package com.github.dafutils.dns.records;

import java.util.Set;

public interface TxtRecord {

	String name();

	int ttl();

	String clazz();

	Set<TxtRecordItem> text();

	String type();

	static TxtRecord of(String destinationDomain,
						int ttl,
						Set<TxtRecordItem> text) {

		return new TxtRecordImpl(destinationDomain, ttl, text);
	}
}
