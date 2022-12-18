package ru.ssau.delivery.pojo;

import lombok.Data;

import java.util.Set;

@Data
public class SignupRequest {
	
	private String username;
	private String email;
	private String phone;
	private Set<String> roles;
	private String password;
	
}
