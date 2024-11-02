package com.proj.SegAProj.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "lessons")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

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

    @ManyToMany(mappedBy = "lessonListUser")
    @JsonIgnore
    private List<User> userListLesson;

    @OneToMany(mappedBy = "lessonAssist")
    @JsonIgnore
    private List<UserLessonAssist> lessonAssistListLesson;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonInclude
    @JoinColumn(
            name = "subject_id",
            referencedColumnName = "id"
    )
    private Subject subjectLesson;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonInclude
    @JoinColumn(
            name = "classroom_id",
            referencedColumnName = "id"
    )
    private Classroom classroomLesson;

    public Lesson(DayOfWeek dayWeek, LocalTime startTime, LocalTime endTime) {
        this.dayWeek = dayWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
