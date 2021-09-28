package com.choshsh.itsmv.api.admin;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HeaderRepository extends CrudRepository<HeaderEntity, Long> {

  List<HeaderEntity> findAllByOrderBySortAsc();

}
