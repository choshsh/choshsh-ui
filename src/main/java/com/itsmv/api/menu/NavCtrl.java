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
public class NavCtrl {

	@Autowired
	private NavRepo navRepo;

	@GetMapping(value = "/api/nav")
	List<NavEntity> list() {
		List<NavEntity> list = new ArrayList<>();
		Iterable<NavEntity> it = navRepo.findAll();
		it.forEach(o -> list.add(o));
		return list;
	}

	@PostMapping(value = "/api/nav")
	NavEntity create(@RequestBody NavEntity navEntity) {
		NavEntity created = navRepo.save(navEntity);
		return created;
	}

	@PostMapping(value = "/api/navs")
	List<NavEntity> create(@RequestBody Iterable<NavEntity> navEntities) throws Exception {
		navRepo.deleteAll();
		navRepo.saveAll(navEntities);
		return this.list();
	}

	@PutMapping(value = "/api/nav/{id}")
	NavEntity update(@PathVariable("id") Long id, @RequestBody NavEntity navEntity) {
		navEntity.setId(id);
		NavEntity updated = navRepo.save(navEntity);
		return updated;
	}

	@DeleteMapping(value = "/api/nav/{id}")
	public void delete(@PathVariable("id") Long id) {
		navRepo.deleteById(id);
	}
}