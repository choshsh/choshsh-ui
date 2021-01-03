package com.itsmv.api.calendar;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CalendarChildCtrl {

	@Autowired
	private CalendarChildRepo calendarChildRepo;

	@GetMapping(value = "/api/calendarChild")
	List<CalendarChildEntity> list() {
		List<CalendarChildEntity> list = new ArrayList<>();
		Iterable<CalendarChildEntity> it = calendarChildRepo.findAll();
		it.forEach(o -> list.add(o));
		return list;
	}

	@PostMapping(value = "/api/calendarChild")
	CalendarChildEntity create(@RequestBody CalendarChildEntity calendarChildEntity) {
		CalendarChildEntity created = calendarChildRepo.save(calendarChildEntity);
		return created;
	}

	@PutMapping(value = "/api/calendarChild/{id}")
	CalendarChildEntity update(@PathVariable("id") Long id, @RequestBody CalendarChildEntity calendarChildEntity) {
		calendarChildEntity.setId(id);
		CalendarChildEntity updated = calendarChildRepo.save(calendarChildEntity);
		return updated;
	}

	@DeleteMapping(value = "/api/calendarChild/{id}")
	public void delete(@PathVariable("id") Long id) {
		calendarChildRepo.deleteById(id);
	}

}