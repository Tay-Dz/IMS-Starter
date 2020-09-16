package com.qa.ims.persistence.domain;

import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

public class AccountTest {

	@Test
	public void testEquals() {
		EqualsVerifier.simple().forClass(Account.class).verify();
	}

}
