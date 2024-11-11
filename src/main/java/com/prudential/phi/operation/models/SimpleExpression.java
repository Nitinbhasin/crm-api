 package com.prudential.phi.operation.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * SimpleExpression
 */
public class SimpleExpression {

	  @Valid
	  private List<String> lhs;

	  private SimpleOpEnum op;

	  private AnyValue value;

	  public SimpleExpression lhs(List<String> lhs) {
	    this.lhs = lhs;
	    return this;
	  }

	  public SimpleExpression addLhsItem(String lhsItem) {
	    if (this.lhs == null) {
	      this.lhs = new ArrayList<>();
	    }
	    this.lhs.add(lhsItem);
	    return this;
	  }

	  /**
	   * Get lhs
	   * @return lhs
	  */
	  @Size(max = 20) 
	  @Schema(name = "lhs", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
	  @JsonProperty("lhs")
	  public List<String> getLhs() {
	    return lhs;
	  }

	  public void setLhs(List<String> lhs) {
	    this.lhs = lhs;
	  }

	  public SimpleExpression op(SimpleOpEnum op) {
	    this.op = op;
	    return this;
	  }

	  /**
	   * Get op
	   * @return op
	  */
	  @Pattern(regexp = "^[~\\p{L}\\p{M}\\p{N}\\p{P}\\p{Zs}\\p{Zl}\\p{Zp}]+$|^$") @Size(max = 300) 
	  @Schema(name = "op", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
	  @JsonProperty("op")
	  public String getOp() {
	    return this.op.getValue();
	  }

	  public void setOp(SimpleOpEnum op) {
	    this.op = op;
	  }

	  public SimpleExpression value(AnyValue value) {
	    this.value = value;
	    return this;
	  }

	  /**
	   * Get value
	   * @return value
	  */
	  @Valid 
	  @Schema(name = "value", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
	  @JsonProperty("value")
	  public AnyValue getValue() {
	    return value;
	  }

	  public void setValue(AnyValue value) {
	    this.value = value;
	  }

	  @Override
	  public boolean equals(Object o) {
	    if (this == o) {
	      return true;
	    }
	    if (o == null || getClass() != o.getClass()) {
	      return false;
	    }
	    SimpleExpression simpleExpression = (SimpleExpression) o;
	    return Objects.equals(this.lhs, simpleExpression.lhs) &&
	        Objects.equals(this.op, simpleExpression.op) &&
	        Objects.equals(this.value, simpleExpression.value);
	  }

	  @Override
	  public int hashCode() {
	    return Objects.hash(lhs, op, value);
	  }

	  @Override
	  public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class SimpleExpression {\n");
	    sb.append("    lhs: ").append(toIndentedString(lhs)).append("\n");
	    sb.append("    op: ").append(toIndentedString(op)).append("\n");
	    sb.append("    value: ").append(toIndentedString(value)).append("\n");
	    sb.append("}");
	    return sb.toString();
	  }

	  /**
	   * Convert the given object to string with each line indented by 4 spaces
	   * (except the first line).
	   */
	  private String toIndentedString(Object o) {
	    if (o == null) {
	      return "null";
	    }
	    return o.toString().replace("\n", "\n    ");
	  }
	}