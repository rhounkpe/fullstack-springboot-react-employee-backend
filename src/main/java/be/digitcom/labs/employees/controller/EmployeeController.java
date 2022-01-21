package be.digitcom.labs.employees.controller;

import be.digitcom.labs.employees.exception.ResourceNotFoundException;
import be.digitcom.labs.employees.model.Employee;
import be.digitcom.labs.employees.repository.EmployeeRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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

    // Get employee by id
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getById(@PathVariable UUID id) {
        Employee employee = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee does not exist with ID: " + id));

        return ResponseEntity.ok(employee);
    }

    // Update employee
    @PutMapping("/{id}")
    public ResponseEntity<Employee> update(@PathVariable UUID id, @RequestBody Employee employeeDetails) {
        Employee employee = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee does not exist with ID: " + id));

        employee.setFirstName(employeeDetails.getFirstName());
        employee.setLastName(employeeDetails.getLastName());
        employee.setEmail(employeeDetails.getEmail());

        Employee savedEmployee = repository.save(employee);
        return ResponseEntity.ok(savedEmployee);
    }
}
