package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetDTO;
import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.service.ScheduleService;
import com.udacity.jdnd.course3.critter.service.UserService;
import com.udacity.jdnd.course3.critter.user.Employee;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private UserService userService;

    @Autowired
    private PetService petService;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {

//        throw new UnsupportedOperationException();

        return convertScheduletoDTO(scheduleService.addSchedule(convertDTOtoSchedule(scheduleDTO)));


    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {

        // throw new UnsupportedOperationException();

        return scheduleService.getAllSchedules().stream().
                map(s -> {return convertScheduletoDTO(s);}).collect(Collectors.toList());

    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
//         throw new UnsupportedOperationException();

        List<ScheduleDTO> schedules = scheduleService.getScheduleForPet(petService.findPetById(petId)).stream().
                map(s -> {return convertScheduletoDTO(s);}).collect(Collectors.toList());

        if(schedules.isEmpty()) {

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No schedules found.");

        }

        return schedules;

    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
//        throw new UnsupportedOperationException();

        List<ScheduleDTO> schedules = scheduleService.getScheduleForEmployee(userService.findEmployeeById(employeeId)).stream().
                map(s -> {return convertScheduletoDTO(s);}).collect(Collectors.toList());

        if(schedules.isEmpty()) {

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No schedules found.");

        }

        return schedules;

    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
//        throw new UnsupportedOperationException();

        List<ScheduleDTO> schedules = scheduleService.getScheduleForCustomer(customerId).stream().
                map(s -> {return convertScheduletoDTO(s);}).collect(Collectors.toList());

        if(schedules.isEmpty()) {

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No schedules found.");

        }

        return schedules;


    }

    private ScheduleDTO convertScheduletoDTO(Schedule schedule) {

        ScheduleDTO scheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(schedule, scheduleDTO);
        List<Employee> es = schedule.getEmployees();
        List<Pet> ps = schedule.getPets();
        List<Long> eIds = es.stream().map(e -> e.getId()).collect(Collectors.toList());
        List<Long> pIds = ps.stream().map(p -> p.getPetId()).collect(Collectors.toList());
        scheduleDTO.setEmployeeIds(eIds);
        scheduleDTO.setPetIds(pIds);
        return scheduleDTO;
    }

    private Schedule convertDTOtoSchedule(ScheduleDTO scheduleDTO) {

        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleDTO, schedule);

        List<Pet> pets = petService.findPetsById(scheduleDTO.getPetIds());
        List<Employee> employees = userService.findEmployeesById(scheduleDTO.getEmployeeIds());
        schedule.setPets(pets);
        schedule.setEmployees(employees);
        return schedule;
    }


}
