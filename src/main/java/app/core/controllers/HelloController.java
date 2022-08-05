package app.core.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

	@Value("${spring.datasource.username}")
	private String dbUser;
	@Value("${spring.datasource.password}")
	private String dbPassword;
	@Value("${wt.util.secret.key}")
	private String jwtSecret;

	@GetMapping("/hello")
	public List<String> hello() {

		return List.of(dbUser, dbPassword, jwtSecret);

	}

}
