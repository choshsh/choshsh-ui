package com.choshsh.choshshui.api.admin;

import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
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
  private final HeaderRepository headerRepo;
  private final IframeRepository iframeRepo;
  private final EnvRepository envRepository;

  private AdminController(HeaderRepository headerRepo, IframeRepository iframeRepo,
      EnvRepository envRepository) {
    this.headerRepo = headerRepo;
    this.iframeRepo = iframeRepo;
    this.envRepository = envRepository;
  }

  @ApiOperation(value = "Front-end 헤더 리스트 조회")
  @GetMapping(value = PREFIX_URL + "/header")
  public List<HeaderEntity> listHeader() {
    return headerRepo.findAllByOrderBySortAsc();
  }

  @ApiOperation(value = "Front-end 헤더 등록")
  @ResponseBody
  @PostMapping(value = PREFIX_URL + "/header")
  public int insertHeader(@RequestBody Iterable<HeaderEntity> headerEntities) {
    try {
      headerRepo.deleteAll();
      headerRepo.saveAll(headerEntities);
      return 1;
    } catch (Exception e) {
      log.warn(e.getMessage());
      return -1;
    }
  }

  @ApiOperation(value = "Front-end iframe 리스트 조회")
  @GetMapping(value = PREFIX_URL + "/iframe")
  public List<IframeEntity> listIframe() {
    return iframeRepo.findAllByOrderByCategoryAsc();
  }

  @GetMapping(value = PREFIX_URL + "/iframe/{category}")
  public List<IframeEntity> listIframeCategory(@PathVariable("category") String category) {
    return iframeRepo.findAllByCategoryOrderBySortAsc(category);
  }

  @ApiOperation(value = "Front-end iframe 등록")
  @ResponseBody
  @PostMapping(value = PREFIX_URL + "/iframe")
  public int insertIframe(@RequestBody Iterable<IframeEntity> iframeEntities) {
    try {
      iframeRepo.deleteAll();
      iframeRepo.saveAll(iframeEntities);
      return 1;
    } catch (Exception e) {
      log.warn(e.getMessage());
      return -1;
    }
  }

  @ApiOperation(value = "Front-end 환경변수 리스트 조회")
  @GetMapping(value = PREFIX_URL + "/env")
  public List<EnvEntity> listEnv() {
    return envRepository.findAllByOrderByKeyAsc();
  }

  @ApiOperation(value = "Front-end 환경변수 조회")
  @GetMapping(value = PREFIX_URL + "/env/{key}")
  public EnvEntity infoEnv(@PathVariable("key") String key) {
    return envRepository.findByKey(key)
        .orElseGet(EnvEntity::new);
  }

  @ApiOperation(value = "Front-end 환경변수 수정")
  @ResponseBody
  @PutMapping(value = PREFIX_URL + "/env/{id}")
  public EnvEntity insertEnv(@PathVariable("id") Long id, @RequestBody EnvEntity envEntity) {
    envEntity.setId(id);
    return envRepository.save(envEntity);
  }

}
