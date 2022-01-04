package com.choshsh.choshshui.test.prometheus;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@Disabled
@SpringBootTest
@ActiveProfiles({"dev"})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
public class PrometheusConrollerTest {

  @Autowired
  protected MockMvc mockMvc;

  @Test
  public void GET_PROMETHEUS_RULES() throws Exception {
    mockMvc.perform(get("/api/prometheus/rules"))
        .andExpect(status().isOk())
        .andDo(print());
  }

  @Test
  public void GET_PROMETHEUS_ALERTS() throws Exception {
    mockMvc.perform(get("/api/prometheus/alerts"))
        .andExpect(status().isOk())
        .andDo(print());
  }

}
