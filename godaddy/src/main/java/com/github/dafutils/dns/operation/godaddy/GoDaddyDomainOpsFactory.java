package com.github.dafutils.dns.operation.godaddy;

import static lombok.AccessLevel.NONE;

import java.util.function.Supplier;

import lombok.NoArgsConstructor;

import com.github.dafutils.dns.operations.DomainDnsOperationsClient;
import com.google.gson.GsonBuilder;

@NoArgsConstructor(access = NONE)
public class GoDaddyDomainOpsFactory {

	public static DomainDnsOperationsClient createClientFor(String goDaddyBaseUrl, Supplier<String> shopperIdSupplier) {
		return new GoDaddyDomainDnsClient(
				goDaddyBaseUrl, 
				shopperIdSupplier, 
				new GsonBuilder().create(), 
				new MxRecordTransformer(),
				new TxtRecordTransformer()
		);
	}
}
