package edu.sru.cpsc.webshopping.util.enums;

public enum AttributeDataType {
  STRING,
  TEXTAREA,
  INTEGER,
  NUMBER,
  DECIMAL,
  BOOLEAN,
  DATE,
  YEAR,
  CONDITION,
  KEYWORDS,
  WEIGHT;

  public static boolean isValid(String dataType) {
      for (AttributeDataType dt : AttributeDataType.values()) {
          if (dt.name().equals(dataType)) {
              return true;
          }
      }
      return false;
  }
}
