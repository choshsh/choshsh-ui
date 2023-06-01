package com.choshsh.choshshui.api.admin;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AdminService {

  private final HeaderRepository headerRepo;
  private final IframeRepository iframeRepo;
  private final EnvRepository envRepo;

  /**
   * 헤더 리스트 조회
   *
   * @return List
   */
  public List<HeaderEntity> listHeader() {
    return headerRepo.findAllByOrderBySortAsc();
  }

  /**
   * 헤더 등록
   *
   * @param headerEntity
   * @return HeaderEntity
   */
  public HeaderEntity addHeader(HeaderEntity headerEntity) {
    return headerRepo.save(headerEntity);
  }

  /**
   * 헤더 리스트 일괄 등록
   *
   * @param headerEntities
   * @return List
   */
  @Transactional
  public List<HeaderEntity> addHeaders(Iterable<HeaderEntity> headerEntities) {
    headerRepo.deleteAll();
    List<HeaderEntity> newHeaderEntities = new ArrayList<>();
    headerRepo.saveAll(headerEntities).forEach(newHeaderEntities::add);
    return newHeaderEntities;
  }

  /**
   * iframe 리스트 조회
   *
   * @return List
   */
  public List<IframeEntity> listIframe() {
    return iframeRepo.findAllByOrderByCategoryAscSortAsc();
  }

  /**
   * iframe 검색 by category
   *
   * @param category
   * @return List
   */
  public List<IframeEntity> findIframeByCategory(String category) {
    return iframeRepo.findAllByCategoryOrderBySortAsc(category);
  }

  /**
   * iframe 등록
   *
   * @param iframeEntity
   * @return IframeEntity
   */
  public IframeEntity addIframe(IframeEntity iframeEntity) {
    return iframeRepo.save(iframeEntity);
  }

  /**
   * iframe 리스트 일괄 등록
   *
   * @param iframeEntities
   * @return List
   */
  @Transactional
  public List<IframeEntity> addIframes(Iterable<IframeEntity> iframeEntities) {
    iframeRepo.deleteAll();
    List<IframeEntity> newIframeEntities = new ArrayList<>();
    iframeRepo.saveAll(iframeEntities).forEach(newIframeEntities::add);
    return newIframeEntities;
  }

  /**
   * env 리스트 조회
   *
   * @return EnvEntity
   */
  public List<EnvEntity> listEnv() {
    envRepo.findAllByOrderByKeyAsc().forEach(System.out::println);
    return envRepo.findAllByOrderByKeyAsc();
  }

  /**
   * env 조회 by key
   *
   * @param key
   * @return EnvEntity
   */
  public EnvEntity infoEnv(String key) {
    return envRepo.findByKey(key).orElseGet(EnvEntity::new);
  }

  /**
   * env 등록
   *
   * @param id
   * @param envEntity
   * @return EnvEntity
   */
  public EnvEntity addEnv(Long id, EnvEntity envEntity) {
    envEntity.setId(id);
    return envRepo.save(envEntity);
  }

}
