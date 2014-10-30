package core.september.rescue.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.auth0.jwt.Algorithm;
import com.auth0.jwt.ClaimSet;
import com.auth0.jwt.JwtProxy;
import com.auth0.jwt.impl.BasicPayloadHandler;
import com.auth0.jwt.impl.JwtProxyImpl;


@Service
public class JWTService {

	final Algorithm algorithm = Algorithm.HS256;

	final JwtProxy proxy = new JwtProxyImpl();
	
	//final ObjectMapper objectMapper = new ObjectMapper();
	
	@Autowired
	private Environment environment;
	
	public JWTService() {
		 proxy.setPayloadHandler(new BasicPayloadHandler());
		// objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
	}
		   
	public String encode (Object payload, int dayExpire) throws Exception {
		
		ClaimSet claimSet = new ClaimSet();
	    claimSet.setExp(24 * 60 * 60 * dayExpire);
		String token = proxy.encode(algorithm, payload, environment.getProperty("APP_KEY"), claimSet);
		return token;
	} 
	
	public String decode(String token) throws Exception {
		String decoded = (String) proxy.decode(algorithm, token, environment.getProperty("APP_KEY"));
		return decoded;
	}
}
