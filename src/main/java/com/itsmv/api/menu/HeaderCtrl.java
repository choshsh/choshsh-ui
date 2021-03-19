package com.itsmv.api.menu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class HeaderCtrl {

    private final HeaderRepo headerRepo;

    @Autowired
    private HeaderCtrl(HeaderRepo headerRepo) {
        this.headerRepo = headerRepo;
    }

    @GetMapping(value = "/api/header")
    public List<HeaderEntity> list() {
        List<HeaderEntity> list = new ArrayList<>();
        Iterable<HeaderEntity> it = headerRepo.findAll();
        it.forEach(list::add);
        return list;
    }

    @PostMapping(value = "/api/header")
    public HeaderEntity create(@RequestBody HeaderEntity headerEntity) {
        return headerRepo.save(headerEntity);
    }

    @PostMapping(value = "/api/headers")
    public List<HeaderEntity> create(@RequestBody Iterable<HeaderEntity> headerEntities) {
        headerRepo.deleteAll();
        headerRepo.saveAll(headerEntities);
        return this.list();
    }

    @PutMapping(value = "/api/header/{id}")
    public HeaderEntity update(@PathVariable("id") Long id, @RequestBody HeaderEntity headerEntity) {
        headerEntity.setId(id);
        return headerRepo.save(headerEntity);
    }

    @DeleteMapping(value = "/api/header/{id}")
    public void delete(@PathVariable("id") Long id) {
        headerRepo.deleteById(id);
    }
}
