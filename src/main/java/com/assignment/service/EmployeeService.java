package com.assignment.service;

import com.assignment.model.Address;
import com.assignment.model.Employee;
import com.assignment.model.Response;
import org.springframework.validation.BindingResult;

public interface EmployeeService {
    Response createEmployee(Employee employee, BindingResult bindingResult);

    Response updateEmployee(Long id, Employee employee, BindingResult bindingResult);

    boolean deleteEmployee(Long id);

    Response getEmployeeById(Long id);

    Response getAllEmployees();

    Response createEmployeeAddress(Long employeeId, Address address);

    Response updateEmployeeAddress(Long employeeId, Long addressId, Address address);

    Response getAllAddressesForEmployee(Long employeeId);
}
