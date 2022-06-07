package app.core.tests;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;

import app.core.entities.Company;
import app.core.entities.Coupon;
import app.core.entities.Coupon.Category;
import app.core.exceptions.CouponSystemException;
import app.core.login.LoginManagerInterface.ClientType;
import app.core.login.ServiceLoginManager;
import app.core.services.AdminService;
import app.core.services.CompanyService;
import app.core.utilities.JwtUtil;

//@Component
@Order(value = 2)
public class CompanyTestRunner implements CommandLineRunner {

	@Autowired
	private ServiceLoginManager loginManager;
	@Autowired
	private AdminService adminService;
	@Autowired
	private JwtUtil jwtUtil;

	private String token;
	private Company testCompany;
	private int idToUpdate;

	@Override
	public void run(String... args) throws Exception {

		Thread.sleep(3000);

		System.out.println("**********starting company test**********");
		System.out.println();

		// get company list and pick a random company to log in
		List<Company> companies = adminService.getAllCompanies();
		if (!companies.isEmpty()) {
			int index = (int) (Math.random() * companies.size());
			this.testCompany = companies.get(index);
		} else {
			throw new CouponSystemException("company test failed - no companies exist");
		}

		System.out.println("**********logging in**********");

		CompanyService service = (CompanyService) loginManager.login(this.testCompany.getEmail(),
				this.testCompany.getPassword(), ClientType.COMPANY);

		System.out.println("**********adding new coupons**********");

		Coupon c1 = new Coupon(0, Category.FOOD, "free pizza", "free slice", LocalDate.now(),
				LocalDate.now().plusMonths(3), 100, 7.5, "img", testCompany, null);
		Coupon c2 = new Coupon(0, Category.FOOD, "25% discount", "25% off second burger", LocalDate.now(),
				LocalDate.now().plusMonths(6), 300, 20, "img", testCompany, null);
		Coupon c3 = new Coupon(0, Category.VACATION, "3rd night free", "3rd night free when paying for 2",
				LocalDate.now(), LocalDate.now().plusMonths(3), 100, 250, "img", testCompany, null);
		Coupon c4 = new Coupon(0, Category.ELECTRICITY, "10% off electronics", "10% first item", LocalDate.now(),
				LocalDate.now().plusMonths(12), 700, 25, "img", testCompany, null);
		Coupon c5 = new Coupon(0, Category.RESTAURANT, "desert at 1/2 price", "50% off desert when buying a full meal",
				LocalDate.now(), LocalDate.now().plusMonths(7), 10, 50, "img", testCompany, null);

		Coupon[] couponsArray = { c1, c2, c3, c4, c5 };

		for (Coupon coupon : couponsArray) {
			try {
				service.addCoupon(coupon, jwtUtil.extractId(token));
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}

		System.out.println("**********getting all coupons**********");

		try {

			List<Coupon> coupons = service.getCompanyCoupons(jwtUtil.extractId(token));
			for (Coupon coupon : coupons) {
				System.out.println(coupon);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		System.out.println("**********getting all coupons by category**********");

		try {

			for (Category category : Category.values()) {

				System.out.println("----- " + category + " -----");

				List<Coupon> couponsByCategory = service.getCompanyCoupons(category, jwtUtil.extractId(token));
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
			int price = (int) (Math.random() * 900 + 100);

			System.out.println("----- max price: " + price + " -----");

			List<Coupon> couponsUpToPrice = service.getCompanyCoupons(price, jwtUtil.extractId(token));
			for (Coupon coupon : couponsUpToPrice) {
				System.out.println(coupon);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		System.out.println("**********updating coupons**********");

		try {

			// pick random coupon to be updated

			List<Coupon> coupons = service.getCompanyCoupons(jwtUtil.extractId(token));
			if (!coupons.isEmpty()) {
				int index = (int) (Math.random() * coupons.size());
				idToUpdate = coupons.get(index).getId();
			} else {
				throw new CouponSystemException("failed to get coupon - no coupons exist");
			}

			Coupon couponToUpdate = service.getOneCoupon(idToUpdate, jwtUtil.extractId(token));
			System.out.println("coupon to be updated: " + couponToUpdate);

			couponToUpdate.setTitle("new_title");
			couponToUpdate.setDescription("new_description");
			couponToUpdate.setStartDate(LocalDate.of(2022, 02, 22));
			couponToUpdate.setEndDate(LocalDate.of(2023, 03, 23));
			couponToUpdate.setAmount(1000);
			couponToUpdate.setPrice(1000);
			couponToUpdate.setCompany(new Company());
			service.updateCoupon(couponToUpdate, jwtUtil.extractId(token));

			System.out.println("updated coupon: " + couponToUpdate);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		System.out.println("**********deleting coupons**********");

		try {

			service.deleteCoupon(idToUpdate, jwtUtil.extractId(token));

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.println("**********company test ended**********");
		System.out.println();

		System.out.println("**********add coupons for customer test**********");
		System.out.println("**********expired and out of stock**********");
		// expired coupon
		Coupon c6 = new Coupon(0, Category.RESTAURANT, "buisness lunch", "free drinks with meal", LocalDate.now(),
				LocalDate.now().minusDays(7), 10, 50, "img", testCompany, null);
		// out of stock
		Coupon c7 = new Coupon(0, Category.VACATION, "buisness lounge", "free entrance to the lounge", LocalDate.now(),
				LocalDate.now().plusMonths(6), 0, 50, "img", testCompany, null);

		try {

			service.addCoupon(c6, jwtUtil.extractId(token));
			service.addCoupon(c7, jwtUtil.extractId(token));
		} catch (CouponSystemException e) {
			System.out.println(e.getMessage());
		}

	}

}
