package com.proj.SegAProj.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationRequest {

    @Column(name = "reservation_date", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Schema(type = "string", example = "yyyy-MM-dd")
    private LocalDate reservationDate;

    @Column(name = "start_time", nullable = false)
    @JsonFormat(pattern = "HH:mm:ss")
    @Schema(type = "string", example = "HH:mm:ss")
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    @JsonFormat(pattern = "HH:mm:ss")
    @Schema(type = "string", example = "HH:mm:ss")
    private LocalTime endTime;

}
