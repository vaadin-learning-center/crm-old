package com.vaadin.tutorial.spring.backend.repository;

import com.vaadin.tutorial.spring.backend.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
