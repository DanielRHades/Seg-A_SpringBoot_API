package com.proj.SegAProj.models;


import com.fasterxml.jackson.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;


@Entity
@Table(name = "subjects")
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

    @Column(name = "day_week", nullable = false)
    private DayOfWeek dayWeek;

    @Column(name = "start_time", nullable = false)
    @JsonFormat(pattern = "HH:mm:ss")
    @Schema(type = "string", example = "HH:mm:ss")
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    @JsonFormat(pattern = "HH:mm:ss")
    @Schema(type = "string", example = "HH:mm:ss")
    private LocalTime endTime;

    @ManyToMany(mappedBy = "subjectListUser")
    @JsonIgnore
    private List<User> userListSubject;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonInclude
    @JoinColumn(
            name = "classroom_id",
            referencedColumnName = "id"
    )
    private Classroom classroomSubject;

    @OneToMany(mappedBy = "subjectAssist")
    @JsonIgnore
    private List<UserSubjectAssist> subjectAssistListSubject;

}
