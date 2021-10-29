package com.choshsh.choshshui.api.prometheus;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
public class PrometheusController {

  String url = "http://prometheus.choshsh.com";
  private static final String PREFIX_URL = "/api/prometheus";
  private WebClient webClient;

  public PrometheusController(WebClient webClient) {
    this.webClient = webClient;
  }

  @ApiOperation(value = "Prometheus 알림 규칙 조회")
  @GetMapping(PREFIX_URL + "/rules")
  public Mono<PrometheusDTO> getRules() {
    return webClient.get()
        .uri(url + "/api/v1/rules")
        .retrieve()
        .bodyToMono(PrometheusDTO.class);
  }

  @ApiOperation(value = "Prometheus 활성 알림 조회")
  @GetMapping(PREFIX_URL + "/alerts")
  public Mono<PrometheusDTO> getAlerts() {
    return webClient.get()
        .uri(url + "/api/v1/alerts")
        .retrieve()
        .bodyToMono(PrometheusDTO.class);
  }

}
