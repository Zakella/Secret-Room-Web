package com.example.secretroom.authentication.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ApplicationPasswordEncoder extends BCryptPasswordEncoder {
}
