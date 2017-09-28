package com.springexample.controller;

import java.util.Map;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.springexample.model.Signup;
import com.springexample.service.EmailService;
import com.springexample.service.UserService;

@Controller
public class PasswordController {

	@Autowired
	private UserService userService;

	@Autowired
	private EmailService emailService;

	/*
	 * @Autowired private BCryptPasswordEncoder bCryptPasswordEncoder;
	 */

	@RequestMapping("/forgot")
	public ModelAndView displayForgotPasswordPage() {
		return new ModelAndView("forgotPassword");
	}

	@RequestMapping(value = "/forgot", method = RequestMethod.POST)
	public ModelAndView processForgotPasswordForm(ModelAndView modelAndView,
			@RequestParam("user_email") String user_email, HttpServletRequest request, HttpServletResponse response) {

		System.out.println("check");
		// Lookup user in database by e-mail
		Signup found = userService.getUserByUserEmail(user_email);
		System.out.println("found" + found);

		if (found == null) {
			modelAndView.addObject("errorMessage", "We didn't find an account for that e-mail address.");
		} else {

			// Generate random 36-character string token for reset password

			found.setResetToken(UUID.randomUUID().toString());

			// Save token to database
			userService.saveToken(found);

			String appUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
					+ request.getServletContext().getContextPath();
			System.out.println("appurl" + appUrl);

			System.out.println(request.getServletContext().getContextPath()); /// springexample

			// Email message
			SimpleMailMessage passwordResetEmail = new SimpleMailMessage();
			passwordResetEmail.setFrom("akashkumarsingh57@gmail.com");
			passwordResetEmail.setTo(found.getUser_email());
			passwordResetEmail.setSubject("Password Reset Request");
			passwordResetEmail.setText("To reset your password, click the link below:\n" + appUrl + "/reset?resetToken="
					+ found.getResetToken());

			emailService.sendEmail(passwordResetEmail);

			// Add success message to view
			modelAndView.addObject("successMessage", "A password reset link has been sent to " + user_email);
		}

		modelAndView.setViewName("forgotPassword");
		return modelAndView;

	}

	// Display form to reset password

	@RequestMapping("/reset")
	public ModelAndView displayResetPasswordPage(ModelAndView modelAndView,
			@RequestParam("resetToken") String resetToken) {
		System.out.println("resettoken is " + resetToken);
		Signup user = userService.findUserByResetToken(resetToken);

		System.out.println("user" + user);
		if (user != null) { // Token found in DB
			modelAndView.addObject("resetToken", resetToken);

		} else {
			// Token not found in
			modelAndView.addObject("errorMessage", "Oops!  This is an invalid password reset link.");
		}

		modelAndView.setViewName("resetPassword");
		return modelAndView;
	}

	// Process reset password form

	@RequestMapping(value = "/reset", method = RequestMethod.POST)
	public ModelAndView setNewPassword(@RequestParam Map<String, String> requestParams, ModelAndView modelAndView,
			Signup signup) {

		String token = requestParams.get("resetToken");
		/*
		 * System.out.println("working"+ req.getURI());
		 * System.out.println("token is "+requestParams.getBytes());
		 */
		// System.out.println("token is "+requestParams.get("resetToken"));
		// Find the user associated with the reset token
		Signup user = userService.findUserByResetToken(token);

		// This should always be non-null but we check just in case

		if (user != null) {
			System.out.println("new password " + requestParams.get("newPassword"));
			System.out.println("user" + user);
			String password = requestParams.get("newPassword");
			// Set new password
			/*
			 * resetUser.setPassword(bCryptPasswordEncoder.encode(requestParams.get(
			 * "newPassword")));
			 */

			user.setUser_password(password);
			user.setResetToken(token);
			userService.setPassword(user);
			// user.setResetToken(token);
			// userService.setPassword(password, token);
			// Set the reset token to null so it cannot be used again
			user.setResetToken("");
			System.out.println("last" + user);
			userService.saveToken(user);

			// Save user userService.saveUser(resetUser);

			// In order to set a model attribute on a redirect, we must use //

			// modelAndView.setViewName("redirect:/login");

		} else {
			modelAndView.addObject("errorMessage", "Oops!  This is an invalid password reset link.");
			modelAndView.setViewName("resetPassword");
		}

		return modelAndView;
	}

	// Going to reset page without a token redirects to login page

	/*
	 * @ExceptionHandler(MissingServletRequestParameterException.class) public
	 * ModelAndView handleMissingParams(MissingServletRequestParameterException ex)
	 * { return new ModelAndView("redirect:login"); }
	 */

}
