package com.choshsh.choshshui.api.prometheus;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
public class PrometheusController {

  String url = "http://prometheus.choshsh.com";
  private static final String PREFIX_URL = "/api/prometheus";
  private final WebClient webClient;
  private final PrometheusService prometheusService;

  public PrometheusController(WebClient webClient, PrometheusService prometheusService) {
    this.webClient = webClient;
    this.prometheusService = prometheusService;
  }

  @ApiOperation(value = "Prometheus 알림 규칙 조회")
  @GetMapping(value = PREFIX_URL + "/rules", consumes = APPLICATION_JSON_VALUE)
  public Mono<PrometheusDTO> listRules() {
    return webClient.get()
        .uri(prometheusService.getPrometheusUrl() + "/api/v1/rules")
        .retrieve()
        .bodyToMono(PrometheusDTO.class);
  }

  @ApiOperation(value = "Prometheus 활성 알림 조회")
  @GetMapping(value = PREFIX_URL + "/alerts", consumes = APPLICATION_JSON_VALUE)
  public Mono<PrometheusDTO> listAlerts() {
    return webClient.get()
        .uri(prometheusService.getPrometheusUrl() + "/api/v1/alerts")
        .retrieve()
        .bodyToMono(PrometheusDTO.class);
  }

}
