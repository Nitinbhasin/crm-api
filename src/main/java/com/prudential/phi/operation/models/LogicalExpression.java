package com.prudential.phi.operation.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.validation.Valid;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import io.swagger.v3.oas.annotations.media.Schema;

public class LogicalExpression {

	  @Valid
	  private List<@Valid FilterExpression> expressions;

	  /**
	   * Gets or Sets op
	   */
	  public enum OpEnum {
	    AND("and"),
	    
	    OR("or"),
	    
	    NOT("not");

	    private String value;

	    OpEnum(String value) {
	      this.value = value;
	    }

	    @JsonValue
	    public String getValue() {
	      return value;
	    }

	    @Override
	    public String toString() {
	      return String.valueOf(value);
	    }

	    @JsonCreator
	    public static OpEnum fromValue(String value) {
	      for (OpEnum b : OpEnum.values()) {
	        if (b.value.equalsIgnoreCase(value)) {
	          return b;
	        }
	      }
	      throw new IllegalArgumentException("Unexpected value '" + value + "'");
	    }
	  }

	  private OpEnum op;

	  public LogicalExpression expressions(List<@Valid FilterExpression> expressions) {
	    this.expressions = expressions;
	    return this;
	  }

	  public LogicalExpression addExpressionsItem(FilterExpression expressionsItem) {
	    if (this.expressions == null) {
	      this.expressions = new ArrayList<>();
	    }
	    this.expressions.add(expressionsItem);
	    return this;
	  }

	  /**
	   * Get expressions
	   * @return expressions
	  */
	  @Valid @Size(max = 20) 
	  @Schema(name = "expressions", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
	  @JsonProperty("expressions")
	  public List<@Valid FilterExpression> getExpressions() {
	    return expressions;
	  }

	  public void setExpressions(List<@Valid FilterExpression> expressions) {
	    this.expressions = expressions;
	  }

	  public LogicalExpression op(OpEnum op) {
	    this.op = op;
	    return this;
	  }

	  /**
	   * Get op
	   * @return op
	  */
	  
	  @Schema(name = "op", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
	  @JsonProperty("op")
	  public OpEnum getOp() {
	    return op;
	  }

	  public void setOp(OpEnum op) {
	    this.op = op;
	  }

	  @Override
	  public boolean equals(Object o) {
	    if (this == o) {
	      return true;
	    }
	    if (o == null || getClass() != o.getClass()) {
	      return false;
	    }
	    LogicalExpression logicalExpression = (LogicalExpression) o;
	    return Objects.equals(this.expressions, logicalExpression.expressions) &&
	        Objects.equals(this.op, logicalExpression.op);
	  }

	  @Override
	  public int hashCode() {
	    return Objects.hash(expressions, op);
	  }

	  @Override
	  public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class LogicalExpression {\n");
	    sb.append("    expressions: ").append(toIndentedString(expressions)).append("\n");
	    sb.append("    op: ").append(toIndentedString(op)).append("\n");
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