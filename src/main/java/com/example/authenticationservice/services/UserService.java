package com.example.authenticationservice.services;

import com.example.authenticationservice.exceptions.UserAlreadyExistsException;
import com.example.authenticationservice.exceptions.UserDoesNotExistException;
import com.example.authenticationservice.exceptions.WrongPasswordException;
import com.example.authenticationservice.models.User;
import com.example.authenticationservice.repositories.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.util.*;

@Service
public class UserService {
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private SecretKey key = Jwts.SIG.HS256.key().build();

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public boolean signUpUser(User toBeCreatedUser) throws UserAlreadyExistsException {
        //check if the user's email already exists
        if(userRepository.findByEmail(toBeCreatedUser.getEmail()).isPresent()){
            throw new UserAlreadyExistsException("User with mail '" + toBeCreatedUser.getEmail() + "' already exists!");
        }
        //encrypt the password before saving in the DB
        toBeCreatedUser.setPassword(bCryptPasswordEncoder.encode(toBeCreatedUser.getPassword()));
        //persist the user data in the db
        User createdUser = userRepository.save(toBeCreatedUser);
        return true;
    }

    public String loginUser(User userToLogin) throws UserDoesNotExistException, WrongPasswordException {
        //check if user exists in the DB
        Optional<User> loggedInUser = userRepository.findByEmail(userToLogin.getEmail());
        if(loggedInUser.isPresent()) {
            //validate the password
            String dbPassword = loggedInUser.get().getPassword();
            if(!bCryptPasswordEncoder.matches(userToLogin.getPassword(), dbPassword)){
                throw new WrongPasswordException("Password is wrong!");
            }
            else {
                String jwtToken = createJWTToken(loggedInUser.get().getId(), loggedInUser.get().getEmail());
                return jwtToken;
            }
        }else {
            throw new UserDoesNotExistException("User does not exist!");
        }
    }

    public String createJWTToken(long userId, String email) {
        Map<String, Object> headerMap = new HashMap<String, Object>();
        headerMap.put("userID", userId);
        headerMap.put("email", email);
        headerMap.put("role", "USER");

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 30);
        Date tokenExpirationDate = calendar.getTime();

        String jwtToken = Jwts.builder()
                .claims(headerMap)
                .expiration(tokenExpirationDate)
                .issuedAt(new Date())
                .issuer("Eshwar_Dev")
                .signWith(key)
                .compact();

        return jwtToken;
    }
}
