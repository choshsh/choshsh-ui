package com.choshsh.choshshui.api.admin;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class AdminController {

  private static final String PREFIX_URL = "/api/admin";
  private final AdminService adminService;

  private AdminController(EnvRepository envRepository, AdminService adminService) {
    this.adminService = adminService;
  }

  @GetMapping(value = PREFIX_URL + "/header")
  @Operation(
      description = "List headers",
      method = "GET",
      responses = @ApiResponse(responseCode = "200", content = @Content(array = @ArraySchema(schema = @Schema(implementation = HeaderEntity.class))))
  )
  public List<HeaderEntity> listHeader() {
    return adminService.listHeader();
  }

  @PostMapping(value = PREFIX_URL + "/header", consumes = APPLICATION_JSON_VALUE)
  @Operation(
      description = "Add a header",
      method = "POST",
      responses = @ApiResponse(responseCode = "201", content = @Content(schema = @Schema(implementation = HeaderEntity.class)))
  )
  public ResponseEntity<HeaderEntity> addHeader(@RequestBody HeaderEntity headerEntity) {
    HeaderEntity newHeaderEntity = adminService.addHeader(headerEntity);
    return ResponseEntity.status(HttpStatus.CREATED).body(newHeaderEntity);
  }

  @PostMapping(value = PREFIX_URL + "/headers", consumes = APPLICATION_JSON_VALUE)
  @ResponseBody
  @Operation(
      description = "Add header multiple",
      method = "POST",
      responses = @ApiResponse(responseCode = "201", content = @Content(array = @ArraySchema(schema = @Schema(implementation = HeaderEntity.class))))
  )
  public ResponseEntity<List<HeaderEntity>> addHeaders(
      @RequestBody Iterable<HeaderEntity> headerEntities) {
    List<HeaderEntity> newHeaderEntities = adminService.addHeaders(headerEntities);
    return ResponseEntity.status(HttpStatus.CREATED).body(newHeaderEntities);
  }

  @Operation(description = "iframe 조회 - list")
  @GetMapping(value = PREFIX_URL + "/iframe")
  public List<IframeEntity> listIframe() {
    return adminService.listIframe();
  }

  @Operation(description = "ifram 검색 by category")
  @GetMapping(value = PREFIX_URL + "/iframe/{category}")
  public List<IframeEntity> findIframeByCategory(@PathVariable("category") String category) {
    return adminService.findIframeByCategory(category);
  }

  @Operation(description = "iframe 등록")
  @PostMapping(value = PREFIX_URL + "/iframe", consumes = APPLICATION_JSON_VALUE)
  public IframeEntity addIframe(@RequestBody IframeEntity iframeEntity) {
    return adminService.addIframe(iframeEntity);
  }

  @Operation(description = "iframe 등록 - list")
  @ResponseBody
  @PostMapping(value = PREFIX_URL + "/iframes", consumes = APPLICATION_JSON_VALUE)
  public ResponseEntity<List<IframeEntity>> addIframes(
      @RequestBody Iterable<IframeEntity> iframeEntities) {
    List<IframeEntity> newIframeEntities = adminService.addIframes(iframeEntities);
    return ResponseEntity.status(HttpStatus.CREATED).body(newIframeEntities);
  }

  @Operation(description = "Front-end 환경변수 리스트 조회")
  @GetMapping(value = PREFIX_URL + "/env")
  public List<EnvEntity> listEnv() {
    return adminService.listEnv();
  }

  @Operation(description = "Front-end 환경변수 조회")
  @GetMapping(value = PREFIX_URL + "/env/{key}")
  public EnvEntity infoEnv(@PathVariable("key") String key) {
    return adminService.infoEnv(key);
  }

  @Operation(description = "Front-end 환경변수 수정")
  @ResponseBody
  @PutMapping(value = PREFIX_URL + "/env/{id}", consumes = APPLICATION_JSON_VALUE)
  public EnvEntity addEnv(@PathVariable("id") Long id, @RequestBody EnvEntity envEntity) {
    return adminService.addEnv(id, envEntity);
  }

}
