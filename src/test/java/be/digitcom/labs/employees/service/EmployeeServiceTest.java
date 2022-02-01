package be.digitcom.labs.employees.service;

import be.digitcom.labs.employees.exception.ResourceNotFoundException;
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
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    private EmployeeService service;

    // Can I use Argument Captor in Assertion? Because I heard that we can use it only in verification
    @Captor
    private ArgumentCaptor<Employee> employeeArgumentCaptor;

    @Mock
    private EmployeeRepository repository;

    // Use Spies to test tricky situations, particularly code you cannot verify.
    // Spies should only be used occasionally and are useful when you find yourself in a situation with code that's impossible, or too costly, to change.

    @BeforeEach
    void setUp() {
        service = new EmployeeService(repository);
        // MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void findAll() {
        List<Employee> employeeList = new ArrayList<>();

        Employee dom = new Employee(UUID.fromString("f118e7f3-5e45-4c31-bddd-920c2e7a83b6"),"Dominique", "claeys", "dominique@yahoo.fr");
        Employee rufin = new Employee(UUID.fromString("5866d6f7-b510-4b3e-a107-0507f2ca52ab"), "Rufin", "Hounkpe", "rufin.hounkpe@digit-com.be");
        Employee herve = new Employee(UUID.fromString("ed92d608-c7a5-4cda-8900-bcab73b2ca4e"), "Hervé", "Ekem", "herve.ekem@digit-com.be");
        Employee eliot = new Employee(UUID.fromString("57a9c8a8-9747-430b-adcd-945971432dc4"), "Eliot", "Fagnon", "eliot.fagnon@digit-com.be");

        employeeList.add(dom);
        employeeList.add(rufin);
        employeeList.add(herve);
        employeeList.add(eliot);

        when(repository.findAll()).thenReturn(employeeList);
        assertEquals(4, service.findAll().size());

        // How to use Argument Captor to verify that at the 1st position in this list with have an object
        // which has as first name "Dominique"
        // Is it interesting to verify such a behavior?
        // Throw java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0
        verify(repository, times(1)).findAll();
        final List<Employee> employees = employeeArgumentCaptor.getAllValues();
        assertEquals("Dominique", employees.get(0).getFirstName());
    }

    @Test
    public void shouldSaveNewEmployeeInDatabase() {
        Employee dom = new Employee("Dominique", "Claeys", "dominique@yahoo.fr");
        // Given
        when(repository.save(dom)).thenReturn(dom);
        assertEquals(dom, service.save(dom));

        // Then
        verify(repository, times(1)).save(any(Employee.class));
        verify(repository).save(employeeArgumentCaptor.capture());

        // When I save a new record in DB I don't care about the ID. It will be generate by the DB engine.
        // But here, ID is null.
        // How can I verify that DB attributes an autogenerate ID to this newly created entity?
        assertNotNull(employeeArgumentCaptor.getValue().getId());
        assertEquals("Claeys", employeeArgumentCaptor.getValue().getLastName());

    }

    @Test
    void save() {
        Employee dom = new Employee(UUID.fromString("f118e7f3-5e45-4c31-bddd-920c2e7a83b6"),"Dominique", "claeys", "dominique@yahoo.fr");
        when(repository.save(dom)).thenReturn(dom);
        assertEquals(dom, service.save(dom));

        verify(repository, times(1)).save(ArgumentMatchers.any(Employee.class));
        verify(repository, times(1)).save(employeeArgumentCaptor.capture());

        // Can I use Argument Captor in Assertion? Because I heard that we can use it only in verification
        assertEquals("Dominique", employeeArgumentCaptor.getValue().getFirstName());

        // Use case: check it in documentation
        verifyNoMoreInteractions(repository);
    }

    @Test
    void findById() {
        Employee dom = new Employee(UUID.fromString("f118e7f3-5e45-4c31-bddd-920c2e7a83b6"),"Dominique", "claeys", "dominique@yahoo.fr");

        when(repository.findById(UUID.fromString("f118e7f3-5e45-4c31-bddd-920c2e7a83b6"))).thenReturn(Optional.of(dom));

        assertEquals(dom.getId(), service.findById(UUID.fromString("f118e7f3-5e45-4c31-bddd-920c2e7a83b6")).getId());

        // Throw NullPointerException
        // verify(repository, times(1)).findById(employeeArgumentCaptor.capture().getId());

    }

    @Test
    public void shouldThowResourceNotFoundException() {
        UUID uuid = UUID.fromString("01b5bb59-1717-4147-ab91-414ae5b36ffc");
        when(repository.findById(uuid)).thenThrow(new ResourceNotFoundException("Employee does not exist with ID: " + uuid));
        assertThrows(ResourceNotFoundException.class, () -> service.findById(uuid));
    }

    @Test
    public void shouldThrowRepositoryThrows() {
        /*
        when(repository.save(new Employee("Dominique", "claeys", "dominique@yahoo.fr")))
                .thenThrow(new RuntimeException());
        assertThrows(RuntimeException.class, () -> service.save(new Employee("Dominique", "claeys", "dominique@yahoo.fr")));

         */
    }

    @Test
    void delete() {
        Employee dom = new Employee(UUID.fromString("f118e7f3-5e45-4c31-bddd-920c2e7a83b6"),"Dominique", "claeys", "dominique@yahoo.fr");
        service.delete(dom);
        verify(repository, times(1)).delete(dom);
    }
}
