package com.evanesce.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.evanesce.entity.User;
import com.evanesce.repository.UserDao;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	// Insert a new user into the database
	@Override
	public User insertUser(User user) {
		return userDao.save(user);
	}

	// Retrieve user by email and password (for login)
	@Override
	public List<User> getUserByEmailAndPassword(String email, String password) {
		return userDao.findByEmailAndPassword(email, password);
	}

	// Retrieve users by phone number
	@Override
	public List<User> findByPhone(String phone) {
		return userDao.findByPhone(phone);
	}

	// Admin functionality: Retrieve all users
	@Override
	public List<User> getAllUsers() {
		return userDao.findAll();
	}

	// Admin functionality: Delete user by email
	@Override
	public void deleteUser(String email) {
		userDao.findById(email).ifPresentOrElse(
				userDao::delete,
				() -> { throw new RuntimeException("User not found with email: " + email); }
		);
	}

	// Retrieve users by email (for password recovery)
	@Override
	public List<User> findByEmail(String email) {
		return userDao.findByEmail(email);
	}

	// Validate security question and answer for password recovery
	@Override
	public List<User> forgetPassword(String email, String securityQues, String securityAns) {
		return userDao.findByEmailAndSecurityQuesAndSecurityAns(email, securityQues, securityAns);
	}

	// Update the user's password
	@Override
	public User updatePassword(User user) {
		return userDao.findById(user.getEmail())
				.map(existingUser -> {
					existingUser.setPassword(user.getPassword());
					return userDao.save(existingUser);
				})
				.orElseThrow(() -> new RuntimeException("User not found with email: " + user.getEmail()));
	}
}
