package com.itsmv.test;

import com.itsmv.api.server.ServerEntity;
import com.itsmv.api.server.ServerRepo;
import com.itsmv.api.server.ServerSvc;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.BDDAssertions.then;

@SpringBootTest
@ActiveProfiles({"dev"})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ServiceTest {
    @Autowired
    private ServerSvc serverSvc;
    @Autowired
    private ServerRepo serverRepo;

    @Test
    @Order(1)
    public void test() {
        List<ServerEntity> list = serverSvc.list();
        Optional<ServerEntity> info = serverRepo.findById(1L);

        System.out.println(list.stream().map(ServerEntity::getServerNm).collect(Collectors.toList()));
        System.out.println(info.orElseGet(ServerEntity::new).getServerNm());

        System.out.println(ServerEntity.builder().id(2L).build().getId());

        then(list).isNotNull();
    }
}
