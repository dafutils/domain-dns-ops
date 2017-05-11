package com.github.dafutils.dns.operation.godaddy.model;

import lombok.Builder;
import lombok.Value;

/**
 * An object modeling a DNS record for the GoDaddy API
 */
@Value
@Builder
public class DNSRecord {
	String type;
	String name;
	String data;
	Integer priority;
	Integer ttl;
}
