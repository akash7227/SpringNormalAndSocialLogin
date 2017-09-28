package com.springexample.service;

import com.springexample.model.Signup;

public interface UserService {
	void insertUser(Signup signup);

	Signup getUserByUserEmail(String user_email);

	Signup getUserByLogin(String user_email, String user_password);

	void saveToken(Signup signup);

	Signup findUserByResetToken(String resetToken);

/*	void setPassword(String password,String token);
*/	void setPassword(Signup signup);

}
