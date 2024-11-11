package com.prudential.phi.operation.models;

import java.io.IOException;
import java.util.Objects;

import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * QueryOrderBy
 */
public class QueryOrderBy {

  @SerializedName("prop")
  private String prop = null;
  
  /**
   * Gets or Sets order
   */
  @JsonAdapter(OrderEnum.Adapter.class)
  public enum OrderEnum {
    
    ASC("asc"),
    DESC("desc");

    private String value;

    OrderEnum(String value) {
      this.value = value;
    }
    
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }
    
    public static OrderEnum fromValue(String text) {
      for (OrderEnum b : OrderEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
    
    public static class Adapter extends TypeAdapter<OrderEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final OrderEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public OrderEnum read(final JsonReader jsonReader) throws IOException {
        String value = jsonReader.nextString();
        return OrderEnum.fromValue(String.valueOf(value));
      }
    }
  }
  
  @SerializedName("order")
  private OrderEnum order = null;
  
  public QueryOrderBy prop(String prop) {
    this.prop = prop;
    return this;
  }

  
  /**
  * Get prop
  * @return prop
  **/
  
  
  @Schema(description = "")
  public String getProp() {
    return prop;
  }
  public void setProp(String prop) {
    this.prop = prop;
  }
  
  public QueryOrderBy order(OrderEnum order) {
    this.order = order;
    return this;
  }

  
  /**
  * Get order
  * @return order
  **/
  
  
  @Schema(description = "")
  public OrderEnum getOrder() {
    return order;
  }
  public void setOrder(OrderEnum order) {
    this.order = order;
  }
  
  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    QueryOrderBy queryOrderBy = (QueryOrderBy) o;
    return Objects.equals(this.prop, queryOrderBy.prop) &&
        Objects.equals(this.order, queryOrderBy.order);
  }

  @Override
  public int hashCode() {
    return java.util.Objects.hash(prop, order);
  }
  
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class QueryOrderBy {\n");
    
    sb.append("    prop: ").append(toIndentedString(prop)).append("\n");
    sb.append("    order: ").append(toIndentedString(order)).append("\n");
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



