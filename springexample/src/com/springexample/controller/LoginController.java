package com.springexample.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.springexample.model.Login;
import com.springexample.model.Signup;
import com.springexample.service.UserService;

@Controller
/* @RequestMapping("/sp") */
public class LoginController {
	/*
	 * @Autowired // @Qualifier("myUserService") UserService userService;
	 * 
	 * @Autowired // @Qualifier("userService2")
	 * com.springexample.controller.UserService userService2;
	 */

	/*
	 * @RequestMapping("/test") public ModelAndView testMethod(HttpServletRequest
	 * request, HttpServletResponse response ) throws Exception {
	 * userService.print(); userService2.test(); System.out.println("test");
	 * //return "test"; return new ModelAndView("test"); }
	 */

	@Autowired
	private UserService userService;

	@RequestMapping("/")
	public ModelAndView start() {
		// userService.print();
		// userService2.test();
		ModelAndView model = new ModelAndView("login");
		Login Userlogin = new Login();
		model.addObject("login", Userlogin);
		return model;
	}

	@RequestMapping(value = "/SaveLogin", method = RequestMethod.POST)
	public ModelAndView login(@Valid Login login, BindingResult result, ModelAndView model, HttpSession session) {
		System.out.println(login);
		if (result.hasErrors()) {

			model = new ModelAndView("login");
			return model;

		} else {

			Signup found = userService.getUserByLogin(login.getUser_email(), login.getUser_password());

			if (found != null) {

				model = new ModelAndView("test");
				session.setAttribute("found", found);
				// model.addObject("found", found);

			} else {

				model = new ModelAndView("login");
				model.addObject("error", "sorry your email or password is wrong");
			}

		}

		return model;

	}

	@RequestMapping(value = "/SaveLogin")
	public ModelAndView redirect(ModelAndView model) {
		model = new ModelAndView("redirect:/");
		/*
		 * Login Userlogin = new Login(); model.addObject("login", Userlogin);
		 */
		return model;

	}

	@RequestMapping(value = "/logout")
	public ModelAndView logout(HttpServletRequest request, ModelAndView model) {

		HttpSession httpSession = request.getSession();
		httpSession.invalidate();
		model = new ModelAndView("redirect:/");
		return model;
	}

}
