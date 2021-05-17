package com.itsmv.api.calendar;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CalendarChildRepo extends CrudRepository<CalendarChildEntity, Long> {
    List<CalendarChildEntity> findAll();
}
