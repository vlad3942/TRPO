package ru.ssau.delivery.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
@CrossOrigin(origins = "*", maxAge = 3600)
public class TestController {

	@GetMapping("/all")
	public String allAccess() {
		return "public API";
	}
	
	@GetMapping("/user")
	@PreAuthorize("hasRole('USER') or hasRole('COURIER') or hasRole('REST') or hasRole('ADMIN')")
	public String userAccess() {
		return "user API";
	}
	
	@GetMapping("/cour")
	@PreAuthorize("hasRole('COURIER') or hasRole('REST') or hasRole('ADMIN')")
	public String courierAccess() {
		return "courier API";
	}

	@GetMapping("/rest")
	@PreAuthorize("hasRole('REST') or hasRole('ADMIN')")
	public String restaurantAccess() {
		return "restaurant API";
	}

	@GetMapping("/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public String adminAccess() {
		return "admin API";
	}
}
