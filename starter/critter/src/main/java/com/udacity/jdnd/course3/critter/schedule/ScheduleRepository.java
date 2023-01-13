package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.user.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.nio.channels.FileChannel;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

//    @Query("select s from Schedule s where s.pet_id = :petId")
    List<Schedule> findScheduleByPets(Pet pet);

//    @Query("select s from Schedule s where s.pet_id = :petId")
    List<Schedule> findScheduleByEmployees(Employee employee);
}
