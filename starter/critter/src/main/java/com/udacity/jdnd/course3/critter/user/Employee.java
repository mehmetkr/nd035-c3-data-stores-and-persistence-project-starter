package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.schedule.Schedule;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Employee extends User {


    @Column
    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<EmployeeSkill> skills;

    @Column
    @ElementCollection(targetClass =  DayOfWeek.class)
    @Enumerated(EnumType.STRING)
    private Set<DayOfWeek> daysAvailable;

    public Employee() {
        super();

    }

    public Employee(String name, Set<EmployeeSkill> skills, Set<DayOfWeek> daysAvailable, List<Schedule> schedules) {
        super(name);
        this.skills = skills;
        this.daysAvailable = daysAvailable;
    }

    public Set<EmployeeSkill> getSkills() {
        return skills;
    }

    public void setSkills(Set<EmployeeSkill> skills) {
        this.skills = skills;
    }

    public Set<DayOfWeek> getDaysAvailable() {
        return daysAvailable;
    }

    public void setDaysAvailable(Set<DayOfWeek> daysAvailable) {

        this.daysAvailable = daysAvailable;

    }

}
