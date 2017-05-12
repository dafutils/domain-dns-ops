package com.github.dafutils.dns.records;

/**
 * As described here:
 * http://domainmx.net/mxsetup.shtml
 */
public interface MxRecord {
	String destinationDomain();

	long ttl();

	String clazz();

	int preferenceNumber();

	String mailServerName();

	String type();

	static MxRecord of(String destinationDomain, long ttl, int preferenceNumber, String mailServerName) {
		return new MxRecordImpl(destinationDomain, ttl, preferenceNumber, mailServerName);
	}
}
