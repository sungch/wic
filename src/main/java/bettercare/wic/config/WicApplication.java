package bettercare.wic.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication(scanBasePackages = "bettercare.wic")
public class WicApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(WicApplication.class, args);
	}

}
