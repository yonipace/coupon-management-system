package app.core.login;

import app.core.login.LoginManagerInterface.ClientType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Auth {

	private String email;
	private ClientType client;
	private String token;

}
