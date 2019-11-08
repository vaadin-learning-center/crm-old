package com.vaadin.tutorial.spring.backend.service;

import com.vaadin.tutorial.spring.backend.entity.Company;
import com.vaadin.tutorial.spring.backend.repository.CompanyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {

  private CompanyRepository companyRepository;

  public CompanyService(CompanyRepository companyRepository) {
    this.companyRepository = companyRepository;
  }

  public List<Company> findAll() {
    return companyRepository.findAll();
  }
}
