package com.github.dafutils.dns.records;

final class MxRecordImpl implements MxRecord {

	private final String destinationDomain;
	private final int ttl;
	private final int preferenceNumber;
	private final String mailServerName;

	public MxRecordImpl(String destinationDomain,
				 int ttl,
				 int preferenceNumber,
				 String mailServerName) {

		this.destinationDomain = destinationDomain;
		this.ttl = ttl;
		this.preferenceNumber = preferenceNumber;
		this.mailServerName = mailServerName;
	}

	@Override
	public String destinationDomain() {
		return destinationDomain;
	}

	@Override
	public int ttlInSeconds() {
		return ttl;
	}

	@Override
	public String clazz() {
		return "IN";
	}

	@Override
	public int preferenceNumber() {
		return preferenceNumber;
	}

	@Override
	public String mailServerName() {
		return mailServerName;
	}

	@Override
	public String type() {
		return "MX";
	}
}
