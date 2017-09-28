package com.springexample.model;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class Signup {

	@NotEmpty(message = "user name can not be empty")
	@Pattern(regexp = ("^[A-Za-z]*$"), message = "name must contain letter.")
	private String user_name;

	@NotEmpty(message = "Email can not be empty")
	@Email
	private String user_email;

	@NotEmpty(message = "address can not be empty")
	private String user_address;

	@NotEmpty(message = "password can not be empty")
	@Size(min = 6, max = 15, message = "Your password must between 6 and 15 characters")
	private String user_password;
	
	private String resetToken;

	public String getResetToken() {
		return resetToken;
	}

	public void setResetToken(String resetToken) {
		this.resetToken = resetToken;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getUser_email() {
		return user_email;
	}

	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}

	public String getUser_address() {
		return user_address;
	}

	public void setUser_address(String user_address) {
		this.user_address = user_address;
	}

	public String getUser_password() {
		return user_password;
	}

	public void setUser_password(String user_password) {
		this.user_password = user_password;
	}

	@Override
	public String toString() {
		return "Signup [user_name=" + user_name + ", user_email=" + user_email + ", user_address=" + user_address
				+ ", user_password=" + user_password + "]";
	}

}