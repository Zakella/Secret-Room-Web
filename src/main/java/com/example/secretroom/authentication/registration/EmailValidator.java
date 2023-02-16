package com.example.secretroom.authentication.registration;

import com.example.secretroom.utils.PatternMatcher;
import org.springframework.stereotype.Service;

import java.util.function.Predicate;
import java.util.regex.Pattern;

@Service
public class EmailValidator  {

    private PatternMatcher patternMatcher;
    public void validate(String email) {
        if (email == null){
            throw new IllegalStateException("email can be null!");
        }

        String isValidEmail = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

        if (PatternMatcher.patternMatches(email, isValidEmail)) {
            throw new IllegalStateException(
                    String.format(String.format("invalid email %s is valid!", email)));
        }

    }


}
