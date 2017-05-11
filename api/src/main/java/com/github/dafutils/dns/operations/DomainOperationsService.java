package com.github.dafutils.dns.operations;

import java.util.Set;

import com.github.dafutils.dns.records.MXRecord;
import com.github.dafutils.dns.records.TXTRecord;

/**
 * Implementations of this service need to be able to perform the declared operations on a domain using 
 * a for a concrete domain hosting service.
 */
public interface DomainOperationsService {

	void configureDomainEmailRouting(String domainName, Set<MXRecord> mxRecords);

	void addTextRecord(String domainName, TXTRecord record);
}
