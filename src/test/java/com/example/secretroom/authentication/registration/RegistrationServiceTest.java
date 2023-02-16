package com.example.secretroom.authentication.registration;

import com.example.secretroom.authentication.user.ApplicationUserService;
import org.apache.coyote.Request;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceTest {

    private RegistrationService underTest;
    private EmailValidator emailValidator;
    private ApplicationUserService applicationUserService;

    @BeforeEach
    void setUp() {
        underTest = new RegistrationService(emailValidator, applicationUserService);
    }

    @Test
    void itShouldRegister() {
        //Actual
        RegistrationRequest request = new RegistrationRequest(
                "Zapolschi","Slava", "zap.slava@gmail.com", "123456"
        );

        //expected


        //When
//        underTest.register()
        //Then

    }
}