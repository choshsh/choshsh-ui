package com.itsmv.api.calendar;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CalendarRepo extends CrudRepository<CalendarEntity, String> {

}
