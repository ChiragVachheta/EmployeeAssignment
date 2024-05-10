package com.assignment.service;

import java.util.List;

import com.assignment.model.Address;
import com.assignment.model.Employee;

public interface EmployeeService {
    Employee createEmployee(Employee employee);

    Employee updateEmployee(Long id, Employee employee);

    void deleteEmployee(Long id);

    Employee getEmployeeById(Long id);

    List<Employee> getAllEmployees();

    Address createEmployeeAddress(Long employeeId, Address address);

    Address updateEmployeeAddress(Long employeeId, Long addressId, Address address);

    List<Address> getAllAddressesForEmployee(Long employeeId);
}
