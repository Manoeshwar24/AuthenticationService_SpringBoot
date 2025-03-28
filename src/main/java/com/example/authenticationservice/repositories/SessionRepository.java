package com.example.authenticationservice.repositories;

import com.example.authenticationservice.models.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {

    Optional<Session> findByUserIdAndIpAddress(long id, String ip1);
    Session save(Session session);

    @Modifying
    @Transactional
    @Query("UPDATE Session s SET s.sessionStatus = 'ENDED' WHERE s.user.id = :userID")
    void endAllSessions(Long userID);

    @Modifying
    @Transactional
    @Query("UPDATE Session s SET s.sessionStatus = 'ENDED' WHERE s.user.id = :userID AND s.ipAddress = :ipAddress")
    void endCurrentSession(Long userID, String ipAddress);
}
