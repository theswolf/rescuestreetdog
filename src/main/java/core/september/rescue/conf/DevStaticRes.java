package core.september.rescue.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Profile(value={"dev","test"})
@EnableWebMvc
@Configuration
public class DevStaticRes extends WebMvcConfigurerAdapter {
	
//	private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {
//	        "classpath:/app/"};

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
	    registry.addResourceHandler("/app/**").addResourceLocations("/app/");
	    registry.addResourceHandler("app/bower_components/**").addResourceLocations("/bower_components/");
	}
	
}
