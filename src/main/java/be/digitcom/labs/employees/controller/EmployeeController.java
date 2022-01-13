package be.digitcom.labs.employees.controller;

import be.digitcom.labs.employees.model.Employee;
import be.digitcom.labs.employees.repository.EmployeeRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
@CrossOrigin(origins = {"http://localhost:3000"})
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

    // Create employee
    @PostMapping(value = {"", "/"})
    public Employee create(@RequestBody Employee employee) {
        return repository.save(employee);
    }
}
