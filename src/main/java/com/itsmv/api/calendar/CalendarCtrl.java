package com.itsmv.api.calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class CalendarCtrl {

    private final CalendarRepo calendarRepo;

    private final CalendarChildRepo calendarChildRepo;

    @Autowired
    private CalendarCtrl(CalendarRepo calendarRepo, CalendarChildRepo calendarChildRepo) {
        this.calendarRepo = calendarRepo;
        this.calendarChildRepo = calendarChildRepo;
    }

    @GetMapping(value = "/api/calendar")
    public List<CalendarEntity> list() {
//        List<CalendarEntity> list = new ArrayList<>();
//        Iterable<CalendarEntity> it = calendarRepo.findAll();
//        it.forEach(o -> {
//            o.setErrorCnt(o.getCalendarChildEntity().size());
//            list.add(o);
//        });
//        return list;
        return calendarRepo.findAll();
    }

    @PostMapping(value = "/api/calendar")
    public CalendarEntity create(@RequestBody CalendarEntity calendarEntity) {
        CalendarEntity created = calendarRepo.save(calendarEntity);
        if (created.getIsError().equalsIgnoreCase("Y")) {
            calendarChildRepo.saveAll(calendarEntity.getCalendarChildEntity());
        }
        return created;
    }

    @PutMapping(value = "/api/calendar/{id}")
    public CalendarEntity update(@PathVariable("id") String id, @RequestBody CalendarEntity calendarEntity) {
        Optional<CalendarEntity> old = calendarRepo.findById(id);
        if (old.get().getCalendarChildEntity().size() > 0) {
            calendarChildRepo.deleteAll(old.get().getCalendarChildEntity());
        }

        if (calendarEntity.getCalendarChildEntity().size() > 0) {
            calendarChildRepo.saveAll(calendarEntity.getCalendarChildEntity());
        }

        calendarEntity.setCalendarDate(id);
        calendarEntity.setCalendarChildEntity(null);
        return calendarRepo.save(calendarEntity);
    }

    @DeleteMapping(value = "/api/calendar/{id}")
    public void delete(@PathVariable("id") String id) {
        calendarRepo.deleteById(id);
    }

}