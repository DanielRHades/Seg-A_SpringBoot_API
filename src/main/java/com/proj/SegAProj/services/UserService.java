package com.proj.SegAProj.services;

import com.proj.SegAProj.dto.LessonDTO;
import com.proj.SegAProj.dto.ReservationDTO;
import com.proj.SegAProj.dto.UserDTO;
import com.proj.SegAProj.models.Lesson;
import com.proj.SegAProj.models.Reservation;
import com.proj.SegAProj.models.User;
import com.proj.SegAProj.repositories.LessonRepository;
import com.proj.SegAProj.repositories.ReservationRepository;
import com.proj.SegAProj.repositories.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final LessonRepository lessonRepository;
    private final ReservationRepository reservationRepository;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @Autowired
    public UserService (UserRepository userRepository,
                        LessonRepository lessonRepository,
                        ReservationRepository reservationRepository){
        this.userRepository = userRepository;
        this.lessonRepository = lessonRepository;
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

    public UserDTO findByIdWithLessons(Long userId) {
        return convertOneUserToDTOWithLessons(userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("No existe el usuario")));
    }

    public UserDTO findByIdWithReservations(Long userId) {
        return convertOneUserToDTOWithReservations(userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("No existe el usuario")));
    }

    public List<UserDTO> findAllWithLessons() {
        return convertAllUserToDTOWithLessons(userRepository.findAll());
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
    public User enrollLessonToUser(Long userId, Long lessonId){
        User user = userRepository.findById(userId)
                .orElseThrow(()->new RuntimeException("No existe el usuario"));
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(()->new RuntimeException("No existe la lección."));
        user.getLessonListUser().add(lesson);
        return userRepository.save(user);
    }

    @Transactional
    public void deleteEnrollLessonToUser(Long userId, Long lessonId){
        User user = userRepository.findById(userId)
                .orElseThrow(()->new RuntimeException("No existe el usuario."));
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(()->new RuntimeException("No existe la lección."));
        user.getLessonListUser().remove(lesson);
        userRepository.deleteRowFromUserLessonTable(user.getId(), lesson.getId());
    }

    @Transactional
    public User enrollReservationToUser(Long userId, Long reservationId){
        User user = userRepository.findById(userId)
                .orElseThrow(()->new RuntimeException("No existe el usuario."));
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(()-> new RuntimeException("No existe la reserva."));
        user.getReservationListUser().add(reservation);
        return userRepository.save(user);
    }

    @Transactional
    public void deleteEnrollReservationToUser(Long idUser, Long idReservation){
        User user = userRepository.findById(idUser)
                .orElseThrow(()->new RuntimeException("No existe el usuario."));
        Reservation reservation = reservationRepository.findById(idReservation)
                .orElseThrow(()-> new RuntimeException("No existe la reserva."));
        userRepository.deleteRowFromUserReservationTable(user.getId(), reservation.getId());
    }

    public List<UserDTO> convertAllUsersToDTO(List<User> userList){
        List<UserDTO> userDTOList = new ArrayList<>(userList.size());
        userList.forEach(user -> userDTOList.add(convertOneUserToDTO(user)));
        return userDTOList;
    }

    public List<UserDTO> convertAllUserToDTOWithLessons(List<User> userList){
        List<UserDTO> userDTOList = new ArrayList<>(userList.size());
        userList.forEach(user -> userDTOList.add(convertOneUserToDTOWithLessons(user)));
        return userDTOList;
    }

    public List<UserDTO> convertAllUserToDTOWithReservations(List<User> userList){
        List<UserDTO> userDTOList = new ArrayList<>(userList.size());
        userList.forEach(user -> userDTOList.add(convertOneUserToDTOWithReservations(user)));
        return userDTOList;
    }

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

    public UserDTO convertOneUserToDTOWithLessons(User user) {
        Set<LessonDTO> lessonDTOs = user.getLessonListUser().stream()
                .map(lesson -> new LessonDTO(lesson.getId(),
                        lesson.getDayWeek(),
                        lesson.getStartTime(),
                        lesson.getEndTime(),
                        lesson.getSubjectLesson(),
                        lesson.getClassroomLesson()))
                .collect(Collectors.toSet());

        return new UserDTO(
                user.getId(),
                user.getUniId(),
                user.getRole(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                lessonDTOs,
                null
        );
    }

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
