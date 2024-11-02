package com.proj.SegAProj.models;


import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@Table(name = "subjects", uniqueConstraints = {@UniqueConstraint(name = "nrc_unique_constraint", columnNames = "nrc")})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "nrc", nullable = false)
    private String nrc;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "subjectLesson")
    @JsonIgnore
    private List<Lesson> lessonListSubject;

}
