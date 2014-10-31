package core.september.rescue.repo.event;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeDelete;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;

import core.september.rescue.model.User;

@RepositoryEventHandler(User.class)
public class UserEventHandler {
	
	@Autowired
	HttpServletRequest request;

	@HandleBeforeCreate
	public void preventCreation(User user) throws Exception {
		throw new Exception("User should be created by signup workflow");
	}
	
	@HandleBeforeSave @HandleBeforeDelete
	public void handleBefore(User user) throws Exception {
		User currentUser = (User) request.getAttribute("CurrentUser");
		if(!currentUser.getId().equalsIgnoreCase(user.getId())) {
			throw new Exception("Can change or delete others user");
		}
	}
}
