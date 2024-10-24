package com.proj.SegAProj.dto;

import com.proj.SegAProj.models.Classroom;
import lombok.Data;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Set;

@Data
public class ClassDTO implements Serializable {
    private Long id;
    private String name;
    private DayOfWeek dayWeek;
    private LocalTime startTime;
    private LocalTime endTime;
    private Classroom classroomClass;
    private Set<UserDTO> userSetHash;

    public ClassDTO(Long id, String name, DayOfWeek dayWeek, LocalTime startTime, LocalTime endTime, Classroom classroomClass) {
        this.id = id;
        this.name = name;
        this.dayWeek = dayWeek;
        this.startTime = startTime;
        this.endTime = endTime;
        this.classroomClass = classroomClass;
    }


    public ClassDTO(Long id, String name, DayOfWeek dayWeek, LocalTime startTime, LocalTime endTime, Classroom classroomClass, Set<UserDTO> userSetHash) {
        this.id = id;
        this.name = name;
        this.dayWeek = dayWeek;
        this.startTime = startTime;
        this.endTime = endTime;
        this.classroomClass = classroomClass;
        this.userSetHash = userSetHash;
    }

}
