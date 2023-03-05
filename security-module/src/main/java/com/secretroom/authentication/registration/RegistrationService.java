package com.secretroom.authentication.registration;

import com.secretroom.authentication.user.ApplicationUser;
import com.secretroom.authentication.user.ApplicationUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static com.secretroom.authentication.user.ApplicationUserRole.USER;

@AllArgsConstructor
@Service
public class RegistrationService {

    private final EmailValidator emailValidator;
    private final ApplicationUserService applicationUserService;

    public ResponseEntity<String>  register(RegistrationRequest request) {

        emailValidator.validate(request.email());

        return applicationUserService.signUpUser(
                new ApplicationUser(
                        request.firstName(),
                        request.lastName(),
                        request.email(),
                        request.password(),
                        USER

                )
        );
    }
}