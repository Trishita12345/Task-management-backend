package com.example.auth;

import com.example.auth.model.dto.auth.RegisterRequestDto;
import com.example.auth.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AuthApplication implements CommandLineRunner {

	@Value("${admin.username}")
	private String username;

	@Value("${admin.password}")
	private String password;

	@Autowired
	private IEmployeeService employeeService;

	public static void main(String[] args) throws Exception {
		SpringApplication.run(AuthApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		RegisterRequestDto requestDto = RegisterRequestDto.builder()
				.email(username)
				.password(password)
				.firstname("ADMIN")
				.lastname("USER")
				.build();
		employeeService.saveAdmin(requestDto);
	}
}
