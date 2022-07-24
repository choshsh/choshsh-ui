package com.choshsh.choshshui.api.prometheus;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
public class PrometheusController {

  private static final String PREFIX_URL = "/api/prometheus";
  private final WebClient webClient;
  private final PrometheusService prometheusService;

  public PrometheusController(WebClient webClient, PrometheusService prometheusService) {
    this.webClient = webClient;
    this.prometheusService = prometheusService;
  }

  @GetMapping(value = PREFIX_URL + "/rules")
  @Operation(description = "List prometheus alert rules")
  public Mono<PrometheusDTO> listRules() {
    return webClient.get()
        .uri(prometheusService.getPrometheusUrl() + "/api/v1/rules")
        .retrieve()
        .bodyToMono(PrometheusDTO.class);
  }

  @GetMapping(value = PREFIX_URL + "/alerts")
  @Operation(description = "List prometheus alert firing")
  public Mono<PrometheusDTO> listAlerts() {
    return webClient.get()
        .uri(prometheusService.getPrometheusUrl() + "/api/v1/alerts")
        .retrieve()
        .bodyToMono(PrometheusDTO.class);
  }

}
