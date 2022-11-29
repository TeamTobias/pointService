package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MypointMapperTest {

    private MypointMapper mypointMapper;

    @BeforeEach
    public void setUp() {
        mypointMapper = new MypointMapperImpl();
    }
}
