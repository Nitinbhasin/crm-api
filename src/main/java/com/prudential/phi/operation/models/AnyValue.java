package com.prudential.phi.operation.models;

/**
 * AnyValue
 */
public class AnyValue {

	private Object value;

	public AnyValue() {
		super();

	}

	public AnyValue(Object value) {
		super();
		this.value = value;

	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	@Override
	public boolean equals(java.lang.Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		return java.util.Objects.hash();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class AnyValue {\n");

		sb.append("}");
		return sb.toString();
	}

}
