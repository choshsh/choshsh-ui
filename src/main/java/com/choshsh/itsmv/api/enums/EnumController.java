package com.choshsh.itsmv.api.enums;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class EnumController {

  @GetMapping(value = "/api/code/yn")
  public List<EnumDTO> yn() {
    return Arrays
        .stream(YN.class.getEnumConstants())
        .map(EnumDTO::new)
        .collect(Collectors.toList());
  }

}
