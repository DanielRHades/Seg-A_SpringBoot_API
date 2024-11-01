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
public class UserSubjectAssist {

    @EmbeddedId
    private UserSubjectAssistKey id;

    @ManyToOne
    @MapsId("userId")
    @JsonIgnore
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id")
    private User userAssist;

    @ManyToOne
    @MapsId("subjectId")
    @JsonIgnore
    @JoinColumn(
            name = "subject_id",
            referencedColumnName = "id")
    private Subject subjectAssist;

    @Column(name = "entry_time", nullable = false)
    @JsonFormat(pattern = "HH:mm:ss")
    @Schema(type = "string", example = "HH:mm:ss")
    private LocalTime entryTime;
}

