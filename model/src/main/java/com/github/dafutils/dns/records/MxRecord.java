package com.github.dafutils.dns.records;

/**
 * As described here:
 * http://domainmx.net/mxsetup.shtml
 */
public interface MxRecord {
	String destinationDomain();

	int ttlInSeconds();

	String clazz();

	int preferenceNumber();

	String mailServerName();

	String type();

	static MxRecord of(String destinationDomain, int ttl, int preferenceNumber, String mailServerName) {
		return new MxRecordImpl(destinationDomain, ttl, preferenceNumber, mailServerName);
	}
}
