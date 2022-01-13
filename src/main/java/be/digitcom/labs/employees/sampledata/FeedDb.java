package be.digitcom.labs.employees.sampledata;

import be.digitcom.labs.employees.model.Employee;
import be.digitcom.labs.employees.repository.EmployeeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class FeedDb implements CommandLineRunner {
    private final EmployeeRepository employeeRepository;

    public FeedDb(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        employeeRepository.save(new Employee("Rufin", "Hounkpe", "rufin.hounkpe@digit-com.be"));
        employeeRepository.save(new Employee("Hervé", "Ekem", "herve.ekem@digit-com.be"));
        employeeRepository.save(new Employee("Eliot", "Fagnon", "eliot.fagnon@digit-com.be"));
    }
}
