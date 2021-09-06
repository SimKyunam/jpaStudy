package com.jpa.demo.querydsl.study;

import com.jpa.demo.querydsl.domain.Academy;
import com.jpa.demo.repository.AcademyRepository;
import com.jpa.demo.repository.AcademyRepositorySupport;
import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class QueryStudyTest {
    @Autowired
    private AcademyRepository academyRepository;

    @Autowired
    private AcademyRepositorySupport academyRepositorySupport;

    @AfterEach
    public void tearDown() throws Exception {
        academyRepository.deleteAllInBatch();
    }

    @DisplayName("1. 기본 테스트")
    @Test
    void test_1(){
        //given
        String name = "홍길동";
        String address = "test@gmail.com";
        academyRepository.save(new Academy(name, address));

        //when
//        List<Academy> result = academyRepositorySupport.findByName(name);
        List<Academy> result = academyRepository.findByName(name);

        //then
        assertEquals(result.size(), 1);
        assertEquals(result.get(0).getAddress(), address);
    }

}
