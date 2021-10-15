package com.choshsh.choshshui.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserRole implements EnumModel {

  ADMIN("ADMIN"), USER("USER"), GUEST("GUEST");

  private final String value;

  @Override
  public String getKey() {
    return name();
  }

  @Override
  public String getValue() {
    return value;
  }

}
