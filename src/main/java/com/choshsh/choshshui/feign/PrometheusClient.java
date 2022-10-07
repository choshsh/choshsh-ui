package com.choshsh.choshshui.feign;

import com.choshsh.choshshui.api.prometheus.PrometheusDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "prometheus", url = "${custom.prometheus.url}")
public interface PrometheusClient {

  @GetMapping("/api/v1/rules")
  PrometheusDTO listRules();

  @GetMapping("/api/v1/alerts")
  PrometheusDTO listAlerts();

}
