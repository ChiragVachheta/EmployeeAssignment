package com.assignment.controller.test;
import com.assignment.Controller.EmployeeController;
import com.assignment.model.Employee;
import com.assignment.model.Response;
import com.assignment.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.util.Set;

import static org.mockito.Mockito.*;
/**
 * Unit Test case in Written Demo Purpose
 */
@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerTest {
    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    public void testCreateEmployee_Success() {
        // Create a valid employee object
        Employee employee = new Employee();
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setEmail("john.doe@example.com");
        employee.setPhone("1234567890");

        // Validate the employee object
        BindingResult bindingResult = new BeanPropertyBindingResult(employee, "employee");
        // In your test method:
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Employee>> violations = validator.validate(employee);
        assertTrue(violations.isEmpty(), "Validation should pass for a valid employee object");


        // Mock the response
        Employee createdEmployee = new Employee();
        createdEmployee.setId(12L);
        createdEmployee.setFirstName("John");
        createdEmployee.setLastName("Doe");
        createdEmployee.setEmail("john.doe@example.com");
        createdEmployee.setPhone("1234567890");

        Response expectedResponse = new Response();
        expectedResponse.setData(createdEmployee);

        // Mock service method behavior
        when(employeeService.createEmployee(eq(employee), any())).thenReturn(expectedResponse);

        // Perform the POST request
        ResponseEntity<Response> responseEntity = employeeController.createEmployee(employee, bindingResult);

        // Assertions
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody().getData() instanceof Employee);
        assertEquals("John", ((Employee) responseEntity.getBody().getData()).getFirstName());
        assertEquals("Doe", ((Employee) responseEntity.getBody().getData()).getLastName());
        assertEquals("john.doe@example.com", ((Employee) responseEntity.getBody().getData()).getEmail());
        assertEquals("1234567890", ((Employee) responseEntity.getBody().getData()).getPhone());
    }
}
