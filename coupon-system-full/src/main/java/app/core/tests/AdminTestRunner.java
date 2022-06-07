package app.core.tests;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;

import app.core.entities.Company;
import app.core.entities.Customer;
import app.core.exceptions.CouponSystemException;
import app.core.login.ServiceLoginManager;
import app.core.login.LoginManagerInterface.ClientType;
import app.core.services.AdminService;

//@Component
@Order(value = 1)
public class AdminTestRunner implements CommandLineRunner {

	@Autowired
	private ServiceLoginManager loginManager;

	private int companyIdToUpdate;
	private int customerIdToUpdate;

	@Override
	public void run(String... args) throws Exception {

		Thread.sleep(3000);

		System.out.println("**********starting admin test**********");
		System.out.println();

		System.out.println("**********logging in**********");

		AdminService service = (AdminService) loginManager.login("admin@admin.com", "admin", ClientType.ADMIN);

		System.out.println("**********adding new companies**********");

		Company cmp1 = new Company(0, "robotics", "robotics@robotics.com", "robotics", null);
		Company cmp2 = new Company(0, "vegcafe", "vegcafe@vegcafe.com", "vegcafe", null);
		Company cmp3 = new Company(0, "gotrip", "gotrip@gotrip.com", "gotrip", null);
		Company cmp4 = new Company(0, "biomedia", "biomedia@biomedia.com", "biomedia", null);
		Company cmp5 = new Company(0, "pizzapp", "pizzapp@pizzapp.com", "pizzapp", null);

		Company[] comapniesArray = { cmp1, cmp2, cmp3, cmp4, cmp5 };

		for (Company company : comapniesArray) {
			try {
				service.addCompany(company);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}

		System.out.println("**********getting all companies**********");

		try {

			List<Company> companies = service.getAllCompanies();
			for (Company company : companies) {
				System.out.println(company);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		System.out.println("**********updating companies**********");

		try {

			// pick a random company to be updated
			List<Company> companies = service.getAllCompanies();
			if (!companies.isEmpty()) {
				int index = (int) (Math.random() * companies.size());
				companyIdToUpdate = companies.get(index).getId();
			} else {
				throw new CouponSystemException("failed to get company - no companies exist");
			}

			Company toBeUpdatedCompany = service.getOneCompany(companyIdToUpdate);
			toBeUpdatedCompany.setName("newName");
			toBeUpdatedCompany.setEmail("newEmail@email.com");
			toBeUpdatedCompany.setPassword("newPassword");
			service.updateCompany(toBeUpdatedCompany);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		System.out.println("**********getting one company**********");

		try {

			System.out.println(service.getOneCompany(companyIdToUpdate));

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		System.out.println("**********deleting companies**********");

		try {

			service.deleteCompany(companyIdToUpdate);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		System.out.println("**********adding new customers**********");

		Customer c1 = new Customer(0, "Shira", "Cohen", "shira@cohen.com", "shira", null);
		Customer c2 = new Customer(0, "Tom", "Zeev", "tom@zeev.com", "tom", null);
		Customer c3 = new Customer(0, "Zvi", "Yedidya", "zvi@yedidya.com", "zvi", null);
		Customer c4 = new Customer(0, "Yuval", "Ron", "yuval@ron.com", "yuval", null);
		Customer c5 = new Customer(0, "Neta", "Levy", "neta@levy.com", "neta", null);

		Customer[] customersArray = { c1, c2, c3, c4, c5 };

		for (Customer customer : customersArray) {

			try {
				service.addCustomer(customer);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}

		System.out.println("**********getting all customers**********");

		try {

			List<Customer> customers = service.getAllCustomers();
			for (Customer customer : customers) {
				System.out.println(customer);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		System.out.println("**********updating customers**********");

		try {

			// pick a random customer to be updated
			List<Customer> customers = service.getAllCustomers();
			if (!customers.isEmpty()) {
				int index = (int) (Math.random() * customers.size());
				customerIdToUpdate = customers.get(index).getId();
			} else {
				throw new CouponSystemException("failed to get customer - no customers exist");
			}

			Customer toBeUpdatedCustomer = service.getOneCustomer(customerIdToUpdate);
			toBeUpdatedCustomer.setFirstName("newFirstName");
			toBeUpdatedCustomer.setLastName("newLastName");
			toBeUpdatedCustomer.setEmail("newEmail@email.com");
			toBeUpdatedCustomer.setPassword("newPassword");
			service.updateCustomer(toBeUpdatedCustomer);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		System.out.println("**********getting one customer**********");

		try {

			System.out.println(service.getOneCustomer(customerIdToUpdate));

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		System.out.println("**********deleting customers**********");

		try {

			service.deleteCustomer(customerIdToUpdate);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		System.out.println("**********admin test ended**********");
		System.out.println();

	}

}
