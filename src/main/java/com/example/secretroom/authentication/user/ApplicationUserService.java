package com.example.secretroom.authentication.user;

import com.example.secretroom.authentication.security.ApplicationPasswordEncoder;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class ApplicationUserService implements UserDetailsService {

    private final static String USER_NOT_FOUND_MSG =
            "user with email %s not found";

    private final ApplicationUserRepository applicationUserRepository;

    private final ApplicationPasswordEncoder passwordEncoder;



    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {


        return applicationUserRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                String.format(USER_NOT_FOUND_MSG, email)));
    }


    public ResponseEntity<String> signUpUser(ApplicationUser appUser) {
        boolean userExists = applicationUserRepository
                .findByEmail(appUser.getEmail())
                .isPresent();

        if (userExists) {
            // TODO check of attributes are the same and
            // TODO if email not confirmed send confirmation email.

//            throw new IllegalStateException("email already taken");
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User with email already exists");
        }

        String encodedPassword = passwordEncoder
                .encode(appUser.getPassword());

        appUser.setPassword(encodedPassword);

        applicationUserRepository.save(appUser);

        return  ResponseEntity.ok("User successfully signed up");
    }
}
