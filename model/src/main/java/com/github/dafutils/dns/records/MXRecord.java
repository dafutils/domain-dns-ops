package com.github.dafutils.dns.records;

/**
 * As described here:
 * http://domainmx.net/mxsetup.shtml
 */
public interface MXRecord {
	String destinationDomain();

	long ttl();

	String clazz();

	int preferenceNumber();

	String mailServerName();

	String type();

	static MXRecord of(String destinationDomain, long ttl, int preferenceNumber, String mailServerName) {
		return new MXRecordImpl(destinationDomain, ttl, preferenceNumber, mailServerName);
	}
}
