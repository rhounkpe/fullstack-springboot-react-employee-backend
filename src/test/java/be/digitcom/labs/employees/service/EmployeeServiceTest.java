package be.digitcom.labs.employees.service;

import be.digitcom.labs.employees.model.Employee;
import be.digitcom.labs.employees.repository.EmployeeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

// @ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    private EmployeeService service;
    private EmployeeRepository repository = mock(EmployeeRepository.class);

    @Captor
    private ArgumentCaptor<Employee> employeeArgumentCaptor;
    /*
    @Mock
    private EmployeeRepository repository;
     */

    @BeforeEach
    void setUp() {
        service = new EmployeeService(repository);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void findAll() {
        List<Employee> employeeList = new ArrayList<>();

        Employee dom = service.save(new Employee(UUID.fromString("f118e7f3-5e45-4c31-bddd-920c2e7a83b6"),"Dominique", "claeys", "dominique@yahoo.fr"));
        Employee rufin = service.save(new Employee(UUID.fromString("5866d6f7-b510-4b3e-a107-0507f2ca52ab"), "Rufin", "Hounkpe", "rufin.hounkpe@digit-com.be"));
        Employee herve = service.save(new Employee(UUID.fromString("ed92d608-c7a5-4cda-8900-bcab73b2ca4e"), "Hervé", "Ekem", "herve.ekem@digit-com.be"));
        Employee eliot = service.save(new Employee(UUID.fromString("57a9c8a8-9747-430b-adcd-945971432dc4"), "Eliot", "Fagnon", "eliot.fagnon@digit-com.be"));

        employeeList.add(dom);
        employeeList.add(rufin);
        employeeList.add(herve);
        employeeList.add(eliot);

        when(service.findAll()).thenReturn(employeeList);

       // when(service.findAll().size()).thenReturn(4);
    }

    @Test
    void save() {
        when(repository.save(new Employee("Dominique", "claeys", "dominique@yahoo.fr")))
                .thenReturn(new Employee("Dominique", "claeys", "dominique@yahoo.fr"));
        Employee dom = new Employee(UUID.fromString("fd8abf27-7b33-44d6-8f6c-bf6956ba0bb2"), "Dominique", "claeys", "dominique@yahoo.fr");

        service.save(dom);
        // verify(repository, times(1)).save(ArgumentMatchers.any(Employee.class));
        verify(repository, times(1)).save(employeeArgumentCaptor.capture());
        assertEquals("Dominique", employeeArgumentCaptor.getValue().getFirstName());
    }

    @Test
    void findById() {
        Employee dom = new Employee(UUID.fromString("f118e7f3-5e45-4c31-bddd-920c2e7a83b6"),"Dominique", "claeys", "dominique@yahoo.fr");
        Employee savedDom = service.save(dom);

        // when(service.findById(UUID.fromString("f118e7f3-5e45-4c31-bddd-920c2e7a83b6"))).thenReturn(savedDom);

        assertEquals(dom.getId(), service.findById(UUID.fromString("f118e7f3-5e45-4c31-bddd-920c2e7a83b6")));

    }

    @Test
    void delete() {
    }
}
