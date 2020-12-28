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
public class HeaderCtrl {

	@Autowired
	private HeaderRepo headerRepo;

	@GetMapping(value = "/api/header")
	List<HeaderEntity> list() {
		List<HeaderEntity> list = new ArrayList<>();
		Iterable<HeaderEntity> it = headerRepo.findAll();
		it.forEach(o -> list.add(o));
		return list;
	}

	@PostMapping(value = "/api/header")
	HeaderEntity create(@RequestBody HeaderEntity headerEntity) {
		HeaderEntity created = headerRepo.save(headerEntity);
		return created;
	}

	@PostMapping(value = "/api/headers")
	List<HeaderEntity> create(@RequestBody Iterable<HeaderEntity> headerEntities) {
		headerRepo.deleteAll();
		headerRepo.saveAll(headerEntities);
		return this.list();
	}

	@PutMapping(value = "/api/header/{id}")
	HeaderEntity update(@PathVariable("id") Long id, @RequestBody HeaderEntity headerEntity) {
		headerEntity.setId(id);
		HeaderEntity updated = headerRepo.save(headerEntity);
		return updated;
	}

	@DeleteMapping(value = "/api/header/{id}")
	public void delete(@PathVariable("id") Long id) {
		headerRepo.deleteById(id);
	}
}
