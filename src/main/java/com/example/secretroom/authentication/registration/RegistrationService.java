package com.example.secretroom.authentication.registration;

import com.example.secretroom.authentication.user.ApplicationUser;
import com.example.secretroom.authentication.user.ApplicationUserRole;
import com.example.secretroom.authentication.user.ApplicationUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static com.example.secretroom.authentication.user.ApplicationUserRole.USER;

@AllArgsConstructor
@Service
public class RegistrationService {

    private final EmailValidator emailValidator;
    private final ApplicationUserService applicationUserService;

    public ResponseEntity<String>  register(RegistrationRequest request) {

        emailValidator.validate(request.getEmail());

        return applicationUserService.signUpUser(
                new ApplicationUser(
                        request.getFirstName(),
                        request.getLastName(),
                        request.getEmail(),
                        request.getPassword(),
                        USER

                )
        );
    }
}
