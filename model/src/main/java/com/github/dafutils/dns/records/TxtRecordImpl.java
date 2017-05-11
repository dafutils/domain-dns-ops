package com.github.dafutils.dns.records;

import static java.util.Collections.unmodifiableSet;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * As described here https://en.wikipedia.org/wiki/TXT_record
 * name  ttl  class   type     text
 * joe        IN      TXT    "Located in a black hole" "Likely to be eaten by a grue"
 */
public class TxtRecordImpl implements TxtRecord {

	private final String destinationDomain;
	private final long ttl;
	private final Set<TxtRecordItem> text;

	TxtRecordImpl(String destinationDomain,
				  long ttl,
				  Set<TxtRecordItem> text) {
		this.destinationDomain = destinationDomain;
		this.ttl = ttl;
		this.text = unmodifiableSet(text);
	}

	@Override
	public String destinationDomain() {
		return destinationDomain;
	}

	@Override
	public long ttl() {
		return ttl;
	}

	@Override
	public String clazz() {
		return "IN";
	}

	@Override
	public Set<TxtRecordItem> text() {
		return text;
	}

	@Override
	public String type() {
		return "TXT";
	}
}
