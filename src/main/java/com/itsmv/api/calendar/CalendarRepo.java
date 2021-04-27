package com.itsmv.api.calendar;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CalendarRepo extends CrudRepository<CalendarEntity, String> {

    @EntityGraph(attributePaths = {"calendarChildEntity", "calendarChildEntity.serverEntity"})
    List<CalendarEntity> findAll();

}
