package be.digitcom.labs.employees.controller;

import be.digitcom.labs.employees.model.Employee;
import be.digitcom.labs.employees.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/employees")
@CrossOrigin(origins = {"http://localhost:3000"})
public class EmployeeController {
    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    // Get all employees
    @GetMapping({"", "/"})
    public List<Employee> getAll() {
        return service.findAll();
    }

    // Create employee
    @PostMapping(value = {"", "/"})
    public Employee create(@RequestBody Employee employee) {
        return service.save(employee);
    }

    // Get employee by id
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getById(@PathVariable UUID id) {
        Employee employee = service.findById(id);

        return ResponseEntity.ok(employee);
    }

    // Update employee
    @PutMapping("/{id}")
    public ResponseEntity<Employee> update(@PathVariable UUID id, @RequestBody Employee employeeDetails) {
        // UUID uuid = UUID.fromString(id);
        Employee employee = service.findById(id);

        employee.setFirstName(employeeDetails.getFirstName());
        employee.setLastName(employeeDetails.getLastName());
        employee.setEmail(employeeDetails.getEmail());

        Employee savedEmployee = service.save(employee);
        return ResponseEntity.ok(savedEmployee);
    }

    // Delete employee REST API
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable UUID id) {
        Employee employee = service.findById(id);

        service.delete(employee);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
