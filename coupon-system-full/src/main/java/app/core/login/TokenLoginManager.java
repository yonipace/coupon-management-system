package app.core.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import app.core.exceptions.CouponSystemException;
import app.core.services.AdminService;
import app.core.services.CompanyService;
import app.core.services.CustomerService;
import app.core.utilities.JwtUtil;

@Component
public class TokenLoginManager implements LoginManagerInterface {

	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	private ApplicationContext ctx;

	@Override
	public String login(String email, String password, ClientType client) throws CouponSystemException {

		if (client.equals(ClientType.ADMIN)) {
			AdminService service = ctx.getBean(AdminService.class);
			if (service.login(email, password) != -1) {
				int id = service.login(email, password);
				return jwtUtil.generateToken(id, email, ClientType.ADMIN);
			}
		}
		if (client.equals(ClientType.COMPANY)) {
			CompanyService service = ctx.getBean(CompanyService.class);
			if (service.login(email, password) != -1) {
				int id = service.login(email, password);
				return jwtUtil.generateToken(id, email, ClientType.COMPANY);
			}
		}
		if (client.equals(ClientType.CUSTOMER)) {
			CustomerService service = ctx.getBean(CustomerService.class);
			if (service.login(email, password) != -1) {
				int id = service.login(email, password);
				return jwtUtil.generateToken(id, email, ClientType.CUSTOMER);
			}
		}
		throw new CouponSystemException("login failed - email or password are incorrect");
	}

}
