package com.apap.tutorial8.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.apap.tutorial8.model.UserRoleModel;
import com.apap.tutorial8.repository.UserRoleDb;

@Service
public class UserRoleServiceImpl implements UserRoleService{
	@Autowired
	private UserRoleDb userDb;
	
	@Override
	public UserRoleModel addUser(UserRoleModel user) {
		// TODO Auto-generated method stub
		String pass = encrypt(user.getPassword());
		user.setPassword(pass);
		return userDb.save(user);
	}

	@Override
	public String encrypt(String password) {
		// TODO Auto-generated method stub
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(password);
		return hashedPassword;
	}

	@Override
	public UserRoleModel getUser(String username) {
		// TODO Auto-generated method stub
		return userDb.findByUsername(username);
	}

	@Override
	public Boolean validatePassword(String passLama, String passLamaCek) {
		// TODO Auto-generated method stub
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		return passwordEncoder.matches(passLamaCek, passLama);
	}

	@Override
	public void updatePassword(String username, String passBaru) {
		// TODO Auto-generated method stub
		UserRoleModel user = userDb.findByUsername(username);
		String passwordBaru = encrypt(passBaru);
		user.setPassword(passwordBaru);
		userDb.save(user);
		
	}

}
