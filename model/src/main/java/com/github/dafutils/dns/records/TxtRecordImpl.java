package com.github.dafutils.dns.records;

import static java.util.Collections.unmodifiableList;

import java.util.List;

/**
 * As described here https://en.wikipedia.org/wiki/TXT_record
 * name  ttl  class   type     text
 * joe        IN      TXT    "Located in a black hole" "Likely to be eaten by a grue"
 */
public final class TxtRecordImpl implements TxtRecord {

	private final String name;
	private final int ttl;
	private final List<String> text;

	TxtRecordImpl(String name,
				  int ttl,
				  List<String> text) {
		this.name = name;
		this.ttl = ttl;
		this.text = unmodifiableList(text);
	}

	@Override
	public String name() {
		return name;
	}

	@Override
	public int ttl() {
		return ttl;
	}

	@Override
	public String clazz() {
		return "IN";
	}

	@Override
	public List<String> text() {
		return text;
	}

	@Override
	public String type() {
		return "TXT";
	}
}
