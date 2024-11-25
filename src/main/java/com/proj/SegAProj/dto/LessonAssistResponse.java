package com.proj.SegAProj.dto;

import com.proj.SegAProj.models.Subject;
import lombok.Data;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@Data
public class LessonAssistResponse implements Serializable {

    private Long id;
    private Subject subject;
    private DayOfWeek dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;
    private Map<LocalDate, List<AssistUserDetail>> assistByDate;

    @Data
    public static class AssistUserDetail {
        private Long id;
        private String uniId;
        private String name;
        private String email;
        private LocalTime entryTime;
    }

    public LessonAssistResponse(Long id, Subject subject, DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime, Map<LocalDate, List<AssistUserDetail>> assistByDate) {
        this.id = id;
        this.subject = subject;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
        this.assistByDate = assistByDate;
    }

}
