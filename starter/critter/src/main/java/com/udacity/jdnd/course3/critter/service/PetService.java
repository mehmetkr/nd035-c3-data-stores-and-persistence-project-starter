package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetRepository;
import com.udacity.jdnd.course3.critter.user.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class PetService {

    @Autowired
    private PetRepository petRepository;

    public Pet addPet(Pet pet) {

        return petRepository.save(pet);

    }

    public Pet findPetById(Long petId) {

       return petRepository.findPetByPetId(petId);


    }


    public List<Pet> findAllPets() {

        return petRepository.findAll();

    }

    public Customer findOwnerByPet(long petId) {

        return petRepository.findPetByPetId(petId).getOwner();

    }
}
