
 package com.prudential.phi.operation.models;

import java.util.Objects;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * FilterExpression
 */
public class FilterExpression {

	  private SimpleExpression simpleExpression;

	  private LogicalExpression logicalExpression;

	  public FilterExpression simpleExpression(SimpleExpression simpleExpression) {
	    this.simpleExpression = simpleExpression;
	    return this;
	  }

	  /**
	   * Get simpleExpression
	   * @return simpleExpression
	  */
	  @Valid 
	  @Schema(name = "simpleExpression", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
	  @JsonProperty("simpleExpression")
	  public SimpleExpression getSimpleExpression() {
	    return simpleExpression;
	  }

	  public void setSimpleExpression(SimpleExpression simpleExpression) {
	    this.simpleExpression = simpleExpression;
	  }

	  public FilterExpression logicalExpression(LogicalExpression logicalExpression) {
	    this.logicalExpression = logicalExpression;
	    return this;
	  }

	  /**
	   * Get logicalExpression
	   * @return logicalExpression
	  */
	  @Valid 
	  @Schema(name = "logicalExpression", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
	  @JsonProperty("logicalExpression")
	  public LogicalExpression getLogicalExpression() {
	    return logicalExpression;
	  }

	  public void setLogicalExpression(LogicalExpression logicalExpression) {
	    this.logicalExpression = logicalExpression;
	  }

	  @Override
	  public boolean equals(Object o) {
	    if (this == o) {
	      return true;
	    }
	    if (o == null || getClass() != o.getClass()) {
	      return false;
	    }
	    FilterExpression filterExpression = (FilterExpression) o;
	    return Objects.equals(this.simpleExpression, filterExpression.simpleExpression) &&
	        Objects.equals(this.logicalExpression, filterExpression.logicalExpression);
	  }

	  @Override
	  public int hashCode() {
	    return Objects.hash(simpleExpression, logicalExpression);
	  }

	  @Override
	  public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("class FilterExpression {\n");
	    sb.append("    simpleExpression: ").append(toIndentedString(simpleExpression)).append("\n");
	    sb.append("    logicalExpression: ").append(toIndentedString(logicalExpression)).append("\n");
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
