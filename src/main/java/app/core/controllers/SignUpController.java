package app.core.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import app.core.entities.Company;
import app.core.entities.Customer;
import app.core.exceptions.CouponSystemException;
import app.core.login.Auth;
import app.core.login.LoginManagerInterface.ClientType;
import app.core.services.AdminService;
import app.core.utilities.JwtUtil;

@RestController
@RequestMapping("/signup")
@CrossOrigin
public class SignUpController {

	@Autowired
	private AdminService service;
	@Autowired
	private JwtUtil jwtUtil;

	@PostMapping("/company")
	public Auth companySignUp(@RequestBody Company company) {

		try {
			Company newCompany = service.addCompany(company);
			String token = jwtUtil.generateToken(newCompany.getId(), newCompany.getEmail(), ClientType.COMPANY);
			return new Auth(newCompany.getEmail(), ClientType.COMPANY, token);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	@PostMapping("/customer")
	public Auth customerSignUp(@RequestBody Customer customer) {

		try {
			Customer newCustomer = service.addCustomer(customer);

			String token = jwtUtil.generateToken(newCustomer.getId(), newCustomer.getEmail(), ClientType.CUSTOMER);
			return new Auth(newCustomer.getEmail(), ClientType.CUSTOMER, token);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

}
