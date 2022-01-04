package com.choshsh.choshshui.api.admin;

import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnvRepository extends CrudRepository<EnvEntity, Long> {
  List<EnvEntity> findAllByOrderByKeyAsc();
  Optional<EnvEntity> findByKey(String key);
}
