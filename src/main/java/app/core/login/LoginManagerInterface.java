package app.core.login;

import app.core.exceptions.CouponSystemException;

public interface LoginManagerInterface {

	Object login(String email, String password, ClientType client) throws CouponSystemException;

	enum ClientType {

		ADMIN, COMPANY, CUSTOMER

	}

}
