package dns.records;

import java.util.Map;

public interface TXTRecord {

	String destinationDomain();

	long ttl();

	String clazz();

	Map<String, String> text();

	String rr();

	static TXTRecord of(String destinationDomain,
						long ttl,
						Map<String, String> text) {

		return new TXTRecordImpl(destinationDomain, ttl, text);
	}
}
