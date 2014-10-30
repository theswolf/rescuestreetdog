package core.september.rescue.controller;

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("info")
public class InfoController {
	
	@Autowired
	private Environment environment;
	
	@RequestMapping(value="environment")
	public void showEnvironmet(HttpServletResponse response) throws IOException {
		ServletOutputStream out = response.getOutputStream();
		out.println("Profile:"+environment.getProperty("spring.active.profiles") );
		out.println("App key:"+environment.getProperty("APP_KEY") );
		out.println("spring.data.mongodb.host:"+environment.getProperty("spring.data.mongodb.host"));
		out.println("spring.data.mongodb.port:"+environment.getProperty("spring.data.mongodb.post"));
		out.println("spring.data.mongodb.database:"+environment.getProperty("spring.data.mongodb.database"));
	}
}
