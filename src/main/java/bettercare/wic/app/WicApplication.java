package bettercare.wic.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication(scanBasePackages = {"bettercare.wic"})
public class WicApplication {

	public static void main(String[] args) {
		SpringApplication.run(WicApplication.class, args);
	}
}
