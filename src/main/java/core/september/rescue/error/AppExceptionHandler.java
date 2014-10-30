package core.september.rescue.error;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class AppExceptionHandler {
	@ExceptionHandler(Exception.class)
	
	public void handleAllException(Exception ex,HttpServletResponse resp) {
 
		
		try {
			resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
			resp.getWriter().append(ex.getMessage());
		} catch (IOException e) {
			Logger.getLogger(this.getClass()).error(e);
		}
	}
}
