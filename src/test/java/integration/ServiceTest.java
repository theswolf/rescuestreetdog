package integration;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.Assert;

import core.september.rescue.RescueStreetDogApp;
import core.september.rescue.model.User;
import core.september.rescue.service.JWTService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = RescueStreetDogApp.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
@DirtiesContext
@ActiveProfiles(value="test")
public class ServiceTest {

	@Autowired
	private JWTService jwtService;
	
	@Test
	public void testVerify() throws Exception {
		User user = new User();
		user.setId("Pippo");
		//86400
		String token = jwtService.encode(user, 1);
		Thread.sleep(TimeUnit.SECONDS.toMillis(3));
		String decoded = jwtService.decode(token);
		Map<String,Object> verified = jwtService.verify(token);
		System.out.println(System.currentTimeMillis());
		System.out.println(verified.get("expire"));
		Assert.notNull(verified);
	}
}
