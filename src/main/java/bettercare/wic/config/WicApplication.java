package bettercare.wic.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"bettercare.wic"})
public class WicApplication {

	public static void main(String[] args) {
		SpringApplication.run(WicApplication.class, args);
	}

}
