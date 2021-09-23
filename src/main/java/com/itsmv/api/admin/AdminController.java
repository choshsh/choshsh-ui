package com.itsmv.api.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class AdminController {
    private final HeaderRepository headerRepo;
    private final IframeRepository iframeRepo;

    @Autowired
    private AdminController(HeaderRepository headerRepo, IframeRepository iframeRepo) {
        this.headerRepo = headerRepo;
        this.iframeRepo = iframeRepo;
    }

    @GetMapping(value = "/api/admin/header")
    public List<HeaderEntity> menu() {
        return headerRepo.findAllByOrderBySortAsc();
    }

    @ResponseBody
    @PostMapping(value = "/api/admin/header")
    public int menuInsert(@RequestBody Iterable<HeaderEntity> headerEntities) {
        try {
            headerRepo.deleteAll();
            headerRepo.saveAll(headerEntities);
            return 1;
        } catch (Exception e) {
            log.warn(e.getMessage());
            return -1;
        }
    }

    @GetMapping(value = "/api/admin/iframe")
    public List<IframeEntity> iframes() {
        return iframeRepo.findAllByOrderBySortAsc();
    }

    @GetMapping(value = "/api/admin/iframe/{category}")
    public List<IframeEntity> iframe(@PathVariable("category") String category) {
        return iframeRepo.findAllByCategoryOrderBySortAsc(category);
    }

    @ResponseBody
    @PostMapping(value = "/api/admin/iframe")
    public int iframeInsert(@RequestBody Iterable<IframeEntity> iframeEntities) {
        try {
            iframeRepo.deleteAll();
            iframeRepo.saveAll(iframeEntities);
            return 1;
        } catch (Exception e) {
            log.warn(e.getMessage());
            return -1;
        }
    }

}
