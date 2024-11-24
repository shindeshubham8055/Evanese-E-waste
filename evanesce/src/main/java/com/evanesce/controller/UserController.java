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

	// Login
	@PostMapping("/login")
	public ResponseEntity<List<User>> getUserByEmailAndPassword(@RequestBody User user) {
		System.out.println("\n@PostMapping(\"/login\")");
		System.out.println("List<User> getUserByEmailAndPassword(@RequestBody User user)");

		List<User> users = userService.getUserByEmailAndPassword(user.getEmail(), user.getPassword());

		if (users.isEmpty()) {
			// If no users found, return a 404 NOT FOUND response
			return ResponseEntity.notFound().build();
		} else {
			// If users found, return a 200 OK response with the list of users
			return ResponseEntity.ok(users);
		}
	}

	// Register
	@PostMapping("/register")
	public ResponseEntity<User> insertUser(@RequestBody User user) {
		System.out.println("\n@PostMapping(\"/register\")");
		System.out.println("public User insertUser(@RequestBody User user)");

		User createdUser = userService.insertUser(user);

		// If the user is successfully created, return a 201 CREATED response
		return ResponseEntity.status(201).body(createdUser);
	}

	// Forget Password Mappings
	@PostMapping("/findbyemail") // For Forgot Password 1
	public ResponseEntity<List<User>> findByEmail(@RequestBody User user) {
		System.out.println("\n@PostMapping(\"/findbyemail\")");
		System.out.println("List<User> findByEmail(@RequestBody User user)");
		List<User> users = userService.findByEmail(user.getEmail());
		return ResponseEntity.ok(users);
	}

	@PostMapping("/forget") // For Forgot Password 2
	public ResponseEntity<List<User>> forgetPassword(@RequestBody User user) {
		System.out.println("\n@PostMapping(\"/forget\")");
		System.out.println("List<User> forgetPassword(@RequestBody User user)");
		List<User> users = userService.forgetPassword(user.getEmail(), user.getSecurityQues(), user.getSecurityAns());
		return ResponseEntity.ok(users);
	}

	@PutMapping("/updatepassword") // For Forgot Password 3
	public ResponseEntity<User> updatePassword(@RequestBody User user) {
		System.out.println("\n@PutMapping(\"/updatepassword\") ");
		System.out.println("User updatePassword(@RequestBody User user)");
		User updatedUser = userService.updatePassword(user);
		return ResponseEntity.ok(updatedUser);
	}

	// Admin Module - Delete User
	@DeleteMapping("deleteuser/{uemail}")
	public ResponseEntity<String> deleteUser(@PathVariable String uemail) {
		System.out.println("\n@DeleteMapping(\"deleteuser/{uemail}\")");
		System.out.println("String deleteUser(@PathVariable String uemail)");
		userService.deleteUser(uemail);
		return ResponseEntity.ok("Deleted");
	}

	// Admin Module - Get All Users
	@GetMapping("/getallusers")
	public ResponseEntity<List<User>> getAllUsers() {
		System.out.println("\n@GetMapping(\"/getallusers\")");
		System.out.println("List<User> getAllUsers() ");
		List<User> users = userService.getAllUsers();
		return ResponseEntity.ok(users);
	}
}
