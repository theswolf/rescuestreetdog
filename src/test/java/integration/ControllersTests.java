/*
 * Copyright 2012-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package integration;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import core.september.rescue.RescueStreetDogApp;
import core.september.rescue.controller.SignInController.LoginRequest;
import core.september.rescue.controller.SignUpController.SignUpParms;
import core.september.rescue.model.User;
import core.september.rescue.repo.UserRepo;
import core.september.rescue.service.UserService;

/**
 * Basic integration tests for demo application.
 *
 * @author Dave Syer
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = RescueStreetDogApp.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
@DirtiesContext
@ActiveProfiles(value="test")
public class ControllersTests {
	
	@Autowired
	private Environment environment;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private UserService userService;
	
	@Before
	public void prepareForTest() {
		userRepo.deleteAll();
	}

	@Value("${local.server.port}")
	private int port;

	
	//@Test
	
	
	//@Test
	public void showallparameters() {
		
//		 Map<String, Object> map = new HashMap();
//	        for(Iterator it = ((AbstractEnvironment) environment).getPropertySources().iterator(); it.hasNext(); ) {
//	            PropertySource propertySource = (PropertySource) it.next();
//	            if (propertySource instanceof MapPropertySource) {
//	                map.putAll(((MapPropertySource) propertySource).getSource());
//	            }
//	        }
//		
//		//Map<String, String> env = environment.getProperty(arg0)getenv();
//		for (String envName : map.keySet()) {
//		    System.out.format("%s=%s%n", envName, map.get(envName));
//		}
		System.out.println(environment.getProperty("APP_KEY"));
	}
	
	private ResponseEntity<String> signup(String user,String password, String confirmPassword) {
		SignUpParms simpleParams = new SignUpParms();
		simpleParams.setUsername(user);
		simpleParams.setPassword(password);
		simpleParams.setConfirmPassword(confirmPassword == null ? password : confirmPassword);
		
		ResponseEntity<String> response = new TestRestTemplate().postForEntity(
				"http://localhost:" + this.port+"/signup/me", simpleParams, String.class);
		
		return response;
	}
	
	private ResponseEntity<String> login(String user,String password) {
		LoginRequest loginReq = new LoginRequest();
		loginReq.setUsername(user);
		loginReq.setPassword(password);
		
		ResponseEntity<String> response = new TestRestTemplate().postForEntity(
				"http://localhost:" + this.port+"/login", loginReq, String.class);
		
		return response;
	}
	
	@Test
	public void testSignupFail() {
	
		
		ResponseEntity<String> response = signup("pippo@gmail.com","kayak","uno");
		
		Assert.assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
	}
	
	@Test
	public void testSignupOk() {
	
		
		ResponseEntity<String> response = signup("pippo@gmail.com","kayak","uno");
		 response = signup("pippo@gmail.com","kayak",null);
		
		Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	public void testLogin() throws Exception {
		
		userService.saveUser("pluto@gmail.com", "kayak");
		ResponseEntity<String>  response = login("pluto@gmail.com", "kayak");
		
		Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	public void testRest() {
		User user = new User();
		user.setUsername("user");
		user.setHashedPassword("password");
		
		ResponseEntity<String> response = new TestRestTemplate().postForEntity(
				"http://localhost:" + this.port+"/api/data/users", user, String.class);
		
		System.out.println(response.getBody());
		Assert.assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
	}
	
	@Test
	public void testModify() {
		User user = new User();
		user.setUsername("user");
		user.setHashedPassword("password");
		
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.add("Auth", signup("user", "password",null).getBody());
		HttpEntity<User> requestEntity = new HttpEntity<User>(requestHeaders);
		
		ResponseEntity<String> response = new TestRestTemplate().exchange(
				"http://localhost:" + this.port+"/api/data/user/1",HttpMethod.DELETE, requestEntity, String.class);
		
		System.out.println(response.getBody());
		Assert.assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
	}
	

}
