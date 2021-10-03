package com.choshsh.choshshui.api.admin;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IframeRepository extends CrudRepository<IframeEntity, Long> {

  List<IframeEntity> findAllByOrderBySortAsc();

  List<IframeEntity> findAllByOrderByCategoryAsc();

  List<IframeEntity> findAllByCategoryOrderBySortAsc(String category);

}
