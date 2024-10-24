package com.proj.SegAProj.models;

import com.fasterxml.jackson.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "reservations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

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

    @ManyToMany(mappedBy = "reservationListUser")
    @JsonIgnore
    private List<User> userListReservation;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonInclude
    @JoinColumn(
            name = "classroom_id",
            referencedColumnName = "id"
    )
    private Classroom classroomReservation;

}
