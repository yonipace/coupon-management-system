package app.core.tests;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;

import app.core.entities.Coupon;
import app.core.entities.Coupon.Category;
import app.core.entities.Customer;
import app.core.exceptions.CouponSystemException;
import app.core.login.LoginManagerInterface.ClientType;
import app.core.login.ServiceLoginManager;
import app.core.services.AdminService;
import app.core.services.CustomerService;
import app.core.utilities.JwtUtil;

//@Component
@Order(value = 3)
public class CustomerTestRunner implements CommandLineRunner {

	@Autowired
	private ServiceLoginManager loginManager;
	@Autowired
	private AdminService adminService;
	@Autowired
	private JwtUtil jwtUtil;
	private String token;
	private Customer testCustomer;

	@Override
	public void run(String... args) throws Exception {

		Thread.sleep(3000);

		System.out.println("**********starting customer test**********");
		System.out.println();

		// get customer list and pick a random customer to log in
		List<Customer> customers = adminService.getAllCustomers();
		if (!customers.isEmpty()) {
			int index = (int) (Math.random() * customers.size());
			this.testCustomer = customers.get(index);
		} else {
			throw new CouponSystemException("customer test failed - no customers exist");
		}

		System.out.println("**********logging in**********");

		CustomerService service = (CustomerService) loginManager.login(this.testCustomer.getEmail(),
				this.testCustomer.getPassword(), ClientType.CUSTOMER);

		System.out.println("**********purchase coupon**********");

		// get existing coupons list
		List<Coupon> coupons = adminService.getAllCoupons();
		if (!coupons.isEmpty()) {
			for (Coupon coupon : coupons) {
				try {
					service.purchaseCoupon(coupon, jwtUtil.extractId(token));
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
		} else {
			throw new CouponSystemException("failed to purchase coupons - no coupons exist");
		}

		System.out.println("**********getting all coupons**********");

		try {
			List<Coupon> purchasedCoupons = service.getCustomerCoupons(jwtUtil.extractId(token));
			for (Coupon coupon : purchasedCoupons) {
				System.out.println(coupon);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		System.out.println("**********getting all coupons by category**********");

		try {

			for (Category category : Category.values()) {

				System.out.println("----- " + category + " -----");

				List<Coupon> couponsByCategory = service.getCustomerCoupons(category, jwtUtil.extractId(token));
				for (Coupon coupon : couponsByCategory) {
					System.out.println(coupon);
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		System.out.println("**********getting all coupons up to price**********");

		try {

			// get random price
			double price = Math.random() * 900 + 100;

			System.out.println("----- max price: " + price + " -----");

			List<Coupon> couponsUpToPrice = service.getCustomerCoupons(price, jwtUtil.extractId(token));
			for (Coupon coupon : couponsUpToPrice) {
				System.out.println(coupon);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		System.out.println("**********customer test ended**********");
		System.out.println();

	}
}
