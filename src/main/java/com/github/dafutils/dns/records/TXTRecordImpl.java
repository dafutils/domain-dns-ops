package com.github.dafutils.dns.records;

import java.util.Collections;
import java.util.Map;

/**
 * As described here https://en.wikipedia.org/wiki/TXT_record
 * name  ttl  class   type     text
 * joe        IN      TXT    "Located in a black hole" "Likely to be eaten by a grue"
 */
public class TXTRecordImpl implements TXTRecord {

	private final String destinationDomain;
	private final long ttl;
	private final Map<String, String> text;

	TXTRecordImpl(String destinationDomain,
				  long ttl,
				  Map<String, String> text) {
		this.destinationDomain = destinationDomain;
		this.ttl = ttl;
		this.text = Collections.unmodifiableMap(text);
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
	public Map<String, String> text() {
		return text;
	}

	@Override
	public String rr() {
		return "TXT";
	}
}
