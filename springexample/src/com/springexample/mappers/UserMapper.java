package com.springexample.mappers;

import org.apache.ibatis.annotations.Insert;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.springexample.model.Signup;

public interface UserMapper {

	@Insert("INSERT INTO userdetails(user_name,user_email," + "user_address,user_password) VALUES" + "(#{user_name},"
			+ "#{user_email}, #{user_address},#{user_password})")

	public void insertUser(Signup signup);

	@Select("SELECT user_name,user_password, " + "user_address,user_email  "
			+ "FROM userdetails WHERE  user_email = #{ user_email}")
	public Signup getUserByUserEmail(String user_email);

	@Update("update userdetails set resetToken=#{resetToken} where user_email = #{ user_email} ")
	public void insertToken(Signup signup);

	@Select("select * from userdetails where resetToken=#{resetToken} ")
	public Signup findUserByResetToken(String resetToken);

	@Update("update userdetails set user_password=#{user_password} where resetToken = #{resetToken} ")
	public void updatePassword(Signup signup);
}
