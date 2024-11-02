package com.proj.SegAProj.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Entity
@Table(name = "assists")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLessonAssist {

    @EmbeddedId
    private UserLessonAssistKey id;

    @ManyToOne
    @MapsId("userId")
    @JsonIgnore
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id")
    private User userAssist;

    @ManyToOne
    @MapsId("lessonId")
    @JsonIgnore
    @JoinColumn(
            name = "lesson_id",
            referencedColumnName = "id")
    private Lesson lessonAssist;

    @Column(name = "entry_time", nullable = false)
    @JsonFormat(pattern = "HH:mm:ss")
    @Schema(type = "string", example = "HH:mm:ss")
    private LocalTime entryTime;
}

