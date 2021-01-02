package com.itsmv.api.menu;

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
public class MonotoringCtrl {

	@Autowired
	private MonitoringRepo monitoringRepo;

	@GetMapping(value = "/api/monitoring")
	List<MonitoringEntity> list() {
		List<MonitoringEntity> list = new ArrayList<>();
		Iterable<MonitoringEntity> it = monitoringRepo.findAll();
		it.forEach(o -> list.add(o));
		return list;
	}

	@PostMapping(value = "/api/monitoring")
	MonitoringEntity create(@RequestBody MonitoringEntity monitoringEntity) {
		MonitoringEntity created = monitoringRepo.save(monitoringEntity);
		return created;
	}

	@PostMapping(value = "/api/monitorings")
	List<MonitoringEntity> create(@RequestBody Iterable<MonitoringEntity> monitoringEntities) throws Exception {
		monitoringRepo.deleteAll();
		monitoringRepo.saveAll(monitoringEntities);
		return this.list();
	}

	@PutMapping(value = "/api/monitoring/{id}")
	MonitoringEntity update(@PathVariable("id") Long id, @RequestBody MonitoringEntity monitoringEntity) {
		monitoringEntity.setId(id);
		MonitoringEntity updated = monitoringRepo.save(monitoringEntity);
		return updated;
	}

	@DeleteMapping(value = "/api/monitoring/{id}")
	public void delete(@PathVariable("id") Long id) {
		monitoringRepo.deleteById(id);
	}
}