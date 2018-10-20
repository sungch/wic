package bettercare.wic.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"bettercare.wic.dal.config","bettercare.wic.service.config"})
public class WicApplication {

	public static void main(String[] args) {
		SpringApplication.run(WicApplication.class, args);
	}
}
