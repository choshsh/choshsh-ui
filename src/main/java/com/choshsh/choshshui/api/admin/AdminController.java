package com.choshsh.choshshui.api.admin;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import io.swagger.annotations.ApiOperation;
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

  @ApiOperation(value = "해더 조회 - list")
  @GetMapping(value = PREFIX_URL + "/header")
  public List<HeaderEntity> listHeader() {
    return adminService.listHeader();
  }

  @ApiOperation(value = "헤더 등록", code = 201)
  @PostMapping(value = PREFIX_URL + "/header", consumes = APPLICATION_JSON_VALUE)
  public ResponseEntity<HeaderEntity> addHeader(@RequestBody HeaderEntity headerEntity) {
    HeaderEntity newHeaderEntity = adminService.addHeader(headerEntity);
    return ResponseEntity.status(HttpStatus.CREATED).body(newHeaderEntity);
  }

  @ApiOperation(value = "헤더 등록 - list", code = 201)
  @ResponseBody
  @PostMapping(value = PREFIX_URL + "/headers", consumes = APPLICATION_JSON_VALUE)
  public ResponseEntity<List<HeaderEntity>> addHeaders(
      @RequestBody Iterable<HeaderEntity> headerEntities) {
    List<HeaderEntity> newHeaderEntities = adminService.addHeaders(headerEntities);
    return ResponseEntity.status(HttpStatus.CREATED).body(newHeaderEntities);
  }

  @ApiOperation(value = "iframe 조회 - list")
  @GetMapping(value = PREFIX_URL + "/iframe")
  public List<IframeEntity> listIframe() {
    return adminService.listIframe();
  }

  @ApiOperation(value = "ifram 검색 by category")
  @GetMapping(value = PREFIX_URL + "/iframe/{category}")
  public List<IframeEntity> findIframeByCategory(@PathVariable("category") String category) {
    return adminService.findIframeByCategory(category);
  }

  @ApiOperation(value = "iframe 등록", code = 201)
  @PostMapping(value = PREFIX_URL + "/iframe", consumes = APPLICATION_JSON_VALUE)
  public IframeEntity addIframe(@RequestBody IframeEntity iframeEntity) {
    return adminService.addIframe(iframeEntity);
  }

  @ApiOperation(value = "iframe 등록 - list", code = 201)
  @ResponseBody
  @PostMapping(value = PREFIX_URL + "/iframes", consumes = APPLICATION_JSON_VALUE)
  public ResponseEntity<List<IframeEntity>> addIframes(
      @RequestBody Iterable<IframeEntity> iframeEntities) {
    List<IframeEntity> newIframeEntities = adminService.addIframes(iframeEntities);
    return ResponseEntity.status(HttpStatus.CREATED).body(newIframeEntities);
  }

  @ApiOperation(value = "Front-end 환경변수 리스트 조회")
  @GetMapping(value = PREFIX_URL + "/env")
  public List<EnvEntity> listEnv() {
    return adminService.listEnv();
  }

  @ApiOperation(value = "Front-end 환경변수 조회")
  @GetMapping(value = PREFIX_URL + "/env/{key}")
  public EnvEntity infoEnv(@PathVariable("key") String key) {
    return adminService.infoEnv(key);
  }

  @ApiOperation(value = "Front-end 환경변수 수정")
  @ResponseBody
  @PutMapping(value = PREFIX_URL + "/env/{id}", consumes = APPLICATION_JSON_VALUE)
  public EnvEntity addEnv(@PathVariable("id") Long id, @RequestBody EnvEntity envEntity) {
    return adminService.addEnv(id, envEntity);
  }

}
