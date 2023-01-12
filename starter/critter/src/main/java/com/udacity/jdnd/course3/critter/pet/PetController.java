package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.service.UserService;
import com.udacity.jdnd.course3.critter.user.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired
    private PetService petService;

    @Autowired
    private UserService userService;


    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {

//        throw new UnsupportedOperationException();

        return convertPettoDTO(petService.addPet(convertDTOtoPet(petDTO)));

    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {

//        throw new UnsupportedOperationException();

        return convertPettoDTO(petService.findPetById(petId));

    }

    @GetMapping
    public List<PetDTO> getPets(){

//        throw new UnsupportedOperationException();

        return petService.findAllPets().stream().
                map(p -> {return convertPettoDTO(p);}).collect(Collectors.toList());


    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {

//        throw new UnsupportedOperationException();

        return userService.findCustomerById(ownerId).getPets().stream().
                map(p -> {return convertPettoDTO(p);}).collect(Collectors.toList());
    }


    private Pet convertDTOtoPet(PetDTO petDTO) {

        Pet pet = new Pet();
        BeanUtils.copyProperties(petDTO, pet);
        pet.setPetType(petDTO.getType());
        pet.setOwner(userService.findCustomerById(petDTO.getOwnerId()));
        return pet;
    }

    private PetDTO convertPettoDTO(Pet pet) {

        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(pet, petDTO);
        petDTO.setType(pet.getPetType());
        petDTO.setOwnerId(pet.getOwner().getId());
        return petDTO;
    }


}
