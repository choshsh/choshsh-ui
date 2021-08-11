package com.itsmv.api.menu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class MonotoringCtrl {

    private final MonitoringRepo monitoringRepo;

    private MonotoringCtrl(MonitoringRepo monitoringRepo) {
        this.monitoringRepo = monitoringRepo;
    }

    @GetMapping(value = "/api/monitoring")
    public List<MonitoringEntity> list() {
        List<MonitoringEntity> list = new ArrayList<>();
        Iterable<MonitoringEntity> it = monitoringRepo.findAll();
        it.forEach(list::add);
        return list;
    }

    @PostMapping(value = "/api/monitoring")
    public MonitoringEntity create(@RequestBody MonitoringEntity monitoringEntity) {
        return monitoringRepo.save(monitoringEntity);
    }

    @PostMapping(value = "/api/monitorings")
    public List<MonitoringEntity> create(@RequestBody Iterable<MonitoringEntity> monitoringEntities) throws Exception {
        monitoringRepo.deleteAll();
        monitoringRepo.saveAll(monitoringEntities);
        return this.list();
    }

    @PutMapping(value = "/api/monitoring/{id}")
    public MonitoringEntity update(@PathVariable("id") Long id, @RequestBody MonitoringEntity monitoringEntity) {
        monitoringEntity.setId(id);
        return monitoringRepo.save(monitoringEntity);
    }

    @DeleteMapping(value = "/api/monitoring/{id}")
    public void delete(@PathVariable("id") Long id) {
        monitoringRepo.deleteById(id);
    }
}