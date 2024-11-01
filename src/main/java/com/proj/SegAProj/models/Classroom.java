package com.proj.SegAProj.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@Table(name = "classrooms", uniqueConstraints = { @UniqueConstraint(name = "name_classroom_uk_constraint", columnNames = "name") })
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Classroom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "capacity")
    private Integer capacity;

    @OneToMany(mappedBy = "classroomReservation")
    @JsonIgnore
    private List<Reservation> reservationListClassroom;

    @OneToMany(mappedBy = "classroomSubject")
    @JsonIgnore
    private List<Subject> subjectListClassroom;
}
