package core.september.rescue.model;

import org.springframework.data.mongodb.core.mapping.DBRef;




//@Document
public class User extends BaseEntity{
	
	private String username; //should be mail
	private String hashedPassword;
	
	@DBRef
	private Profile profile;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getHashedPassword() {
		return hashedPassword;
	}
	public void setHashedPassword(String hashedPassword) {
		this.hashedPassword = hashedPassword;
	}
	public Profile getProfile() {
		return profile;
	}
	public void setProfile(Profile profile) {
		this.profile = profile;
	}
	
	
	

}
