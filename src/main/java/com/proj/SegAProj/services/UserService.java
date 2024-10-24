package com.proj.SegAProj.services;

import com.proj.SegAProj.dto.ClassDTO;
import com.proj.SegAProj.dto.ReservationDTO;
import com.proj.SegAProj.dto.UserDTO;
import com.proj.SegAProj.models.Class;
import com.proj.SegAProj.models.Reservation;
import com.proj.SegAProj.models.User;
import com.proj.SegAProj.repositories.ClassRepository;
import com.proj.SegAProj.repositories.ReservationRepository;
import com.proj.SegAProj.repositories.UserRepository;
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
public class UserService {

    private final UserRepository userRepository;
    private final ClassRepository classRepository;
    private final ReservationRepository reservationRepository;

    @Autowired
    public UserService (UserRepository userRepository, ClassRepository classRepository, ReservationRepository reservationRepository){
        this.userRepository = userRepository;
        this.classRepository = classRepository;
        this.reservationRepository = reservationRepository;
    }

    public List<UserDTO> findAll(){
        return convertAllUsersToDTO(userRepository.findAll());
    }

    public UserDTO findById(Long id){
        return convertOneUserToDTO(userRepository.findById(id)
                .orElseThrow(()->new RuntimeException("No existe el usuario.")));
    }

    public UserDTO findByIdUni (String idUni){
        return convertOneUserToDTO(userRepository.findByIdUni(idUni)
                .orElseThrow(()->new RuntimeException("No existe el usuario.")));
    }

    public UserDTO findByIdWithClasses(Long userId) {
        return convertOneUserToDTOWithClasses(userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("No existe el usuario")));
    }

    public UserDTO findByIdWithReservations(Long userId) {
        return convertOneUserToDTOWithReservations(userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("No existe el usuario")));
    }

    public List<UserDTO> findAllWithClasses() {
        return convertAllUserToDTOWithClasses(userRepository.findAll());
    }

    public List<UserDTO> findAllWithReservations() {
        return convertAllUserToDTOWithReservations(userRepository.findAll());
    }

    @Transactional
    public User create (User user){
        return userRepository.save(user);
    }

    @Transactional
    public User update (Long id, User user){
        User userPersisted = userRepository.findById(id).orElseThrow();
        if (!Objects.equals(userPersisted.getId(), id)) {
            return userPersisted;
        }
        BeanUtils.copyProperties(user, userPersisted, "id");
        return userRepository.save(userPersisted);
    }

    @Transactional
    public void delete (Long id){
        userRepository.deleteById(id);
    }

    @Transactional
    public User enrollClassToUser(Long userId, Long classId){
        User user = userRepository.findById(userId).orElseThrow(()->new RuntimeException("No existe el usuario"));
        Class classEntity = classRepository.findById(classId).orElseThrow(()->new RuntimeException("No existe la clase."));
        user.getClassListUser().add(classEntity);
        return userRepository.save(user);
    }

    @Transactional
    public void deleteEnrollClassToUser(Long idUser, Long idClass){
        User user = userRepository.findById(idUser).orElseThrow(()->new RuntimeException("No existe el usuario."));
        Class classEntity = classRepository.findById(idClass).orElseThrow(()->new RuntimeException("No existe la clase."));
        userRepository.deleteRowFromUserClassTable(user.getId(), classEntity.getId());
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
                user.getIdUni(),
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
    public List<UserDTO> convertAllUserToDTOWithClasses(List<User> users){
        List<UserDTO> userList = new ArrayList<>();
        for (User user : users){
             userList.add(convertOneUserToDTOWithClasses(user));
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
    public UserDTO convertOneUserToDTOWithClasses(User user) {
        Set<ClassDTO> classDTOs = user.getClassListUser().stream()
                .map(classEntity -> new ClassDTO(classEntity.getId(),
                        classEntity.getName(),
                        classEntity.getDayWeek(),
                        classEntity.getStartTime(),
                        classEntity.getEndTime(),
                        classEntity.getClassroomClass()))
                .collect(Collectors.toSet());

        return new UserDTO(
                user.getId(),
                user.getIdUni(),
                user.getRole(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                classDTOs,
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
                user.getIdUni(),
                user.getRole(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                null,
                reservationDTOs
        );
    }
}
