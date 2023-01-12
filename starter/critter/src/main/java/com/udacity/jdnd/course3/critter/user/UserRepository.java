package com.udacity.jdnd.course3.critter.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select c from Customer c")
    List<Customer> findAllCustomers();

    @Query("select e from Employee e")
    List<Employee> findAllEmployees();

    Customer findCustomerById(long userId);

    Employee findEmployeeById(long employeeId);


//    @Query("SELECT DISTINCT e FROM Entry e WHERE :user MEMBER OF e.users AND :groups in e.groups")

    @Query("select distinct e from Employee e where :skills in e.skills")
    List<Employee> findEmployeesByDay(List<EmployeeSkill> skills/*, DayOfWeek date*/);





}
