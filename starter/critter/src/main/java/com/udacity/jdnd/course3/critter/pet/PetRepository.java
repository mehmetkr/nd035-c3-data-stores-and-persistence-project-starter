package com.udacity.jdnd.course3.critter.pet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long>  {

    //    @Query("select p from Pet where p.id=:petId")
    Pet findPetByPetId(Long petId);

    List<Pet> findByPetIdIn(List<Long> petIds);

}

