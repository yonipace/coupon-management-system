package app.core.services;

import org.springframework.beans.factory.annotation.Autowired;

import app.core.repositories.CompanyRepository;
import app.core.repositories.CouponRepository;
import app.core.repositories.CustomerRepository;

public abstract class ClientService {

	@Autowired
	protected CouponRepository couponRepo;
	@Autowired
	protected CompanyRepository companyRepo;
	@Autowired
	protected CustomerRepository customerRepo;

	/**
	 * checks the email and password against the DB (calling a JPA Repository
	 * method). if the client credentials are authenticated returns the client id to
	 * the loginManager. the id is then used by the loginManager to create a token
	 * for the client.
	 * 
	 * 
	 * 
	 * 
	 * @param email
	 * @param password
	 * @return client Id (int) or -1 if the email or password are incorrect
	 */
	public abstract int login(String email, String password);

}
