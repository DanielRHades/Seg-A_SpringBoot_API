package com.proj.SegAProj.dto;

import com.proj.SegAProj.enums.DayWeek;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalTime;

@Data
public class ClassDTO implements Serializable {
    private Long id;
    private String name;
    private DayWeek dayWeek;
    private LocalTime startTime;
    private LocalTime endTime;

    public ClassDTO(Long id, String name, DayWeek dayWeek, LocalTime startTime, LocalTime endTime) {
        this.id = id;
        this.name = name;
        this.dayWeek = dayWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }

}
