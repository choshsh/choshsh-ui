package com.itsmv.api.server;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class ServerCtrl {

    private final ServerSvc serverSvc;
    private final ServerRepo serverRepo;

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
        serverEntity.setId(id);
        return serverRepo.save(serverEntity);
    }

    @DeleteMapping(value = "/api/server/{id}")
    public void delete(@PathVariable("id") Long id) {
        serverRepo.deleteById(id);
    }

//    @PostMapping(value = "/api/server/excel")
//    public void excel(HttpServletRequest req, HttpServletResponse res) {
//        try {
//            serverSvc.excel(req, res);
//        } catch (Exception e) {
//            logger.info(e.getMessage());
//        }
//    }
}
