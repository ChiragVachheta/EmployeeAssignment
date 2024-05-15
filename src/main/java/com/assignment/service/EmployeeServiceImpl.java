package com.assignment.service;

import com.assignment.model.Address;
import com.assignment.model.Response;
import com.assignment.repo.AddressRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.assignment.model.Employee;
import com.assignment.repo.EmployeeRepository;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Validated
public class EmployeeServiceImpl implements EmployeeService {
    private Logger logger = LoggerFactory.getLogger(EmployeeService.class);
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Override
    public Response createEmployee(Employee employee, BindingResult bindingResult) {
        Response data = new Response();
        try{
            if (bindingResult.hasErrors()) {
                List<String> errors = bindingResult.getFieldErrors().stream()
                        .map(error -> error.getDefaultMessage())
                        .collect(Collectors.toList());
                data.setError(errors.get(0));
            }else {
                Employee save = employeeRepository.save(employee);
                data.setData(save);
            }
            return data;
        }catch (Exception ex){
            logger.error(ex.getStackTrace().toString());
            data.setError(ex.getMessage());
            return data;
        }

    }

    @Override
    public Response updateEmployee(Long id, Employee employee,BindingResult bindingResult) {
    Response response=new Response();
        Optional<Employee> existingEmployeeOptional = employeeRepository.findById(id);
        if (existingEmployeeOptional.isPresent()) {
            Employee existingEmployee = existingEmployeeOptional.get();
            existingEmployee.setFirstName(employee.getFirstName());
            existingEmployee.setLastName(employee.getLastName());
            existingEmployee.setEmail(employee.getEmail());
            existingEmployee.setPhone(employee.getPhone());
            Employee updated = employeeRepository.save(existingEmployee);
            response.setData(updated);
            return response;
        } else {
            response.setError("Employee Records not Found");
           return response;
        }
    }
    @Override
    public boolean deleteEmployee(Long id) {
        Optional<Employee> employeeOptional = employeeRepository.findById(id);
        if (employeeOptional.isPresent()) {
            Employee employee = employeeOptional.get();
            addressRepository.deleteAll(employee.getAddresses());
            employeeRepository.delete(employee);
            return true;
        } else {
            throw new RuntimeException("Employee not found with id: " + id);
        }
    }

    @Override
    public Response getEmployeeById(Long id) {
        Response response = new Response();
        response.setData(employeeRepository.findById(id).orElse(null));
        return response;
    }

    @Override
    public Response getAllEmployees(){
        Response response = new Response();
        response.setData(employeeRepository.findAll());
        return  response;
    }


    @Override
    public Response createEmployeeAddress(Long employeeId, Address address) {
        Response response = new Response();
        Employee employee = employeeRepository.findById(employeeId).orElse(null);
        if (employee != null) {
            address.setEmployee(employee);
            response.setData(addressRepository.save(address));
            return response;
        }else{
            response.setData(null);
            return null;
        }
    }

    @Override
    public Response updateEmployeeAddress(Long employeeId, Long addressId, Address updatedAddress) {
        Response response=new Response();
        Address existingAddress = addressRepository.findById(addressId).orElse(null);
        if (existingAddress != null && existingAddress.getEmployee().getId().equals(employeeId)) {
            existingAddress.setStreet(updatedAddress.getStreet());
            existingAddress.setCity(updatedAddress.getCity());
            existingAddress.setState(updatedAddress.getState());
            existingAddress.setPostalCode(updatedAddress.getPostalCode());
            response.setData(addressRepository.save(existingAddress));
            return response;
        }else{
            response.setData(null);
            return response;
        }
    }

    @Override
    public Response getAllAddressesForEmployee(Long employeeId) {
        Response response=new Response();
        response.setData(addressRepository.findByEmployeeId(employeeId));
        return response;
    }

}
