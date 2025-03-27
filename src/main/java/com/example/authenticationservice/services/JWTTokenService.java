package com.example.authenticationservice.services;

import com.example.authenticationservice.exceptions.TokenInvalidException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JWTTokenService {
    private SecretKey jwtSecretKey;
    private SessionService sessionService;

    public JWTTokenService(@Value("${jwt.secret}") String customerSecretKey, SessionService sessionService) {
        this.jwtSecretKey = Keys.hmacShaKeyFor(customerSecretKey.getBytes());
        this.sessionService = sessionService;
    }

    public String generateToken(long id, String email, String ipAddress) {
        Map<String, Object> claims = new HashMap<String, Object>();
        claims.put("userID", id);
        claims.put("email", email);
        claims.put("role", "USER");
        claims.put("ipAddress", ipAddress);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 30);
        Date tokenExpirationDate = calendar.getTime();

        String jwtToken = Jwts.builder()
                .claims(claims)
                .expiration(tokenExpirationDate)
                .issuedAt(new Date())
                .issuer("Eshwar_Dev")
                .signWith(jwtSecretKey)
                .compact();

        return jwtToken;
    }

    public boolean validateToken(String jwtToken) throws TokenInvalidException{
        Jws<Claims> claims = Jwts.parser()
                .verifyWith(jwtSecretKey)
                .build()
                .parseSignedClaims(jwtToken);

        //get the user details from the token
        String email = claims.getPayload().get("email", String.class);
        Long userID = claims.getPayload().get("userID", Long.class);
        String ipAddress = claims.getPayload().get("ipAddress", String.class);

        //check if the session is ended or token is expired for the userID and ipAddress
        if(sessionService.isTokenActive(userID, ipAddress)){
            sessionService.endAllSessions(userID);

            return true;
        }

        return false;
    }
}
