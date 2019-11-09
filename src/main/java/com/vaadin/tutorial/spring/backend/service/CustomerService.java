package com.vaadin.tutorial.spring.backend.service;

import com.vaadin.tutorial.spring.backend.entity.Company;
import com.vaadin.tutorial.spring.backend.entity.Customer;
import com.vaadin.tutorial.spring.backend.entity.CustomerStatus;
import com.vaadin.tutorial.spring.backend.repository.CompanyRepository;
import com.vaadin.tutorial.spring.backend.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CustomerService {
	private static final Logger LOGGER = Logger.getLogger(CustomerService.class.getName());
	private CustomerRepository customerRepository;
	private CompanyRepository companyRepository;

	public CustomerService(CustomerRepository customerRepository, CompanyRepository companyRepository) {
		this.customerRepository = customerRepository;
		this.companyRepository = companyRepository;
	}

	/**
	 * @return all available Customer objects.
	 */
	public List<Customer> findAll() {
		return customerRepository.findAll();
	}

	/**
	 * Finds all Customer's that match given filter.
	 *
	 * @param stringFilter
	 *            filter that returned objects should match or null/empty string
	 *            if all objects should be returned.
	 * @return list a Customer objects
	 */
	public synchronized List<Customer> findAll(String stringFilter) {
		if (stringFilter == null || stringFilter.isEmpty()) {
			return customerRepository.findAll();
		} else {
		  return customerRepository.search(stringFilter);
		}
	}

	/**
	 * @return the amount of all customers in the system
	 */
	public long count() {
		return customerRepository.count();
	}

	/**
	 * Deletes a customer from a system
	 *
	 * @param customer
	 *            the Customer to be deleted
	 */
	public void delete(Customer customer) {
		customerRepository.delete(customer);
	}

	/**
	 * Persists or updates customer in the system. Also assigns an identifier
	 * for new Customer instances.
	 *
	 * @param customer
	 */
	public void save(Customer customer) {
		if (customer == null) {
			LOGGER.log(Level.SEVERE,
					"Customer is null. Are you sure you have connected your form to the application as described in tutorial chapter 7?");
			return;
		}
		customerRepository.save(customer);
	}

	/**
	 * Sample data generation
	 */
	@PostConstruct
	public void populateTestData() {
		if(companyRepository.count()==0) {
			companyRepository.saveAll(
					Stream.of("Path-Way Electronics", "E-Tech Management", "Path-E-Tech Management")
							.map(Company::new)
							.collect(Collectors.toList()));
		}

		if (customerRepository.count()==0) {
			Random r = new Random(0);
			List<Company> companies = companyRepository.findAll();
			customerRepository.saveAll(Stream.of("Gabrielle Patel", "Brian Robinson", "Eduardo Haugen",
					"Koen Johansen", "Alejandro Macdonald", "Angel Karlsson", "Yahir Gustavsson", "Haiden Svensson",
					"Emily Stewart", "Corinne Davis", "Ryann Davis", "Yurem Jackson", "Kelly Gustavsson",
					"Eileen Walker", "Katelyn Martin", "Israel Carlsson", "Quinn Hansson", "Makena Smith",
					"Danielle Watson", "Leland Harris", "Gunner Karlsen", "Jamar Olsson", "Lara Martin",
					"Ann Andersson", "Remington Andersson", "Rene Carlsson", "Elvis Olsen", "Solomon Olsen",
					"Jaydan Jackson", "Bernard Nilsen")
					.map(name -> {
						String[] split = name.split(" ");
						Customer customer = new Customer();
						customer.setFirstName(split[0]);
						customer.setLastName(split[1]);
						customer.setCompany(companies.get(r.nextInt(companies.size())));
						customer.setStatus(CustomerStatus.values()[r.nextInt(CustomerStatus.values().length)]);
						String email = (customer.getFirstName()+"."+customer.getLastName()+"@"+customer.getCompany().getName().replaceAll("[\\s-]", "")+".com").toLowerCase();
						customer.setEmail(email);
						return customer;
					}).collect(Collectors.toList()));
		}
	}
}