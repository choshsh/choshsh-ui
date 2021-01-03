package com.itsmv.api.editor;

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
public class EditorCtrl {

	@Autowired
	private EditorRepo editorRepo;

	@GetMapping(value = "/api/editor")
	List<EditorEntity> list() {
		List<EditorEntity> list = new ArrayList<>();
		Iterable<EditorEntity> it = editorRepo.findAll();
		it.forEach(o -> list.add(o));
		return list;
	}

	@PostMapping(value = "/api/editor")
	EditorEntity create(@RequestBody EditorEntity editorEntity) {
		EditorEntity created = editorRepo.save(editorEntity);
		return created;
	}

	@PutMapping(value = "/api/editor/{id}")
	EditorEntity update(@PathVariable("id") Long id, @RequestBody EditorEntity editorEntity) {
		editorEntity.setEntityId(id);
		EditorEntity updated = editorRepo.save(editorEntity);
		return updated;
	}

	@DeleteMapping(value = "/api/editor/{id}")
	public void delete(@PathVariable("id") Long id) {
		editorRepo.deleteById(id);
	}

}