package com.proj.SegAProj.controllers;

import com.proj.SegAProj.dto.ReservationDTO;
import com.proj.SegAProj.dto.ReservationRequest;
import com.proj.SegAProj.models.Reservation;
import com.proj.SegAProj.services.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2/reservation")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController (ReservationService reservationService){
        this.reservationService = reservationService;
    }

    @Operation(summary = "Obtiene una reserva utilizando su ID.")
    @GetMapping(path = "/{id}")
    public Reservation findById(@PathVariable Long id){
        return reservationService.findById(id);
    }

    @Operation(summary = "Obtiene una reserva utilizando su ID, AGREGANDO todos los usuarios de la reserva.")
    @GetMapping(path = "/with-users/{id}")
    public ReservationDTO findByIdWithUsers(@PathVariable Long id){
        return reservationService.findByIdWithUsers(id);
    }

    @Operation(summary = "Obtiene todas las reservas.")
    @GetMapping
    public List<Reservation> findAll(){
        return reservationService.findAll();
    }

    @Operation(summary = "Obtiene todas las reservas, AGREGANDO todos los usuarios de las reservas.")
    @GetMapping(path = "/with-users")
    public List<ReservationDTO> findAllWithUsers(){
        return reservationService.findAllWithUsers();
    }

    @Operation(summary = "Crea una nueva reserva.")
    @PostMapping
    public Reservation create(@RequestBody ReservationRequest reservationRequest){
        return reservationService.create(reservationRequest);
    }

    @Operation(summary = "Actualiza una reserva utilizando su ID.")
    @PutMapping(path = "/{id}")
    public Reservation update (@PathVariable Long id, @RequestBody ReservationRequest reservationRequest){
        return reservationService.update(id, reservationRequest);
    }

    @Operation(summary = "Put encargado de asignar un salon a una reserva utilizando las ID de cada uno. " +
            "Le agrega el id_classroom a la entidad en la tabla reservations basado en el principio de ManyToOne como Foreign Key.")
    @PutMapping(path = "/{reservationId}/classroom/{classroomId}")
    public Reservation assignClassroomToReservation(@PathVariable Long reservationId,
                                                    @PathVariable Long classroomId){
        return reservationService.assignClassroomToReservation(reservationId, classroomId);
    }

    @Operation(summary = "Delete encargado de borrar el salon previamente asignado a una reserva utilizando el ID de la reserva.")
    @DeleteMapping(path = "/delete/classroom-in-reservation/{id}")
    public Reservation unassignClassroomToReservation(@PathVariable Long id){
        return reservationService.unassignClassroomToReservation(id);
    }

    @Operation(summary = "Delete encargado de borrar una reserva utilizando su ID.")
    @DeleteMapping(path = "/{id}")
    public void delete (@PathVariable Long id){
        reservationService.delete(id);
    }

}
