package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.user.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User addUser(User user) {

        return userRepository.save(user);

    }


    public List<Customer> findAllCustomers() {

        return userRepository.findAllCustomers();
    }

    public List<Employee> findAllEmployees() {
        return userRepository.findAllEmployees();
    }

    public Customer findCustomerById(long userId) {

        return userRepository.findCustomerById(userId);

    }

    public Employee findEmployeeById(long employeeId) {

        return userRepository.findEmployeeById(employeeId);

    }

    public List<Employee> findEmployeesForService(Set<EmployeeSkill> skills, LocalDate date) {


        return userRepository.findAllEmployees().stream().
                filter(e -> e.getSkills().containsAll(skills)).
                filter(e -> e.getDaysAvailable().contains(date.getDayOfWeek())).
                collect(Collectors.toList());


    }

    public Employee setEmployeeAvailability(long employeeId, Set<DayOfWeek> daysAvailable) {

        Employee employee = findEmployeeById(employeeId);

        if(employee==null) {
            return null;
        }

        employee.setDaysAvailable(daysAvailable);

        userRepository.save(employee);

        return employee;


    }

    public List<Employee> findEmployeesById(List<Long> employeeIds) { return userRepository.findEmployeeByIdIn(employeeIds);}

}

