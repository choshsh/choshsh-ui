package com.choshsh.itsmv.test;

import static org.assertj.core.api.BDDAssertions.then;

import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.Configuration;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1Pod;
import io.kubernetes.client.openapi.models.V1PodList;
import io.kubernetes.client.util.ClientBuilder;
import io.kubernetes.client.util.KubeConfig;
import java.io.FileReader;
import java.io.IOException;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles({"dev"})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class KubeTest {

  @Test
  @Order(1)
  public void test() throws IOException, ApiException {
    for (V1Pod item : listPod("dev").getItems()) {
      System.out.println(item.getMetadata().getName());
      System.out.println(item.getMetadata().getClusterName());
    }

    then(1).isGreaterThan(0);
  }

  public V1PodList listPod(String namespace) throws IOException, ApiException {
    String kubeConfigPath = "D:/config";

    ApiClient client =
        ClientBuilder.kubeconfig(KubeConfig.loadKubeConfig(new FileReader(kubeConfigPath))).build();

    Configuration.setDefaultApiClient(client);

    // the CoreV1Api loads default api-client from global configuration.
    CoreV1Api api = new CoreV1Api();
    if (namespace != null) {
      return api.listNamespacedPod(namespace, null, null, null, null, null, null, null, null, null,
          null);
    } else {
      return api.listPodForAllNamespaces(null, null, null, null, null, null, null, null, null,
          null);
    }
  }
}
