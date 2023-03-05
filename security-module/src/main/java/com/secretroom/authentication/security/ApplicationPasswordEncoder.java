package com.secretroom.authentication.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class ApplicationPasswordEncoder extends BCryptPasswordEncoder {
}
