package com.itsmv.test;

import com.itsmv.api.admin.HeaderEntity;
import com.itsmv.api.admin.HeaderRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.stream.Stream;

import static org.assertj.core.api.BDDAssertions.then;

@SpringBootTest
@ActiveProfiles({"dev"})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ServiceTest {

    @Autowired
    private HeaderRepository headerRepository;

    @Test
    @Order(1)
    public void test() {
        Iterable<HeaderEntity> list = headerRepository.findAll();
        then(Stream.of(list).toList().size()).isGreaterThan(0);
    }
}
