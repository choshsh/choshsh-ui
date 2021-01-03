package com.itsmv.api.server;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServerRepo extends CrudRepository<ServerEntity, Long> {
}
