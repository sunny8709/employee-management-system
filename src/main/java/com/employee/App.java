package com.employee;

import com.employee.controller.MainMenu;
import com.employee.model.User;
import com.employee.repository.UserRepository;
import com.employee.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Scanner;

@SpringBootApplication
@RequiredArgsConstructor
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Bean
    public CommandLineRunner run(LoginService loginService, MainMenu mainMenu, UserRepository userRepository) {
        return args -> {
            createDefaultUser(userRepository);

            try (Scanner scanner = new Scanner(System.in)) {
                System.out.println("\n=== Employee Management & Payroll System ===");
                System.out.print("Enter username: ");
                String username = scanner.nextLine();
                System.out.print("Enter password: ");
                String password = scanner.nextLine();

                if (loginService.login(username, password)) {
                    System.out.println("\nLogin successful! Welcome " + username + "\n");
                    mainMenu.displayMenu();
                } else {
                    System.out.println("\nAuthentication failed. Exiting...");
                }
            }
        };
    }

    private void createDefaultUser(UserRepository userRepository) {
        if (!userRepository.existsByUsername("admin")) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword("admin123");
            admin.setRole("ADMIN");
            userRepository.save(admin);
            System.out.println("✓ Default admin user created (username: admin, password: admin123)");
        }
    }
}
