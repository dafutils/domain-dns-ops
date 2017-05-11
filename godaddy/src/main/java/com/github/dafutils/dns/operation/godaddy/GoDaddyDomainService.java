package com.github.dafutils.dns.operation.godaddy;

import java.util.Set;

import com.github.dafutils.dns.operations.DomainOperationsService;
import com.github.dafutils.dns.records.MXRecord;
import com.github.dafutils.dns.records.TXTRecord;

public class GoDaddyDomainService implements DomainOperationsService {
	@Override
	public void configureDomainEmailRouting(String domainName, Set<MXRecord> mxRecords) {
		
	}

	@Override
	public void addTextRecord(String domainName, TXTRecord record) {

	}
}
