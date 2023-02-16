package com.example.secretroom.utils;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@AllArgsConstructor
@Component
public class PatternMatcher {

    public static boolean patternMatches(String value, String regexPattern) {
        return !Pattern.compile(regexPattern)
                .matcher(value)
                .matches();
    }

}
