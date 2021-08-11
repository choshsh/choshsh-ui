package com.itsmv.api.calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CalendarChildCtrl {

    private final CalendarChildRepo calendarChildRepo;


    private CalendarChildCtrl(CalendarChildRepo calendarChildRepo) {
        this.calendarChildRepo = calendarChildRepo;
    }

    @GetMapping(value = "/api/calendarChild")
    public List<CalendarChildEntity> list() {
        List<CalendarChildEntity> list = new ArrayList<>();
        Iterable<CalendarChildEntity> it = calendarChildRepo.findAll();
        it.forEach(list::add);
        return list;
    }

    @PostMapping(value = "/api/calendarChild")
    public CalendarChildEntity create(@RequestBody CalendarChildEntity calendarChildEntity) {
        return calendarChildRepo.save(calendarChildEntity);
    }

    @PutMapping(value = "/api/calendarChild/{id}")
    public CalendarChildEntity update(@PathVariable("id") Long id, @RequestBody CalendarChildEntity calendarChildEntity) {
        calendarChildEntity.setId(id);
        return calendarChildRepo.save(calendarChildEntity);
    }

    @DeleteMapping(value = "/api/calendarChild/{id}")
    public void delete(@PathVariable("id") Long id) {
        calendarChildRepo.deleteById(id);
    }

}