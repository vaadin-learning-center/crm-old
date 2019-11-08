package com.vaadin.tutorial.spring.backend.repository;

import com.vaadin.tutorial.spring.backend.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

  @Query("select c from Customer c where lower(c.firstName) like %:searchTerm% or lower(c.lastName) like %:searchTerm%")
  List<Customer> search(@Param("searchTerm") String searchTerm);
}
