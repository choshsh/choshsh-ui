package com.choshsh.choshshui.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@ActiveProfiles({"dev"})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
public class AdminControllerTest {

  @Autowired
  protected MockMvc mockMvc;

  @Autowired
  protected ObjectMapper objectMapper;

  @Order(1)
  @Test
  public void POST_HEADER() throws Exception {
    Map<String, String> param = new HashMap<>();
    param.put("name", "test");
    param.put("url", "https://choshsh.com");

    mockMvc.perform(
            post("/api/admin/header")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(List.of(param)))
        )
        .andExpect(status().isOk())
        .andDo(print());
  }

  @Order(2)
  @Test
  public void GET_HEADER_LIST() throws Exception {
    mockMvc.perform(get("/api/admin/header"))
        .andExpect(status().isOk())
        .andDo(print());
  }

  @Test
  public void IFRAME_LIST_GET() throws Exception {
    mockMvc.perform(get("/api/admin/iframe"))
        .andExpect(status().isOk())
        .andDo(print());
  }

}
