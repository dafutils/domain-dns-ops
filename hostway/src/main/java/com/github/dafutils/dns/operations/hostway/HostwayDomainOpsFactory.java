package com.github.dafutils.dns.operations.hostway;

import static lombok.AccessLevel.NONE;

import lombok.NoArgsConstructor;

import com.github.dafutils.dns.operations.DomainDnsOperationsClient;
import com.google.gson.GsonBuilder;

@NoArgsConstructor(access = NONE)
public class HostwayDomainOpsFactory {
	public static DomainDnsOperationsClient hostwayClient(String hostwayBaseUrl, String username, String password) {
		return new HostwayDomainDnsClient(
				new HostwayCredentialsSupplier(username, password), 
				hostwayBaseUrl, 
				new TxtRecordTransformer(), 
				new MxRecordTransformer(), 
				new GsonBuilder().create()
		);
	}
}
