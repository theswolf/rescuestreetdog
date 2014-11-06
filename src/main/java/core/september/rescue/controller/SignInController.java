package core.september.rescue.controller;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import core.september.rescue.model.User;
import core.september.rescue.service.JWTService;
import core.september.rescue.service.PasswordService;
import core.september.rescue.service.UserService;



@RestController
public class SignInController {
	
	@Autowired
	private JWTService jwtService;
	@Autowired
	private UserService userService;
	@Autowired
	private PasswordService passService;

	public static class LoginRequest{
		private String username;
		private String password;
		
//		public LoginRequest() {}
//		
//		public LoginRequest(String username, String password) {
//			this.username = username;
//			this.password = password;
//		}
		
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
		
		
	}
	
	
	
	@RequestMapping(value = "verify", method = RequestMethod.POST)
	@ResponseBody
	public String verify(@RequestBody String token) throws Exception {
		Logger.getLogger(this.getClass()).debug(token);
		String req = jwtService.decode(token);
		return req;
	}
	
	@RequestMapping(value = "login", method = RequestMethod.POST)
    @ResponseBody
	public String signIn(@RequestBody LoginRequest request) throws Exception {
	   
		String username = request.getUsername();
		String password = request.getPassword();
		
		User user = userService.find(username);
		if(user == null) {
			throw new Exception("Invalid credentials!!!");
		}
		
		Boolean logged = passService.validatePassword(password, user.getHashedPassword());
		if(!logged) {
			throw new Exception("Invalid credentials!!!");
		} 
		
		String token = jwtService.encode(user.getId(), 2);
	    return token;
		
	}
}
