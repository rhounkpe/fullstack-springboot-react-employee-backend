package be.digitcom.labs.employees.controller;

import be.digitcom.labs.employees.model.Employee;
import be.digitcom.labs.employees.service.EmployeeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.UUID;

// https://www.youtube.com/watch?v=vcRFkp8jHJ8&list=PLSVW22jAG8pByICwbp3c99FoXWIaDJ1gv&index=3&ab_channel=ProgrammingTechie

@WebMvcTest(controllers = EmployeeController.class)
class EmployeeControllerTest {
    @MockBean
    private EmployeeService service;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }


    @Test
    @DisplayName("Should list all Employees when making GET request to endpoint - /api/v1/employees")
    public void getAll() throws Exception {
        Employee dom = service.save(new Employee(UUID.fromString("f118e7f3-5e45-4c31-bddd-920c2e7a83b6"),"Dominique", "claeys", "dominique@yahoo.fr"));
        Employee rufin = service.save(new Employee(UUID.fromString("5866d6f7-b510-4b3e-a107-0507f2ca52ab"), "Rufin", "Hounkpe", "rufin.hounkpe@digit-com.be"));
        Employee herve = service.save(new Employee(UUID.fromString("ed92d608-c7a5-4cda-8900-bcab73b2ca4e"), "Hervé", "Ekem", "herve.ekem@digit-com.be"));
        Employee eliot = service.save(new Employee(UUID.fromString("57a9c8a8-9747-430b-adcd-945971432dc4"), "Eliot", "Fagnon", "eliot.fagnon@digit-com.be"));

        Mockito.when(service.findAll()).thenReturn(Arrays.asList(dom, rufin, herve, eliot));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/employees"))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
//                .andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("$[0].id", Matchers.eq("f118e7f3-5e45-4c31-bddd-920c2e7a83b6")));
                ;
                // .andExpect(MockMvcResultMatchers.jsonPath("$.size()", Matchers.(4)))

    }

    @Test
    void create() {
    }

    @Test
    void getById() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}
