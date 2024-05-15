package com.assignment.Controller;

import com.assignment.model.Address;
import com.assignment.model.Employee;
import com.assignment.model.EndUser;
import com.assignment.model.Response;
import com.assignment.service.EmployeeService;
import com.assignment.service.EndUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    private Logger logger= LoggerFactory.getLogger(EmployeeController.class);

    @PostMapping("/user")
    public ResponseEntity<Response> createUser(@RequestBody EndUser user) {
        Response response =new Response();
        try {
            response = endUserService.saveUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/employee")
    public ResponseEntity<Response> createEmployee(@Valid @RequestBody Employee employee, BindingResult bindingResult) {
        Response response=new Response();
        try {
            response = employeeService.createEmployee(employee, bindingResult);
            if (response.getError() != null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception ex) {
            response.setError(ex.getMessage().toString());
            logger.error(ex.getMessage().toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/employee/{id}")
    public ResponseEntity<Response> updateEmployee(@PathVariable Long id, @RequestBody Employee employee,BindingResult bindingResult) {

        Response updatedEmployee = employeeService.updateEmployee(id, employee,bindingResult);
        if (updatedEmployee.getData() != null) {
            return ResponseEntity.ok(updatedEmployee);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/employee/{id}")
    public ResponseEntity<Response> deleteEmployee(@PathVariable Long id) {
        Response response = new Response();
        boolean flag = employeeService.deleteEmployee(id);
        if (flag){
            response.setData("Employee Records deleted SuccessFully for Employe ID "+id);
            return ResponseEntity.ok(response);
        }else {
            response.setData("Employee Records Notfound for Employe ID "+id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/employee/{id}")
    public ResponseEntity<Response> getEmployeeById(@PathVariable Long id) {
        Response response =new Response();
        response = employeeService.getEmployeeById(id);
        if (response.getData() != null) {
            return ResponseEntity.ok(response);
        } else {
            response.setData("Records Not Found for EmployeID: "+id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/employee-list")
    public ResponseEntity<Response> getAllEmployees() {
        Response response = new Response();
        response = employeeService.getAllEmployees();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/addresses/{employeeId}")
    public ResponseEntity<Response> createEmployeeAddress(@PathVariable Long employeeId, @RequestBody Address address) {
        Response createdAddress = employeeService.createEmployeeAddress(employeeId, address);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAddress);
    }

    @PutMapping("/addresses/{employeeId}/{addressId}")
    public ResponseEntity<Response> updateEmployeeAddress(@PathVariable Long employeeId, @PathVariable Long addressId, @RequestBody Address address) {
        Response updatedAddress = employeeService.updateEmployeeAddress(employeeId, addressId, address);
        if (updatedAddress.getData() != null) {
            return ResponseEntity.ok(updatedAddress);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/addresses/{employeeId}")
    public ResponseEntity<Response> getAllAddressesForEmployee(@PathVariable Long employeeId) {
        Response addresses = employeeService.getAllAddressesForEmployee(employeeId);
        return ResponseEntity.ok(addresses);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        logger.error(ex.getStackTrace().toString());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An Error occur "+ex.getMessage());
    }
}