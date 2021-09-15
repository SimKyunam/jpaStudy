package com.jpa.demo.querydsl.repository;

import com.jpa.demo.querydsl.domain.Academy;

import java.util.List;

public interface AcademyRepositoryCustom {
    List<Academy> findByName(String name);
}
