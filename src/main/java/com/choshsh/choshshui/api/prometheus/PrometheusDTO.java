package com.choshsh.choshshui.api.prometheus;

import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PrometheusDTO {

  private String status;
  private Map<String, ?> data;

}