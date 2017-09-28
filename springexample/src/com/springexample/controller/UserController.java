package com.springexample.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.springexample.model.Signup;
import com.springexample.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping("/signup")
	public ModelAndView signup() {
		System.out.println("signup");
		ModelAndView model = new ModelAndView("signup");
		Signup Usersignup = new Signup();
		model.addObject("signup", Usersignup);
		return model;

	}

	@RequestMapping(value = "/saveUser", method = RequestMethod.POST)
	public String signup(@Valid @ModelAttribute("signup") Signup signup, BindingResult result) {

		if (result.hasErrors()) {
			System.out.println(result.getErrorCount());
			return "signup";
		}
		System.out.println("2nd");
		// ModelAndView model = new ModelAndView("login");
		// model.addObject("login", signup);
		System.out.println(signup);
		userService.insertUser(signup);
		return "redirect:/";
	}
	/*
	 * @ModelAttribute("signup") public Signup populateUser() { Signup Usersignup =
	 * new Signup(); Usersignup.setUser_name("akash"); return Usersignup; }
	 */

}
