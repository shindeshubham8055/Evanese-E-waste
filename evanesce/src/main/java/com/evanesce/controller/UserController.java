package com.evanesce.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;  // Import ResponseEntity
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

@CrossOrigin
@RestController
public class UserController {

	@Autowired
	private UserService userService;

	// Endpoint to handle user login based on email and password
	@PostMapping("/login")
	public ResponseEntity<List<User>> getUserByEmailAndPassword(@RequestBody User user) {
		System.out.println("\n@PostMapping(\"/login\")");
		System.out.println("List<User> getUserByEmailAndPassword(@RequestBody User user)");

		List<User> users = userService.getUserByEmailAndPassword(user.getEmail(), user.getPassword());

		// If no users found, return 404 NOT FOUND response
		if (users.isEmpty()) {
			return ResponseEntity.notFound().build();
		} else {
			// If users found, return a 200 OK response with the list of users
			return ResponseEntity.ok(users);
		}
	}

	// Endpoint for user registration
	@PostMapping("/register")
	public ResponseEntity<User> insertUser(@RequestBody User user) {
		System.out.println("\n@PostMapping(\"/register\")");
		System.out.println("public User insertUser(@RequestBody User user)");

		// Create a new user and return a 201 CREATED response
		User createdUser = userService.insertUser(user);
		return ResponseEntity.status(201).body(createdUser);
	}

	// Endpoint for Forgot Password (Step 1) - Find user by email
	@PostMapping("/findbyemail")
	public ResponseEntity<List<User>> findByEmail(@RequestBody User user) {
		System.out.println("\n@PostMapping(\"/findbyemail\")");
		System.out.println("List<User> findByEmail(@RequestBody User user)");

		// Retrieve users by email for password reset
		List<User> users = userService.findByEmail(user.getEmail());
		return ResponseEntity.ok(users);
	}

	// Endpoint for Forgot Password (Step 2) - Verify security question and answer
	@PostMapping("/forget")
	public ResponseEntity<List<User>> forgetPassword(@RequestBody User user) {
		System.out.println("\n@PostMapping(\"/forget\")");
		System.out.println("List<User> forgetPassword(@RequestBody User user)");

		// Verify security question and answer for password reset
		List<User> users = userService.forgetPassword(user.getEmail(), user.getSecurityQues(), user.getSecurityAns());
		return ResponseEntity.ok(users);
	}

	// Endpoint for Forgot Password (Step 3) - Update the password
	@PutMapping("/updatepassword")
	public ResponseEntity<User> updatePassword(@RequestBody User user) {
		System.out.println("\n@PutMapping(\"/updatepassword\") ");
		System.out.println("User updatePassword(@RequestBody User user)");

		// Update the user's password and return the updated user information
		User updatedUser = userService.updatePassword(user);
		return ResponseEntity.ok(updatedUser);
	}

	// Admin Endpoint to delete a user by email
	@DeleteMapping("deleteuser/{uemail}")
	public ResponseEntity<String> deleteUser(@PathVariable String uemail) {
		System.out.println("\n@DeleteMapping(\"deleteuser/{uemail}\")");
		System.out.println("String deleteUser(@PathVariable String uemail)");

		// Delete user by email
		userService.deleteUser(uemail);
		return ResponseEntity.ok("Deleted");
	}

	// Admin Endpoint to get all users
	@GetMapping("/getallusers")
	public ResponseEntity<List<User>> getAllUsers() {
		System.out.println("\n@GetMapping(\"/getallusers\")");
		System.out.println("List<User> getAllUsers() ");

		// Retrieve and return all users
		List<User> users = userService.getAllUsers();
		return ResponseEntity.ok(users);
	}
}
