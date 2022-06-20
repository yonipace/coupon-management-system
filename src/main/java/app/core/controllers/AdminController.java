package app.core.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import app.core.entities.Company;
import app.core.entities.Customer;
import app.core.exceptions.CouponSystemException;
import app.core.services.AdminService;

@RestController
@RequestMapping("/admin")
@CrossOrigin
public class AdminController {

	@Autowired
	private AdminService adminService;

	@PostMapping("/companies")
	public Company addCompany(@RequestBody Company company, @RequestHeader String token) {
		try {
			return this.adminService.addCompany(company);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}

	}

	@PutMapping("/companies")
	public Company updateCompany(@RequestBody Company company, @RequestHeader String token) {

		try {
			return this.adminService.updateCompany(company);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}

	}

	@DeleteMapping("/companies/{id}")
	public void deleteCompany(@PathVariable int id, @RequestHeader String token) {

		try {
			this.adminService.deleteCompany(id);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}

	}

	@GetMapping("/companies/{id}")
	public Company getOneCompany(@PathVariable int id, @RequestHeader String token) {

		try {
			return adminService.getOneCompany(id);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}

	}

	@GetMapping("/companies")
	public List<Company> getAllCompanies(@RequestHeader String token) {

		try {
			return this.adminService.getAllCompanies();
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}

	}

	@PostMapping("/customers")
	public Customer addCustomer(@RequestBody Customer customer, @RequestHeader String token) {

		try {
			return this.adminService.addCustomer(customer);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}

	}

	@PutMapping("/customers")
	public Customer updateCustomer(@RequestBody Customer customer, @RequestHeader String token) {

		try {
			return this.adminService.updateCustomer(customer);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}

	}

	@DeleteMapping("/customers/{id}")
	public void deleteCustomer(@PathVariable int id, @RequestHeader String token) {

		try {
			this.adminService.deleteCustomer(id);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}

	}

	@GetMapping("/customers/{id}")
	public Customer getOneCustomer(@PathVariable int id, @RequestHeader String token) {

		try {
			return adminService.getOneCustomer(id);
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}

	}

	@GetMapping("/customers")
	public List<Customer> getAllCustomers(@RequestHeader String token) {

		try {
			return this.adminService.getAllCustomers();
		} catch (CouponSystemException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}

	}
}
