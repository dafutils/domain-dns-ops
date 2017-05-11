package com.github.dafutils.dns.records;

class MXRecordImpl implements MXRecord {

	private final String destinationDomain;
	private final long ttl;
	private final int preferenceNumber;
	private final String mailServerName;

	MXRecordImpl(String destinationDomain,
				 long ttl,
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
	public long ttl() {
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
