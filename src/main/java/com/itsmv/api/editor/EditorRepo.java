package com.itsmv.api.editor;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EditorRepo extends CrudRepository<EditorEntity, Long> {
}
