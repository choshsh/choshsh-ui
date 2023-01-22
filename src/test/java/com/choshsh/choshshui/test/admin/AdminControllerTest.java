package com.choshsh.choshshui.test.admin;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.choshsh.choshshui.api.admin.HeaderEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
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

  @Autowired
  CacheManager cacheManager;

  @Test
  public void ADD_HEADER() throws Exception {
    Map<String, String> param = ImmutableMap.of(
        "name", "test",
        "url", "https://choshsh.com");

    mockMvc.perform(
            post("/api/admin/header")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(param)))
        .andExpect(status().isCreated())
        .andDo(print());
  }

  @Test
  public void ADD_HEADER_INVALID_PARAMETER() throws Exception {
    Map<String, String> param = ImmutableMap.of(
        "name", "",
        "url", "");

    mockMvc.perform(
            post("/api/admin/header")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(param)))
        .andExpect(status().isBadRequest())
        .andDo(print());
  }

  @Test
  public void ADD_HEADERS() throws Exception {
    Map<String, String> param = ImmutableMap.of(
        "name", "test",
        "url", "https://choshsh.com");

    Map<String, String> param2 = ImmutableMap.of(
        "name", "test2",
        "url", "https://choshsh222.com");

    mockMvc.perform(
            post("/api/admin/headers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    objectMapper.writeValueAsString(List.of(param, param2)))
        )
        .andExpect(status().isCreated())
        .andDo(print());
  }

  @Test
  public void POST_HEADER_MEDIATYPE_ERROR() throws Exception {
    Map<String, String> param = ImmutableMap.of(
        "name", "test",
        "url", "https://choshsh.com");

    mockMvc.perform(
            post("/api/admin/header")
                .contentType(MediaType.TEXT_PLAIN)
                .content(objectMapper.writeValueAsString(param)))
        .andExpect(status().is4xxClientError())
        .andDo(print());
  }

  @Test
  public void List_HEADER() throws Exception {
    mockMvc.perform(
            get("/api/admin/header")
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());

    List<HeaderEntity> headerEntities = (List<HeaderEntity>) cacheManager.getCache("header")
        .get("headerList").get();

    Assertions.assertTrue(headerEntities.size() > 1);
    Assertions.assertNotNull(cacheManager.getCache("header"));
    Assertions.assertEquals("SH-GitHub", headerEntities.get(0).getName());

    IntStream.range(0, 100).forEach(i -> {
      try {
        mockMvc.perform(
            get("/api/admin/header")
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    });

  }

  @Test
  public void GET_IFRAME_LIST() throws Exception {
    mockMvc.perform(
            get("/api/admin/iframe")
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andDo(print());
  }

}
