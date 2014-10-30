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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.auth0.jwt.Algorithm;
import com.auth0.jwt.JwtProxy;
import com.auth0.jwt.impl.BasicPayloadHandler;
import com.auth0.jwt.impl.JwtProxyImpl;


public class JWTFilter implements Filter {

	private final  String secret;
	
	private static Log logger = LogFactory.getLog(JWTFilter.class);
	
	public JWTFilter(String secret) {
		this.secret = secret;
	}
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {

		
		logger.debug("The app key is: "+secret);
	    final Algorithm algorithm = Algorithm.HS256;
		
	    String token = ((HttpServletRequest)req).getHeader("Auth");
	    
	    if(token == null) {
	    	((HttpServletResponse)resp).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	    	chain.doFilter(req, resp);
	    }
	    
		 JwtProxy proxy = new JwtProxyImpl();
		    proxy.setPayloadHandler(new BasicPayloadHandler());
		    
		    
		try {
			Object payload = proxy.decode(algorithm, token, secret);
			((HttpServletResponse)resp).setHeader("payload", payload.toString());
			
			chain.doFilter(req, resp);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			((HttpServletResponse)resp).setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
		
		
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}
	
}

