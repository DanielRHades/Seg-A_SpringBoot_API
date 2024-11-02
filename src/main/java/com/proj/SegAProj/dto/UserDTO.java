package com.proj.SegAProj.dto;

import com.proj.SegAProj.enums.Role;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

@Data
public class UserDTO implements Serializable {
    private Long id;
    private String uniId;
    private Role role;
    private String firstName;
    private String lastName;
    private String email;
    private Set<LessonDTO> lessonSetHas;
    private Set<ReservationDTO> reservationSetHash;

    public UserDTO(Long id, String uniId, Role role, String firstName, String lastName, String email,
                   Set<LessonDTO> lessonSetHas, Set<ReservationDTO> reservationSetHash) {
        this.id = id;
        this.uniId = uniId;
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.lessonSetHas = lessonSetHas;
        this.reservationSetHash = reservationSetHash;
    }

    public UserDTO(Long id, String uniId, Role role, String firstName, String lastName, String email) {
        this.id = id;
        this.uniId = uniId;
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
}
