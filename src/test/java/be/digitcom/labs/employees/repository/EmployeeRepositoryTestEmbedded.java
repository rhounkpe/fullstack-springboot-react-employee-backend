package be.digitcom.labs.employees.repository;

import be.digitcom.labs.employees.model.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

// https://www.youtube.com/watch?v=9UmwBfZ98U8&list=PLSVW22jAG8pByICwbp3c99FoXWIaDJ1gv&index=2&ab_channel=ProgrammingTechie
@DataJpaTest
@ActiveProfiles("test")
class EmployeeRepositoryTestEmbedded {
    @Autowired
    private EmployeeRepository repository;

    @Test
    public void shouldSaveEmployee() {
        Employee dom = new Employee(UUID.fromString("fd8abf27-7b33-44d6-8f6c-bf6956ba0bb2"), "Dominique", "claeys", "dominique@yahoo.fr");
        Employee savedDom = repository.save(dom);

        assertEquals(dom.getFirstName(), savedDom.getFirstName());
    }

    @Test
    @Sql("classpath:test-data.sql")
    public void shouldSaveEmployeesThroughSqlFileq() {
        Optional<Employee> test = repository.findById(UUID.fromString("19aed03a-4e40-4458-bc29-f716fc192d43"));
        assertNotNull(test);
        assertEquals("Constant", test.get().getFirstName());
    }
}
