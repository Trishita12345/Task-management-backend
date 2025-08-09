package com.example.auth;

import com.example.auth.constants.Constants;
import com.example.auth.model.Permission;
import com.example.auth.model.Role;
import com.example.auth.model.dto.auth.RegisterRequestDto;
import com.example.auth.repository.IPermissionRepository;
import com.example.auth.repository.IRoleRepository;
import com.example.auth.service.IEmployeeService;
import com.example.auth.service.ISeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.stream.Stream;

@SpringBootApplication
public class AuthApplication implements CommandLineRunner {

    @Autowired
    private ISeedService service;

	public static void main(String[] args) throws Exception {
		SpringApplication.run(AuthApplication.class, args);
	}

	@Override
	public void run(String... args) {
        service.setupInitialData();
    }

}
