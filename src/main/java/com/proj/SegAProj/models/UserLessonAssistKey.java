package com.proj.SegAProj.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDate;

@Embeddable
@EqualsAndHashCode
@Data
public class UserLessonAssistKey implements Serializable {

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "lesson_id")
    private Long lessonId;

    @Column(name = "entry_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Schema(type = "string", example = "yyyy-MM-dd")
    private LocalDate entryDate;

}