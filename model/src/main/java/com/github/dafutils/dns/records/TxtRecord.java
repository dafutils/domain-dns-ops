package com.github.dafutils.dns.records;

import java.util.Set;

public interface TxtRecord {

	String destinationDomain();

	long ttl();

	String clazz();

	Set<TxtRecordItem> text();

	String type();

	static TxtRecord of(String destinationDomain,
						long ttl,
						Set<TxtRecordItem> text) {

		return new TxtRecordImpl(destinationDomain, ttl, text);
	}
}
