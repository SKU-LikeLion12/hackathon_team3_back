package hackathon2024.hackathon2024_jh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class Hackathon2024JhApplication {

	public static void main(String[] args) {
		SpringApplication.run(Hackathon2024JhApplication.class, args);
	}

}
