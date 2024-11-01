package com.proj.SegAProj.controllers;

import com.proj.SegAProj.dto.UserDTO;
import com.proj.SegAProj.models.User;
import com.proj.SegAProj.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @Operation(summary = "Obtener un usuario utilizando su ID.")
    @GetMapping(path = "/id/{id}")
    public UserDTO findById(@PathVariable Long id){
        return userService.findById(id);
    }

    @Operation(summary = "Obtener un usuario por su idUni.")
    @GetMapping(path = "/uni-id/{uniId}")
    public UserDTO findByUniId(@PathVariable String uniId){
        return userService.findByUniId(uniId);
    }

    @Operation(summary = "Obtener un usuario utilizando su ID, AGREGANDO todas las asignaturas de este usuario.")
    @GetMapping(path = "/id/with-subjects/{id}")
    public UserDTO findByIdWithSubjects (@PathVariable Long id){
        return userService.findByIdWithSubjects(id);
    }

    @Operation(summary = "Obtener un usuario utilizando su ID, AGREGANDO todas las reservas de este usuario.")
    @GetMapping(path = "/id/with-reservations/{id}")
    public UserDTO findByIdWithReservations(@PathVariable Long id){
        return userService.findByIdWithReservations(id);
    }

    @Operation(summary = "Obtener todos los usuarios, AGREGANDO todas las asignaturas de los usuarios.")
    @GetMapping(path = "/with-subjects")
    public List<UserDTO> findAllWithSubjects (){
        return userService.findAllWithSubjects();
    }

    @Operation(summary = "Obtener todos los usuarios, AGREGANDO todas las reservas de los usuarios.")
    @GetMapping(path = "/with-reservations")
    public List<UserDTO> findAllWithReservations (){
        return userService.findAllWithReservations();
    }

    @Operation(summary = "Obtener todos los usuarios.")
    @GetMapping
    public List<UserDTO> findAll(){
        return userService.findAll();
    }

    @Operation(summary = "Crea un nuevo usuario.")
    @PostMapping
    public User create(@RequestBody User user){
        return userService.create(user);
    }

    @Operation(summary = "Actualiza un usuario utilizando su ID.")
    @PutMapping(path = "/{id}")
    public User update(@RequestBody User user, @PathVariable Long id){
         return userService.update(id, user);
    }

    @Operation(summary = "Crea la relación entre un usuario y una asignatura en la tabla users_subjects, basado en la relación ManyToMany, utilizando la ID de cada uno.")
    @PutMapping(path = "/{userId}/subject/{subjectId}")
    public User enrollUserToSubject(@PathVariable Long userId,
                                   @PathVariable Long subjectId){
        return userService.enrollSubjectToUser(userId, subjectId);
    }

    @Operation(summary = "Crea la relación entre un usuario y una reserva en la tabla users_reservations, basado en la relación ManyToMany, utilizando la ID de cada uno.")
    @PutMapping(path = "/{userId}/reservation/{reservationId}")
    public User enrollUserToReservation(@PathVariable Long userId,
                                        @PathVariable Long reservationId){
        return userService.enrollReservationToUser(userId, reservationId);
    }

    @Operation(summary = "Elimina la fila que contiene la relación entre un usuario y una asignatura de la tabla users_subjects, utilizando la ID de cada uno.")
    @DeleteMapping(path = "/delete-user-subject/user/{userId}/subject/{subjectId}")
    public void deleteEnrollUserToSubject(@PathVariable Long userId, @PathVariable Long subjectId){
        userService.deleteEnrollSubjectToUser(userId, subjectId);
    }

    @Operation(summary = "Elimina la fila que contiene la relación entre un usuario y una reserva de la tabla users_reservations, utilizando la ID de cada uno.")
    @DeleteMapping(path = "/delete-user-reservation/user/{userId}/reservation/{reservationId}")
    public void deleteEnrollUserToReservation(@PathVariable Long userId, @PathVariable Long reservationId){
        userService.deleteEnrollReservationToUser(userId, reservationId);
    }

    @Operation(summary = "Elimina un usuario utilizando su ID.")
    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable Long id){
        userService.delete(id);
    }
}
