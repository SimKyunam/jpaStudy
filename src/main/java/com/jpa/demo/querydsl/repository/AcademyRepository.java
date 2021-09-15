package com.jpa.demo.querydsl.repository;

import com.jpa.demo.querydsl.domain.Academy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AcademyRepository extends JpaRepository<Academy, Long>, AcademyRepositoryCustom {
}
