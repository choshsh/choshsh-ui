package com.itsmv.api.menu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class NavCtrl {

    private final NavRepo navRepo;

    @Autowired
    private NavCtrl(NavRepo navRepo) {
        this.navRepo = navRepo;
    }

    @GetMapping(value = "/api/nav")
    public List<NavEntity> list() {
        List<NavEntity> list = new ArrayList<>();
        Iterable<NavEntity> it = navRepo.findAll();
        it.forEach(list::add);
        return list;
    }

    @PostMapping(value = "/api/nav")
    public NavEntity create(@RequestBody NavEntity navEntity) {
        return navRepo.save(navEntity);
    }

    @PostMapping(value = "/api/navs")
    public List<NavEntity> create(@RequestBody Iterable<NavEntity> navEntities) throws Exception {
        navRepo.deleteAll();
        navRepo.saveAll(navEntities);
        return this.list();
    }

    @PutMapping(value = "/api/nav/{id}")
    public NavEntity update(@PathVariable("id") Long id, @RequestBody NavEntity navEntity) {
        navEntity.setId(id);
        return navRepo.save(navEntity);
    }

    @DeleteMapping(value = "/api/nav/{id}")
    public void delete(@PathVariable("id") Long id) {
        navRepo.deleteById(id);
    }
}