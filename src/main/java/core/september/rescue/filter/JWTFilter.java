package core.september.rescue.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import core.september.rescue.service.JWTService;


public class JWTFilter implements Filter {

	protected final  JWTService jwtService;
	
	private static Log logger = LogFactory.getLog(JWTFilter.class);
	
	public JWTFilter(JWTService jwtService) {
		this.jwtService = jwtService;
	}
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException {

		
		 try {
			 	String token = ((HttpServletRequest)req).getHeader("Auth");
				String payload = jwtService.decode(token);
				((HttpServletRequest)req).setAttribute("payload", payload.replace("\"", ""));
				 chain.doFilter(req, resp);
				
			} 
		    
		    catch(IllegalStateException e) {
		    	((HttpServletResponse)resp).setStatus(HttpServletResponse.SC_CONFLICT);
		    	((HttpServletResponse)resp).sendError(HttpServletResponse.SC_CONFLICT,e.getMessage());
		    }
		    
		    catch (Exception e) {
				// TODO Auto-generated catch block
				((HttpServletResponse)resp).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				((HttpServletResponse)resp).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,e.getMessage());
			}
		    
		
	  
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}
	
}

