package com.github.dafutils.dns.operations;

import java.util.Set;

import com.github.dafutils.dns.records.MxRecord;
import com.github.dafutils.dns.records.TxtRecord;

/**
 * Implementations of this service need to be able to perform the declared operations on a domain using 
 * a for a concrete domain hosting service.
 */
public interface DomainDnsOperationsClient {

	void configureDomainEmailRouting(String domainName, Set<? extends MxRecord> mxRecords);

	void addTextRecord(String domainName, TxtRecord record);
}
