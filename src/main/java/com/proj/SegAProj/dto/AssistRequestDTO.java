package com.proj.SegAProj.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class AssistRequestDTO implements Serializable {
    private Long userId;
    private Long subjectId;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Schema(type = "string", example = "yyyy-MM-dd")
    private LocalDate entryDate;

    @JsonFormat(pattern = "HH:mm:ss")
    @Schema(type = "string", example = "HH:mm:ss")
    private LocalTime entryTime;
}
