package com.springexample.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springexample.mappers.UserMapper;
import com.springexample.model.Signup;

@Service("userService")
public class UserServiceImp implements UserService {
	@Autowired
	private UserMapper userMapper;

	@Transactional
	public void insertUser(Signup signup) {
		System.out.println("dekh be"+signup);
		userMapper.insertUser(signup);

	}

	public Signup getUserByLogin(String user_email, String user_password) {
		System.out.println("e is  " + user_email + "p is " + user_password);
		Signup signup = userMapper.getUserByUserEmail(user_email);
		System.out.println("signup is " + signup);

		if (signup != null && signup.getUser_password().equals(user_password)) {
			return signup;
		}

		return null;
	}

	public Signup getUserByUserEmail(String user_email) {
		Signup signup = userMapper.getUserByUserEmail(user_email);

		if (signup != null) {
			return signup;
		}

		return signup;
	}

	public void saveToken(Signup signup) {
		userMapper.insertToken(signup);

	}

	@Override
	public Signup findUserByResetToken(String resetToken) {
		// TODO Auto-generated method stub
		Signup signup = userMapper.findUserByResetToken(resetToken);
		return signup;
	}

	@Override
	public void setPassword(Signup signup) {
		// TODO Auto-generated method stub
		System.out.println("setpassword");
		userMapper.updatePassword(signup);
		// System.out.println("kklj"+signup);

	}

}
