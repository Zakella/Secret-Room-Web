package com.secretroom.customer.registration;

public record CustomerRegistrationRequest(String firstname, String lastName, String email, String phone) {
}