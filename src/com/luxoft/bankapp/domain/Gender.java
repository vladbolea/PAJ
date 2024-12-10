package com.luxoft.bankapp.domain;

public enum Gender {
	MALE("Mr."), FEMALE("Ms.");

	private final String greeting;

	private Gender(String greeting) {
		this.greeting = greeting;
	}

	public String getGreeting() {
		return greeting;
	}
}
