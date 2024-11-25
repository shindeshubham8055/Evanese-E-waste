package com.evanesce.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.evanesce.entity.User;
import com.evanesce.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@CrossOrigin
@RestController
public class UserController {

	@Autowired
	private UserService userService;

	// Initialize Logger
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	// Endpoint to handle user login based on email and password
	@PostMapping("/login")
	public ResponseEntity<List<User>> getUserByEmailAndPassword(@RequestBody User user) {
		logger.info("Login attempt for email: {}", user.getEmail());

		List<User> users = userService.getUserByEmailAndPassword(user.getEmail(), user.getPassword());

		if (users.isEmpty()) {
			logger.warn("No user found with email: {}", user.getEmail());
			return ResponseEntity.notFound().build();
		} else {
			logger.info("User found and logged in successfully.");
			return ResponseEntity.ok(users);
		}
	}

	// Endpoint for user registration
	@PostMapping("/register")
	public ResponseEntity<User> insertUser(@RequestBody User user) {
		logger.info("Registering new user with email: {}", user.getEmail());

		User createdUser = userService.insertUser(user);
		logger.info("User registered successfully: {}", createdUser.getEmail());
		return ResponseEntity.status(201).body(createdUser);
	}

	// Endpoint for Forgot Password (Step 1) - Find user by email
	@PostMapping("/findbyemail")
	public ResponseEntity<List<User>> findByEmail(@RequestBody User user) {
		logger.info("Finding user by email: {}", user.getEmail());

		List<User> users = userService.findByEmail(user.getEmail());
		return ResponseEntity.ok(users);
	}

	// Endpoint for Forgot Password (Step 2) - Verify security question and answer
	@PostMapping("/forget")
	public ResponseEntity<List<User>> forgetPassword(@RequestBody User user) {
		logger.info("Verifying security question for email: {}", user.getEmail());

		List<User> users = userService.forgetPassword(user.getEmail(), user.getSecurityQues(), user.getSecurityAns());
		return ResponseEntity.ok(users);
	}

	// Endpoint for Forgot Password (Step 3) - Update the password
	@PutMapping("/updatepassword")
	public ResponseEntity<User> updatePassword(@RequestBody User user) {
		logger.info("Updating password for email: {}", user.getEmail());

		User updatedUser = userService.updatePassword(user);
		logger.info("Password updated successfully for email: {}", user.getEmail());
		return ResponseEntity.ok(updatedUser);
	}

	// Admin Endpoint to delete a user by email
	@DeleteMapping("deleteuser/{uemail}")
	public ResponseEntity<String> deleteUser(@PathVariable String uemail) {
		logger.info("Deleting user with email: {}", uemail);

		userService.deleteUser(uemail);
		logger.info("User deleted successfully: {}", uemail);
		return ResponseEntity.ok("Deleted");
	}

	// Admin Endpoint to get all users
	@GetMapping("/getallusers")
	public ResponseEntity<List<User>> getAllUsers() {
		logger.info("Fetching all users.");

		List<User> users = userService.getAllUsers();
		return ResponseEntity.ok(users);
	}
}
