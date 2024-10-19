package com.proj.SegAProj.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.proj.SegAProj.enums.DayWeek;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "classes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Class {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "day_week", nullable = false)
    private DayWeek dayWeek;

    @Column(name = "start_time", nullable = false)
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime endTime;

    @ManyToOne
    @JoinColumn(name = "classroom_id")
    private Classroom classroomClass;

    @ManyToMany(mappedBy = "classes")
    private List<User> users;
}
