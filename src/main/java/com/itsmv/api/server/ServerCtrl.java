package com.itsmv.api.server;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
public class ServerCtrl {

    private final ServerSvc serverSvc;
    private final ServerRepo serverRepo;

    @Autowired
    private HttpServletResponse res;
    private ServerCtrl(ServerSvc serverSvc, ServerRepo serverRepo) {
        this.serverSvc = serverSvc;
        this.serverRepo = serverRepo;
    }

    @PreAuthorize(value = "#oauth2.hasScope('1')")
    @GetMapping("/api/ping")
    public String  ping() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        log.info("auth: {}", context.getAuthentication().getDetails());
        return "Scopes: " + authentication.getAuthorities();
    }

    @GetMapping(value = "/api/server")
    public List<ServerEntity> list() {
        return serverSvc.list();
    }

    @GetMapping(value = "/api/server/{id}")
    public Optional<ServerEntity> info(@PathVariable("id") Long id) {
        return serverRepo.findById(id);
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
