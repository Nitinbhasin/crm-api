package com.prudential.phi.operation.models;

import java.io.IOException;

import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

/**
 * Gets or Sets op
 */
@JsonAdapter(SimpleOpEnum.Adapter.class)
public enum SimpleOpEnum {
  
  EQ("="),
  NEQ("neq"),
  IN("in"),
  GT("gt"),
  GTE("gte"),
  LT("lt"),
  LTE("lte"),
  LIKE("like"),
  NOTLIKE("notlike"),
  BETWEEN("between"),
  CONTAINS("contains");

	


  private String value;

  SimpleOpEnum(String value) {
    this.value = value;
  }
  
  public String getValue() {
    return value;
  }

  @Override
  public String toString() {
    return String.valueOf(value);
  }
  
  public static SimpleOpEnum fromValue(String text) {
    for (SimpleOpEnum b : SimpleOpEnum.values()) {
      if (String.valueOf(b.value).equals(text)) {
        return b;
      }
    }
    return null;
  }
  
  public static class Adapter extends TypeAdapter<SimpleOpEnum> {
    @Override
    public void write(final JsonWriter jsonWriter, final SimpleOpEnum enumeration) throws IOException {
      jsonWriter.value(enumeration.getValue());
    }

    @Override
    public SimpleOpEnum read(final JsonReader jsonReader) throws IOException {
      String value = jsonReader.nextString();
      return SimpleOpEnum.fromValue(String.valueOf(value));
    }
  }
}
