package com.itsmv.api.calendar;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CalendarCtrl {

	@Autowired
	private CalendarRepo calendarRepo;

	@Autowired
	private CalendarChildRepo calendarChildRepo;

	@GetMapping(value = "/api/calendar")
	List<CalendarEntity> list() {
		List<CalendarEntity> list = new ArrayList<>();
		Iterable<CalendarEntity> it = calendarRepo.findAll();
		it.forEach(o -> {
			o.setErrorCnt(o.getCalendarChildEntity().size());
			list.add(o);
		});
		return list;
	}

	@PostMapping(value = "/api/calendar")
	CalendarEntity create(@RequestBody CalendarEntity calendarEntity) {
		CalendarEntity created = calendarRepo.save(calendarEntity);
		calendarChildRepo.saveAll(calendarEntity.getCalendarChildEntity());
		return created;
	}

	@PutMapping(value = "/api/calendar/{id}")
	CalendarEntity update(@PathVariable("id") String id, @RequestBody CalendarEntity calendarEntity) {
		Optional<CalendarEntity> old = calendarRepo.findById(id);
		if (old.get().getCalendarChildEntity().size() > 0) {
			calendarChildRepo.deleteAll(old.get().getCalendarChildEntity());
		}

		if (calendarEntity.getCalendarChildEntity().size() > 0) {
			calendarChildRepo.saveAll(calendarEntity.getCalendarChildEntity());
		}

		calendarEntity.setCalendarDate(id);
		calendarEntity.setCalendarChildEntity(null);
		CalendarEntity updated = calendarRepo.save(calendarEntity);
		return updated;
	}

	@DeleteMapping(value = "/api/calendar/{id}")
	public void delete(@PathVariable("id") String id) {
		calendarRepo.deleteById(id);
	}

}