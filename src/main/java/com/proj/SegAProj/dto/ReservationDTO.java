package com.proj.SegAProj.dto;

import com.proj.SegAProj.models.Classroom;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@Data
public class ReservationDTO implements Serializable {

    private Long id;
    private LocalDate reservationDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private Set<UserDTO> userSetHash;
    private Classroom classroomReservation;

    public ReservationDTO(Long id, LocalDate reservationDate, LocalTime startTime, LocalTime endTime, Classroom classroomReservation) {
        this.id = id;
        this.reservationDate = reservationDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.classroomReservation = classroomReservation;
    }

    public ReservationDTO(Long id, LocalDate reservationDate, LocalTime startTime, LocalTime endTime,Classroom classroomReservation,  Set<UserDTO> userSetHash) {
        this.id = id;
        this.reservationDate = reservationDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.classroomReservation = classroomReservation;
        this.userSetHash = userSetHash;
    }

    public ReservationDTO(Long id, LocalDate reservationDate, LocalTime startTime, LocalTime endTime) {
        this.id = id;
        this.reservationDate = reservationDate;
        this.startTime = startTime;
        this.endTime = endTime;;
    }
}
