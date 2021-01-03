package com.itsmv.api.server;

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
public class ServerCtrl {

	@Autowired
	private ServerRepo serverRepo;

	@GetMapping(value = "/api/server")
	List<ServerEntity> list() {
		List<ServerEntity> list = new ArrayList<>();
		Iterable<ServerEntity> it = serverRepo.findAll();
		it.forEach(o -> list.add(o));
		return list;
	}

	@PostMapping(value = "/api/server")
	ServerEntity create(@RequestBody ServerEntity serverEntity) {
		ServerEntity created = serverRepo.save(serverEntity);
		return created;
	}

	@PostMapping(value = "/api/servers")
	List<ServerEntity> create(@RequestBody Iterable<ServerEntity> serverEntities) {
		serverRepo.deleteAll();
		serverRepo.saveAll(serverEntities);
		return this.list();
	}

	@PutMapping(value = "/api/server/{id}")
	ServerEntity update(@PathVariable("id") Long id, @RequestBody ServerEntity serverEntity) {
		serverEntity.setId(id);
		ServerEntity updated = serverRepo.save(serverEntity);
		return updated;
	}

	@DeleteMapping(value = "/api/server/{id}")
	public void delete(@PathVariable("id") Long id) {
		serverRepo.deleteById(id);
	}
}
