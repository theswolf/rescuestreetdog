package core.september.rescue.model;

import java.util.Collection;

//@Document
public class Profile extends BaseEntity {

	
	private String description;
	private String nickName;
	private Collection<String> socialUrls;
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public Collection<String> getSocialUrls() {
		return socialUrls;
	}
	public void setSocialUrls(Collection<String> socialUrl) {
		this.socialUrls = socialUrl;
	}
	
	
	
	
	
}
