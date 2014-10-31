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
	
	

	
	protected String preCheck(ServletRequest req, ServletResponse resp) throws ServletException { 
	 	String token = ((HttpServletRequest)req).getHeader("Auth");
	    
	    try {
			String payload = jwtService.decode(token);
			((HttpServletResponse)resp).setHeader("payload", payload.toString());
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			((HttpServletResponse)resp).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			throw new ServletException(e);
		}
	    
	    return token;
		
	}
	
	protected void doFilterGET(ServletRequest req, ServletResponse resp) throws ServletException { 
	 	preCheck(req, resp);	
		
	}
	
	protected void doFilterPOST(ServletRequest req, ServletResponse resp) throws ServletException {
		doFilterGET(req, resp);
	}
	
	protected void doFilterPUT(ServletRequest req, ServletResponse resp) throws ServletException {
		doFilterGET(req, resp);
	}
	
	protected void doFilterPATCH(ServletRequest req, ServletResponse resp) throws ServletException {
		doFilterGET(req, resp);
	}
	
	protected void doFilterDELETE(ServletRequest req, ServletResponse resp) throws ServletException {
		doFilterGET(req, resp);
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		
		if(request.getMethod().equals("GET")) { 
	    	doFilterGET(req,resp);
	    }
	    
	    else if(request.getMethod().equals("POST")) { 
	    	doFilterPOST(req,resp);
	    }
	    
	    else if(request.getMethod().equals("PATCH")) { 
	    	doFilterPATCH(req,resp);
	    }
	    
	    else if(request.getMethod().equals("PUT")) { 
	    	doFilterPUT(req,resp);
	    }
	    
	    else if(request.getMethod().equals("DELETE")) { 
	    	doFilterDELETE(req,resp);
	    }
		
	    chain.doFilter(req, resp);
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}
	
}

