package app.core.login;

import app.core.login.LoginManagerInterface.ClientType;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class LoginCredentials {

	private String email;
	private String password;
	private ClientType client;
}
