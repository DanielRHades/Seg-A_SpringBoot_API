package com.proj.SegAProj.repositories;

import com.proj.SegAProj.models.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUniId(String uniId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM users_lessons WHERE user_id = :userId AND lesson_id = :lessonId", nativeQuery = true)
    void deleteRowFromUserLessonTable(@Param("userId") Long userId, @Param("lessonId") Long lessonId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM users_reservations WHERE user_id = :userId AND reservation_id = :reservationId", nativeQuery = true)
    void deleteRowFromUserReservationTable(@Param("userId") Long userId, @Param("reservationId") Long reservationId);

}
