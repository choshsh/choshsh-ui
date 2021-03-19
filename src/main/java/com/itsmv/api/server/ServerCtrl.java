package com.itsmv.api.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
public class ServerCtrl {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ServerSvc serverSvc;

    private final ServerRepo serverRepo;

    @Autowired
    private ServerCtrl(ServerSvc serverSvc, ServerRepo serverRepo) {
        this.serverSvc = serverSvc;
        this.serverRepo = serverRepo;
    }

    @GetMapping(value = "/api/server")
    public List<ServerEntity> list() {
        return serverSvc.list();
    }

    @PostMapping(value = "/api/server")
    public ServerEntity create(@RequestBody ServerEntity serverEntity) {
        return serverRepo.save(serverEntity);
    }

    @PutMapping(value = "/api/server/{id}")
    public ServerEntity update(@PathVariable("id") Long id, @RequestBody ServerEntity serverEntity) {
        serverEntity.setServerId(id);
        return serverRepo.save(serverEntity);
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
