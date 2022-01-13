package be.digitcom.labs.employees.controller;

import be.digitcom.labs.employees.model.Employee;
import be.digitcom.labs.employees.repository.EmployeeRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {
    private final EmployeeRepository repository;

    public EmployeeController(EmployeeRepository repository) {
        this.repository = repository;
    }

    // Get all employees
    @GetMapping({"", "/"})
    public List<Employee> getAll() {
        return repository.findAll();
    }
}
