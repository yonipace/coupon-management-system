package app.core.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.core.entities.Coupon;
import app.core.entities.Coupon.Category;
import app.core.entities.Customer;
import app.core.exceptions.CouponSystemException;

@Service
@Transactional
public class CustomerService extends ClientService {

	@Override
	public int login(String email, String password) {

		Optional<Customer> opt = this.customerRepo.findByEmailAndPassword(email, password);

		return opt.isPresent() ? opt.get().getId() : -1;
	}

	public Coupon purchaseCoupon(Coupon coupon, int id) throws CouponSystemException {

		// check if coupon exists
		if (!couponRepo.existsById(coupon.getId())) {
			throw new CouponSystemException("failed to purchase " + coupon.getTitle() + " - coupon does not exist");
		}
		// check if coupon has been purchased by the customer
		if (couponRepo.existsByIdAndCustomersId(coupon.getId(), id)) {
			throw new CouponSystemException("failed to purchase " + coupon.getTitle() + " - coupon already purchased");
		}
		// check if the coupon is out of stock
		if (coupon.getAmount() == 0) {
			throw new CouponSystemException("failed to purchase " + coupon.getTitle() + " - coupon is out of stock");
		}
		// check if the coupon has expired
		if (coupon.getEndDate().isBefore(LocalDate.now())) {
			throw new CouponSystemException("failed to purchase " + coupon.getTitle() + " - coupon expired");

		}
		// purchase coupon
		getCustomerDetails(id).addCoupon(coupon);
		// update coupon amount
		coupon.setAmount(coupon.getAmount() - 1);
		// update coupon in DB
		return couponRepo.save(coupon);

//		System.out.printf("coupon %d purchased successfully \n", coupon.getId());
	}

	public List<Coupon> getCustomerCoupons(int id) throws CouponSystemException {

		return couponRepo.findAllByCustomersId(id);

	}

	public List<Coupon> getCustomerCoupons(Category category, int id) throws CouponSystemException {

		return couponRepo.findAllByCustomersIdAndCategory(id, category);

	}

	public List<Coupon> getCustomerCoupons(double maxPrice, int id) throws CouponSystemException {

		return couponRepo.findAllByCustomersIdAndPriceLessThanEqual(id, maxPrice);

	}

	public Customer getCustomerDetails(int id) throws CouponSystemException {

		return customerRepo.findById(id).orElseThrow(

				() -> new CouponSystemException("failed to get customer " + id));
	}

	public List<Coupon> getAllCoupons() throws CouponSystemException {

		return couponRepo.findAll();

	}

}
