package com.proj.SegAProj.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.proj.SegAProj.enums.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table (name = "users",
        uniqueConstraints = { @UniqueConstraint(name = "email_uk_constraint", columnNames = "email"),
                @UniqueConstraint(name = "id_uni_uk_constraint", columnNames = "id_uni") } )
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column (name = "id_uni", nullable = false)
    private String idUni;

    @Column(name = "role", nullable = false)
    private Role role;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @JsonIgnore
    @ManyToMany(mappedBy = "userHashClass")
    private Set<Class> classHashUser = new HashSet<>();

    @ManyToMany(mappedBy = "userHashReservation")
    private Set<Reservation> reservationHashUser = new HashSet<>();

    //private List<Reservation> reservations;

}
