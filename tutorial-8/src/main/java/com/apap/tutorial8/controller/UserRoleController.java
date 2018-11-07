package com.apap.tutorial8.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.ui.Model;

import com.apap.tutorial8.model.UserRoleModel;
import com.apap.tutorial8.service.UserRoleService;

@Controller
@RequestMapping("/user")
public class UserRoleController {
	@Autowired
	private UserRoleService userService;
	
	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	private String addUserSubmit(@ModelAttribute UserRoleModel user, Model model) {
		if(user.getPassword().length() < 8 ) {
			model.addAttribute("return","password tidak boleh kurang dari 8 karakter");
			return "home";
		}else {
			if(user.getPassword().matches(".*[a-zA-Z].*") && user.getPassword().matches(".*[0-9].*")) {
				userService.addUser(user);
				return "add-user";
			}else {
				model.addAttribute("return", "password harus mengandung angka dan huruf");
				return "home";
			}
		}
	}
	
	@RequestMapping(value = "/updatePassword/{username}", method = RequestMethod.GET)
	private String updatePassword(@PathVariable(value="username") String username, Model model) {
		UserRoleModel user = userService.getUser(username);
		model.addAttribute("user", user);
		model.addAttribute("username", username);
		model.addAttribute("msg", "");
		return "updatePassword";
	}
	
	@RequestMapping(value = "/updatePassword/{username}", method = RequestMethod.POST)
    private String updatePasswordSubmit(@PathVariable(value="username") String username, String passLama, String passBaru, String konfirmPass, Model model) {
		UserRoleModel user = userService.getUser(username);
		if (passBaru.length() >= 8 && passBaru.matches(".*[a-zA-Z].*") && passBaru.matches(".*[0-9].*")) {
			if (passBaru.equals(konfirmPass) == false) {
				model.addAttribute("return", "konfirmasi password tidak sama");
				return "updatePassword";
			} else {
				boolean passwordValid = userService.validatePassword(user.getPassword(), passLama);
				if (passwordValid  == true) {
					userService.updatePassword(username, passBaru);
					return "success-update";
				} else {
					model.addAttribute("return", "password awal tidak sesuai");
					return "updatePassword";	
				}
			}
		} else {
			model.addAttribute("return", "Password harus mengandung angka dan huruf serta tidak kurang dari 8 karakter");
			return "updatePassword";
		}
		
		
    }
}
