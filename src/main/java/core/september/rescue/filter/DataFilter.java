package core.september.rescue.filter;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import core.september.rescue.model.User;
import core.september.rescue.service.JWTService;
import core.september.rescue.service.UserService;


public class DataFilter extends JWTFilter{
	
	protected UserService userService;
	
	public DataFilter(JWTService jwtService,UserService userService) {
		super(jwtService);
		this.userService = userService;
	}
	
	@Override
	protected void doFilterGET(ServletRequest req, ServletResponse resp) throws ServletException {
		
	}
	
	@Override
	protected void doFilterPOST(ServletRequest req, ServletResponse resp) throws ServletException {
		
		User user = userService.getRepo().findOne(preCheck(req, resp));
		req.setAttribute("CurrentUser", user);
	}
	
	@Override
	protected void doFilterDELETE(ServletRequest req, ServletResponse resp)
			throws ServletException {
		super.doFilterPOST(req, resp);
	}
	
	@Override
	protected void doFilterPATCH(ServletRequest req, ServletResponse resp)
			throws ServletException {
		super.doFilterPOST(req, resp);
	}
	
	@Override
	protected void doFilterPUT(ServletRequest req, ServletResponse resp)
			throws ServletException {
		super.doFilterPOST(req, resp);
	}
	
}
