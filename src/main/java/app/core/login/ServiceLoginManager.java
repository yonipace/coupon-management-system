package app.core.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import app.core.exceptions.CouponSystemException;
import app.core.services.AdminService;
import app.core.services.ClientService;
import app.core.services.CompanyService;
import app.core.services.CustomerService;

@Component
public class ServiceLoginManager implements LoginManagerInterface {

	@Autowired
	private ApplicationContext ctx;

	@Override
	public ClientService login(String email, String password, ClientType client) throws CouponSystemException {

		if (client.equals(ClientType.ADMIN)) {
			AdminService service = ctx.getBean(AdminService.class);
			if (service.login(email, password) != -1) {
				return service;
			}

		} else if (client.equals(ClientType.COMPANY)) {
			CompanyService service = ctx.getBean(CompanyService.class);
			if (service.login(email, password) != -1) {
				return service;
			}

		} else if (client.equals(ClientType.CUSTOMER)) {
			CustomerService service = ctx.getBean(CustomerService.class);
			if (service.login(email, password) != -1) {
				return service;
			}
		}
		return null;

	}

}
