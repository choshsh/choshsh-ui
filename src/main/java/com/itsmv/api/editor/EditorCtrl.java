package com.itsmv.api.editor;

import com.itsmv.api.enums.DeptGroup;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class EditorCtrl {

    private final EditorRepo editorRepo;

    private EditorCtrl(EditorRepo editorRepo) {
        this.editorRepo = editorRepo;
    }

    @GetMapping(value = "/api/editor")
    List<EditorEntity> list() {
        Iterable<EditorEntity> t = editorRepo.findAll();
        t.forEach(o -> o.setDept(DeptGroup.TEAM01));
        editorRepo.saveAll(t);

        List<EditorEntity> list = new ArrayList<>();
        Iterable<EditorEntity> it = editorRepo.findAll();
        it.forEach(list::add);
        return list;
    }

    @PostMapping(value = "/api/editor")
    EditorEntity create(@RequestBody EditorEntity editorEntity) {

        editorEntity.setDept(DeptGroup.TEAM01);
        return editorRepo.save(editorEntity);
    }

    @PutMapping(value = "/api/editor/{id}")
    EditorEntity update(@PathVariable("id") String id, @RequestBody Map<?, ?> object) {
        System.out.println(1);
//        ObjectMapper m = new ObjectMapper();
//        EditorEntity editorEntity = m.convertValue(object, EditorEntity.class);
//        editorEntity.setEntityId(Long.getLong(id));
//        return editorRepo.save(editorEntity);
        return new EditorEntity();
    }

    @DeleteMapping(value = "/api/editor/{id}")
    public void delete(@PathVariable("id") Long id) {
        editorRepo.deleteById(id);
    }

}