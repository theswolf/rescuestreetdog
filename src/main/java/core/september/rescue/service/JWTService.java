package core.september.rescue.service;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.auth0.jwt.Algorithm;
import com.auth0.jwt.ClaimSet;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.JwtProxy;
import com.auth0.jwt.impl.BasicPayloadHandler;
import com.auth0.jwt.impl.JwtProxyImpl;
import com.auth0.jwt.internal.com.fasterxml.jackson.databind.JsonNode;
import com.auth0.jwt.internal.org.apache.commons.codec.binary.Base64;


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
		   
	public String encode (Object payload, int expire) throws Exception {
		
		ClaimSet claimSet = new ClaimSet();
	    //claimSet.setExp(24 * 60 * 60 * dayExpire);
		claimSet.setExp(timeConverter(expire));
		String token = proxy.encode(algorithm, payload, environment.getProperty("APP_KEY"), claimSet);
		return token;
	} 
	
	public String decode(String token) throws Exception {
		String decoded = (String) proxy.decode(algorithm, token, environment.getProperty("APP_KEY"));
		return decoded;
	}
	
	public Map<String,Object> verify(String token) throws Exception {
		JWTVerifier verifier = new JWTVerifier(Base64.encodeBase64String(environment.getProperty("APP_KEY").getBytes()));
		return verifier.verify(token);
	}
	
	private int timeConverter(int input) {
		String timeUnit = environment.getProperty("jwt.expiration.unit");
		Long value;
		switch (timeUnit) {
		case "D":
			value = TimeUnit.DAYS.toSeconds(input);
			break;

		default:
			value = TimeUnit.SECONDS.toSeconds(input);
			break;
			
		}
		return value.intValue();
	}
	
}
