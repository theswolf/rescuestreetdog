package core.september.rescue.service;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import core.september.rescue.model.User;
import core.september.rescue.repo.UserRepo;



@Service
public class UserService {

	@Autowired
	private UserRepo userRepo;
	@Autowired
	private PasswordService passwordService;
	private static final String EMAIL_PATTERN = 
			"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	 
	private Pattern pattern = Pattern.compile(EMAIL_PATTERN);
	
	public boolean exists(String mail) {
		return  userRepo.findOne(mail) != null;
	}
	
	public User find(String username) {
		return userRepo.findOne(username);
	}
	
	public boolean validateMail(String mail) {
		return pattern.matcher(mail).matches();
	}
	
	public User saveUser(String username,String password) throws Exception {
		User user = new User();
		user.setUsername(username);
		user.setHashedPassword(passwordService.createHash(password));
		return userRepo.save(user);
		//return user;
	}
	
	public UserRepo getRepo() {
		return userRepo;
	}
}
