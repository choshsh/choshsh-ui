package com.itsmv.api.server;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ServerSvc serverSvc;

    @Autowired
    private ServerRepo serverRepo;

    @GetMapping(value = "/api/server")
    List<ServerEntity> list() {
        return serverSvc.list();
    }

    @PostMapping(value = "/api/server")
    ServerEntity create(@RequestBody ServerEntity serverEntity) {
        ServerEntity created = serverRepo.save(serverEntity);
        return created;
    }

    @PutMapping(value = "/api/server/{id}")
    ServerEntity update(@PathVariable("id") Long id, @RequestBody ServerEntity serverEntity) {
        serverEntity.setServerId(id);
        ServerEntity updated = serverRepo.save(serverEntity);
        return updated;
    }

    @DeleteMapping(value = "/api/server/{id}")
    public void delete(@PathVariable("id") Long id) {
        serverRepo.deleteById(id);
    }

    @PostMapping(value = "/api/server/excel")
    public void excel(HttpServletRequest req, HttpServletResponse res) {
        try {
            serverSvc.excel(req, res);
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
    }
}
