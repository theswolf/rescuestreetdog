package core.september.rescue.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import core.september.rescue.model.User;
import core.september.rescue.service.JWTService;
import core.september.rescue.service.UserService;


@RestController
@RequestMapping("signup")
public class SignUpController {

	@Autowired
	private UserService userService;
	@Autowired
	private JWTService jwtService;
	
	public static class SignUpParms{
		private String username;
		private String password;
		private String confirmPassword;
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public String getConfirmPassword() {
			return confirmPassword;
		}
		public void setConfirmPassword(String confirmPassword) {
			this.confirmPassword = confirmPassword;
		}
		
	}
	
	@RequestMapping(value = "me", method = RequestMethod.POST)
    @ResponseBody
	public String signUp(@RequestBody SignUpParms params) throws Exception {
		if(!params.confirmPassword.equals(params.password)) {
			throw new Exception("Password not match");
		}
		if(!userService.validateMail(params.username)) {
			throw new Exception("Username must be a valid mail address");
		}
		if(userService.exists(params.username)) {
			throw new Exception("User already registered");
		}
		
		User user = userService.saveUser(params.username, params.password);
		String token = jwtService.encode(user.getId(), 1);
		return token;
	}
	
}
