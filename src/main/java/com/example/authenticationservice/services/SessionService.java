// SessionService.java
package com.example.authenticationservice.services;

import com.example.authenticationservice.models.Session;
import com.example.authenticationservice.models.SessionStatus;
import com.example.authenticationservice.models.User;
import com.example.authenticationservice.repositories.SessionRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class SessionService {
    private final SessionRepository sessionRepository;

    public SessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public void createOrUpdateSession(User user, String ipAddress ,String jwtToken) {
        Optional<Session> dbSession = sessionRepository.findByUserIdAndIpAddress(user.getId(), ipAddress);
        if (dbSession.isPresent()) {
            updateSession(dbSession.get(), jwtToken);
        } else {
            createNewSession(user, jwtToken, ipAddress);
        }
    }

    private void updateSession(Session session, String jwtToken) {
        session.setToken(jwtToken);
        session.setExpiryAt(LocalDateTime.now().plusDays(30));
        session.setLastLogin(LocalDateTime.now());
        session.setUpdatedAt(LocalDateTime.now());
        sessionRepository.save(session);
    }

    private void createNewSession(User user, String jwtToken, String ipAddress) {
        Session newSession = new Session();
        newSession.setToken(jwtToken);
        newSession.setUser(user);
        newSession.setIpAddress(ipAddress);
        newSession.setSessionStatus(SessionStatus.ACTIVE);
        newSession.setExpiryAt(LocalDateTime.now().plusDays(30));
        newSession.setLastLogin(LocalDateTime.now());
        newSession.setCreatedAt(LocalDateTime.now());
        newSession.setUpdatedAt(LocalDateTime.now());
        sessionRepository.save(newSession);
    }

    public boolean isTokenActive(Long userID, String ipAddress) {

        Optional<Session> dbSession = sessionRepository.findByUserIdAndIpAddress(userID, ipAddress);
        if (dbSession.isEmpty() || dbSession.get().getSessionStatus() != SessionStatus.ACTIVE ||
            dbSession.get().getExpiryAt().isBefore(LocalDateTime.now())) {
            return false;
        }

        return true;
    }

    public void endAllSessions(Long userID) {
        sessionRepository.endAllSessions(userID);
    }
}