package com.proj.SegAProj.services;

import com.proj.SegAProj.dto.ReservationDTO;
import com.proj.SegAProj.dto.UserDTO;
import com.proj.SegAProj.models.Class;
import com.proj.SegAProj.models.Classroom;
import com.proj.SegAProj.models.Reservation;
import com.proj.SegAProj.models.User;
import com.proj.SegAProj.repositories.ClassroomRepository;
import com.proj.SegAProj.repositories.ReservationRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ClassroomRepository classroomRepository;

    @Autowired
    public ReservationService (ReservationRepository reservationRepository, ClassroomRepository classroomRepository){
        this.reservationRepository = reservationRepository;
        this.classroomRepository = classroomRepository;
    }

    public Reservation findById(Long id){
        return reservationRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("No existe una reserva con esa id."));
    }

    public ReservationDTO findByIdWithUsers(Long id){
        return convertOneReservationToDTOWithUsers(reservationRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("No existe una reserva con esa id.")));
    }

    public List<Reservation> findAll(){
        return reservationRepository.findAll();
    }

    public List<ReservationDTO> findAllWithUsers(){
        return convertAllReservationsToDTOWithUsers(findAll());
    }

    @Transactional
    public Reservation create(Reservation reservation){
        reservation.setClassroomReservation(null);
        return reservationRepository.save(reservation);
    }

    @Transactional
    public Reservation update(Long id, Reservation reservation){
        reservation.setClassroomReservation(null);
        var reservationPersisted = findById(id);
        if (!Objects.equals(reservationPersisted.getId(),id)){
            return reservationPersisted;
        }
        BeanUtils.copyProperties(reservation, reservationPersisted, "id", "classroomReservation");
        return reservationRepository.save(reservationPersisted);
    }

    @Transactional
    public void delete(Long id){reservationRepository.deleteById(id);}

    @Transactional
    public Reservation assignClassroomToReservation(Long idReservation, Long idClassroom){
        Reservation reservation = findById(idReservation);
        Classroom classroom = classroomRepository.findById(idClassroom)
                .orElseThrow(()->new RuntimeException("No existe esta clase."));
        List<Class> classList = classroom.getClassListClassroom();
        for (Class classEntity : classList){
            boolean interference = reservation.getReservationDate().getDayOfWeek().equals(classEntity.getDayWeek()) &&
                    reservation.getStartTime().isBefore(classEntity.getEndTime()) &&
                    reservation.getEndTime().isAfter(classEntity.getStartTime());
            if (interference){
                throw new RuntimeException("Existe una clase en ese salon, a esa hora.");
            }
        }
        reservation.setClassroomReservation(classroom);
        return reservationRepository.save(reservation);
    }

    @Transactional
    public Reservation unassignClassroomToReservation(Long id){
        Reservation reservation = findById(id);
        reservation.setClassroomReservation(null);
        return reservationRepository.save(reservation);
    }

    @Transactional
    public List<ReservationDTO> convertAllReservationsToDTOWithUsers(List<Reservation> reservations){
        List<ReservationDTO> reservationDTOs = new ArrayList<>();
        for (Reservation reservation : reservations){
            reservationDTOs.add(convertOneReservationToDTOWithUsers(reservation));
        }
        return reservationDTOs;
    }

    @Transactional
    public ReservationDTO convertOneReservationToDTOWithUsers(Reservation reservation){
        Set<UserDTO> userDTOs = reservation.getUserListReservation().stream().map(
                user -> new UserDTO(user.getId(),
                        user.getIdUni(),
                        user.getRole(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getEmail()))
                .collect(Collectors.toSet());
        return new ReservationDTO(
                reservation.getId(),
                reservation.getReservationDate(),
                reservation.getStartTime(),
                reservation.getEndTime(),
                reservation.getClassroomReservation(),
                userDTOs
        );
    }
}

