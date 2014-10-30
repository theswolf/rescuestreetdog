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
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;

import core.september.rescue.filter.CORSFilter;
import core.september.rescue.filter.JWTFilter;

@Configuration
@EnableAutoConfiguration
@Import(RepositoryRestMvcConfiguration.class)
@EnableMongoRepositories
@ComponentScan

public class RescueStreetDogApp {
	
	private static Log logger = LogFactory.getLog(RescueStreetDogApp.class);

	
	@Autowired
	private Environment env;
	
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
		 registrationBean.setFilter(new JWTFilter(env.getProperty("APP_KEY")));
		 return registrationBean;
	}
	
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
