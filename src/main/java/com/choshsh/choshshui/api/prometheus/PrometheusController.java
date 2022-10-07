package com.choshsh.choshshui.api.prometheus;

import com.choshsh.choshshui.feign.PrometheusClient;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PrometheusController {

  private static final String PREFIX_URL = "/api/prometheus";
  private final PrometheusClient prometheusClient;


  @Operation(description = "List prometheus alert rules")
  @GetMapping(value = PREFIX_URL + "/rules")
  public PrometheusDTO listRules() {
    return prometheusClient.listRules();
  }

  @Operation(description = "List prometheus alert firing")
  @GetMapping(value = PREFIX_URL + "/alerts")
  public PrometheusDTO listAlerts() {
    return prometheusClient.listAlerts();
  }

}
