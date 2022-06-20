package app.core.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.core.entities.Company;
import app.core.entities.Coupon;
import app.core.entities.Customer;
import app.core.exceptions.CouponSystemException;

@Service
@Transactional
public class AdminService extends ClientService {

	@Value("${client.admin.email}")
	private String email;
	@Value("${client.admin.password}")
	private String password;

	@Override
	public int login(String email, String password) {

		return email.equals(this.email) && password.equals(this.password) ? 0 : -1;
	}

	public Company addCompany(Company company) throws CouponSystemException {

		// each company must have a unique email
		if (companyRepo.existsByEmail(company.getEmail())) {
			throw new CouponSystemException("failed to add company - email already exists");
		}
		// each company must have a unique name
		if (companyRepo.existsByName(company.getName())) {
			throw new CouponSystemException("failed to add company - name already exists");
		}
		System.out.println("company " + company.getId() + " added successfully");
		return companyRepo.save(company);

	}

	public Company updateCompany(Company company) throws CouponSystemException {

		Company companyFromDB = companyRepo.findById(company.getId()).orElseThrow(() -> new CouponSystemException(
				"failed to update company - company " + company.getId() + " does not exist"));

		// company name must stay the same when performing an update
		company.setName(companyFromDB.getName());
		// update company
		System.out.println("company " + company.getId() + " updated successfully");
		return companyRepo.save(company);
	}

	public void deleteCompany(int companyId) throws CouponSystemException {

		// check if company exists
		if (!companyRepo.existsById(companyId)) {
			throw new CouponSystemException("failed to delete company - company " + companyId + " does not exist");
		}
		// delete company
		companyRepo.deleteById(companyId);

		System.out.println("company " + companyId + " deleted successfully");
	}

	public List<Company> getAllCompanies() throws CouponSystemException {

		return companyRepo.findAll();
	}

	public Company getOneCompany(int companyId) throws CouponSystemException {

		return companyRepo.findById(companyId)
				.orElseThrow(() -> new CouponSystemException("company " + companyId + " does not exist"));
	}

	public Customer addCustomer(Customer customer) throws CouponSystemException {

		// each customer must have a unique email
		if (customerRepo.existsByEmail(customer.getEmail())) {
			throw new CouponSystemException(
					"failed to add customer - email " + customer.getEmail() + " already exists");
		}

		System.out.println("customer " + customer.getId() + " added successfully");

		return customerRepo.save(customer);
	}

	public Customer updateCustomer(Customer customer) throws CouponSystemException {

		// check if the customer exists (by id)
		if (!customerRepo.existsById(customer.getId())) {
			throw new CouponSystemException(
					"failed to update customer - customer " + customer.getId() + " does not exist");
		}
		// update customer
		System.out.println("customer " + customer.getId() + " updated successfully");

		return customerRepo.save(customer);
	}

	public void deleteCustomer(int customerId) throws CouponSystemException {

		// check if the customer exists (by id)
		if (!customerRepo.existsById(customerId)) {
			throw new CouponSystemException("failed to delete customer - customer " + customerId + " does not exist");
		}
		// delete customer
		customerRepo.deleteById(customerId);

		System.out.println("customer " + customerId + " deleted successfully");
	}

	public List<Customer> getAllCustomers() throws CouponSystemException {

		return customerRepo.findAll();
	}

	public Customer getOneCustomer(int customerId) throws CouponSystemException {

		return customerRepo.findById(customerId)
				.orElseThrow(() -> new CouponSystemException("customer " + customerId + " does not exist"));
	}

	public List<Coupon> getAllCoupons() {

		return couponRepo.findAll();
	}

}
