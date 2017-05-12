package com.github.dafutils.dns.operations.hostway;

import static java.lang.String.format;

import java.util.Base64;
import java.util.function.Supplier;

public class HostwayCredentialsSupplier implements Supplier<String> {

	private final String hostwayToken;

	HostwayCredentialsSupplier(String username, String password) {
		this.hostwayToken = Base64.getEncoder()
				.encodeToString(format("%s:%s", username, password).getBytes());
	}

	@Override
	public String get() {
		return hostwayToken;
	}
}
