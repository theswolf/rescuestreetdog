package core.september.rescue.model;

import java.util.Collection;
import java.util.LinkedList;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import com.mongodb.gridfs.GridFSDBFile;

//@Document
public class Profile extends BaseEntity{


	private String description;
	private Collection<SocialUrl> socialUrls;
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getUsername() {
		return getId();
	}
	public void setUsername(String username) {
		setId(username);
	}
	public Collection<SocialUrl> getSocialUrls() {
		return socialUrls;
	}
	public void setSocialUrls(Collection<SocialUrl> socialUrl) {
		this.socialUrls = socialUrl;
	}
	
	public void addSocialUrl(SocialUrl socialUrl) {
		if(getSocialUrls() == null) {
			setSocialUrls(new LinkedList<SocialUrl>());
		}
		getSocialUrls().add(socialUrl);
	}
	
	
	public static class SocialUrl {
		public enum Social{
			FACEBOOK,
			GOOGLE,
			TWITTER
		}
		
		private Social social;
		private String url;
		public Social getSocial() {
			return social;
		}
		public void setSocial(Social social) {
			this.social = social;
		}
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		
	}
	
	
	
	
}
