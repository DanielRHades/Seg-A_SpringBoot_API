package com.proj.SegAProj.services;

import com.proj.SegAProj.dto.SubjectDTO;
import com.proj.SegAProj.dto.ReservationDTO;
import com.proj.SegAProj.dto.UserDTO;
import com.proj.SegAProj.models.Subject;
import com.proj.SegAProj.models.Reservation;
import com.proj.SegAProj.models.User;
import com.proj.SegAProj.repositories.SubjectRepository;
import com.proj.SegAProj.repositories.ReservationRepository;
import com.proj.SegAProj.repositories.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final SubjectRepository subjectRepository;
    private final ReservationRepository reservationRepository;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @Autowired
    public UserService (UserRepository userRepository,
                        SubjectRepository subjectRepository,
                        ReservationRepository reservationRepository){
        this.userRepository = userRepository;
        this.subjectRepository = subjectRepository;
        this.reservationRepository = reservationRepository;
    }

    public List<UserDTO> findAll(){
        return convertAllUsersToDTO(userRepository.findAll());
    }

    public UserDTO findById(Long id){
        return convertOneUserToDTO(userRepository.findById(id)
                .orElseThrow(()->new RuntimeException("No existe el usuario.")));
    }

    public UserDTO findByUniId (String uniId){
        return convertOneUserToDTO(userRepository.findByUniId(uniId)
                .orElseThrow(()->new RuntimeException("No existe el usuario.")));
    }

    public UserDTO findByIdWithSubjects(Long userId) {
        return convertOneUserToDTOWithSubjects(userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("No existe el usuario")));
    }

    public UserDTO findByIdWithReservations(Long userId) {
        return convertOneUserToDTOWithReservations(userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("No existe el usuario")));
    }

    public List<UserDTO> findAllWithSubjects() {
        return convertAllUserToDTOWithSubjects(userRepository.findAll());
    }

    public List<UserDTO> findAllWithReservations() {
        return convertAllUserToDTOWithReservations(userRepository.findAll());
    }

    @Transactional
    public User create (User user){
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Transactional
    public User update (Long id, User user){
        User userPersisted = userRepository.findById(id).orElseThrow();
        if (!Objects.equals(userPersisted.getId(), id)) {
            return userPersisted;
        }
        user.setPassword(encoder.encode(user.getPassword()));
        BeanUtils.copyProperties(user, userPersisted, "id", "uniId");
        return userRepository.save(userPersisted);
    }

    @Transactional
    public void delete (Long id){
        userRepository.deleteById(id);
    }

    @Transactional
    public User enrollSubjectToUser(Long userId, Long subjectId){
        User user = userRepository.findById(userId).orElseThrow(()->new RuntimeException("No existe el usuario"));
        Subject subject = subjectRepository.findById(subjectId).orElseThrow(()->new RuntimeException("No existe la asignatura."));
        user.getSubjectListUser().add(subject);
        return userRepository.save(user);
    }

    @Transactional
    public void deleteEnrollSubjectToUser(Long userId, Long subjectId){
        User user = userRepository.findById(userId).orElseThrow(()->new RuntimeException("No existe el usuario."));
        Subject subject = subjectRepository.findById(subjectId).orElseThrow(()->new RuntimeException("No existe la asignatura."));
        user.getSubjectListUser().remove(subject);
        userRepository.deleteRowFromUserSubjectTable(user.getId(), subject.getId());
    }

    @Transactional
    public User enrollReservationToUser(Long userId, Long reservationId){
        User user = userRepository.findById(userId).orElseThrow(()->new RuntimeException("No existe el usuario"));
        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(()-> new RuntimeException("No existe la reserva"));
        user.getReservationListUser().add(reservation);
        return userRepository.save(user);
    }

    @Transactional
    public void deleteEnrollReservationToUser(Long idUser, Long idReservation){
        User user = userRepository.findById(idUser).orElseThrow(()->new RuntimeException("No existe el usuario."));
        Reservation reservation = reservationRepository.findById(idReservation).orElseThrow(()-> new RuntimeException("No existe la reserva."));
        userRepository.deleteRowFromUserReservationTable(user.getId(), reservation.getId());
    }

    @Transactional
    public UserDTO convertOneUserToDTO(User user){
        return new UserDTO(
                user.getId(),
                user.getUniId(),
                user.getRole(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail()
        );

    }

    @Transactional
    public List<UserDTO> convertAllUsersToDTO(List<User> userList){
        List<UserDTO> userDTOList = new ArrayList<>();
        for (User user : userList){
            userDTOList.add(convertOneUserToDTO(user));
        }
        return userDTOList;
    }

    @Transactional
    public List<UserDTO> convertAllUserToDTOWithSubjects(List<User> users){
        List<UserDTO> userList = new ArrayList<>();
        for (User user : users){
             userList.add(convertOneUserToDTOWithSubjects(user));
        }
        return userList;
    }

    @Transactional
    public List<UserDTO> convertAllUserToDTOWithReservations(List<User> users){
        List<UserDTO> userList = new ArrayList<>();
        for (User user : users){
            userList.add(convertOneUserToDTOWithReservations(user));
        }
        return userList;
    }

    @Transactional
    public UserDTO convertOneUserToDTOWithSubjects(User user) {
        Set<SubjectDTO> subjectDTOS = user.getSubjectListUser().stream()
                .map(subject -> new SubjectDTO(subject.getId(),
                        subject.getNrc(),
                        subject.getName(),
                        subject.getDayWeek(),
                        subject.getStartTime(),
                        subject.getEndTime(),
                        subject.getClassroomSubject()))
                .collect(Collectors.toSet());

        return new UserDTO(
                user.getId(),
                user.getUniId(),
                user.getRole(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                subjectDTOS,
                null
        );
    }

    @Transactional
    public UserDTO convertOneUserToDTOWithReservations(User user){
        Set<ReservationDTO> reservationDTOs = user.getReservationListUser().stream()
                .map(reservation -> new ReservationDTO(reservation.getId(),
                        reservation.getReservationDate(),
                        reservation.getStartTime(),
                        reservation.getEndTime(),
                        reservation.getClassroomReservation()))
                .collect(Collectors.toSet());

        return new UserDTO(
                user.getId(),
                user.getUniId(),
                user.getRole(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                null,
                reservationDTOs
        );
    }
}
