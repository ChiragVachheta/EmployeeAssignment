package com.assignment.service;

import com.assignment.model.Address;
import com.assignment.repo.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.assignment.model.Employee;
import com.assignment.repo.EmployeeRepository;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Override
    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public Employee updateEmployee(Long id, Employee employee) {
        Optional<Employee> existingEmployeeOptional = employeeRepository.findById(id);
        if (existingEmployeeOptional.isPresent()) {
            Employee existingEmployee = existingEmployeeOptional.get();
            existingEmployee.setFirstName(employee.getFirstName());
            existingEmployee.setLastName(employee.getLastName());
            existingEmployee.setEmail(employee.getEmail());
            existingEmployee.setPhone(employee.getPhone());
            return employeeRepository.save(existingEmployee);
        } else {
            // Handle not found error or throw exception
            return null;
        }
    }

    @Override
    public void deleteEmployee(Long id) {
        Optional<Employee> employeeOptional = employeeRepository.findById(id);
        if (employeeOptional.isPresent()) {
            Employee employee = employeeOptional.get();
            addressRepository.deleteAll(employee.getAddresses());
            employeeRepository.delete(employee);
        } else {
            throw new RuntimeException("Employee not found with id: " + id);
        }
    }

    @Override
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id).orElse(null);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }


    @Override
    public Address createEmployeeAddress(Long employeeId, Address address) {
        Employee employee = employeeRepository.findById(employeeId).orElse(null);
        if (employee != null) {
            address.setEmployee(employee);
            return addressRepository.save(address);
        }
        return null;
    }

    @Override
    public Address updateEmployeeAddress(Long employeeId, Long addressId, Address updatedAddress) {
        Address existingAddress = addressRepository.findById(addressId).orElse(null);
        if (existingAddress != null && existingAddress.getEmployee().getId().equals(employeeId)) {
            existingAddress.setStreet(updatedAddress.getStreet());
            existingAddress.setCity(updatedAddress.getCity());
            existingAddress.setState(updatedAddress.getState());
            existingAddress.setPostalCode(updatedAddress.getPostalCode());
            return addressRepository.save(existingAddress);
        }
        return null;
    }

    @Override
    public List<Address> getAllAddressesForEmployee(Long employeeId) {
        return addressRepository.findByEmployeeId(employeeId);
    }

}
