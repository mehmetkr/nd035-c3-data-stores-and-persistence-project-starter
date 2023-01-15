package com.udacity.jdnd.course3.critter.user;

import com.fasterxml.jackson.annotation.JsonView;
import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PetService petService;

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
//        throw new UnsupportedOperationException();

        return (CustomerDTO) convertCustomertoDTO((Customer) userService.addUser((User) convertDTOtoCustomer(customerDTO)));

    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){

 //       throw new UnsupportedOperationException();

        return userService.findAllCustomers().stream().
                map(c -> {return convertCustomertoDTO(c);}).collect(Collectors.toList());

    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId) {

//        throw new UnsupportedOperationException();

        CustomerDTO customerDTO;

        try{
            customerDTO = convertCustomertoDTO( petService.findOwnerByPet(petId));
        }
        catch(Exception e){

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Pet not found");

        }

        return customerDTO;

    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
//        throw new UnsupportedOperationException();

        return (EmployeeDTO) convertEmployeetoDTO((Employee) userService.addUser((User) convertDTOtoEmployee(employeeDTO)));

    }

    @GetMapping("/employee")
    public List<EmployeeDTO> getAllEmployees(){

        //       throw new UnsupportedOperationException();

        return userService.findAllEmployees().stream().
                map(e -> {return convertEmployeetoDTO(e);}).collect(Collectors.toList());

    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {

//        throw new UnsupportedOperationException();

        return convertEmployeetoDTO(userService.findEmployeeById(employeeId));

    }

    @PutMapping("/employee/{employeeId}")
    public ResponseEntity setAvailability(@RequestBody Set<DayOfWeek> daysAvailable,
                                                    @PathVariable long employeeId) {
//        throw new UnsupportedOperationException();

        if(userService.setEmployeeAvailability(employeeId, daysAvailable) == null) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Employee not found");

        }

        return ResponseEntity.status(HttpStatus.OK).body("Availability set for employee with id " + employeeId);

    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeRequestDTO) {
//        throw new UnsupportedOperationException();

        List<EmployeeDTO> employeeList = userService.findEmployeesForService(employeeRequestDTO.getSkills(), employeeRequestDTO.getDate()).stream().
                map(e -> {return convertEmployeetoDTO(e);}).collect(Collectors.toList());

        if(employeeList.isEmpty()) {

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No employees found.");

        }

        return employeeList;

    }

    private Customer convertDTOtoCustomer(CustomerDTO customerDTO) {

        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);
        return customer;
    }

    private CustomerDTO convertCustomertoDTO(Customer customer) {

       CustomerDTO customerDTO = new CustomerDTO();
       BeanUtils.copyProperties(customer, customerDTO);

       if(customer.getPets() != null)
           customerDTO.setPetIds(customer.getPets().stream().
               map(pet -> {return pet.getPetId();}).collect(Collectors.toList()));
       return customerDTO;
    }

    private Employee convertDTOtoEmployee(EmployeeDTO employeeDTO) {

        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);
        employee.setSkills(employeeDTO.getSkills());
        employee.setDaysAvailable(employeeDTO.getDaysAvailable());
        return employee;
    }

    private EmployeeDTO convertEmployeetoDTO(Employee employee) {

        EmployeeDTO employeeDTO = new EmployeeDTO();
        BeanUtils.copyProperties(employee, employeeDTO);
        employeeDTO.setSkills(employee.getSkills());
        employeeDTO.setDaysAvailable(employee.getDaysAvailable());
        return employeeDTO;
    }

}
