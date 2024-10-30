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
    @GetMapping(path = "/id-uni/{idUni}")
    public UserDTO findByIdUni(@PathVariable String idUni){
        return userService.findByIdUni(idUni);
    }

    @Operation(summary = "Obtener un usuario utilizando su ID, AGREGANDO todas las clases de este usuario.")
    @GetMapping(path = "/id/with-classes/{id}")
    public UserDTO findByIdWithClasses (@PathVariable Long id){
        return userService.findByIdWithClasses(id);
    }

    @Operation(summary = "Obtener un usuario utilizando su ID, AGREGANDO todas las reservas de este usuario.")
    @GetMapping(path = "/id/with-reservations/{id}")
    public UserDTO findByIdWithReservations(@PathVariable Long id){
        return userService.findByIdWithReservations(id);
    }

    @Operation(summary = "Obtener todos los usuarios, AGREGANDO todas las clases de los usuarios.")
    @GetMapping(path = "/with-classes")
    public List<UserDTO> findAllWithClasses (){
        return userService.findAllWithClasses();
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

    @Operation(summary = "Crea la relación entre un usuario y una clase en la tabla users_classes, basado en la relación ManyToMany, utilizando la ID de cada uno.")
    @PutMapping(path = "/{userId}/class/{classId}")
    public User enrollUserToClass(@PathVariable Long userId,
                                   @PathVariable Long classId){
        return userService.enrollClassToUser(userId, classId);
    }

    @Operation(summary = "Crea la relación entre un usuario y una reserva en la tabla users_reservations, basado en la relación ManyToMany, utilizando la ID de cada uno.")
    @PutMapping(path = "/{userId}/reservation/{reservationId}")
    public User enrollUserToReservation(@PathVariable Long userId,
                                        @PathVariable Long reservationId){
        return userService.enrollReservationToUser(userId, reservationId);
    }

    @Operation(summary = "Elimina la fila que contiene la relación entre un usuario y una clase de la tabla users_classes, utilizando la ID de cada uno.")
    @DeleteMapping(path = "/delete-user-class/user/{idUser}/class/{idClass}")
    public void deleteEnrollUserToClass(@PathVariable Long idUser, @PathVariable Long idClass){
        userService.deleteEnrollClassToUser(idUser, idClass);
    }

    @Operation(summary = "Elimina la fila que contiene la relación entre un usuario y una reserva de la tabla users_reservations, utilizando la ID de cada uno.")
    @DeleteMapping(path = "/delete-user-reservation/user/{idUser}/reservation/{idReservation}")
    public void deleteEnrollUserToReservation(@PathVariable Long idUser, @PathVariable Long idReservation){
        userService.deleteEnrollReservationToUser(idUser, idReservation);
    }

    @Operation(summary = "Elimina un usuario utilizando su ID.")
    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable Long id){
        userService.delete(id);
    }
}
