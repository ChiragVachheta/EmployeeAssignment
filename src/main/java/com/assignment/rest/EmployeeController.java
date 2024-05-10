package com.assignment.rest;

import com.assignment.model.Address;
import com.assignment.model.Employee;
import com.assignment.model.EndUser;
import com.assignment.service.EmployeeService;
import com.assignment.service.EndUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bizsense")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    EndUserService endUserService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/user")
    public ResponseEntity<EndUser> createUser(@RequestBody EndUser user) {
        EndUser usercreated = endUserService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(usercreated);
    }

    @PostMapping("/employee")
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        Employee createdEmployee = employeeService.createEmployee(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEmployee);
    }

    @PutMapping("/employee/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
        Employee updatedEmployee = employeeService.updateEmployee(id, employee);
        if (updatedEmployee != null) {
            return ResponseEntity.ok(updatedEmployee);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/employee/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/employee/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Employee employee = employeeService.getEmployeeById(id);
        if (employee != null) {
            return ResponseEntity.ok(employee);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/employee-list")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    @PostMapping("/addresses/{employeeId}")
    public ResponseEntity<Address> createEmployeeAddress(@PathVariable Long employeeId, @RequestBody Address address) {
        Address createdAddress = employeeService.createEmployeeAddress(employeeId, address);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAddress);
    }

    @PutMapping("/addresses/{employeeId}/{addressId}")
    public ResponseEntity<Address> updateEmployeeAddress(@PathVariable Long employeeId, @PathVariable Long addressId, @RequestBody Address address) {
        Address updatedAddress = employeeService.updateEmployeeAddress(employeeId, addressId, address);
        if (updatedAddress != null) {
            return ResponseEntity.ok(updatedAddress);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/addresses/{employeeId}")
    public ResponseEntity<List<Address>> getAllAddressesForEmployee(@PathVariable Long employeeId) {
        List<Address> addresses = employeeService.getAllAddressesForEmployee(employeeId);
        return ResponseEntity.ok(addresses);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + ex.getMessage());
    }
}