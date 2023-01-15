package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetRepository;
import com.udacity.jdnd.course3.critter.schedule.Schedule;
import com.udacity.jdnd.course3.critter.schedule.ScheduleDTO;
import com.udacity.jdnd.course3.critter.schedule.ScheduleRepository;
import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.Employee;
import com.udacity.jdnd.course3.critter.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ScheduleService {

    @Autowired
    ScheduleRepository scheduleRepository;

    @Autowired
    ScheduleRepository customerRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PetRepository petRepository;

    public Schedule addSchedule(Schedule schedule) {

        return scheduleRepository.save(schedule);
    }

    public List<Schedule> getAllSchedules() {

        return scheduleRepository.findAll();

    }


    public List<Schedule> getScheduleForPet(Pet pet) {

        return scheduleRepository.findScheduleByPets(pet);

    }

    public List<Schedule> getScheduleForEmployee(Employee employee) {

        return scheduleRepository.findScheduleByEmployees(employee);


    }

    public List<Schedule> getScheduleForCustomer(long customerId) {

        List<Schedule> schedules = new ArrayList();

        userRepository.findCustomerById(customerId).getPets().
                forEach( (p) -> schedules.addAll(getScheduleForPet(p)));

        return schedules;


    }
}
