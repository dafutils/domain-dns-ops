package com.github.dafutils.dns.operations.hostway;

import lombok.Value;

@Value
class HostwayTxtRecord {
	String name;
	String type;
	String data;
	int ttl;
}
