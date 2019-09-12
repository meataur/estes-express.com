package com.estes.dto.ecm.common;

/**
 * Enumeration of operators.
 */
public enum Operator {
	GreaterThan(">"),
	LessThan("<"),
	GreaterThanEqualTo(">="),
	LessThanEqualTo("<="),
	EqualTo("="),
	NotEqualTo("<>"),
	Like("Like"),
	Between("Between");
	
	private final String stringValue;
	
	private Operator(String stringValue) {
		this.stringValue = stringValue;
	}
	
	@Override public String toString() {
		return stringValue;
	}
}
