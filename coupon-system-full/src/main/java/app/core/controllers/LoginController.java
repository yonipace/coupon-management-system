package app.core.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.JsonProcessingException;

import app.core.exceptions.CouponSystemException;
import app.core.login.Auth;
import app.core.login.LoginCredentials;
import app.core.login.LoginManagerInterface.ClientType;
import app.core.login.TokenLoginManager;

@RestController
@RequestMapping("/login")
@CrossOrigin
public class LoginController {

	@Autowired
	private TokenLoginManager loginManager;

	@PostMapping
	public Auth Login(@RequestBody LoginCredentials credentials) throws JsonProcessingException {

		System.out.println(credentials);

		String email = credentials.getEmail();
		String password = credentials.getPassword();
		ClientType client = credentials.getClient();

		if (email.equals("admin@admin.com") && password.equals("admin")) {
			client = ClientType.ADMIN;
		}

		try {

			return new Auth(email, client, loginManager.login(email, password, client));

		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
		}
	}

}
