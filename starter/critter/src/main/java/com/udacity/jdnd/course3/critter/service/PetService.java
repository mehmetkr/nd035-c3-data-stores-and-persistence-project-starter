package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetRepository;
import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PetService {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public Pet addPet(Pet pet) {

        Pet savedPet = petRepository.save(pet);
        Customer customer = savedPet.getOwner();

        List<Pet> customerPets = customer.getPets();
        if(customerPets == null) {
            customerPets = new ArrayList<>();
        }

        customerPets.add(savedPet);
        customer.setPets(customerPets);
        userRepository.save(customer);

        return savedPet;

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

    public List<Pet> findPetsById(List<Long> petIds) { return petRepository.findByPetIdIn(petIds);}

}
