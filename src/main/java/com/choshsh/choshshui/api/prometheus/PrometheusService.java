package com.choshsh.choshshui.api.prometheus;

import com.choshsh.choshshui.api.admin.EnvEntity;
import com.choshsh.choshshui.api.admin.EnvRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class PrometheusService {

  private final EnvRepository envRepository;

  public PrometheusService(EnvRepository envRepository) {
    this.envRepository = envRepository;
  }

  public String getPrometheusUrl() {
    Optional<EnvEntity> envEntity = envRepository.findByKey("PROMETHEUS_URL");
    return envEntity.map(EnvEntity::getValue).orElse(null);
  }
}
