package com.choshsh.choshshui.api.admin;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IframeRepository extends CrudRepository<IframeEntity, Long> {

  List<IframeEntity> findAllByOrderByCategoryAscSortAsc();

  List<IframeEntity> findAllByCategoryOrderBySortAsc(String category);

}
