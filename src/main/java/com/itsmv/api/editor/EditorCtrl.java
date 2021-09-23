package com.itsmv.api.editor;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class EditorCtrl {

    private final EditorRepo editorRepo;


    private EditorCtrl(EditorRepo editorRepo) {
        this.editorRepo = editorRepo;
    }

    @GetMapping(value = "/api/editor")
    List<EditorEntity> list() {
        List<EditorEntity> list = new ArrayList<>();
        Iterable<EditorEntity> it = editorRepo.findAll();
        it.forEach(list::add);
        return list;
    }

    @PostMapping(value = "/api/editor")
    EditorEntity create(@RequestBody EditorEntity editorEntity) {
        return editorRepo.save(editorEntity);
    }

    @PutMapping(value = "/api/editor/{id}")
    EditorEntity update(@PathVariable("id") Long id, @RequestBody EditorEntity editorEntity) {
        editorEntity.setEntityId(id);
        return editorRepo.save(editorEntity);
    }

    @DeleteMapping(value = "/api/editor/{id}")
    public void delete(@PathVariable("id") Long id) {
        editorRepo.deleteById(id);
    }

}