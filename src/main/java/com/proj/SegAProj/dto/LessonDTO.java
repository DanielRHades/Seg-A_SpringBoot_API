package com.proj.SegAProj.dto;

import com.proj.SegAProj.models.Classroom;
import com.proj.SegAProj.models.Subject;
import lombok.Data;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Set;

@Data
public class LessonDTO implements Serializable {

    private Long id;
    private DayOfWeek dayWeek;
    private LocalTime startTime;
    private LocalTime endTime;
    private Subject subjectLesson;
    private Classroom classroomLesson;
    private Set<UserDTO> userSetHash;

    public LessonDTO(Long id, DayOfWeek dayWeek, LocalTime startTime, LocalTime endTime, Subject subjectLesson, Classroom classroomLesson) {
        this.id = id;
        this.dayWeek = dayWeek;
        this.startTime = startTime;
        this.endTime = endTime;
        this.subjectLesson = subjectLesson;
        this.classroomLesson = classroomLesson;
    }

    public LessonDTO(Long id, DayOfWeek dayWeek, LocalTime startTime, LocalTime endTime, Subject subjectLesson, Classroom classroomLesson, Set<UserDTO> userSetHash) {
        this.id = id;
        this.dayWeek = dayWeek;
        this.startTime = startTime;
        this.endTime = endTime;
        this.subjectLesson = subjectLesson;
        this.classroomLesson = classroomLesson;
        this.userSetHash = userSetHash;
    }
}
