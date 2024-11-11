package com.prudential.phi.operation.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.google.gson.annotations.SerializedName;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Query
 */
public class Query implements Serializable {
	

  private static final long serialVersionUID = 1L;
 
  @SerializedName("objectClassName")
  private String objectClassName;
  
  @SerializedName("projs")
  private List<String> projs = null;
  
  @SerializedName("filter")
  private FilterExpression filter = null;
  
  @SerializedName("limit")
  private Integer limit = null;
  
  @SerializedName("orderBy")
  private List<QueryOrderBy> orderBy = null;
  
  public Query projs(List<String> projs) {
    this.projs = projs;
    return this;
  }

  public Query addProjsItem(String projsItem) {
    
    if (this.projs == null) {
      this.projs = new ArrayList<String>();
    }
    
    this.projs.add(projsItem);
    return this;
  }
  
  /**
  * Get projs
  * @return projs
  **/
  
  
  @Schema(description = "")
  public List<String> getProjs() {
    return projs;
  }
  public void setProjs(List<String> projs) {
    this.projs = projs;
  }
  
  public Query filter(FilterExpression filter) {
    this.filter = filter;
    return this;
  }

  
  /**
  * Get filter
  * @return filter
  **/
  
  
  @Schema(description = "")
  public FilterExpression getFilter() {
    return filter;
  }
  public void setFilter(FilterExpression filter) {
    this.filter = filter;
  }
  
  public Query limit(Integer limit) {
    this.limit = limit;
    return this;
  }

  
  /**
  * Get limit
  * @return limit
  **/
  
  
  @Schema(description = "")
  public Integer getLimit() {
    return limit;
  }
  public void setLimit(Integer limit) {
    this.limit = limit;
  }
  
  public Query orderBy(List<QueryOrderBy> orderBy) {
    this.orderBy = orderBy;
    return this;
  }

  public Query addOrderByItem(QueryOrderBy orderByItem) {
    
    if (this.orderBy == null) {
      this.orderBy = new ArrayList<QueryOrderBy>();
    }
    
    this.orderBy.add(orderByItem);
    return this;
  }
  
  /**
  * Get orderBy
  * @return orderBy
  **/
  
  public String getObjectClassName() {
		return objectClassName;
	}

	public void setObjectClassName(String objectClassName) {
		this.objectClassName = objectClassName;
	}

  
  @Schema(description = "")
  public List<QueryOrderBy> getOrderBy() {
    return orderBy;
  }
  public void setOrderBy(List<QueryOrderBy> orderBy) {
    this.orderBy = orderBy;
  }
  
  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Query query = (Query) o;
    return Objects.equals(this.projs, query.projs) &&
        Objects.equals(this.filter, query.filter) &&
        Objects.equals(this.limit, query.limit) &&
        Objects.equals(this.orderBy, query.orderBy);
  }

  @Override
  public int hashCode() {
    return java.util.Objects.hash(projs, filter, limit, orderBy);
  }
  
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Query {\n");
    
    sb.append("    projs: ").append(toIndentedString(projs)).append("\n");
    sb.append("    filter: ").append(toIndentedString(filter)).append("\n");
    sb.append("    limit: ").append(toIndentedString(limit)).append("\n");
    sb.append("    orderBy: ").append(toIndentedString(orderBy)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
  
  
}



