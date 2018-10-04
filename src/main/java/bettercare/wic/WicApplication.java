package bettercare.wic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
public class WicApplication {

	public static void main(String[] args) {
		SpringApplication.run(WicApplication.class, args);
	}
}
