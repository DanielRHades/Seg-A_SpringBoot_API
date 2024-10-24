package com.proj.SegAProj.controllers;

import com.proj.SegAProj.models.Reservation;
import com.proj.SegAProj.services.ReservationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reservation")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController (ReservationService reservationService){
        this.reservationService = reservationService;
    }

    @GetMapping(path = "/{id}")
    public Reservation findById(@PathVariable Long id){
        return reservationService.findById(id);
    }

    @GetMapping
    public List<Reservation> findAll(){
        return reservationService.findAll();
    }

    @PostMapping
    public Reservation create(@RequestBody Reservation reservation){
        return reservationService.create(reservation);
    }

    @PutMapping(path = "/{id}")
    public Reservation update (@PathVariable Long id, @RequestBody Reservation reservation){
        return reservationService.update(id, reservation);
    }

    @PutMapping(path = "/{reservationId}/classroom/{classroomId}")
    public Reservation assignClassroomToReservation(@PathVariable Long reservationId,
                                                    @PathVariable Long classroomId){
        return reservationService.assignClassroomToReservation(reservationId, classroomId);
    }

    @DeleteMapping(path = "/delete/classroomInReservation/{id}")
    public Reservation unassignClassroomToReservation(@PathVariable Long id){
        return reservationService.unassignClassroomToReservation(id);
    }

    @DeleteMapping(path = "/{id}")
    public void delete (@PathVariable Long id){
        reservationService.delete(id);
    }

}
