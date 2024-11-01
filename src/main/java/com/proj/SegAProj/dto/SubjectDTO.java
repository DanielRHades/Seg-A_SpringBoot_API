package com.proj.SegAProj.dto;

import com.proj.SegAProj.models.Classroom;
import lombok.Data;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Set;

@Data
public class SubjectDTO implements Serializable {
    private Long id;
    private String nrc;
    private String name;
    private DayOfWeek dayWeek;
    private LocalTime startTime;
    private LocalTime endTime;
    private Classroom classroomSubject;
    private Set<UserDTO> userSetHash;

    public SubjectDTO(Long id, String nrc, String name, DayOfWeek dayWeek, LocalTime startTime, LocalTime endTime, Classroom classroomSubject) {
        this.id = id;
        this.nrc = nrc;
        this.name = name;
        this.dayWeek = dayWeek;
        this.startTime = startTime;
        this.endTime = endTime;
        this.classroomSubject = classroomSubject;
    }


    public SubjectDTO(Long id, String nrc, String name, DayOfWeek dayWeek, LocalTime startTime, LocalTime endTime, Classroom classroomSubject, Set<UserDTO> userSetHash) {
        this.id = id;
        this.nrc = nrc;
        this.name = name;
        this.dayWeek = dayWeek;
        this.startTime = startTime;
        this.endTime = endTime;
        this.classroomSubject = classroomSubject;
        this.userSetHash = userSetHash;
    }

    public SubjectDTO(Long id, String nrc, String name, DayOfWeek dayWeek, LocalTime startTime, LocalTime endTime) {
        this.id = id;
        this.nrc = nrc;
        this.name = name;
        this.dayWeek = dayWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }

}
