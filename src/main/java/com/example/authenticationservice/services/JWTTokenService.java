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

    public boolean validateToken(Long userID, String ipAddress){

        //check if there is a session for the userID and ipAddress
        //if yes, check if the session is active and the token is not expired
        return sessionService.isTokenValid(userID, ipAddress);
    }

    public Claims parseToken(String jwtToken) {
        Jws<Claims> claims = Jwts.parser()
                .verifyWith(jwtSecretKey)
                .build()
                .parseSignedClaims(jwtToken);

        return claims.getPayload();
    }
}
