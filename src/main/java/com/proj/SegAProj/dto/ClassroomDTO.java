package com.proj.SegAProj.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Set;

@Data
public class ClassroomDTO implements Serializable {

    private Long id;
    private String name;
    private Integer capacity;
    private Set<SubjectDTO> subjectSetHash;
    private Set<ReservationDTO> reservationSetHash;

    public ClassroomDTO(Long id, String name, Integer capacity, Set<SubjectDTO> subjectSetHash, Set<ReservationDTO> reservationSetHash){
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.subjectSetHash = subjectSetHash;
        this.reservationSetHash = reservationSetHash;
    }

}
