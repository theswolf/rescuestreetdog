package core.september.rescue;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import core.september.rescue.conf.RestDataConfig;
import core.september.rescue.filter.CORSFilter;
import core.september.rescue.filter.JWTFilter;
import core.september.rescue.service.JWTService;
import core.september.rescue.service.UserService;

@Configuration
@EnableAutoConfiguration
@Import(RestDataConfig.class)
@EnableMongoRepositories
@ComponentScan

public class RescueStreetDogApp {
	
	private static Log logger = LogFactory.getLog(RescueStreetDogApp.class);

	
	@Autowired
	private Environment env;
	
	@Autowired
	private JWTService jwtService;
	
	@Autowired
	private UserService userService;
	
	@Bean
	FilterRegistrationBean corsFilter() {
		FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		 registrationBean.setFilter(new CORSFilter());
		 return registrationBean;
	}
	
	@Bean
	FilterRegistrationBean jwtFilter() {
		FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		 registrationBean.addUrlPatterns("/api/sec/*");
		 registrationBean.setFilter(new JWTFilter(jwtService));
		 return registrationBean;
	}
	
//	@Bean
//	FilterRegistrationBean dataFilter() {
//		FilterRegistrationBean registrationBean = new FilterRegistrationBean();
//		 registrationBean.addUrlPatterns("/api/data/*");
//		 registrationBean.setFilter(new DataFilter(jwtService,userService));
//		 return registrationBean;
//	}
	
	@Bean
	protected ServletContextListener listener() {
		return new ServletContextListener() {

			@Override
			public void contextInitialized(ServletContextEvent sce) {
				logger.info("ServletContext initialized");
			}

			@Override
			public void contextDestroyed(ServletContextEvent sce) {
				logger.info("ServletContext destroyed");
			}

		};
	}
	
	

	public static void main(String[] args) throws Exception {
		SpringApplication.run(RescueStreetDogApp.class, args);
	}
}
