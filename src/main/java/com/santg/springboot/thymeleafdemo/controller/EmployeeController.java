package com.santg.springboot.thymeleafdemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.santg.springboot.thymeleafdemo.entity.Employee;
import com.santg.springboot.thymeleafdemo.service.EmployeeService;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

	private EmployeeService employeeService;
	
	@Autowired
	public EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	
	// add mapping for "/list"
	@GetMapping("/list")
	public String employeeList(Model model) {
		return findPaginated(1,model);
	}
	
	// add mapping for "/add" to add new employees
	@GetMapping("/add")
	public String addEmployee(Model model) {
		
		// create model attribute to bind form data
		Employee employee = new Employee();
		
		model.addAttribute("employee", employee);
		
		return "employees/employee-form";
	}
	
	@GetMapping("/update")
	public String updateEmployee(@RequestParam("employeeId") int id, Model model) {
		
		// get the employee from the service
		Employee employee = employeeService.findById(id);
		
		// set employee as a model attribute to pre-populate the form
		model.addAttribute("employee", employee);
		
		// send over to our form
		return "employees/employee-form";
	}
	
	@GetMapping("/delete")
	public String delete(@RequestParam("employeeId") int id) {
		
		// delete employee
		employeeService.deleteById(id);
		
		// return to list
		return "redirect:/employees/list";
	}
	
	@PostMapping("/save")
	public String saveEmployee(@ModelAttribute("employee") Employee employee) {
		
		// save the employee
		employeeService.save(employee);
		
		// use a redirect to prevent duplicated submissions
		return "redirect:/employees/list";
	}

	@GetMapping("/page/{pageNo}")
	public String findPaginated(@PathVariable(value = "pageNo") int pageNo, Model model) {
	int pageSize = 5;
		Page<Employee> employes = employeeService.findPaginated(pageNo,pageSize);
		List<Employee> list = employes.getContent();

		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", employes.getTotalPages());
		model.addAttribute("totalItems", employes.getTotalElements());
		model.addAttribute("employees", list);
		return "employees/list-employees";
	}

}
