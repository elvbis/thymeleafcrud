package com.santg.springboot.thymeleafdemo.service;

import java.util.List;

import com.santg.springboot.thymeleafdemo.entity.Employee;
import org.springframework.data.domain.Page;

public interface EmployeeService {

	public List<Employee> findAll();
	
	public Employee findById(int theId);
	
	public void save(Employee theEmployee);
	
	public void deleteById(int theId);


	Page<Employee> findPaginated(int pageNo, int pageSize);
	
}
