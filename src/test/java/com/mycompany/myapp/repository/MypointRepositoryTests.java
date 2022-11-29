package com.mycompany.myapp.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

@SpringBootTest
@Profile("local")
public class MypointRepositoryTests {

    @Autowired
    private MypointRepository mypointRepository;

    // set profile to dev
    @BeforeAll
    public static void beforeAll() {
        System.setProperty("spring.profiles.active", "dev");
    }

    // return last one record userid
    @Test
    public void testFindByUserid() {
        Long total = 100L;
        System.out.println();
        System.out.println(total + mypointRepository.findTopByUseridOrderByCreatedAtDesc("124").getUnit_amount());
        System.out.println();
    }
}
